package universe.sortalgorithmssimulation.activity.views.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


public class Ball {

    public float x;
    public float y;
    public String text;
    private Bitmap mBallBitmap;

    private final Rect txtBounds = new Rect();
    private static final Paint mTextPaint = new Paint();

    public Ball(float x, float y, String text, Bitmap blueBall, float scale) {
        this.x = x;
        this.y = y;
        this.text = text;
        mBallBitmap = blueBall;

        mTextPaint.setTextSize(scale);
        mTextPaint.getTextBounds(text, 0, text.length(), txtBounds);
    }

    public void switchBall(Bitmap ballBitmap) {
        mBallBitmap = ballBitmap;
    }

    public void drawBall(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBallBitmap, x, y, paint);
    }

    public void drawTextBall(Canvas canvas, Paint textPaint) {
        canvas.drawText(text, x + (mBallBitmap.getWidth())/2 + txtBounds.width()/2
                , y + (mBallBitmap.getHeight() + txtBounds.height())/2
                , textPaint);
    }
}
