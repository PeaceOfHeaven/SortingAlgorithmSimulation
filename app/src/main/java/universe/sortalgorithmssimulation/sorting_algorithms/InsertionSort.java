package universe.sortalgorithmssimulation.sorting_algorithms;

/**
 * Created by Nhat on 5/22/2017.
 */
public class InsertionSort extends BaseSortAlgorithm<InsertionSort.Callback> {

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

    public InsertionSort() {
        mCallback = mFakeCallback;
    }

    @Override
    protected void execute() {
        for (int i = 1; i < elements.length && isRunning; i++) {
            int x = elements[i];
            int pos = i - 1;
            mCallback.onSelectedElement(i);
            if ((pos >= 0) && (elements[pos] > x)) {
                while ((pos >= 0) && (elements[pos] > x) && isRunning) {
                    elements[pos + 1] = elements[pos];
                    mCallback.onCompare(pos, true);
                    mCallback.onShiftRightElementByOne(pos);
                    mCallback.onCompare(pos, false);
                    pos--;
                }
            }
            elements[pos + 1] = x;
            mCallback.onShiftLeftElement(i, pos + 1);
        }
    }

    public interface Callback extends BaseSortAlgorithm.Callback {
        void onCompare(int index, boolean enable);

        void onSelectedElement(int index);

        void onShiftRightElementByOne(int index);

        void onShiftLeftElement(int index, int leftPos);
    }
}
