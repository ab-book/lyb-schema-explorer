package com.rmi;

import java.rmi.Naming;

/**
 * Created by kongxuan on 2016/12/3.
 */
public class ClientMain {
    public static void main(String[] args)throws Exception {
        //服务引入
        HelloService helloService = (HelloService) Naming.lookup("rmi://localhost:8801/helloService");
        //调用远程方法
        System.out.println("RMI服务器返回的结果是 "+helloService.sayHello("kongxuan"));
    }
}
