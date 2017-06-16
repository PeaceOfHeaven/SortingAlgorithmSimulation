package universe.sortalgorithmssimulation.activity.presenters;

import universe.sortalgorithmssimulation.activity.views.InsertionSortView;
import universe.sortalgorithmssimulation.sorting_algorithms.InsertionSort;

/**
 * Created by Nhat on 5/23/2017.
 */

public class InsertionSortPresenter extends BaseSortPresenter<InsertionSort, InsertionSortView>
                                    implements InsertionSort.Callback {

    public InsertionSortPresenter(InsertionSort insertionSort, MainView mainView,
                                  InsertionSortView insertionSortView) {
        super(insertionSort, mainView, insertionSortView);
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
    public void onSelectedElement(int index) {
        pauseWhenNeeded();
        if (mSortView != null) {
            mSortView.moveSelectedBall(index);
            delayShort();
        }
    }

    @Override
    public void onCompare(int index, boolean enable) {
        pauseWhenNeeded();
        if (mSortView != null) {
            mSortView.highlightComparingBall(index, enable);
            delayNormal();
        }
    }

    @Override
    public void onShiftRightElementByOne(int index) {
        pauseWhenNeeded();
        if (mSortView != null) {
            mSortView.moveGreaterBallToRight(index);
            delayShort();
        }
    }

    @Override
    public void onShiftLeftElement(int index, int leftPos) {
        pauseWhenNeeded();
        if (mSortView != null) {
            mSortView.moveLesserBallToLeft(index, leftPos);
            mSortView.highlightComparingBall(leftPos, false);
            delayShort();
        }
    }

    public interface View extends BaseSortPresenter.BaseView {
        void highlightComparingBall(int index, boolean active);

        void moveSelectedBall(int index);

        void moveGreaterBallToRight(int index);

        void moveLesserBallToLeft(int index, int leftPos);
    }
}
