package ares.remoting.framework.helper;

import ares.remoting.framework.serialization.common.SerializeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author liyebing created on 17/2/2.
 * @version $Id$
 */
public class PropertyConfigeHelper {

    private static final Logger logger = LoggerFactory.getLogger(PropertyConfigeHelper.class);

    private static final String PROPERTY_CLASSPATH = "/ares_remoting.properties";
    private static final Properties properties = new Properties();

    //ZK服务地址
    private static String zkService = "";
    //ZK session超时时间
    private static int zkSessionTimeout;
    //ZK connection超时时间
    private static int zkConnectionTimeout;
    //序列化算法类型
    private static SerializeType serializeType;
    //每个服务端提供者的Netty的连接数
    private static int channelConnectSize;


    /**
     * 初始化
     */
    static {
        InputStream is = null;
        try {
            is = PropertyConfigeHelper.class.getResourceAsStream(PROPERTY_CLASSPATH);
            if (null == is) {
                throw new IllegalStateException("ares_remoting.properties can not found in the classpath.");
            }
            properties.load(is);

            zkService = properties.getProperty("zk_service");
            zkSessionTimeout = Integer.parseInt(properties.getProperty("zk_sessionTimeout", "500"));
            zkConnectionTimeout = Integer.parseInt(properties.getProperty("zk_connectionTimeout", "500"));
            channelConnectSize = Integer.parseInt(properties.getProperty("channel_connect_size", "10"));
            String seriType = properties.getProperty("serialize_type");
            serializeType = SerializeType.queryByType(seriType);
            if (serializeType == null) {
                throw new RuntimeException("serializeType is null");
            }

        } catch (Throwable t) {
            logger.warn("load ares_remoting's properties file failed.", t);
            throw new RuntimeException(t);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String getZkService() {
        return zkService;
    }

    public static int getZkSessionTimeout() {
        return zkSessionTimeout;
    }

    public static int getZkConnectionTimeout() {
        return zkConnectionTimeout;
    }

    public static int getChannelConnectSize() {
        return channelConnectSize;
    }

    public static SerializeType getSerializeType() {
        return serializeType;
    }
}
