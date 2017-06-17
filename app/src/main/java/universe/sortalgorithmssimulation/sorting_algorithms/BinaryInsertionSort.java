package universe.sortalgorithmssimulation.sorting_algorithms;

/**
 * Created by Nhat on 5/26/2017.
 */
public class BinaryInsertionSort extends BaseSortAlgorithm<BinaryInsertionSort.Callback> {

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

    public BinaryInsertionSort() {
        mCallback = mFakeCallback;
    }

    @Override
    protected void execute() {
        for(int i = 1; i < elements.length && isRunning; i++) {
            int x = elements[i];
            int left = 0;
            int right = i - 1;
            mCallback.onSelectedElement(i);

            while (left <= right && isRunning) {
                int mid = (left + right) / 2;
                mCallback.onCompare(mid, true);
                if (x < elements[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
                mCallback.onCompare(mid, false);
            }
            for (int j = i - 1; j >= left && isRunning; j--) {
                elements[j + 1] = elements[j];
                mCallback.onShiftRightElementByOne(j);
            }
            elements[left] = x;
            mCallback.onShiftLeftElement(i, left);
        }
    }

    public interface Callback extends BaseSortAlgorithm.Callback {
        void onCompare(int index, boolean enable);

        void onSelectedElement(int index);

        void onShiftRightElementByOne(int index);

        void onShiftLeftElement(int index, int leftPos);
    }
}
