package universe.sortalgorithmssimulation.sorting_algorithms;

/**
 * Created by Nhat on 5/22/2017.
 */

public abstract class BaseSortAlogorithm {

    protected int[] elements;

    protected Callback mCallback;

    protected boolean isFinished = true;

    protected boolean isRunning;

    protected final Callback mFakeCallback = new Callback() {
        @Override
        public void onPreExecute(int[] elements) {

        }

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
        public void onFinished() {

        }
    };

    public void setCallback(Callback callback) {
        mCallback = callback == null ? mFakeCallback : callback;
    }

    public void setElements(int[] elements) {
        if (isRunning) {
            throw new IllegalStateException("Algorithm is running");
        }
        this.elements = elements;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public abstract void execute();

    public interface Callback {

        void onPreExecute(int[] elements);

        void onComparing(int index1, int index2, boolean enable);

        void onSwap(int indexGreater, int indexLesser);

        void onSortedBall(int index);

        void onFinished();
    }
}
