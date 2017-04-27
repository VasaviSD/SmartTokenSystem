package com.project.mini.sts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {

    EditText edName,edPhone,edPass,edConfPass;
    Button btnReg;
    String varName,varPass;
    String varPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        Intent intent= getIntent();
        String varMsg=intent.getStringExtra( "message" );
        Toast.makeText( this, varMsg, Toast.LENGTH_SHORT ).show();


        edName=(EditText)findViewById( R.id.edName );
        edPhone=(EditText)findViewById( R.id.edPhone );
        edPass=(EditText)findViewById( R.id.edPass );
        edConfPass=(EditText)findViewById( R.id.edConfPass );
        btnReg=(Button)findViewById( R.id.btnReg );
        varName=edName.getText().toString();
        varPhone= edPhone.getText().toString();

        if(edName.getText().toString().length() < 3)
        {
            edName.setError("Name should at least contain 3 letters");
        }
        if (edPhone.getText().toString().length()< 10)
        {
            edPhone.setError("Enter a valid mobile number");
        }
        if (edPass.getText().toString().length()<=1)
        {
            edPass.setError("Password should contain at least 3 letters");
        }
        if (edConfPass.getText().toString() == edPass.getText().toString())
        {
            edConfPass.setError("Password doesn't match");
        }

        btnReg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncReg reg=new AsyncReg();
                reg.execute(varName,varPhone,varPass);

            }
        } );

    }

    class AsyncReg extends AsyncTask<String,String,String>
    {

        String varConfirmation="Welcome to Login page. Please Login !!";
        ProgressDialog pd=new ProgressDialog(RegisterActivity.this);
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
                url=new URL( "http://192.168.43.38:80/SmartTokenSystem/register.php" );
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
                builder= new Uri.Builder().appendQueryParameter("user",params[0]);
                builder.appendQueryParameter("contact",params[1]);
                builder.appendQueryParameter("password",params[2]);

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
            Toast.makeText( RegisterActivity.this,result, Toast.LENGTH_SHORT ).show();

            if(result.equalsIgnoreCase("Successfully Registered!!"))
            {
                Toast.makeText( RegisterActivity.this, "Registration Successful !!", Toast.LENGTH_SHORT ).show();
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                intent.putExtra( "confirmation", varConfirmation);
                startActivity( intent );
            }
            else if(result.equalsIgnoreCase("Try again please"))
            {
                Toast.makeText( RegisterActivity.this, "Registration Failed. Try Again", Toast.LENGTH_SHORT ).show();
            }
        }

    }
}
