package universe.sortalgorithmssimulation.sorting_algorithms;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by Nhat on 5/23/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class InsertionSortTest {
    @Mock
    InsertionSort.Callback mCallback;
    InsertionSort mInsertionSort;

    @Before
    public void setUp() throws Exception {
        mInsertionSort = new InsertionSort();
        mInsertionSort.setCallback(mCallback);
    }

    @Test
    public void shouldReturnSortedElements1() throws Exception {
        int[] elements = new int[]{8, 6, 5, 3, 2, 10, 0};
        mInsertionSort.setElements(elements);

        mInsertionSort.run();

        int[] expected = new int[]{0, 2, 3, 5, 6, 8, 10};
        Mockito.verify(mCallback).onFinished(expected);
    }

    @Test
    public void shouldReturnSortedElements2() throws Exception {
        int[] elements = new int[]{10, 8, 6, 5, 3, 2, 0};
        mInsertionSort.setElements(elements);

        mInsertionSort.run();

        int[] expected = new int[]{0, 2, 3, 5, 6, 8, 10};
        Mockito.verify(mCallback).onFinished(expected);
    }

    @Test
    public void shouldReturnSortedElements3() throws Exception {
        int[] elements = new int[]{0, 8, 3, 5, 3, 2, 0};
        mInsertionSort.setElements(elements);

        mInsertionSort.run();

        int[] expected = new int[]{0, 0, 2, 3, 3, 5, 8};
        Mockito.verify(mCallback).onFinished(expected);
    }

    @Test
    public void shouldReturnSortedElements4() throws Exception {
        int[] elements = new int[]{0, 0, 2, 3, 3, 5, 8};
        mInsertionSort.setElements(elements);

        mInsertionSort.run();

        int[] expected = new int[]{0, 0, 2, 3, 3, 5, 8};
        Mockito.verify(mCallback, Mockito.never()).onShiftRightElementByOne(0);
        Mockito.verify(mCallback, Mockito.never()).onShiftRightElementByOne(3);
        Mockito.verify(mCallback, Mockito.never()).onShiftRightElementByOne(6);
        Mockito.verify(mCallback).onFinished(expected);
    }

    @Test
    public void shouldDoNothing_WhenNotFinished() throws Exception {
        mInsertionSort.setRunning(true);

        mInsertionSort.run();

        Mockito.verify(mCallback, Mockito.never()).onCompare(0, true);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_WhenSetElementsWhileAlgorithmIsRunning() throws Exception {
        mInsertionSort.setRunning(true);

        mInsertionSort.setElements(new int[0]);
    }
}