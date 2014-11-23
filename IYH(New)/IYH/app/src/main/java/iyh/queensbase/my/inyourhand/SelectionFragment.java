package iyh.queensbase.my.inyourhand;

/**
 * Created by QueensBase on 13-Oct-14.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SelectionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.activity_menu,container, false);
        return view;
    }
}

