package dpr.svich.brc;

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
        switch (v.getId()){
            case R.id.sign_up_button:
                Toast.makeText(getApplicationContext(),
                        "Login request", Toast.LENGTH_SHORT).show();
                try {
                    if(loginET.getText().toString().isEmpty() || passET.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(),
                                "Invalid data", Toast.LENGTH_SHORT).show();
                    } else{
                        String login = loginET.getText().toString();
                        String password = passET.getText().toString();
                        final String basicAuth = "Basic "+ Base64.
                                encodeToString((login+":"+password).getBytes(), Base64.NO_WRAP);
                        URL url = new URL("http://192.168.38.96:8080/login");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestProperty("Authorization", basicAuth);
                    }
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
        }
    }
}
