package nl.mprog.setup.npuzzle10173072;
/*
 * Thomas Stephens 10173072
 * This class opens when the game is won
 * The image is shown and also the number of moves made
 * When the image is clicked we go back to the main menu
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class YouWinActivity extends ActionBarActivity implements OnClickListener  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_you_win);
		
		RelativeLayout relative = (RelativeLayout)findViewById(R.id.youwin);
		relative.setOnClickListener(this);
		
		//Get the image from the previous activity and display it
		ImageView img = (ImageView)findViewById(R.id.singleview);
		Bundle extras = getIntent().getExtras();
		int resource = (int)extras.getInt("imageToDisplay");
		img.setImageResource(resource);
		
		// This textview displays the number of moves made by the player
		TextView textview = (TextView)findViewById(R.id.textview);
		int move = (int)extras.getInt("numberOfMoves");
		textview.setText("Number of moves : " + move);
					
	}
	
	public void onClick(View v) {
		//When the image shown is clicked go the Mainscreen
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.you_win, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
