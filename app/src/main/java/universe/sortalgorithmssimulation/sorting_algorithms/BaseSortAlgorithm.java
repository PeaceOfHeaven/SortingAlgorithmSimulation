package universe.sortalgorithmssimulation.sorting_algorithms;

/**
 * Created by Nhat on 5/22/2017.
 */

public abstract class BaseSortAlgorithm implements Runnable {
    protected int[] elements;

    protected Callback mCallback;
    protected boolean isRunning;

    public void setCallback(Callback callback) {
        mCallback = callback;
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

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void run() {
        executeIfNotRunning();
    }

    private void executeIfNotRunning() {
        if (!isRunning) {
            isRunning = true;
            execute();
            isRunning = false;
        }
    }
    protected abstract void execute();

    public interface Callback {

        void onPreExecute(int[] elements);

        void onFinished(int[] sortedElements);
    }
}
