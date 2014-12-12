package nl.mprog.setup.npuzzle10173072;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GameAdapter extends BaseAdapter {
	
	private Context mContext;
	private Bitmap[] mImages;
	private ImageView[] views;
	private int[] mIds;
	
	//Game Constructor
	public GameAdapter(Context c, Bitmap[] images, int[] ID ){
		mContext = c;
		mImages = images  ;
		views = new ImageView[images.length];
		for (int x = 0; x < images.length; x++){
			views[x] = new ImageView(mContext);
			
		}
		mIds = ID;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mImages.length;
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mImages[position];
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imgView = views[position];
		
		imgView.setImageBitmap(mImages[position]);
		//change this later!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		imgView.setTag(mIds[position]);
		return imgView;
	}

}
