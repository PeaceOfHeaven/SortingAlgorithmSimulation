package universe.sortalgorithmssimulation.sorting_algorithms;

import universe.sortalgorithmssimulation.R;

import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.INSERTION_SORT;

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

    public interface Complexity {
        int BIG_O_N = 1;
        int BIG_O_N2 = 2;
        int BIG_O_NLOGN = 3;
    }

    private String mTitle;
    private int mDescriptionResId;
    private int mPseducodeResId;
    private int mType;
    private int mBestcaseComplexity;
    private int mWorstcaseComplexity;
    private int mAveragecaseComplexity;

    private SortAlgorithmInfo() {
    }

    /**
     *
     * @param type sort algorithm category -> {@link SortAlgorithmInfo.Type}
     * @return instance of sort algorithm or null if there are no supported algorithms
     */
    public static SortAlgorithmInfo getInstance(int type) {
        SortAlgorithmInfo sortAlgorithmInfo = new SortAlgorithmInfo();
        switch (type) {
            case Type.BUBBLE_SORT:
                sortAlgorithmInfo.mTitle = "Bubble Sort";
                sortAlgorithmInfo.mDescriptionResId = R.string.info_buble_sort_idea;
                sortAlgorithmInfo.mPseducodeResId = R.string.info_buble_sort_pesudo;
                sortAlgorithmInfo.mBestcaseComplexity = Complexity.BIG_O_N;
                sortAlgorithmInfo.mAveragecaseComplexity = Complexity.BIG_O_N2;
                sortAlgorithmInfo.mWorstcaseComplexity = Complexity.BIG_O_N2;
                break;
            case INSERTION_SORT:
                sortAlgorithmInfo.mTitle = "Insertion Sort";
                sortAlgorithmInfo.mDescriptionResId = R.string.info_buble_sort_idea;
                sortAlgorithmInfo.mPseducodeResId = R.string.info_buble_sort_pesudo;
                sortAlgorithmInfo.mBestcaseComplexity = Complexity.BIG_O_N;
                sortAlgorithmInfo.mAveragecaseComplexity = Complexity.BIG_O_N2;
                sortAlgorithmInfo.mWorstcaseComplexity = Complexity.BIG_O_N2;
                break;
            case Type.BINARY_INSERTION_SORT:
                sortAlgorithmInfo.mTitle = "Binary Insertion Sort";
                sortAlgorithmInfo.mDescriptionResId = R.string.info_buble_sort_idea;
                sortAlgorithmInfo.mPseducodeResId = R.string.info_buble_sort_pesudo;
                sortAlgorithmInfo.mBestcaseComplexity = Complexity.BIG_O_N;
                sortAlgorithmInfo.mAveragecaseComplexity = Complexity.BIG_O_N2;
                sortAlgorithmInfo.mWorstcaseComplexity = Complexity.BIG_O_N2;
                break;
            case Type.SELECTION_SORT:
                sortAlgorithmInfo.mTitle = "Selection Sort";
                sortAlgorithmInfo.mDescriptionResId = R.string.info_buble_sort_idea;
                sortAlgorithmInfo.mPseducodeResId = R.string.info_buble_sort_pesudo;
                sortAlgorithmInfo.mBestcaseComplexity = Complexity.BIG_O_N2;
                sortAlgorithmInfo.mAveragecaseComplexity = Complexity.BIG_O_N2;
                sortAlgorithmInfo.mWorstcaseComplexity = Complexity.BIG_O_N2;
                break;
            case Type.QUICK_SORT:
                sortAlgorithmInfo.mTitle = "Quick Sort";
                sortAlgorithmInfo.mDescriptionResId = R.string.info_buble_sort_idea;
                sortAlgorithmInfo.mPseducodeResId = R.string.info_buble_sort_pesudo;
                sortAlgorithmInfo.mBestcaseComplexity = Complexity.BIG_O_NLOGN;
                sortAlgorithmInfo.mAveragecaseComplexity = Complexity.BIG_O_NLOGN;
                sortAlgorithmInfo.mWorstcaseComplexity = Complexity.BIG_O_N2;
                break;
            case Type.MERGE_SORT:
                sortAlgorithmInfo.mTitle = "Merge Sort";
                sortAlgorithmInfo.mDescriptionResId = R.string.info_buble_sort_idea;
                sortAlgorithmInfo.mPseducodeResId = R.string.info_buble_sort_pesudo;
                sortAlgorithmInfo.mBestcaseComplexity = Complexity.BIG_O_NLOGN;
                sortAlgorithmInfo.mWorstcaseComplexity = Complexity.BIG_O_NLOGN;
                sortAlgorithmInfo.mAveragecaseComplexity = Complexity.BIG_O_NLOGN;
                break;
            default:
                return null;
        }
        sortAlgorithmInfo.mType = type;
        return sortAlgorithmInfo;
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

    public int getBestcaseComplexity() {
        return mBestcaseComplexity;
    }

    public int getWorstcaseComplexity() {
        return mWorstcaseComplexity;
    }

    public int getAveragecaseComplexity() {
        return mAveragecaseComplexity;
    }
}
