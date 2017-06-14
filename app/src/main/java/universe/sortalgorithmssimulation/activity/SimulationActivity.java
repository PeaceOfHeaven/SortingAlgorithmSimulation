package universe.sortalgorithmssimulation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import universe.sortalgorithmssimulation.activity.presenters.BaseSortPresenter;
import universe.sortalgorithmssimulation.activity.views.BaseSortView;
import universe.sortalgorithmssimulation.sorting_algorithms.SortAlgorithmInfo;
import universe.sortalgorithmssimulation.utils.ElementGenerator;

public class SimulationActivity extends AppCompatActivity implements SurfaceHolder.Callback, BaseSortPresenter.MainView {

    private final int MAX_SIZE = 7;
    private static final String EXTRA_KEY_TYPE = "type";

    @BindView(R.id.surface_container)
    FrameLayout surfaceContainer;

    @BindView(R.id.btn_pause_resume)
    FloatingActionButton pauseResumeFab;

    private BaseSortPresenter mPresenter;
    BaseSortView surface;
    private int[] mCurrentElements;
    private Menu mMenu;

    public static Intent getStarterIntent(Context context, int sortAlgorithmType) {
        Intent intent = new Intent(context, SimulationActivity.class);
        intent.putExtra(EXTRA_KEY_TYPE, sortAlgorithmType);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_simulation);
        getWindow().setBackgroundDrawable(null);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        int type = intent.getIntExtra(EXTRA_KEY_TYPE, -1);
        SortAlgorithmInfo sortAlgorithmInfo = SortAlgorithmInfo.getInstance(type);
        if (sortAlgorithmInfo == null) {
            finish();
        }

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(sortAlgorithmInfo.getTitle());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(R.id.elevation_pre_lollipop).setVisibility(View.GONE);
        }

        int[] elements = ElementGenerator.generate(MAX_SIZE, ElementGenerator.RANDOM);
        mPresenter = SortPresenterFactory.provideSortPresenter(this, this, type);
        mPresenter.setElements(elements);

        surface = (BaseSortView) mPresenter.getSView();
        surface.getHolder().addCallback(this);
        surfaceContainer.addView(surface);
        Timber.d("onCreate");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString("nhat", "nhat");
        super.onSaveInstanceState(outState, outPersistentState);
        Timber.d("onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Timber.d("onRestoreInstanceState");
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
        surface.getHolder().removeCallback(this);
        mPresenter.stop();
        super.onDestroy();
        Timber.d("onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_simulation, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_random:
                mCurrentElements = ElementGenerator.generate(MAX_SIZE, ElementGenerator.RANDOM);
                break;
            case R.id.action_reversed:
                mCurrentElements = ElementGenerator.generate(MAX_SIZE, ElementGenerator.REVERSED);
                break;
            case R.id.action_few_unique:
                mCurrentElements = ElementGenerator.generate(MAX_SIZE, ElementGenerator.FEW_UNIQUE);
                break;
            case R.id.action_sorted:
                mCurrentElements = ElementGenerator.generate(MAX_SIZE, ElementGenerator.SORTED);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        mPresenter.setElements(mCurrentElements);
        return true;
    }

    @OnClick(R.id.btn_pause_resume)
    public void togglePauseResume() {
        if (mPresenter.isPaused()) {
            mPresenter.start();
        } else {
            mPresenter.pause();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        Timber.d("surfaceCreated");
        mPresenter.attachViews(this, (BaseSortPresenter.BaseView) surface);
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
        holder.getSurface().release();
        mPresenter.detachViews();
        mPresenter.pauseHome();
        Timber.d("surfaceDestroyed");
    }


    @Override
    public void togglePauseFab(final boolean active) {
        if (active) {
            pauseResumeFab.setImageResource(R.drawable.ic_play_arrow_white_24dp);
        } else {
            pauseResumeFab.setImageResource(R.drawable.ic_pause_white_24dp);
        }
    }

    @Override
    public void toggleFinished() {
        pauseResumeFab.setImageResource(R.drawable.ic_replay_black_24dp);
    }

    @Override
    public void showSetElementsError() {
        final Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinatorLayoutRoot)
                , "Alogirthm is running", Snackbar.LENGTH_LONG)
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        snackbar.show();
    }
}
