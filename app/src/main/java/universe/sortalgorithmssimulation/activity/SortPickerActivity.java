package universe.sortalgorithmssimulation.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import universe.sortalgorithmssimulation.R;
import universe.sortalgorithmssimulation.SortAlogirthmsApplication;
import universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo;
import universe.sortalgorithmssimulation.utils.Utils;

import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Complexity.BIG_O_N;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Complexity.BIG_O_N2;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Complexity.BIG_O_NLOGN;

public class SortPickerActivity extends AppCompatActivity {

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.fab_play)
    FloatingActionButton mFab;

    @BindView(R.id.gifImageView)
    ImageView mGifImageView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SparseArrayCompat<LineData> mBunchOfLineDatas = new SparseArrayCompat<>(SortAlogirthmsApplication.NUM_OF_ALGORITHMS);
    private List<SortAlgorithmInfo> mSortAlgorithms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_picker);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(null);

        findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        final CollapsingToolbarLayout toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar.setTitle(getString(R.string.selection_appbar_title));

        ((AppBarLayout) findViewById(R.id.app_bar)).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == -appBarLayout.getTotalScrollRange()) {
                    toolbar.setTitleEnabled(true);
                    mGifImageView.setVisibility(View.INVISIBLE);
                } else {
                    toolbar.setTitleEnabled(false);
                    mGifImageView.setVisibility(View.VISIBLE);
                }
                Timber.d("Vertical offset : " + verticalOffset);
                Timber.d("TotalScrollRange : " + appBarLayout.getTotalScrollRange());
            }
        });

        mRecyclerView.smoothScrollToPosition(5);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Timber.d(dx + "-" + dy);
            }
        });

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use elements linear layout manager
        mLayoutManager = new LinearLayoutManager(this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSortAlgorithms = SortAlogirthmsApplication.getSortAlgorithms();
        loadAlgorithmComplexity();
        mAdapter = new MyAdapter(mSortAlgorithms);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadAlgorithmComplexity() {
        if(mSortAlgorithms == null && mSortAlgorithms.isEmpty()) {
            return;
        }
        ComplexityDataLineChart bigON = new ComplexityDataLineChart(FileUtils.loadEntriesFromAssets(getAssets(), "n.txt")
                                            , ColorTemplate.VORDIPLOM_COLORS[0]);
        ComplexityDataLineChart bigONLOGN = new ComplexityDataLineChart(FileUtils.loadEntriesFromAssets(getAssets(), "nlogn.txt")
                                            , ColorTemplate.VORDIPLOM_COLORS[1]);
        ComplexityDataLineChart bigON2 = new ComplexityDataLineChart(FileUtils.loadEntriesFromAssets(getAssets(), "square.txt")
                                            , ColorTemplate.VORDIPLOM_COLORS[2]);

        SparseArrayCompat<ComplexityDataLineChart> complexityDataCategories = new SparseArrayCompat<>(3);
        complexityDataCategories.put(BIG_O_N, bigON);
        complexityDataCategories.put(BIG_O_N2, bigON2);
        complexityDataCategories.put(BIG_O_NLOGN, bigONLOGN);

        //"O(n\u00B2)"
        for (SortAlgorithmInfo sortAlgorithm : mSortAlgorithms) {
            List<ILineDataSet> lineDataSets = new ArrayList<>(3);
            ComplexityDataLineChart complexityData = complexityDataCategories.get(sortAlgorithm.getBestcaseComplexity());
            LineDataSet bestCaseComplexity = new LineDataSet(complexityData.getEntries(), "Best-case");
            bestCaseComplexity.setColor(complexityData.getColor());

            complexityData = complexityDataCategories.get(sortAlgorithm.getAveragecaseComplexity());
            LineDataSet averageCaseComplexity = new LineDataSet(complexityData.getEntries(), "Average-case");
            averageCaseComplexity.setColor(complexityData.getColor());

            complexityData = complexityDataCategories.get(sortAlgorithm.getWorstcaseComplexity());
            LineDataSet worstCaseComplexity = new LineDataSet(complexityData.getEntries(), "Worst-case");
            worstCaseComplexity.setColor(complexityData.getColor());

            bestCaseComplexity.setLineWidth(5.0f);
            bestCaseComplexity.setDrawCircles(false);
            averageCaseComplexity.setLineWidth(5.0f);
            averageCaseComplexity.setDrawCircles(false);
            worstCaseComplexity.setLineWidth(5.0f);
            worstCaseComplexity.setDrawCircles(false);

            lineDataSets.add(bestCaseComplexity);
            lineDataSets.add(averageCaseComplexity);
            lineDataSets.add(worstCaseComplexity);
            mBunchOfLineDatas.put(sortAlgorithm.getType(), new LineData(lineDataSets));
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<SortAlgorithmInfo> mSortAlgorithmInfos;

        private int mExpandedPosition = -1;

        // Provide elements reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for elements data item in elements view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just elements string in this case
            @BindView(R.id.txtView_algorithm_title)
            public TextView mTextView;

            @BindView(R.id.icon_algorithm_letter)
            public MaterialLetterIcon mMaterialLetterIcon;

            @BindView(R.id.lineChart1)
            public LineChart mChart;

            public ViewHolder(View v) {
                super(v);
                ButterKnife.bind(this, v);

                mChart.getDescription().setEnabled(false);
                mChart.setDragEnabled(false);
                mChart.setDoubleTapToZoomEnabled(false);
                mChart.setDrawGridBackground(false);
                mChart.setScaleXEnabled(false);
                mChart.setScaleYEnabled(false);
                mChart.setTouchEnabled(false);
                mChart.getAxisRight().setEnabled(false);
                mChart.setExtraBottomOffset(16);

                Legend legend = mChart.getLegend();
                legend.setWordWrapEnabled(true);
                legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

                YAxis leftYAxis = mChart.getAxisLeft();
                leftYAxis.setDrawLabels(false);
                leftYAxis.setDrawGridLines(true);
                leftYAxis.setSpaceBottom(0);
                leftYAxis.setAxisMaximum(500);
                leftYAxis.setDrawTopYLabelEntry(true);

                XAxis bottomXAxis = mChart.getXAxis();
                bottomXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                bottomXAxis.setDrawGridLines(true);
                bottomXAxis.setDrawLabels(false);
            }
        }

        // Provide elements suitable constructor (depends on the kind of dataset)
        public MyAdapter(List<SortAlgorithmInfo> sortAlgorithmInfos) {
            mSortAlgorithmInfos = sortAlgorithmInfos;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sort_item, parent, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        // Replace the contents of elements view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final SortAlgorithmInfo sortAlgorithmInfo = mSortAlgorithmInfos.get(position);

            holder.mTextView.setText(sortAlgorithmInfo.getTitle());
            holder.mMaterialLetterIcon.setShapeColor(getResources().getColor(Utils.getColorId(sortAlgorithmInfo.getType())));
            holder.mMaterialLetterIcon.setLetter(sortAlgorithmInfo.getTitle().substring(0, 1));
            LineData lineData = mBunchOfLineDatas.get(sortAlgorithmInfo.getType());
            ((LineDataSet) lineData.getDataSetByIndex(0)).setLineWidth(3.0f);
            ((LineDataSet) lineData.getDataSetByIndex(1)).setLineWidth(3.0f);
            ((LineDataSet) lineData.getDataSetByIndex(2)).setLineWidth(3.0f);
            holder.mChart.setData(lineData);

            final boolean isExpanded = position == mExpandedPosition;
            holder.itemView.setActivated(isExpanded);
            holder.mChart.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExpandedPosition = isExpanded ? -1 : position;
                    if (mExpandedPosition != -1) {
                        mFab.show();
                        mFab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(SimulationActivity
                                        .getStarterIntent(SortPickerActivity.this
                                                , sortAlgorithmInfo.getType()));
                            }
                        });
                    } else {
                        mFab.hide();
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        TransitionManager.beginDelayedTransition(mRecyclerView);
                    } else {
                        com.transitionseverywhere.TransitionManager.beginDelayedTransition(mRecyclerView);
                    }
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mSortAlgorithmInfos.size();
        }
    }
}
