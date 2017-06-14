package universe.sortalgorithmssimulation.utils;

import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Nhat on 6/2/2017.
 */

public final class ElementGenerator {

    private static final int MAX = 99;
    public static final int SORTED = 1;
    public static final int REVERSED = 2;
    public static final int FEW_UNIQUE = 3;
    public static final int RANDOM = 4;

    private ElementGenerator() {
    }

    /**
     *
     * @param choice one of {@link #SORTED}, {@link #REVERSED}, {@link #FEW_UNIQUE} or
     *               {@link #RANDOM}
     * @return elements fit with choice otherwise null
     */
    @Nullable
    public static int[] generate(int size, int choice) {
        int[] elements = null;
        switch (choice) {
            case SORTED:
                elements = generateSorted(size);
                break;
            case REVERSED:
                elements = generateReversed(size);
                break;
            case FEW_UNIQUE:
                elements = generateFewUnique(size);
                break;
            case RANDOM:
                elements = generateRandom(size);
                break;
        }
        return elements;
    }

    private static int[] generateSorted(int size) {
        int[] a = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            a[i] = (random.nextInt(MAX) + 1);
        }
        Arrays.sort(a);
        return a;
    }

    private static int[] generateReversed(int size) {
        int[] a = generateSorted(size);
        for (int i = 0; i < a.length/2; i++)
        {
            a[i] = a[a.length-1-i] + a[i] ;
            a[a.length-1-i] = a[i] - a[a.length-1-i];
            a[i] = a[i] - a[a.length-1-i] ;
        }
        return a;
    }

    private static int[] generateFewUnique(int size) {
        int[] a = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            a[i] = random.nextInt(3) + random.nextInt(4);
        }
        return a;
    }

    private static int[] generateRandom(int size) {
        int[] a = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            a[i] = random.nextInt(MAX) + 1;
        }
        return a;
    }
}
