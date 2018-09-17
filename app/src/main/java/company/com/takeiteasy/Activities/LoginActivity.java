package company.com.takeiteasy.Activities;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import company.com.takeiteasy.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    private EditText mEmailView;
    private EditText mPasswordView;
    String username;
    String password;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("mlog","test1");
        AccountManager am = AccountManager.get(this);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginAndPassword()){
                    SignIn signIn=new SignIn();
                    signIn.execute();
                    Log.d("mlog","Sign in");
                }
                else{
                    Log.d("mlog","null data");
                }
            }
        });
        Button mEmailSignUpButton = (Button) findViewById(R.id.email_sign_up_button);
        mEmailSignUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("mlog","Sign up");
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }




    // Store values at the time of the login attempt.
    private boolean loginAndPassword() {
        Log.d("mlog","test2");
        if(mEmailView.getText()!=null && mPasswordView.getText()!=null) {
            username = mEmailView.getText().toString();
            password = mPasswordView.getText().toString();
            return true;
        }
        else return false;
    }

    class SignIn extends AsyncTask<String,String,String>{
        String params,server;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            params="username="+username+"&password="+password;
            server="http://46.21.249.26:8000/users/auth";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("200")){
                finish();

                Toast toast = Toast.makeText(getApplicationContext(),"Sign in",Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(),"Fail "+s.toString(),Toast.LENGTH_LONG);
                toast.show();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Log.d("mLog", "TRY ... ");
                String Data = params; // строковая переменная с параметрами запроса
                URL url = new URL(server); // переменая с адресом сервера
                HttpURLConnection con = (HttpURLConnection) url.openConnection(); // создаем соединения
                con.setRequestMethod("POST"); // метод передачи запроса
                con.setConnectTimeout(150000); // время ожидания запроса
                con.getOutputStream().write(Data.getBytes("UTF-8")); // устанавливаем кодировку
                Log.d("mLog", "getOutputStream готовим ... "+server+" "+params+ " "+con.getResponseMessage()+" "+ con.getResponseCode());
                return Integer.toString(con.getResponseCode());
            } catch (Exception e) {
                Log.d("mLog", "Ошибка Соединения " + e);
                cancel(true);
                return "error";
            }


        }
    }






}

