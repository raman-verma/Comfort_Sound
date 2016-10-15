package sound.myapp.com.comfortsound;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.reflect.Field;

public class Song_List_Activity extends Activity {

    private MediaPlayer mp;
    //ListView songListView;
    RecyclerView songRecyclerView;
    private RecyclerView.LayoutManager songLayoutManager;
    String[] songs;
    Field fields[] = R.raw.class.getDeclaredFields();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_songs_list);

        //songListView = (ListView) findViewById(R.id.songList);
        songRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        songRecyclerView.setHasFixedSize(true);

        songs = getAllRawResources();

        songLayoutManager = new LinearLayoutManager(this);
        songRecyclerView.setLayoutManager(songLayoutManager);

        SongAdapter adapter = new SongAdapter(songs);
        //songListView.setAdapter(adapter);
        songRecyclerView.setAdapter(adapter);

        mp = new MediaPlayer();

        /*songListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Song_List_Activity.this, getAllRawResources()[position].toString(), Toast.LENGTH_SHORT).show();
                playSong(position);
            }
        });*/
    }

    public class SongAdapter extends RecyclerView.Adapter<ViewHolder> {

        Context mContext;
        String[] mSongs;
        LayoutInflater mInflater;

        public SongAdapter(String[] songs) {
            //mContext = c;
            mSongs = songs;
            //mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /*@Override
        public int getCount() {
            //Field fields[] = R.raw.class.getDeclaredFields();

//            for (int count=0;count<fields.length;count++) {
//            int resourdeId=fields[count].getInt(fields[count]);   //for resource id of the raw files
//                String name=fields[count].getName();    //for name of the raw files
//            }

            return fields.length;
        }

        @Override
        public Object getItem(int position) {
            return getAllRawResources()[position];
        }*/

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_song_list_layout, parent, false);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = songRecyclerView.getChildAdapterPosition(v);
                    if (pos >= 0 && pos < getItemCount()) {
                        Toast.makeText(Song_List_Activity.this, getAllRawResources()[pos].toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });



            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textSngName.setText(getAllRawResources()[position]);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return fields.length;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        final public ToggleButton togglePlayButton, toggleFavButton;
        public TextView textSngName;

        public ViewHolder(View v) {
            super(v);

            togglePlayButton = (ToggleButton) v.findViewById(R.id.togglePlayButton);
            textSngName = (TextView) v.findViewById(R.id.textSngName);
            toggleFavButton = (ToggleButton) v.findViewById(R.id.toggleFavButton);

        }
    }

/*        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.songs_list_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.togglePlayButton = (ToggleButton) convertView.findViewById(R.id.togglePlayButton);
                viewHolder.textSngName = (TextView) convertView.findViewById(R.id.textSngName);
                viewHolder.toggleFavButton = (ToggleButton) convertView.findViewById(R.id.toggleFavButton);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textSngName.setText(getAllRawResources()[position]);

            return convertView;
        }*/
    //}

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

    /*private static class ViewHolder {
        public ToggleButton togglePlayButton, toggleFavButton;
        public TextView textSngName;
    }*/

    private void playSong(int position) {
        int resourceId = 0;
        mp.reset();
        try {
            resourceId = fields[position].getInt(fields[position]);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mp = MediaPlayer.create(getApplicationContext(), resourceId);

        mp.start();
        mp.setLooping(true);
        mp.setVolume(100, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
        mp.release();
    }
}