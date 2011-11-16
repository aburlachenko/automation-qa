import org.swift.common.soap.jira.JiraSoapService;
import org.swift.common.soap.jira.JiraSoapServiceServiceLocator;
import org.swift.common.soap.jira.RemoteIssue;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.Scanner;



public class JiraTS {

    public static final String USR = "login";
    public static final String PWD = "pass";
    public static final String HST = "host";
    public static final String ENDPOINT = "/rpc/soap/jirasoapservice-v2";   // Initialize JiraSoapService object, which will be used for all communication with JIRA

   public static void main (String args[]) throws ServiceException, RemoteException {

      JiraSoapServiceServiceLocator jiraSoapServiceGetter = new JiraSoapServiceServiceLocator() {{
      setJirasoapserviceV2EndpointAddress(HST + ENDPOINT);
      setMaintainSession(true);
      }};
      JiraSoapService jiraSoapService = jiraSoapServiceGetter.getJirasoapserviceV2();
      String token = jiraSoapService.login(USR, PWD);


        Scanner in = new Scanner(System.in);

        System.out.println("Enter Jira Ticket ID: ");

        String id = in.nextLine();


       try{

      RemoteIssue ISearch = jiraSoapService.getIssue(token, id);



      //RemoteComment [] GCom = jiraSoapService.getComments(token, jql);


             System.out.println("Description from the issue " +id + ": "+ ISearch.getDescription()  );

       } catch(RemoteException RException) {

         System.out.println ("Ooops :(  Seems that issue does not exists or you do nat have permissions to view it.");

       }

   }
}
















