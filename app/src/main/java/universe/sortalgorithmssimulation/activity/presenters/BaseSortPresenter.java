package universe.sortalgorithmssimulation.activity.presenters;

import android.os.Handler;

import java.util.Arrays;

import universe.sortalgorithmssimulation.sorting_algorithms.BaseSortAlgorithm;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Nhat on 4/21/2017.
 */

public abstract class BaseSortPresenter<A extends BaseSortAlgorithm,
                                        V extends BaseSortPresenter.BaseView> {
    private static final int DELAY_SHORT_TIME = 200;
    private static final int DELAY_NORMAL_TIME = 400;

    private final Handler mHandler = new Handler();
    private MainView mMainView;
    protected V mSortView;
    protected final A mSortAlogrithm;
    private final Object pauseLock = new Object();
    private SortThread mSortThread;

    private boolean isPaused;
    private boolean isFinished = true;
    private boolean isPausedHome;
    private boolean isFirstTime = true;
    private int[] mOriginElements;

    public BaseSortPresenter(A sortAlgorithm,
                             MainView mainView,
                             V sortView) {
        checkNotNull(sortAlgorithm);
        checkNotNull(mainView);
        checkNotNull(sortView);

        mSortAlogrithm = sortAlgorithm;
        mMainView = mainView;
        mSortView = sortView;
    }

    public V getSView() {
        return mSortView;
    }

    public void attachViews(MainView mainView, V sortView) {
        checkNotNull(mainView);
        checkNotNull(sortView);

        mMainView = mainView;
        mSortView = sortView;
    }

    public void detachViews() {
        mMainView = null;
        mSortView = null;
    }

    public void start() {
        if (mOriginElements == null) {
            throw new IllegalStateException("No elements need to be sorted!");
        }
        if (isFinished) {
            if (!isPausedHome) {
                isFinished = false;
                mSortAlogrithm.setElements(Arrays.copyOf(mOriginElements, mOriginElements.length));
                mSortThread = new SortThread(mSortAlogrithm);
                mSortThread.start();
            } else {
                mSortView.showBalls(null);
                mMainView.toggleFinished();
            }
        } else {
            if (!isPausedHome) {
                if (isPaused) resume();
            } else {
                mSortView.showBalls(null);
                if (!isPaused) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            resume();
                        }
                    }, DELAY_NORMAL_TIME);
                }
            }
        }
        isPausedHome = false;
    }

    public void setElements(int[] elements) {
        checkNotNull(elements);
        boolean shouldPlayNow = mOriginElements == null ? false : true;
        if(isFinished || isFirstTime) {
            mOriginElements = elements;
            if(!isFinished) {
                mSortAlogrithm.setElements(Arrays.copyOf(mOriginElements, mOriginElements.length));
                isPaused = false;
                synchronized (pauseLock) {
                    pauseLock.notify();
                }
            } else {
                if (shouldPlayNow) {
                    start();
                }
            }
        } else {
            mMainView.showSetElementsError();
        }
    }

    protected void handlePreExecute(int[] elements) {
        mSortView.showBalls(elements);
        isFirstTime = true;
        firstTimePause();
    }

    protected void handleFinished(int[] sortedElements) {
        isFinished = true;
        if(mSortView != null) {
            mSortView.showFinished(sortedElements);
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(mMainView != null) {
                    mMainView.toggleFinished();
                }
            }
        });
    }

    protected void firstTimePause() {
        if (isFirstTime) {
            pause();
            pauseWhenNeeded();
            isFirstTime = false;
        }
    }

    public void pause() {
        isPaused = true;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMainView.togglePauseFab(true);
            }
        });
    }

    public void pauseHome() {
        isPausedHome = true;
    }

    private void resume() {
        isPaused = false;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMainView.togglePauseFab(false);
            }
        });
        synchronized (pauseLock) {
            pauseLock.notify();
        }
    }

    public void stop() {
        mHandler.removeCallbacksAndMessages(null);
        mSortAlogrithm.stopExecute();
        if (isPaused || isPausedHome) {
            isPaused = false;
            isPausedHome = false;
            synchronized (pauseLock) {
                pauseLock.notify();
            }
        }
    }

    public boolean isPaused() {
        return isPaused || isFinished;
    }

    protected void pauseWhenNeeded() {
        if (isPaused || isPausedHome) {
            synchronized (pauseLock) {
                while (isPaused || isPausedHome) {
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

    protected void delayShort() {
        try {
            Thread.sleep(DELAY_SHORT_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void delayNormal() {
        try {
            Thread.sleep(DELAY_NORMAL_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public interface MainView {
        void togglePauseFab(boolean active);

        void toggleFinished();

        void showSetElementsError();
    }

    public interface BaseView {
        void showBalls(int[] elements);

        void showFinished(int[] sortedElements);
    }

    private static class SortThread extends Thread {

        public SortThread(Runnable target) {
            super(target);
        }

        @Override
        public void run() {
            super.run();
        }
    }
}
