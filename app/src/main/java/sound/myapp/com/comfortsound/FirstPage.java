package sound.myapp.com.comfortsound;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.concurrent.TimeUnit;

import static android.os.SystemClock.*;

public class FirstPage extends Activity implements View.OnClickListener {

    Button inc_time, dec_time;
    ToggleButton startbutton;
    Chronometer chrono;
    TextView tv;
    Boolean resume = false;
    int stoppedMilliseconds;
    static CounterClass timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);

        tv = (TextView) findViewById(R.id.textview1);
        inc_time = (Button) findViewById(R.id.increase);
        dec_time = (Button) findViewById(R.id.decrease);
        startbutton = (ToggleButton) findViewById(R.id.start_timer);
        chrono = (Chronometer) findViewById(R.id.chronometer);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Android Insomnia Regular.ttf");    //for giving font the title of the app.
        tv.setTypeface(custom_font);

        inc_time.setOnClickListener(this);
        dec_time.setOnClickListener(this);

        chrono.setText("05:01");
        startbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (startbutton.isChecked()) {
                    startTimer();
                    inc_time.setEnabled(false);
                    dec_time.setEnabled(false);
                } else {
                    Toast.makeText(FirstPage.this, "off", Toast.LENGTH_SHORT).show();
                    timer.cancel();
                    timer.onFinish();
                    inc_time.setEnabled(true);
                    dec_time.setEnabled(true);
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.increase:
                increaseButton();
                break;
            case R.id.decrease:
                decreaseButton();
                break;
        }
    }

    private void startTimer() {

        if (!resume) {

            //int stoppedMilliseconds = 0;

            String chronoText = chrono.getText().toString();
            String array[] = chronoText.split(":");
            if (array.length == 2) {
                stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
                        + Integer.parseInt(array[1]) * 1000;
            } else if (array.length == 3) {
                stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                        + Integer.parseInt(array[1]) * 60 * 1000
                        + Integer.parseInt(array[2]) * 1000;
            }

            timer = new CounterClass(stoppedMilliseconds, 1000);
            timer.start();

            resume = true;
        }
    }

    private void increaseButton() {

        int stoppedMilliseconds = 0;

        String chronoText = chrono.getText().toString();
        String array[] = chronoText.split(":");
        if (array.length == 2) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
                    + Integer.parseInt(array[1]) * 1000;
        } else if (array.length == 3) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                    + Integer.parseInt(array[1]) * 60 * 1000
                    + Integer.parseInt(array[2]) * 1000;
        }

        int addedtime =stoppedMilliseconds + 30000;

//        int minutes = (int) (newtimer - (newtimer / 3600000) * 3600000) / 60000;
//        int seconds = (int) (newtimer  - (newtimer / 3600000) * 3600000 - minutes * 60000) / 1000;

        chrono.setBase(elapsedRealtime() - addedtime);
    }

    private void decreaseButton() {

        if((chrono.getText().toString()).equals("00:01")){
            return;
        }else {
            int stoppedMilliseconds = 0;

            String chronoText = chrono.getText().toString();
            String array[] = chronoText.split(":");
            if (array.length == 2) {
                stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
                        + Integer.parseInt(array[1]) * 1000;
            } else if (array.length == 3) {
                stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                        + Integer.parseInt(array[1]) * 60 * 1000
                        + Integer.parseInt(array[2]) * 1000;
            }

            int subtime = stoppedMilliseconds - 30000;

            chrono.setBase(elapsedRealtime() - subtime);
        }
    }

    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

            String hms=null;
            long millis = millisUntilFinished;

            String chronoText = chrono.getText().toString();
            String array[] = chronoText.split(":");
            if (array.length == 2) {
                hms = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            } else if (array.length == 3) {
                hms = String.format("%02d:%02d:%02d",TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            }
            chrono.setText(hms);
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            resume = false;
        }
    }
}