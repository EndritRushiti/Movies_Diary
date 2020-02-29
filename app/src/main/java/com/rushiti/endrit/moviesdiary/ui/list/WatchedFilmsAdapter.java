package com.rushiti.endrit.moviesdiary.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rushiti.endrit.moviesdiary.R;
import com.rushiti.endrit.moviesdiary.db.Filmat;
import com.rushiti.endrit.moviesdiary.db.FilmateShikuara;

import java.util.ArrayList;
import java.util.List;

public class WatchedFilmsAdapter extends RecyclerView.Adapter<WatchedFilmsAdapter.MyViewHolder> implements Filterable {

    private List<FilmateShikuara> mData = new ArrayList<>();
    private List<FilmateShikuara> mDataFull;

    public void setList(List<FilmateShikuara> filmsList) {
        this.mData.clear();
        this.mData.addAll(filmsList);
        mDataFull = new ArrayList<>(filmsList);
    }

    @Override
    public Filter getFilter() {
        return mDataFilter;
    }

    private Filter mDataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FilmateShikuara> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mDataFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (FilmateShikuara item : mData) {
                    if (item.getEmri().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mData.clear();
            mData.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView movieName;
        TextView movieRating;
        TextView movieDescription;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            movieName = itemView.findViewById(R.id.MovieName);
            movieRating = itemView.findViewById(R.id.Movie_Rating);
            movieDescription = itemView.findViewById(R.id.Movie_Description);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_watched_film, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.movieName.setText(mData.get(i).getEmri());
        myViewHolder.movieRating.setText(mData.get(i).getRating().toString());
        myViewHolder.movieDescription.setText(mData.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
