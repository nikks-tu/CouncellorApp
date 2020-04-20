package com.techuva.new_changes_corporate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.techuva.councellorapp.BuildConfig;
import com.techuva.councellorapp.R;
import com.techuva.councellorapp.contus_Corporate.activity.RegisterActivity;
import com.techuva.councellorapp.contus_Corporate.activity.SplashScreenActivity;
import com.techuva.councellorapp.contus_Corporate.activity.Welcome;
import com.techuva.councellorapp.contus_Corporate.app.Constants;
import com.techuva.councellorapp.contus_Corporate.residemenu.MenuActivity;
import com.techuva.councellorapp.contusfly_corporate.MApplication;
import com.techuva.councellorapp.contusfly_corporate.api_interface.AppVersionDataInterface;
import com.techuva.councellorapp.contusfly_corporate.model.AppVersionPostParameters;
import com.techuva.new_changes_corporate.post_parameters.SignupPostParameters;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class SignUpActivity extends AppCompatActivity {
    TextView tv_heading, tv_details, tv_already_register, tv_login_here, tv_letter_count;
    EditText edt_first_name, edt_last_name, edt_mobile, edt_email, edt_reason_to_join;
    Button btn_Register;
    String first_name, last_name, mobileNo, emailId, reason, name;
    Context context;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Init();
        btn_Register.setOnClickListener(v -> {
            getInput();
            if(!first_name.equals(""))
            {
                if(!emailId.equals(""))
                {
                    if(emailId.matches(emailPattern) && emailId.length() > 0)
                    {
                        if(mobileNo.length()>9 && validCellPhone(mobileNo))
                        {
                            name = first_name+" "+last_name;
                            MApplication.materialdesignDialogStart(this);
                            serviceCall();
                        }
                        else {
                            Toast.makeText(context, "Please enter valid mobile number!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(context, "Please enter valid email-id!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context, "Please enter email-id!", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(context, "Please enter your name!", Toast.LENGTH_SHORT).show();
            }
        });


        edt_reason_to_join.addTextChangedListener(mTextEditorWatcher);



        tv_login_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }


    public boolean validCellPhone(String number)
    {
        return android.util.Patterns.PHONE.matcher(number).matches();
    }


    private void getInput() {
        first_name = edt_first_name.getText().toString();
        last_name = edt_last_name.getText().toString();
        mobileNo = edt_mobile.getText().toString();
        emailId = edt_email.getText().toString();
        reason = edt_reason_to_join.getText().toString();
    }

    private void Init() {
        context = SignUpActivity.this;
        tv_heading = findViewById(R.id.tv_heading);
        tv_details = findViewById(R.id.tv_details);
        tv_already_register = findViewById(R.id.tv_already_register);
        tv_login_here = findViewById(R.id.tv_login_here);
        tv_letter_count = findViewById(R.id.tv_letter_count);
        edt_first_name = findViewById(R.id.edt_first_name);
        edt_last_name = findViewById(R.id.edt_last_name);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_email = findViewById(R.id.edt_email);
        edt_reason_to_join = findViewById(R.id.edt_reason_to_join);
        btn_Register = findViewById(R.id.btn_Register);
    }



    public void serviceCall(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AppVersionDataInterface service = retrofit.create(AppVersionDataInterface.class);
        Call<JsonElement> call = service.newUserSignup(new SignupPostParameters(name, emailId, mobileNo, reason));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
               if(response.body()!=null)
                {
                    MApplication.materialdesignDialogStop();
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    String errorCode = jsonObject.get("success").getAsString();
                    if(errorCode.equals("0"))
                    {
                        String msg = jsonObject.get("msg").getAsString();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, RegisterActivity.class);
                        startActivity(intent);
                    }
                    else {
                        String msg = jsonObject.get("msg").getAsString();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                    }
                else
                    {
                        MApplication.materialdesignDialogStop();
                    }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                MApplication.materialdesignDialogStop();
                //   Toast.makeText(context, "Error" +t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            tv_letter_count.setText(s.length()+"/50");
        }

        public void afterTextChanged(Editable s) {
        }
    };

}
