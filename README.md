# ImageSwitcher
Android图片轮播组件，提供多种切换效果。

## Demo

![](https://github.com/nightkyb/ImageSwitcher/blob/master/demo.gif)

## Usage

### Step 1

#### Gradle

root build.gradle:

 ```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
``` 

app build.gradle:

```groovy
dependencies {
    implementation 'com.github.nightkyb:ImageSwitcher:1.0.0'
}
```

### Step 2

Add the ImageSwitcher to your layout:

```java
<com.nightkyb.imageswitcher.ImageSwitcher
        android:id="@+id/is_images1"
        android:layout_width="match_parent"
        android:layout_height="200dp"/>
```

### Step 3

Java code:

```java
imageSwitcher.setImageList(images) // 设置要展示的图片列表
             .setImageLoader(new SwitcherImageLoader() {
                 @Override
                 public void loadImage(final int index, @NonNull final Object uri, @NonNull final ImageView imageView) {
                     imageView.setImageResource(Integer.parseInt(uri.toString()));
                 }
              }) // 设置图片加载器
              .setStyle(ImageSwitcher.STYLE_FLOAT_LEFT) // 设置动画样式，默认STYLE_LOOP
              .setScaleType(ImageView.ScaleType.CENTER_CROP) // 设置图片缩放类型，默认ScaleType.CENTER_CROP
              .setInterval(2000) // 设置轮播间隔，默认2000ms
              .setDuration(1500) // 设置动画时间，默认1000ms
              .setStartDelay(300) // 设置动画前延时，默认150ms
              .setHorizontalMultiple(2f) // 设置水平变化参数，默认3f
              .setVerticalMultiple(2f) // 设置垂直变化参数，默认4f
              .setParticleRadius(4) // 设置粒子基础半径，单位为px，默认1dp
              .start();
```

[Code example](https://github.com/nightkyb/ImageSwitcher/blob/master/app/src/main/java/com/nightkyb/imageswitcherdemo/MainActivity.java)
