package sound.myapp.com.comfortsound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.lang.reflect.Field;

public class TestSongService extends Service implements MediaPlayer.OnCompletionListener {

    public MediaPlayer mp;
    Field fields[] = R.raw.class.getDeclaredFields();
    Boolean paused = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        int resourceId = 0;
        startId = intent.getExtras().getInt("position");
        if (!paused) {
            mp.reset();
            paused = true;
        }

        try {
            resourceId = fields[startId].getInt(fields[startId]);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        mp = MediaPlayer.create(this, resourceId);

        mp.start();
        mp.setLooping(false);

        mp.setOnCompletionListener(this);

        paused = false;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mp.isPlaying()) {
            mp.stop();
        }
        mp.release();

        Toast.makeText(TestSongService.this, "stop it", Toast.LENGTH_SHORT).show();
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        stopSelf();
    }
}