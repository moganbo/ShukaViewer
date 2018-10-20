package moganbo.github.io.shukaviewer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

public class RuntimePermissionUtil {
    private RuntimePermissionUtil(){

    }

    public  static  boolean hasSelfPermission(@NonNull Context context, @NonNull String... permissons){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return  true;
        }
        for (String permissions : permissons){
            if (context.checkSelfPermission(permissions) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    public static void requestPermission(@NonNull Activity activity, @NonNull String permission, int requestCode){
        requestPermissions(activity, new String[]{permission}, requestCode);
    }

    public static void requestPermissions(@NonNull Activity activity, @NonNull String[] permissions, int requestCode){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            activity.requestPermissions(permissions, requestCode);
        }
    }

    public static boolean checkGrantResults(@NonNull int... grantResults){
        if (grantResults.length == 0){
            throw new IllegalArgumentException("grantResult is empty");
        }
        for (int result : grantResults){
            if (result != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    public  static boolean shouldShowRequestPermissonRationale(@NonNull Activity activity, @NonNull String permisson){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return activity.shouldShowRequestPermissionRationale(permisson);
        }
        return true;
    }
}
