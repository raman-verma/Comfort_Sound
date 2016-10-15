package sound.myapp.com.comfortsound;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TestOneFragment extends Fragment {

    ListView myListView;
    protected TestCustomAdapter customAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.songs_list, container, false);
        myListView = (ListView) layout.findViewById(R.id.songList);

        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        customAdapter = new TestCustomAdapter(getActivity(),myListView);
        myListView.setAdapter(customAdapter);
    }
}
