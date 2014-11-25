package nl.mprog.setup.npuzzle10173072;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private int[] mImages;
	private Context mContext = null;
	
	//store a cache of resized bitmaps
	private Bitmap[] cache;
	
	//Creating a constructor
	public ImageAdapter(Context context) {
		mContext = context;
	
	
		//Figuring out which images are in the drawbale folder
		//obtain a list of all the objects in the R.drawable class
		Field[] list = R.drawable.class.getFields();
		
		int count = 0, index = 0, j = list.length;
		
		// We first need to figure out how many of our images we have before
		// We can request the memory for an array of integers to hold their contents
		
		// We want to loop all of the fields in the R.drawable class
		// If the image starts with img_ then thats the image we want
		for(int i=0; i < j; i++)
			if (list[i].getName().startsWith("img_")) count++;
		
		// Reserve memory for an array of integers with length 'count' and initialize our cache.
		mImages = new int[count];
		cache = new Bitmap[count];
		
		// Try to get the values of each of those fields into the images array.
		try{
			for(int i=0; i<j; i++)
				if(list[i].getName().startsWith("img_"))
					mImages[index++] = list[i].getInt(null);
		} catch(Exception e) {}
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mImages.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return mImages[position];
	}
	
	// This creates an image view when requested
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// we've been asked for an ImageView at a specific position. If
		// one doesn't already exist (ie, convertView is null) then we must create
		// one. Otherwise we can pass it convertView or a recycled view
		// that's been passed to us.
		
		ImageView imgView;

		
		if(convertView == null) {

			// create a new view
			imgView = new ImageView(mContext);
			imgView.setLayoutParams(new GridView.LayoutParams(100,100));

		} else {
	
			// recycle an old view (it might have old thumbs in it!)
			imgView = (ImageView) convertView;
	
		}

		// see if we've stored a resized thumb in cache
		if(cache[position] == null) {
		
			// create a new Bitmap that stores a resized
			// version of the image we want to display. 
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 4;
			Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), mImages[position], options);

			// store the resized thumb in a cache so we don't have to re-generate it
			cache[position] = thumb;
		}

		// use the resized image we have in the cache
		imgView.setImageBitmap(cache[position]);


		// We might be tempted to do the below, but this is bad. The
		// images we've put in the drawable directory are quite large
		// and need to be scaled down to load all of them in memory to
		// display on screen. If we just use the raw images (as in the
		// below code) we would quickly get an OutOfMemory exception,
		// as the entire image would be loaded in memory and scaled 
		// down live.
		//imgView.setImageResource(images[position]);

		return imgView;
	}

}
