package com.votingresult.Adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.votingresult.Model.CountModel;
import com.votingresult.R;

/**
 * Created by MAT on 12/26/2017.
 */

public class QueenAdapter extends RecyclerView.Adapter<QueenAdapter.KingViewHolder> implements View.OnClickListener {
    Context context;
    String name[] ;
    String age[] ;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    String height[];
    String natives[];
    String favourite_color[];
    String hobbies[];
    String idol_person[];
    String fb_account[] ;
    int count;
    int profile[];
    Firebase king_firebase;
    int king_position=0;

    public QueenAdapter(Context context, String[] name, String[] age, String[] height, String[] natives, String[] favourite_color, String[] hobbies, String[] idol_person, String[] fb_account, int[] profile) {
        this.context=context;
        this.name=name;
        this.age=age;
        this.height=height;
        this.natives=natives;
        this.favourite_color=favourite_color;
        this.hobbies=hobbies;
        this.idol_person=idol_person;
        this.fb_account=fb_account;
        this.profile=profile;

    }

    @Override
    public QueenAdapter.KingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_queen_adapter,parent,false);
        QueenAdapter.KingViewHolder holder=new QueenAdapter.KingViewHolder(v);
        return holder;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_register_ok:

                count++;
                CountModel data=new CountModel();
                data.setCount(count);
                updateData(data);
                alertDialog.dismiss();
                break;
            case R.id.user_register_cancel:
                alertDialog.dismiss();
                break;
        }

    }
    @Override
    public void onBindViewHolder(final QueenAdapter.KingViewHolder holder, final int position) {
        Firebase.setAndroidContext(context);
        king_firebase=new Firebase("https://randomnumber-cba06.firebaseio.com/");
        king_firebase.child("Queen_Result").child((position+1)+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int send_count=dataSnapshot.getValue(CountModel.class).getCount();
                holder.result_text.setText(send_count+""+"    Votes");
                Toast.makeText(context, send_count+"", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(context,count+"", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        holder.profile.setImageResource(profile[position]);
        holder.name.setText("အမည္    =  "+name[position]);

        holder.age.setText("အသက္    =  "+age[position]);
        holder.height.setText("အရပ္အျမင့္ ="+height[position]);
        holder.natives.setText("ဇာတိ     = "+natives[position]);
        holder.favourite_color.setText("အနွစ္သက္ဆ့ုးအေရာင္ = "+favourite_color[position]);
        holder.hobbies.setText("၀ါသနာ    =  "+hobbies[position]);
        holder.idol_person.setText("Idol      =  "+idol_person[position]);
        holder.fb_account.setText("Fb Account = "+fb_account[position]);

        holder.vote_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                king_position=position;
                Firebase.setAndroidContext(context);
                king_firebase=new Firebase("https://randomnumber-cba06.firebaseio.com/");
                king_firebase.child("Queen_Result").child((position+1)+"").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        count=dataSnapshot.getValue(CountModel.class).getCount();
//                            Toast.makeText(context,count+"", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                setDialog();

            }
        });

    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class KingViewHolder extends RecyclerView.ViewHolder {
        TextView name ;
        TextView age ;
        TextView height;
        TextView natives;
        TextView favourite_color;
        TextView hobbies;
        TextView idol_person;
        TextView fb_account ;
        ImageView profile;
        Button vote_btn;
        TextView result_text;

        public KingViewHolder(View itemView) {
            super(itemView);
            result_text= (TextView) itemView.findViewById(R.id.queen_result_text);
            name= (TextView) itemView.findViewById(R.id.queen_name);
            age= (TextView) itemView.findViewById(R.id.queen_age);
            height= (TextView) itemView.findViewById(R.id.queen_height);
            natives= (TextView) itemView.findViewById(R.id.queen_natives);
            favourite_color= (TextView) itemView.findViewById(R.id.queen_favourite_color);
            hobbies= (TextView) itemView.findViewById(R.id.queen_hobbies);
            idol_person= (TextView) itemView.findViewById(R.id.queen_idol_person);
            fb_account= (TextView) itemView.findViewById(R.id.queen_fb_account);
            profile= (ImageView) itemView.findViewById(R.id.queen_profile);
            vote_btn= (Button) itemView.findViewById(R.id.queen_vote_btn);

        }

    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot sh:dataSnapshot.getChildren()){
            for(DataSnapshot ss:sh.getChildren()){
                CountModel data=new CountModel();
                data.setCount(ss.getValue(CountModel.class).getCount()+1);
                updateData(data);
            }
        }
    }

    private void updateData(CountModel data) {
        king_firebase.child("Queen_Result").child((king_position+1)+"").setValue(data);



    }
    private void setDialog() {

        View v= LayoutInflater.from(context).inflate(R.layout.king_confirm_dialog,null);
        Button btn_ok= (Button) v.findViewById(R.id.user_register_ok);
        Button btn_cancel= (Button) v.findViewById(R.id.user_register_cancel);
        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        builder=new AlertDialog.Builder(context);
        builder.setView(v);
        alertDialog=builder.create();
        alertDialog.show();
    }
}
