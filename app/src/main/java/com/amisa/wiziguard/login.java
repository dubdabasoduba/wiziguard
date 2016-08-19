package com.amisa.wiziguard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amisa.wiziguard.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class login extends AppCompatActivity {
	String EmailValue;
	String PasswordValue;
	String data;
	private EditText editText, editPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		editText = (EditText) findViewById(R.id.etEmail);
		editPass = (EditText) findViewById(R.id.etPassword);

		ProgressDialog pDialog = new ProgressDialog(this);
		pDialog.setCancelable(false);

		Button btn = (Button) findViewById(R.id.btnLogin);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

/*
                Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
*/
				EmailValue = editText.getEditableText().toString().trim();
				PasswordValue = editPass.getEditableText().toString().trim();

				if (PasswordValue.equals("") && EmailValue == "") {
					Toast.makeText(login.this, "one of the fields are missing", Toast.LENGTH_SHORT).show();


				} else {

					toJSON();
					sendDataToServer();
					getServerResponse(data);
//        Toast.makeText(getApplicationContext(), Passvalue, Toast.LENGTH_LONG).show();
//        JSONObject jsonObject= new JSONObject(Passvalue);
				}
			}
		});
	}

	private String toJSON() {

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("email", EmailValue);
			jsonObject.put("password", PasswordValue);

			jsonArray.put(jsonObject);

			Toast.makeText(login.this, data, Toast.LENGTH_SHORT).show();
			data = jsonArray.toString();
		} catch (JSONException e) {

			e.printStackTrace();
			Log.d("JSP", "cant format json");
		}

// System.out.println(data);
		return "success";
	}

	private void sendDataToServer() {

		final String data = toJSON();
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {

				return getServerResponse(data);
			}
		}.execute();
	}

	private String getServerResponse(String data) {
		String response = "hello in try login";
		HttpsURLConnection urlConnection = null;

		try {
			URL url = new URL("https://10.0.2.2/wiziguard/api_login.php");
			urlConnection = (HttpsURLConnection) url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.connect();
			urlConnection.setRequestMethod("GET");

			InputStreamReader wr = new InputStreamReader(urlConnection.getInputStream());
			wr.read();

			urlConnection.getResponseMessage();
			int responsecode = urlConnection.getResponseCode();

			if (responsecode == HttpURLConnection.HTTP_OK) {
				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

				while ((line = br.readLine()) != null) {
					response += line;
				}

			} else {
				response = "";
			}


		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			Toast.makeText(login.this, "finally login", Toast.LENGTH_SHORT).show();
			if (urlConnection != null) urlConnection.disconnect();
		}

		return "success";
	}
}

