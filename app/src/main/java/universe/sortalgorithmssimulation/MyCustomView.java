package universe.sortalgorithmssimulation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Nhat on 4/6/2017.
 */

public class MyCustomView extends View {

    private Paint mPaint;
    private Bitmap mBallBitmap;

    public MyCustomView(Context context) {
        super(context);
        init();
    }

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);

        mBallBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ballcomparing);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(161, 161, 50, mPaint);
        canvas.drawBitmap(mBallBitmap, 500, 500, null);
    }
}
