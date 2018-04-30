package com.example.cr7.tesse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cr7.Model.Expert;
import com.example.cr7.Model.User;

public class SignUpActivity extends AppCompatActivity {
    TextView txtTitle;
    ImageView imgAva;
    EditText txtFirstName,txtLastName,txtEmail,txtPass;
    String strAvatar;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_editprofile);
    }
    private void addControls() {
        txtTitle = findViewById(R.id.txtTitle);
        btnSave= findViewById(R.id.btnSave);
        imgAva = findViewById(R.id.imgAvatar);
        txtEmail = findViewById(R.id.txtEmail);
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
                        if(item.getItemId()==R.id.dakota){
                            strAvatar="https://znews-photo-td.zadn.vn/w960/Uploaded/mdf_drkydd/2017_04_12/8up.jpg";

                        }else if(item.getItemId()==R.id.eva){
                            strAvatar = "https://media.wmagazine.com/photos/58540202c7188f9b26c951c5/2:3/w_640/0816.w.MM_.eva_.lo30_View5-copy-copy_RGB.jpg";

                        }else if(item.getItemId()==R.id.scarlet){
                            strAvatar ="http://hot97svg.com/wp-content/uploads/2018/01/Scarlett-Johansson-2014-1170x658.jpg";

                        }else if(item.getItemId()==R.id.olsen){
                            strAvatar = "https://i-giaitri.vnecdn.net/2016/05/06/elizabeth-olsen-01-1462524673_680x0.jpg";

                        }else if(item.getItemId()==R.id.taylor){
                            strAvatar ="https://wallpapersmug.com/large/8ef669/sunglasses_taylor_swift_white_dress.jpg";
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
                String idUser = txtEmail.getText().toString() ;
                String password = txtPass.getText().toString();
                String lName= txtLastName.getText().toString();
                String fName= txtFirstName.getText().toString();
                String image= strAvatar;
                if (txtEmail.getText().toString().trim().equals("")) {
                    txtEmail.requestFocus();
                    Toast.makeText(SignUpActivity.this, "Mời nhập MailID ", Toast.LENGTH_SHORT).show();
                } else if (txtFirstName.getText().toString().trim().equals("")) {
                    txtFirstName.requestFocus();
                    Toast.makeText(SignUpActivity.this, "Mời nhập First Name", Toast.LENGTH_SHORT).show();
                } else if (txtLastName.getText().toString().trim().equals("")) {
                    txtLastName.requestFocus();
                    Toast.makeText(SignUpActivity.this, "Mời nhập Last Name", Toast.LENGTH_SHORT).show();
                }
                else {
                    User user= new User(idUser,password,fName,lName,image,0);
                    

                }


            }
        });
    }
}
