package com.velti.messaging.qa.jmeter;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import java.util.Map;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.apache.jmeter.testelement.property.PropertyIterator;

import org.apache.jmeter.testelement.property.TestElementProperty;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.velti.messaging.Message;
import com.velti.messaging.qa.jmeter.gui.JSONSlide;
import com.velti.messaging.qa.jmeter.gui.JSONSlides;
import com.velti.messaging.versions.MessageV6_0;

public class JMSMessageSampler extends AbstractSampler {

	private static final Logger log = LoggingManager.getLoggerForClass();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String MESSAGE = "MESSAGE";
	public static final String JMSFACTORYCLASS = "JMSFACTORYCLASS";
	public static final String JMSURL = "JMSURL";
	public static final String JMSQUEUE = "JMSQUEUE";
	public static final String JMSMODE = "JMSMODE";
	public static final String QUEUETYPE = "QUEUETYPE";
	public static final String WAITFORDR = "WAITFORDR";
	public static final String DRTIMEOUT = "DRTIMEOUT";
	public static final String TESTNAME = "TESTNAME";
	public static final String PAYLOADARGS = "PAYLOADARGS";
	public static final String ROUTINGARGS = "ROUTINGARGS";
	public static final String METADATAARGS = "METADATAARGS";

	private static final String SERVICE_SEND_Q = "SERVICE-%s-UT";
	private static final String SERVICE_RECEIVE_Q = "SERVICE-%s-UO";

	private static final String ACCOUNT_SEND_Q = "ACCOUNT-%s-UO";
	private static final String ACCOUNT_RECEIVE_Q = "ACCOUNT-%s-UT";

