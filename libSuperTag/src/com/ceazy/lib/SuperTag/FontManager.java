package com.ceazy.lib.SuperTag;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Typeface;

class FontManager {
	
	Context context;
	
	protected FontManager(Context context) {
		setContext(context);
	}
	
	private void setContext(Context context) {
		this.context = context;
	}
	
	private Context getContext() {
		return context;
	}
	
	protected Typeface getFontFromResource(int resource) //Credits to bk138 from StackOverFlow
	{ 
	    Typeface tf = null;
	    InputStream is = null;
	    try {
	        is = getContext().getResources().openRawResource(resource);
	    }
	    catch(NotFoundException e) {
	    }

	    String outPath = getContext().getCacheDir() + "/tmp" + System.currentTimeMillis() + ".raw";

	    try
	    {
	        byte[] buffer = new byte[is.available()];
	        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outPath));

	        int l = 0;
	        while((l = is.read(buffer)) > 0)
	            bos.write(buffer, 0, l);

	        bos.close();

	        tf = Typeface.createFromFile(outPath);

	        // clean up
	        new File(outPath).delete();
	    }
	    catch (IOException e)
	    {
	        return null;
	    }

	    return tf;      
	}

}
