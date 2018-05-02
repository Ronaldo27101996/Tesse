package com.example.cr7.Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cr7.Adapter.AdapterAppointmentBooking;
import com.example.cr7.Adapter.AdapterAppointmentExpert;
import com.example.cr7.Model.Appointment;
import com.example.cr7.Rest.APIService;
import com.example.cr7.Rest.RetrofitClient;
import com.example.cr7.tesse.LoginActivity;
import com.example.cr7.tesse.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CR7 on 4/2/2018.
 */

public class Manager_Appointment_Expert_Fragment extends Fragment {
    private List<Appointment> listAppointment;
    private RecyclerView rvAppointment;
    private AdapterAppointmentExpert adapterAppointment;
    ProgressDialog mProgressDialog;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_appointment_booking, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {


    }

    @Override
    public void onResume() {
        loadData();

        super.onResume();

    }

    private void addControls(View view) {
        rvAppointment = view.findViewById(R.id.rvAppointment);
        listAppointment = new ArrayList<>();



    }
public void loadData(){
    final APIService apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL).create(APIService.class);
    Call<List<Appointment>> callMail = apiService.getAppointmentOfExpert(LoginActivity.USER_ID);
    showDialog();
    Log.e("URL", callMail.request().url() + " ");
    callMail.enqueue(new Callback<List<Appointment>>() {
        @Override
        public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
            hideDialog();
            List<Appointment> list_Appointment = response.body();
            listAppointment.clear();
            listAppointment.addAll(list_Appointment);
            adapterAppointment = new AdapterAppointmentExpert(listAppointment, getActivity());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rvAppointment.setLayoutManager(mLayoutManager);
            rvAppointment.setAdapter(adapterAppointment);
            adapterAppointment.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<List<Appointment>> call, Throwable t) {
            hideDialog();
            Log.e("onFailure: ", "something fail ");
            Toast.makeText(getActivity(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
        }
    });
}
    public void showDialog() {
        if (mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
