package ru.ifmo.md.exam2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

public class AddDialogFragment extends DialogFragment {

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(String title, String author, String year);
    }

    NoticeDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.add_dialog, null))
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText titleEdit = (EditText) AddDialogFragment.this.getDialog().findViewById(R.id.title_edit);
                        EditText authorEdit = (EditText) AddDialogFragment.this.getDialog().findViewById(R.id.author_edit);
                        EditText yearEdit = (EditText) AddDialogFragment.this.getDialog().findViewById(R.id.year_edit);
                        String title = titleEdit.getText().toString();
                        String author = authorEdit.getText().toString();
                        String year = yearEdit.getText().toString();
                        mListener.onDialogPositiveClick(title, author, year);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
