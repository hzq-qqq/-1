package com.IO.BIO.案例练习.servlet;


import com.IO.BIO.案例练习.constants.Constants;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Servlet {
//    Socket : 表示一个用户
//    String ：表示用户的名称
    private static Map<Socket,String>onLineSockets = new HashMap<>();
    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(Constants.PORT);
        while (true) {
//            阻塞式等待客户连接
            Socket accept = socket.accept();
//            每连接上一个用于就单独开启一个线程来处理
            new ServerReader(accept).start();
        }
    }


    /**
     * 注意这里是处理某一个客户端的线程
     */
   static class ServerReader extends Thread{
        private Socket socket;
        public ServerReader(Socket socket){
            this.socket = socket;
        }

       @Override
       public synchronized void run() {
           DataInputStream dis = null;

           try {
//               创建一个输入流，用于服务器接受客户端发来的信息 ，这里发现DataInputStream，
//               在使用Data的时候 ，类似与队列，村的时候是以什么顺序村的内容，那么在接受的时候，就是按照什么顺来接受数据
//               不然会报错误
               dis = new DataInputStream(socket.getInputStream());
               while (true) {
//                   flag 表示消息的类型 1，登陆信息 2，群聊，@ 发 3，私发
//                   在客户端发送信息的时候，会发送一个消息的类型信息flag
                   int flag = dis.readInt();
                   if (flag == 1) {
//                       这里是直接在服务器的后台窗口输出用户上线的信息
                       String name = dis.readUTF();
                       System.out.println(name + "------>" + socket.getInetAddress());
                       onLineSockets.put(socket, name);
                   }
//                   用户上线之后，则将再次刷新每个客户端右侧的人数列表
                   writeMsg(flag,dis); // 方法抛出异常，那么这里就会捕获异常，然后 移除socket
               }
           } catch (Exception e) {
               System.out.println("——有人下线了——");
               Servlet.onLineSockets.remove(socket);
               try {
                   writeMsg(1,dis); // 移除用户之后就再次通知客户端来更新 在线用户
               } catch (Exception exception) {
                   exception.printStackTrace();
               }
           }
       }

        /**
         *
         * @param flag  消息类型
         * @param dis   提取当前客户端发送的消息
         * @throws Exception
         */
       private void writeMsg(int flag, DataInputStream dis) throws Exception{
           String msg = null;
            if (flag == 1){
               StringBuilder sb = new StringBuilder();
               Collection<String> values = Servlet.onLineSockets.values();
//               获取所有在线的客户的名字，并使用协议分隔符来分割
               if (values.size() > 0){
                   for (String value : values) {
                       sb.append(value).append(Constants.SPILIT);
                   }
               }
               msg = sb.substring(0, sb.lastIndexOf(Constants.SPILIT));
//               获取内容后，下面进行内容的发送
               sendMsyAll(flag,msg);
           }else if (flag == 2 || flag == 3) {
                msg = dis.readUTF(); // 会等待客户端信息，如果客户端挂掉，就会抛出异常
//                这里可以直接确定当前的socket 就是发送数据的客户端的原因是
//                当代码可以走到这里时,说明在客户端已经发送的信息,然后在服务端相应的 socket已经接受到信息,所以对应的服务端这边的
//                socket 对应的就是对应的发送信息的客户端
                String name = onLineSockets.get(socket);
                StringBuilder sb = new StringBuilder();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE");
                if (flag == 2) {
                    sb.append(name).append(" ").append(sdf.format(System.currentTimeMillis()*2)).append("\r\n").
                            append("     ").append(msg).append("\r\n");
                    sendMsyAll(flag, sb.toString());
                } else {
                    sb.append(name).append(sdf.format(System.currentTimeMillis())).append("对您私发\r\n").
                            append("      ").append(msg);
//                    客户端通过数据输流将目标对象写过来，前提这里用户名是唯一的
                    String destName = dis.readUTF();
                    sendMsgOne(destName, sb.toString());
                }
            }
       }

       private void sendMsgOne(String destName, String msg) throws Exception {
           Set<Socket> sockets = onLineSockets.keySet();
           for (Socket socket1 : sockets) {
//               定位对象的socket管道
               if (onLineSockets.get(socket1).equals(destName)){
                   DataOutputStream dis = null;
                       dis = new DataOutputStream(socket1.getOutputStream());
//                       这里消息的类型是2，虽然这里是私发送数据，前面通过if 已经找到了相应的socket 管道
//                       ，在客户端就直接以群聊的方式展示就可以了
                       dis.writeInt(2);
                       dis.writeUTF(msg);
                       dis.flush();
               }
           }
       }
//       遍历所有的服务器的socket 并发送信息给客户端socket, 客户端根据不同的类型来以不同的方式来展示
       private void sendMsyAll(int flag, String msg) throws Exception{
//           拿到与每个客户端对应的socket的输出咯流，然后将信息写回给每个客户端
           Set<Socket> sockets = onLineSockets.keySet();
           for (Socket socket1 : sockets) {
                 DataOutputStream dos = new DataOutputStream(socket1.getOutputStream());
                   dos.writeInt(flag);  // 登陆消息，离线消息， @ 消息
                   dos.writeUTF(msg);
                   dos.flush();
           }
       }
   }
}

