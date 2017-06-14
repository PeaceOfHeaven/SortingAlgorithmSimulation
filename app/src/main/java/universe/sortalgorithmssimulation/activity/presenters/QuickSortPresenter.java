package universe.sortalgorithmssimulation.activity.presenters;

import universe.sortalgorithmssimulation.activity.views.QuickSortView;
import universe.sortalgorithmssimulation.sorting_algorithms.QuickSort;

/**
 * Created by Nhat on 5/26/2017.
 */

public class QuickSortPresenter extends BaseSortPresenter<QuickSort, QuickSortView>
                                implements QuickSort.Callback {

    public QuickSortPresenter(QuickSort sortAlogrithm, MainView mainView,
                              QuickSortView quickSortView) {
        super(sortAlogrithm, mainView, quickSortView);
        mSortAlogrithm.setCallback(this);
    }

    @Override
    public void onPreExecute(int[] elements) {
        handlePreExecute(elements);
    }

    @Override
    public void onFinished(int[] sortedElements) {
        handleFinished(sortedElements);
    }

    @Override
    public void onCompare(int index, boolean enable) {
        pauseWhenNeeded();
        mSortView.highlightComparingBall(index, enable);
        delayShort();
    }

    @Override
    public void onSwap(int indexGreater, int indexLesser) {
        pauseWhenNeeded();
        mSortView.switchPairOfBalls(indexGreater, indexLesser);
        delayShort();
    }

    public interface View extends BaseSortPresenter.BaseView {
        void highlightComparingBall(int index, boolean active);

        void switchPairOfBalls(int indexGreater, int indexLesser);
    }
}
