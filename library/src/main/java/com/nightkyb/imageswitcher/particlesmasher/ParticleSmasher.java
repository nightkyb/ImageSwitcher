package com.nightkyb.imageswitcher.particlesmasher;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ParticleSmasher extends View {
    private List<SmashAnimator> mAnimators = new ArrayList<>();
    private final Canvas mCanvas = new Canvas();
    private Activity mActivity;

    public ParticleSmasher(@NonNull Context context) {
        this(context, null);
    }

    public ParticleSmasher(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParticleSmasher(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /*public ParticleSmasher(Activity activity) {
        super((Context) activity);
        this.mActivity = activity;
        // addView2Window(activity);
        init();
    }*/

    /**
     * 添加View到当前界面
     */
    private void addView2Window(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        // 需要足够的空间展现动画，因此这里使用的是充满父布局
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.addView(this, layoutParams);
    }

    private void init() {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (SmashAnimator animator : mAnimators) {
            animator.draw(canvas);
        }
    }

    public SmashAnimator with(View view) {
        // 每次都新建一个单独的SmashAnimator对象
        SmashAnimator animator = new SmashAnimator(this, view);
        mAnimators.add(animator);
        return animator;
    }

    /**
     * 获取View的Rect，并去掉状态栏、toolbar高度
     *
     * @param view 来源View
     * @return 获取到的Rect
     */
    public Rect getViewRect(View view) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);

        int[] location = new int[2];
        getLocationOnScreen(location);

        rect.offset(-location[0], -location[1]);
        return rect;
    }

    /**
     * 获取View的Bitmap
     *
     * @param view 来源View
     * @return 获取到的图片
     */
    public Bitmap createBitmapFromView(View view) {
        view.clearFocus();

        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        if (bitmap != null) {
            synchronized (mCanvas) {
                Canvas canvasTemp = mCanvas;
                canvasTemp.setBitmap(bitmap);
                view.draw(canvasTemp);
                canvasTemp.setBitmap(null);
            }
        }

        return bitmap;
    }

    /**
     * 移除动画
     *
     * @param animator 需要移除的动画
     */
    public void removeAnimator(SmashAnimator animator) {
        mAnimators.remove(animator);
    }

    /**
     * 清除所有动画
     */
    public void clear() {
        mAnimators.clear();
        invalidate();
    }

    /**
     * 让View重新显示
     *
     * @param view 已经隐藏的View
     */
    public void reShowView(View view) {
        view.animate().setDuration(100).setStartDelay(0).scaleX(1).scaleY(1).alpha(1).start();
    }
}
