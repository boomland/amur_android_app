package dating_ml.ru.amur.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dating_ml.ru.amur.R;
import dating_ml.ru.amur.adapter.RemindListAdapter;
import dating_ml.ru.amur.dto.RemindDTO;


public class MatchesFragment extends AbstractTabFragment {
    private static final int LAYOUT = R.layout.fragment_matches;

    public static MatchesFragment getInstance(Context context) {
        Bundle args = new Bundle();
        MatchesFragment fragment = new MatchesFragment();
        fragment.setArguments(args);

        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_matches));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        RecyclerView rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(new RemindListAdapter(createMockData()));

        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private List<RemindDTO> createMockData() {
        List<RemindDTO> data = new ArrayList<>();
        data.add(new RemindDTO("Item 1"));
        data.add(new RemindDTO("Item 2"));
        data.add(new RemindDTO("Item 3"));
        data.add(new RemindDTO("Item 4"));
        data.add(new RemindDTO("Item 5"));
        data.add(new RemindDTO("Item 6"));
        data.add(new RemindDTO("Item 7"));
        data.add(new RemindDTO("Item 8"));
        data.add(new RemindDTO("Item 9"));
        data.add(new RemindDTO("Item 10"));

        return data;
    }
}
