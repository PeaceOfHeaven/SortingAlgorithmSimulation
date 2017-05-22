package universe.sortalgorithmssimulation.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;

import com.commit451.elasticdragdismisslayout.ElasticDragDismissListener;
import com.commit451.viewtiful.Viewtiful;

import universe.sortalgorithmssimulation.utils.ColorUtils;

@TargetApi(21)
public class SystemChromeFader implements ElasticDragDismissListener {

    private final Activity activity;
    private final int statusBarAlpha;
    private final int navBarAlpha;
    private final boolean fadeNavBar;

    public SystemChromeFader(Activity activity) {
        this.activity = activity;
        statusBarAlpha = Color.alpha(activity.getWindow().getStatusBarColor());
        navBarAlpha = Color.alpha(activity.getWindow().getNavigationBarColor());
        fadeNavBar = Viewtiful.isNavigationBarOnBottom(activity);
    }

    @Override
    public void onDrag(float elasticOffset, float elasticOffsetPixels,
                       float rawOffset, float rawOffsetPixels) {
        if (elasticOffsetPixels > 0) {
            // dragging downward, fade the status bar in proportion
            activity.getWindow().setStatusBarColor(ColorUtils.modifyAlpha(activity.getWindow()
                    .getStatusBarColor(), (int) ((1f - rawOffset) * statusBarAlpha)));
        } else if (elasticOffsetPixels == 0) {
            // reset
            activity.getWindow().setStatusBarColor(ColorUtils.modifyAlpha(
                    activity.getWindow().getStatusBarColor(), statusBarAlpha));
            activity.getWindow().setNavigationBarColor(ColorUtils.modifyAlpha(
                    activity.getWindow().getNavigationBarColor(), navBarAlpha));
        } else if (fadeNavBar) {
            // dragging upward, fade the navigation bar in proportion
            activity.getWindow().setNavigationBarColor(
                    ColorUtils.modifyAlpha(activity.getWindow().getNavigationBarColor(),
                            (int) ((1f - rawOffset) * navBarAlpha)));
        }
    }

    public void onDragDismissed() {
        activity.finishAfterTransition();
    }
}
