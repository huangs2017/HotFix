package hunter.changeSkinFrame.attr;

import android.view.View;

public class SkinAttr {

    public SkinAttrType attrType;
    public String resName;

    public SkinAttr(SkinAttrType attrType, String resName) {
        this.attrType = attrType;
        this.resName = resName;
    }

    public void apply(View view) {
        attrType.apply(view, resName);
    }

}
