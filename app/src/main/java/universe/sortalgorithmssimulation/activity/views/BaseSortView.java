package universe.sortalgorithmssimulation.activity.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.util.SparseArrayCompat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import universe.sortalgorithmssimulation.R;
import universe.sortalgorithmssimulation.activity.views.model.Ball;
import universe.sortalgorithmssimulation.utils.BitmapUtils;

import static universe.sortalgorithmssimulation.activity.views.BaseSortView.State.IDLE;
import static universe.sortalgorithmssimulation.activity.views.BaseSortView.State.COMPARING;
import static universe.sortalgorithmssimulation.activity.views.BaseSortView.State.FINISHED;

/**
 * Created by Nhat on 4/7/2017.
 */

public abstract class BaseSortView extends SurfaceView {
    private static final int MAX_STATES = 3;
    protected SurfaceHolder mSurfaceHolder;
    protected Ball mBalls[];
    // protected Bitmap mIdleBall, mComparingBall, mFinishedBall;
    private final SparseArrayCompat<Bitmap> mBitmapStates =
                            new SparseArrayCompat<>(MAX_STATES);

    protected int mBallSize;
    protected int mBallDistance;
    private Paint mTxtBallStyle;
    private Paint mBallStyle = new Paint(Paint.FILTER_BITMAP_FLAG);

    public interface State {
        int IDLE = 1;
        int COMPARING = 2;
        int FINISHED = 3;
    }

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
        mBallDistance = mBallSize + mBallSize / 4;

        /*mIdleBall = BitmapUtils.loadResizedBitmap(resources,
                R.drawable.ball_normal, mBallSize, mBallSize);
        mComparingBall = BitmapUtils.loadResizedBitmap(resources,
                R.drawable.ball_comparing, mBallSize, mBallSize);
        mFinishedBall = BitmapUtils.loadResizedBitmap(resources,
                R.drawable.ball_finished, mBallSize, mBallSize);*/
        mBitmapStates.put(IDLE, BitmapUtils.loadResizedBitmap(resources,
                R.drawable.ball_normal, mBallSize, mBallSize));
        mBitmapStates.put(COMPARING, BitmapUtils.loadResizedBitmap(resources,
                R.drawable.ball_comparing, mBallSize, mBallSize));
        mBitmapStates.put(FINISHED, BitmapUtils.loadResizedBitmap(resources,
                R.drawable.ball_finished, mBallSize, mBallSize));
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
                    /*mBalls[i] = new Ball(xT, getMeasuredHeight() / 2 - mBallSize
                            , mIdleBall, mBallSize
                            , String.valueOf(elements[i]), scale);*/
                    mBalls[i] = new Ball(xT, getMeasuredHeight() / 2 - mBallSize
                            , mBallSize
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
            ball.drawBall(canvas, mBitmapStates.get(ball.state), mBallStyle);
            // ball.drawBall(canvas, mBallStyle);
            ball.drawTextBall(canvas, mTxtBallStyle);
        }
    }
}
