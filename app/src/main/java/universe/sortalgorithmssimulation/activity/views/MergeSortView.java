package universe.sortalgorithmssimulation.activity.views;

import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;

import java.util.ArrayList;

import universe.sortalgorithmssimulation.activity.presenters.MergeSortPresenter;
import universe.sortalgorithmssimulation.activity.views.model.Ball;
import universe.sortalgorithmssimulation.utils.PathUtils;

/**
 * Created by Nhat on 5/27/2017.
 */

public class MergeSortView extends BaseSortView implements MergeSortPresenter.View {

    private ArrayList<Ball> mergedBalls;

    public MergeSortView(Context context) {
        super(context);
    }

    @Override
    public void highlightComparingBall(int index, boolean active) {
        mBalls[index].state = active ? State.COMPARING : State.IDLE;
        // mBalls[index].bitmap = active ? mComparingBall : mIdleBall;
        drawPanel();
    }

    @Override
    public void moveDownBallsInRange(int start, int end) {
        int step = (int) (mBallDistance * 0.05);
        int destinationY = (int) (mBalls[start].y + mBallDistance);
        while (mBalls[start].y < destinationY) {
            for (int i = start; i <= end; i++) {
                mBalls[i].y += step;
            }
            drawPanel();
        }
    }

    @Override
    public void createMergedBalls(int size) {
        if(mergedBalls == null) {
            mergedBalls = new ArrayList<>(size);
        }
    }

    @Override
    public void updateMergedBalls(int indexResult, int resultOffset, int index) {
        float[] position = new float[2];
        float startX = mBalls[index].x;
        float startY = mBalls[index].y;
        float endX, endY;
        if (mergedBalls.isEmpty()) {
            endX = mBalls[resultOffset].x;
            endY = mBalls[resultOffset].y - mBallDistance;
        } else {
            endX = mergedBalls.get(indexResult-1).x + mBallDistance;
            endY = mergedBalls.get(indexResult-1).y;
        }
        Path path = PathUtils.createPathTo(startX, startY, endX, endY);

        // Calculate positions on bezier curve to move mBalls
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float pathLength = pathMeasure.getLength();

        int step = (int) (pathLength * 0.1);
        float distance = 0;
        boolean balancePos = false;

        while (distance <= pathLength) {
            pathMeasure.getPosTan(distance, position, null);
            mBalls[index].x = position[0];
            mBalls[index].y = position[1];
            drawPanel();

            distance += step;
            if (distance > pathLength && !balancePos) {
                distance = pathLength;
                balancePos = true;
            }
        }
        mergedBalls.add(mBalls[index]);
    }

    @Override
    public void finishMerge(int left) {
        for (int i = 0; i < mergedBalls.size(); i++) {
            mBalls[left + i] = mergedBalls.get(i);
        }
        mergedBalls.clear();
    }

    @Override
    public void showBalls(int[] elements) {
        initBalls(elements);
    }

    @Override
    public void showFinished(int[] elements) {
        for (Ball ball : mBalls) {
            // ball.bitmap = mFinishedBall;
            ball.state = State.FINISHED;
        }
        drawPanel();
    }
}
