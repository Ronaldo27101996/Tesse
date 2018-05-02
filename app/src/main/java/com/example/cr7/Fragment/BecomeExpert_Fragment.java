package com.example.cr7.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cr7.Model.Career;
import com.example.cr7.Model.Expert;
import com.example.cr7.Model.Skill;
import com.example.cr7.Model.User;
import com.example.cr7.Rest.APIService;
import com.example.cr7.Rest.RetrofitClient;
import com.example.cr7.tesse.ContainerActivity;
import com.example.cr7.tesse.LoginActivity;
import com.example.cr7.tesse.MapPickerActivity;
import com.example.cr7.tesse.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CR7 on 4/11/2018.
 */

public class BecomeExpert_Fragment extends Fragment {
    User user;
    ImageView imgAva, imgExactlyLocation;
    TextView txtExactlyLocation;
    EditText txtFirstName, txtLastName, txtEmail, txtPass, txtSDT, txtCountry;
    AutoCompleteTextView txtCareer;
    MultiAutoCompleteTextView txtSkill;
    String strAvatar;
    Button btnSave;
    ArrayList<Career> listCareer = new ArrayList<>();
    ArrayList<Skill> listSkill = new ArrayList<>();
    //ArrayList<Skill> listSkillExist = new ArrayList<>();
    int career_id = -1;
    int skill_id = -1;
    ArrayList<String> arrCareer = new ArrayList<>();
    ArrayList<String> arrSkill = new ArrayList<>();
    ArrayList<Integer> arrSkilltoSave = new ArrayList<>();
    ArrayList<String> arrSkilltoShow = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.becom_expert_info, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addControls(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            user = (User) bundle.getSerializable("user");
        }

        imgExactlyLocation = view.findViewById(R.id.imgExactlyLocation);
        txtExactlyLocation = view.findViewById(R.id.txtExactlyLocation);
        btnSave = view.findViewById(R.id.btnSave);
        imgAva = view.findViewById(R.id.imgAvatar);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtPass = view.findViewById(R.id.txtPass);
        txtFirstName = view.findViewById(R.id.txtFName);
        txtLastName = view.findViewById(R.id.txtLName);
        txtCareer = view.findViewById(R.id.txtCareer);

        txtCountry = view.findViewById(R.id.txtCountry);
        txtSkill = view.findViewById(R.id.txtSkill);
        txtSDT = view.findViewById(R.id.txtSdt);
        //loadData();
        txtSkill.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        txtCareer.setThreshold(1);
        txtEmail.setText(user.getIdUser());
        txtFirstName.setText(user.getfName());
        txtLastName.setText(user.getlName());
        txtPass.setText(user.getPassword());
        Glide.with(getActivity()).load(user.getAvatar()).into(imgAva);
        strAvatar=user.getAvatar();


    }

