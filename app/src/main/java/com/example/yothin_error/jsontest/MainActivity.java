package com.example.yothin_error.jsontest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    ListView listView1;
    private UserAdapter mAdapter;
    private ArrayList<UserList> data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = new ArrayList<UserList>();
        new AsyncFetch().execute();
        listView1 = (ListView) findViewById(R.id.listView1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String autid = String.valueOf(((UserList)mAdapter.getItem(position)).getAuthorities_id());

                Toast.makeText(MainActivity.this,"ID = " +autid,Toast.LENGTH_LONG).show();
            }
        });
        Log.d("mAdapter " ,data+"");
    }
    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL("http://10.0.3.2/test1/updateAut.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            }
            try {


                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }
            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pdLoading.dismiss();

            pdLoading.dismiss();
            try {

                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    UserList userList = new UserList();
                    userList.setAuthorities_id(json_data.getInt("authorities_id"));
                    userList.setAuthorities_code(json_data.getString("authorities_code"));
                    userList.setAuthorities_pass(json_data.getString("authorities_pass"));
                    userList.setAuthorities_name(json_data.getString("authorities_name"));
                    userList.setAuthorities_lastname(json_data.getString("authorities_lastname"));
                    userList.setAuthorities_address(json_data.getString("authorities_address"));
                    userList.setAuthorities_telephone(json_data.getString("authorities_telephone"));
                    userList.setAuthorities_datein(json_data.getString("authorities_datein"));
                    userList.setAuthorities_status(json_data.getString("authorities_status"));
                    data.add(userList);

                }

                mAdapter = new UserAdapter(MainActivity.this, data);
                listView1.setAdapter(mAdapter);


            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }
    }

}
