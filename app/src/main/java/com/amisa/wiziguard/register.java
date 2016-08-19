package com.amisa.wiziguard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class register extends AppCompatActivity {
	EditText FName;
	EditText LName;
	EditText Tel;
	EditText Password;
	EditText ConfirmPassword;
	Button Register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		Button cancel = (Button) findViewById(R.id.btnCancel);
		FName = (EditText) findViewById(R.id.etFName);
		LName = (EditText) findViewById(R.id.etLName);
		Tel = (EditText) findViewById(R.id.etTel);
		Password = (EditText) findViewById(R.id.etPassword);
		ConfirmPassword = (EditText) findViewById(R.id.etConfirm);
		Register = (Button) findViewById(R.id.btnRegister);


		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		Register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

			}
		});
	}
}
