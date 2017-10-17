import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

/**
 * @author liyebing created on 17/5/1.
 * @version $Id$
 */
public class ObjenesisDemo {


    public static void main(String[] args) {
        //实例化对象第一种方式
        Objenesis objenesis1 = new ObjenesisStd();
        MessageInfo messageInfo1 = (MessageInfo) objenesis1.newInstance(MessageInfo.class);
        System.out.println(messageInfo1.getClass());

        //实例化对象第二种方式(ObjectInstantiator线程安全,可复用,可提高性能)
        Objenesis objenesis2 = new ObjenesisStd();
        ObjectInstantiator instantiator = objenesis2.getInstantiatorOf(MessageInfo.class);
        MessageInfo messageInfo2 = (MessageInfo) instantiator.newInstance();
        System.out.println(messageInfo2.getClass());
    }


    class MessageInfo {

        //去除默认的无参构造函数
        public MessageInfo(long messageId) {
            this.messageId = messageId;
        }

        private long messageId;

        public long getMessageId() {
            return messageId;
        }

        public void setMessageId(long messageId) {
            this.messageId = messageId;
        }
    }

}
