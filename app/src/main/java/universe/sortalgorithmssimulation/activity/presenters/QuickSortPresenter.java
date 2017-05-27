package universe.sortalgorithmssimulation.activity.presenters;

import universe.sortalgorithmssimulation.activity.views.BaseSortView;
import universe.sortalgorithmssimulation.sorting_algorithms.BaseSortAlgorithm;
import universe.sortalgorithmssimulation.sorting_algorithms.QuickSort;

/**
 * Created by Nhat on 5/26/2017.
 */

public class QuickSortPresenter extends BaseSortPresenter implements QuickSort.Callback {

    private QuickSort mQuickSort;
    private QuickSortPresenter.View mQuickSortView;

    public QuickSortPresenter(BaseSortAlgorithm sortAlogrithm, int[] elements, MainView mainView, BaseSortView quickSortView) {
        super(sortAlogrithm, elements, mainView);
        mQuickSort = (QuickSort) sortAlogrithm;
        mQuickSort.setCallback(this);
        mQuickSortView = (View) quickSortView;
    }

    @Override
    public void onPreExecute(int[] elements) {
        mQuickSortView.showBalls(elements);
        firstTimePause();
    }

    @Override
    public void onFinished(int[] sortedElements) {
        mMainView.toggleFinished();
        mQuickSortView.showFinished(sortedElements);
    }

    @Override
    public void onCompare(int index, boolean enable) {
        pauseWhenNeeded();
        mQuickSortView.highlightComparingBall(index, enable);
        delay();
    }

    @Override
    public void onSwap(int indexGreater, int indexLesser) {
        pauseWhenNeeded();
        mQuickSortView.switchPairOfBalls(indexGreater, indexLesser);
        delay();
    }

    public interface View extends BaseView {
        void highlightComparingBall(int index, boolean active);

        void switchPairOfBalls(int indexGreater, int indexLesser);
    }
}
