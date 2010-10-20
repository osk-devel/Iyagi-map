package com.knifenomad.anonymous;

import android.content.Context;
import android.view.View;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import org.json.JSONObject;

//         Util.showToast(AnonymousLoginActivity.this, "Toast!", 0);

public class Global {
	
	public static Dict getDict(String name)
	{
		AnonymousApplication context = AnonymousApplication.getContext();
		return (Dict) context.getGlobal(name);
	}
	
	public static Object get(String name)
	{
		AnonymousApplication context = AnonymousApplication.getContext();
		return context.getGlobal(name);
	}
	
	public static void set(String name, Object obj)
	{
		AnonymousApplication context = AnonymousApplication.getContext();
		context.setGlobal(name, obj);
	}
	
	public static void showLongToast(Context context, String text)
	{
		showLongToastWithImage(context, text, 0);
	}
	
	public static void showLongToastWithImage(Context context, String text, int src)
	{
		showToast(context, text, src, false);
	}
	
	public static void showShortToast(Context context, String text)
	{
		showShortToastWithImage(context, text, 0);
	}
	
	public static void showShortToastWithImage(Context context, String text, int src)
	{
		showToast(context, text, src, false);
	}
	
    public static void showToast(Context context, String text, int src, boolean shortmsg) {

    	int duration = Toast.LENGTH_LONG;
        View view = View.inflate(context, R.layout.message_toast, null);

        TextView tv  = (TextView)view.findViewById(R.id.message_text);
        tv.setText(text);

        if (src > 0)
        {
        	ImageView iv = (ImageView)view.findViewById(R.id.message_image);
        	iv.setScaleType(ScaleType.FIT_XY);
        	iv.setImageResource(src);
        }
        Toast toast = new Toast(context);
        toast.setView(view);
        if(shortmsg) duration = Toast.LENGTH_SHORT;
        toast.setDuration(duration);
        toast.show();
    }
}
