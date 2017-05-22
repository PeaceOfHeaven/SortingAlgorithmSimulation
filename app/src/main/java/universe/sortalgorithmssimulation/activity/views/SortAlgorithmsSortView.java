package universe.sortalgorithmssimulation.activity.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import universe.sortalgorithmssimulation.R;
import universe.sortalgorithmssimulation.activity.presenters.SortPresenter;
import universe.sortalgorithmssimulation.activity.views.model.Ball;
import universe.sortalgorithmssimulation.utils.BitmapUtils;

/**
 * Created by Nhat on 4/7/2017.
 */

public class SortAlgorithmsSortView extends SurfaceView implements SortPresenter.SortView {

    private SurfaceHolder mSurfaceHolder;

    private Bitmap mComparingBall;
    private Bitmap mIdleBall;
    private Bitmap mFinishedBall;

    private Ball mBalls[];

    private int mBallSize;

    private Paint mInfoStyle;

    public SortAlgorithmsSortView(Context context) {
        this(context, null);
    }

    public SortAlgorithmsSortView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortAlgorithmsSortView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSurfaceHolder = getHolder();
        Resources resources = getResources();

        float scale = resources.getDimensionPixelSize(R.dimen.text_ball_size);
        mInfoStyle = new Paint();
        mInfoStyle.setColor(Color.BLACK);
        mInfoStyle.setAntiAlias(true);
        mInfoStyle.setTextSize(scale);
        mInfoStyle.setStyle(Paint.Style.FILL);
        mInfoStyle.setTextAlign(Paint.Align.RIGHT);


        mBallSize = resources.getDimensionPixelSize(R.dimen.ball_size);
        mComparingBall = BitmapUtils.loadResizedBitmap(resources,
                R.drawable.ballcomparing, mBallSize, mBallSize);

        mIdleBall = BitmapUtils.loadResizedBitmap(resources,
                R.drawable.bluebubble, mBallSize, mBallSize);

        mFinishedBall = BitmapUtils.loadResizedBitmap(resources,
                R.drawable.ballfinished, mBallSize, mBallSize);
    }

    @Override
    public void showBalls(int[] elements) {
        int xT = 0;
        Resources resources = getResources();

        int width = resources.getDisplayMetrics().widthPixels / 2;
        float scale = resources.getDimensionPixelSize(R.dimen.text_ball_size);

        int distance = mBallSize + mBallSize / 4 + 1;

        mBalls = new Ball[elements.length];

        for (int i = 0; i < elements.length; i++) {
            if (i == 0)
                xT += width - mBalls.length * (mBallSize + mBallSize / 4) / 2 + 10;
            else
                xT += distance;

            mBalls[i] = new Ball(xT, getMeasuredHeight() / 2 - mBallSize, String.valueOf(elements[i]), mIdleBall, scale);
        }
        drawPanel();
    }

    @Override
    public void highlightComparingPairOfBalls(int first, int second, boolean active) {
        Bitmap ballBitmap = active ? mComparingBall : mIdleBall;
        mBalls[first].switchBall(ballBitmap);
        mBalls[second].switchBall(ballBitmap);
        drawPanel();
    }

    @Override
    public void switchPairOfBalls(int indexGreater, int indexLesser) {
        if (mSurfaceHolder.getSurface().isValid()) {
            int ballHalfSize = mBallSize / 2;

            float[] position = new float[2];

            float centerGreaterBallX = mBalls[indexGreater].x + ballHalfSize;
            float centerGreaterBallY = mBalls[indexGreater].y + ballHalfSize;
            float centerLesserBallX = mBalls[indexLesser].x + ballHalfSize;
            float centerLesserBallY = mBalls[indexLesser].y + ballHalfSize;

            Path path = createBezierCurve(centerGreaterBallX, centerGreaterBallY
                    , centerGreaterBallX + (mBalls[indexLesser].x - mBalls[indexGreater].x) / 2
                    , centerGreaterBallY + mBallSize + ballHalfSize
                    , centerLesserBallX
                    , centerLesserBallY);

            // Calculate positions on bezier curve to move mBalls
            PathMeasure pathMeasure = new PathMeasure(path, false);
            float pathLength = pathMeasure.getLength();

            int step = (int) (pathLength * 100 / 1000);
            float distance = 0;
            boolean balancePos = false;

            while (distance <= pathLength) {
                pathMeasure.getPosTan(distance, position, null);
                mBalls[indexGreater].x = position[0] - ballHalfSize;
                mBalls[indexGreater].y = position[1] - ballHalfSize;

                position[0] = centerLesserBallX - (position[0] - centerGreaterBallX);
                position[1] = centerLesserBallY - (position[1] - centerGreaterBallY);

                mBalls[indexLesser].x = position[0] - ballHalfSize;
                mBalls[indexLesser].y = position[1] - ballHalfSize;

                drawPanel();

                distance += step;

                if (distance > pathLength && !balancePos) {
                    distance = pathLength;
                    balancePos = true;
                }
            }
        }
        // Swap references
        Ball temp = mBalls[indexGreater];
        mBalls[indexGreater] = mBalls[indexLesser];
        mBalls[indexLesser] = temp;
    }

    @Override
    public void highlightSortedBalls(int index) {
        mBalls[index].switchBall(mFinishedBall);
        drawPanel();
    }

    @Override
    public void displayFinished() {

    }

    private void drawPanel() {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            drawBalls(canvas);
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawBalls(Canvas canvas) {
        for (int i = 0; i < mBalls.length; i++) {
            mBalls[i].drawBall(canvas);
            mBalls[i].drawTextBall(canvas, mInfoStyle);
        }
    }

    private Path createBezierCurve(float startX, float startY,
                                   float ctrlPointX, float ctrlPointY,
                                   float endX, float endY) {
        // We only need 1 path for greater mBalls and then we can calculate lesser mBalls
        // depend on greater mBalls
        Path path = new Path();
        path.moveTo(startX, startY);

        // bezier curve
        path.quadTo(ctrlPointX
                , ctrlPointY
                , endX
                , endY);
        return path;
    }
}
