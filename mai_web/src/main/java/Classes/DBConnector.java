package Classes;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Подключение к базе данных.
 *
 * @author Solyanoy Leonid(solyanoy.leonid@gmail.com)
 */
public class DBConnector {

    private static Connection con;

    /**
     * Если подключение закрыто то его необходимо открыть.
     */
    public static void init() {
        try {
            InputStream inputStream = DBConnector.class.getResourceAsStream("/config.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            Class.forName(properties.getProperty("db_class_name"));

            con = DriverManager.getConnection(properties.getProperty("connection"), properties);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Проверка состояния подключения.
     * @return true если открыто.
     */
    public static boolean isOpen() {
        try {
            return !con.isClosed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Отправка запроса извлекающего данные.
     * @param q строка запроса.
     * @return result set
     * @throws SQLException
     */
    public static ResultSet executeQuery(String q) throws SQLException {
        if (con == null || !isOpen()) init();
        ResultSet rs;
        Statement st = con.createStatement();
        rs = st.executeQuery(q);
        return rs;
    }

    /**
     * Отправка запроса обновляющего данные.
     * @param q строка запроса.
     * @return Количество измененных записей.
     */
    public static long executeUpdate(String q) throws SQLException {
        if (con == null || !isOpen()) init();
        long count = 0;
        Statement st = con.createStatement();
        count += st.executeUpdate(q);
        return count;
    }
}