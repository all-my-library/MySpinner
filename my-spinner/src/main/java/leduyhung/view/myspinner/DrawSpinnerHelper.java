package leduyhung.view.myspinner;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;

/**
 * Created by hungleduy on 11/24/17.
 */

class DrawSpinnerHelper {

    private Paint paintArrow, paintValue, paintBackground, paintValueVisible;
    private Path pathArrow;
    private RectF rectView;
    private Rect boundText, boundTextContruct;
    private int radiusView;
    private int widthView, heightView;
    private int paddingArrow, arrowSize, boundArrow;
    private int valueColor;
    private float centerY, centerX;
    private int itemCurrent, oldItemCurrent, totalItem;
    private float space, speed;
    private boolean isValueRunComplete;

    private ArrayList<SpinnerData> dataSpinner;
    private ArrayList<String> arrData;

    DrawSpinnerHelper(int radiusView, int arrowColor, int valueColor, int colorBackground, int valueSize, int arrowSize, int borderArrowSize, int paddingArrow) {

        paintArrow = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintArrow.setStyle(Paint.Style.STROKE);
        paintValue = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintValueVisible = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintArrow.setColor(arrowColor);
        paintValue.setColor(valueColor);
        paintValueVisible.setColor(valueColor);
        paintBackground.setColor(colorBackground);
        paintValue.setTextSize(valueSize);
        paintValueVisible.setTextSize(valueSize);
        rectView = new RectF();
        paintArrow.setStrokeWidth(borderArrowSize);
        this.paddingArrow = paddingArrow;
        this.arrowSize = arrowSize;
        this.radiusView = radiusView;
        this.valueColor = valueColor;
        pathArrow = new Path();
        boundText = new Rect();
        boundTextContruct = new Rect();
        boundArrow = paddingArrow + arrowSize + borderArrowSize;
    }

    private void drawArrowLeft(Canvas canvas) {

        pathArrow.reset();
        pathArrow.moveTo(paddingArrow + (arrowSize / 2), centerY - (arrowSize / 3));
        pathArrow.lineTo(paddingArrow, centerY);
        canvas.drawPath(pathArrow, paintArrow);
        pathArrow.lineTo(paddingArrow + (arrowSize / 2), centerY + (arrowSize / 3));
        canvas.drawPath(pathArrow, paintArrow);
        pathArrow.close();
    }

    private void drawArrowRight(Canvas canvas) {

        pathArrow.reset();
        pathArrow.moveTo(widthView - (paddingArrow + (arrowSize / 2)), centerY - (arrowSize / 3));
        pathArrow.lineTo(widthView - paddingArrow, centerY);
        canvas.drawPath(pathArrow, paintArrow);
        pathArrow.lineTo(widthView - (paddingArrow + (arrowSize / 2)), centerY + (arrowSize / 3));
        canvas.drawPath(pathArrow, paintArrow);
        pathArrow.close();
    }

    private void drawValue(Canvas canvas) {

        if (arrData != null) {
            paintValue.getTextBounds(arrData.get(itemCurrent), 0, arrData.get(itemCurrent).length(), boundTextContruct);
            canvas.drawText(dataSpinner.get(itemCurrent).getValue(), centerX - (boundTextContruct.width() / 2), centerY + (boundTextContruct.height() / 2), paintValue);
        }
    }

    void setSpace(int space) {


    }

    void setArrData(ArrayList<String> data) {

        this.arrData = data;
        dataSpinner = new ArrayList();
        totalItem = arrData.size();
    }

    void setItemCurrent(int i) {

        this.itemCurrent = i;
        oldItemCurrent = itemCurrent;
    }

    void setWidthHeightView(int width, int height) {

        this.widthView = width;
        this.heightView = height;
        rectView.set(0, 0, widthView, heightView);
        centerY = heightView / 2;
        centerX = widthView / 2;
        this.space = widthView / 3;
        speed = space / 5;
        if (arrData != null) {

            for (int i = 0; i < totalItem; i++) {
                paintValue.getTextBounds(arrData.get(i), 0, arrData.get(i).length(), boundText);
                if (i != itemCurrent) {

                    dataSpinner.add(new SpinnerData(arrData.get(i), centerX - (boundText.width() / 2) + (space * (i - itemCurrent)), centerY + (boundText.height() / 2)));
                } else {

                    dataSpinner.add(new SpinnerData(arrData.get(i), centerX - (boundText.width() / 2), centerY + (boundText.height() / 2)));
                }
            }
        }
    }

    int getWidthView() {

        return widthView;
    }

    int getHeightView() {

        return heightView;
    }

    ArrayList getData() {

        return arrData;
    }

    boolean isValueRunComplete() {

        return isValueRunComplete;
    }

    void setIsValueRunComplete(boolean a) {

        this.isValueRunComplete = a;
    }

    void drawContruct(Canvas canvas, boolean isDrawTextContruct) {

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.ADD);
        canvas.drawRoundRect(rectView, radiusView, radiusView, paintBackground);
        if (itemCurrent != 0)
            drawArrowLeft(canvas);
        if (itemCurrent != (totalItem - 1))
            drawArrowRight(canvas);
        if (isDrawTextContruct) {

            drawValue(canvas);
        }
    }

    synchronized void drawChangeValue(Canvas canvas, int nextItem, int count) {

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        isValueRunComplete = false;
        if (nextItem > itemCurrent) {

            if (count <= 5) {

                paintValueVisible.setAlpha((5 - count) * 20);
                canvas.drawText(dataSpinner.get(itemCurrent).getValue(), dataSpinner.get(itemCurrent).getX() - (speed * count), dataSpinner.get(itemCurrent).getY(), paintValueVisible);

                paintValueVisible.setAlpha(count * 20);
                canvas.drawText(dataSpinner.get(nextItem).getValue(), dataSpinner.get(nextItem).getX() - (speed * count), dataSpinner.get(nextItem).getY(), paintValueVisible);
            } else {

                for (int i = 0; i < totalItem; i++) {

                    dataSpinner.get(i).setX(dataSpinner.get(i).getX() - space);
                }
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                itemCurrent = nextItem;
                isValueRunComplete = true;
            }
        } else if (nextItem < itemCurrent) {

            if (count <= 5) {

                paintValueVisible.setAlpha(count * 20);
                canvas.drawText(dataSpinner.get(itemCurrent).getValue(), dataSpinner.get(itemCurrent).getX() + (speed * count), dataSpinner.get(itemCurrent).getY(), paintValueVisible);

                paintValueVisible.setAlpha((5 - count) * 20);
                canvas.drawText(dataSpinner.get(nextItem).getValue(), dataSpinner.get(nextItem).getX() + (speed * count), dataSpinner.get(nextItem).getY(), paintValueVisible);
            } else {

                for (int i = 0; i < totalItem; i++) {

                    dataSpinner.get(i).setX(dataSpinner.get(i).getX() + space);
                }
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                itemCurrent = nextItem;
                isValueRunComplete = true;
            }
        }
    }
}