package com.bgenterprise.roompractise;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private UsersRoomDatabase usersDb;
    String user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View view1 = findViewById(R.id.base_layout);
        usersDb = UsersRoomDatabase.getFileDatabase(MainActivity.this);

        MaterialButton btn_register = findViewById(R.id.btn_register);
        MaterialButton btn_login = findViewById(R.id.btn_login);
        final TextInputEditText et_username = findViewById(R.id.et_username);
        final TextInputEditText et_password = findViewById(R.id.et_password);
        final TextInputLayout txtinput_password = findViewById(R.id.txtinput_password);
        final TextInputLayout txtinput_username = findViewById(R.id.txtinput_username);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(isEmptyField(et_password.getText().toString())){
                    txtinput_username.setErrorEnabled(true);
                    txtinput_username.setError("Fill in your username details.");
                }else if(isEmptyField(et_username.getText().toString())){
                    txtinput_password.setErrorEnabled(true);
                    txtinput_password.setError("Fill in your password details.");
                }else{
                    checkUser chkUser = new checkUser(MainActivity.this){
                        @Override
                        protected void onPostExecute(List<Users> users) {
                            super.onPostExecute(users);
                            if(!users.isEmpty()){
                                Log.d("user_key", users.get(0).getFullname());
                                startActivity(new Intent(MainActivity.this, LandingPage.class));
                            }else{
                                Snackbar.make(view1, "Login details not found", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    };chkUser.execute(et_username.getText().toString(), et_password.getText().toString());

                }
            }
        });
    }

    public boolean isEmptyField(String text){
        return text.isEmpty();
    }

    @SuppressLint("StaticFieldLeak")
    public class checkUser extends AsyncTask<String, Void, List<Users>>{
        Context context;
        List<Users> singleUser = new ArrayList<>();

        checkUser(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected List<Users> doInBackground(String... strings) {
            try{
                singleUser = usersDb.getUsersDao().selectSingleUser(strings[0], strings[1]);
                return singleUser;
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }

        }
    }
}
