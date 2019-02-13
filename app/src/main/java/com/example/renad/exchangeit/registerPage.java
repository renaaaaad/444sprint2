package com.example.renad.exchangeit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class registerPage extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;  // to add new users
    private DatabaseReference user_data; // to store the data in the database
    private ProgressDialog progressDialog;
    private Button register ;
    private EditText firstName;
    private EditText lastName;
    private EditText Email;
    private EditText password;
    private EditText rePassword;
    private EditText phone;
    private TextView signIn;
    private EditText city2;
    Spinner category;
    private User user;
    public  boolean newRegister =false;
    public boolean varifiction = false;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        // to check if the user is already enterd



        mAuth = FirebaseAuth.getInstance();
        register = (Button)findViewById(R.id.add);
        register.setOnClickListener(this);
        user_data = FirebaseDatabase.getInstance().getReference("Users");
        progressDialog = new ProgressDialog(this);




    }//on create
    //-----------------------------------------------------------------------------------------------
    private void registerUser(){

        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        Email = (EditText)findViewById(R.id.Email);
        password = (EditText)findViewById(R.id.password);
        phone = (EditText)findViewById(R.id.phoneNumber);
        signIn = (TextView) findViewById(R.id.signIn);
        category = (Spinner)findViewById(R.id.cityspinner);
           rePassword = (EditText)findViewById(R.id.Confermpassword);
        String Fname =null;
        String Lname = null;
        String email =null;
        String Passowrd =null;
        String REpassword=null ;
        String Phone =null;
        final String city = category.getSelectedItem().toString();



        if(!firstName.getText().toString().equals("")){
            Fname = firstName.getText().toString();
        }
        else {
            firstName.setError("Please Enter Your First Name");
            firstName.requestFocus();
            return;
        }

        if(!lastName.getText().toString().equals("")){
            Lname=lastName.getText().toString();
        }
        else {
            lastName.setError("Please Enter Your Last  Name");
            lastName.requestFocus();
            return;

        }

        if(!Email.getText().toString().equals("")){
            email= Email.getText().toString();
        }
        else {
            Email.setError("Please Enter Your E-mail  Address ");
            Email.requestFocus();
            return;
        }

        if(!phone.getText().toString().equals("")){
            Phone= phone.getText().toString();



        }
        else {
            phone.setError("Please Enter Your Phone Number  ");
            phone.requestFocus();
            return;

        }

        if( !password.getText().toString().equals("")){
            Passowrd= password.getText().toString();
        }
        else {
            password.setError("Please Enter Your Password ");
            password.requestFocus();
            return;

        }



        if( password.getText().toString().length()<6){
            password.setError("Please Your Password Need to Contain 6 Charecters or More ");
            password.requestFocus();
            return;
        }



        if( !rePassword.getText().toString().equals("")){
            REpassword= password.getText().toString();

            if(rePassword.getText().toString().equals(password.getText().toString())){}
            else {
                rePassword.setError("Reenter again ");
                rePassword.requestFocus();
                return;
            }

        }
        else {
            rePassword.setError("Please Confirm Your Password ");
            rePassword.requestFocus();
            return;

        }



        if(Phone.length()<10 || Phone.length()>10){
            phone.setError("Please Enter Your Phone Number Correctly ");
            phone.requestFocus();
            return;
        }



        progressDialog.setMessage("Please Wait ....");
        progressDialog.show();
// create new user
        mAuth.createUserWithEmailAndPassword(email,Passowrd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()) {
// to send farivication

                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                newRegister = true;
                            }
                        }
                    });



// grnrate
                    String uid = mAuth.getCurrentUser().getUid();
                    String id = user_data.push().getKey();
                    user=new User(id,firstName.getText().toString(),lastName.getText().toString(),phone.getText().toString(),Email.getText().toString(),city,"https://firebasestorage.googleapis.com/v0/b/exchangeit-3940a.appspot.com/o/images%2FUsers%2F3.jpg?alt=media&token=e4ae2c99-ecfc-4b72-bf9c-9f2e5b97f0fa");
                    user_data.child(uid).setValue(user);

// to move the user to the log in oage


                    startActivity(new Intent(getApplicationContext(),loginPage.class));


                }
                else {
                    // dosent work
                    newRegister=false;
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Log.e("registerPage", "Failed Registration", e);

                }
            }//oncompleate

        });
        if(varifiction){
            Toast.makeText(this, " Register Successful , Please verify your Email ",Toast.LENGTH_LONG).show();
        }
    }//registerUser


    //-----------------------------------------------------------------------------------------------

    @Override
    public void onClick(View v) {
        if(v==register){
            registerUser();
        }
        if(v==signIn){
            Intent loginPage = new Intent(registerPage.this,loginPage.class);
            startActivity(loginPage);
        }

    }//onclick
//-----------------------------------------------------------------------------------------------





}// class
