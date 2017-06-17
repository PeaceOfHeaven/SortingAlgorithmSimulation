package universe.sortalgorithmssimulation.sorting_algorithms;

import android.os.Process;
import android.support.annotation.NonNull;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Nhat on 5/22/2017.
 */

public abstract class BaseSortAlgorithm<C extends BaseSortAlgorithm.Callback> implements Runnable {
    protected int[] elements = new int[]{};

    protected C mCallback;
    protected boolean isRunning;
    private boolean elementsChangedBeforeRunning;

    public void setCallback(@NonNull C callback) {
        checkNotNull(callback);
        mCallback = callback;
    }

    public void setElements(int[] elements) {
        if (isRunning) {
            throw new IllegalStateException("Algorithm is running");
        }
        this.elements = elements;
        elementsChangedBeforeRunning = true;
    }

    void setRunning(boolean a) {

    }

    public void stopExecute() {
        isRunning = false;
    }

    @Override
    public void run() {
        if(elements == null) {
            throw new IllegalStateException("No elements need to be sorted!");
        }
        Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND);
        elementsChangedBeforeRunning = false;
        while (true) {
            executeIfNotRunning();
            if (elementsChangedBeforeRunning) {
                elementsChangedBeforeRunning = false;
            } else {
                break;
            }
        }
    }

    private void executeIfNotRunning() {
        if (!isRunning) {
            mCallback.onPreExecute(elements);
            if(elementsChangedBeforeRunning) {
                return;
            }
            isRunning = true;
            execute();
            mCallback.onFinished(elements);
            isRunning = false;
        }
    }
    protected abstract void execute();

    public interface Callback {

        void onPreExecute(int[] elements);

        void onFinished(int[] sortedElements);
    }
}
