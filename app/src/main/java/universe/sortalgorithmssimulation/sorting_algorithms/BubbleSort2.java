package universe.sortalgorithmssimulation.sorting_algorithms;

/**
 * Created by Nhat on 4/21/2017.
 */

public class BubbleSort2 extends BaseSortAlogorithm {

    public BubbleSort2() {
        this.elements = new int[]{};
        mCallback = mFakeCallback;
    }

    @Override
    public void execute() {
        if(isFinished) {
            mCallback.onPreExecute(elements);

            isRunning = true;
            for (int i = 0; i < elements.length - 1 && isRunning; i++) {
                for (int j = elements.length - 1; j > i && isRunning; j--) {
                    // Start Comparing
                    mCallback.onComparing(j, j - 1, true);

                    if (elements[j] < elements[j - 1]) {
                        int temp = elements[j];
                        elements[j] = elements[j - 1];
                        elements[j - 1] = temp;

                        mCallback.onSwap(j - 1, j);
                    }
                    // End Comparing
                    mCallback.onComparing(j, j - 1, false);
                }
                // Set i ball finished
                mCallback.onSortedBall(i);
            }
            mCallback.onSortedBall(elements.length - 1);
            mCallback.onFinished();
        }
        isFinished = true;
        isRunning = false;
    }
}
