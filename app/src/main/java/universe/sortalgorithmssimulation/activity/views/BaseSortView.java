package universe.sortalgorithmssimulation.activity.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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
    protected Ball mBalls[];
    protected Bitmap mIdleBall, mComparingBall, mFinishedBall;

    protected int mBallSize;
    protected int mBallDistance;
    private Paint mTxtBallStyle;
    private Paint mBallStyle = new Paint();

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
        mBallStyle.setDither(true);
        mBallStyle.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER ));

        mBallSize = resources.getDimensionPixelSize(R.dimen.ball_size);
        mBallDistance = mBallSize + mBallSize / 4;

        mIdleBall = BitmapUtils.loadResizedBitmap(resources,
                R.drawable.ball_normal, mBallSize, mBallSize);
        mComparingBall = BitmapUtils.loadResizedBitmap(resources,
                R.drawable.ball_comparing, mBallSize, mBallSize);
        mFinishedBall = BitmapUtils.loadResizedBitmap(resources,
                R.drawable.ball_finished, mBallSize, mBallSize);
    }

    protected void initBalls(int[] elements) {
        if (elements != null) {
            Resources resources = getResources();
            int width = resources.getDisplayMetrics().widthPixels / 2;
            float scale = resources.getDimensionPixelSize(R.dimen.text_ball_size);
            int length = elements.length;

            mBalls = new Ball[length];
            int xT = 0;
            for (int i = 0; i < length; i++) {
                if (mBalls[i] == null) {
                    if (i == 0)
                        xT += width - mBalls.length * mBallDistance / 2 + 10;
                    else xT += mBallDistance;
                    mBalls[i] = new Ball(xT, getMeasuredHeight() / 2 - mBallSize
                            , mIdleBall, mBallSize
                            , String.valueOf(elements[i]), scale);
                } else {
                    mBalls[i].text = String.valueOf(elements[i]);
                }
            }
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
        for (Ball ball : mBalls) {
            ball.drawBall(canvas, mBallStyle);
            ball.drawTextBall(canvas, mTxtBallStyle);
        }
    }
}
