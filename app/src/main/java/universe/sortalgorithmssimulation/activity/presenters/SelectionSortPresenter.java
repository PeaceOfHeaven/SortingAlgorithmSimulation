package universe.sortalgorithmssimulation.activity.presenters;

import universe.sortalgorithmssimulation.activity.views.SelectionSortView;
import universe.sortalgorithmssimulation.sorting_algorithms.SelectionSort;

/**
 * Created by Nhat on 5/25/2017.
 */

public class SelectionSortPresenter extends BaseSortPresenter<SelectionSort, SelectionSortView>
                                    implements SelectionSort.Callback{

    public SelectionSortPresenter(SelectionSort sortAlogrithm, MainView mainView
                    , SelectionSortView selectionSortView) {
        super(sortAlogrithm, mainView, selectionSortView);
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
    public void onCompare(int index1, boolean enable) {
        pauseWhenNeeded();
        if (mSortView != null) {
            mSortView.highlightComparingBall(index1, enable);
            delayShort();
        }
    }

    @Override
    public void onMinChange(int newIndex, int oldIndex) {
        pauseWhenNeeded();
        if (mSortView != null) {
            mSortView.highlightComparingBall(newIndex, true);
            if (oldIndex != -1) {
                mSortView.highlightComparingBall(oldIndex, false);
            }
            delayShort();
        }
    }

    @Override
    public void onSwap(int index, int minIndex) {
        pauseWhenNeeded();
        if (mSortView != null) {
            mSortView.switchPairOfBalls(index, minIndex);
            delayShort();
        }
    }

    @Override
    public void onSortedBall(int index) {
        pauseWhenNeeded();
        if (mSortView != null) {
            mSortView.highlightSortedBall(index);
            delayShort();
        }
    }

    public interface View extends BaseSortPresenter.BaseView {
        void highlightComparingBall(int index, boolean active);

        void highlightSortedBall(int index);

        void switchPairOfBalls(int indexGreater, int indexLesser);
    }
}
