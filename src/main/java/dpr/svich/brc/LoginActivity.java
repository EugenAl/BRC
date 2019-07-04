package dpr.svich.brc;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Message;
import android.os.StrictMode;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText loginET;
    private TextInputEditText passET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginET = findViewById(R.id.login_input);
        passET = findViewById(R.id.password_input);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.sign_up_button){
            Toast.makeText(getApplicationContext(),
                    "Login request", Toast.LENGTH_SHORT).show();
            if (loginET.getText().toString().isEmpty() || passET.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(),
                        "Invalid data", Toast.LENGTH_SHORT).show();
            } else {
                new AsyncSignUp().execute();
            }
        }
    }

    private class AsyncSignUp extends AsyncTask<Void, Void, Integer>{
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Create a nuclear bomb");
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            Integer responseCode = 199;
            try{
                String login = loginET.getText().toString();
                String password = passET.getText().toString();
                final String basicAuth = "Basic " + Base64.
                        encodeToString((login + ":" + password).getBytes(), Base64.NO_WRAP);
                URL url = new URL("http://192.168.38.96:8080/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Authorization", basicAuth);
                connection.connect();
                responseCode = connection.getResponseCode();
            } catch (Exception e){
                e.printStackTrace();
            }
            return responseCode;
        }

        @Override
        protected void onPostExecute(Integer aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();
            signUpProgress(aVoid);
        }
    }

    public void signUpProgress(Integer state){
        switch (state){
            case 200:
                Toast.makeText(this, "Success, code 200", Toast.LENGTH_LONG).show();
                break;
            case 401:
                Toast.makeText(this, "Unauthorized", Toast.LENGTH_LONG).show();
                break;
                default:
                    Toast.makeText(this, "Connect with code "+state,
                            Toast.LENGTH_LONG).show();
        }
    }
}
