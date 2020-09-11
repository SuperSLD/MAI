package Sockets.MessageReply;

import Sockets.ConnectionWebsocket;

import javax.websocket.Session;

/**
 * Интерфейс для определения ответа на сообщение.
 */
public interface MessageReply {
    public void reply(ConnectionWebsocket cw, String message, Session session) throws Exception;
}
