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

    private List<Filmat> filmsList;
    private List<Filmat> mDataFull;

    public ToWatchFilmsAdapter(List<Filmat> filmsList) {
        this.filmsList = filmsList;
        mDataFull = new ArrayList<>(filmsList);
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
                filteredList.addAll(mDataFull);
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
        productViewHolder.textView.setText(filmat.getTitle());
        productViewHolder.watchedcheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (productViewHolder.watchedcheckbox.isChecked()) {
                    final Intent intent = new Intent(view.getContext(), RatingMovieActivity.class);
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                            .setTitle("Information")
                            .setMessage("Do you want to rate your film?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    intent.putExtra("EmriFilmit", productViewHolder.textView.getText().toString());
                                    view.getContext().startActivity(intent);
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    productViewHolder.watchedcheckbox.setChecked(false);
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
        TextView textView;
        CheckBox watchedcheckbox;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textViewTitle);
            watchedcheckbox = itemView.findViewById(R.id.checkbox1);
        }
    }
}