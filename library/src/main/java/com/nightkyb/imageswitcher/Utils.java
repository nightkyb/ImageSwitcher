package com.nightkyb.imageswitcher;

import android.content.res.Resources;

public class Utils {
    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    /**
     * dp转换px
     *
     * @param dp dp值
     * @return 转换后的px值
     */
    public static int dp2px(int dp) {
        return Math.round(dp * DENSITY);
    }
}
