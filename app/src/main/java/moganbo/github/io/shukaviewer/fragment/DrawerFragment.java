package moganbo.github.io.shukaviewer.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import moganbo.github.io.shukaviewer.R;
import moganbo.github.io.shukaviewer.activity.MainActivity;
import moganbo.github.io.shukaviewer.constants.PageUrl;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrawerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_drawer)
public class DrawerFragment extends Fragment {

    public static final String TAG = DrawerFragment.class.getSimpleName();


    public DrawerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DrawerFragment.
     */
    public static DrawerFragment newInstance() {
        DrawerFragment fragment = new DrawerFragment_();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @AfterViews
    protected void afterViews(){

    }

    @Click(R.id.fragment_drawer_land_text_view)
    protected void onClickLandTextView(){
        loadPage(PageUrl.SHUKA_LAND_TOP.getUrl());
        closeThisView();
    }

    @Click(R.id.fragment_drawer_blog_text_view)
    protected void onClickBlogTextView(){
        loadPage(PageUrl.SHUKA_BLOG.getUrl());
        closeThisView();
    }

    private void loadPage(String  url){
        if (getMainActivity() != null && getMainActivity().getNowFragment() instanceof  MainWebViewFragment){
            ((MainWebViewFragment) getMainActivity().getNowFragment()).loadPage(url);
        }
    }

    private void closeThisView(){
        if (getMainActivity() != null){
            getMainActivity().onBackPressed();
        }
    }

    private MainActivity getMainActivity(){
        Activity activity = getActivity();
        if (activity != null && activity instanceof MainActivity){
            return (MainActivity)activity;
        }

        return null;
    }
}
