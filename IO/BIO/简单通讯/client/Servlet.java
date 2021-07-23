package com.IO.BIO.简单通讯.client;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servlet {
    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(9999);

        Socket accept = socket.accept();

        InputStream is = accept.getInputStream();
//        包装一个缓存字符输入流
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String msg ;
        while ((msg = br.readLine()) != null){
            System.out.println(socket.getInetAddress());
            System.out.println("服务的接受到的是： " + msg);
        }

    }
}

