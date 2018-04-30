package com.example.cr7.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cr7.tesse.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by CR7 on 4/11/2018.
 */

public class EditInfo_Fragment extends Fragment {
    ImageView imgAva;
    Calendar cal;
    EditText txtFirstName,txtLastName,txtEmail,txtPass,txtBirthday;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_editprofile,container,false);
        addControls(view);
        //addEvents();
        return view;
    }

    private void addControls(View view) {
        //txtBirthday = view.findViewById(R.id.txtBirthday);
    }

    private void addEvents() {
        txtBirthday.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (txtBirthday.getRight() - txtBirthday.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        MDatePicker datePicker = new MDatePicker();
                        datePicker.show(getFragmentManager(), "Birthday");

                        return true;
                    }
                }
                return false;
            }
        });
    }
}
