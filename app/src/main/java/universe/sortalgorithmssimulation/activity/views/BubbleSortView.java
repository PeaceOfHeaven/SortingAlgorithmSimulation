package universe.sortalgorithmssimulation.activity.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.PathMeasure;

import universe.sortalgorithmssimulation.activity.presenters.BubbleSortPresenter;
import universe.sortalgorithmssimulation.activity.views.model.Ball;
import universe.sortalgorithmssimulation.utils.PathUtils;

/**
 * Created by Nhat on 5/22/2017.
 */

public class BubbleSortView extends BaseSortView implements BubbleSortPresenter.View {

    public BubbleSortView(Context context) {
        super(context);
    }

    @Override
    public void showBalls(int[] elements) {
        initBalls(elements);
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
        int ballHalfSize = mBallSize / 2;
        float[] position = new float[2];

        float centerGreaterBallX = mBalls[indexGreater].x + ballHalfSize;
        float centerGreaterBallY = mBalls[indexGreater].y + ballHalfSize;
        float centerLesserBallX = mBalls[indexLesser].x + ballHalfSize;
        float centerLesserBallY = mBalls[indexLesser].y + ballHalfSize;

        Path path = PathUtils.createBezierCurve(centerGreaterBallX, centerGreaterBallY
                , centerGreaterBallX + (mBalls[indexLesser].x - mBalls[indexGreater].x) / 2
                , centerGreaterBallY + mBallSize + ballHalfSize
                , centerLesserBallX
                , centerLesserBallY);

        // Calculate positions on bezier curve to move mBalls
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float pathLength = pathMeasure.getLength();

        int step = (int) (pathLength * 0.1);
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
        // Swap references
        Ball temp = mBalls[indexGreater];
        mBalls[indexGreater] = mBalls[indexLesser];
        mBalls[indexLesser] = temp;
    }

    @Override
    public void highlightSortedBall(int index) {
        mBalls[index].switchBall(mFinishedBall);
        drawPanel();
    }

    @Override
    public void showFinished(int[] elements) {

    }
}
