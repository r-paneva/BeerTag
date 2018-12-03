package com.beertag.android.views.beerCreate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
//import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class BeerSpinner { //extends ActionBarActivity {
//
//    Spinner spinnercountry;
//    String ip, db, un, passwords;
//    Connection connect;
//    PreparedStatement stmt;
//    ResultSet rs;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.spinners);
//
//        ip = "192.168.0.100";
//        un = "sa";
//        passwords = "123";
//        db = "MyDatabase";
//        spinnercountry = (Spinner) findViewById(R.id.spinnercountry);
//
//        connect = CONN(un, passwords, db, ip);
//        String query = "select CountryName from countries";
//
//        try {
//            connect = CONN(un, passwords, db, ip);
//            stmt = connect.prepareStatement(query);
//            rs = stmt.executeQuery();
//            ArrayList<String> data = new ArrayList<String>();
//            while (rs.next()) {
//                String id = rs.getString("CountryName");
//                data.add(id);
//
//            }
//            String[] array = data.toArray(new String[0]);
//            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
//                    android.R.layout.simple_list_item_1, data);
//            spinnercountry.setAdapter(NoCoreAdapter);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        spinnercountry.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//
//                String name = spinnercountry.getSelectedItem().toString();
//                Toast.makeText(ForSpinner.this, name, Toast.LENGTH_SHORT)
//                        .show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//    }
//
//    @SuppressLint("NewApi")
//    private Connection CONN(String _user, String _pass, String _DB,
//                            String _server) {
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//                .permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        Connection conn = null;
//        String ConnURL = null;
//        try {
//
//            Class.forName("net.sourceforge.jtds.jdbc.Driver");
//            ConnURL = "jdbc:jtds:sqlserver://" + _server + ";"
//                    + "databaseName=" + _DB + ";user=" + _user + ";password="
//                    + _pass + ";";
//            conn = DriverManager.getConnection(ConnURL);
//        } catch (SQLException se) {
//            Log.e("ERRO", se.getMessage());
//        } catch (ClassNotFoundException e) {
//            Log.e("ERRO", e.getMessage());
//        } catch (Exception e) {
//            Log.e("ERRO", e.getMessage());
//        }
//        return conn;
//    }

}

