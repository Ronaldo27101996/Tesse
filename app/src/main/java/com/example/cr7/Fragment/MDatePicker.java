package com.example.cr7.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;


import com.example.cr7.tesse.R;

import java.util.Calendar;

/**
 * Created by huong.tx on 1/25/2018.
 */

public class MDatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        return dialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Toast.makeText(getActivity(), day + "/" + month + 1 + "/" + year, Toast.LENGTH_SHORT).show();
        //TextView txtMain = getActivity().findViewById(R.id.txtBirthday);
        //txtMain.setText( day + "/" + month + 1 + "/" + year);
    }
}
