package com.salton123.sf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;

import net.wequick.small.Small;

public class MainActivity extends AppCompatActivity {
    LottieAnimationView animationView;
    private boolean beSetup = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setContentView(R.layout.activity_main);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);

//        Small.setUp(getApplicationContext(), new Small.OnCompleteListener() {
//            @Override
//            public void onComplete() {
//                beSetup = true;
//                gotoMain();
//            }
//        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        animationView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Small.setUp(getApplicationContext(), new Small.OnCompleteListener() {
                    @Override
                    public void onComplete() {
                        beSetup = true;
                        gotoMain();
                        MainActivity.this.finish();
                    }
                });
//                if (beSetup) {
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
//                }
            }
        }, 3000);
    }

    private void gotoMain() {
//        SupportFragment fragment = Small.createObject("fragment-v4", "zhihu/first", MainActivity.this);
//        if (fragment == null) {
//            System.out.println("fragment is null");
//            Toast.makeText(getApplicationContext(), "fragment is null", Toast.LENGTH_LONG).show();
//        } else {
////                    getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
//            loadRootFragment(R.id.container, fragment);
//        }
//        System.out.println("size:" + SizeUtils.getSize());
        String url = Small.getBaseUri();
        System.out.println("url=" + url);
        Small.openUri("zhihu", MainActivity.this);
    }
}
