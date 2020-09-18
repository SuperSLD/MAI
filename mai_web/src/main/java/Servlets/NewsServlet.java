package Servlets;

import Classes.DBConnector;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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