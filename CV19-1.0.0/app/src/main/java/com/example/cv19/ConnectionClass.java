package com.example.cv19;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    String classs = "com.mysql.jdbc.Driver";

    String url = "jdbc:mysql://192.168.1.2:3306/cv19db";
    String user = "antov";
    String pass = "antov";

    @SuppressLint("NewApi")
    public Connection CONN(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;

        try{
            Class.forName(classs);

            conn = DriverManager.getConnection(url,user,pass);
        }catch (SQLException se){
            Log.e("ERR1: ", se.getMessage());
        }catch(ClassNotFoundException e){
            Log.e("ERR2: ", e.getMessage());
        }catch(Exception e){
            Log.e("ERR3: ", e.getMessage());
        }

        return conn;
    }
}
