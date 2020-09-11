package Sockets;

import Classes.DBConnector;
import Sockets.MessageReply.MessageReply;
import Sockets.MessageReply.ReplyType;
import org.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Вебсокет для обработки потока данных от
 * клментов и занесения изменений в базу данных.
 *
 * @author Solyanoy Leonid(solyanoy.leonid@gmail.com)
 */
@ServerEndpoint("/connection")
public class ConnectionWebsocket {

    private static HashMap<String, MessageReply> replies = new HashMap<>();
    private static boolean isInit = false;

    /**
     * Инициализация сокета.
     */
    private static void init() {
        if (!isInit) {
            System.out.println("INITIALISATION WEBSOCKET STATIC OBJECT");
            isInit = true;
            File file = new File(
                    ConnectionWebsocket.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                    + "/Sockets/MessageReply"
            );
            for(String fileName : file.list()) {
                System.out.println("Check class -> " + fileName);
                try {
                    Class<?> classData = Class.forName("Sockets.MessageReply." + fileName.replaceAll("\\.class", ""));
                    ReplyType replyType = classData.getAnnotation(ReplyType.class);
                    if (replyType != null) {
                        MessageReply reply = (MessageReply) classData.newInstance();
                        replies.put(replyType.messageType(), reply);
                        System.out.println("    .annotation load [" + replyType.messageType() + "]");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("MessageReply open [" + file.isDirectory() + "]");
        }
    }

    /**
     * Поиск классов MessageReply.
     */
    private static void findClassList(File file) {

    }

    @OnOpen
    public void onOpen(Session session) {
        init();
        System.out.println("onOpen::" + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("onClose::" +  session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            JSONObject messageObject = new JSONObject(message);
            MessageReply messageReply = replies.get(messageObject.getString("type"));

            if (messageReply != null) {
                messageReply.reply(this, message, session);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("onError::" + t.getMessage());
    }
}