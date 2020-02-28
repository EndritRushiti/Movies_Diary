package com.rushiti.endrit.moviesdiary.ui.dialog;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.rushiti.endrit.moviesdiary.ApplicationClass;
import com.rushiti.endrit.moviesdiary.R;

import javax.inject.Inject;

public class NewFilmDialog extends DialogFragment {
    @Inject
    SQLiteDatabase db;
    private EditText addFilmEdit;
    private NewFilmAddedCallback newFilmAddedCallback;

    public NewFilmDialog(NewFilmAddedCallback newFilmAddedCallback) {
        this.newFilmAddedCallback = newFilmAddedCallback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ApplicationClass) requireActivity().getApplication()).getComponent().inject(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.fragment_add_film_to_watch, null);
        addFilmEdit = view.findViewById(R.id.addfilmedittext);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(view)
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (addFilmEdit.getText().toString().length() > 0) {
                            insertFilmInDb();
                        } else {
                            Toast.makeText(requireContext(), "You have to add at least one sign", Toast.LENGTH_LONG).show();
                        }
                        newFilmAddedCallback.dataInserted();
                        dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        return builder.create();
    }

    private void insertFilmInDb() {
        ContentValues cv = new ContentValues();
        cv.put("Emri", addFilmEdit.getText().toString());
        db.insert("Filmi", null, cv);
    }
}
