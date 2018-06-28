package com.teaanddogdog.flowviewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author banbury
 * @version v1.0
 * @created 2018/2/2_11:45.
 * @description
 */

public class AutoNewLineViewGroup extends RelativeLayout {
    private static final String TAG = "AutoNewLineViewGroup";
    private int mChildCount;
    private int mViewMeasuredWidth;
    private int mTempLineWidth;//临时的已用行宽
    private int mTempLineHeight;//临时的已用行宽
    private List<Integer> mLineWidthList = new ArrayList<>();//每行的宽度
    private List<Integer> mLineHeightList = new ArrayList<>();//每行的高度
    private List<List<View>> TopViews = new ArrayList<>();
    private List<View> lineViews = new ArrayList<>();

    public AutoNewLineViewGroup(Context context) {
        super(context);
    }

    public AutoNewLineViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoNewLineViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量子view
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //由于前面调用了super.onMeasure(),通过下面的方法可以得到本view的宽度，也可以通过widthMeasureSpec获得
        mViewMeasuredWidth = getMeasuredWidth();
        Log.d(TAG, "onMeasure: " + mViewMeasuredWidth);
        //获取本view左右两侧的padding
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        //实际可以供子view的宽度
        int canUseWidth = mViewMeasuredWidth - paddingLeft - paddingRight;
        mLineWidthList.clear();
        mLineHeightList.clear();
        TopViews.clear();
        lineViews.clear();//不加这个，第一行会莫名其妙多2个view，还不清楚是什么原因
        mChildCount = getChildCount();
        for (int i = 0; i < mChildCount; i++) {
            View child = getChildAt(i);
            //先测量子View
            MarginLayoutParams childLayoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childMeasuredWidth = child.getMeasuredWidth() + childLayoutParams.leftMargin + childLayoutParams.rightMargin;//子view的实际占宽
            int childMeasuredHeight = child.getMeasuredHeight() + childLayoutParams.topMargin + childLayoutParams.bottomMargin;//子view的实际占高
            if ((mTempLineWidth + childMeasuredWidth > canUseWidth) && lineViews.size() > 0) {
                mLineWidthList.add(mTempLineWidth);
                mLineHeightList.add(mTempLineHeight);
                TopViews.add(lineViews);
                mTempLineWidth = childMeasuredWidth;
                mTempLineHeight = childMeasuredHeight;
                lineViews = new ArrayList<>();
            } else {
                mTempLineWidth += childMeasuredWidth;
                mTempLineHeight = Math.max(mTempLineHeight, childMeasuredHeight);
            }
            lineViews.add(child);
        }
        //最后一行的数据需要添加进对应集合中
        mLineWidthList.add(mTempLineWidth);
        mLineHeightList.add(mTempLineHeight);
        TopViews.add(lineViews);

        mTempLineWidth = 0;
        mTempLineHeight = 0;
        int totalHeight = 0;
        for (Integer integer : mLineHeightList) {
            totalHeight += integer;
        }
        setMeasuredDimension(mViewMeasuredWidth, totalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int tempTop = 0;
        //实际可以供子view的宽度
        int canUseWidth = mViewMeasuredWidth - getPaddingLeft() - getPaddingRight();
        for (int i = 0; i < TopViews.size(); i++) {
            List<View> views = TopViews.get(i);
            Integer lineWidth = mLineWidthList.get(i);
            Integer lineHeight = mLineHeightList.get(i);
            int tempLeft = 0;
            int child_left;
            int child_right;
            MarginLayoutParams childLayoutParams;
            for (View view : views) {
                childLayoutParams = (MarginLayoutParams) view.getLayoutParams();
//                int childMeasuredWidth = view.getMeasuredWidth() + childLayoutParams.leftMargin + childLayoutParams.rightMargin;//子view的实际占宽
                child_left = tempLeft + childLayoutParams.leftMargin;
                child_right = child_left + view.getMeasuredWidth();
                if (child_right > canUseWidth) {
//                    child_right = canUseWidth-child_left-childLayoutParams.rightMargin;
                }

                int viewMeasuredHeight = view.getMeasuredHeight();
                int marge = (lineHeight - viewMeasuredHeight) / 2;
                int child_top = tempTop + marge;

                view.layout(child_left, child_top, child_right, child_top + viewMeasuredHeight);

                Log.d(TAG, "第" + i + "行，onLayout: " + view.getLeft() + "==" + view.getTop() + "==" + view.getRight() + "==" + view.getBottom());
                tempLeft = child_right + childLayoutParams.rightMargin;
            }
            tempTop += lineHeight;
        }
    }
}
