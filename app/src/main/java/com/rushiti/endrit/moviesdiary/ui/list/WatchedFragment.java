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
import com.rushiti.endrit.moviesdiary.R;
import com.rushiti.endrit.moviesdiary.db.FilmateShikuara;

import java.util.ArrayList;

import javax.inject.Inject;

public class WatchedFragment extends Fragment {
    @Inject
    SQLiteDatabase db;
    private static final String TAG = "WatchedFragment";
    private ArrayList<FilmateShikuara> productList;
    private WatchedFilmsAdapter adapterWatchedFilms;

    public WatchedFragment() {
        // Required empty public constructor
    }

    public static WatchedFragment newInstance() {
        return new WatchedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ApplicationClass) requireActivity().getApplication()).getComponent().inject(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_watched_films_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        productList.clear();
        readDatabase();
        adapterWatchedFilms.notifyDataSetChanged();
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
                adapterWatchedFilms.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productList = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.films_watched_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        readDatabase();
        adapterWatchedFilms = new WatchedFilmsAdapter(productList);
        recyclerView.setAdapter(adapterWatchedFilms);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.recycler_divider));
        recyclerView.addItemDecoration(itemDecorator);
    }

    public void readDatabase() {
        Cursor c = db.rawQuery("SELECT * FROM WatchedMovie", null);
        while (c.moveToNext()) {
            String movieName = c.getString(c.getColumnIndex("Emri"));
            Double rate = c.getDouble(c.getColumnIndex("Rate"));
            String description = c.getString(c.getColumnIndex("Description"));
            productList.add(new FilmateShikuara(movieName, rate, description));
        }

    }
}

