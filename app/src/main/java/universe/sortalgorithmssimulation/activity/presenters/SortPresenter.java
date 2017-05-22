package universe.sortalgorithmssimulation.activity.presenters;

import java.util.Arrays;

import universe.sortalgorithmssimulation.sorting_algorithms.BubbleSort2;

/**
 * Created by Nhat on 4/21/2017.
 */

public class SortPresenter implements BubbleSort2.Callback {

    private BubbleSort2 mBubbleSort;

    private SortView mSortView;

    private MainView mMainView;

    private boolean isPaused;

    private Object pauseLock = new Object();

    private Thread mSortThread;

    private boolean isRunning;

    private boolean fistTime = true;

    private int[] mOriginElements;

    private static final int DELAY_TIME = 300;

    public SortPresenter(BubbleSort2 bubbleSort, MainView mainView,
                         SortView sortView, int[] elements) {
        mBubbleSort = bubbleSort;
        mBubbleSort.setCallback(this);
        mSortView = sortView;
        mMainView = mainView;
        mOriginElements = elements;
    }

    public void start()  {
        if (!isRunning) {
            mBubbleSort.setElements(Arrays.copyOf(mOriginElements, mOriginElements.length));
            mSortThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    mBubbleSort.execute();
                }
            });
            mSortThread.start();
            isRunning = true;

            mMainView.togglePauseFab(false);
        }
    }

    public void setElements(int[] elements) {
        mOriginElements = elements;
        mBubbleSort.setElements(elements);
    }

    public void pause() {
        isPaused = true;
        mMainView.togglePauseFab(true);
    }

    public void resume() {
        isPaused = false;
        mMainView.togglePauseFab(false);
        pauseWhenNeeded();
    }

    public void stop() {
        mBubbleSort.setRunning(false);
        mBubbleSort.setCallback(null);
        mSortThread = null;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void onPreExecute(int[] elements)  {
        pauseWhenNeeded();
        mSortView.showBalls(elements);
        if(fistTime) {
            pause();
            fistTime = false;
        }
    }

    @Override
    public void onComparing(int index1, int index2, boolean enable) {
        pauseWhenNeeded();
        mSortView.highlightComparingPairOfBalls(index1, index2, enable);
        delay();
    }

    @Override
    public void onSwap(int indexGreater, int indexLesser) {
        pauseWhenNeeded();
        mSortView.switchPairOfBalls(indexGreater, indexLesser);
        delay();
    }

    @Override
    public void onSortedBall(int index) {
        pauseWhenNeeded();
        mSortView.highlightSortedBalls(index);
        delay();
    }

    @Override
    public void onFinished() {
        isRunning = false;
        mMainView.toggleFinished();
        mSortView.displayFinished();
    }

    private void pauseWhenNeeded() {
        if (isPaused) {
            synchronized (pauseLock) {
                while (isPaused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        } else {
            synchronized (pauseLock) {
                pauseLock.notifyAll();
            }
        }
    }

    private void delay() {
        try {
            Thread.sleep(DELAY_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public interface SortView {

        void showBalls(int[] elements);

        /**
         *
         * @param first first ball need to be highlighted
         * @param second second ball need to be highlighted
         * @param active highlight balls if T otherwise F
         */
        void highlightComparingPairOfBalls(int first, int second, boolean active);

        void switchPairOfBalls(int indexGreater, int indexLesser);

        void highlightSortedBalls(int index);

        void displayFinished();
    }

    public interface MainView {

        void togglePauseFab(boolean active);

        void toggleFinished();
    }
}
