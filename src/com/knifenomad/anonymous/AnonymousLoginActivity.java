package com.knifenomad.anonymous;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.text.Editable;
import android.graphics.Color;
import android.view.View;
import android.view.View;
import android.view.KeyEvent;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.Toast;
import android.widget.Button;
import android.R.drawable;

public class AnonymousLoginActivity extends Activity {
	
	Button   signupBtn;
	EditText email;
	EditText password;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Global.showLongToastWithImage(AnonymousLoginActivity.this, "hahahahahaha", R.drawable.sample_thumb_2);
        String emailText;
        String passwordText;
        
        InputMethodManager mInputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputManager.showSoftInput(getCurrentFocus(), InputMethodManager.SHOW_FORCED);
        
        findViewById(R.id.signup).setOnClickListener(mGetClickListener);
        findViewById(R.id.password).setOnKeyListener(mGetKeyListener);
    }
    
    private boolean checkLogin(String email, String password)
    {
    	
    	String get_params  = null;
    	String auth_string = null;
    	String hashed = null;
    	String url = null;
    	hashed = HashString.digest(email+password).toUpperCase();
    	get_params = String.format("auto=0&email=%s&pwd=%s", email, hashed);
    	url = "http://175.122.253.4:33333/cgi-bin/login.py?" + get_params;
    	HttpData data = HttpRequest.get(url);
    	Dict decoded = Dict.decode(data.content);
    	if (decoded == null) return false;
    	auth_string = decoded.getString("msg");
    	if (auth_string.equals("OK")) {
    		Global.set("user", decoded);
    		return true;
    	}
    	return false;
    }
    
    private View.OnKeyListener mGetKeyListener = new View.OnKeyListener() {

    	public boolean onKey(View v, int keyCode, KeyEvent event) {
    		// TODO Auto-generated method stub
    		if ( keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
    			email    = (EditText)findViewById(R.id.email);
    			password = (EditText)findViewById(R.id.password);
    			if(checkLogin(email.getText().toString().trim(), password.getText().toString().trim()))
    			{
	    			Intent intent = getIntent();
	    			setResult(RESULT_OK,intent);
	    			finish();
	    			return true;
    			}
    			String msg = "Wrong E-mail or Password.";
    			Global.showLongToastWithImage(AnonymousLoginActivity.this, msg, drawable.ic_dialog_alert);
    			return false;
    		}
    		return false;
    	}
    };
    
    private View.OnClickListener mGetClickListener = new View.OnClickListener() {
    	public void onClick(View v) {
    	    Intent signup = new Intent(AnonymousLoginActivity.this, AnonymousSignupActivity.class);
    	    startActivity(signup);
    	}
    };

}
