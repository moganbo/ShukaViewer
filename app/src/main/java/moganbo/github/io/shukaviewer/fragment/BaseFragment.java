package moganbo.github.io.shukaviewer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import moganbo.github.io.shukaviewer.R;
import moganbo.github.io.shukaviewer.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {


    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onBackPressed(){
        // 継承先で実装
    }

    public MainActivity getMainActivity() {
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            return (MainActivity) getActivity();
        }
        return null;
    }

    // フラグメント置き換え処理 main_container
    protected void replaceFragmentForMain(Fragment fragment, String tag, boolean isAddToBackStack) {
        replaceFragment(R.id.activity_main_container, fragment, tag, isAddToBackStack);
    }

    // フラグメント置き換え処理
    protected void replaceFragment(int viewId, Fragment fragment, String tag, boolean isAddToBackStack) {
        if (getContext() == null|| !(getContext() instanceof FragmentActivity)){
            return;
        }
        FragmentTransaction transaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(viewId, fragment, tag);
        if (isAddToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    // フラグメント置き換え処理
    protected void replaceFragmentForClose(Fragment fragment, String tag, boolean isAddToBackStack) {
        replaceFragmentForClose(R.id.activity_main_container, fragment, tag, isAddToBackStack);
    }

    // フラグメント置き換え処理
    protected void replaceFragmentForClose(int viewId, Fragment fragment, String tag, boolean isAddToBackStack) {
        if (getContext() == null|| !(getContext() instanceof FragmentActivity)){
            return;
        }
        FragmentTransaction transaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.replace(viewId, fragment, tag);
        if (isAddToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }
}