//    private void loadSkillExist(String idExpert) {
//        final APIService apiService = RetrofitClient.getClient().create(APIService.class);
//        Call<List<Skill>> call = apiService.getSkillByExpert(idExpert);
//        Log.e("URL", call.request().url() + " ");
//        call.enqueue(new Callback<List<Skill>>() {
//            @Override
//            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
//                listSkillExist = (ArrayList<Skill>) response.body();
//                String str="";
//                for (Skill skill : listSkillExist) {
//                    str+=skill.getName()+", ";
//                }
//                txtSkill.setText(str);
//            }
//
//            @Override
//            public void onFailure(Call<List<Skill>> call, Throwable t) {
//                Log.e("onFailure: ", "something fail: " + t.getMessage());
//                Toast.makeText(getActivity(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void addEvents() {

        txtSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (career_id == -1) {
                    Toast.makeText(getActivity(), "Please choose Career", Toast.LENGTH_SHORT).show();
                    txtCareer.requestFocus();
                    return;
                } else {
                    getSkill(career_id);
                    txtSkill.showDropDown();
                }

            }
        });
        txtSkill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long l) {

                skill_id = listSkill.get(pos).getId();
                Log.e("Skill Id choose: ", skill_id + "");

//                for (int i = 0; i < arrSkill.size(); i++) {
//                    if (arrSkill.get(i).equals(parent.getItemAtPosition(pos).toString())) {
//                        skill_id = i;
                for (int j : arrSkilltoSave) {
                    if (j == skill_id) {
//                         just show old list
                        String strSkillShow = "";
                        for (String str : arrSkilltoShow) {
                            strSkillShow += str + ", ";
                        }
                        txtSkill.setText(strSkillShow);
                        return;
                    }
                }
                arrSkilltoSave.add(skill_id);
                arrSkilltoShow.add(listSkill.get(pos).getName());
//                    }
//                }
                Log.e("arrSkilltoSave: ", arrSkilltoSave.toString());
                // show new list
                String strSkillShow = "";
                for (String str : arrSkilltoShow) {
                    strSkillShow += str + ", ";
                }
                txtSkill.setText(strSkillShow);
            }
        });


        txtCareer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Toast.makeText(getActivity(), "onFocusChange", Toast.LENGTH_LONG).show();
                    loadData();
                    ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrSkill);
                    txtSkill.setAdapter(adapter);
                    txtCareer.showDropDown();
                }

            }
        });
        txtCareer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long l) {

                for (int i = 0; i < arrCareer.size(); i++) {
                    if (arrCareer.get(i).equals(parent.getItemAtPosition(pos).toString())) {
                        career_id = i + 1;
                        Log.e("Career Choose ", career_id + "");
//                        Toast.makeText(parent.getContext(), "Choose " + career_id,
//                                Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }
        });
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
                String idExpert = txtEmail.getText().toString();
                String password = txtPass.getText().toString();
                String lName = txtLastName.getText().toString();
                String fName = txtFirstName.getText().toString();
                String sdt = txtSDT.getText().toString();
                String career = txtCareer.getText().toString();
                String country = txtCountry.getText().toString();

                String image = strAvatar;
                int isOnline = 1;
                double lat = ContainerActivity.LAT;
                double lon = ContainerActivity.LON;
                //Expert expert= new Expert(idExpert,password,lName,fName,sdt,career,country,image,isOnline,lat,lon);
                final APIService apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL).create(APIService.class);
                Call<Expert> call = apiService.addExpert(idExpert,password,lName,fName,career,country,image,0,lat,lon,sdt);
                Log.e("URL", call.request().url() + " ");
                call.enqueue(new Callback<Expert>() {
                    @Override
                    public void onResponse(Call<Expert> call, Response<Expert> response) {
                        if(response.body()!=null){
                            Expert ex = response.body();
                            LoginActivity.isExpert=1;
                            Toast.makeText(getActivity(), ""+ex.getIdExpert()+" is expert now", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Expert> call, Throwable t) {
                        Log.e("onFailure: ", "something fail: " + t.getMessage());
                        Toast.makeText(getActivity(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });
                addExpert_Skill();
                //getFragmentManager().popBackStack("landing_page", 0);
            }
        });

        imgExactlyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMap();
            }
        });
        txtExactlyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMap();
            }
        });
    }

    private void addExpert_Skill() {
        for(int i=0;i<arrSkilltoSave.size();i++){
            final APIService apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL).create(APIService.class);
            Call<Integer> call = apiService.addExpert_Skill(txtEmail.getText().toString().trim(),arrSkilltoSave.get(i));
            Log.e("URLEx", call.request().url() + " ");
            final int finalI = i;
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    Log.e("Test Expert_ Skill", response.body()+"" ); // 1 is success
                    if(finalI ==arrSkilltoSave.size()-1){
                        updateUserBecomeExpert();
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

    }

    private void updateUserBecomeExpert() {
        final APIService apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL).create(APIService.class);
        Call<Integer> call = apiService.updateUserbecomeExpert(txtEmail.getText().toString().trim(),LoginActivity.isExpert);
        Log.e("URL", call.request().url() + " ");
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body()!=0){
                    Log.e("update Success",""+response.body() );
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

    private void pickMap() {
        Intent intent = new Intent(getActivity(), MapPickerActivity.class);
        startActivity(intent);
    }


    private void getSkill(int career_id) {
        final APIService apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL).create(APIService.class);
        Call<List<Skill>> call = apiService.getSkillByCareer(career_id);
        Log.e("URL", call.request().url() + " ");
        call.enqueue(new Callback<List<Skill>>() {
            @Override
            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                listSkill = (ArrayList<Skill>) response.body();
                arrSkill.clear();
                for (Skill skill : listSkill) {
                    arrSkill.add(skill.getName());
                }
            }

            @Override
            public void onFailure(Call<List<Skill>> call, Throwable t) {
                Log.e("onFailure: ", "something fail: " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {
        final APIService apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL).create(APIService.class);
        Call<List<Career>> call = apiService.getListCareer();
        Log.e("URL", call.request().url() + " ");
        call.enqueue(new Callback<List<Career>>() {
            @Override
            public void onResponse(Call<List<Career>> call, Response<List<Career>> response) {
                listCareer = (ArrayList<Career>) response.body();
                arrCareer.clear();
                for (Career career : listCareer) {
                    arrCareer.add(career.getNameCareer());
                }
                ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrCareer);
                txtCareer.setAdapter(adapter);
                Log.e("arrCareer", arrCareer.toString());

            }

            @Override
            public void onFailure(Call<List<Career>> call, Throwable t) {
                Log.e("onFailure: ", "something fail: " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
