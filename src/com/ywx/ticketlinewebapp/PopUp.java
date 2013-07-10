package com.ywx.ticketlinewebapp;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PopUp extends Activity {
    Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          System.gc();
          
          button = (Button) findViewById(R.id.btn_ok);
          button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                      finish();
                }
          });
    }

}
