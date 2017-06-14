package universe.sortalgorithmssimulation.activity;

import android.content.Context;

import universe.sortalgorithmssimulation.activity.presenters.BaseSortPresenter;
import universe.sortalgorithmssimulation.activity.presenters.BinaryInsertionSortPresenter;
import universe.sortalgorithmssimulation.activity.presenters.BubbleSortPresenter;
import universe.sortalgorithmssimulation.activity.presenters.InsertionSortPresenter;
import universe.sortalgorithmssimulation.activity.presenters.MergeSortPresenter;
import universe.sortalgorithmssimulation.activity.presenters.QuickSortPresenter;
import universe.sortalgorithmssimulation.activity.presenters.SelectionSortPresenter;
import universe.sortalgorithmssimulation.activity.views.BinaryInsertionSortView;
import universe.sortalgorithmssimulation.activity.views.BubbleSortView;
import universe.sortalgorithmssimulation.activity.views.InsertionSortView;
import universe.sortalgorithmssimulation.activity.views.MergeSortView;
import universe.sortalgorithmssimulation.activity.views.QuickSortView;
import universe.sortalgorithmssimulation.activity.views.SelectionSortView;
import universe.sortalgorithmssimulation.sorting_algorithms.BinaryInsertionSort;
import universe.sortalgorithmssimulation.sorting_algorithms.BubbleSort;
import universe.sortalgorithmssimulation.sorting_algorithms.InsertionSort;
import universe.sortalgorithmssimulation.sorting_algorithms.MergeSort;
import universe.sortalgorithmssimulation.sorting_algorithms.QuickSort;
import universe.sortalgorithmssimulation.sorting_algorithms.SelectionSort;

import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.BINARY_INSERTION_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.BUBBLE_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.INSERTION_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.MERGE_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.QUICK_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.SELECTION_SORT;

/**
 * Created by Nhat on 6/13/2017.
 */
public final class SortPresenterFactory {

    private SortPresenterFactory() {
    }

    public static BaseSortPresenter provideSortPresenter(Context context,
                                           BaseSortPresenter.MainView mainView,
                                           int type) {
        switch (type) {
            case BUBBLE_SORT:
                return new BubbleSortPresenter(new BubbleSort(), mainView, new BubbleSortView(context));
            case INSERTION_SORT:
                return new InsertionSortPresenter(new InsertionSort(), mainView, new InsertionSortView(context));
            case BINARY_INSERTION_SORT:
                return new BinaryInsertionSortPresenter(new BinaryInsertionSort(), mainView, new BinaryInsertionSortView(context));
            case SELECTION_SORT:
                return new SelectionSortPresenter(new SelectionSort(), mainView, new SelectionSortView(context));
            case QUICK_SORT:
                return new QuickSortPresenter(new QuickSort(), mainView, new QuickSortView(context));
            case MERGE_SORT:
                return new MergeSortPresenter(new MergeSort(), mainView, new MergeSortView(context));
            default:
                return null;
        }
    }
}
