package com.example.cr7.tesse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cr7.Model.User;
import com.example.cr7.Rest.APIService;
import com.example.cr7.Rest.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    TextView txtTitle;
    ImageView imgAva;
    EditText txtFirstName, txtLastName, txtEmail, txtPass;
    String strAvatar;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_editprofile);
        addControls();
        addEvents();
    }

    private void addControls() {
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Sign Up");
        btnSave = findViewById(R.id.btnSave);
        btnSave.setText("Done");
        imgAva = findViewById(R.id.imgAvatar);
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        txtFirstName = findViewById(R.id.txtFName);
        txtLastName = findViewById(R.id.txtLName);

    }

    private void addEvents() {
        imgAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(SignUpActivity.this, imgAva);
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

                        Glide.with(SignUpActivity.this).load(strAvatar).into(imgAva);
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idUser = txtEmail.getText().toString();
                String password = txtPass.getText().toString();
                String lName = txtLastName.getText().toString();
                String fName = txtFirstName.getText().toString();
                String image = strAvatar;
                if (txtEmail.getText().toString().trim().equals("")) {
                    txtEmail.requestFocus();
                    Toast.makeText(SignUpActivity.this, "Mời nhập MailID ", Toast.LENGTH_SHORT).show();
                } else if (txtFirstName.getText().toString().trim().equals("")) {
                    txtFirstName.requestFocus();
                    Toast.makeText(SignUpActivity.this, "Mời nhập First Name", Toast.LENGTH_SHORT).show();
                } else if (txtLastName.getText().toString().trim().equals("")) {
                    txtLastName.requestFocus();
                    Toast.makeText(SignUpActivity.this, "Mời nhập Last Name", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(idUser, password, fName, lName, image, 0);
                    //User user= new User("idUser","password","txtFirstName.getText().toString()","lName","image",0);

                    signUpProcess(user);

                }


            }
        });
    }

    private void signUpProcess(User user) {
        final APIService apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL).create(APIService.class);
        Call<User> call = apiService.addUser(user.getIdUser(),
                user.getPassword(),
                user.getfName(),
                user.getlName(),
                user.getAvatar(),
                user.getIsExpert());
        Log.e("URL", call.request().url() + " ");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user != null) {
                    LoginActivity.user=user;
                    Toast.makeText(SignUpActivity.this, "Success!!!", Toast.LENGTH_SHORT).show();
                    LoginActivity.isExpert=0;
                    LoginActivity.USER_ID= user.getIdUser();
                    Intent intent = new Intent(SignUpActivity.this, ContainerActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
        }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("onFailure: ", "something fail ");
                Toast.makeText(SignUpActivity.this, "Check your connection or server", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
