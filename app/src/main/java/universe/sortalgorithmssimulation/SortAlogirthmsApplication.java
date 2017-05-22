package universe.sortalgorithmssimulation;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Nhat on 5/7/2017.
 */

public class SortAlogirthmsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree() {

             @Override
             protected String createStackElementTag(StackTraceElement element) {
                 return super.createStackElementTag(element) + "Nhat";
             }
        });
    }
}
