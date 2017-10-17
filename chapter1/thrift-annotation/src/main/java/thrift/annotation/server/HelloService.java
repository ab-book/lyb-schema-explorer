package thrift.annotation.server;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;

/**
 * @author liyebing created on 16/12/17.
 * @version $Id$
 */
@ThriftService("HelloService")
public interface HelloService {

    @ThriftMethod
    public String sayHello(@ThriftField(name = "user") User user);


}
