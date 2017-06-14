package universe.sortalgorithmssimulation.activity.presenters;

import universe.sortalgorithmssimulation.activity.views.BinaryInsertionSortView;
import universe.sortalgorithmssimulation.sorting_algorithms.BinaryInsertionSort;

/**
 * Created by Nhat on 5/26/2017.
 */

public class BinaryInsertionSortPresenter extends BaseSortPresenter<BinaryInsertionSort, BinaryInsertionSortView>
                        implements BinaryInsertionSort.Callback {

    public BinaryInsertionSortPresenter(BinaryInsertionSort binaryInsertionSort,
                                  MainView mainView, BinaryInsertionSortView binaryInsertionSortView) {
        super(binaryInsertionSort, mainView, binaryInsertionSortView);
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
        if(mSortView != null) {
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
