package com.example.cryptoappreal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.io.BufferedReader;

/**
 * Created by sarang on 15/02/2018.
 */

public class AboutDialogFragment extends DialogFragment {

    public Dialog onCreateDialog(Bundle onSaveInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.about_app_text)
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       //intentionally left blank
                    }
                });
            return builder.create();
    }










}
