/* Handles the webview which establishes contact with Ticketlines online ordering process */
package com.ywx.ticketlinewebapp;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.graphics.Bitmap;
import android.view.KeyEvent;

public class Prices extends Activity{
	
	WebView TicketsWebView;
	String EventId; // Also known as SessionId
	 ProgressBar progressBar;
	 final Activity MyActivity = this;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prices_web);
        
        EventId = /*"13256069";*/ getIntent().getStringExtra("event-id"); //Uncomment  for final build
        
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
                
        
        // Assign ID to variable and load Ticketline Web App
        TicketsWebView = (WebView)findViewById(R.id.webView1);
        TicketsWebView.getSettings().setJavaScriptEnabled(true);
        TicketsWebView.loadUrl("https://app.ticketline.co.uk/order/tickets/" + EventId); 
        
        // Accept SSL Certificate
        TicketsWebView.setWebViewClient(new WebViewClient() {
 
            @Override
            public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
            
            
            @Override
        	public void onPageStarted(WebView view, String url, Bitmap favicon) {
        		// TODO Auto-generated method stub
        		super.onPageStarted(view, url, favicon);
        	}
     
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
     
                view.loadUrl(url);
                return true;
     
            }
     
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
     
                progressBar.setVisibility(View.GONE);
            }
            
            
            @SuppressWarnings("unused")
			public void onProgressChanged(WebView view, int progress)   
            {
               //Make the bar disappear after URL is loaded, and changes string to Loading...
               MyActivity.setTitle("Loading...");
               MyActivity.setProgress(progress * 100); //Make the bar disappear after URL is loaded

               // Return the app name after finish loading
                //if(progress == 100)
                //   MyActivity.setTitle(R.string.app_name);
            }
                       
            
            
        });
        
        
        
	}
	
	/*
	
    // To handle "Back" key press event for WebView to go back to previous screen.
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK) && TicketsWebView.canGoBack()) {
			TicketsWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}	
	*/
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
            case KeyEvent.KEYCODE_BACK:
                if(TicketsWebView.canGoBack() == true){
                	TicketsWebView.goBack();
                }else{
                    finish();
                }
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }	
	
}

