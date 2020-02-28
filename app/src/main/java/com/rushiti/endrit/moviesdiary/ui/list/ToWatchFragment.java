package com.rushiti.endrit.moviesdiary.ui.list;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rushiti.endrit.moviesdiary.ApplicationClass;
import com.rushiti.endrit.moviesdiary.ui.dialog.NewFilmAddedCallback;
import com.rushiti.endrit.moviesdiary.R;
import com.rushiti.endrit.moviesdiary.db.Filmat;
import com.rushiti.endrit.moviesdiary.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ToWatchFragment extends Fragment implements NewFilmAddedCallback {
    @Inject
    SQLiteDatabase db;
    private static final String TAG = "ToWatchFragment";
    private List<Filmat> productList;
    private ToWatchFilmsAdapter filmsToWatchFilmsAdapter;
    private RecyclerView recyclerView;

    public ToWatchFragment() {
        // Required empty public constructor
    }

    public static ToWatchFragment newInstance() {
        return new ToWatchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ApplicationClass) requireActivity().getApplication()).getComponent().inject(this);
        ((MainActivity) getActivity()).setCallback(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_to_watch_films_list, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        SearchManager searchManager = (SearchManager) requireContext().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filmsToWatchFilmsAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        productList.clear();
        readDataBase();
        filmsToWatchFilmsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.films_to_watch_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        readDataBase();
        filmsToWatchFilmsAdapter = new ToWatchFilmsAdapter(productList);
        recyclerView.setAdapter(filmsToWatchFilmsAdapter);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.recycler_divider));
        recyclerView.addItemDecoration(itemDecorator);
    }

    private void readDataBase() {
        productList.clear();
        Cursor c = db.rawQuery("SELECT * FROM Filmi", null);
        while (c.moveToNext()) {
            productList.add(new Filmat(c.getString(c.getColumnIndex("Emri"))));
        }
        c.close();
    }

    @Override
    public void dataInserted() {
        readDataBase();
        filmsToWatchFilmsAdapter.notifyDataSetChanged();
    }
}
