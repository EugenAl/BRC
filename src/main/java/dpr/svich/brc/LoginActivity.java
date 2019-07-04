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

    private Thread signUpThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginET = findViewById(R.id.login_input);
        passET = findViewById(R.id.password_input);

        signUpThread = new Thread(new Runnable() {
            @Override
            public void run() {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    if (loginET.getText().toString().isEmpty() || passET.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(),
                                "Invalid data", Toast.LENGTH_SHORT).show();
                    } else {
                        String login = loginET.getText().toString();
                        String password = passET.getText().toString();
                        final String basicAuth = "Basic " + Base64.
                                encodeToString((login + ":" + password).getBytes(), Base64.NO_WRAP);
                        URL url = new URL("http://192.168.38.96:8080/");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestProperty("Authorization", basicAuth);
                        connection.connect();

                        int response = connection.getResponseCode();
                        //Toast.makeText(getApplicationContext(),
                        //        "Connect with code " + response, Toast.LENGTH_LONG).show();

//                        HttpController controller = new HttpController(this);
//                        Toast.makeText(this, controller
//                                .get("http://192.168.38.96:8080/clients/1/accounts"),
//                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

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

    private class AsyncSignUp extends AsyncTask<Void, Void, Boolean>{
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Create a nuclear bomb");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean succes;
            try{
                String login = loginET.getText().toString();
                String password = passET.getText().toString();
                final String basicAuth = "Basic " + Base64.
                        encodeToString((login + ":" + password).getBytes(), Base64.NO_WRAP);
                URL url = new URL("http://192.168.38.96:8080/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Authorization", basicAuth);
                connection.connect();
                succes = true;
            } catch (Exception e){
                e.printStackTrace();
                succes = false;
            }
            return succes;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();
            signUpProgress(aVoid);
        }
    }

    public void signUpProgress(boolean state){
        if(state){
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ohh, something went wrong",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
