package com.salton123.sf.appflow;

import android.os.Bundle;

import com.salton123.sf.appflow.ui.fragment_swipe_back.FirstSwipeBackFragment;

import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;


/**
 * Created by YoKeyword on 16/4/19.
 */
public class SwipeBackSampleActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_back);

        if (findFragment(FirstSwipeBackFragment.class) == null) {
            loadRootFragment(R.id.fl_container, FirstSwipeBackFragment.newInstance());
        }

        getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_ALL);
    }

    /**
     * 限制SwipeBack的条件,默认栈内Fragment数 <= 1时 , 优先滑动退出Activity , 而不是Fragment
     *
     * @return true: Activity优先滑动退出;  false: Fragment优先滑动退出
     */
    @Override
    public boolean swipeBackPriority() {
        return super.swipeBackPriority();
    }

    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
