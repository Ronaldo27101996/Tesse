package com.example.cr7.Fragment;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cr7.Model.Appointment;
import com.example.cr7.Model.User;
import com.example.cr7.Rest.APIService;
import com.example.cr7.Rest.RetrofitClient;
import com.example.cr7.tesse.ContainerActivity;
import com.example.cr7.tesse.LoginActivity;
import com.example.cr7.tesse.MapPickerActivity;
import com.example.cr7.tesse.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CR7 on 4/6/2018.
 */

public class Appointment_Fragment  extends DialogFragment {
    public static String DATE = "1/1/2000" , TIME="00:00";
    EditText txtMessage;
    TextView txtDate,txtTime;
    ImageView imgLocation;
    Button btnSend;
    String id_expert;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_appointment, container, false);
        addControls(v);
        addEvents();

        return v;
    }

    private void addControls(View v) {
        txtDate = v.findViewById(R.id.txtDate);
        txtTime = v.findViewById(R.id.txtTime);
        txtMessage = v.findViewById(R.id.txtMessageAppointment);
        imgLocation =v.findViewById(R.id.imgLocation);
        btnSend = v.findViewById(R.id.btnSend);
        txtDate.setText(DATE);
        txtTime.setText(TIME);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id_expert = bundle.getString("expert_id");
        }
    }

    private void addEvents() {
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MTimePicker timePicker = new MTimePicker();
                timePicker.show(getFragmentManager(), "Time");
            }
        });
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MDatePicker datePicker = new MDatePicker();
                datePicker.show(getFragmentManager(), "Date");
            }
        });
        imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMap();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAppointment();
            }
        });
    }

    private void addAppointment() {
        final APIService apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL).create(APIService.class);
        Call<Integer> call = apiService.addAppointment(
                id_expert,
                LoginActivity.USER_ID,
                txtDate.getText().toString().trim(),
                txtTime.getText().toString().trim(),
                ContainerActivity.LAT,
                ContainerActivity.LON,
                txtMessage.getText().toString().trim());
        Log.e("URL", call.request().url() + " ");
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body()!=0){
                    Log.e("Appointment Success",""+response.body() );
                    Toast.makeText(getActivity(), "Appointment Successful!!!", Toast.LENGTH_SHORT).show();
                    //getFragmentManager().popBackStackImmediate();
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("onFailure: ", "something fail: " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pickMap() {
        Intent intent = new Intent(getActivity(), MapPickerActivity.class);
        startActivity(intent);
    }

}