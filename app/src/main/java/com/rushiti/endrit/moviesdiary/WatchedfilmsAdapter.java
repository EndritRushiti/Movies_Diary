package com.rushiti.endrit.moviesdiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WatchedfilmsAdapter extends RecyclerView.Adapter<WatchedfilmsAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private List<FilmateShikuara> mData;
    private List<FilmateShikuara> mDataFull;

    public WatchedfilmsAdapter(Context mContext, List<FilmateShikuara> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mDataFull=new ArrayList<>(mData);
    }

    @Override
    public Filter getFilter() {
        return mDataFilter;
    }

    private Filter mDataFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FilmateShikuara> filteredList=new ArrayList<>();
            if(constraint==null || constraint.length()==0)
            {
                filteredList.addAll(mDataFull);
            }
            else
            {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (FilmateShikuara item:mDataFull)
                {
                    if(item.getEmri().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mData.clear();
            mData.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView movieName;
        TextView movieRating;
        TextView movieDescription;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            movieName=(TextView)itemView.findViewById(R.id.MovieName);
            movieRating=(TextView)itemView.findViewById(R.id.Movie_Rating);
            movieDescription=(TextView) itemView.findViewById(R.id.Movie_Description);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.layout_product,null);
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
