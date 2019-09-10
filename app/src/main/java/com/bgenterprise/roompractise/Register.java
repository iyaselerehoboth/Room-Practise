package com.bgenterprise.roompractise;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class Register extends AppCompatActivity {
    TextInputEditText reg_fullname, reg_username, reg_password;
    MaterialButton reg_button;
    private UsersRoomDatabase userDb;
    String username, fullname, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final View view1 = findViewById(R.id.base_layout);
        userDb = UsersRoomDatabase.getFileDatabase(Register.this);

        reg_fullname = findViewById(R.id.reg_fullname);
        reg_username = findViewById(R.id.reg_username);
        reg_password = findViewById(R.id.reg_password);

        reg_button = findViewById(R.id.btn_submit_registration);

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = reg_username.getText().toString();
                fullname = reg_fullname.getText().toString();
                password = reg_password.getText().toString();

                if(!reg_fullname.getText().toString().isEmpty() && !reg_username.getText().toString().isEmpty() && !reg_password.getText().toString().isEmpty()){
                    @SuppressLint("StaticFieldLeak") insertUser insUser = new insertUser(Register.this) {
                        @Override
                        protected void onPostExecute(Boolean aVoid) {
                            super.onPostExecute(aVoid);
                            if(aVoid){
                                Toast.makeText(Register.this, "User details saved", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Register.this, MainActivity.class));
                            }
                        }
                    };insUser.execute(new Users(username, fullname, password));
                }else{
                    Snackbar.make(view1, "Fill in all details", Snackbar.LENGTH_SHORT);
                }
            }
        });
    }

    public class insertUser extends AsyncTask<Users, Void, Boolean>{
        @SuppressLint("StaticFieldLeak")
        Context mCtx;

        insertUser(Context mCtx) {
            this.mCtx = mCtx;
        }

        @Override
        protected Boolean doInBackground(Users... users) {
            try{
                userDb.getUsersDao().InsertUsers(users);
                return true;
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }

        }
    }
}
