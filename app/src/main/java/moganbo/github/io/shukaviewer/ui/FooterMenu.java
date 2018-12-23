package moganbo.github.io.shukaviewer.ui;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

import moganbo.github.io.shukaviewer.R;
import moganbo.github.io.shukaviewer.activity.MainActivity;
import moganbo.github.io.shukaviewer.constants.TransitionPage;
import moganbo.github.io.shukaviewer.fragment.MainWebViewFragment;

@EViewGroup(R.layout.part_footer_menu)
public class FooterMenu extends FrameLayout {

    public FooterMenu(@NonNull Context context) {
        super(context, null);
    }

    public FooterMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, R.attr.footer_menu);
    }

    public FooterMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @ViewById(R.id.part_footer_menu_image_button_1)
    protected ImageButton imageButton1;
    @ViewById(R.id.part_footer_menu_image_button_2)
    protected ImageButton imageButton2;
    @ViewById(R.id.part_footer_menu_image_button_3)
    protected ImageButton imageButton3;
    @ViewById(R.id.part_footer_menu_image_button_4)
    protected ImageButton imageButton4;
    @ViewById(R.id.part_footer_menu_image_button_5)
    protected ImageButton imageButton5;

    private Map<ImageButton, TransitionPage> buttonTransitionPageMap = new HashMap<>();

    @AfterViews
    protected void afterViews() {
        this.setVisibility(VISIBLE);
        buttonTransitionPageMap = new HashMap<>();
        buttonTransitionPageMap.put(imageButton1, TransitionPage.MUSEUM);
        buttonTransitionPageMap.put(imageButton2, TransitionPage.BOOKSTAND);
        buttonTransitionPageMap.put(imageButton3, TransitionPage.THEATERS);
        buttonTransitionPageMap.put(imageButton4, TransitionPage.SCHEDULES);
        buttonTransitionPageMap.put(imageButton5, TransitionPage.NEWS);
        updateButton();
    }

    public void updateForShukaLand(){
        this.setVisibility(VISIBLE);
        buttonTransitionPageMap = new HashMap<>();
        buttonTransitionPageMap.put(imageButton1, TransitionPage.MUSEUM);
        buttonTransitionPageMap.put(imageButton2, TransitionPage.BOOKSTAND);
        buttonTransitionPageMap.put(imageButton3, TransitionPage.THEATERS);
        buttonTransitionPageMap.put(imageButton4, TransitionPage.SCHEDULES);
        buttonTransitionPageMap.put(imageButton5, TransitionPage.NEWS);
        updateButton();
    }

    public void updateForShukaBlog(){
        // TODO: しゅか通信用のグローバルメニュー
        this.setVisibility(GONE);
    }

    public void updateForAnd(){
        // TODO: 「AND」のグローバルメニュー
        this.setVisibility(GONE);
    }

    private void updateButton() {
        for (Map.Entry<ImageButton, TransitionPage> entry : buttonTransitionPageMap.entrySet()) {
            entry.getKey().setImageResource(entry.getValue().getResourceId());
            entry.getKey().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v instanceof ImageButton) {
                        onClickImageButton((ImageButton) v);
                    }
                }
            });
        }
    }


    private void onClickImageButton(ImageButton imageButton) {
        if (buttonTransitionPageMap.containsKey(imageButton)) {
            TransitionPage page = buttonTransitionPageMap.get(imageButton);
            if (page != null) {
                String url = page.getPageUrl().getUrl();
                Activity activity = scanForActivity(getContext());
                if (activity != null && activity instanceof MainActivity &&
                        ((MainActivity) activity).getNowFragment() != null &&
                        ((MainActivity) activity).getNowFragment() instanceof MainWebViewFragment) {
                    ((MainWebViewFragment) ((MainActivity) activity).getNowFragment()).loadPage(url);
                }
            }
        }
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

