package universe.sortalgorithmssimulation.activity.views;

import android.content.Context;

import universe.sortalgorithmssimulation.activity.presenters.BinaryInsertionSortPresenter;
import universe.sortalgorithmssimulation.activity.views.model.Ball;

/**
 * Created by Nhat on 5/26/2017.
 */

public class BinaryInsertionSortView extends BaseSortView implements BinaryInsertionSortPresenter.View {

    public BinaryInsertionSortView(Context context) {
        super(context);
    }

    @Override
    public void showBalls(int[] elements) {
        initBalls(elements);
    }

    @Override
    public void showFinished(int[] elements) {
        for (Ball ball : mBalls) {
            ball.bitmap = mFinishedBall;
        }
        drawPanel();
    }

    @Override
    public void highlightComparingBall(int index, boolean active) {
        mBalls[index].bitmap = active ? mComparingBall : mIdleBall;
        drawPanel();
    }

    @Override
    public void moveSelectedBall(int index) {
        mBalls[index].bitmap = mFinishedBall;

        float y = mBalls[index].y - mBallDistance;
        int step = (int) ((mBalls[index].y - y) * 0.1);

        while(mBalls[index].y >= y) {
            mBalls[index].y -= step;
            drawPanel();
        }
    }

    @Override
    public void moveGreaterBallToRight(int index) {
        float rightPosX = mBalls[index].x + mBallDistance;
        int step = (int) ((rightPosX - mBalls[index].x) * 0.1);

        while (mBalls[index].x < rightPosX) {
            mBalls[index].x += step;
            if(mBalls[index].x > rightPosX) {
                mBalls[index].x = rightPosX;
            }
            drawPanel();
        }
    }

    @Override
    public void moveLesserBallToLeft(final int index, int leftPos) {
        final float leftPosX = mBalls[index].x - mBallDistance * (index - leftPos);
        int step = (int) ((mBalls[index].x - leftPosX) * 0.1);
        while (mBalls[index].x > leftPosX) {
            mBalls[index].x -= step;
            if(mBalls[index].x < leftPosX) {
                mBalls[index].x = leftPosX;
            }
            drawPanel();
        }

        float y = mBalls[index].y + mBallDistance;
        step = (int) ((y - mBalls[index].y) * 0.1);
        while(mBalls[index].y <= y) {
            mBalls[index].y += step;
            drawPanel();
        }

        Ball temp = mBalls[index];
        for (int i = index; i > leftPos ; i--) {
            mBalls[i] = mBalls[i-1];
        }
        mBalls[leftPos] = temp;
    }
}
