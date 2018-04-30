package com.example.cr7.tesse;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cr7.Fragment.LandingPageFragment;
import com.example.cr7.Model.Expert;

// NOT USE
public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    View header;
    NavigationView navigationView;

//    private List<Expert> listExpert;
//    private RecyclerView rvExpert;
//    private AdapterExpert adapterExpert;
    ImageView imgAvatarHeader;
    FloatingActionButton fab;
    ProgressDialog mProgressDialog;
    TextView txtNameHeader, txtMailHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


//        toggle= new ActionBarDrawerToggle(
//                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
        //toggle.syncState();
        getSupportActionBar().hide();

        addControls();
        addEvents();
    }

    private void addEvents() {
//        imgAvatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!drawer.isDrawerOpen(GravityCompat.START)) {
//                    drawer.openDrawer(GravityCompat.START);
//                }
//            }
//        });
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId()==android.R.id.home){
//            drawer.openDrawer(GravityCompat.START);
//        }
//        else {
//            drawer.closeDrawer(GravityCompat.START);
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void addControls() {
        drawer = findViewById(R.id.drawer_layout); //drawerlayout
        navigationView = findViewById(R.id.nav_view); // navigation (menu) in drawer layout
        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0); // header of the navigation

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        LandingPageFragment landingPageFragment = new LandingPageFragment();
        fragmentTransaction.replace(R.id.layoutContent,landingPageFragment);
        fragmentTransaction.commit();
        //rvExpert = findViewById(R.id.rvExpert);
        //imgAvatar = findViewById(R.id.imgAvatarmini);
        imgAvatarHeader = header.findViewById(R.id.imgAvatarHeader);

        txtMailHeader = header.findViewById(R.id.txtMailHeader);
        txtNameHeader = header.findViewById(R.id.txtNameHeader);

        Expert expert = (Expert) getIntent().getSerializableExtra("expert");
        txtNameHeader.setText(expert.getfName()+" "+expert.getlName());
        txtMailHeader.setText(expert.getIdExpert().toString());
        Glide.with(getApplicationContext()).load(expert.getImage()).into(imgAvatarHeader);


//        Glide.with(getApplicationContext())
//                .load(user.getAvatar())
//                .into(imgAvatar);
//
//        listExpert = new ArrayList<>();
////        listExpert.add(new Expert("Huy Duy", "Mobile Developer App Android", "Việt Nam", "http://cdni.condenast.co.uk/320x480/d_f/Emma-Watson-close-up-Vogue-28Aug15-Getty_b_320x480.jpg", true, 10.847588, 106.775818));
////        listExpert.add(new Expert("Huong To", "Mobile Developer ", "Việt Nam", "http://www.elle.vn/wp-content/uploads/2015/10/30/emma-stone.jpg", true, 10.847588, 106.775818));
////        listExpert.add(new Expert("Khoa Nguyen", "AI", "Việt Nam", "https://genknews.genkcdn.vn/k:thumb_w/640/2015/a-1436946541628/truyen-tranh-naruto-gaara-sasuke-hai-cuoc-doi-mot-noi-dau.png", false, 10.847588, 106.775818));
////        listExpert.add(new Expert("Tran Trinh", "Nodejs", "Việt Nam", "https://cdn1.thr.com/sites/default/files/2017/03/chris_evans_captain_america_uk_getty_h_2016.jpg", true, 10.847588, 106.775818));
////        listExpert.add(new Expert("Naruto", "Ninja", "Japan", "https://pbs.twimg.com/media/DX1Rd1xUMAABmVm.jpg", false, 10.847588, 106.775818));
//
//        final APIService apiService = RetrofitClient.getClient().create(APIService.class);
//        Call<List<Expert>> callMail = apiService.getListExpert();
//        Log.e("URL", callMail.request().url() + " ");
//        callMail.enqueue(new Callback<List<Expert>>() {
//            @Override
//            public void onResponse(Call<List<Expert>> call, Response<List<Expert>> response) {
//                List<Expert> list_Expert = response.body();
//                listExpert.addAll(list_Expert);
//            }
//
//            @Override
//            public void onFailure(Call<List<Expert>> call, Throwable t) {
//                Log.e("onFailure: ", "something fail mail");
//                Toast.makeText(DrawerActivity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        adapterExpert = new AdapterExpert(listExpert, DrawerActivity.this);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        rvExpert.setLayoutManager(mLayoutManager);
//        rvExpert.setAdapter(adapterExpert);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//            FragTest fragTest = new FragTest();
//            fragmentTransaction.replace(R.id.layoutContent,fragTest);
//            fragmentTransaction.commit();

        } else if (id == R.id.nav_gallery) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            LandingPageFragment fragTest = new LandingPageFragment();
            fragmentTransaction.replace(R.id.layoutContent,fragTest);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
