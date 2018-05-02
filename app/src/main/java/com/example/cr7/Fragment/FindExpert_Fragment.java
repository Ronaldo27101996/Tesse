package com.example.cr7.Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cr7.Adapter.AdapterExpert;
import com.example.cr7.Model.Expert;
import com.example.cr7.Model.User;
import com.example.cr7.Rest.APIService;
import com.example.cr7.Rest.RetrofitClient;
import com.example.cr7.tesse.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CR7 on 4/2/2018.
 */

public class FindExpert_Fragment extends Fragment {
    private List<Expert> listExpert;
    private RecyclerView rvExpert;
    private AdapterExpert adapterExpert;
    FloatingActionButton fab;
    ProgressDialog mProgressDialog;
    EditText txtSearch;
    ImageView imgSearch;
    String keyword;


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
        View view = inflater.inflate(R.layout.activity_find_expert, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchProcess();
            }
        });

    }

    private void addControls(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            keyword = bundle.getString("keyword");
        }
        txtSearch = view.findViewById(R.id.txtSearch);
        txtSearch.setText(keyword);
        imgSearch = view.findViewById(R.id.imgSearch);
        rvExpert = view.findViewById(R.id.rvExpert);
        //txtSearch.requestFocus();
        listExpert = new ArrayList<>();
        adapterExpert = new AdapterExpert(listExpert, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvExpert.setLayoutManager(mLayoutManager);
        rvExpert.setAdapter(adapterExpert);
//        listExpert.add(new Expert("Huy Duy", "Mobile Developer App Android", "Việt Nam", "http://cdni.condenast.co.uk/320x480/d_f/Emma-Watson-close-up-Vogue-28Aug15-Getty_b_320x480.jpg", true, 10.847588, 106.775818));
//        listExpert.add(new Expert("Huong To", "Mobile Developer ", "Việt Nam", "http://www.elle.vn/wp-content/uploads/2015/10/30/emma-stone.jpg", true, 10.847588, 106.775818));
//        listExpert.add(new Expert("Khoa Nguyen", "AI", "Việt Nam", "https://genknews.genkcdn.vn/k:thumb_w/640/2015/a-1436946541628/truyen-tranh-naruto-gaara-sasuke-hai-cuoc-doi-mot-noi-dau.png", false, 10.847588, 106.775818));
//        listExpert.add(new Expert("Tran Trinh", "Nodejs", "Việt Nam", "https://cdn1.thr.com/sites/default/files/2017/03/chris_evans_captain_america_uk_getty_h_2016.jpg", true, 10.847588, 106.775818));
//        listExpert.add(new Expert("Naruto", "Ninja", "Japan", "https://pbs.twimg.com/media/DX1Rd1xUMAABmVm.jpg", false, 10.847588, 106.775818));
        searchProcess();
    }

    private void searchProcess() {
        if(txtSearch.getText().toString().trim().equals("")){
            txtSearch.setText("all");
        }
        if(txtSearch.getText().toString().trim().equals("all")){
            getAllData();
        }else{
            getSearchData(txtSearch.getText().toString().trim());
        }
    }

    public void getSearchData(String key) {
        final APIService apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL).create(APIService.class);
        Call<List<Expert>> call = apiService.getExpertbyKeyword(key);
        showDialog();
        Log.e("URL", call.request().url() + " ");
        call.enqueue(new Callback<List<Expert>>() {
            @Override
            public void onResponse(Call<List<Expert>> call, Response<List<Expert>> response) {
                hideDialog();
                List<Expert> list_Expert = response.body();
                listExpert.clear();
                listExpert.addAll(list_Expert);
                adapterExpert.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Expert>> call, Throwable t) {
                hideDialog();
                Log.e("onFailure: ", "something fail ");
                Toast.makeText(getActivity(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getAllData(){
        final APIService apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL).create(APIService.class);
        Call<List<Expert>> call = apiService.getListExpert();
        showDialog();
        Log.e("URL", call.request().url() + " ");
        call.enqueue(new Callback<List<Expert>>() {
            @Override
            public void onResponse(Call<List<Expert>> call, Response<List<Expert>> response) {
                hideDialog();
                List<Expert> list_Expert = response.body();
                listExpert.addAll(list_Expert);
                adapterExpert.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Expert>> call, Throwable t) {
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
