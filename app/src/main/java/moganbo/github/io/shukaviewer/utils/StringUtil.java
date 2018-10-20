package moganbo.github.io.shukaviewer.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class StringUtil {

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static String getFileNameFromUri(@NonNull Context context, Uri uri) {
        // is null
        if (null == uri) {
            return null;
        }

        // get scheme
        String scheme = uri.getScheme();

        // get file name
        String fileName = null;
        switch (scheme) {
            case "content":
                String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME};
                Cursor cursor = context.getContentResolver()
                        .query(uri, projection, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        fileName = cursor.getString(
                                cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME));
                    }
                    cursor.close();
                }
                break;
            case "file":
                fileName = new File(uri.getPath()).getName();
                break;

            default:
                break;
        }
        return fileName;
    }

    public static String getFileNameFromUrl(String url) {
        if (url == null) {
            return "";
        }
        URL resource;
        try {
            resource = new URL(url);
            String host = resource.getHost();
            if (host.length() > 0 && url.endsWith(host)) {
                // handle ...example.com
                return "";
            }
        }
        catch(MalformedURLException e) {
            return "";
        }

        String urlString = resource.getFile();

        return urlString.substring(urlString.lastIndexOf('/') + 1).split("\\?")[0].split("#")[0];
    }

    public static String removeFileNameExtension(String fileName) {
        // is null
        if (null == fileName) {
            return null;
        }

        // get point index
        int point = fileName.lastIndexOf(".");
        if (-1 < point) {
            return fileName.substring(0, point);
        }
        return fileName;
    }

    public static String getFileNameExtension(String fileName) {
        if (fileName == null) return null;
        int point = fileName.lastIndexOf(".");
        if (point != -1) {
            return fileName.substring(point + 1);
        }
        return null;
    }
}
