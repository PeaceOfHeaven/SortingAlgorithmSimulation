package universe.sortalgorithmssimulation;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicPlayerService extends Service {
    private static final String TAG = null;
    static MediaPlayer player;
    
    public IBinder onBind(Intent arg0) {

        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.pikachu1);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);

    }
    public int onStartCommand(Intent intent, int flags, int startId) {


        player.start();

        return 1;
    }

    public void onStart(Intent intent, int startId) {
        // TODO

    }
    public IBinder onUnBind(Intent arg0) {
        // TODO Auto-generated method stub

        return null;
    }

    public void onStop() {

    }
    public void onPause() {
    	player.pause();
    	player.start();
    }
    
    @Override
    public void onDestroy() {

        player.stop();
        player.release();
    }
    @Override
    public boolean stopService(Intent name) {
    	player.stop();
        player.release();
        return super.stopService(name);
    };
    @Override
    public void onLowMemory() {

    } 
}
