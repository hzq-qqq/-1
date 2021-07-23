package com.IO.BIO.案例练习.client;
import com.IO.BIO.案例练习.constants.Constants;

import java.io.DataInputStream;
import java.net.Socket;
class ClientReader extends Thread {
	private Socket socket;
//	这个这个表示当前线程维护一个窗口
	private ClientChat clientChat ;

	public ClientReader(ClientChat clientChat, Socket socket) {
		this.clientChat = clientChat;
		this.socket = socket;
	}

//	这个线程用于处理服务器端发来的信息，并根据不同的类型（flag）展示到不同的位置上
	@Override
	public void run() {
		try {
//			输入流用于接受客户端发来的消息
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			/** 循环一直等待客户端的消息 */
			while(true){
				/** 读取当前的消息类型 ：登录,群发,私聊 , @消息 */
				int flag = dis.readInt();
				if(flag == 1){
					// 在线人数消息回来了
					String nameDatas = dis.readUTF();
					// 展示到在线人数的界面
					String[] names = nameDatas.split(Constants.SPILIT);

					clientChat.onLineUsers.setListData(names);
				}else if(flag == 2){
					//群发,私聊 , @消息 都是直接显示的。这里不需要单独设置flag = 3 的情况是因为，在服务器端已经确定了私发的socket
					String msg = dis.readUTF() ;
					clientChat.smsContent.append(msg);
					// 让消息界面滾動到底端
					clientChat.smsContent.setCaretPosition(clientChat.smsContent.getText().length());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
