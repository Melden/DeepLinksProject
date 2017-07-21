package com.example.cm.deeplinksproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        //    String action = intent.getAction();
        Uri data = intent.getData();
        if (data != null) openDeepLink(data);

        Button activity1B = (Button) findViewById(R.id.activity1);
        activity1B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity1.class);
                startActivity(intent);
            }
        });

        Button activity2B = (Button) findViewById(R.id.activity2);
        activity2B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                startActivity(intent);
            }
        });

        Button activity3B = (Button) findViewById(R.id.activity3);
        activity3B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity3.class);
                startActivity(intent);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.badge);
        item.setActionView( R.layout.action_badge);
        RelativeLayout notifCount = (RelativeLayout) item.getActionView();


        TextView tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);

        //tv.setText("0");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        InboxFragment.newInstance().test();
        int count= prefs.getInt("messages",0);
        String countStr= ""+count;
        tv.setText(countStr);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InboxActivity.class);
                startActivity(intent);
            }
        });
        notifCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InboxActivity.class);
                startActivity(intent);
            }
        });


        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
      int id= item.getItemId();
        if(id==R.id.badge){
            Intent intent = new Intent(MainActivity.this,InboxActivity.class);
            startActivity(intent);
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    };



    private void openDeepLink(Uri deepLink) {
        String path = deepLink.getPath();
        if (path.equals("/activity1")) {
            startActivity(new Intent(this, Activity1.class));
        } else if (path.equals("/activity2")) {
            startActivity(new Intent(this, Activity2.class));
        } else if (path.equals("/activity3")) {
            startActivity(new Intent(this, Activity3.class));
        } else if (path.equals("/activity4")) {
            startActivity(new Intent(this, Activity4.class));
        }


    }
}



