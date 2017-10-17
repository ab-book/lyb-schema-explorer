package com.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by kongxuan on 2016/12/3.
 */
public interface HelloService extends Remote{
  String sayHello(String someOne) throws RemoteException;
}
