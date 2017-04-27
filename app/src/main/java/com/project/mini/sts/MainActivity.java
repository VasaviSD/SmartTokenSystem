package com.project.mini.sts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btnLogin,btnRegister;
    EditText edUsername,edPassword;
    String varUsername,varPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edUsername=(EditText)findViewById(R.id.edUserName);
        edPassword=(EditText)findViewById(R.id.edPassword);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnRegister=(Button)findViewById(R.id.btnRegister);


        final Intent intent=getIntent();
        String varConfirmation = intent.getStringExtra( "confirmation" );
        Toast.makeText( this, varConfirmation, Toast.LENGTH_SHORT ).show();

        btnRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String varMsg="Welcome to Registration Page";
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                intent.putExtra( "message",varMsg );
                startActivity( intent );
            }
        } );


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varUsername=edUsername.getText().toString();
                varPassword=edPassword.getText().toString();
                //String varPath="http://192.168.1.4/SmartTokenSystem/custogin.php";
                AsyncLogin login=new AsyncLogin();
                login.execute(varUsername,varPassword);

            }
        });


        }

    class AsyncLogin extends AsyncTask<String,String,String>
    {
        Context context;
        ProgressDialog pd = new ProgressDialog( MainActivity.this);
        HttpURLConnection conn;
        URL url=null;

        protected void onPreExecute()
        {
            pd.setMessage( "\t Please Wait !! Loading..." );
            pd.setCancelable( false );
            pd.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try{
                url=new URL( "http://192.168.43.38:80/SmartTokenSystem/custlogin.php" );
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
                return "Exception";
            }
            try{
                conn=(HttpURLConnection)url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");

                conn.setDoInput( true );
                conn.setDoOutput( true );

                Uri.Builder builder;
                builder= new Uri.Builder().appendQueryParameter("userID",params[0]);
                builder.appendQueryParameter("password",params[1]);

                String query= builder.build().getEncodedQuery();
                //String query1=;

                OutputStream os=conn.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write( query );
                // writer.write( query1 );
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception : "+e.getMessage();
            }
            try {
                int response_code = conn.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader( new InputStreamReader( input ) );
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null)
                        result.append( line );

                    return (result.toString());

                } else {
                    return ("Unsuccessful");
                }
            }
            catch (IOException e){
                e.printStackTrace();
                return ("Exception : "+e.getMessage());
            }
            finally {
                conn.disconnect();
            }
        }

        protected  void onPostExecute(String result)
        {
            pd.dismiss();
            Toast.makeText( MainActivity.this,result, Toast.LENGTH_SHORT ).show();

            if(result.equalsIgnoreCase( "Login Successful" )) {
                Toast.makeText( MainActivity.this, "Login Successful", Toast.LENGTH_SHORT ).show();
             /*   Intent intent1=new Intent( MainActivity.this,MenuActivity.class );
                String varMsg="Welcome to Main Menu, Please Select Your Desired Service.";
                intent1.putExtra( "msg", varMsg);
                startActivity( intent1 );*/
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.this.startActivity(intent);
            }
            else if(result.equalsIgnoreCase( "Login Failed" ))
            {
                Toast.makeText( MainActivity.this, "Login Failed", Toast.LENGTH_SHORT ).show();
            }

        }



    }
}
