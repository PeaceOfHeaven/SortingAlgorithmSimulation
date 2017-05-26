package universe.sortalgorithmssimulation.activity.presenters;

import java.util.Arrays;

import universe.sortalgorithmssimulation.sorting_algorithms.BaseSortAlgorithm;

/**
 * Created by Nhat on 4/21/2017.
 */

public abstract class BaseSortPresenter  {
    private static final int DELAY_TIME = 300;

    protected final MainView mMainView;
    protected final BaseSortAlgorithm mSortAlogrithm;
    private Object pauseLock = new Object();
    private Thread mSortThread;

    protected boolean isPaused;
    protected boolean isRunning;
    protected boolean fistTime = true;

    private int[] mOriginElements;

    public BaseSortPresenter(BaseSortAlgorithm sortAlogrithm
            , int[] elements, MainView mainView) {
        mSortAlogrithm = sortAlogrithm;
        mOriginElements = elements;
        mMainView = mainView;
    }

    public void start()  {
        if (!mSortAlogrithm.isRunning()) {
            mSortAlogrithm.setElements(Arrays.copyOf(mOriginElements, mOriginElements.length));
            mSortThread = new Thread(mSortAlogrithm);
            mSortThread.start();

            mMainView.togglePauseFab(false);
        } else if(isPaused) {
            isPaused = false;
            mMainView.togglePauseFab(false);
            synchronized (pauseLock) {
                pauseLock.notifyAll();
            }
        }
    }

    public void setElements(int[] elements) {
        mOriginElements = elements;
        mSortAlogrithm.setElements(elements);
    }

    protected void firstTimePause() {
        if(fistTime) {
            pause();
            fistTime = false;
        }
    }

    public void pause() {
        isPaused = true;
        mMainView.togglePauseFab(true);
    }

    public void stop() {
        mSortAlogrithm.setRunning(false);
        mSortAlogrithm.setCallback(null);
        mSortThread = null;
    }

    public boolean isPaused() {
        return isPaused || !mSortAlogrithm.isRunning();
    }

    protected void pauseWhenNeeded() {
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
        }
    }

    protected void delay() {
        try {
            Thread.sleep(DELAY_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public interface MainView {

        void togglePauseFab(boolean active);

        void toggleFinished();
    }

    public interface BaseView {

        void showBalls(int[] elements);

        void showFinished(int[] elements);
    }
}
