package invoke;

import framework.ConsumerProxy;
import service.HelloService;

/**
 * 服务的调用客户端
 *
 * @author liyebing created on 16/12/25.
 * @version $Id$
 */
public class RpcConsumerMain {

    public static void main(String[] args) throws Exception {
        HelloService service = ConsumerProxy.consume(HelloService.class, "127.0.0.1", 8083);
        for (int i = 0; i < 5; i++) {
            String hello = service.sayHello("liyebing_" + i);
            System.out.println(hello);
            Thread.sleep(1000);
        }
    }

}
