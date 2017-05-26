package universe.sortalgorithmssimulation.utils;

import universe.sortalgorithmssimulation.R;

import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.BINARY_INSERTION_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.BUBBLE_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.INSERTION_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.MERGE_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.QUICK_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.SELECTION_SORT;

/**
 * Created by Nhat on 5/24/2017.
 */

public final class Utils {

    public static int getColorId(int type) {
        int colorId = R.color.white;
        switch (type) {
            case BUBBLE_SORT:
                colorId = R.color.orange;
                break;
            case INSERTION_SORT:
                colorId = R.color.green;
                break;
            case BINARY_INSERTION_SORT:
                colorId = R.color.yellow;
                break;
            case SELECTION_SORT:
                colorId = R.color.purple;
                break;
            case QUICK_SORT:
                colorId = R.color.pink;
                break;
            case MERGE_SORT:
                colorId = R.color.colorPrimary;
                break;
        }
        return colorId;
    }
}
