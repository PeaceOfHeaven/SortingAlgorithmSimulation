package universe.sortalgorithmssimulation.activity.presenters;

import universe.sortalgorithmssimulation.activity.views.MergeSortView;
import universe.sortalgorithmssimulation.sorting_algorithms.MergeSort;

/**
 * Created by Nhat on 5/27/2017.
 */

public class MergeSortPresenter extends BaseSortPresenter<MergeSort, MergeSortView>
                                implements MergeSort.Callback {

    public MergeSortPresenter(MergeSort mergeSort, MainView mainView,
                              MergeSortView mergeSortView) {
        super(mergeSort, mainView, mergeSortView);
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
    public void onLeftMid(int left, int mid, boolean start) {
        if(mSortView != null) {
            pauseWhenNeeded();
            mSortView.moveDownBallsInRange(left, mid);
            delayShort();
        }
    }

    @Override
    public void onMidRight(int mid, int right, boolean start) {
        if(mSortView != null) {
            pauseWhenNeeded();
            mSortView.moveDownBallsInRange(mid, right);
            delayShort();
        }
    }

    @Override
    public void onStartMerge(int sizeMergedResult) {
        if(mSortView != null) {
            pauseWhenNeeded();
            mSortView.createMergedBalls(sizeMergedResult);
            delayShort();
        }
    }

    @Override
    public void onUpdateMergeResult(int indexResult, int leftOffset, int indexLesser) {
        if(mSortView != null) {
            pauseWhenNeeded();
            mSortView.updateMergedBalls(indexResult, leftOffset, indexLesser);
        }
    }

    @Override
    public void onMergeFinished(int left) {
        if(mSortView != null) {
            pauseWhenNeeded();
            mSortView.finishMerge(left);
            delayShort();
        }
    }

    @Override
    public void onCompare(int first, int second, boolean enable) {
        if(mSortView != null) {
            mSortView.highlightComparingBall(first, enable);
            mSortView.highlightComparingBall(second, enable);
            delayNormal();
        }
    }

    public interface View extends BaseSortPresenter.BaseView {
        void highlightComparingBall(int index, boolean active);

        void moveDownBallsInRange(int start, int end);

        void createMergedBalls(int size);

        void updateMergedBalls(int indexResult, int resultOffset, int index);

        void finishMerge(int left);
    }
}
