package com.example.kumarharshil.api;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class  MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    String str;
    TextView tv1;
    EditText txt;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        txt = findViewById(R.id.etName);
        tv1 = findViewById(R.id.tv);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkTask networkTask = new NetworkTask();
                str = txt.getText().toString();
                String url = "https://api.github.com/search/users?q="+str;
                tv1.setText(url);
                networkTask.execute(url);
            }
        });
    }



    class NetworkTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = strings[0];

            try {
                URL url = new URL(stringUrl);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                Scanner scanner = new Scanner(inputStream);

                scanner.useDelimiter("\\A");

                if (scanner.hasNext()) {
                    String s = scanner.next();
                    return s;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Failed to load";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<GithubUser> users = parseJson(s);
            GithubUserAdapter githubUserAdapter = new GithubUserAdapter(users);
            RecyclerView recyclerView = findViewById(R.id.rvUsers);
            recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            recyclerView.setAdapter(githubUserAdapter);
        }
    }

    ArrayList<GithubUser> parseJson(String s) {
        ArrayList<GithubUser> githubUsers = new ArrayList<>();

        //Parse the json
        try {
            JSONObject root = new JSONObject(s);
            JSONArray items = root.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject object = items.getJSONObject(i);
                String login = object.getString("login");
                Integer id = object.getInt("id");
                //String avatar = object.getString("avatar_url");
                Double score = object.getDouble("score");
                String html = object.getString("html_url");
                GithubUser githubUser = new GithubUser(login, id, html, score);
                githubUsers.add(githubUser);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return githubUsers;
    }

}