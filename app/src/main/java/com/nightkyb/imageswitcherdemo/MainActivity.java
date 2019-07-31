package com.nightkyb.imageswitcherdemo;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.nightkyb.imageswitcher.ImageSwitcher;
import com.nightkyb.imageswitcher.SwitcherImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageSwitcher is1;
    private ImageSwitcher is2;
    private ImageSwitcher is3;
    private List<Integer> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        is1 = findViewById(R.id.is_images1);
        is2 = findViewById(R.id.is_images2);
        is3 = findViewById(R.id.is_images3);

        images.add(R.drawable.ic_01);
        images.add(R.drawable.ic_02);
        images.add(R.drawable.ic_03);
        images.add(R.drawable.ic_04);
        images.add(R.drawable.ic_05);
        images.add(R.drawable.ic_06);

        is1.setImageList(images)
           .setImageLoader(new SwitcherImageLoader() {
               @Override
               public void loadImage(final int index, @NonNull final Object uri, @NonNull final ImageView imageView) {
                   imageView.setImageResource(Integer.parseInt(uri.toString()));
               }
           })
           .setStyle(ImageSwitcher.STYLE_FLOAT_LEFT)
           .setParticleRadius(6)
           // .setInterval(6000)
           .setDuration(1500)
           .setHorizontalMultiple(2f)
           .setVerticalMultiple(2f);

        is2.setImageList(images)
           .setImageLoader(new SwitcherImageLoader() {
               @Override
               public void loadImage(final int index, @NonNull final Object uri, @NonNull final ImageView imageView) {
                   imageView.setImageResource(Integer.parseInt(uri.toString()));
               }
           })
           .setScaleType(ImageView.ScaleType.FIT_CENTER)
           .setStyle(ImageSwitcher.STYLE_LOOP);

        is3.setImageList(images)
           .setImageLoader(new SwitcherImageLoader() {
               @Override
               public void loadImage(final int index, @NonNull final Object uri, @NonNull final ImageView imageView) {
                   imageView.setImageResource(Integer.parseInt(uri.toString()));
               }
           })
           .setStyle(ImageSwitcher.STYLE_FLOAT_RIGHT)
           .setParticleRadius(1)
           .setDuration(3000)
           .setInterval(4000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        is1.start();
        is2.start();
        is3.start();
    }

    @Override
    protected void onStop() {
        is1.stop();
        is2.stop();
        is3.stop();
        super.onStop();
    }
}