	@SuppressWarnings("unchecked")
	@Override
	public SampleResult sample(Entry e) {
		SampleResult ret = new SampleResult();
		boolean isOK = false;

		ret.sampleStart();
		ConnectionFactory fac = null;

		Connection c = null;
		Session inS = null;
		Session outS = null;
		MessageProducer mp = null;
		MessageConsumer mc = null;

		String name = getPropertyAsString(TESTNAME);
		log.info("Name:" + name);

		try {
			Constructor<ConnectionFactory> facClass = (Constructor<ConnectionFactory>) Class
					.forName(getPropertyAsString(JMSFACTORYCLASS))
					.getConstructor(String.class);
			fac = facClass.newInstance(getPropertyAsString(JMSURL));
			c = fac.createConnection();
			inS = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
			outS = c.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Message msg = (Message) getProperty(MESSAGE).getObjectValue();

			PropertyIterator payloadArgs = this.getPayloadArguments()
					.iterator();
			PropertyIterator routingArgs = this.getRoutingArguments()
					.iterator();
			PropertyIterator metadataArgs = this.getMetadataArguments()
					.iterator();

			// Transform JMeter variables into values
			transformMessage(msg, metadataArgs, routingArgs, payloadArgs);

			String targetName = getPropertyAsString(JMSQUEUE);
			String targetType = getPropertyAsString(QUEUETYPE);
			String mode = getPropertyAsString(JMSMODE);
			boolean getDr = getPropertyAsBoolean(WAITFORDR);
			long timeout = getPropertyAsLong(DRTIMEOUT);

			String inQName = null;
			String outQName = null;

			log.info("Target: " + targetName);
			log.info("Target: " + targetType);
			log.info("Mode: " + mode);

			if (targetType.equals("SERVICE")) {
				inQName = String.format(SERVICE_RECEIVE_Q, targetName);
				outQName = String.format(SERVICE_SEND_Q, targetName);
				log.info("Will use SERVICE");
			} else if (targetType.equals("ACCOUNT")) {
				inQName = String.format(ACCOUNT_RECEIVE_Q, targetName);
				outQName = String.format(ACCOUNT_SEND_Q, targetName);
				log.info("Will use ACCOUNT");
			} else {// QUEUE
				log.info("Will use QUEUE");
				if (mode.equals("SEND")) {
					outQName = targetName;
				} else {
					inQName = targetName;
				}
			}

			log.info("Using producer Q:" + outQName);
			log.info("Using consumer Q:" + inQName);

			if (outQName != null) {
				Queue outQ = outS.createQueue(outQName);
				mp = outS.createProducer(outQ);

			}
			if (inQName != null) {
				Queue inQ = inS.createQueue(inQName);
				mc = inS.createConsumer(inQ);
			}

			c.start();

			if (mode.equals("SEND")) {
				ret.setSamplerData(msg.toXML());

				ObjectMessage jmsmsg = inS.createObjectMessage();
				jmsmsg.setObject(msg);
				mp.send(jmsmsg);

				log.info("Sent Message: " + msg.toXML());

				if (getDr) {
					javax.jms.ObjectMessage jmsdr = (ObjectMessage) mc
							.receive(timeout);
					if (jmsdr == null) {
						ret.setResponseData("Timed out without receiving DR"
								.getBytes());
					} else {
						Message dr = (Message) jmsdr.getObject();
						ret.setResponseData(dr.toXML().getBytes());

						log.info("Received message: " + dr.toXML());
						isOK = true;
					}
				} else {
					ret.setResponseData("Can only have data if joined with DR"
							.getBytes());
					isOK = true;
				}
			} else {// RECEIVE
				ret.setSamplerData(String.format(
						"Read from: %s - until queue is empty for %s", inQName,
						timeout));
				ret.setRequestHeaders(String.format("Queue:%s\nTimeout:%s",
						inQName, timeout));

				StringBuilder buf = new StringBuilder();
				while (true) {
					javax.jms.ObjectMessage jmsmsg = (ObjectMessage) mc
							.receive(timeout);
					if (jmsmsg == null) {
						break;
					}
					Message drMsg = (Message) jmsmsg.getObject();
					buf.append(drMsg.toXML());
					isOK = true;
				}
				if (buf.length() == 0) {
					ret.setResponseData("Timed out without receiving DR"
							.getBytes());
				} else {
					buf.insert(0, "<MESSAGES>");
					buf.append("</MESSAGES>");
					ret.setResponseData(buf.toString().getBytes());
				}
			}

			ret.setDataType(SampleResult.TEXT);
			ret.setResponseCodeOK();
			ret.setResponseMessage("OK");

		} catch (Exception ex) {
			ret.setResponseCodeOK();
			ret.setResponseMessage(ex.toString());
			log.error("Oups!", ex);
		} finally {
			try {
				if (inS != null) {
					inS.close();
				}
				if (outS != null) {
					outS.close();
				}
				if (mp != null) {
					mp.close();
				}
				if (mc != null) {
					mc.close();
				}
			} catch (Exception e1) {
				log.error("Oooups!", e1);
			} finally {
				try {
					if (c != null) {
						c.close();
					}
				} catch (JMSException e2) {
					log.error("Oooups!", e2);
				}
			}
		}
		if (isOK) {
			ret.setSampleLabel(name + " - You shall not fail! ");
		} else {
			ret.setSampleLabel(name + " - Even the best systems fail!");
		}

		ret.sampleEnd(); // End timing
		ret.setSuccessful(isOK);

		return ret;
	}

