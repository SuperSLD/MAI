package Classes;

import java.util.ArrayList;

/**
 * Функции для проверки входящих данных.
 */
public class SqlSecurity {

    private static final String WHITE_LIST = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.: ";

    /**
     * Проверка символов по белому списку.
     * @param string
     * @return true если строка корректна.
     *
     * @author Solyanoy Leonid(solyanoy.leonid@gmail.com)
     */
    public static boolean checkInjection(String string) {
        for (char c : string.toCharArray()) {
            if (WHITE_LIST.indexOf(c) < 0) {
                return false;
            }
        }
        return true;
    }
}
