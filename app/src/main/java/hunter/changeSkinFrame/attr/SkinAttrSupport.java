package hunter.changeSkinFrame.attr;

import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import hunter.hotFix.R;

public class SkinAttrSupport {

    // 递归遍历所有的子View，根据tag命名，记录需要换肤的View
    public static void addSkinViews(View view, List<SkinView> skinViews) {
        SkinView skinView = getSkinView(view);  ///////////
        if (skinView != null) skinViews.add(skinView);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0, n = viewGroup.getChildCount(); i < n; i++) {
                View child = viewGroup.getChildAt(i);
                addSkinViews(child, skinViews);
            }
        }
    }

    public static SkinView getSkinView(View view) {
        Object tag = view.getTag(R.id.skin_tag_id);
        if (tag == null) {
            tag = view.getTag();
        }
        if (tag != null) {
            String tagStr = (String) tag;
            List<SkinAttr> skinAttrs = parseTag(tagStr); ///////////
            if (!skinAttrs.isEmpty()) {
                changeViewTag(view);
                return new SkinView(view, skinAttrs);
            }
        }
        return null;
    }

    private static void changeViewTag(View view) {
        Object tag = view.getTag(R.id.skin_tag_id);
        if (tag == null) {
            tag = view.getTag();
            view.setTag(R.id.skin_tag_id, tag);
            view.setTag(null);
        }
    }

    // skin:left_menu_icon:src|skin:color_red:textColor
    private static List<SkinAttr> parseTag(String tagStr) {
        List<SkinAttr> skinAttrs = new ArrayList<>();
        String[] items = tagStr.split("[|]");
        for (String item : items) {
            if (item.startsWith("skin:")) {
                String[] resItems = item.split(":");
                if (resItems.length == 3) {
                    String resType = resItems[1];
                    String resName = resItems[2];
                    SkinAttrType attrType = getSupportAttrType(resType);
                    if (attrType != null) {
                        SkinAttr attr = new SkinAttr(attrType, resName); ///////////
                        skinAttrs.add(attr);
                    }
                }
            }
        }
        return skinAttrs;
    }

    private static SkinAttrType getSupportAttrType(String attrName) {
        for (SkinAttrType attrType : SkinAttrType.values()) {
            if (attrType.getAttrType().equals(attrName))
                return attrType;
        }
        return null;
    }

}