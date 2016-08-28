package com.dcc.hackathon.locus;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.dcc.hackathon.locus.R;
// ...

public class CreateEventDialog extends DialogFragment implements TextView.OnEditorActionListener {

    private EditText mEditText;

    public interface EditNameDialogListener {
        void onFinishEditDialog(String title, String description);
    }

    public CreateEventDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_event, container);
        mEditText = (EditText) view.findViewById(R.id.event_title_input);
        getDialog().setTitle("Criar Evento");

        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mEditText.setOnEditorActionListener(this);

        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            EditNameDialogListener activity = (EditNameDialogListener) getActivity();
            activity.onFinishEditDialog(mEditText.getText().toString(), "placeholder");
            this.dismiss();
            return true;
        }
        return false;
    }
}