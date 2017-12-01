package leduyhung.view.myspinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by hungleduy on 11/24/17.
 */

public class MySpinnerView extends SurfaceView implements Runnable {

    private static final int SIZE_ARROW_DEFAULT = 3;
    private static final int PADDING_ARROW_DEFAULT = 30;
    private static final int BORDER_ARROW_DEFAULT = 5;

    private SurfaceHolder holder;
    private Thread thread;
    private DrawSpinnerHelper drawHelper;
    private SpinnerClickListener spinnerClickListener;

    private boolean isStop;
    private int arrowColor, valueColor, backgroundColor;
    private int paddingArrow;
    private int valueSize, arrowSize, borderArrowSize;
    private int radius;
    private int nextItem;
    private int totalItem;

    public MySpinnerView(Context context) {
        super(context);

        getAttribute(context, null);
    }

    public MySpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttribute(context, attrs);
    }

    public MySpinnerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttribute(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawHelper.setWidthHeightView(getWidth(), getHeight());
        drawHelper.drawContruct(canvas, isStop);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        if (visibility == VISIBLE) {

            if (thread == null) {

                thread = new Thread(this);
                thread.start();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (thread != null)
            thread.interrupt();
        thread = null;
        holder = null;
    }

    @Override
    public void run() {

        while (holder != null && !holder.getSurface().isValid() && !isStop)
            continue;

        runValue();
        thread = null;
        isStop = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if (event.getX() <= (paddingArrow + arrowSize + borderArrowSize)) {

                    previousData();
                } else if (event.getX() >= (drawHelper.getWidthView() - (paddingArrow + arrowSize + borderArrowSize))) {

                    nextData();
                } else {

                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void getAttribute(Context context, AttributeSet attrs) {

        if (attrs != null) {

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MySpinnerView);
            arrowColor = typedArray.getColor(R.styleable.MySpinnerView_my_spinner_arrow_color, 0);
            valueColor = typedArray.getColor(R.styleable.MySpinnerView_my_spinner_value_color, 0);
            backgroundColor = typedArray.getColor(R.styleable.MySpinnerView_my_spinner_background_color, 0);
            arrowSize = typedArray.getDimensionPixelSize(R.styleable.MySpinnerView_my_spinner_arrow_size, SIZE_ARROW_DEFAULT);
            borderArrowSize = typedArray.getDimensionPixelSize(R.styleable.MySpinnerView_my_spinner_border_arrow_size, BORDER_ARROW_DEFAULT);
            valueSize = typedArray.getDimensionPixelSize(R.styleable.MySpinnerView_my_spinner_value_size, 0);
            paddingArrow = typedArray.getDimensionPixelOffset(R.styleable.MySpinnerView_my_spinner_padding_arrow, PADDING_ARROW_DEFAULT);
            radius = typedArray.getInteger(R.styleable.MySpinnerView_my_spinner_radius, 0);
            typedArray.recycle();
        }
        holder = getHolder();
        holder.setFormat(PixelFormat.TRANSPARENT);
        setZOrderOnTop(true);
        setWillNotDraw(false);
        drawHelper = new DrawSpinnerHelper(radius, arrowColor, valueColor, backgroundColor, valueSize, arrowSize, borderArrowSize, paddingArrow);
        isStop = true;
        nextItem = 0;
        setCurrentItem(0);
    }

    private void runValue() {

        Canvas c;
        int count = 1;

        while (!isStop && holder != null && holder.getSurface().isValid() && !drawHelper.isValueRunComplete()) {

            try {
                c = holder.lockCanvas();
                if (c != null) {

                    drawHelper.drawChangeValue(c, nextItem, count);
                    holder.unlockCanvasAndPost(c);
                    count++;
                }
            } catch (Exception e) {

                // TODO: show log exception
            }
        }

        postInvalidate();
        return;
    }

    public void setData(ArrayList data) {

        drawHelper.setArrData(data);
        totalItem = data.size();
        invalidate();
    }

    public void setCurrentItem(int i) {

        nextItem = i;
        drawHelper.setItemCurrent(i);
        invalidate();
    }

    public void nextData() {

        if (nextItem < totalItem - 1 && isStop) {
            isStop = false;
            drawHelper.setIsValueRunComplete(false);
            nextItem++;
            thread = new Thread(this);
            invalidate();
            thread.start();
            if (spinnerClickListener != null)
                spinnerClickListener.onPreviousClick(nextItem);
        }
    }

    public void previousData() {

        if (nextItem > 0 && isStop) {

            isStop = false;
            drawHelper.setIsValueRunComplete(false);
            nextItem--;
            thread = new Thread(this);
            invalidate();
            thread.start();
            if (spinnerClickListener != null)
                spinnerClickListener.onPreviousClick(nextItem);
        }
    }

    public ArrayList getData() {

        return drawHelper.getData();
    }

    public int getTotalItem() {

        return totalItem;
    }

    public int getCurrentItem() {

        return nextItem;
    }

    public void setSpinnerClickListener(SpinnerClickListener spinnerClickListener) {

        this.spinnerClickListener = spinnerClickListener;
    }
}