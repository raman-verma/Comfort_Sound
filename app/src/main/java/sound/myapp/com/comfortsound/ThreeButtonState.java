package sound.myapp.com.comfortsound;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

public class ThreeButtonState extends ImageButton {

    public enum FlashEnum{
        AUTOMATIC,ON,OFF;
    }

    public interface FlashListener{
        void onAutomatic();
        void onOn();
        void onOff();
    }

    private FlashEnum mState;
    private FlashListener mFlashListener;

    public ThreeButtonState(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int next=((mState.ordinal() + 1) % FlashEnum.values().length);
                setState(FlashEnum.values()[next]);
                performFlashClick();
            }
        });
        //set initial state
        setState(FlashEnum.AUTOMATIC);
    }

    private void performFlashClick(){
        if (mFlashListener==null)
            return;
        switch(mState){
            case AUTOMATIC:
                mFlashListener.onAutomatic();
                break;
            case ON:
                mFlashListener.onOn();
                break;
            case OFF:
                mFlashListener.onOff();
                break;
        }
    }

    private void createDrawableState(){
        switch (mState){
            case AUTOMATIC:
                setImageResource(R.drawable.soundmemory);
                break;
            case ON:
                setImageResource(R.drawable.btn_star_on_normal);
                break;
            case OFF:
                setImageResource(R.drawable.btn_star_off_normal);
                break;
        }
    }

    public FlashEnum getState(){
        return mState;
    }

    public void setState(FlashEnum state){
        if(state==null)return;
        this.mState=state;
        createDrawableState();
    }

    public FlashListener getFlashListener(){
        return mFlashListener;
    }

    public void setFlashListener(FlashListener flashListener){
        this.mFlashListener=flashListener;
    }
}
