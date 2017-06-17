package universe.sortalgorithmssimulation.sorting_algorithms;

/**
 * Created by Nhat on 5/27/2017.
 */
public class MergeSort extends BaseSortAlgorithm<MergeSort.Callback> {

    private MergeSort.Callback mFakeCallback = new Callback() {
        @Override
        public void onLeftMid(int left, int mid, boolean start) {

        }

        @Override
        public void onMidRight(int mid, int right, boolean start) {

        }

        @Override
        public void onStartMerge(int sizeMergedResult) {

        }

        @Override
        public void onUpdateMergeResult(int indexResult, int leftOffset, int indexLesser) {

        }

        @Override
        public void onMergeFinished(int left) {

        }

        @Override
        public void onCompare(int first, int second, boolean enable) {

        }

        @Override
        public void onPreExecute(int[] elements) {

        }

        @Override
        public void onFinished(int[] sortedElements) {

        }
    };

    public MergeSort() {
        mCallback = mFakeCallback;
    }

    @Override
    protected void execute() {
        doMergeSort(0, elements.length - 1);
    }

    private void doMergeSort(int left, int right) {
        if (left < right && isRunning) {
            int mid = (left + right) / 2;
            mCallback.onLeftMid(left, mid, true);
            doMergeSort(left, mid);

            mCallback.onMidRight(mid + 1, right, true);
            doMergeSort(mid + 1, right);

            doMerge(left, mid, right);
        }
    }

    private void doMerge(int left, int mid, int right) {
        if(!isRunning) {
            return;
        }
        int[] mergedResult = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;

        mCallback.onStartMerge(mergedResult.length);
        while (i <= mid && j <= right) {
            mCallback.onCompare(i, j, true);
            if (elements[i] < elements[j]) { // 1st < 2st
                mergedResult[k] = elements[i];
                mCallback.onUpdateMergeResult(k, left + k, i);
                mCallback.onCompare(i, j, false);
                k++;
                i++;
            } else {  // 1st > 2st
                mergedResult[k] = elements[j];
                mCallback.onUpdateMergeResult(k, left + k, j);
                mCallback.onCompare(j, i, false);
                k++;
                j++;
            }
        }
        while (j <= right) {
            mergedResult[k] = elements[j];
            mCallback.onUpdateMergeResult(k, left + k, j);
            k++;
            j++;
        }
        while (i <= mid) {
            mergedResult[k] = elements[i];
            mCallback.onUpdateMergeResult(k, left + k, i);
            k++;
            i++;
        }
        for (i = 0; i < k; ++i) {
            elements[left + i] = mergedResult[i];
        }
        mCallback.onMergeFinished(left);
    }

    public interface Callback extends BaseSortAlgorithm.Callback {
        void onLeftMid(int left, int mid, boolean start);

        void onMidRight(int mid, int right, boolean start);

        void onStartMerge(int sizeMergedResult);

        void onUpdateMergeResult(int indexResult, int leftOffset, int indexLesser);

        void onMergeFinished(int left);

        void onCompare(int first, int second, boolean enable);
    }
}
