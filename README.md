# ImageSwitcher
Android图片轮播切换组件，提供多种切换效果。

## Demo

## Usage

### Step 1

#### Gradle

root build.gradle

 ```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
``` 

app build.gradle

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

[Code example](https://github.com/nightkyb/ImageSwitcher/blob/master/app/src/main/java/com/nightkyb/imageswitcherdemo/MainActivity.java)

### Step 3






