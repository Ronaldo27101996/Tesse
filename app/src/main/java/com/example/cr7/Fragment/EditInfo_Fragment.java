package com.example.cr7.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cr7.Model.User;
import com.example.cr7.Rest.APIService;
import com.example.cr7.Rest.RetrofitClient;
import com.example.cr7.tesse.LoginActivity;
import com.example.cr7.tesse.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CR7 on 4/11/2018.
        */

public class EditInfo_Fragment extends Fragment {
    ImageView imgAva;
    EditText txtFirstName,txtLastName,txtPass,txtEmail;
    User user;
    Button btnSave;
    String strAvatar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_editprofile,container,false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addControls(View view) {
        imgAva= view.findViewById(R.id.imgAvatar);
        txtLastName=view.findViewById(R.id.txtLName);
        txtFirstName =view.findViewById(R.id.txtFName);
        txtPass=view.findViewById(R.id.txtPass);
        btnSave= view.findViewById(R.id.btnSave);
        txtEmail = view.findViewById(R.id.txtEmail);
        Bundle bundle = getArguments();
        if (bundle != null) {
            user = (User) bundle.getSerializable("user");
        }
        Glide.with(getActivity()).load(user.getAvatar()).into(imgAva);
        txtPass.setText(user.getPassword());
        txtFirstName.setText(user.getfName());
        txtLastName.setText(user.getlName());
        txtEmail.setText(user.getIdUser());
        txtEmail.setEnabled(false);
        strAvatar = user.getAvatar();

    }

    private void addEvents() {
        imgAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getActivity(), imgAva);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_avatar, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        //Toast.makeText(ContainerActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        if (item.getItemId() == R.id.dakota) {
                            strAvatar = "https://znews-photo-td.zadn.vn/w960/Uploaded/mdf_drkydd/2017_04_12/8up.jpg";

                        } else if (item.getItemId() == R.id.eva) {
                            strAvatar = "https://media.wmagazine.com/photos/58540202c7188f9b26c951c5/2:3/w_640/0816.w.MM_.eva_.lo30_View5-copy-copy_RGB.jpg";

                        } else if (item.getItemId() == R.id.scarlet) {
                            strAvatar = "http://hot97svg.com/wp-content/uploads/2018/01/Scarlett-Johansson-2014-1170x658.jpg";

                        } else if (item.getItemId() == R.id.olsen) {
                            strAvatar = "https://i-giaitri.vnecdn.net/2016/05/06/elizabeth-olsen-01-1462524673_680x0.jpg";

                        } else if (item.getItemId() == R.id.taylor) {
                            strAvatar = "https://wallpapersmug.com/large/8ef669/sunglasses_taylor_swift_white_dress.jpg";
                        }

                        Glide.with(getActivity()).load(strAvatar).into(imgAva);
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            updateInfoUser();

            }
        });
    }

    private void updateInfoUser() {
        final APIService apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL).create(APIService.class);
        Call<Integer> call = apiService.updateUserInfo(txtEmail.getText().toString().trim(),
                txtPass.getText().toString().trim(),
                txtFirstName.getText().toString().trim(),
                txtLastName.getText().toString().trim(),
                strAvatar);
        Log.e("URL", call.request().url() + " ");
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body()!=0){
                   updateInfoExpert();
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("onFailure: ", "something fail: " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void updateInfoExpert(){
        final APIService apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL).create(APIService.class);
        Call<Integer> call = apiService.updateExpertInfo(txtEmail.getText().toString().trim(),
                txtPass.getText().toString().trim(),
                txtFirstName.getText().toString().trim(),
                txtLastName.getText().toString().trim(),
                strAvatar);
        Log.e("URL", call.request().url() + " ");
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body()!=0){
                    Log.e("update Success",""+response.body() );
                    Toast.makeText(getActivity(), "Update Successful!!!", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStackImmediate();
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("onFailure: ", "something fail: " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
