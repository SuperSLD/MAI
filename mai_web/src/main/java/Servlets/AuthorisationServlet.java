package Servlets;

import Classes.DBConnector;
import Classes.SqlSecurity;
import Sockets.ConnectionWebsocket;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;

/**
 * Авторизация пользователя в системме.
 * Проверка его данных.
 *
 * @author Solyanoy Leonid(solyanoy.leonid@gmail.com)
 */
public class AuthorisationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        if (session.getAttribute("key") != null) {
            setAttribute(session, req);
            req.getRequestDispatcher("main.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("authorisation.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("key -> " + req.getParameter("key"));

        try {
            if (!SqlSecurity.checkInjection(req.getParameter("key"))) {
                return;
            }
            ResultSet rs = DBConnector.executeQuery("SELECT id, level_user FROM auth_keys WHERE key_user='" +
                    req.getParameter("key") + "'");
            if (rs.next()) {
                HttpSession session = req.getSession();
                session.setAttribute("key", req.getParameter("key"));
                session.setAttribute("level", rs.getString("level_user"));

                setAttribute(session, req);
                req.getRequestDispatcher("main.jsp").forward(req, resp);
            } else {
                req.setAttribute("error_text", "Ключ не найден");
                req.getRequestDispatcher("authorisation.jsp").forward(req, resp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("error_text", "Ошибка на сервере");
            req.getRequestDispatcher("authorisation.jsp").forward(req, resp);
        }

    }

    private void setAttribute(HttpSession session, HttpServletRequest req) {
        req.setAttribute("user_level", session.getAttribute("level"));
        req.setAttribute("title","Новости");

        String newsHtml = "";
        try {
            ResultSet rs = DBConnector.executeQuery("SELECT title, news_text, date_string FROM news ORDER BY date_string DESC LIMIT 100");
            while (rs.next()) {
                newsHtml += insertDataInHtml(
                        rs.getString("title"),
                        rs.getString("news_text").replaceAll("!@nl", "<br>"),
                        rs.getString("date_string"));
            }
        } catch (Exception ex) {

        }
        req.setAttribute("news", newsHtml);
    }

    public static String insertDataInHtml(String title, String text, String date) {
        return "<div class=\"note\">" +
                "<span class=\"news_title\"><b>"+title+"</b></span><br><br>" +
                "<span class=\"news_content\">" + text +
                "</span><br>" +
                "<div align='right'><span class=\"news_time\">" + date +
                "</span></div>" +
                "</div>";
    }
}
