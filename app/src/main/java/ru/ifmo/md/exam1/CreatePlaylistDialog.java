package ru.ifmo.md.exam1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by dimatomp on 23.01.15.
 */
public class CreatePlaylistDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText editText = new EditText(getActivity());
        return builder.setTitle("New playlist").setView(editText)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().getContentResolver().insert(Uri.parse(ItemsListProvider.CREATE_PLAYLIST +
                                "?name=" + Uri.encode(editText.getText().toString())), null);
                        getActivity().getLoaderManager().restartLoader(0, null, (LoaderManager.LoaderCallbacks) getActivity());
                        dismiss();
                    }
                }).create();
    }
}
