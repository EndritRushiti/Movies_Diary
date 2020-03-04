package com.rushiti.endrit.moviesdiary.ui.list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rushiti.endrit.moviesdiary.R;
import com.rushiti.endrit.moviesdiary.db.Filmat;
import com.rushiti.endrit.moviesdiary.ui.rating.RatingMovieActivity;

import java.util.ArrayList;
import java.util.List;

public class ToWatchFilmsAdapter extends RecyclerView.Adapter<ToWatchFilmsAdapter.ProductViewHolder> implements Filterable {

    private List<Filmat> filmsList = new ArrayList<>();
    private List<Filmat> filmsFull;

    public void setList(List<Filmat> filmsList) {
        this.filmsList.clear();
        this.filmsList.addAll(filmsList);
        filmsFull = new ArrayList<>(filmsList);
    }

    @Override
    public Filter getFilter() {
        return filmsFilter;
    }

    private Filter filmsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Filmat> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(filmsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Filmat item : filmsList) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
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
            filmsList.clear();
            filmsList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int index) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_film_to_watch, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder productViewHolder, int index) {
        final Filmat filmat = filmsList.get(index);
        productViewHolder.title.setText(filmat.getTitle());
        productViewHolder.watchedChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (productViewHolder.watchedChecked.isChecked()) {
                    final Intent intent = new Intent(view.getContext(), RatingMovieActivity.class);
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                            .setTitle("Information")
                            .setMessage("Do you want to rate your film?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    intent.putExtra("EmriFilmit", productViewHolder.title.getText().toString());
                                    view.getContext().startActivity(intent);
                                    productViewHolder.watchedChecked.setChecked(false);
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    productViewHolder.watchedChecked.setChecked(false);
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filmsList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        CheckBox watchedChecked;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            watchedChecked = itemView.findViewById(R.id.checkbox1);
        }
    }
}
