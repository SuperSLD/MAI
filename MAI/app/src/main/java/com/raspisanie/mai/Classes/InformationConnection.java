package com.raspisanie.mai.Classes;

import android.content.SharedPreferences;

import com.neovisionaries.ws.client.ThreadType;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.raspisanie.mai.InformationConnection.InformationRename;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InformationConnection {
    private static final String SERVER_IP = "89.250.2.13:8080";
    //private static final String SERVER_IP = "192.168.1.55:8080";

    private static WebSocket ws;
    private static boolean open = false;
    private static SharedPreferences pref;

    /**
     * Открытие подключения.
     */
    public static void openConnection() {
        new Thread(() -> {
            try {
                while (true) {
                    if (!open) {
                        ws = new WebSocketFactory().setConnectionTimeout(5000)
                                .createSocket("ws://" + SERVER_IP + "/SupportingServer/info");
                        //.createSocket("ws://" + SERVER_IP + "/info");
                        Logger.getLogger("mailog").log(Level.INFO, "start connection / " + ws.isOpen());
                        ws.addListener(new WebSocketAdapter() {
                            @Override
                            public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
                                Logger.getLogger("mailog").log(Level.INFO, "ws open id: " + pref.getString("infoId", "0"));
                                open = true;
                                SimpleTree<String> tree = (SimpleTree<String>) Parametrs.getParam("tree");
                                String groupName = tree.getChildList().get((int) Parametrs.getParam("kurs"))
                                        .getChildList().get((int) Parametrs.getParam("fac"))
                                        .getChildList().get((int) Parametrs.getParam("group")).getValue();
                                String facName = tree.getChildList().get((int) Parametrs.getParam("kurs"))
                                        .getChildList().get((int) Parametrs.getParam("fac")).getValue();
                                String kursName = tree.getChildList().get((int) Parametrs.getParam("kurs"))
                                        .getValue();

                                if (pref.getString("infoId", "0").equals("0")) {
                                    Logger.getLogger("mailog").log(Level.INFO, "ws no id");
                                    ws.sendText("reg<!>" + groupName + "<!>" + facName + "<!>" + kursName);
                                } else {
                                    Logger.getLogger("mailog").log(Level.INFO, "ws id save");
                                    ws.sendText("connect<!>" + pref.getString("infoId", ""));
                                }
                            }

                            @Override
                            public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
                                Logger.getLogger("mailog").log(Level.INFO, "ws close");
                                open = false;
                            }

                            @Override
                            public void onTextMessage(WebSocket websocket, String text) throws Exception {
                                Logger.getLogger("mailog").log(Level.INFO, "ws message: " + text);
                                String[] mess = text.split("<!>");
                                switch (mess[0]) {
                                    case "id":
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("infoId", mess[1]);
                                        editor.apply();
                                        break;
                                }
                            }
                        });
                    }
                    ws.connectAsynchronously();
                    Thread.sleep(1000*60);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * Отправка информации о классе к которому обратилось приложение.
     * @param cl
     */
    public static void sendInfoActivity(Class cl, String parametrs) {
        if (ws != null && ws.isOpen()) {
            String className = "";
            try {
                Class<?> clas = Class.forName(cl.getName());
                InformationRename annotationRename = clas.getAnnotation(InformationRename.class);
                if (annotationRename.name().equals("-")) {
                    className = cl.getName();
                } else {
                    className = annotationRename.name();
                }
            } catch (Exception ex) {}
            ws.sendText("activity<!>"+className
            +"<!>"+parametrs);
        }
    }

    /**
     * Проверка состояния подключекия.
     * @return
     */
    public static boolean isOpen() {
        return open;
    }

    /**
     * Установка настроек.
     * @param prefer
     */
    public static void setPref(SharedPreferences prefer) {
        pref = prefer;
    }
}
