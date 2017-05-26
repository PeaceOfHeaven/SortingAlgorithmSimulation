package universe.sortalgorithmssimulation.sorting_algorithms;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by Nhat on 5/25/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectionSortTest {
    @Mock
    SelectionSort.Callback mCallback;
    SelectionSort mSelectionSort;

    @Before
    public void setUp() throws Exception {
        mSelectionSort = new SelectionSort();
        mSelectionSort.setCallback(mCallback);
    }

    @Test
    public void shouldReturnSortedElements1() throws Exception {
        int[] elements = new int[]{8, 6, 5, 3, 2, 10, 0};
        mSelectionSort.setElements(elements);

        mSelectionSort.run();

        int[] expected = new int[]{0, 2, 3, 5, 6, 8, 10};
        Mockito.verify(mCallback).onFinished(expected);
    }

    @Test
    public void shouldReturnSortedElements2() throws Exception {
        int[] elements = new int[]{10, 8, 6, 5, 3, 2, 0};
        mSelectionSort.setElements(elements);

        mSelectionSort.run();

        int[] expected = new int[]{0, 2, 3, 5, 6, 8, 10};
        Mockito.verify(mCallback).onFinished(expected);
    }

    @Test
    public void shouldReturnSortedElements3() throws Exception {
        int[] elements = new int[]{0, 8, 3, 5, 3, 2, 0};
        mSelectionSort.setElements(elements);

        mSelectionSort.run();

        int[] expected = new int[]{0, 0, 2, 3, 3, 5, 8};
        Mockito.verify(mCallback).onFinished(expected);
    }

    @Test
    public void shouldReturnSortedElements4() throws Exception {
        int[] elements = new int[]{0, 0, 2, 3, 3, 5, 8};
        mSelectionSort.setElements(elements);

        mSelectionSort.run();

        int[] expected = new int[]{0, 0, 2, 3, 3, 5, 8};
        Mockito.verify(mCallback, Mockito.never()).onSwap(0, 0);
        Mockito.verify(mCallback).onFinished(expected);
    }

    @Test
    public void shouldDoNothing_WhenNotFinished() throws Exception {
        mSelectionSort.setRunning(true);

        mSelectionSort.run();

        Mockito.verify(mCallback, Mockito.never()).onCompare(0, true);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_WhenSetElementsWhileAlgorithmIsRunning() throws Exception {
        mSelectionSort.setRunning(true);
        mSelectionSort.setElements(new int[0]);
    }
}
