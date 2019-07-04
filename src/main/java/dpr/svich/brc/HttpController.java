package dpr.svich.brc;

import android.content.Context;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpController {

    private HttpClient httpClient;

    private HttpResponse response;

    private InputStream inputStream;

    private Context context;

    public HttpController(Context context){
        this.httpClient = new DefaultHttpClient();
        this.context = context;
    }

    public String get(String url){
        String result = "";
        try{
            response = httpClient.execute(new HttpGet(url));
            inputStream = response.getEntity().getContent();
            if(inputStream != null){
                result = convertInputStreamToString(inputStream);
            } else{
                result = "Wrong!";
            }
        } catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return result;
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
