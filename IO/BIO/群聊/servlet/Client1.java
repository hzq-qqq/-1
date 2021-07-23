package com.IO.BIO.群聊.servlet;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) throws Exception {
        InetAddress serverIp = InetAddress.getByName("localhost");
        int port = 9999;
        Socket socket = new Socket(serverIp, port);

        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
//        按照行  包装一个打印流
        PrintStream pos = new PrintStream(os);
        String message = null;

        Scanner sin = new Scanner(System.in);
        while (true){
            String msg = sin.nextLine();
            pos.println(msg);
            pos.flush();
            System.out.println(buf.readLine());
        }




    }
}
