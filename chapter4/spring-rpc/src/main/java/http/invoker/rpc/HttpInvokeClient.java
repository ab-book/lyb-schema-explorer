package http.invoker.rpc;

import rpc.common.User;
import rpc.common.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liyebing created on 17/2/12.
 * @version $Id$
 */
public class HttpInvokeClient {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("httpinvoker-rpc-client.xml");
        UserService userService = (UserService) context.getBean("userServiceProxy");

        User user = userService.findByName("liyebing");
        System.out.println(user.getName() + "   " + user.getEmail());
    }

}
