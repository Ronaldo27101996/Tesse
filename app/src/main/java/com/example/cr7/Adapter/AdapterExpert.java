package com.example.cr7.Adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cr7.Fragment.Appointment_Fragment;
import com.example.cr7.Model.Expert;
import com.example.cr7.tesse.LoginActivity;
import com.example.cr7.tesse.MapDirectionActivity;
import com.example.cr7.tesse.R;

import java.util.List;

/**
 * Created by CR7 on 3/12/2018.
 */

public class AdapterExpert extends RecyclerView.Adapter<AdapterExpert.ExpertViewHolder>{
    private List<Expert> listExpert;
    private Context context;

    public AdapterExpert(List<Expert> listExpert, Context context) {
        this.listExpert = listExpert;
        this.context = context;
    }

    @Override
    public AdapterExpert.ExpertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_expert,parent,false);
        return new ExpertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterExpert.ExpertViewHolder holder, final int position) {
        Expert expert = listExpert.get(position);
        holder.txtName.setText(expert.getfName()+" "+expert.getlName());
        holder.txtCareer.setText(expert.getCareer());
        holder.txtCountry.setText(expert.getCountry());
        if(expert.getIsOnline()==1){
            holder.imgOnOff.setImageResource(R.drawable.icon_online);
            holder.txtOnOff.setText("Online");
        }else{
            holder.imgOnOff.setImageResource(R.drawable.icon_offline);
            holder.txtOnOff.setText("Offline");
        }
        //if(conversation.getImage().equals("")!=false){
        Glide.with(context)
                .load(expert.getImage())
                .into(holder.imgAvatar);
        // }
        holder.imgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateAppointment(position);
            }
        });
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goCall(position);
            }
        });
        holder.btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goMessage(position);
            }
        });
        holder.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goDirection(position);
            }
        });

    }

    private void goDirection(int position) {
        Intent intent =new Intent(context,MapDirectionActivity.class);
        intent.putExtra("lat",listExpert.get(position).getLat());
        intent.putExtra("lon",listExpert.get(position).getLon());
        context.startActivity(intent);
    }

    private void goMessage(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms: " + listExpert.get(position).getSdt()));
        context.startActivity(intent);

    }

    private void goCall(int position) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + listExpert.get(position).getSdt()));
            context.startActivity(intent);
        } catch (Exception e) {
            //TODO smth
        }
    }
    private void dateAppointment(int pos) {
        Bundle bundle = new Bundle();
        bundle.putString("expert_id", listExpert.get(pos).getIdExpert());
        FragmentTransaction ft = ((Activity) context).getFragmentManager().beginTransaction();
        Fragment prev = (((Activity) context).getFragmentManager()).findFragmentByTag("dialogAppointment");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment dialogFragment = new Appointment_Fragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(ft, "dialogAppointment");
    }

    @Override
    public int getItemCount() {
        return listExpert.size();
    }
    public class ExpertViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName,txtCareer,txtCountry,txtOnOff;
        private ImageView imgAvatar,imgOnOff,imgDate,btnMessage,btnCall,btnMap;
        public ExpertViewHolder(View itemView) {
            super(itemView);
            txtName= itemView.findViewById(R.id.txtName);
            txtCareer= itemView.findViewById(R.id.txtCareer);
            txtCountry= itemView.findViewById(R.id.txtCountry);
            txtOnOff= itemView.findViewById(R.id.txtOnOff);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            imgOnOff = itemView.findViewById(R.id.imgOnOff);
            imgDate = itemView.findViewById(R.id.btnDate);
            btnCall = itemView.findViewById(R.id.btnCall);
            btnMessage = itemView.findViewById(R.id.btnChat);
            btnMap =itemView.findViewById(R.id.btnMap);
        }
    }
}

