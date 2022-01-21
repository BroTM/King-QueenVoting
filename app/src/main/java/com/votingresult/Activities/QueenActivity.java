package com.votingresult.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.votingresult.Adapters.QueenAdapter;
import com.votingresult.R;

/**
 * Created by MAT on 12/26/2017.
 */

public class QueenActivity extends Fragment {
    String name[] = {"Thet Hnin Phyu", "Thet Hnin Phyu"};
    String age[] = {"06/16/2017", "06/16/2017"};
    String height[]={" 5'2" ," 5' 3"};
    String natives[]={"Yamethin","Yangon"};
    String favourite_color[]={"Blue","Yellow"};
    String hobbies[]={"Travelling","Reading"};
    String idol_person[] = {"U Ba", "U Hla"};
    String fb_account[] = {"Thet Hnin Phyu", "Thet Hnin Phyu"};
    int profile[]={R.drawable.cover,R.drawable.ucsone};
    RecyclerView king_recycler;
    RecyclerView.Adapter king_adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_queen, container, false);
        king_recycler = (RecyclerView) v.findViewById(R.id.queen_recycler);
        king_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        king_adapter = new QueenAdapter(getContext(), name, age,height,natives,favourite_color,hobbies,idol_person,fb_account,profile);
        king_recycler.setAdapter(king_adapter);
        return v;
    }
}
