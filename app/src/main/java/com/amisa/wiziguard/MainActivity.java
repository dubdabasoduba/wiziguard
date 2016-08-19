package com.amisa.wiziguard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toast.makeText(MainActivity.this, "Welcome to WiziGuard", Toast.LENGTH_SHORT).show();

		Button login = (Button) findViewById(R.id.login);
		Button register = (Button) findViewById(R.id.signup);
		Button getLocation = (Button) findViewById(R.id.getLocation);
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(), login.class);
				startActivityForResult(intent, 0);
			}
		});
		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(), register.class);
				startActivityForResult(intent, 0);
			}
		});
		getLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(), location.class);
				startActivity(intent);
			}
		});
	}
}
