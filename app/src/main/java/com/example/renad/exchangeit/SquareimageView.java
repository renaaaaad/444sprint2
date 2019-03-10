package com.example.renad.exchangeit;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class SquareimageView extends AppCompatImageView {
    public SquareimageView(Context context) {
        super(context);
    }

    public SquareimageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareimageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
