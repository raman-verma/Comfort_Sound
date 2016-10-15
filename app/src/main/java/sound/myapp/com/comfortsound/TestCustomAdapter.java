package sound.myapp.com.comfortsound;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.reflect.Field;

public class TestCustomAdapter extends BaseAdapter {

    private static final int ONGOING_NOTIFICATION_ID = 0;
    private static final String APP_SHAREDPREFERENCE_NAME = "AppSharedPref";
    private static final String TOGGLE_STATE_KEY1 = "TB_KEY1";
    Context context;
    LayoutInflater mInflater;
    Field fields[] = R.raw.class.getDeclaredFields();
    NotificationManager nm;
    private boolean[] img = new boolean[fields.length];
    SharedPreferences sharedPreferences;
    ListView listView;
    int pos;

    public TestCustomAdapter(Context c, ListView list) {
        context = c;
        this.listView = list;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return fields.length;
    }

    @Override
    public Object getItem(int position) {
        return getAllRawResources()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder viewHolder = new ViewHolder();

        if (row == null || row.getTag() == null) {
            row = mInflater.inflate(R.layout.songs_list_layout, null);
            //ViewHolder viewHolder = new ViewHolder();

            //viewHolder.togglePlayButton = (ToggleButton) row.findViewById(R.id.togglePlayButton);
            //viewHolder.imageButtonPlay = (ImageButton) row.findViewById(R.id.imageButtonPlay);
            viewHolder.imageButtonPlay = (ThreeButtonState) row.findViewById(R.id.imageButtonPlay);
            viewHolder.textSngName = (TextView) row.findViewById(R.id.textSngName);
            viewHolder.toggleButtonFav = (ToggleButton) row.findViewById(R.id.toggleButtonFav);

            viewHolder.toggleButtonFav.setTag(position);

            row.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) row.getTag();
        }

        viewHolder.textSngName.setText(getAllRawResources()[position]);
        //viewHolder.toggleButtonFav.setOnCheckedChangeListener(myCheckChangeList);

        //viewHolder.toggleButtonFav.setChecked(getState()[position]);

        sharedPreferences= context.getSharedPreferences(APP_SHAREDPREFERENCE_NAME,0);
        viewHolder.toggleButtonFav.setChecked(sharedPreferences.getBoolean(TOGGLE_STATE_KEY1+position,false));

        row.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, TestSongService.class);
                intent.putExtra("position", position);
                context.startService(intent);

                //Notification code is created.
//                nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                Notification notification = new Notification(R.drawable.appicon512_1, "Comfort Sound",
//                        System.currentTimeMillis());
//                Intent notificationIntent = new Intent(context, TestOneFragment.class);
//                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
//                notification.setLatestEventInfo(context, getItem(position).toString(), "click to close", pendingIntent);
//                nm.notify(ONGOING_NOTIFICATION_ID, notification);
            }
        });

        //sharedPreferences = context.getSharedPreferences(APP_SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        //viewHolder.toggleButtonFav.setChecked(getState());

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.toggleButtonFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(context, "value- " + getItemId(position + 1) + "- " + getItem(position), Toast.LENGTH_SHORT).show();
                pos=(Integer)buttonView.getTag();

                if(finalViewHolder.toggleButtonFav.isChecked()){
                    finalViewHolder.toggleButtonFav.setChecked(true);
                    SharedPreferences.Editor editor= context.getSharedPreferences(APP_SHAREDPREFERENCE_NAME,0).edit();
                    editor.putBoolean(TOGGLE_STATE_KEY1+pos,true);
                    editor.commit();

                }else {
                    finalViewHolder.toggleButtonFav.setChecked(false);
                    SharedPreferences.Editor editor= context.getSharedPreferences(APP_SHAREDPREFERENCE_NAME,0).edit();
                    editor.putBoolean(TOGGLE_STATE_KEY1+pos,false);
                    editor.commit();
                }
//                img[position] = isChecked;
//                savedChecked(img[position]);
            }
        });


        return row;
    }

//    CompoundButton.OnCheckedChangeListener myCheckChangeList = new CompoundButton.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//            ((ToggleButton) buttonView).toggle();
//
//            int position = (int) buttonView.getTag();
//            Toast.makeText(context, "value- " + getItemId(position + 1) + "- " + getItem(position), Toast.LENGTH_SHORT).show();
//
//
//            sharedPreferences = context.getSharedPreferences(APP_SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
//
//            img[position] = isChecked;
//            savedChecked(img[position],position);
//        }
//    };

    public String[] getAllRawResources() {
        Field fields[] = R.raw.class.getDeclaredFields();
        String[] names = new String[fields.length];

        try {
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                names[i] = f.getName();
//                Log.v("app", names[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }

    private static class ViewHolder {
        public ToggleButton togglePlayButton, toggleButtonFav;
        //public ImageButton imageButtonPlay;
        public ThreeButtonState imageButtonPlay;
        public TextView textSngName;

        public ViewHolder(){
            this.imageButtonPlay=null;
            this.textSngName=null;
            this.toggleButtonFav=null;
        }
    }

    private void savedChecked(boolean isChecked,int pos) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(TOGGLE_STATE_KEY1, isChecked);
        editor.putInt(TOGGLE_STATE_KEY1,pos);
        editor.commit();
    }

    public boolean getState() {
            return sharedPreferences.getBoolean(TOGGLE_STATE_KEY1, false);
    }
}