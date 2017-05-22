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
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.transitionseverywhere.TransitionManager;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;
import universe.sortalgorithmssimulation.R;
import universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithm;

public class TestActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Typeface productSansRegular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getWindow().setBackgroundDrawable(null);

        productSansRegular = Typeface.createFromAsset(getAssets(), "fonts/ProductSans-Regular.ttf");

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        findViewById(R.id.toolbar).setBackground(null);

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

        String[] alogrithmsName = getResources().getStringArray(R.array.selection_algorithms_name);
        int[] alogrithmsColor = getResources().getIntArray(R.array.selection_algorithms_color);

        List<SortAlgorithm> sortAlgorithms = new ArrayList<>();
        if(alogrithmsName.length == alogrithmsColor.length) {
            for (int i = 0; i < alogrithmsColor.length; i++) {
                sortAlgorithms.add(new SortAlgorithm(alogrithmsName[i], "", alogrithmsColor[i]));
            }
            /*sortAlgorithms.add(new SortAlgorithm("Bubble Sort", "", R.color.orange));
            sortAlgorithms.add(new SortAlgorithm("Insertion Sort", "", R.color.green));
            sortAlgorithms.add(new SortAlgorithm("Binary Insertion Sort", "", R.color.yellow));
            sortAlgorithms.add(new SortAlgorithm("Selection Sort", "", R.color.purple));
            sortAlgorithms.add(new SortAlgorithm("Quick Sort", "", R.color.pink));
            sortAlgorithms.add(new SortAlgorithm("Merge Sort", "", R.color.colorPrimary));*/
        }

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(sortAlgorithms);
        mRecyclerView.setAdapter(mAdapter);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<SortAlgorithm> mSortAlgorithms;

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

            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.txtView_algorithm_title);
                mTextView.setTypeface(productSansRegular);
                mMaterialLetterIcon = (MaterialLetterIcon) v.findViewById(R.id.icon_algorithm_letter);
                mDescriptionTxtView = (TextView) v.findViewById(R.id.txtView_algorithm_description);
                mSimulate = v.findViewById(R.id.simulate);
            }
        }

        // Provide elements suitable constructor (depends on the kind of dataset)
        public MyAdapter(List<SortAlgorithm> sortAlgorithms) {
            mSortAlgorithms = sortAlgorithms;

        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout, parent, false);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        // Replace the contents of elements view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            SortAlgorithm sortAlgorithm = mSortAlgorithms.get(position);
            holder.mTextView.setText(sortAlgorithm.getTitle());
            // holder.mMaterialLetterIcon.setShapeColor(getResources().getColor(sortAlgorithm.getColor()));
            holder.mMaterialLetterIcon.setShapeColor(sortAlgorithm.getColor());
            holder.mMaterialLetterIcon.setLetter(sortAlgorithm.getTitle().substring(0, 1));

            SpannableStringBuilder cs = new SpannableStringBuilder();
            cs.append("Worst-case : O(n2)\n");
            int start = cs.length() - 3;
            cs.setSpan(new SuperscriptSpan(), start, start + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cs.setSpan(new RelativeSizeSpan(0.6f), start, start + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            cs.append("Best-case : O(n)\n");

            cs.append("Average : O(n2)\n");
            start = cs.length() - 3;
            cs.setSpan(new SuperscriptSpan(), start, start + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cs.setSpan(new RelativeSizeSpan(0.6f), start, start + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            cs.append("Worst-case space : O(1)");

            holder.mDescriptionTxtView.setText(cs);
            /*holder.mDescriptionTxtView.setText(Html.fromHtml("X<sup>2</sup>")+"\nWorst-case performance : O(" + Html.fromHtml("n<sup>2</sup>") +
                    ")\nBest-case performance\t{\\displaystyle O(n)} O(n)\n" +
                    "Average performance\t{\\displaystyle O(n^{2})} O(n^{2})");*/

            final boolean isExpanded = position == mExpandedPosition;
            holder.mDescriptionTxtView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            holder.mSimulate.setVisibility(isExpanded ? View.VISIBLE : View.INVISIBLE);
            holder.itemView.setActivated(isExpanded);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExpandedPosition = isExpanded ? -1 : position;
                    TransitionManager.beginDelayedTransition(mRecyclerView);
                    notifyDataSetChanged();
                }
            });

            holder.mSimulate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(TestActivity.this, CoorActivity.class));
                }
            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mSortAlgorithms.size();
        }
    }
}
