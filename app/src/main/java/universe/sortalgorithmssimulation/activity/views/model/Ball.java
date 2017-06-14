package universe.sortalgorithmssimulation.activity.views.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import universe.sortalgorithmssimulation.activity.views.BaseSortView;

public class Ball {

    public float x;
    public float y;
    // public Bitmap bitmap;
    public String text;
    public int state;
    private int mSize;

    private final Rect txtBounds = new Rect();
    private static final Paint mTextPaint = new Paint();

    /*public Ball(float x, float y, Bitmap bitmap, int size, String text, float scale) {
        this.x = x;
        this.y = y;
        this.text = text;
        mSize = size;
        this.bitmap = bitmap;

        mTextPaint.setTextSize(scale);
        mTextPaint.getTextBounds(text, 0, text.length(), txtBounds);
    }*/

    public Ball(float x, float y, int size, String text, float scale) {
        this.x = x;
        this.y = y;
        this.text = text;
        mSize = size;
        this.state = BaseSortView.State.IDLE;

        mTextPaint.setTextSize(scale);
        mTextPaint.getTextBounds(text, 0, text.length(), txtBounds);
    }

    /*public void drawBall(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, x, y, paint);
    }*/

    public void drawBall(Canvas canvas, Bitmap bitmap, Paint paint) {
        canvas.drawBitmap(bitmap, x, y, paint);
    }

    public void drawTextBall(Canvas canvas, Paint textPaint) {
        canvas.drawText(text, x + mSize/2 + txtBounds.width()/2
                , y + (mSize + txtBounds.height())/2
                , textPaint);
    }
}
