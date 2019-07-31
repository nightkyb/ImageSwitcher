package com.nightkyb.imageswitcher;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;

import com.nightkyb.imageswitcher.particlesmasher.ParticleSmasher;
import com.nightkyb.imageswitcher.particlesmasher.SmashAnimator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 图片轮播切换组件，提供多种切换效果。
 *
 * @author nightkyb created at 2019/7/24 15:34
 */
public class ImageSwitcher extends FrameLayout {
    public static final int STYLE_FLOAT_LEFT = 1;     // 飘落——>自左往右，逐列飘落
    public static final int STYLE_FLOAT_RIGHT = 2;    // 飘落——>自右往左，逐列飘落
    public static final int STYLE_FLOAT_TOP = 3;      // 飘落——>自上往下，逐行飘落
    public static final int STYLE_FLOAT_BOTTOM = 4;   // 飘落——>自下往上，逐行飘落
    public static final int STYLE_RANDOM = 5;         // 随机切换 左/右/上/下
    public static final int STYLE_LOOP = 6;           // 循环切换 左->右->上->下

    private ImageView ivSwitcher;
    private ParticleSmasher particleSmasher;
    private List<?> images = new ArrayList<>();
    private int[] styles = {
            STYLE_FLOAT_LEFT,
            STYLE_FLOAT_RIGHT,
            STYLE_FLOAT_TOP,
            STYLE_FLOAT_BOTTOM
    };
    private int imageIndex = 0;
    private int styleIndex = 0;
    private SwitcherImageLoader imageLoader;
    private Random random = new Random();
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                switchImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.postDelayed(this, interval);
        }
    };

    private int style = STYLE_LOOP;                    // 动画样式
    private long interval = 2000L;                     // 轮播间隔
    private long duration = 1000L;                     // 动画持续时间
    private long startDelay = 150L;                    // 动画开始前延时
    private float horizontalMultiple = 3F;             // 粒子水平变化幅度
    private float verticalMultiple = 4F;               // 粒子垂直变化幅度
    private int radius = Utils.dp2px(1);               // 粒子基础半径

    public ImageSwitcher(@NonNull Context context) {
        this(context, null);
    }

    public ImageSwitcher(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageSwitcher(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        ivSwitcher = new ImageView(context);
        ivSwitcher.setScaleType(ScaleType.CENTER_CROP);
        addView(ivSwitcher, params);

        particleSmasher = new ParticleSmasher(context);
        addView(particleSmasher, params);
    }

    /**
     * 设置要展示的图片列表
     *
     * @param images 图片列表
     * @return 链式调用，因此返回本身
     */
    public ImageSwitcher setImageList(@NonNull final List<?> images) {
        this.images = images;

        // 加载第一帧图片
        if (imageLoader != null) {
            imageLoader.loadImage(0, images.get(0), ivSwitcher);
        }

        return this;
    }

    /**
     * 设置图片加载器
     *
     * @param imageLoader 图片加载器
     * @return 链式调用，因此返回本身
     */
    public ImageSwitcher setImageLoader(@NonNull final SwitcherImageLoader imageLoader) {
        this.imageLoader = imageLoader;

        // 加载第一帧图片
        if (!images.isEmpty()) {
            imageLoader.loadImage(0, images.get(0), ivSwitcher);
        }

        return this;
    }

    /**
     * 设置图片缩放类型，默认ScaleType.CENTER_CROP
     *
     * @param scaleType 图片缩放类型
     * @return 链式调用，因此返回本身
     */
    public ImageSwitcher setScaleType(ScaleType scaleType) {
        ivSwitcher.setScaleType(scaleType);

        // 加载第一帧图片
        if (imageLoader != null && !images.isEmpty()) {
            imageLoader.loadImage(0, images.get(0), ivSwitcher);
        }

        return this;
    }

    /**
     * 设置动画样式
     *
     * @param style {@link #STYLE_FLOAT_LEFT}, {@link #STYLE_FLOAT_RIGHT}, {@link #STYLE_FLOAT_TOP}, {@link #STYLE_FLOAT_BOTTOM}, {@link #STYLE_RANDOM}, {@link #STYLE_LOOP};
     * @return 链式调用，因此返回本身
     */
    public ImageSwitcher setStyle(int style) {
        this.style = style;
        return this;
    }

    /**
     * 设置轮播间隔
     *
     * @param interval 时间，单位为毫秒
     * @return 链式调用，因此返回本身
     */
    public ImageSwitcher setInterval(long interval) {
        this.interval = interval;
        return this;
    }

    /**
     * 设置动画时间
     *
     * @param duration 时间，单位为毫秒
     * @return 链式调用，因此返回本身
     */
    public ImageSwitcher setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    /**
     * 设置动画前延时
     *
     * @param startDelay 动画开始前的延时，单位为毫秒
     * @return 链式调用，因此返回本身
     */
    public ImageSwitcher setStartDelay(long startDelay) {
        this.startDelay = startDelay;
        return this;
    }

    /**
     * 设置水平变化参数
     *
     * @param horizontalMultiple 水平变化幅度，默认为3。为0则不产生变化。
     * @return 链式调用，因此返回本身
     */
    public ImageSwitcher setHorizontalMultiple(float horizontalMultiple) {
        this.horizontalMultiple = horizontalMultiple;
        return this;
    }

    /**
     * 设置垂直变化参数
     *
     * @param verticalMultiple 垂直变化参数，默认为4，为0则不产生变化
     * @return 链式调用，因此返回本身
     */
    public ImageSwitcher setVerticalMultiple(float verticalMultiple) {
        this.verticalMultiple = verticalMultiple;
        return this;
    }

    /**
     * 设置粒子基础半径
     *
     * @param radius 半径，单位为px
     * @return 链式调用，因此返回本身
     */
    public ImageSwitcher setParticleRadius(@Px int radius) {
        this.radius = radius;
        return this;
    }

    /**
     * 开始轮播
     */
    public void start() {
        handler.postDelayed(runnable, interval);
    }

    /**
     * 停止轮播
     */
    public void stop() {
        handler.removeCallbacks(runnable);
    }

    private void switchImage() {
        imageIndex++;
        if (imageIndex == images.size()) {
            imageIndex = 0;
        }

        int currentStyle;
        switch (style) {
            case STYLE_LOOP:
                styleIndex++;
                if (styleIndex == styles.length) {
                    styleIndex = 0;
                }
                currentStyle = styles[styleIndex];
                break;
            case STYLE_RANDOM:
                styleIndex = random.nextInt(styles.length);
                currentStyle = styles[styleIndex];
                break;
            default:
                currentStyle = style;
                break;
        }

        particleSmasher.with(ivSwitcher)
                       .setStyle(currentStyle)                    // 设置动画样式
                       .setDuration(duration)                     // 设置动画时间
                       .setStartDelay(startDelay)                 // 设置动画前延时
                       .setHorizontalMultiple(horizontalMultiple) // 设置横向运动幅度，默认为3
                       .setVerticalMultiple(verticalMultiple)     // 设置竖向运动幅度，默认为4
                       .setParticleRadius(radius)
                       .addAnimatorListener(new SmashAnimator.OnAnimatorListener() {
                           @Override
                           public void onAnimatorStart() {
                               if (imageLoader != null) {
                                   imageLoader.loadImage(imageIndex, images.get(imageIndex), ivSwitcher);
                               }
                           }
                       })
                       .start();
    }
}
