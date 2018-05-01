package com.example.cr7.tesse;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.bumptech.glide.Glide;
import com.example.cr7.Fragment.BecomeExpert_Fragment;
import com.example.cr7.Fragment.EditInfo_Fragment;
import com.example.cr7.Fragment.LandingPageFragment;
import com.example.cr7.Model.User;

public class ContainerActivity extends AppCompatActivity {
    ImageView imgMenu;
    ImageView imgAvatar;

    public static double LAT = 0;
    public static double LON = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        getSupportActionBar().hide();
        addControls();
        addEvents();
    }

    private void addEvents() {

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(ContainerActivity.this, imgAvatar);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_category, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        //Toast.makeText(ContainerActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        if (item.getItemId() == R.id.Profile) {
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            EditInfo_Fragment editInfo_fragment = new EditInfo_Fragment();

                            fragmentTransaction.replace(R.id.layout_container, editInfo_fragment, "profile");

                            fragmentTransaction.addToBackStack("profile");
                            fragmentTransaction.commit();

                        } else if (item.getItemId() == R.id.Logout) {
                            Toast.makeText(ContainerActivity.this, "Log out success!!!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ContainerActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else if (item.getItemId() == R.id.BecomeExpert) {
                            if (LoginActivity.isExpert == 1) {
                                Toast.makeText(ContainerActivity.this, "You are expert already!", Toast.LENGTH_SHORT).show();
                            } else {
                                User user = (User) getIntent().getSerializableExtra("user");
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("user", user);
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                BecomeExpert_Fragment becomeExpert_fragment = new BecomeExpert_Fragment();
                                becomeExpert_fragment.setArguments(bundle);
                                fragmentTransaction.replace(R.id.layout_container, becomeExpert_fragment, "become_expert");
                                fragmentTransaction.addToBackStack("become_expert");
                                fragmentTransaction.commit();
                            }
                        }
                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });
    }

    private void addControls() {
        //imgMenu =findViewById(R.id.imgMenu);
        imgAvatar = findViewById(R.id.imgAvatar);
        User user = (User) getIntent().getSerializableExtra("user");
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        Glide.with(ContainerActivity.this)
                .load(user.getAvatar())
                .into(imgAvatar);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LandingPageFragment landingPageFragment = new LandingPageFragment();
        landingPageFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.layout_container, landingPageFragment, "landing_page");
        fragmentTransaction.addToBackStack("landing_page");
        fragmentTransaction.commit();
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
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack("landing_page", 0);
        } else {
            AppExit();
        }
    }
}
