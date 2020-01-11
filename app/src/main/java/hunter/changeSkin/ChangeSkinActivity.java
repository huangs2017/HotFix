package hunter.changeSkin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import hunter.changeSkinFrame.SkinManager;
import hunter.hotFix.R;

public class ChangeSkinActivity extends AppCompatActivity {

    private ListView lv;
    private List<String> mDatas = new ArrayList<>(Arrays.asList("Activity", "Service", "Activity", "Service"));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SkinManager.getInstance().register(this);   /////
        setContentView(R.layout.change_skin_activity);
        lv = findViewById(R.id.id_listview);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.id_left_menu_container);
        if (fragment == null) {
            fm.beginTransaction().add(R.id.id_left_menu_container, new LeftFragment()).commit();
        }
        initEvents();
    }

    private void initEvents() {
        ArrayAdapter mAdapter = new ArrayAdapter<String>(this, -1, mDatas) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(ChangeSkinActivity.this).inflate(R.layout.lv_adapter, parent, false);
                }
//                SkinManager.getInstance().injectSkin(convertView);   /////可以去掉
                TextView tv = convertView.findViewById(R.id.id_tv_title);
                tv.setText(getItem(position));
                return convertView;
            }
        };
        lv.setAdapter(mAdapter);
    }

    public void txtClick(View view) {
        Intent intent = new Intent(this, DymaicAddActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);   /////
    }

}

/*

插件式换肤
应用内换肤

1、如何去获取插件包中的资源?
2、如何去捕获需要换肤的控件?
3、如何为指定控件的指定属性去替换成资源包中的指定的资源?

tag属性分为3部分组成：
1：skin
2：支持的属性，目前支持src、background、textColor。
3：资源的名称，需要与当前app内使用的资源名称一致。

对于一个View有多个属性需要换肤的，使用|进行分隔。
例如：android:tag="skin:item_text_color:textColor|skin:icon:src"

*/


// aapt   Android Asset Packaging Tool
// arsc   Android Package Resource
// resources.arsc文件就是一个资源索引表