package iyh.queensbase.my.inyourhand;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class LoginMenu extends Activity {

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
   }

    public void editProfile(View view)
    {
        Intent i = new Intent(LoginMenu.this, EditProfile.class);
        startActivity(i);
    }

    public void viewCampaign(View view)
    {
        Intent i = new Intent(LoginMenu.this, CampaignDetails.class);
        startActivity(i);
    }

    public void newCampaign(View view)
    {
        Intent i = new Intent(LoginMenu.this, CreateCampaign.class);
        startActivity(i);
    }

    public void viewReport(View view)
    {
        Intent i = new Intent(LoginMenu.this, ViewReport.class);
        startActivity(i);
    }
}