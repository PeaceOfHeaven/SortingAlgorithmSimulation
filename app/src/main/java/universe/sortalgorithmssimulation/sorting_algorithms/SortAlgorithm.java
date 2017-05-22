package universe.sortalgorithmssimulation.sorting_algorithms;

import android.content.res.Resources;

import universe.sortalgorithmssimulation.R;

/**
 * Created by Nhat on 4/11/2017.
 */

public class SortAlgorithm {

    interface Type {
        int BUBBLE_SORT = 1;
    }

    private String title;
    private String description;
    private String idea;
    private String pseducode;
    private String timeWorstCase;
    private String timeAvgCase;
    private String timeBestCase;
    private int color;

    private SortAlgorithm() {
    }

    public SortAlgorithm(String title, String description, int color) {
        this.title = title;
        this.description = description;
        this.color = color;
    }

    public static SortAlgorithm getInstance(int type, Resources res) {
        SortAlgorithm sortAlgorithm = new SortAlgorithm();
        String title;
        String idea;
        String description;
        int color;

        switch (type) {
            case Type.BUBBLE_SORT:
                sortAlgorithm.title = "Bubble Sort";
                sortAlgorithm.idea = res.getString(R.string.info_buble_sort_idea);
                sortAlgorithm.pseducode = res.getString(R.string.info_buble_sort_pesudo);
                break;
        }

        return null;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getColor() {
        return color;
    }

}
