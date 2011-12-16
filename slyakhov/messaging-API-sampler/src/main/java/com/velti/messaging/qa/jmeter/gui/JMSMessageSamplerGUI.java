package com.velti.messaging.qa.jmeter.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.LayoutStyle;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.PowerTableModel;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.ObjectProperty;
import org.apache.jmeter.testelement.property.TestElementProperty;
import org.apache.jorphan.collections.Data;
import org.apache.jorphan.gui.JLabeledChoice;
import org.apache.jorphan.gui.JLabeledTextField;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import com.velti.messaging.DefaultMessage;
import com.velti.messaging.Message;
import com.velti.messaging.qa.jmeter.JMSMessageSampler;
import com.velti.messaging.versions.MessageV8_0;


public class JMSMessageSamplerGUI extends AbstractSamplerGui {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggingManager.getLoggerForClass();

	private JLabeledTextField targetName = new JLabeledTextField("Target Name");

	private ArgumentsPanel payloadArgsPanel;
	private ArgumentsPanel routingArgsPanel;
	private ArgumentsPanel metadataArgsPanel;
	
	private JLabeledTextField JMSConnectionFactory = new JLabeledTextField(
			"JMSConnectionFactory class");
	private JLabeledTextField jmsConnectURL = new JLabeledTextField(
			"JMS connect URL");

	private final JLabeledChoice msgType;
	private final JLabeledChoice targetType;
	private final JLabeledChoice mode;

	private final JLabeledTextField timeout = new JLabeledTextField("Timeout");
	private final JCheckBox waitForDr = new JCheckBox("Wait For DR");

	public JMSMessageSamplerGUI() {
		// Reflect and set MESSAGETYPEs
		MessageV8_0.MESSAGETYPE[] mt = MessageV8_0.MESSAGETYPE.values();
		String[] msgTypes = new String[mt.length];
		for (int i = 0; i < mt.length; i++) {
			msgTypes[i] = mt[i].toString();
		}
		this.msgType = new JLabeledChoice("Message Type", msgTypes);

		// target type
		targetType = new JLabeledChoice("Target type", new String[] {
				"SERVICE", "ACCOUNT", "QUEUE" });

		// mode
		mode = new JLabeledChoice("Mode", new String[] { "SEND", "RECEIVE" });

		init();
	}
    
	private void init() {
		setLayout(new BorderLayout());
		setBorder(makeBorder());
		add(makeTitlePanel(), BorderLayout.NORTH);

		VerticalPanel panel = new VerticalPanel();
		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.add(getConfigPanel(), BorderLayout.NORTH);
		panel.add(getMessagePanel(), BorderLayout.CENTER);

		add(panel, BorderLayout.CENTER);
	}

	private JPanel getConfigPanel() {
		VerticalPanel panel = new VerticalPanel();
		panel.setBorder(BorderFactory.createEtchedBorder());
		VerticalPanel vert1 = new VerticalPanel();
		vert1.add(JMSConnectionFactory);
		vert1.add(jmsConnectURL);
		vert1.add(targetName);
		panel.add(vert1, BorderLayout.NORTH);

		HorizontalPanel horiz2 = new HorizontalPanel();
		horiz2.add(targetType);
		horiz2.add(mode);
		panel.add(horiz2, BorderLayout.CENTER);

		HorizontalPanel drpanel = new HorizontalPanel();
		drpanel.add(waitForDr);
		drpanel.add(timeout);
		panel.add(drpanel, BorderLayout.SOUTH);
		
		HorizontalPanel head = new HorizontalPanel();
		head.add(panel, BorderLayout.CENTER);
		return head;
	}

