package com.knifenomad.anonymous.template;

import com.knifenomad.anonymous.R;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

import android.content.Context;

// 1. Title, text
// 2. image text
// 3. image, title, text
// 4. adding arrows



public class Cell{

	public static void getCellImageTitleDesc(int resid, String title, String Desc) {
	}
	
	public static View CellTitleEdittext(Context context, String title, String hint) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View cell = (View) inflater.inflate(R.layout.cell_title_edittext, null);
		TextView tv = (TextView) cell.findViewById(R.id.title);
		EditText et = (EditText) cell.findViewById(R.id.edit);
		tv.setText(title);
		if (hint != null) et.setHint(hint);
		
		return cell;
	}
	
	public static String getCellText(View v, int id) {
		TextView tv = (TextView) v.findViewById(id);
		return tv.getText().toString();
	}
}