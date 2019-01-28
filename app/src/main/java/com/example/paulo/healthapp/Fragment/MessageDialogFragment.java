package com.example.paulo.healthapp.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.paulo.healthapp.R;

import java.util.Random;


/**
 * Created by Paulo on 18/08/2016.
 */

// NÃO ESTA SENDO USADA
public class MessageDialogFragment extends DialogFragment{
    private AlertDialog.Builder messageDialogBuilder;
    private Button bt_confirmar;
    private TextView tv_message;
    private View dialog_view;

    public interface MessageDialogListener {

    }

    // Use this instance of the interface to deliver action events
    MessageDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (MessageDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dialog_view = getActivity().getLayoutInflater().inflate(R.layout.dialog_message, null);

        bt_confirmar = (Button) dialog_view.findViewById(R.id.bt_confirmar);

        tv_message = (TextView) dialog_view.findViewById(R.id.mensagem_tuberculose);

        Random rand = new Random();
        int n = rand.nextInt(19) + 1; // Gives n such that 1 <= n <= 19

        String message = "mensagem" + n;

        tv_message.setText(getString(getActivity().getResources().getIdentifier(message,"string",getActivity().getPackageName())));

        messageDialogBuilder = new AlertDialog.Builder(getActivity());
        messageDialogBuilder.setView(dialog_view);

        bt_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return messageDialogBuilder.create();
    }

}
