package com.example.cr7.Fragment;

/**
 * Created by CR7 on 5/2/2018.
 */

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.cr7.tesse.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MTimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


    public MTimePicker() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker

        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Toast.makeText(getActivity(), hourOfDay + ":" + minute , Toast.LENGTH_SHORT).show();
        TextView txtMain = getFragmentManager().findFragmentByTag("dialogAppointment").getView().findViewById(R.id.txtTime);
        txtMain.setText( hourOfDay + ":" + minute);
    }
}