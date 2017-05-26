package universe.sortalgorithmssimulation.activity.presenters;

import universe.sortalgorithmssimulation.activity.views.BaseSortView;
import universe.sortalgorithmssimulation.sorting_algorithms.BaseSortAlgorithm;
import universe.sortalgorithmssimulation.sorting_algorithms.SelectionSort;

/**
 * Created by Nhat on 5/25/2017.
 */

public class SelectionSortPresenter extends BaseSortPresenter implements SelectionSort.Callback{

    private SelectionSort mSelectionSort;
    private SelectionSortPresenter.View mSelectionSortView;

    public SelectionSortPresenter(BaseSortAlgorithm sortAlogrithm, int[] elements, MainView mainView
                    , BaseSortView selectionSortView) {
        super(sortAlogrithm, elements, mainView);
        mSelectionSort = (SelectionSort) sortAlogrithm;
        mSelectionSort.setCallback(this);
        mSelectionSortView = (SelectionSortPresenter.View) selectionSortView;
    }

    @Override
    public void onPreExecute(int[] elements) {
        mSelectionSortView.showBalls(elements);
        firstTimePause();
    }

    @Override
    public void onFinished(int[] sortedElements) {
        mMainView.toggleFinished();
        mSelectionSortView.showFinished(sortedElements);
        delay();
    }

    @Override
    public void onCompare(int index1, boolean enable) {
        pauseWhenNeeded();
        mSelectionSortView.highlightComparingBall(index1, enable);
        delay();
    }

    @Override
    public void onMinChange(int newIndex, int oldIndex) {
        pauseWhenNeeded();
        mSelectionSortView.highlightComparingBall(newIndex, true);
        if(oldIndex != -1) {
            mSelectionSortView.highlightComparingBall(oldIndex, false);
        }
        delay();
    }

    @Override
    public void onSwap(int index, int minIndex) {
        pauseWhenNeeded();
        mSelectionSortView.switchPairOfBalls(index, minIndex);
        delay();
    }

    @Override
    public void onSortedBall(int index) {
        pauseWhenNeeded();
        mSelectionSortView.highlightSortedBall(index);
        delay();
    }

    public interface View extends BaseView {
        void highlightComparingBall(int index, boolean active);

        void highlightSortedBall(int index);

        void switchPairOfBalls(int indexGreater, int indexLesser);
    }
}
