package axis2.client;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;

import javax.xml.namespace.QName;

/**
 * @author liyebing created on 16/12/4.
 * @version $Id$
 */
public class Axis2Client {


    public static void main(String[] args) {
        try {
            // http://localhost:8080/services/HelloService?wsdl
            EndpointReference targetEPR = new EndpointReference(
                    "http://localhost:8080/services/HelloService");
            RPCServiceClient serviceClient = new RPCServiceClient();
            Options options = serviceClient.getOptions();
            options.setManageSession(true);
            options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, true);
            options.setTo(targetEPR);
            QName opQName = new QName("http://service.axis2", "sayHello");
            Object[] paramArgs = new Object[]{"kongxuan"};

            //处理返回数据
            Class[] returnTypes = new Class[]{String.class};
            Object[] response = serviceClient.invokeBlocking(opQName, paramArgs, returnTypes);
            serviceClient.cleanupTransport();
            String result = (String) response[0];
            if (result == null) {
                System.out.println("HelloService didn't initialize!");
            } else {
                System.out.println(result);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