	private JPanel getMessagePanel() {
		VerticalPanel panel = new VerticalPanel();
		panel.setBorder(BorderFactory.createEtchedBorder());
		HorizontalPanel h1 = new HorizontalPanel();
		h1.add(msgType);
		panel.add(h1);

		// payload
		HorizontalPanel h2 = new HorizontalPanel();
		JLabel pLabel = new JLabel("Content");
		h2.add(pLabel);
		JButton clearPayload = new JButton("Clear");
		h2.add(clearPayload);
		clearPayload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fillPayloadModel(false);
			}
		});
		JButton defaultPayload = new JButton("Default");
		h2.add(defaultPayload);
		defaultPayload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fillPayloadModel(true);
			}
		});

		h2.add(getPayloadPanel(), BorderLayout.CENTER);
		panel.add(h2);
		
		// r info
		VerticalPanel h3 = new VerticalPanel();
		HorizontalPanel h31 = new HorizontalPanel();
		JLabel rLabel = new JLabel("RouInfo");
		h31.add(rLabel);
		JButton clearroutingInfo = new JButton("Clear");
		h31.add(clearroutingInfo);
		clearroutingInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fillRInfoModel(false);
			}
		});
		JButton defaulroutingInfod = new JButton("Default");
		h31.add(defaulroutingInfod);
		defaulroutingInfod.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fillRInfoModel(true);
			}
		});
		h3.add(h31);
		h3.add(getRoutingPanel(),BorderLayout.CENTER);
	

		// meta
		VerticalPanel h4 = new VerticalPanel();
		HorizontalPanel h41 = new HorizontalPanel();
		JLabel mLabel = new JLabel("Metadata");
		h41.add(mLabel);
		JButton clearmetadata = new JButton("Clear");
		h41.add(clearmetadata);
		clearmetadata.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fillMetadataModel(false);
			}
		});
		JButton defaulmetadata = new JButton("Default");
		h41.add(defaulmetadata);
		defaulmetadata.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fillMetadataModel(true);
			}
		});

		h4.add(h41, BorderLayout.NORTH);
		h4.add(getMetadataPanel(), BorderLayout.CENTER);
		
		//Rinfo and Metadata Together
		HorizontalPanel h5 = new HorizontalPanel(); 
		GridLayout experimentLayout = new GridLayout(0,2,25,2);
		h5.setLayout(experimentLayout);
		h5.add(h3);
		h5.add(h4, BorderLayout.EAST);
		panel.add(h5, BorderLayout.CENTER);

		HorizontalPanel head = new HorizontalPanel();
		head.add(panel, BorderLayout.CENTER);
		return head;
	}
    protected JPanel getPayloadPanel() {
        payloadArgsPanel = new ArgumentsPanel("Payload");

        return payloadArgsPanel;
    }
    
    protected JPanel getRoutingPanel() {
        routingArgsPanel = new ArgumentsPanel("Routing Info");
        return routingArgsPanel;
    }
    
    protected JPanel getMetadataPanel() {
        metadataArgsPanel = new ArgumentsPanel("Metadata");

        return metadataArgsPanel;
    }
    
    public void clear() {
        msgType.setText("SMS"); // $NON-NLS-1$
		JMSConnectionFactory.setText("org.apache.activemq.ActiveMQConnectionFactory");// $NON-NLS-1$
		jmsConnectURL.setText("");// $NON-NLS-1$
		targetType.setText("");// $NON-NLS-1$
		mode.setText("");// $NON-NLS-1$
		targetName.setText("");// $NON-NLS-1$

		timeout.setText("");// $NON-NLS-1$
		waitForDr.setSelected(false);
		fillMetadataModel(true);
		fillPayloadModel(true);
		fillRInfoModel(true);
        //payloadArgsPanel.clear();
        //routingArgsPanel.clear();
        //metadataArgsPanel.clear();
    }
    
	@Override
	public TestElement createTestElement() {
		JMSMessageSampler sampler = new JMSMessageSampler();
		this.clear();
		modifyTestElement(sampler);
		return sampler;
	}

	@Override
	public String getLabelResource() {
		return "msm"; // $NON-NLS-1$
	}

	public String getStaticLabel() {
		return "MSM TEAM RULEZ";
	}

	public void configure(TestElement el) {
		super.configure(el);
		//System.out.println("Configure:" + el.toString());
		payloadArgsPanel.configure((TestElement) el.getProperty(JMSMessageSampler.PAYLOADARGS).getObjectValue());
		routingArgsPanel.configure((TestElement) el.getProperty(JMSMessageSampler.ROUTINGARGS).getObjectValue());
		metadataArgsPanel.configure((TestElement) el.getProperty(JMSMessageSampler.METADATAARGS).getObjectValue());
		
		JMSConnectionFactory.setText(el
				.getPropertyAsString(JMSMessageSampler.JMSFACTORYCLASS));
		jmsConnectURL.setText(el.getPropertyAsString(JMSMessageSampler.JMSURL));
		targetType.setText(el.getPropertyAsString(JMSMessageSampler.QUEUETYPE));
		mode.setText(el.getPropertyAsString(JMSMessageSampler.JMSMODE));
		targetName.setText(el.getPropertyAsString(JMSMessageSampler.JMSQUEUE));

		timeout.setText(el.getPropertyAsString(JMSMessageSampler.DRTIMEOUT));
		waitForDr.setSelected(el.getPropertyAsBoolean(JMSMessageSampler.WAITFORDR));
		
		Message msg = (Message) el.getProperty(JMSMessageSampler.MESSAGE)
				.getObjectValue();
		msgType.setText(msg.getMessageType());
		
	}

	private void fillPayloadModel(boolean addDefaults) {
		//System.out.println(payloadModel.toString());
		payloadArgsPanel.clear();
		if (addDefaults) {
				MessageV8_0.PAYLOAD_SMS[] tmpSms = MessageV8_0.PAYLOAD_SMS
						.values();
				Arguments args = new Arguments();
				for (int i = 0; i < tmpSms.length; i++) {
					Argument arg = new Argument();
					arg.setName(tmpSms[i].name());
					arg.setValue("");
					args.addArgument(arg);
			}
			payloadArgsPanel.configure((TestElement) args);
		}

	}

	private void fillMetadataModel(boolean addDefaults) {
		metadataArgsPanel.clear();
			
		if (addDefaults) {
			MessageV8_0.METADATA[] tmpSms = MessageV8_0.METADATA
					.values();
			Arguments args = new Arguments();
			for (int i = 0; i < tmpSms.length; i++) {
				Argument arg = new Argument();
				arg.setName(tmpSms[i].name());
				arg.setValue("");
				args.addArgument(arg);
			}
			metadataArgsPanel.configure((TestElement) args);
		}
	}

	
	
	private void fillRInfoModel(boolean addDefaults) {
		routingArgsPanel.clear();
			
		if (addDefaults) {
			MessageV8_0.ROUTINGINFO[] tmpSms = MessageV8_0.ROUTINGINFO
					.values();
			Arguments args = new Arguments();
			for (int i = 0; i < tmpSms.length; i++) {
				Argument arg = new Argument();
				arg.setName(tmpSms[i].name());
				arg.setValue("");
				args.addArgument(arg);
			}
			routingArgsPanel.configure((TestElement) args);
		}
	}

	@Override
	public void modifyTestElement(TestElement te) {
		te.clear();
		configureTestElement(te);

		Arguments payloadArgs = (Arguments) payloadArgsPanel.createTestElement();
        te.setProperty(new TestElementProperty(JMSMessageSampler.PAYLOADARGS, payloadArgs));

		Arguments routingArgs = (Arguments) routingArgsPanel.createTestElement();
        te.setProperty(new TestElementProperty(JMSMessageSampler.ROUTINGARGS, routingArgs));
        
		Arguments metadataArgs = (Arguments) metadataArgsPanel.createTestElement();
        te.setProperty(new TestElementProperty(JMSMessageSampler.METADATAARGS, metadataArgs));
        
		Message msg = new DefaultMessage(msgType.getText());

		te.setProperty(JMSMessageSampler.JMSFACTORYCLASS, JMSConnectionFactory
				.getText());
		te.setProperty(JMSMessageSampler.JMSURL, jmsConnectURL.getText());
		te.setProperty(JMSMessageSampler.JMSQUEUE, targetName.getText());
		te.setProperty(JMSMessageSampler.JMSMODE, mode.getText());
		log.debug(targetType.getText());
		te.setProperty(JMSMessageSampler.QUEUETYPE, targetType.getText());

		te.setProperty(JMSMessageSampler.DRTIMEOUT, timeout.getText());
		te.setProperty(JMSMessageSampler.WAITFORDR, String.valueOf(waitForDr.isSelected()));

		te.setProperty(JMSMessageSampler.TESTNAME,getName());
		
		
		JMeterProperty p = new ObjectProperty();
		p.setName(JMSMessageSampler.MESSAGE);
		p.setObjectValue(msg);

		te.setProperty(p);
		
	}

}
