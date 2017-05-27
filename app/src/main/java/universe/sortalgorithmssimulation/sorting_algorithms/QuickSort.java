package universe.sortalgorithmssimulation.sorting_algorithms;

/**
 * Created by Nhat on 5/26/2017.
 */

public class QuickSort extends BaseSortAlgorithm {

    private QuickSort.Callback mQuickSortCallback;
    private final QuickSort.Callback mFakeCallback = new Callback() {
        @Override
        public void onCompare(int index, boolean enable) {

        }

        @Override
        public void onSwap(int indexGreater, int indexLesser) {

        }

        @Override
        public void onPreExecute(int[] elements) {

        }

        @Override
        public void onFinished(int[] sortedElements) {

        }
    };

    @Override
    public void setCallback(BaseSortAlgorithm.Callback callback) {
        if (callback != null && !(callback instanceof QuickSort.Callback)) {
            throw new IllegalArgumentException("mQuickSortCallback should be instance of doQuickSort.Callback");
        } else if (callback == null) {
            callback = mFakeCallback;
        }
        super.setCallback(callback);
        mQuickSortCallback = (Callback) callback;
    }

    @Override
    protected void execute() {
        doQuickSort(0, elements.length - 1);
    }

    protected void doQuickSort(int left, int right) {
        int i, j, x;
        i = left;
        j = right;
        x = elements[(left + right)/2];

        mQuickSortCallback.onCompare((left + right)/2, true);
        while (i < j) {
            while (onCompare(i) && elements[i] < x) {
                i++;
            }
            while (onCompare(j) && elements[j] > x) {
                j--;
            }
            if (i <= j) {
                if(i != j) {
                    mQuickSortCallback.onSwap(i, j);
                    int temp = elements[i];
                    elements[i] = elements[j];
                    elements[j] = temp;
                }
                i++; j--;
            }
        }
        mQuickSortCallback.onCompare((left + right)/2, false);
        if (left < j)
            doQuickSort(left, j);
        if (i < right)
            doQuickSort(i, right);
    }

    private boolean onCompare(int i) {
        mQuickSortCallback.onCompare(i, true);
        mQuickSortCallback.onCompare(i, false);
        return true;
    }

    public interface Callback extends BaseSortAlgorithm.Callback {
        void onCompare(int index, boolean enable);

        void onSwap(int indexGreater, int indexLesser);
    }
}
