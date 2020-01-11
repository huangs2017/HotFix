package hunter.changeSkin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import hunter.MyApplication;
import hunter.changeSkinFrame.SkinManager;
import hunter.hotFix.R;

public class LeftFragment extends Fragment implements View.OnClickListener {


    View mInnerChange01;
    View mChangeSkin;
    View mRestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.left_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInnerChange01 = view.findViewById(R.id.innerchange01);
        mChangeSkin = view.findViewById(R.id.changeskin);
        mRestore = view.findViewById(R.id.restore);
        mInnerChange01.setOnClickListener(this);
        mChangeSkin.setOnClickListener(this);
        mRestore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.innerchange01:
                SkinManager.getInstance().changeSkin("red");
                break;
            case R.id.changeskin:
                SkinManager.getInstance().changeSkin(MyApplication.skinPkgPath, MyApplication.skinPkgName);
                break;
            case R.id.restore:
                SkinManager.getInstance().removeAnySkin();
                break;
        }
    }

}