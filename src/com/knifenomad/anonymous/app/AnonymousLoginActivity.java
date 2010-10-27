package com.knifenomad.anonymous.app;

import com.knifenomad.anonymous.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.view.KeyEvent;
import android.widget.Button;
import android.R.drawable;
import android.util.Log;

public class AnonymousLoginActivity extends Activity {
	
	Button   signupBtn;
	EditText email;
	EditText password;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //Global.showLongToastWithImage(AnonymousLoginActivity.this, "hahahahahaha", R.drawable.sample_thumb_2);
        
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
    	JDict params = new JDict();
    	params.set("auto",  "0");
    	params.set("email", email);
    	params.set("pwd",   hashed);
    	String data = Apis.call("login.py", params);
    	JDict decoded = JDict.decode(data);
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
