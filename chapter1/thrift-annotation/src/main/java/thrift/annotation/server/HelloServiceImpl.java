package thrift.annotation.server;

/**
 * @author liyebing created on 16/12/17.
 * @version $Id$
 */
public class HelloServiceImpl implements HelloService {
    public String sayHello(User user) {
        return "hello," + user.getName() + user.getEmail();
    }
}
