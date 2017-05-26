package universe.sortalgorithmssimulation.sorting_algorithms;

/**
 * Created by Nhat on 4/21/2017.
 */

public class BubbleSort extends BaseSortAlgorithm {

    private final Callback mFakeCallback = new Callback() {
        @Override
        public void onComparing(int index1, int index2, boolean enable) {

        }

        @Override
        public void onSwap(int indexGreater, int indexLesser) {

        }

        @Override
        public void onSortedBall(int index) {

        }

        @Override
        public void onPreExecute(int[] elements) {

        }

        @Override
        public void onFinished(int[] sortedElements) {

        }
    };

    public BubbleSort() {
        this.elements = new int[]{};
        mCallback = mFakeCallback;
    }

    @Override
    public void setCallback(BaseSortAlgorithm.Callback callback) {
        if (callback != null && !(callback instanceof BubbleSort.Callback)) {
            throw new IllegalArgumentException("callback should be BubbleSort.Callback");
        } else if (callback == null) {
            callback = mFakeCallback;
        }
        super.setCallback(callback);
    }

    @Override
    protected void execute() {
        BubbleSort.Callback callback = (Callback) mCallback;
        callback.onPreExecute(elements);

        for (int i = 0; i < elements.length - 1 && isRunning; i++) {
            for (int j = elements.length - 1; j > i && isRunning; j--) {
                // Start Comparing
                callback.onComparing(j, j - 1, true);
                if (elements[j] < elements[j - 1]) {
                    int temp = elements[j];
                    elements[j] = elements[j - 1];
                    elements[j - 1] = temp;
                    callback.onSwap(j - 1, j);
                }
                // End Comparing
                callback.onComparing(j, j - 1, false);
            }
            callback.onSortedBall(i);
        }
        callback.onSortedBall(elements.length - 1);
        callback.onFinished(elements);
    }

    public interface Callback extends BaseSortAlgorithm.Callback {

        void onComparing(int index1, int index2, boolean enable);

        void onSwap(int indexGreater, int indexLesser);

        void onSortedBall(int index);
    }
}
