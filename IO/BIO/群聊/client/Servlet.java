package com.IO.BIO.群聊.client;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Servlet {
    static ArrayList<Socket> sockets = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(9999);
        while (true){
            Socket accept = socket.accept();
            sockets.add(accept);
            System .out.println(accept.getLocalSocketAddress() + "上线了");
            new Servers(accept).start();
        }

    }
    static class Servers extends Thread {
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
//            获取一个输出流将信心转发给每个用户
            String msg ;
            while (true){
                try {
                    if ((msg = br.readLine()) != null){
                        System.out.println("服务的接受到的是： " + msg);
//                        将信息转发给每个用户
                        trant(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void trant(String msg) throws IOException {
            System.out.println("trant 方法执行了");
            for (Socket socket1 : sockets) {
                PrintStream printStream = new PrintStream(socket1.getOutputStream());
                printStream.println(msg);
                printStream.flush();
            }
        }

    }

}

