package com.ebveneto.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ebveneto.R;


public class CustomLogoTextView extends TextView
{
	private Typeface tf;
	//public  final String DEFAULT_FONT = "fonts/Rimouski.otf";
	//public  final String DEFAULT_FONT = "fonts/lato_light.ttf";
	public  final String DEFAULT_FONT = "fonts/lato_bold.ttf";
	public CustomLogoTextView(Context context)
	{
		super(context);
		if (!isInEditMode())
			init(null);
	}
	public CustomLogoTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		if (!isInEditMode())
			init(attrs);
	}

	public CustomLogoTextView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		if (!isInEditMode())
			init(attrs);
	}

	private void init(AttributeSet attrs)
	{
		if(attrs!=null)
		{
			TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextViewRegular);
			String fontName = a.getString(R.styleable.CustomTextViewRegular_attr_font);
				if (fontName != null)
					tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/"+fontName+".OTF");
				else
					tf = Typeface.createFromAsset(getContext().getAssets(),DEFAULT_FONT);  //default font for app
				
			a.recycle();
			if(tf!=null)
				setTypeface(tf);
		}
	}
}
