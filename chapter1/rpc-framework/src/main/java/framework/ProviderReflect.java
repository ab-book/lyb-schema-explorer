package framework;

import org.apache.commons.lang3.reflect.MethodUtils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liyebing created on 16/12/25.
 * @version $Id$
 */
public class ProviderReflect {


    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 服务的发布
     *
     * @param service
     * @param port
     * @throws Exception
     */
    public static void provider(final Object service, int port) throws Exception {
        if (service == null || port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Illegal param.");
        }

        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            final Socket socket = serverSocket.accept();
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                        try {
                            try {
                                //method name
                                String methodName = objectInputStream.readUTF();
                                //method arguments
                                Object[] arguments = (Object[]) objectInputStream.readObject();

                                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                                try {
                                    //method invoke
                                    Object result = MethodUtils.invokeExactMethod(service, methodName, arguments);
                                    output.writeObject(result);
                                } catch (Throwable t) {
                                    output.writeObject(t);
                                } finally {
                                    output.close();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                objectInputStream.close();
                            }
                        } finally {
                            socket.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


}
