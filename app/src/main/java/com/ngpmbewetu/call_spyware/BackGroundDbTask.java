package com.ngpmbewetu.call_spyware;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackGroundDbTask extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context context;

    BackGroundDbTask(Context context) {
        this.context = context;

    }

    @Override
    protected void onPreExecute() {
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login information......");

        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... voids) {
//        pass info to mysql database
        String Register_Url="http://192.168.43.193/register.php";
        String Login_Url="http://192.168.43.193/login.php";

        String method=voids[0];
//        Toast.makeText(context, ""+method, Toast.LENGTH_SHORT).show();
        if (method.equals("Register")){
//            perform registration operation
            String username=voids[1];
            String password=voids[2];
            String amount=voids[3];

            try {
                URL url=new URL(Register_Url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
//                Encode data before dending it
                String data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+
                        URLEncoder.encode("user_password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                        URLEncoder.encode("user_amount","UTF-8")+"="+URLEncoder.encode(amount,"UTF-8");
//                write this data to buffer writer
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream= httpURLConnection.getInputStream();
                inputStream.close();
                return "Registration sucessfull.....";




            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.i("error","malformed url");

            } catch (IOException e) {

                e.printStackTrace();
                Log.i("error","IO exception");
            }

        }else if (method.equals("login")){
            String login_name=voids[1];
            String login_password=voids[2];
            try {
                URL loginUrl =new URL(Login_Url);
                HttpURLConnection httpURLConnection= (HttpURLConnection) loginUrl.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream= httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("login_name","UTF-8")+"="+
                        URLEncoder.encode(login_name,"UTF-8")+"&"+URLEncoder.encode("login_pass","UTF-8")+"="+
                        URLEncoder.encode(login_password,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                // get response from server via the input stream
                InputStream inputStream= httpURLConnection.getInputStream();
                BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream,"Iso-8859-1"));
                String response ="";
                String line="";
                while ((line=bufferedReader.readLine())!=null){
                    response+=line;


                }bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;







            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        if (result==null){
            Toast.makeText(context, "Registration unsuccessful...", Toast.LENGTH_SHORT).show();
        }

        else if (result.equals("Registration successful.....")){
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        }else {
            alertDialog.setMessage(result);
            alertDialog.show();

        }

    }
}
