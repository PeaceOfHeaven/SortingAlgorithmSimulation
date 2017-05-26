package universe.sortalgorithmssimulation.sorting_algorithms;

import universe.sortalgorithmssimulation.R;

import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.BUBBLE_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.INSERTION_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.SELECTION_SORT;

/**
 * Created by Nhat on 4/11/2017.
 */

public class SortAlgorithmInfo {
    public interface Type {
        int BUBBLE_SORT = 1;
        int INSERTION_SORT = 2;
        int BINARY_INSERTION_SORT = 3;
        int SELECTION_SORT = 4;
        int QUICK_SORT = 5;
        int MERGE_SORT = 6;
    }

    private String mTitle;
    private int mDescriptionResId;
    private int mPseducodeResId;
    private int mType;
    private BaseSortAlgorithm mAlgorithmExecuteable;

    private SortAlgorithmInfo() {
    }

    public static SortAlgorithmInfo getInstance(int type) {
        SortAlgorithmInfo sortAlgorithmInfo = new SortAlgorithmInfo();
        switch (type) {
            case Type.BUBBLE_SORT:
                sortAlgorithmInfo.mTitle = "Bubble Sort";
                sortAlgorithmInfo.mDescriptionResId = R.string.info_buble_sort_idea;
                sortAlgorithmInfo.mPseducodeResId = R.string.info_buble_sort_pesudo;
                break;
            case INSERTION_SORT:
                sortAlgorithmInfo.mTitle = "Insertion Sort";
                sortAlgorithmInfo.mDescriptionResId = R.string.info_buble_sort_idea;
                sortAlgorithmInfo.mPseducodeResId = R.string.info_buble_sort_pesudo;
                break;
            case Type.BINARY_INSERTION_SORT:
                sortAlgorithmInfo.mTitle = "Binary Insertion Sort";
                sortAlgorithmInfo.mDescriptionResId = R.string.info_buble_sort_idea;
                sortAlgorithmInfo.mPseducodeResId = R.string.info_buble_sort_pesudo;
                break;
            case Type.SELECTION_SORT:
                sortAlgorithmInfo.mTitle = "Selection Sort";
                sortAlgorithmInfo.mDescriptionResId = R.string.info_buble_sort_idea;
                sortAlgorithmInfo.mPseducodeResId = R.string.info_buble_sort_pesudo;
                break;
            case Type.QUICK_SORT:
                sortAlgorithmInfo.mTitle = "Quick Sort";
                sortAlgorithmInfo.mDescriptionResId = R.string.info_buble_sort_idea;
                sortAlgorithmInfo.mPseducodeResId = R.string.info_buble_sort_pesudo;
                break;
            case Type.MERGE_SORT:
                sortAlgorithmInfo.mTitle = "Merge Sort";
                sortAlgorithmInfo.mDescriptionResId = R.string.info_buble_sort_idea;
                sortAlgorithmInfo.mPseducodeResId = R.string.info_buble_sort_pesudo;
                break;
        }
        sortAlgorithmInfo.mType = type;
        sortAlgorithmInfo.mAlgorithmExecuteable = getAlgorithmExecutable(type);
        return sortAlgorithmInfo;
    }

    private static BaseSortAlgorithm getAlgorithmExecutable(int type) {
        BaseSortAlgorithm sortAlgorithm = null;
        switch (type) {
            case BUBBLE_SORT:
                sortAlgorithm = new BubbleSort();
                break;
            case INSERTION_SORT:
                sortAlgorithm = new InsertionSort();
                break;
            case SELECTION_SORT:
                sortAlgorithm = new SelectionSort();
                break;
        }
        return sortAlgorithm;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getDescriptionResId() {
        return mDescriptionResId;
    }

    public int getPseducodeResId() {
        return mPseducodeResId;
    }

    public int getType() {
        return mType;
    }

    public BaseSortAlgorithm getAlgorithmExecuteable() {
        return mAlgorithmExecuteable;
    }
}
