package universe.sortalgorithmssimulation.activity.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import universe.sortalgorithmssimulation.R;
import universe.sortalgorithmssimulation.activity.views.model.Ball;
import universe.sortalgorithmssimulation.utils.BitmapUtils;

/**
 * Created by Nhat on 4/7/2017.
 */

public abstract class BaseSortView extends SurfaceView {

    protected SurfaceHolder mSurfaceHolder;

    protected Bitmap mComparingBall;
    protected Bitmap mIdleBall;
    protected Bitmap mFinishedBall;

    protected Ball mBalls[];

    protected int mBallSize;
    protected int mBallDistance;

    private Paint mTxtBallStyle;
    private Paint mBallStyle = new Paint(Paint.FILTER_BITMAP_FLAG);

    public BaseSortView(Context context) {
        this(context, null);
    }

    public BaseSortView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseSortView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSurfaceHolder = getHolder();
        Resources resources = getResources();

        float scale = resources.getDimensionPixelSize(R.dimen.text_ball_size);
        mTxtBallStyle = new Paint();
        mTxtBallStyle.setColor(Color.BLACK);
        mTxtBallStyle.setAntiAlias(true);
        mTxtBallStyle.setTextSize(scale);
        mTxtBallStyle.setStyle(Paint.Style.FILL);
        mTxtBallStyle.setTextAlign(Paint.Align.RIGHT);

        mBallStyle.setFilterBitmap(true);
        mBallStyle.setAntiAlias(true);

        mBallSize = resources.getDimensionPixelSize(R.dimen.ball_size);
        mBallDistance = mBallSize + mBallSize/4;

        mComparingBall = BitmapUtils.loadResizedBitmap(resources,
                R.drawable.ballcomparing, mBallSize, mBallSize);

        mIdleBall = BitmapUtils.loadResizedBitmap(resources,
                R.drawable.bluebubble, mBallSize, mBallSize);

        mFinishedBall = BitmapUtils.loadResizedBitmap(resources,
                R.drawable.ballfinished, mBallSize, mBallSize);
    }

    protected void initBalls(int[] elements) {
        int xT = 0;
        Resources resources = getResources();

        int width = resources.getDisplayMetrics().widthPixels / 2;
        float scale = resources.getDimensionPixelSize(R.dimen.text_ball_size);

        mBalls = new Ball[elements.length];
        for (int i = 0; i < elements.length; i++) {
            if (i == 0)
                xT += width - mBalls.length * (mBallSize + mBallSize / 4) / 2 + 10;
            else
                xT += mBallDistance;

            mBalls[i] = new Ball(xT, getMeasuredHeight() / 2 - mBallSize, String.valueOf(elements[i]), mIdleBall, scale);
        }
        drawPanel();
    }

    protected void drawPanel() {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            drawBalls(canvas);
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    protected void drawBalls(Canvas canvas) {
        for (int i = 0; i < mBalls.length; i++) {
            mBalls[i].drawBall(canvas, mBallStyle);
            mBalls[i].drawTextBall(canvas, mTxtBallStyle);
        }
    }

    protected boolean isSurfaceValid() {
        return mSurfaceHolder.getSurface().isValid();
    }
}
