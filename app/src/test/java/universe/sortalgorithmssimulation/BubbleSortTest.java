package universe.sortalgorithmssimulation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import universe.sortalgorithmssimulation.sorting_algorithms.BubbleSort;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by Nhat on 4/7/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class BubbleSortTest {

    @Mock
    BubbleSort.StateCallback mSortView;

    @Mock
    BubbleSort.BubbleSortCodeView mSortCodeView;

    BubbleSort mBubbleSort;

    @Before
    public void setUp() throws Exception {
        mBubbleSort = new BubbleSort();
    }

    @Test
    public void sorting_isCorrect() throws Exception {
        int[] input = new int[] {5, 3, 7, 1, 4, 8};

        mBubbleSort.setElements(input);
        mBubbleSort.run();

        int[] expected = {1,3,4,5,7,8};
        int[] actual = mBubbleSort.getElements();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void sorting_OneElement() {
        int[] input = new int[] {5};

        mBubbleSort.setElements(input);
        mBubbleSort.run();

        int[] expected = {5};
        int[] actual = mBubbleSort.getElements();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void sorting_EmptyArray() throws Exception {
        int[] input = new int[0];

        mBubbleSort.setElements(input);
        mBubbleSort.run();

        int[] expected = {};
        int[] actual = mBubbleSort.getElements();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void sorting_ReverseSortedElements() {
        int[] input = new int[] {8,6,5,3,2,1};

        mBubbleSort.setElements(input);
        mBubbleSort.run();

        int[] expected = {1,2,3,5,6,8};
        int[] actual = mBubbleSort.getElements();
        assertArrayEquals(expected, actual);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_WhenSetElementsWhileAlgorithmIsRunning() throws Exception {
        //mBubbleSort.setRunning(true);

        mBubbleSort.setElements(new int[0]);
    }
}
