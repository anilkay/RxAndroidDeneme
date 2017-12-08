package com.anilkaynar.rxandroiddeneme2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {
io.reactivex.Observable<String> observable;
WebView webView;
TextView textView;
class AsenkTask extends AsyncTask<Void,Void,String>{

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL("http://ce.istanbul.edu.tr");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();
            InputStream stream = con.getInputStream();
            InputStreamReader reader = new InputStreamReader(stream, Charset.forName("UTF-8"));
            //Use OKHttp
            BufferedReader read = new BufferedReader(reader);
            String temp = read.readLine();
            Log.e("Observe edildi", "Observableın içi");
            String asil = "";
            while (temp != null) {
                asil += temp;
                temp = read.readLine();
            }
            return asil;
        }
        catch (Exception e){
            Log.e("Network Exception",e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        textView.setText(s);
    }
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView2);
        new AsenkTask().execute();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}
