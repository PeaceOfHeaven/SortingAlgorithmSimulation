package universe.sortalgorithmssimulation.activity;

import com.github.mikephil.charting.data.Entry;

import java.util.List;

/**
 * Created by Nhat on 6/2/2017.
 */

public class ComplexityDataLineChart {

    private List<Entry> mEntries;
    private int mColor;

    public ComplexityDataLineChart(List<Entry> entries, int color) {
        mEntries = entries;
        mColor = color;
    }

    public List<Entry> getEntries() {
        return mEntries;
    }

    public int getColor() {
        return mColor;
    }
}
