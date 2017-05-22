package universe.sortalgorithmssimulation.sorting_algorithms;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Nhat on 4/7/2017.
 */

public class BubbleSort implements Runnable {

    private int[] mOriginalElements;

    private int[] mElements;

    private BubbleSortState mState;

    private StateCallback mStateCallback;

    private BubbleSort.BubbleSortCodeView mBubbleSortCodeView;

    private boolean isRunning = false;

    private boolean isPaused = false;
    long time = 500;

    private StateCallback mDefaultCallback = new StateCallback() {
        @Override
        public void updateState(BubbleSortState state) {

        }

        @Override
        public void setComparingPairOfElements(int indexFirstElement, int indexSecondElement, boolean active) {

        }

        @Override
        public void setSortedBall(int index) {

        }
    };

    public BubbleSort() {
        mElements = new int[0];
        mOriginalElements = new int[0];
        mState = new BubbleSortState();
        mStateCallback = mDefaultCallback;
    }

    public void registerStateCallback(StateCallback callback) {
        checkNotNull(callback);
        mStateCallback = callback;
    }

    public void unregisterStateCallback() {
        mStateCallback = mDefaultCallback;
    }

    public void setElements(int[] elements) {
        if (isRunning) {
            throw new IllegalStateException("Bubble sort algorithm is running");
        }
        mElements = elements;
        mOriginalElements = elements;
    }

    void setRunning(boolean enable) {
        isRunning = enable;
    }

    @Override
    public void run() {
        try {
            start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void InitSBubble() {
        /*sArr[0] = " BubbleSort()";
        sArr[1] = " {";
        sArr[2] = "     for( int i = 0 ; i < n - 1; i++ )";
        sArr[3] = "        for( int j = n - 1; j > i ; j-- )";
        sArr[4] = "           if( Ball [ j ] < Ball [ j - 1 ] )";
        sArr[5] = "             swap( Ball [ j ] , Ball [ j - 1 ] );";
        sArr[6] = " }";*/
    }

    public void start() throws InterruptedException {
        if (mElements.length > 1) {
            isRunning = true;
            // Start
            updateCurrentState();
            Thread.sleep(time);

            for (int i = 0; i < mElements.length - 1 && isRunning; i++) {
                // i change
                mState.i = i;
                updateCurrentState();

                // Highlight for 1
                highlightCode(1);
                Thread.sleep(time);

                for (int j = mElements.length - 1; j > i && isRunning; j--) {
                    // j change
                    mState.j = j;
                    updateCurrentState();

                    // Highlight for 2
                    highlightCode(2);
                    Thread.sleep(time);

                    mState.timesOfComparing++;
                    mState.isComparing = true;
                    mStateCallback.setComparingPairOfElements(j, j - 1, true);

                    updateCurrentState();

                    // Highlight comparation
                    highlightCode(3);
                    Thread.sleep(time);

                    if (mElements[j] < mElements[j - 1]) {
                        mState.timesOfSwapping++;
                        mState.isSwapping = true;
                        mState.indexLesserElement = j;
                        mState.indexGreaterElement = j - 1;

                        // Highlight swap
                        highlightCode(4);
                        updateCurrentState();

                        int temp = mElements[j];
                        mElements[j] = mElements[j - 1];
                        mElements[j - 1] = temp;
                        mState.isSwapping = false;
                    }
                    mState.isComparing = false;
                    mStateCallback.setComparingPairOfElements(j, j - 1, false);
                }
                // End for 2
                mStateCallback.setSortedBall(i);
            }
            // End for 1
            // End
            mState.setFinished(true);
            mStateCallback.setSortedBall(mElements.length - 1);
            updateCurrentState();
        }
    }

    private void updateCurrentState() {
        pauseWhenNeeded();
        mStateCallback.updateState(mState);
    }

    private Object pauseLock = new Object();

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
        } else {
            synchronized (pauseLock) {
                pauseLock.notifyAll();
            }
        }
    }

    private void highlightCode(int line) {
        if (mBubbleSortCodeView != null) {
            mBubbleSortCodeView.highlightCode(line);
        }
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    public int[] getElements() {
        return mElements;
    }

    public void restartState() {

    }

    public static class BubbleSortState {

        private int i = -1;
        private int j = -1;
        private int timesOfComparing, timesOfSwapping;
        private String message;
        private boolean finished;

        private boolean isSwapping;
        private int indexGreaterElement = -1;
        private int indexLesserElement = -1;

        private boolean isComparing;
        private int indexFirstComparing = -1;
        private int indexSecondComparing = -1;

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }

        public int getTimesOfSwapping() {
            return timesOfSwapping;
        }

        public int getTimesOfComparing() {
            return timesOfComparing;
        }

        public String getMessage() {
            if (isComparing) {
                message = "Compare Ball [ " + j + " ] && Ball [ " + (j - 1) + " ]";
            } else if (isSwapping) {
                message = "Swap Ball [ " + j + " ] && Ball [ " + (j - 1) + " ]";
            }
            return message;
        }

        public boolean isFinished() {
            return finished;
        }

        private void setFinished(boolean finished) {
            if (finished) {
                this.finished = true;
                message = "Successful";
                i = -1;
                j = -1;
            }
        }

        public boolean isSwapping() {
            return isSwapping;
        }

        public int getIndexGreaterElement() {
            return indexGreaterElement;
        }

        public int getIndexLesserElement() {
            return indexLesserElement;
        }

        public boolean isComparing() {
            return isComparing;
        }

        public int getIndexFirstComparing() {
            return indexFirstComparing;
        }

        public int getIndexSecondComparing() {
            return indexSecondComparing;
        }
    }

    public interface StateCallback {

        void updateState(BubbleSortState state);

        void setComparingPairOfElements(int indexFirstElement, int indexSecondElement, boolean active);

        void setSortedBall(int index);
    }

    public interface BubbleSortCodeView {

        void highlightCode(int line);
    }

    public interface Callback {

        void onPreExecute(int[] elements);

        void onComparing(int index1, int index2, boolean active);

        void onSwap(int indexGreater, int indexLesser);

        void onSortedBall(int index);

        void onFinished();
    }
}
