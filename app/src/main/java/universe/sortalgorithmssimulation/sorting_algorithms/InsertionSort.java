package universe.sortalgorithmssimulation.sorting_algorithms;

/**
 * Created by Nhat on 5/22/2017.
 */

public class InsertionSort extends BaseSortAlgorithm {

    private final InsertionSort.Callback mFakeCallback = new Callback() {
        @Override
        public void onCompare(int index, boolean enable) {

        }

        @Override
        public void onShiftRightElementByOne(int index) {

        }

        @Override
        public void onShiftLeftElement(int index, int pos) {

        }

        @Override
        public void onPreExecute(int[] elements) {

        }

        @Override
        public void onFinished(int[] sortedElements) {

        }

        @Override
        public void onSelectedElement(int index) {

        }
    };

    public void setCallback(BaseSortAlgorithm.Callback callback) {
        if (callback != null && !(callback instanceof InsertionSort.Callback)) {
            throw new IllegalArgumentException("quickSortCallback should be InsertionSort.Callback");
        } else if (callback == null) {
            callback = mFakeCallback;
        }
        super.setCallback(callback);
    }

    @Override
    protected void execute() {
        InsertionSort.Callback callback = (Callback) mCallback;

        for (int i = 1; i < elements.length && isRunning; i++) {
            int x = elements[i];
            int pos = i - 1;
            callback.onSelectedElement(i);
            if ((pos >= 0) && (elements[pos] > x)) {
                while ((pos >= 0) && (elements[pos] > x) && isRunning) {
                    elements[pos + 1] = elements[pos];
                    callback.onCompare(pos, true);
                    callback.onShiftRightElementByOne(pos);
                    callback.onCompare(pos, false);
                    pos--;
                }
            }
            elements[pos + 1] = x;
            callback.onShiftLeftElement(i, pos + 1);
        }
    }

    public interface Callback extends BaseSortAlgorithm.Callback {
        void onCompare(int index, boolean enable);

        void onSelectedElement(int index);

        void onShiftRightElementByOne(int index);

        void onShiftLeftElement(int index, int leftPos);
    }
}
