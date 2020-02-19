package com.rushiti.endrit.moviesdiary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Filmat> productList;
    Dialog dialog;



    public ProductAdapter(Context mCtx, List<Filmat> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.layout_products,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder productViewHolder, int i) {

        final Filmat filmat=productList.get(i);
        productViewHolder.textView.setText(filmat.getTitle());
        productViewHolder.watchedcheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(productViewHolder.watchedcheckbox.isChecked())
                {
                    final Intent i=new Intent(v.getContext(),WatchedMovie.class);
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog

                            i.putExtra("EmriFilmit",productViewHolder.textView.getText().toString());
                            v.getContext().startActivity(i);
                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                    //Intent i = new Intent(v.getContext(),WatchedMovie.class);
                    //i.putExtra("EmriFilmit",productViewHolder.textView.getText().toString());
                    //v.getContext().startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        TextView textView;
        CheckBox watchedcheckbox;
        SQLiteDatabase db;
        FilmaDb dbHelper;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);

            textView=(TextView)itemView.findViewById(R.id.textViewTitle);
            watchedcheckbox=(CheckBox)itemView.findViewById(R.id.checkbox1);

        }

        @Override
        public boolean onLongClick(final View v) {
            dbHelper=new FilmaDb(v.getContext(),"Filma_db",null,2);
            db=dbHelper.getWritableDatabase();
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

            builder.setTitle("Delete");
            builder.setMessage("Are you sure you want to delete the movie ?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                   //Do something
                   db.delete("Filmi","Emri=?",new String[]{textView.getText().toString()});
                    MainActivity.productList.remove(getAdapterPosition());
                    MainActivity.adapter.notifyDataSetChanged();
                    if(MainActivity.adapter.getItemCount()==0)
                    {
                        MainActivity.recycleviewlayout.setVisibility(View.INVISIBLE);
                        MainActivity.clicktoaddmovies.setVisibility(View.VISIBLE);

                    }
                    else
                    {
                        MainActivity.recycleviewlayout.setVisibility(View.VISIBLE);
                        MainActivity.clicktoaddmovies.setVisibility(View.INVISIBLE);

                    }
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
            return false;
        }
    }
}
