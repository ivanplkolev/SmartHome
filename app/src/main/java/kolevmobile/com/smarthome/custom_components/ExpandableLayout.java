package kolevmobile.com.smarthome.custom_components;

/**
 * Created by x on 19.11.2017 г..
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import kolevmobile.com.smarthome.R;


public class ExpandableLayout extends LinearLayout {

    enum States {
        PRE_INIT,
        CLOSED,
        EXPANDED,
        EXPANDING,
        CLOSING
    }

    static class Settings {
        static final int DEFAULT_EXPAND_DURATION = 300;
        int expandDuration = DEFAULT_EXPAND_DURATION;
        boolean expandWithParentScroll;
        boolean expandScrollTogether;
    }

    static class ScrolledParent {
        ViewGroup scrolledView;
        int childBetweenParentCount;
    }

    private Settings mSettings;
    private States mExpandState;
    private ValueAnimator mExpandAnimator;
    private ValueAnimator mParentAnimator;
    private AnimatorSet mExpandScrollAnimotorSet;
    private int mExpandedViewHeight;
    private boolean mIsInit = true;

    private ScrolledParent mScrolledParent;

    public ExpandableLayout(Context context) {
        super(context);
        init(null);
    }

    public ExpandableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ExpandableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    static ScrolledParent getScrolledParent(ViewGroup child) {

        ViewParent parent = child.getParent();
        int childBetweenParentCount = 0;
        while (parent != null) {
            if ((parent instanceof RecyclerView || parent instanceof AbsListView)) {
                ScrolledParent scrolledParent = new ScrolledParent();
                scrolledParent.scrolledView = (ViewGroup) parent;
                scrolledParent.childBetweenParentCount = childBetweenParentCount;
                return scrolledParent;
            }
            childBetweenParentCount++;
            parent = parent.getParent();
        }
        return null;
    }

    static ValueAnimator createParentAnimator(final View parent, int distance, long duration) {
        ValueAnimator parentAnimator = ValueAnimator.ofInt(0, distance);
        parentAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int lastDy;
            int dy;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dy = (int) animation.getAnimatedValue() - lastDy;
                lastDy = (int) animation.getAnimatedValue();
                parent.scrollBy(0, dy);
            }
        });
        parentAnimator.setDuration(duration);

        return parentAnimator;
    }

    private void init(AttributeSet attrs) {
        setClickable(true);
        setOrientation(VERTICAL);
        this.setClipChildren(false);
        this.setClipToPadding(false);
        mExpandState = States.PRE_INIT;
        mSettings = new Settings();
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableLayout);
            mSettings.expandDuration = typedArray.getInt(R.styleable.ExpandableLayout_expDuration, Settings.DEFAULT_EXPAND_DURATION);
            mSettings.expandWithParentScroll = typedArray.getBoolean(R.styleable.ExpandableLayout_expWithParentScroll, false);
            mSettings.expandScrollTogether = typedArray.getBoolean(R.styleable.ExpandableLayout_expExpandScrollTogether, true);
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount != 2) {
            throw new IllegalStateException("ExpandableLayout must has two child view !");
        }
        if (mIsInit) {
            ((MarginLayoutParams) getChildAt(0).getLayoutParams()).bottomMargin = 0;
            MarginLayoutParams marginLayoutParams = ((MarginLayoutParams) getChildAt(1).getLayoutParams());
            marginLayoutParams.bottomMargin = 0;
            marginLayoutParams.topMargin = 0;
            marginLayoutParams.height = 0;
            mExpandedViewHeight = getChildAt(1).getMeasuredHeight();
            mIsInit = false;
            mExpandState = States.CLOSED;
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mSettings.expandWithParentScroll) {
            mScrolledParent = getScrolledParent(this);
        }
    }

    private int getParentScrollDistance() {
        int distance = 0;

        if (mScrolledParent == null) {
            return distance;
        }

        distance = (int) (getY() + getMeasuredHeight() + mExpandedViewHeight
                - mScrolledParent.scrolledView.getMeasuredHeight());
        for (int index = 0; index < mScrolledParent.childBetweenParentCount; index++) {
            ViewGroup parent = (ViewGroup) getParent();
            distance += parent.getY();
        }

        return distance;
    }

    private void verticalAnimate(final int startHeight, final int endHeight) {
        int distance = getParentScrollDistance();

        final View target = getChildAt(1);
        mExpandAnimator = ValueAnimator.ofInt(startHeight, endHeight);
        mExpandAnimator.addUpdateListener(animation -> {
            target.getLayoutParams().height = (int) animation.getAnimatedValue();
            target.requestLayout();
        });

        mExpandAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (endHeight - startHeight < 0) {
                    mExpandState = States.CLOSED;
                } else {
                    mExpandState = States.EXPANDED;
                }
            }
        });

        mExpandState = mExpandState == States.EXPANDED ? States.CLOSING : States.EXPANDING;
        mExpandAnimator.setDuration(mSettings.expandDuration);
        if (mExpandState == States.EXPANDING && mSettings.expandWithParentScroll && distance > 0) {

            mParentAnimator = createParentAnimator(mScrolledParent.scrolledView, distance, mSettings.expandDuration);

            mExpandScrollAnimotorSet = new AnimatorSet();

            if (mSettings.expandScrollTogether) {
                mExpandScrollAnimotorSet.playTogether(mExpandAnimator, mParentAnimator);
            } else {
                mExpandScrollAnimotorSet.playSequentially(mExpandAnimator, mParentAnimator);
            }
            mExpandScrollAnimotorSet.start();

        } else {
            mExpandAnimator.start();
        }
    }

    public void toggle() {
        if (mExpandState == States.EXPANDED) {
            close();
        } else if (mExpandState == States.CLOSED) {
            expand();
        }
    }

    public void expand() {
        verticalAnimate(0, mExpandedViewHeight);
    }

    public void close() {
        verticalAnimate(mExpandedViewHeight, 0);
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mExpandAnimator != null && mExpandAnimator.isRunning()) {
            mExpandAnimator.cancel();
            mExpandAnimator.removeAllUpdateListeners();
        }
        if (mParentAnimator != null && mParentAnimator.isRunning()) {
            mParentAnimator.cancel();
            mParentAnimator.removeAllUpdateListeners();
        }
        if (mExpandScrollAnimotorSet != null) {
            mExpandScrollAnimotorSet.cancel();
        }
    }

    public boolean isExpanded(){
        return mExpandState == States.EXPANDED;
    }
}
