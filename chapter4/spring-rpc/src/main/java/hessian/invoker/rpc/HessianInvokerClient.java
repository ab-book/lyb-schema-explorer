package hessian.invoker.rpc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rpc.common.User;
import rpc.common.UserService;

/**
 * @author liyebing created on 17/2/13.
 * @version $Id$
 */
public class HessianInvokerClient {


    public static void main(String[] args) {


        ApplicationContext context = new ClassPathXmlApplicationContext("hessian-rpc-client.xml");
        UserService userService = (UserService) context.getBean("userServiceHessianProxy");
        User user = userService.findByName("kongxuan");
        System.out.println(user.getName() + "   " + user.getEmail());

    }

}
