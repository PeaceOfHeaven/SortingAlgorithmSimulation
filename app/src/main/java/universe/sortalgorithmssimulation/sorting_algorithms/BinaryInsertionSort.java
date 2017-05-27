package universe.sortalgorithmssimulation.sorting_algorithms;

/**
 * Created by Nhat on 5/26/2017.
 */

public class BinaryInsertionSort extends BaseSortAlgorithm {

    private final BinaryInsertionSort.Callback mFakeCallback = new Callback() {
        @Override
        public void onCompare(int index, boolean enable) {

        }

        @Override
        public void onSelectedElement(int index) {

        }

        @Override
        public void onShiftRightElementByOne(int index) {

        }

        @Override
        public void onShiftLeftElement(int index, int leftPos) {

        }

        @Override
        public void onPreExecute(int[] elements) {

        }

        @Override
        public void onFinished(int[] sortedElements) {

        }
    };

    public void setCallback(BaseSortAlgorithm.Callback callback) {
        if (callback != null && !(callback instanceof BinaryInsertionSort.Callback)) {
            throw new IllegalArgumentException("quickSortCallback should be instance of BinaryInsertionSort.Callback");
        } else if (callback == null) {
            callback = mFakeCallback;
        }
        super.setCallback(callback);
    }

    @Override
    protected void execute() {
        BinaryInsertionSort.Callback callback = (Callback) mCallback;

        for(int i = 1; i < elements.length && isRunning; i++) {
            int x = elements[i];
            int left = 0;
            int right = i - 1;
            callback.onSelectedElement(i);

            while (left <= right && isRunning) {
                int mid = (left + right) / 2;
                callback.onCompare(mid, true);
                if (x < elements[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
                callback.onCompare(mid, false);
            }
            for (int j = i - 1; j >= left && isRunning; j--) {
                elements[j + 1] = elements[j];
                callback.onShiftRightElementByOne(j);
            }
            elements[left] = x;
            callback.onShiftLeftElement(i, left);
        }
    }

    public interface Callback extends BaseSortAlgorithm.Callback {
        void onCompare(int index, boolean enable);

        void onSelectedElement(int index);

        void onShiftRightElementByOne(int index);

        void onShiftLeftElement(int index, int leftPos);
    }
}
