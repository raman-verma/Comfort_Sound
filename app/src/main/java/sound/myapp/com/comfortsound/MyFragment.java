package sound.myapp.com.comfortsound;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyFragment extends android.support.v4.app.Fragment {

    private ListView listView;

    public static MyFragment getInstance(int position) {
        MyFragment myFragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.songs_list, container, false);

        listView = (ListView) layout.findViewById(R.id.songList);

//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            textView.setText("The Page Selected is " + bundle.getInt("position" ));
//        }

//        listView.setAdapter(mAdapter);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,new String[]{"A1","B1","C1","D1"}));

        return layout;
    }
}
