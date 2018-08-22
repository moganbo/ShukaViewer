package moganbo.github.io.shukaviewer.ui;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ActionMenuView;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import moganbo.github.io.shukaviewer.R;
import moganbo.github.io.shukaviewer.activity.MainActivity;

@EViewGroup(R.layout.part_common_header)
public class CommonHeader extends FrameLayout {

    public CommonHeader(@NonNull Context context) {
        this(context, null);
    }

    public CommonHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.common_header);
    }

    public CommonHeader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @ViewById(R.id.part_common_header_text_view)
    protected TextView textView;

    @AfterViews
    protected void afterViews() {

    }

    @Click(R.id.part_common_header_image_button)
    protected void onClickImageButton() {
        Activity activity = scanForActivity(getContext());
        if (activity != null && activity instanceof MainActivity){
            ((MainActivity) activity).showDrawer();
        }
    }

    public void setText(String str){
        textView.setText(str);
    }





    private static Activity scanForActivity(Context context) {
        if (context == null) {
            return null;
        } else if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }
}
