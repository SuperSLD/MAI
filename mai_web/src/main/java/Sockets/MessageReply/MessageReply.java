package main.java.Sockets.MessageReply;

import Sockets.AdminPanelWebsocket;

import javax.websocket.Session;

/**
 * Интерфейс для определения ответа на сообщение.
 */
public interface MessageReply {
    public void reply(AdminPanelWebsocket cw, String message, Session session) throws Exception;
}
