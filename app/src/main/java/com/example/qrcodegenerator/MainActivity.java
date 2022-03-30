package com.example.qrcodegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private EditText nameEdt,generate_edit;
    private Button postDataBtn,generatebtn;
    private TextView responseTV,response_code,response_text,person_name;
    String strNumber,straccountno,strphoneno,strname,strResponse,strGenerate;
    private ProgressBar loadingPB;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initializing our views
        nameEdt = findViewById(R.id.idEdtName);

        postDataBtn = findViewById(R.id.idBtnPost);
        responseTV = findViewById(R.id.idTVResponse);
        loadingPB = findViewById(R.id.idLoadingPB);
        /*response_code = findViewById(R.id.responseCode);
        response_text = findViewById(R.id.responseText);
        person_name = findViewById(R.id.personName);*/
        generatebtn = findViewById(R.id.generate);

        generatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityQR.class);
                startActivity(intent);
            }
        });
        SystemClock.sleep(3000);
        Intent choose_password_or_image_encryption = new Intent(MainActivity.this,MainActivity2.class);
        startActivity(choose_password_or_image_encryption);
        finish();

        // adding on click listener to our button.
        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (nameEdt.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                postData();

            }
        });
    }

    public void postData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            strNumber =  nameEdt.getText().toString();
            Toast.makeText(this, "Number is:"+strNumber, Toast.LENGTH_SHORT).show();
            //  object.put("parameter","value");
            object.put("mobilno",strNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //  stsdl  =  object.tos
        HttpsTrustManager.allowAllSSL();
        // Enter the correct url for your api service site
        String url = "https://202.143.96.44:1831/api/Mob/UserInfo";//getResources().getString(R.string.url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        Log.d("TAG", "Details:" + response);
                        responseTV.setText("String Response : " + response.toString());

                        try {

                            // JSONObject jsonObject = response.getJSONObject(response.toString());
                            strname = response.getString("personName");
                            strphoneno = response.getString("mobileno");
                            straccountno = response.getString("accountno");

                            strResponse = "upi://pay?pn="+strname.replaceAll("\\s+", "_")+"@ybl&pn="+strphoneno+"&pac="+straccountno;
                            Log.i("TAGParser","parseData:"+strResponse);
                            Intent intent = new Intent(getApplicationContext(),QRGenerator.class);
                            intent.putExtra("value",strResponse);
                            startActivity(intent);
                            /*response_code.setText("" +strcode);
                            response_text.setText("" +strtext);*/
                            person_name.setText("" +strname);

                        } catch (JSONException e) {
                            Log.d("TAG", "profile: " + e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseTV.setText(error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}