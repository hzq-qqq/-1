package com.IO.BIO.线程池处理.servlet;


import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) throws Exception {
        InetAddress serverIp = InetAddress.getByName("localhost");
        int port = 9999;
        Socket socket = new Socket(serverIp, port);

        OutputStream os = socket.getOutputStream();
//        按照行  包装一个打印流
        PrintStream pos = new PrintStream(os);
        byte[] bytes = new byte[1024];
        int len = 0;

        Scanner sin = new Scanner(System.in);
        while (true){
            String msg = sin.nextLine();
            pos.println(msg);
            pos.flush();
        }


    }
}
