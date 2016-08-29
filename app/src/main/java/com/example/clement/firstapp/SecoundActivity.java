package com.example.clement.firstapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SecoundActivity extends AppCompatActivity {



    public String[] Countries = new String[]{
            "india", "Brazil", "USA", "Canada", "UK", "Sri Lenka", "Pakistan", "china","Russia"


    };
    private TextView tvSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secound);


        tvSec = (TextView)findViewById(R.id.TextOfSecndActivity);

        Button Download =(Button)findViewById(R.id.download);
        Button Next =(Button)findViewById(R.id.nextBtn);


        Bundle bundle= getIntent().getExtras();

        String string = bundle.getString("dataFromFirstActivity");
        tvSec.setText( "Welcome "+string +" we are trying to fetch the Json Data From a web site using HttpUrlRequest and Display the username from that :");

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),Basic.class);



                startActivity(intent);
            }
        });


        Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Json().execute("http://www.filltext.com/?rows=10&id={index}&email={email}&username={username}&password={randomString|5}&pretty=true");
            }
        });



    }

    public class Json extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;

            BufferedReader reader;

            try {
                URL url= new URL(params[0]);

                connection =(HttpURLConnection)url.openConnection();
                connection.connect();

                InputStream stream= connection.getInputStream();


                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer stringBuffer=new StringBuffer();
                String line="";

                while((line = reader.readLine())!=null) {

                    stringBuffer.append(line);
                }


                String jsonBuffer = stringBuffer.toString();
                StringBuilder stringBuilder= new StringBuilder();
                JSONArray jsonArray = new JSONArray(jsonBuffer);
                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    //int id= jsonObject.getInt("id");
                    String email= jsonObject.getString("email");
                    String username= jsonObject.getString("username");
                    // String password= jsonObject.getString("password");


                    stringBuilder.append("Name: "+username+" Email: "+email+"\n");

                }

                return stringBuilder.toString();



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(connection !=null)
            {
                connection.disconnect();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {


            super.onPostExecute(result);
            tvSec.setText(result);
        }
    }








}
