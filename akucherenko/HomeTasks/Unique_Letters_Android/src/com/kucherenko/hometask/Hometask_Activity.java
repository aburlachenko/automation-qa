package com.kucherenko.hometask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class Hometask_Activity extends Activity implements OnClickListener {
    
	EditText editText;
    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.uniqueButton).setOnClickListener(this);
        findViewById(R.id.singleButton).setOnClickListener(this);
        editText = (EditText) findViewById(R.id.editText);
    }
    String uniqueLetters(String text)
    {
    	String outText=text.substring(0, 1);
    	for (int i=1;i<text.length();i++)
	    	{
	    		if (outText.contains(text.substring(i,i+1))==false)
	    		outText=outText+text.substring(i, i+1);
	    	}
		return outText;
    }
    
    String singleSpaces(String text)
    {
    	String outText=text.substring(0, 1);
    	for (int i=1;i<text.length();i++)
    	{
    		if (text.charAt(i)==' ' & text.charAt(i-1)==' ') {}
    		else outText=outText+text.substring(i, i+1);
    	}
    	return outText;
    }
    
    public void onClick(View b)
    {
    String text = editText.getText().toString();
    switch (b.getId())
    {
    	case R.id.uniqueButton:
    		editText.setText(uniqueLetters(text));
    	break;
    	case R.id.singleButton:
    		editText.setText(singleSpaces(text));
    	break;
    }	
    }
}