	// This function transform all JMeter variables in Message into values
	private void transformMessage(Message msg, PropertyIterator metadataArgs,
			PropertyIterator routingInfoArgs, PropertyIterator payloadArgs) {
		System.out.println("transform");
		/* Routing INFO */
		while (routingInfoArgs.hasNext()) {
			Argument arg = (Argument) routingInfoArgs.next().getObjectValue();
			String value = arg.getValue();
			if (value.indexOf("${") >= 0) {
				String variableKey = value.substring(value.indexOf("$") + 2,
						value.indexOf("}"));
				if (JMeterContextService.getContext().getVariables().get(
						variableKey) != null) {
					String myValue = JMeterContextService.getContext()
							.getVariables().get(variableKey);
					msg.getRoutingInfo().put(arg.getName(), myValue);
				} else if (value != "")
					msg.getRoutingInfo().put(arg.getName(), value);

			} else if (value != "")
				msg.getRoutingInfo().put(arg.getName(), value);
		}

		/* Metadat INFO */
		while (metadataArgs.hasNext()) {
			Argument arg = (Argument) metadataArgs.next().getObjectValue();
			String value = arg.getValue();
			if (value.indexOf("${") >= 0) {
				String variableKey = value.substring(value.indexOf("$") + 2,
						value.indexOf("}"));
				if (JMeterContextService.getContext().getVariables().get(
						variableKey) != null) {
					String myValue = JMeterContextService.getContext()
							.getVariables().get(variableKey);
					msg.getMetadata().put(arg.getName(), myValue);
				} else if (value != "")
					msg.getMetadata().put(arg.getName(), value);
			} else if (value != "")
				msg.getMetadata().put(arg.getName(), value);
		}

		/* Payload INFO */
		Map<String, Serializable> tmp = new HashMap<String, Serializable>();
		while (payloadArgs.hasNext()) {
			Argument arg = (Argument) payloadArgs.next().getObjectValue();
			String value = arg.getValue();
			Serializable sValue=null;

			if (value.indexOf("${") >= 0) {
				String variableKey = value.substring(value.indexOf("$") + 2,
						value.indexOf("}"));
				if (JMeterContextService.getContext().getVariables().get(
						variableKey) != null) {
					String myValue = JMeterContextService.getContext()
							.getVariables().get(variableKey);

					value = myValue;
				}
			}

			log.info("Is MMS:"+value.trim().startsWith("{\"SLIDES\":"));
			if (value.trim().startsWith("{\"SLIDES\":")){
				sValue = convertJson(value);
			}else{
				sValue = value;
			}
			if (sValue != null) {
				tmp.put(arg.getName(), sValue);
			}
		}

		msg.setPayload(tmp);

	}

	private ArrayList<HashMap<String, Serializable>> convertJson(String slides) {
		ObjectMapper m = new ObjectMapper();
		log.info("Will load:" + slides);

		ArrayList<HashMap<String, Serializable>> ret = new ArrayList<HashMap<String, Serializable>>();
		try {
			JSONSlides s = m.readValue(slides, JSONSlides.class);

			for (JSONSlide slide : s.getSLIDES()) {
				HashMap<String, Serializable> tmp = new HashMap<String, Serializable>();
				tmp.put(MessageV6_0.PAYLOAD_MMS.SLIDE_DURATION.name(), slide
						.getDURATION());
				tmp.put(MessageV6_0.PAYLOAD_MMS.SLIDE_TEXT.name(), slide
						.getTEXT());
				tmp.put(MessageV6_0.PAYLOAD_MMS.SLIDE_SOUND.name(), slide
						.getSOUND());
				tmp.put(MessageV6_0.PAYLOAD_MMS.SLIDE_IMAGE.name(), slide
						.getIMAGE());
				tmp.put(MessageV6_0.PAYLOAD_MMS.SLIDE_IMAGE_LAYOUT.name(),
						slide.getIMAGE_LAYOUT());
				tmp.put(MessageV6_0.PAYLOAD_MMS.SLIDE_VIDEO.name(), slide
						.getVIDEO());
				tmp.put(MessageV6_0.PAYLOAD_MMS.SLIDE_SLIDE_PROPERTIES.name(),
						slide.getSLIDE_PROPERTIES());

				ret.add(tmp);
			}
		} catch (Exception e) {
			log.error("Oups", e);
			e.printStackTrace();
		}
		return ret;
	}

	public void setPayloadArguments(Arguments value) {
		setProperty(new TestElementProperty(PAYLOADARGS, value));
	}

	public Arguments getPayloadArguments() {
		return (Arguments) getProperty(PAYLOADARGS).getObjectValue();
	}

	public void setRoutingArguments(Arguments value) {
		setProperty(new TestElementProperty(ROUTINGARGS, value));
	}

	public Arguments getRoutingArguments() {
		return (Arguments) getProperty(ROUTINGARGS).getObjectValue();
	}

	public void setMetadataArguments(Arguments value) {
		setProperty(new TestElementProperty(METADATAARGS, value));
	}

	public Arguments getMetadataArguments() {
		return (Arguments) getProperty(METADATAARGS).getObjectValue();
	}
}
