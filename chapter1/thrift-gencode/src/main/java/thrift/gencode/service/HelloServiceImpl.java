package thrift.gencode.service;

import org.apache.thrift.TException;

/**
 * @author liyebing created on 16/12/18.
 * @version $Id$
 */
public class HelloServiceImpl implements HelloService.Iface {

    public String sayHello(User user) throws TException {
        return "hello," + user.getName() + user.getEmail();
    }
}
