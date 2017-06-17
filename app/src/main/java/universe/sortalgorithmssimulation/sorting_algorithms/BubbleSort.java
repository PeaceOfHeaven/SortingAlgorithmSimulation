package universe.sortalgorithmssimulation.sorting_algorithms;

/**
 * Created by Nhat on 4/21/2017.
 */
public class BubbleSort extends BaseSortAlgorithm<BubbleSort.Callback> {

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
        mCallback = mFakeCallback;
    }

    @Override
    protected void execute() {
        int i = 0;
        boolean swapped;
        do {
            swapped = false;
            for (int j = elements.length - 1; j > i && isRunning; j--) {
                // Start Comparing
                mCallback.onComparing(j, j - 1, true);
                if (elements[j] < elements[j - 1]) {
                    swapped = true;
                    int temp = elements[j];
                    elements[j] = elements[j - 1];
                    elements[j - 1] = temp;
                    mCallback.onSwap(j - 1, j);
                }
                // End Comparing
                mCallback.onComparing(j, j - 1, false);
            }
            if(swapped) {
                mCallback.onSortedBall(i);
            }
            i++;
        } while (swapped && isRunning);
    }

    public interface Callback extends BaseSortAlgorithm.Callback {

        void onComparing(int index1, int index2, boolean enable);

        void onSwap(int indexGreater, int indexLesser);

        void onSortedBall(int index);
    }
}
