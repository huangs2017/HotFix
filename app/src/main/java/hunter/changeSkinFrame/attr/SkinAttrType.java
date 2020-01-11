package hunter.changeSkinFrame.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import hunter.changeSkinFrame.SkinManager;

public enum SkinAttrType {

    BACKGROUND("background") {
        @Override
        public void apply(View view, String resName) {
            Drawable drawable = SkinManager.getInstance().getResourceManager().getDrawable(resName);
            if (drawable != null) {
                view.setBackgroundDrawable(drawable);
            } else {
                int color = SkinManager.getInstance().getResourceManager().getColor(resName);
                view.setBackgroundColor(color);
            }
        }
    }, COLOR("textColor") {
        @Override
        public void apply(View view, String resName) {
            int color = SkinManager.getInstance().getResourceManager().getColor(resName);
            ((TextView) view).setTextColor(color);
        }
    }, SRC("src") {
        @Override
        public void apply(View view, String resName) {
            if (view instanceof ImageView) {
                Drawable drawable = SkinManager.getInstance().getResourceManager().getDrawable(resName);
                if (drawable == null) return;
                ((ImageView) view).setImageDrawable(drawable);
            }
        }
    }, DIVIDER("divider") {
        @Override
        public void apply(View view, String resName) {
            if (view instanceof ListView) {
                Drawable divider = SkinManager.getInstance().getResourceManager().getDrawable(resName);
                if (divider == null) return;
                ((ListView) view).setDivider(divider);
            }
        }
    };

    String attrType;

    SkinAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getAttrType() {
        return attrType;
    }

    public abstract void apply(View view, String resName);

}
