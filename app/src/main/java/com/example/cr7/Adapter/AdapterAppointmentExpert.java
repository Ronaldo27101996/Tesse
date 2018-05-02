package com.example.cr7.Adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cr7.Fragment.Add_Appointment_Fragment;
import com.example.cr7.Fragment.Confirm_Appointment_Fragment;
import com.example.cr7.Model.Appointment;
import com.example.cr7.tesse.MapDirectionActivity;
import com.example.cr7.tesse.R;

import java.util.List;

/**
 * Created by CR7 on 5/2/2018.
 */

public class AdapterAppointmentExpert extends RecyclerView.Adapter<AdapterAppointmentExpert.AppointmentViewHolder>{
        private List<Appointment> listAppointment;
        private Context context;

        public AdapterAppointmentExpert(List<Appointment> listAppointment, Context context) {
            this.listAppointment = listAppointment;
            this.context = context;
        }

    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_appointment_expert,parent,false);
        return new AppointmentViewHolder(view);
    }

    @Override
        public void onBindViewHolder(AppointmentViewHolder holder, final int position) {
            Appointment appointment = listAppointment.get(position);
            holder.txtDate.setText(appointment.getDate());
            holder.txtTime.setText(appointment.getTime());
            holder.txtIDExpert.setText("From: "+ appointment.getIdUser());
        if(appointment.getIsAccepted()==1){
            holder.imgAccept.setImageResource(R.drawable.accept_icon);
            holder.txtAccept.setText("Accepted ");
            holder.txtAccept.setTextColor(Color.GREEN);
        }else if(appointment.getIsAccepted()==0){
            holder.imgAccept.setImageResource(R.drawable.deny_icon);
            holder.txtAccept.setText("Deny ");
            holder.txtAccept.setTextColor(Color.RED);

        }else if(appointment.getIsAccepted()==2){
            holder.imgAccept.setImageResource(R.drawable.icon_wait);
            holder.txtAccept.setText("Waiting ");
            holder.txtAccept.setTextColor(Color.GRAY);

        }
            holder.btnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goDirection(position);
                }
            });
        }

    private void goDirection(int position) {
        Intent intent =new Intent(context,MapDirectionActivity.class);
        intent.putExtra("lat",listAppointment.get(position).getLat());
        intent.putExtra("lon",listAppointment.get(position).getLon());
        context.startActivity(intent);
    }


        @Override
        public int getItemCount() {
            return listAppointment.size();
        }
        public class AppointmentViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{
            private TextView txtDate,txtTime,txtIDExpert,txtAccept;
            private ImageView btnMap,imgAccept;
            Button btnAccept,btnDeny;
            public AppointmentViewHolder(View itemView) {
                super(itemView);
                txtDate= itemView.findViewById(R.id.txtDate);
                txtTime= itemView.findViewById(R.id.txtTime);
                txtIDExpert= itemView.findViewById(R.id.txtExpertID);
                btnMap =itemView.findViewById(R.id.btnMap);
                imgAccept =itemView.findViewById(R.id.imgAccept);
                txtAccept=itemView.findViewById(R.id.txtIsAccepted);
                itemView.setOnLongClickListener( this);
                itemView.setOnClickListener( this);

            }

            @Override
            public void onClick(View view) {
                if(listAppointment.get(this.getAdapterPosition()).getIsAccepted()==2){
                    Toast.makeText(context, "Click"+listAppointment.get(this.getAdapterPosition()).getIdUser(), Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("appointment", listAppointment.get(this.getAdapterPosition()));
                    FragmentTransaction ft = ((Activity) context).getFragmentManager().beginTransaction();
                    Fragment prev = (((Activity) context).getFragmentManager()).findFragmentByTag("dialogAppointmentAccept");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);
                    DialogFragment dialogFragment = new Confirm_Appointment_Fragment();
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(ft, "dialogAppointmentAccept");
                }

            }

            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(context, "LongClick"+ listAppointment.get(this.getAdapterPosition()).getIdUser(), Toast.LENGTH_SHORT).show();
                return true;
            }
        }

}
