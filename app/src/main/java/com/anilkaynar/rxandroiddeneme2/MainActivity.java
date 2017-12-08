package com.anilkaynar.rxandroiddeneme2;

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
import java.util.concurrent.Callable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
io.reactivex.Observable<String> observable;
WebView webView;
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.textView2);
        observable=io.reactivex.Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                URL url=new URL("http://ce.istanbul.edu.tr");
                HttpURLConnection con=(HttpURLConnection) url.openConnection();
                con.connect();
                InputStream stream=con.getInputStream();
                InputStreamReader reader = new InputStreamReader(stream, Charset.forName("UTF-8"));
                //Use OKHttp
                BufferedReader read=new BufferedReader(reader);
                String temp=read.readLine();
                Log.e("Observe edildi","Observableın içi");
                String asil="";
                while(temp!=null)
                {
                    asil+=temp;
                    temp=read.readLine();
                }
                return asil;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onStart() {
        super.onStart();
        observable.subscribe(new io.reactivex.Observer<String>(){

            @Override
            public void onSubscribe(Disposable d) {
              Log.e("onSub","ONsubscribe çalıştı");
            }

            @Override
            public void onNext(String s) {

                  textView.setText(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
