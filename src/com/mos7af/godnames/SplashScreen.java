package com.mos7af.godnames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class SplashScreen extends Activity {
	
	protected int _splashTime = 1000; 
	private Thread splashTread;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.splash);

	    final SplashScreen sPlashScreen = this; 
	    splashTread = new Thread() {
	        @Override
	        public void run() {
	            try {	            	
	            	synchronized(this){
	            		wait(_splashTime);
	            	}
	            } catch(InterruptedException e) {} 
	            finally {
	                finish();
	                Intent i = new Intent();
	                i.setClass(sPlashScreen, GodNames.class);
	        		startActivity(i);
	            }
	        }
	    }; 
	    splashTread.start();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
	    	synchronized(splashTread){
	    		splashTread.notifyAll();
	    	}
	    }
	    return true;
	}
	
}
