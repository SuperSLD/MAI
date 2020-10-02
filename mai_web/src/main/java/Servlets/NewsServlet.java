package Servlets;

import Classes.DBConnector;
import Classes.SqlSecurity;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        JSONObject object = new JSONObject();
        PrintWriter writer = resp.getWriter();
        try {
            if (!SqlSecurity.checkInjection(req.getParameter("last_date"))) {
                return;
            }
            ResultSet rs = DBConnector.executeQuery(
                    "SELECT id, title, news_text, date_string " +
                        "FROM news " +
                        "WHERE date_string > '" + req.getParameter("last_date") + "' " +
                        "ORDER BY date_string LIMIT 100");

            JSONArray array = new JSONArray();
            while (rs.next()) {
                JSONObject news = new JSONObject();

                news.put("id", rs.getString("id"));
                news.put("title", rs.getString("title"));
                news.put("text", rs.getString("news_text").replaceAll("!@nl", "\n"));
                news.put("date", rs.getString("date_string"));

                array.put(news);
            }
            object.put("err_c0de", "0");
            object.put("news_list", array);
        } catch (Exception ex) {
            ex.printStackTrace();
            object.put("err_code", "1");
            object.put("err_text", "Ошибка на сервере.");
        }
        writer.print(object.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        JSONObject object = new JSONObject();
        PrintWriter writer = resp.getWriter();
        try {
            HttpSession session = req.getSession();
            switch (req.getParameter("act")) {
                case "add":
                    if (session.getAttribute("level").equals("admin")) {
                        System.out.println("title: " + req.getParameter("title") + "\ntext: " + req.getParameter("text"));

                        String date = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime());
                        String timeCode = "X";

                        DBConnector.executeUpdate("INSERT news VALUE(0, '"+req.getParameter("title")+"', '"+req.getParameter("text")
                                +"', '"+timeCode+"', '"+date+"')");

                        object.put("err_text", "Запись успешно создана.");
                        object.put("err_code", "0");
                        object.put("html", AuthorisationServlet.insertDataInHtml(
                                req.getParameter("title"),
                                req.getParameter("text"),
                                date
                        ));
                    } else {
                        object.put("err_text", "Недостаточно прав для операции.");
                        object.put("err_code", "1");
                    }
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            object.put("err_text", "Срок вашей сассии истек или на сервере произошла ошибка.");
            object.put("err_code", "1");
        }
        writer.print(object.toString());
    }
}