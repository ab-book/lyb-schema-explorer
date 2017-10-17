package thrift.gencode.invoker;

import org.apache.thrift.TProcessor;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.*;
import org.junit.Test;
import thrift.gencode.service.HelloService;
import thrift.gencode.service.HelloServiceImpl;
import thrift.gencode.service.User;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 非阻塞I/O服务调用示例
 *
 * @author liyebing created on 17/4/29.
 * @version $Id$
 */
public class NonblockingInvoker {

    /**
     * 启动服务
     */
    @Test
    public void startServer() throws TTransportException {
        int port = 8091;
        //创建processor
        TProcessor tprocessor = new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl());
        //创建transport 非阻塞 nonblocking
        TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(port);
        //创建protocol 数据传输协议
        TCompactProtocol.Factory protocol = new TCompactProtocol.Factory();
        //创建transport 数据传输方式  非阻塞需要用这种方式传输
        TFramedTransport.Factory transport = new TFramedTransport.Factory();
        TNonblockingServer.Args args = new TNonblockingServer.Args(serverTransport);
        args.processor(tprocessor);
        args.transportFactory(transport);
        args.protocolFactory(protocol);
        //创建服务器 类型是非阻塞
        TServer server = new TNonblockingServer(args);
        //开启服务
        server.serve();
    }


    /**
     * 客户端调用服务端
     */
    @Test
    public void startClient() throws Exception {
        String ip = "127.0.0.1";
        int port = 8091;
        int timeOut = 1000;

        TAsyncClientManager clientManager = new TAsyncClientManager();
        TNonblockingTransport transport = new TNonblockingSocket(ip, port, timeOut);
        TProtocolFactory tprotocol = new TCompactProtocol.Factory();
        HelloService.AsyncClient asyncClient = new HelloService.AsyncClient(tprotocol, clientManager, transport);

        //客户端异步调用
        User user = new User();
        user.setName("liyebing");
        user.setEmail("test@163.com");
        CountDownLatch latch = new CountDownLatch(1);
        AsynInvokerCallback callBack = new AsynInvokerCallback(latch);
        asyncClient.sayHello(user, callBack);

        latch.await(5, TimeUnit.SECONDS);
    }

}
