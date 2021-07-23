package com.IO.BIO.线程池处理.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Servlet {
    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(9999);
        while (true){
            Socket accept = socket.accept();
            System.out.println(accept.getLocalSocketAddress() + "上线了");
            new Servers(accept).start();
        }

    }
    static class Servers extends Thread{
        private final Socket socket;

        public Servers(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            InputStream is = null;
            try {
                is = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
//        包装一个缓存字符输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String msg ;
            while (true){
                try {
                    if ((msg = br.readLine()) != null){
                        System.out.println(socket.getInetAddress());
                        System.out.println("服务的接受到的是： " + msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

