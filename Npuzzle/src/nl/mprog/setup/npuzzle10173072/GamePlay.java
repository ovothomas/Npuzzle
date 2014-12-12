package nl.mprog.setup.npuzzle10173072;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class GamePlay extends ActionBarActivity implements OnClickListener,
		OnItemClickListener {
	GridView grid;
	int GameDifficulty = 3;
	static Bitmap[] images;
	static Bitmap[] newtemp;
	int tileWidth;
	int tileHeight;
	Bundle extras = null;
	int boardsize;
	int padding = 2;
	int click1, click2, counter;
	Bitmap tempBitmap;
	Bitmap temp;
	int blanktile;
	Bitmap ovobmp;
	Bitmap blanksrc;
	int[] ID = new int[25];
	static ImageView[] views;
	Bitmap background;
	int resource;
	int blankpos1;
	int numberMoves;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_play);
		// Function ImageSplit is called when this activity opens
		
		// retrieve the set of data passed to us by the Intent
		extras = getIntent().getExtras();
		
		ImageSplit();

		// Shuffle();

		grid = (GridView) findViewById(R.id.gridview);

		grid.setNumColumns(GameDifficulty);

		final GameAdapter newGame = new GameAdapter(this, images, ID);
		grid.setAdapter(newGame);

		grid.setOnItemClickListener(this);

	}

	/*
	 * This method splits the image we want to use for the puzzle. We get the
	 * resource from the previous activity . The image is the scaled according
	 * to the desired height and width of the tile I want For the blank tile
	 * another image is loaded directly from the my drawable because this image
	 * will always be used no matter which image is selected.
	 */
	private void ImageSplit() {
		// Method to get the Display height and Width of the particular device
		// being used by the user
		int screenWidth = this.getResources().getDisplayMetrics().widthPixels;
		int screenHeight = this.getResources().getDisplayMetrics().heightPixels;

		boardsize = GameDifficulty * GameDifficulty;

		blanktile = boardsize - 1;

		// retrieve the ImagetoDisplay ID from the extras bundle
		resource = (int) extras.getLong("imageToDisplay");

		blanksrc = BitmapFactory.decodeResource(getResources(),
				R.drawable.black);

		// load large image from resources
		Bitmap background = BitmapFactory.decodeResource(this.getResources(),
				resource);

		// Get the width and height of the image
		int width = background.getWidth();
		int height = background.getHeight();

		// Scaling factor
		float sHeight = (float) screenHeight / height;
		float sWidth = (float) screenWidth / width;
		float scale;

		if (sHeight < sWidth)
			scale = sHeight;
		else
			scale = sWidth;

		width = (int) Math.round(width * scale);
		height = (int) Math.round(height * scale);

		Bitmap newBitmap = Bitmap.createScaledBitmap(background, width, height,
				true);

		int tileWidth = (int) Math.round(width / GameDifficulty);
		int tileHeight = (int) Math.round(height / GameDifficulty);

		images = new Bitmap[boardsize];
		int count = 0;

		// Cropping the tiles and placing it in a images array
		for (int x = 0; x < GameDifficulty; x++) {
			for (int y = 0; y < GameDifficulty; y++) {
				int xCoordinate = x * tileWidth;
				int yCoordinate = y * tileHeight;
				Bitmap cropped = Bitmap.createBitmap(newBitmap, xCoordinate,
						yCoordinate, tileWidth, tileHeight);
				int position = y * GameDifficulty + x;
				images[position] = cropped;
				ID[count] = count;
				count++;
			}
		}
		// adding my blanktile to the array
		Bitmap ovobmp = Bitmap.createScaledBitmap(blanksrc, tileWidth,
				tileHeight, true);

		// no longer need larger image
		background.recycle();

		// image that I want to use as a blanktile is added to the array
		images[blanktile] = ovobmp;

		ImagePreview(newBitmap);
	}

	/*
	 * finishes (closes) the activity when the user clicks on the image
	 */
	public void onClick(View v) {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.game_play, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*
		 * In the onOptionItemSelected menu the Difficulty Settings of the game
		 * can be chosen.
		 */

		switch (item.getItemId()) {
		case R.id.settingseasy:
			GameDifficulty = 3;
			grid.setNumColumns(GameDifficulty);
			ImageSplit();
			grid.setAdapter(new GameAdapter(this, images, ID));
			numberMoves = 0;
			grid.invalidateViews();
			return false;
		case R.id.settingsmedium:
			GameDifficulty = 4;
			grid.setNumColumns(GameDifficulty);
			ImageSplit();
			grid.setAdapter(new GameAdapter(this, images, ID));
			numberMoves = 0;
			grid.invalidateViews();
			return false;
		case R.id.settingshard:
			GameDifficulty = 5;
			grid.setNumColumns(GameDifficulty);
			ImageSplit();
			grid.setAdapter(new GameAdapter(this, images, ID));
			numberMoves = 0;
			grid.invalidateViews();
			return false;
		case R.id.shuffle:
			Shuffle();
			grid.setAdapter(new GameAdapter(this, images, ID));
			numberMoves = 0;
			grid.invalidateViews();
			return false;
		}

		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// This loop searches the position of the blanktile everytime there is a
		// swap
		for (int x = 0; x < boardsize; x++) {
			if ((int) grid.getChildAt(x).getTag() == boardsize - 1) {
				blankpos1 = x;
			}
		}
		click1 = position;
		//int pos = (int) grid.getChildAt(position).getTag();
		//Toast.makeText(GamePlay.this, pos, Toast.LENGTH_SHORT);
		if (position - GameDifficulty == blankpos1) {

			Swap();
			checkWin();
		}

		else if (position + GameDifficulty == blankpos1) {

			Swap();
			checkWin();
		}

		else if (position == blankpos1 + 1 && position % GameDifficulty != 0) {

			Swap();
			checkWin();
		}

		else if (position == blankpos1 - 1
				&& position % GameDifficulty != GameDifficulty - 1) {
			Swap();
			checkWin();
		} else {
			Toast.makeText(GamePlay.this, "Invalid Move", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void Swap() {

		tempBitmap = images[click1];
		images[click1] = images[blankpos1];
		images[blankpos1] = tempBitmap;
		int temp = ID[click1];
		ID[click1] = ID[blankpos1];
		ID[blankpos1] = temp;
		numberMoves++;

		grid.invalidateViews();
	}

	private void Shuffle() {

		for (int x = 0; x < boardsize / 2; x++) {
			temp = images[x];
			images[x] = images[boardsize - x - 2];
			images[boardsize - x - 2] = temp;
		}
		// special case when the board is even
		if (boardsize % 2 == 0) {
			temp = images[boardsize - 2];
			images[boardsize - 2] = images[boardsize - 3];
			images[boardsize - 3] = temp;

		}
		numberMoves = 0;
	}

	public boolean checkWin() {

		for (int x = 0; x < boardsize; x++) {
			if (x != (int) grid.getAdapter().getView(x, null, null).getTag()) {// getChildAt(x).getTag()){
				return false;
			}
		}
		Intent intent = new Intent(this, YouWin.class);
		intent.putExtra("imageToDisplay", resource);
		intent.putExtra("numberOfMoves", numberMoves);
		startActivity(intent);
		finish();
		Toast.makeText(GamePlay.this, "You won", Toast.LENGTH_SHORT).show();
		return true;
	}
	
/*
	@Override
	public void onPause(){
		super.onPause();
		SharedPreferences prefs = this.getSharedPreferences("savedGame", MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("GameDifficulty", GameDifficulty);
		editor.putInt("Resource", resource);
		editor.putInt("Moves", numberMoves);
		editor.putBoolean("gameIsActive", true);
		for (int k = 0; k < GameDifficulty; k++){
			editor.putInt("ID" + k, ID[k]);
		}
		editor.commit();
	}
	
	public void onResume(){
		super.onResume();
		SharedPreferences prefs = this.getSharedPreferences("savedGame", MODE_PRIVATE);
		GameDifficulty = prefs.getInt("GameDifficulty", 3);
		resource = prefs.getInt("Resource", -1);
		numberMoves = prefs.getInt("Moves", 0);
		for(int j = 0; j < GameDifficulty; j++){
			ID[j] = prefs.getInt("ID" + j, j);
		//for(int i = 0; i < GameDifficulty; i++){
			//images[i] = newTemp[i];
		//}
		}
		
*/	

	/*
	 * 
	 * @Override public void onResume(){ super.onResume();
	 * 
	 * // get preferences object SharedPreferences prefs =
	 * this.getSharedPreferences("savedInfo", MODE_PRIVATE); // set text to our
	 * saved value GameDifficulty = prefs.getInt("GameDifficulty", 3); resource
	 * = prefs.getInt("PlayingImage", 2); numberMoves =
	 * prefs.getInt("numberMoves", 0); for(int j = 0; j < boardsize; j++){ ID[j]
	 * = prefs.getInt("ID" + j, j); } grid.invalidateViews(); }
	 */

	private void ImagePreview(Bitmap background) {

		ImageView preview = new ImageView(this);
		preview.setImageBitmap(background);
		Toast toast = new Toast(this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(preview);
		toast.show();
	}
}
