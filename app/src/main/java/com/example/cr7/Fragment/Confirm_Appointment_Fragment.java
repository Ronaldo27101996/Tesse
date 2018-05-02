package com.example.cr7.Fragment;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cr7.Model.Appointment;
import com.example.cr7.Rest.APIService;
import com.example.cr7.Rest.RetrofitClient;
import com.example.cr7.tesse.MapDirectionActivity;
import com.example.cr7.tesse.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CR7 on 4/6/2018.
 */

public class Confirm_Appointment_Fragment extends DialogFragment {

    EditText txtMessage;
    TextView txtDate, txtTime;
    ImageView imgLocation;
    Button btnAccept, btnDeny;
    Appointment appointment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_accept_appointment, container, false);
        addControls(v);
        addEvents();

        return v;
    }

    private void addControls(View v) {
        txtDate = v.findViewById(R.id.txtDate);
        txtTime = v.findViewById(R.id.txtTime);
        txtMessage = v.findViewById(R.id.txtMessageAppointment);
        imgLocation = v.findViewById(R.id.imgLocation);
        btnAccept = v.findViewById(R.id.btnAccept);
        btnDeny = v.findViewById(R.id.btnDeny);
        Bundle bundle = getArguments();
        if (bundle != null) {
            appointment = (Appointment) bundle.getSerializable("appointment");
        }
        txtDate.setText(appointment.getDate());
        txtTime.setText(appointment.getTime());
        txtMessage.setText(appointment.getMessage());

    }

    private void addEvents() {
        imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDirect();
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAppointment(1);
            }
        });
        btnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAppointment(0);
            }
        });
    }

    private void confirmAppointment(int isAccept) {
        final APIService apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL).create(APIService.class);
        Call<Integer> call = apiService.updateCommitAppointment(
                appointment.getId(),
                isAccept
        );
        Log.e("URL", call.request().url() + " ");
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.body() != 0) {
                    Log.e(" Success", "" + response.body());
                    Toast.makeText(getActivity(), " Successful!!!", Toast.LENGTH_SHORT).show();
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


    private void showDirect() {
        Intent intent = new Intent(getActivity(), MapDirectionActivity.class);
        intent.putExtra("lat", appointment.getLat());
        intent.putExtra("lon", appointment.getLon());
        getActivity().startActivity(intent);
    }

}