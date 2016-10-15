package sound.myapp.com.comfortsound;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChronoExample extends Activity {
    Chronometer chrono;
    Button btnStart,btnReset;
    Button btnStop;
    TextView txt;

    long elapsedTime=0;
    String currentTime="";
    long startTime=SystemClock.elapsedRealtime();
    Boolean resume=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chrono_example);
        chrono=(Chronometer)findViewById(R.id.chrono);
        btnStart=(Button)findViewById(R.id.btnStart);
        btnStop=(Button)findViewById(R.id.btnStop);
        btnReset=(Button)findViewById(R.id.btnReset);
        txt=(TextView)findViewById(R.id.txt);

        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener()
        {

            public void onChronometerTick(Chronometer arg0) {
                // TODO Auto-generated method stub

                if(!resume)
                {

                    long minutes=((SystemClock.elapsedRealtime()-chrono.getBase())/1000)/60;
                    long seconds=((SystemClock.elapsedRealtime()-chrono.getBase())/1000)%60;
                    currentTime=minutes+":"+seconds;
                    arg0.setText(currentTime);
                    elapsedTime=SystemClock.elapsedRealtime();
                }
                else
                {

                    long minutes=((elapsedTime-chrono.getBase())/1000)/60;
                    long seconds=((elapsedTime-chrono.getBase())/1000)%60;
                    currentTime=minutes+":"+seconds;
                    arg0.setText(currentTime);
                    elapsedTime=elapsedTime+1000;
                }


            }
        });

    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId())
        {
            case R.id.btnStart:
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
                if(!resume)
                {
                    chrono.setBase(SystemClock.elapsedRealtime());
                    chrono.start();
                }
                else
                {

                    chrono.start();
                }

                break;
            case R.id.btnStop:
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                chrono.stop();
                chrono.setText(currentTime);
                resume=true;
                btnStart.setText("Resume");
                break;
            case R.id.btnReset:

                chrono.stop();
                chrono.setText("00:00");
                resume=false;
                btnStop.setEnabled(false);
                break;
        }
    }
}