package Servlets;

import Classes.DBConnector;
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
    }
}
