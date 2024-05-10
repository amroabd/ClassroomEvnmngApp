package com.is.classroomevnmngapp.utils.crypto;



import androidx.annotation.NonNull;

import com.is.classroomevnmngapp.utils.Log1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class FileHelper {
    private static final String TAG = "FileUtil";
    @NonNull
    public static byte[] readFile(String filePath) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            byte[] data = new byte[(int) new File(filePath).length()];
            int totalBytesRead = 0;
            int bytesRead;
            while ((bytesRead = inputStream.read(data)) != -1) {
                totalBytesRead += bytesRead;
            }
            Log1.d(TAG, String.format(Locale.ENGLISH,"data.size :%d,totalBytesRead :%d", data.length, totalBytesRead));
            return Arrays.copyOfRange(data, 0, totalBytesRead);
        } catch (IOException e) {
            throw new Exception("Error reading file: " + filePath, e);
        }
    }

    public static void writeFile(String filePath, byte[] data) throws Exception {
    	   File f=new File(filePath);
        try (FileOutputStream outputStream = new FileOutputStream(f)) {
            outputStream.write(data);
        	outputStream.close();
        } catch (IOException e) {
            throw new Exception("Error writing file: " + filePath, e);
        }
    }
}
