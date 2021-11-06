package pro.midev.juttermap.gl;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Получение файла с шейдерами для
 * загрузки их в компилятор.
 */
public class JTFileUtils {

    /**
     * Чтение файла из ресурсов.
     * @param context контекст приложения.
     * @param resourceId идентификатор ресурса.
     *
     * @return строка с содержимым файла.
     */
    public static String readTextFromRaw(Context context, int resourceId) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = null;
            try {
                InputStream inputStream = context.getResources().openRawResource(resourceId);
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\r\n");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
        } catch (IOException | Resources.NotFoundException ioex) {
            ioex.printStackTrace();
        }
        return stringBuilder.toString();
    }
}