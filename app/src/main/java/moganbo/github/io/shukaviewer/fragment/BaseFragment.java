package moganbo.github.io.shukaviewer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

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
}
