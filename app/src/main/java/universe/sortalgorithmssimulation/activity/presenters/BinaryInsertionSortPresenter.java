package universe.sortalgorithmssimulation.activity.presenters;

import universe.sortalgorithmssimulation.activity.views.BaseSortView;
import universe.sortalgorithmssimulation.sorting_algorithms.BaseSortAlgorithm;
import universe.sortalgorithmssimulation.sorting_algorithms.BinaryInsertionSort;

/**
 * Created by Nhat on 5/26/2017.
 */

public class BinaryInsertionSortPresenter extends BaseSortPresenter implements BinaryInsertionSort.Callback {

    private BinaryInsertionSort mBinaryInsertionSort;
    private BinaryInsertionSortPresenter.View mBinaryInsertionSortView;

    public BinaryInsertionSortPresenter(BaseSortAlgorithm sortAlogrithm, int[] elements,
                                  MainView mainView, BaseSortView binaryInsertionSortView) {
        super(sortAlogrithm, elements, mainView);
        mBinaryInsertionSort = (BinaryInsertionSort) sortAlogrithm;
        mBinaryInsertionSort.setCallback(this);
        mBinaryInsertionSortView = (View) binaryInsertionSortView;
    }

    @Override
    public void onPreExecute(int[] elements) {
        mBinaryInsertionSortView.showBalls(elements);
        firstTimePause();
    }

    @Override
    public void onFinished(int[] sortedElements) {
        mMainView.toggleFinished();
        mBinaryInsertionSortView.showFinished(sortedElements);
    }

    @Override
    public void onSelectedElement(int index) {
        pauseWhenNeeded();
        mBinaryInsertionSortView.moveSelectedBall(index);
        delay();
    }

    @Override
    public void onCompare(int index, boolean enable) {
        pauseWhenNeeded();
        mBinaryInsertionSortView.highlightComparingBall(index, enable);
        delayNormal();
    }

    @Override
    public void onShiftRightElementByOne(int index) {
        pauseWhenNeeded();
        mBinaryInsertionSortView.moveGreaterBallToRight(index);
        delay();
    }

    @Override
    public void onShiftLeftElement(int index, int leftPos) {
        pauseWhenNeeded();
        mBinaryInsertionSortView.moveLesserBallToLeft(index, leftPos);
        mBinaryInsertionSortView.highlightComparingBall(leftPos, false);
        delay();
    }

    public interface View extends BaseView {
        void highlightComparingBall(int index, boolean active);

        void moveSelectedBall(int index);

        void moveGreaterBallToRight(int index);

        void moveLesserBallToLeft(int index, int leftPos);
    }
}
