package hunter.hotFix;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hunter.hotFixFrame.FixDexUtils;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hot_fix_activity);
        System.out.println("========> " + getClassLoader());
    }

    public void test(View view) {
        Calculator calculator = new Calculator();
        calculator.calculate(this);
    }

    public void fix(View view) {
        FixDexUtils.loadFixedDex(this);
    }
}
