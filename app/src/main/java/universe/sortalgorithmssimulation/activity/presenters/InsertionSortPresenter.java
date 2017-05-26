package universe.sortalgorithmssimulation.activity.presenters;

import universe.sortalgorithmssimulation.activity.views.BaseSortView;
import universe.sortalgorithmssimulation.sorting_algorithms.BaseSortAlgorithm;
import universe.sortalgorithmssimulation.sorting_algorithms.InsertionSort;

/**
 * Created by Nhat on 5/23/2017.
 */

public class InsertionSortPresenter extends BaseSortPresenter implements InsertionSort.Callback {

    private InsertionSort mInsertionSort;
    private InsertionSortPresenter.View mInsertionSortView;

    public InsertionSortPresenter(BaseSortAlgorithm sortAlogrithm, int[] elements,
                                  MainView mainView, BaseSortView insertionSortView) {
        super(sortAlogrithm, elements, mainView);
        mInsertionSort = (InsertionSort) sortAlogrithm;
        mInsertionSort.setCallback(this);
        mInsertionSortView = (View) insertionSortView;
    }

    @Override
    public void onPreExecute(int[] elements) {
        mInsertionSortView.showBalls(elements);
        firstTimePause();
    }

    @Override
    public void onFinished(int[] sortedElements) {
        mMainView.toggleFinished();
        mInsertionSortView.showFinished(sortedElements);
    }

    @Override
    public void onSelectedBall(int index) {
        pauseWhenNeeded();
        mInsertionSortView.highlightComparingBall(index, true);
        mInsertionSortView.moveSelectedBall(index);
        delay();
    }

    @Override
    public void onCompare(int index, boolean enable) {
        pauseWhenNeeded();
        mInsertionSortView.highlightComparingBall(index, enable);
        delay();
    }

    @Override
    public void onShiftRightElementByOne(int index) {
        pauseWhenNeeded();
        mInsertionSortView.moveGreaterBallToRight(index);
        delay();
    }

    @Override
    public void onShiftLeftElement(int index, int leftPos) {
        pauseWhenNeeded();
        mInsertionSortView.moveLesserBallToLeft(index, leftPos);
        mInsertionSortView.highlightComparingBall(leftPos, false);
        delay();
    }

    public interface View extends BaseView {
        void highlightComparingBall(int index, boolean active);

        void moveSelectedBall(int index);

        void moveGreaterBallToRight(int index);

        void moveLesserBallToLeft(int index, int leftPos);
    }
}
