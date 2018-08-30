package moganbo.github.io.shukaviewer.utils;

import android.view.View;
import android.view.ViewGroup;

public class AppUtil {

    public static ViewGroup getActionBar(View view) {
        try {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;

                if (viewGroup instanceof android.support.v7.widget.Toolbar) {
                    return viewGroup;
                }

                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    ViewGroup actionBar = getActionBar(viewGroup.getChildAt(i));

                    if (actionBar != null) {
                        return actionBar;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
