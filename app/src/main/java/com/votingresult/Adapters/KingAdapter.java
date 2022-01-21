package com.votingresult.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.votingresult.Model.LoginModel;
import com.votingresult.R;

import static com.votingresult.R.id.result_text;

/**
 * Created by MAT on 12/26/2017.
 */

public class KingAdapter extends RecyclerView.Adapter<KingAdapter.KingViewHolder> implements View.OnClickListener {
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
    int king_position;
    LoginModel myAccountData=new LoginModel();

    public KingAdapter(Context context, String[] name, String[] age, String[] height, String[] natives, String[] favourite_color, String[] hobbies, String[] idol_person, String[] fb_account, int[] profile) {
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
    public KingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(context).inflate(R.layout.activity_king_adapter,parent,false);
        KingViewHolder holder=new KingViewHolder(v);
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
                break;
            case R.id.user_register_cancel:
                alertDialog.dismiss();
                break;
        }

    }
    @Override
    public void onBindViewHolder(final KingViewHolder holder, final int position) {
        Firebase.setAndroidContext(context);
        king_firebase=new Firebase("https://randomnumber-cba06.firebaseio.com/");
        king_firebase.child("King_Result").child((position+1)+"").addValueEventListener(new ValueEventListener() {
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
                /*Firebase.setAndroidContext(context);
                king_firebase=new Firebase("https://randomnumber-cba06.firebaseio.com/");*/
                SharedPreferences share=context.getSharedPreferences("MyAccount",Context.MODE_PRIVATE);

               boolean  isKing=share.getBoolean("king",false);
                if(!isKing) {
                    king_firebase.child("King_Result").child((position + 1) + "").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            count = dataSnapshot.getValue(CountModel.class).getCount();
//                            Toast.makeText(context,count+"", Toast.LENGTH_SHORT).show();
                            SharedPreferences shar=context.getSharedPreferences("MyAccount",Context.MODE_PRIVATE);
                            myAccountData.setRandom(shar.getString("id",""));
                            myAccountData.setKing(true);
                            myAccountData.setQueen(shar.getBoolean("queen",false));
                            myAccountData.setMaster(shar.getBoolean("master",false));
                            myAccountData.setMiss(shar.getBoolean("miss",false));
                            myAccountData.setPrince(shar.getBoolean("prince",false));
                            myAccountData.setPrincess(shar.getBoolean("princess",false));


                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                    setDialog();
                }
                else
                {
                    Toast.makeText(context, "You can vote only one King_vote", Toast.LENGTH_SHORT).show();
                }


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
            result_text= (TextView) itemView.findViewById(R.id.result_text);
            name= (TextView) itemView.findViewById(R.id.king_name);
            age= (TextView) itemView.findViewById(R.id.king_age);
            height= (TextView) itemView.findViewById(R.id.king_height);
            natives= (TextView) itemView.findViewById(R.id.king_natives);
            favourite_color= (TextView) itemView.findViewById(R.id.king_favourite_color);
            hobbies= (TextView) itemView.findViewById(R.id.king_hobbies);
            idol_person= (TextView) itemView.findViewById(R.id.king_idol_person);
            fb_account= (TextView) itemView.findViewById(R.id.king_fb_account);
            profile= (ImageView) itemView.findViewById(R.id.king_profile);
            vote_btn= (Button) itemView.findViewById(R.id.king_vote_btn);

        }

    }
    private void updateData(CountModel data) {
        king_firebase.child("King_Result").child((king_position+1)+"").setValue(data);
        king_position=0;
        king_firebase.child("Random Login").child(myAccountData.getRandom()).setValue(myAccountData);
        alertDialog.dismiss();

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
