package com.knifenomad.anonymous.app;

import com.knifenomad.anonymous.*;
import com.knifenomad.anonymous.template.Cell;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.Button;
import android.util.Log;

public class AnonymousSignupActivity extends Activity {
    
    View cell1 = null;
    View cell2 = null;
    View cell3 = null;
    View cell4 = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);

        LinearLayout ConfigLayout = new LinearLayout(this);
        ConfigLayout.setOrientation(LinearLayout.VERTICAL);
        
        cell1 = Cell.CellTitleEdittext(AnonymousSignupActivity.this, "Email", "John@example.com");
        cell2 = Cell.CellTitleEdittext(AnonymousSignupActivity.this, "닉네임", "Required");
        cell3 = Cell.CellTitleEdittext(AnonymousSignupActivity.this, "비밀번호", "Required");
        cell4 = Cell.CellTitleEdittext(AnonymousSignupActivity.this, "비밀번호확인", "Required");
        
        ConfigLayout.addView(cell1);
        ConfigLayout.addView(cell2);
        ConfigLayout.addView(cell3);
        ConfigLayout.addView(cell4);
        
        Button cancel = new Button(this);
        Button signup = new Button(this);
        
        cancel.setText("Cancel");
        signup.setText("Register");
        
        ConfigLayout.addView(cancel);
        ConfigLayout.addView(signup);
        
        cancel.setOnClickListener(mOnCancel);
        signup.setOnClickListener(mOnSignup);
        
        setContentView(ConfigLayout);
    }
    
    private View.OnClickListener mOnCancel = new View.OnClickListener() {
    	public void onClick(View v) {
    	    finish();
    	}
    };

    private View.OnClickListener mOnSignup = new View.OnClickListener() {
    	public void onClick(View v) {
    	    String email =   Cell.getCellText(cell1, R.id.edit);
    	    String nick    = Cell.getCellText(cell2, R.id.edit);
    	    String passw   = Cell.getCellText(cell3, R.id.edit);
    	    String confirm = Cell.getCellText(cell4, R.id.edit);
    	    
    	    Dict params = new Dict();
    	    params.put("email", email);
    	    params.put("nick" , nick);
    	    params.put("pwd"  , HashString.digest(email+passw).toUpperCase());
    	    Apis.call("join.py", params);
    	    finish();
    	}
    };
}