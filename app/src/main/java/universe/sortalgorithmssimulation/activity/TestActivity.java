package universe.sortalgorithmssimulation.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.transitionseverywhere.TransitionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import universe.sortalgorithmssimulation.R;
import universe.sortalgorithmssimulation.SortAlogirthmsApplication;
import universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo;
import universe.sortalgorithmssimulation.utils.Utils;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Typeface productSansRegular;

    private LineData mLineData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(null);

        mLineData = getComplexity();
        productSansRegular = Typeface.createFromAsset(getAssets(), "fonts/ProductSans-Regular.ttf");

        findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        final CollapsingToolbarLayout toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar.setTitle(getString(R.string.selection_appbar_title));

        ((AppBarLayout) findViewById(R.id.app_bar)).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset == -appBarLayout.getTotalScrollRange()) {
                    toolbar.setTitleEnabled(true);
                    findViewById(R.id.gifImageView).setVisibility(View.INVISIBLE);
                } else {
                    toolbar.setTitleEnabled(false);
                    findViewById(R.id.gifImageView).setVisibility(View.VISIBLE);
                }
                Timber.d("Vertical offset : " + verticalOffset);
                Timber.d("TotalScrollRange : " + appBarLayout.getTotalScrollRange());
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Timber.d("Idle");
                } else if(newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    Timber.d("Dragging");
                } else if(newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    Timber.d("Settling");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Timber.d(dx+"-"+dy);
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

        List<SortAlgorithmInfo> sortAlgorithmInfos = SortAlogirthmsApplication.getSortAlgorithms();
        mAdapter = new MyAdapter(sortAlgorithmInfos);
        mRecyclerView.setAdapter(mAdapter);
    }

    private LineData getComplexity() {
        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();

        LineDataSet ds1 = new LineDataSet(FileUtils.loadEntriesFromAssets(getAssets(), "n.txt"), "Best-case: O(n)");
        LineDataSet ds2 = new LineDataSet(FileUtils.loadEntriesFromAssets(getAssets(), "square.txt"), "Worst-and-Average-case: O(n\u00B2)");
        LineDataSet ds3 = new LineDataSet(FileUtils.loadEntriesFromAssets(getAssets(), "nlogn.txt"), "O(nlogn)");
        LineDataSet ds4 = new LineDataSet(FileUtils.loadEntriesFromAssets(getAssets(), "three.txt"), "O(n\u00B3)");

        ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        ds2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);

        ds1.setLineWidth(5.0f);
        ds1.setDrawCircles(false);

        ds2.setLineWidth(5.0f);
        ds2.setDrawCircles(false);

        // load DataSets from textfiles in assets folders
        sets.add(ds1);
        sets.add(ds2);

        LineData d = new LineData(sets);
        return d;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<SortAlgorithmInfo> mSortAlgorithmInfos;

        private int mExpandedPosition = -1;

        // Provide elements reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for elements data item in elements view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just elements string in this case
            public TextView mTextView;
            public MaterialLetterIcon mMaterialLetterIcon;
            public TextView mDescriptionTxtView;
            public View mSimulate;
            public LineChart mChart;

            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.txtView_algorithm_title);
                // mTextView.setTypeface(productSansRegular);
                mMaterialLetterIcon = (MaterialLetterIcon) v.findViewById(R.id.icon_algorithm_letter);
                mSimulate = v.findViewById(R.id.simulate);
                mChart = (LineChart) v.findViewById(R.id.lineChart1);

                mChart.getDescription().setEnabled(false);
                mChart.setDragEnabled(false);
                mChart.setDoubleTapToZoomEnabled(false);
                mChart.setDrawGridBackground(false);
                mChart.setScaleXEnabled(false);
                mChart.setScaleYEnabled(false);
                mChart.setTouchEnabled(false);
                mChart.getAxisRight().setEnabled(false);
                mChart.setExtraBottomOffset(16);
                mChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);

                YAxis leftYAxis = mChart.getAxisLeft();
                leftYAxis.setDrawLabels(false);
                leftYAxis.setDrawGridLines(true);
                leftYAxis.setSpaceBottom(0);

                XAxis bottomXAxis = mChart.getXAxis();
                bottomXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                bottomXAxis.setDrawGridLines(true);
                bottomXAxis.setDrawLabels(false);

                ((LineDataSet) mLineData.getDataSetByIndex(0)).setLineWidth(3.0f);
                ((LineDataSet) mLineData.getDataSetByIndex(1)).setLineWidth(3.0f);
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout, parent, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        // Replace the contents of elements view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            final SortAlgorithmInfo sortAlgorithmInfo = mSortAlgorithmInfos.get(position);
            holder.mTextView.setText(sortAlgorithmInfo.getTitle());
            holder.mMaterialLetterIcon.setShapeColor(getResources().getColor(Utils.getColorId(sortAlgorithmInfo.getType())));
            holder.mMaterialLetterIcon.setLetter(sortAlgorithmInfo.getTitle().substring(0, 1));
            holder.mChart.setData(mLineData);
            /*SpannableStringBuilder cs = new SpannableStringBuilder();
            cs.append("Worst-case : O(n2)\n");
            int start = cs.length() - 3;
            cs.setSpan(new SuperscriptSpan(), start, start + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cs.setSpan(new RelativeSizeSpan(0.6f), start, start + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            cs.append("Best-case : O(n)\n");

            cs.append("Average : O(n2)\n");
            start = cs.length() - 3;
            cs.setSpan(new SuperscriptSpan(), start, start + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cs.setSpan(new RelativeSizeSpan(0.6f), start, start + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            cs.append("Worst-case space : O(1)");*/

            final boolean isExpanded = position == mExpandedPosition;
            holder.mSimulate.setVisibility(isExpanded ? View.VISIBLE : View.INVISIBLE);
            holder.itemView.setActivated(isExpanded);
            holder.mChart.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExpandedPosition = isExpanded ? -1 : position;
                    TransitionManager.beginDelayedTransition(mRecyclerView);
                    notifyDataSetChanged();
                    // notifyItemChanged(position);
                }
            });

            holder.mSimulate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(sortAlgorithmInfo.getAlgorithmExecuteable() != null) {
                        Intent intent = new Intent(TestActivity.this, CoorActivity.class);
                        intent.putExtra("type", sortAlgorithmInfo.getType());
                        startActivity(intent);
                    }
                }
            });
        }
        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mSortAlgorithmInfos.size();
        }
    }
}
