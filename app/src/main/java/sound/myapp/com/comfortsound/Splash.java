package sound.myapp.com.comfortsound;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Splash extends Activity {

    MediaPlayer mysong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
//        mysong=MediaPlayer.create(Splash.this,R.raw.per);
//        mysong.start();
        Thread timer=new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    sleep(1000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }finally{
                    Intent startMainActivity=new Intent("sound.myapp.com.comfortsound.FirstPage");
                    startActivity(startMainActivity);
                }
            }
        };
        timer.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
//        mysong.release();
        finish();
    }
}
