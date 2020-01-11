package hunter.hotFix;

import android.content.Context;
import android.widget.Toast;

public class Calculator {
    public void calculate(Context ctx) {
        int a = 666;
        int b = 0;
        Toast.makeText(ctx, "calculate--> " + a / b, Toast.LENGTH_LONG).show();
    }
}
