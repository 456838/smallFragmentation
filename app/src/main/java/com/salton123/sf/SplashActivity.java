package com.salton123.sf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import net.wequick.small.Small;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {
    LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
//                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.setSpeed(2f);
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Small.setUp(getApplicationContext(), new Small.OnCompleteListener() {
                            @Override
                            public void onComplete() {
                                gotoMain();
                                SplashActivity.this.finish();
                            }
                        });
                    }
                });
    }

    private void gotoMain() {
        String url = Small.getBaseUri();
        System.out.println("url=" + url);
        Small.openUri("wechat", SplashActivity.this);
//        Small.openUri("appmain", SplashActivity.this);
//        Small.openUri("appmain", Small.getContext());
    }
}
