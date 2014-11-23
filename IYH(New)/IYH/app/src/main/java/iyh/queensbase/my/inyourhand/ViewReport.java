package iyh.queensbase.my.inyourhand;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;


public class ViewReport extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec1, spec2, spec3;

        spec1 = tabHost.newTabSpec("spec1");
        spec1.setContent(R.id.Daily);
        spec1.setIndicator("Daily");
        tabHost.addTab(spec1);

        spec2 = tabHost.newTabSpec("spec2");
        spec2.setContent(R.id.Weekly);
        spec2.setIndicator("Weekly");
        tabHost.addTab(spec2);

        spec3 = tabHost.newTabSpec("spec3");
        spec3.setContent(R.id.Monthly);
        spec3.setIndicator("Monthly");
        tabHost.addTab(spec3);
    }
}
