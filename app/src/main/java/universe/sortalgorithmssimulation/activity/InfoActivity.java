package universe.sortalgorithmssimulation.activity;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.commit451.elasticdragdismisslayout.ElasticDragDismissFrameLayout;
import com.commit451.elasticdragdismisslayout.ElasticDragDismissListener;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import universe.sortalgorithmssimulation.R;
import universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithm;

public class InfoActivity extends AppCompatActivity {

    @BindView(R.id.pager_info)
    ViewPager mInfoPager;

    @BindView(R.id.draggable_frame)
    ElasticDragDismissFrameLayout mDragDismissFrameLayout;

    @BindView(R.id.viewpagertab)
    SmartTabLayout mSmartTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);

        mInfoPager.setAdapter(new InfoAdapter(getSupportFragmentManager()));
        mInfoPager.setOffscreenPageLimit(2);

        mDragDismissFrameLayout.addListener(new ElasticDragDismissListener() {
            @Override
            public void onDrag(float elasticOffset, float elasticOffsetPixels, float rawOffset, float rawOffsetPixels) {

            }

            @Override
            public void onDragDismissed() {
                if (mDragDismissFrameLayout.getTranslationY() > 0 && Build.VERSION.SDK_INT >= 21) {
                    getWindow().setReturnTransition(
                            TransitionInflater.from(InfoActivity.this)
                                    .inflateTransition(R.transition.about_return_downward));
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            mDragDismissFrameLayout.addListener(new SystemChromeFader(this));
        }

        mSmartTabLayout.setViewPager(mInfoPager);
    }

    public class InfoAdapter extends FragmentStatePagerItemAdapter {

        static final int NUM_ITEMS = 3;

        private SortAlgorithm mSortAlgorithm;

        public InfoAdapter(FragmentManager fm) {
            super(fm, FragmentPagerItems.with(InfoActivity.this)
                    .add("Info", InfoPage.class)
                    .add("Pseducode", InfoPage.class)
                    .add("Time", InfoPage.class).create());
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment infoPage = null;
            String content = "";
            switch (position) {
                case 0:
                    content = getString(R.string.info_buble_sort_idea);
                    infoPage = InfoPage.newInstance(content);
                    break;
                case 1:
                    content = getString(R.string.info_buble_sort_pesudo);
                    infoPage = InfoPage.newInstance(content);
                    break;
                case 2:
                    infoPage = new ComplexityFragment();
                    break;
            }
            return infoPage;
        }
    }

    public static class InfoPage extends Fragment {

        private static final String CONTENT_KEY = "content";

        public static InfoPage newInstance(String content) {
            InfoPage infoPage = new InfoPage();
            Bundle bundle = new Bundle();
            bundle.putString(CONTENT_KEY, content);
            infoPage.setArguments(bundle);

            return infoPage;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_info, container, false);
            TextView contentTv = (TextView) view;
            Typeface georgia = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GothamRoundedBook.ttf");
            contentTv.setTypeface(georgia);
            contentTv.setText(getArguments().getString(CONTENT_KEY));
            return view;
        }
    }
}
