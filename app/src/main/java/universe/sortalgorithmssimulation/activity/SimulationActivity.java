package universe.sortalgorithmssimulation.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;
import universe.sortalgorithmssimulation.R;
import universe.sortalgorithmssimulation.SortAlogirthmsApplication;
import universe.sortalgorithmssimulation.activity.presenters.BaseSortPresenter;
import universe.sortalgorithmssimulation.activity.presenters.BinaryInsertionSortPresenter;
import universe.sortalgorithmssimulation.activity.presenters.BubbleSortPresenter;
import universe.sortalgorithmssimulation.activity.presenters.InsertionSortPresenter;
import universe.sortalgorithmssimulation.activity.presenters.SelectionSortPresenter;
import universe.sortalgorithmssimulation.activity.views.BaseSortView;
import universe.sortalgorithmssimulation.activity.views.BinaryInsertionSortView;
import universe.sortalgorithmssimulation.activity.views.BubbleSortView;
import universe.sortalgorithmssimulation.activity.views.InsertionSortView;
import universe.sortalgorithmssimulation.activity.views.SelectionSortView;
import universe.sortalgorithmssimulation.sorting_algorithms.BaseSortAlgorithm;
import universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo;

import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.BINARY_INSERTION_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.BUBBLE_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.INSERTION_SORT;
import static universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo.Type.SELECTION_SORT;

public class SimulationActivity extends AppCompatActivity implements SurfaceHolder.Callback, BaseSortPresenter.MainView {

    @BindView(R.id.surface_container)
    FrameLayout surfaceContainer;

    @BindView(R.id.btn_pause_resume)
    FloatingActionButton pauseResumeFab;

    private BaseSortPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coor);
        getWindow().setBackgroundDrawable(null);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", -1);
        SortAlgorithmInfo sortAlgorithmInfo = SortAlogirthmsApplication.getSortAlgorithm(type);
        if (sortAlgorithmInfo == null) {
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(sortAlgorithmInfo.getTitle());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(R.id.elevation_pre_lollipop).setVisibility(View.GONE);
        }

        BaseSortView surface = getView(type);
        surface.getHolder().addCallback(this);

        BaseSortAlgorithm sortAlgorithmExecutable = SortAlogirthmsApplication
                .getSortAlgorithm(type).getAlgorithmExecuteable();

        int[] elements = new int[]{8, 6, 1, 3, 2, 10, 4};
        mPresenter = getPresenter(type, elements, sortAlgorithmExecutable, surface);

        surfaceContainer.addView(surface);
    }

    private BaseSortPresenter getPresenter(int type, int[] elements,
                                           BaseSortAlgorithm sortAlgorithmExecutable,
                                           BaseSortView view) {
        switch (type) {
            case BUBBLE_SORT:
                return new BubbleSortPresenter(sortAlgorithmExecutable, elements, this, view);
            case INSERTION_SORT:
                return new InsertionSortPresenter(sortAlgorithmExecutable, elements, this, view);
            case BINARY_INSERTION_SORT:
                return new BinaryInsertionSortPresenter(sortAlgorithmExecutable, elements, this, view);
            case SELECTION_SORT:
                return new SelectionSortPresenter(sortAlgorithmExecutable, elements, this, view);
        }
        return null;
    }

    private BaseSortView getView(int type) {
        switch (type) {
            case BUBBLE_SORT:
                return new BubbleSortView(this);
            case INSERTION_SORT:
                return new InsertionSortView(this);
            case BINARY_INSERTION_SORT:
                return new BinaryInsertionSortView(this);
            case SELECTION_SORT:
                return new SelectionSortView(this);
        }
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Timber.d("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.d("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Timber.d("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Timber.d("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.coor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.info_action:
                startActivity(new Intent(this, InfoActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_pause_resume)
    public void togglePauseResume() {
        /*if(mPresenter.isRunning()) {
            if(mPresenter.isPaused()) {
                mPresenter.resume();
            } else {
                mPresenter.pause();
            }
        } else {
            mPresenter.start();
        }*/

        if(mPresenter.isPaused()) {
            mPresenter.start();
        } else {
            mPresenter.pause();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        Timber.d("surfaceCreated");
        mPresenter.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        mPresenter.stop();
        Timber.d("surfaceDestroyed");
    }


    @Override
    public void togglePauseFab(final boolean active) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (active) {
                    pauseResumeFab.setImageResource(R.drawable.play);
                } else {
                    pauseResumeFab.setImageResource(R.drawable.pause);
                }
            }
        });
    }

    @Override
    public void toggleFinished() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pauseResumeFab.setImageResource(R.drawable.ic_replay_black_24dp);
            }
        });
    }
}
