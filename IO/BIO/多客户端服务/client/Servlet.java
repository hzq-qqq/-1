package com.IO.BIO.多客户端服务.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Servlet {
    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(9999);

        HandlerSocketPool handlerSocketPool = new HandlerSocketPool(3, 1000);
        while (true){
            Socket accept = socket.accept();
            System .out.println(accept.getLocalSocketAddress() + "上线了");
            handlerSocketPool.execute(new Servers(accept));
        }

    }
    static class Servers implements Runnable {
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

     static class HandlerSocketPool{
        private ExecutorService  executor;

         /**
          *
          * @param maxPoolSize   最大线程数量
          * @param queueSize     任务队列数量
          */
        public HandlerSocketPool(int maxPoolSize, int queueSize){

            this.executor = new ThreadPoolExecutor(
                    3, // 8
                    maxPoolSize,
                    120L,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(queueSize) );
        }

        public void execute(Runnable task){
            this.executor.execute(task);
        }
    }
}

