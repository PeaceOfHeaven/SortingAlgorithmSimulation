package universe.sortalgorithmssimulation.sorting_algorithms;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by Nhat on 5/27/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MergeSortTest {

    @Mock
    MergeSort.Callback mCallback;
    MergeSort mMergeSort;

    @Before
    public void setUp() throws Exception {
        mMergeSort = new MergeSort();
        mMergeSort.setCallback(mCallback);
    }

    @Test
    public void shouldReturnSortedElements1() throws Exception {
        int[] elements = new int[]{8, 6, 5, 3, 2, 10, 0};
        mMergeSort.setElements(elements);

        mMergeSort.run();

        int[] expected = new int[]{0, 2, 3, 5, 6, 8, 10};
        Mockito.verify(mCallback).onFinished(expected);
    }

    @Test
    public void shouldReturnSortedElements2() throws Exception {
        int[] elements = new int[]{10, 8, 6, 5, 3, 2, 0};
        mMergeSort.setElements(elements);

        mMergeSort.run();

        int[] expected = new int[]{0, 2, 3, 5, 6, 8, 10};
        Mockito.verify(mCallback).onFinished(expected);
    }

    @Test
    public void shouldReturnSortedElements3() throws Exception {
        int[] elements = new int[]{0, 8, 3, 5, 3, 2, 0};
        mMergeSort.setElements(elements);

        mMergeSort.run();

        int[] expected = new int[]{0, 0, 2, 3, 3, 5, 8};
        Mockito.verify(mCallback).onFinished(expected);
    }

    @Test
    public void shouldReturnSortedElements4() throws Exception {
        int[] elements = new int[]{8, 6, 1, 3, 2, 10, 4};
        mMergeSort.setElements(elements);

        mMergeSort.run();

        int[] expected = new int[]{1, 2, 3, 4, 6, 8, 10};
        Mockito.verify(mCallback).onFinished(expected);
    }

    @Test
    public void shouldDoNothing_WhenNotFinished() throws Exception {
        /*mQuickSort.setRunning(true);

        mQuickSort.run();*/

        Assert.assertTrue(false);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_WhenSetElementsWhileAlgorithmIsRunning() throws Exception {
        mMergeSort.setRunning(true);
        mMergeSort.setElements(new int[0]);
    }
}