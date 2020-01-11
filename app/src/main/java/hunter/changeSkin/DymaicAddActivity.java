package hunter.changeSkin;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import hunter.changeSkinFrame.SkinManager;
import hunter.hotFix.R;

public class DymaicAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dymaic_add_activity);
        SkinManager.getInstance().register(this);
    }

    public void addNewView(View view) {
        TextView tv = new TextView(this);
        tv.setTextColor(getResources().getColorStateList(R.color.item_text_color));
        tv.setText("dymaic add!");
        tv.setTag("skin:textColor:item_text_color");

        ((ViewGroup) findViewById(R.id.id_container)).addView(tv);
        SkinManager.getInstance().injectSkin(tv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
}
