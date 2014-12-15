package iyh.queensbase.my.inyourhand;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    public void login(View view)
    {
        Intent i = new Intent(MyActivity.this, LoginMenu.class);
        startActivity(i);
    }

    public void signup(View view)
    {
        Intent intent = new Intent(MyActivity.this, SignUp.class);
        startActivity(intent);
    }


}
