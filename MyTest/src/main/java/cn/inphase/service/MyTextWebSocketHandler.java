package cn.inphase.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

import cn.inphase.domain.SocketMsg;

public class MyTextWebSocketHandler extends TextWebSocketHandler {

	public static final Map<String, WebSocketSession> userSocketSessionMap = new HashMap<String, WebSocketSession>();

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// TODO Auto-generated method stub
		// DESKTOP-311ABIG/192.168.2.64:11711
		String fromIp = session.getRemoteAddress().toString().split("\\/")[1].split("\\:")[0];
		super.handleTextMessage(session, message);
		SocketMsg socketMsg = new Gson().fromJson(message.getPayload(), SocketMsg.class);
		String textBody = socketMsg.getTextBody();
		System.out.println(message.getPayload());
		String toIp = socketMsg.getToIp();
		if (userSocketSessionMap.get(toIp) == null) {
			socketMsg = new SocketMsg();
			socketMsg.setTextBody("对方不在线");
			session.sendMessage(new TextMessage(new Gson().toJson(socketMsg)));
		} else {
			WebSocketSession session2 = userSocketSessionMap.get(toIp);
			socketMsg = new SocketMsg();
			socketMsg.setTextBody(textBody);
			socketMsg.setFromIp(fromIp);
			session2.sendMessage(new TextMessage(new Gson().toJson(socketMsg)));
		}

	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		String fromIp = session.getRemoteAddress().toString().split("\\/")[1].split("\\:")[0];
		System.out.println(fromIp);
		userSocketSessionMap.put(fromIp, session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		String fromIp = session.getRemoteAddress().toString().split("\\/")[1].split("\\:")[0];
		userSocketSessionMap.remove(fromIp);
	}

}
