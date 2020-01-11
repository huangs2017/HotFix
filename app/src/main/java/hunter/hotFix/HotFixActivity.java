package hunter.hotFix;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class HotFixActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hot_fix_activity);
//        System.out.println("========> " + getClassLoader());
    }

    public void test(View view) {
//        ClassLoader loader = getClassLoader();
//        while (loader!=null) {
//            System.out.println(loader.toString());
//            loader = loader.getParent();
//        }
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void fix(View view) {
    }
}
