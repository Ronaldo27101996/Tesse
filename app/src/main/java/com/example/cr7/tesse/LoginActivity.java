package com.example.cr7.tesse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cr7.Model.Expert;
import com.example.cr7.Model.SharePref;
import com.example.cr7.Model.User;
import com.example.cr7.Rest.APIService;
import com.example.cr7.Rest.RetrofitClient;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.txtId)
    EditText txtID;
    @BindView(R.id.txtPass)
    EditText txtPass;
    @BindView(R.id.ckbRemember)
    CheckBox ckbRemember;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.txtSignUp)
    TextView txtSignUp;
    @BindView(R.id.linearWrapper)
    LinearLayout linearWrapper;
    @BindView(R.id.imgLogo)
    ImageView imgLogo;

    ProgressDialog mProgressDialog;
    public static int isExpert = 0;
    public static User user = null;
    public static String USER_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        SharePref.getInstance(getApplicationContext());
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Boolean b = SharePref.readBoolean(SharePref.ISCHECK, false);
        if (b == true) {
            txtID.setText(SharePref.readString(SharePref.EMAIL, ""));
            txtPass.setText(SharePref.readString(SharePref.PASS, ""));
            ckbRemember.setChecked(b);
        } else {
            txtID.setText("");
            txtPass.setText("");
            ckbRemember.setChecked(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharePref.writeString(SharePref.EMAIL, txtID.getText().toString().trim());
        SharePref.writeString(SharePref.PASS, txtPass.getText().toString().trim());
        SharePref.writeBoolean(SharePref.ISCHECK, ckbRemember.isChecked());
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickLogin();
            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void clickLogin() {
        Log.e("Email+ Pass", txtID.getText().toString().trim() + "__" + txtPass.getText().toString());
        if (txtID.getText().toString().equals("")) {
            txtID.requestFocus();
            Toast.makeText(this, "Mời nhập Email ", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtPass.getText().toString().equals("")) {
            txtPass.requestFocus();
            Toast.makeText(this, "Mời nhập Pass ", Toast.LENGTH_SHORT).show();
        } else {
            loginProcess();
        }

    }

    private void loginProcess() {
        final APIService apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL).create(APIService.class);
        Call<List<User>> call = apiService.getUser(txtID.getText().toString().trim(), txtPass.getText().toString().trim());
        showDialog();
        Log.e("URL", call.request().url() + " ");
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                if (users.size() != 0) {
                    hideDialog();
                     user = users.get(0);
                    USER_ID = user.getIdUser();
                    if (user.getIsExpert() == 0) {
                        isExpert = 0;

                    } else if (user.getIsExpert() == 1) {
                        isExpert = 1;
                        //getExpert(user.getIdUser());
                    }
                    //Expert expert = new Expert(user.getIdUser(),user.getPassword(),user.getlName(),user.getfName(),"","",user.getAvatar(),0,10.847588,106.775818,"");
                    Intent intent = new Intent(LoginActivity.this, ContainerActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);

                } else {
                    hideDialog();
                    Toast.makeText(LoginActivity.this, "User name or password is not correct", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                hideDialog();
                Log.e("onFailure: ", "something fail: " + t.getMessage());
                Toast.makeText(LoginActivity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
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

    public void AppExit()
    {
        this.finish();
        Intent intent= new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    @Override
    public void onBackPressed() {
        AppExit();
    }
}
