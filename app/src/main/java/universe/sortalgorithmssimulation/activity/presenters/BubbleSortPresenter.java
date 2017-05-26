package universe.sortalgorithmssimulation.activity.presenters;

import universe.sortalgorithmssimulation.activity.views.BaseSortView;
import universe.sortalgorithmssimulation.activity.views.BubbleSortView;
import universe.sortalgorithmssimulation.sorting_algorithms.BaseSortAlgorithm;
import universe.sortalgorithmssimulation.sorting_algorithms.BubbleSort;

/**
 * Created by Nhat on 5/22/2017.
 */

public class BubbleSortPresenter extends BaseSortPresenter implements BubbleSort.Callback {

    private BubbleSort mBubbleSort;
    private BubbleSortView mBubbleSortView;

    public BubbleSortPresenter(BaseSortAlgorithm sortAlogrithm, int[] elements
                                                , MainView mainView, BaseSortView bubbleSortView) {
        super(sortAlogrithm, elements, mainView);
        mBubbleSort = (BubbleSort) sortAlogrithm;
        mBubbleSort.setCallback(this);
        mBubbleSortView = (BubbleSortView) bubbleSortView;
    }

    @Override
    public void onPreExecute(int[] elements)  {
        mBubbleSortView.showBalls(elements);
        firstTimePause();
    }

    @Override
    public void onComparing(int index1, int index2, boolean enable) {
        pauseWhenNeeded();
        mBubbleSortView.highlightComparingPairOfBalls(index1, index2, enable);
        delay();
    }

    @Override
    public void onSwap(int indexGreater, int indexLesser) {
        pauseWhenNeeded();
        mBubbleSortView.switchPairOfBalls(indexGreater, indexLesser);
        delay();
    }

    @Override
    public void onSortedBall(int index) {
        pauseWhenNeeded();
        mBubbleSortView.highlightSortedBall(index);
        delay();
    }

    @Override
    public void onFinished(int[] sortedElements) {
        isRunning = false;
        mMainView.toggleFinished();
        mBubbleSortView.showFinished(sortedElements);
    }

    public interface View extends BaseView {

        /**
         *
         * @param first first ball need to be highlighted
         * @param second second ball need to be highlighted
         * @param active highlight balls if T otherwise F
         */
        void highlightComparingPairOfBalls(int first, int second, boolean active);

        void highlightSortedBall(int index);

        void switchPairOfBalls(int indexGreater, int indexLesser);
    }
}
