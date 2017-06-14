package universe.sortalgorithmssimulation.activity.presenters;

import timber.log.Timber;
import universe.sortalgorithmssimulation.activity.views.BubbleSortView;
import universe.sortalgorithmssimulation.sorting_algorithms.BubbleSort;

/**
 * Created by Nhat on 5/22/2017.
 */

public class BubbleSortPresenter extends BaseSortPresenter<BubbleSort, BubbleSortView>
                                                        implements BubbleSort.Callback {
    public BubbleSortPresenter(BubbleSort bubbleSort,
                               MainView mainView,
                               BubbleSortView bubbleSortView) {
        super(bubbleSort, mainView, bubbleSortView);
        mSortAlogrithm.setCallback(this);
    }

    @Override
    public void onPreExecute(int[] elements) {
        handlePreExecute(elements);
    }

    @Override
    public void onComparing(int index1, int index2, boolean enable) {
        pauseWhenNeeded();
        if (mSortView != null) {
            mSortView.highlightComparingPairOfBalls(index1, index2, enable);
            delayShort();
        }
    }

    @Override
    public void onSwap(int indexGreater, int indexLesser) {
        pauseWhenNeeded();
        if (mSortView != null) {
            mSortView.switchPairOfBalls(indexGreater, indexLesser);
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

    @Override
    public void onFinished(int[] sortedElements) {
        handleFinished(sortedElements);
    }

    public interface View extends BaseSortPresenter.BaseView {

        /**
         * @param first  first ball need to be highlighted
         * @param second second ball need to be highlighted
         * @param active highlight balls if T otherwise F
         */
        void highlightComparingPairOfBalls(int first, int second, boolean active);

        void highlightSortedBall(int index);

        void switchPairOfBalls(int indexGreater, int indexLesser);
    }
}
