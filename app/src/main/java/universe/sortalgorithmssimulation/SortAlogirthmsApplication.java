package universe.sortalgorithmssimulation;

import android.app.Application;
import android.support.v4.util.SparseArrayCompat;

import com.github.mikephil.charting.utils.Utils;
import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;

import timber.log.Timber;
import universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo;

import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.BINARY_INSERTION_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.BUBBLE_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.INSERTION_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.MERGE_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.QUICK_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.SELECTION_SORT;

/**
 * Created by Nhat on 5/7/2017.
 */

public class SortAlogirthmsApplication extends Application {

    public static final int NUM_OF_ALGORITHMS = 6;
    private static final SparseArrayCompat<SortAlgorithmInfo> mSortAlgorithms = new SparseArrayCompat<>(NUM_OF_ALGORITHMS);

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree() {

             @Override
             protected String createStackElementTag(StackTraceElement element) {
                 return super.createStackElementTag(element) + "Nhat";
             }
        });
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...

        initializeSortAlogirthms();
        Utils.init(this);
    }

    private void initializeSortAlogirthms() {
        mSortAlgorithms.put(BUBBLE_SORT, SortAlgorithmInfo.getInstance(BUBBLE_SORT));
        mSortAlgorithms.put(INSERTION_SORT, SortAlgorithmInfo.getInstance(INSERTION_SORT));
        mSortAlgorithms.put(BINARY_INSERTION_SORT, SortAlgorithmInfo.getInstance(BINARY_INSERTION_SORT));
        mSortAlgorithms.put(SELECTION_SORT, SortAlgorithmInfo.getInstance(SELECTION_SORT));
        mSortAlgorithms.put(QUICK_SORT, SortAlgorithmInfo.getInstance(QUICK_SORT));
        mSortAlgorithms.put(MERGE_SORT, SortAlgorithmInfo.getInstance(MERGE_SORT));
    }

    public static SortAlgorithmInfo getSortAlgorithm(int type) {
        return mSortAlgorithms.get(type);
    }

    public static ArrayList<SortAlgorithmInfo> getSortAlgorithms() {
        ArrayList<SortAlgorithmInfo> algorithms = new ArrayList<>(mSortAlgorithms.size());
        for (int i = 0; i < mSortAlgorithms.size(); i++) {
            algorithms.add(mSortAlgorithms.valueAt(i));
        }
        return algorithms;
    }
}
