package company.com.takeiteasy.Activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

import company.com.takeiteasy.R;

public class RegisterActivity extends AppCompatActivity {
     EditText usernameET;
     EditText emailET;
     EditText fnameET;
     EditText lnameET;
     EditText passwordET;
     String username,email,fname,lname,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usernameET=(EditText)findViewById(R.id.username_edittext);
        emailET=(EditText)findViewById(R.id.email_edittext);
        fnameET=(EditText)findViewById(R.id.firstname_edittext);
        lnameET=(EditText)findViewById(R.id.lastname_edittext);
        passwordET=(EditText)findViewById(R.id.password_edittext);

        Button regButton= (Button)findViewById(R.id.reg_button);
        regButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edittextData()) {
                    SignUp signUp = new SignUp();
                    signUp.execute();
                    Log.d("mlog","Sign in");
                }
                else{
                    Log.d("logm","null data");
                }
            }
        });
    }
    protected boolean edittextData(){
       if(usernameET.getText()!=null&&emailET.getText()!=null&&fnameET.getText()!=null&&lnameET.getText()!=null&&passwordET.getText()!=null) {
            username = usernameET.getText().toString();
            email = emailET.getText().toString();
            fname = fnameET.getText().toString();
            lname = lnameET.getText().toString();
            password = passwordET.getText().toString();
            return true;
        }
        else{
           return  false;
       }
    }
    class SignUp extends AsyncTask<String,String,String> {
        String params,server;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            params="username="+username+"&email="+email+"&first_name="+fname+"&last_name="+lname+"&password="+password;
            server="http://46.21.249.26:8000/users/reg";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("201")){
                finish();

                Toast toast = Toast.makeText(getApplicationContext(),"Sign up",Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(),"Try again "+s.toString(),Toast.LENGTH_LONG);
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
                con.setConnectTimeout(1500); // время ожидания запроса
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
