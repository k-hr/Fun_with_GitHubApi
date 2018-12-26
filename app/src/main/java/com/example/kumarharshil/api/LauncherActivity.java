package com.example.kumarharshil.api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("uid", "b416027");
//            jsonObject.put("pwd","I123");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, "http://5b8764e035589600143c13d5.mockapi.io/courses", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
//                            char[] levels = {0};
//                            for(int i =0; i<response.length();i++) {
//                                System.out.println("1" + response.getJSONObject(i).getString("Level"));
//                                System.out.println("2" + response.getJSONObject(i).getString("Level").toCharArray());
//                                levels = response.getJSONObject(i).getString("Level").toCharArray();
//                            }
//                            for(int j=0; j<levels.length; j++){
//                                System.out.println("3.");
//                                System.out.print(levels[j] + " ");
//
//                            }
                            ArrayList<String> url = new ArrayList<>();
                            for(int i=0;i<response.length();i++){
                                int id = response.getJSONObject(i).getInt("id");
                                String html = response.getJSONObject(i).getString("Learn");
                                System.out.println(id +" "+ html);
                                url.add(html);
                            }
                            System.out.println(url);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        Mysingleton.getInstance(this).addToRequestqueue(jsonObjectRequest);
    }
}
