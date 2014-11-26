package iyh.queensbase.my.inyourhand;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void clear(View v) {
        EditText username=(EditText) findViewById(R.id.username);
        EditText fullName=(EditText) findViewById(R.id.fullName);
        EditText Password=(EditText) findViewById(R.id.Password);
        EditText email=(EditText) findViewById(R.id.email);
        EditText businessName=(EditText) findViewById(R.id.businessName);

        username.setText("");
        fullName.setText("");
        Password.setText("");
        email.setText("");
        businessName.setText("");
    }
}
