package universe.sortalgorithmssimulation.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import universe.sortalgorithmssimulation.R;


public class ComplexityFragment extends SimpleFragment {

    public static Fragment newInstance() {
        return new ComplexityFragment();
    }

    private LineChart mChart;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_simple_line, container, false);
        
        mChart = (LineChart) v.findViewById(R.id.lineChart1);

        mChart.getDescription().setEnabled(false);
        mChart.setDragEnabled(true);
        mChart.setDoubleTapToZoomEnabled(false);
        mChart.setDrawGridBackground(true);
        mChart.setScaleXEnabled(true);
        mChart.setScaleYEnabled(true);

        mChart.setData(getComplexity());
        mChart.animateX(1000);
        
        //Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"OpenSans-Light.ttf");
        
        YAxis leftYAxis = mChart.getAxisLeft();
        leftYAxis.setDrawLabels(false);
        leftYAxis.setDrawGridLines(false);
        leftYAxis.setSpaceBottom(0);
        
        mChart.getAxisRight().setEnabled(false);

        XAxis bottomXAxis = mChart.getXAxis();
        bottomXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        bottomXAxis.setDrawGridLines(false);
        bottomXAxis.setDrawLabels(false);

        mChart.setExtraBottomOffset(16);
        mChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        return v;
    }
}
