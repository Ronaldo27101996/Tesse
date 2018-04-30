package com.example.cr7.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cr7.Model.Expert;
import com.example.cr7.Model.User;
import com.example.cr7.tesse.R;

/**
 * Created by CR7 on 3/23/2018.
 */

public class LandingPageFragment extends Fragment{
    EditText txtSearch;
    ImageView imgSearch;
    Expert expert;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.frag_ladingpage,container,false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtSearch.getText().equals("")){
                    Bundle bundle =new Bundle();
                    bundle.putSerializable("expert",expert);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    FindExpert_Fragment findExpert_fragment = new FindExpert_Fragment();
                    findExpert_fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.layout_container,findExpert_fragment,"find_expert");
                    fragmentTransaction.addToBackStack("find_expert");
                    fragmentTransaction.commit();
                }

            }
        });
    }

    private void addControls(View view) {
        Bundle bundle =getArguments();
        if(bundle!=null){
            expert = (Expert) bundle.getSerializable("expert");
        }
        txtSearch = view.findViewById(R.id.txtSearch);
        imgSearch =view.findViewById(R.id.imgSearch);
    }

}
