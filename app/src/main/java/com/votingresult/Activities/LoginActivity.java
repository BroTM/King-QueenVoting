package com.votingresult.Activities;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.firebase.client.DataSnapshot;
        import com.firebase.client.Firebase;
        import com.firebase.client.FirebaseError;
        import com.firebase.client.ValueEventListener;
        import com.votingresult.Model.LoginModel;
        import com.votingresult.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_login;
    EditText edit_pass;
    TextView invalid_tv;
    Firebase sf;
    boolean isTrue;
    LoginModel login=new LoginModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login= (Button) findViewById(R.id.login_button);
        edit_pass= (EditText) findViewById(R.id.password);
        invalid_tv= (TextView) findViewById(R.id.invalid_name_password);
        Firebase.setAndroidContext(LoginActivity.this);
        sf=new Firebase("https://randomnumber-cba06.firebaseio.com/");
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final String input=edit_pass.getText().toString();
        sf.child("Random Login").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                check(dataSnapshot,input);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    private void check(DataSnapshot dataSnapshot, String input) {
        for (DataSnapshot sh:dataSnapshot.getChildren())
        {

            login.setRandom(sh.getValue(LoginModel.class).getRandom());
            if(login.getRandom().equals(input))
            {
               /* startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                finish();*/
                login.setKing(sh.getValue(LoginModel.class).isKing());
                login.setQueen(sh.getValue(LoginModel.class).isQueen());
                login.setMaster(sh.getValue(LoginModel.class).isMaster());
                login.setMiss(sh.getValue(LoginModel.class).isMiss());
                login.setPrincess(sh.getValue(LoginModel.class).isPrincess());
                login.setPrince(sh.getValue(LoginModel.class).isPrince());
                isTrue=true;
                break;
            }
            else
            {/* invalid_tv.setVisibility(View.VISIBLE);
                edit_pass.setText("");*/
                isTrue=false;

            }
        }
        if(isTrue)
        {

            SharedPreferences share=getSharedPreferences("MyAccount",MODE_PRIVATE);
            SharedPreferences.Editor editor=share.edit();
            editor.putString("id",login.getRandom());
            editor.putBoolean("king",login.isKing());
            editor.putBoolean("queen",login.isQueen());
            editor.putBoolean("master",login.isMaster());
            editor.putBoolean("miss",login.isMiss());
            editor.putBoolean("prince",login.isPrince());
            editor.putBoolean("princess",login.isPrincess());

            editor.commit();
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
        }
        else
        {invalid_tv.setVisibility(View.VISIBLE);
            edit_pass.setText("");}
    }
}
