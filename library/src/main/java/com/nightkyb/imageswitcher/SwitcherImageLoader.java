package com.nightkyb.imageswitcher;

import android.widget.ImageView;

import androidx.annotation.NonNull;

/**
 * 图片加载器
 *
 * @author nightkyb created at 2019/7/30 9:46
 */
public interface SwitcherImageLoader {
    void loadImage(int index, @NonNull Object uri, @NonNull ImageView imageView);
}
