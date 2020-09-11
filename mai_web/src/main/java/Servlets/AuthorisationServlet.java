package Servlets;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
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
        //resp.setContentType("text/json; charset=windows-1251");

        JSONObject jsonObject = new JSONObject();
        PrintWriter writer = resp.getWriter();

        writer.print(jsonObject.toString());
    }
}
