package universe.sortalgorithmssimulation.sorting_algorithms;

/**
 * Created by Nhat on 5/25/2017.
 */
public class SelectionSort extends BaseSortAlgorithm {

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

    public void setCallback(BaseSortAlgorithm.Callback callback) {
        if (callback != null && !(callback instanceof SelectionSort.Callback)) {
            throw new IllegalArgumentException("quickSortCallback should be SelectionSort.Callback");
        } else if(callback == null) {
            callback = mFakeCallback;
        }
        super.setCallback(callback);
    }

    @Override
    protected void execute() {
        SelectionSort.Callback callback = (Callback) mCallback;

        for(int i = 0; i < elements.length-1 && isRunning; i++) {
            int min = i;
            callback.onMinChange(i, -1);
            for (int j = i + 1; j < elements.length && isRunning; j++) {
                callback.onCompare(j, true);
                if(elements[j] < elements[min]) {
                    int oldIndex = min;
                    min = j;
                    callback.onMinChange(min, oldIndex);
                } else {
                    callback.onCompare(j, false);
                }
            }
            if(min != i) {
                int temp = elements[i];
                elements[i] = elements[min];
                elements[min] = temp;
                callback.onSwap(i, min);
            }
            callback.onSortedBall(i);
        }
        callback.onSortedBall(elements.length-1);
    }

    public interface Callback extends BaseSortAlgorithm.Callback {
        void onCompare(int index1, boolean enable);

        void onMinChange(int newIndex, int oldIndex);

        void onSwap(int index, int minIndex);

        void onSortedBall(int index);
    }
}
