package com.pddstudio.tinyifttt.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * This Class was created by Patrick J
 * on 30.03.16. For more Details and Licensing
 * have a look at the README.md
 */
public class Dialog extends DialogFragment {

    private final String LOG_TAG = getClass().getSimpleName();

    private Context mContext;
    @StringRes private int mDialogTitle;
    @StringRes private int mDialogContent;


    public Dialog setContext(Context context) {
        this.mContext = context;
        return this;
    }

    public void show(FragmentManager fragmentManager, @StringRes int dialogTitle, @StringRes int dialogContent) {
        this.mDialogContent = dialogContent;
        this.mDialogTitle = dialogTitle;
        show(fragmentManager, "DIALOG");
    }

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstance) {
        return new AlertDialog.Builder(mContext)
                .setTitle(mDialogTitle)
                .setMessage(mDialogContent)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(LOG_TAG, "onClick() for positive dialog action called");
                    }
                })
                .create();
    }

}