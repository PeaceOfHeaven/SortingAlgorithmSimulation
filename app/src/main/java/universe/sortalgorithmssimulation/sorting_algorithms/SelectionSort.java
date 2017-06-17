package universe.sortalgorithmssimulation.sorting_algorithms;

/**
 * Created by Nhat on 5/25/2017.
 */
public class SelectionSort extends BaseSortAlgorithm<SelectionSort.Callback> {

    private final SelectionSort.Callback mFakeCallback = new Callback() {
        @Override
        public void onPreExecute(int[] elements) {
        }

        @Override
        public void onFinished(int[] sortedElements) {
        }

        @Override
        public void onCompare(int index1, boolean enable) {

        }

        @Override
        public void onMinChange(int newIndex, int oldIndex) {

        }

        @Override
        public void onSwap(int index, int minIndex) {
        }

        @Override
        public void onSortedBall(int index) {

        }
    };

    public SelectionSort() {
        mCallback = mFakeCallback;
    }

    @Override
    protected void execute() {
        for(int i = 0; i < elements.length-1 && isRunning; i++) {
            int min = i;
            mCallback.onMinChange(i, -1);
            for (int j = i + 1; j < elements.length && isRunning; j++) {
                mCallback.onCompare(j, true);
                if(elements[j] < elements[min]) {
                    int oldIndex = min;
                    min = j;
                    mCallback.onMinChange(min, oldIndex);
                } else {
                    mCallback.onCompare(j, false);
                }
            }
            if(min != i) {
                int temp = elements[i];
                elements[i] = elements[min];
                elements[min] = temp;
                mCallback.onSwap(i, min);
            }
            mCallback.onSortedBall(i);
        }
        mCallback.onSortedBall(elements.length-1);
    }

    public interface Callback extends BaseSortAlgorithm.Callback {
        void onCompare(int index1, boolean enable);

        void onMinChange(int newIndex, int oldIndex);

        void onSwap(int index, int minIndex);

        void onSortedBall(int index);
    }
}
