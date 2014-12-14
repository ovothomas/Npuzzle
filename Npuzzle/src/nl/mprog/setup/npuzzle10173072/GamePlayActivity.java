package nl.mprog.setup.npuzzle10173072;

/*
 * Name: Thomas Stephens 10173072
 * Project: Npuzzle
 * In GamePlay.java the image the user chooses is cropped here.
 * Also the swap, shuffle and win activity are all implemented
 * here. GridView is used to display the cropped images on the screen.
 * With the OnItemCLicked listener the images can swapped from GridView 
 * to GridView. An imageadapter is needed for the cropped images to be 
 * displayed on the screen. I also give a set of Ids to the images.
 * This allows me to know where in tile is on the grid.
 * Our GameState is saved in onPause() and retrieve in 
 * Oncreate() and OnResume(). 	
 */

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

public class GamePlayActivity extends ActionBarActivity implements OnClickListener,
		OnItemClickListener {
	private GridView grid;
	int gameDifficulty = 3;
	static Bitmap[] images;
	int tileWidth;
	int tileHeight;
	Bundle extras = null;
	int boardSize;
	int click1;
	Bitmap temp;
	int blankTile;
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
		// the set of data from the previous activity is retrieved
		extras = getIntent().getExtras();

		// Retrieve state if the game was paused.
		// If the game was paused loop through the board and give the
		// saved ids to the adapter so it can place the images on the
		// board accordingly.
		if (extras == null)
			extras = getIntent().getExtras();
		SharedPreferences prefs = getSharedPreferences("gameState",
				MODE_PRIVATE);
		numberMoves = prefs.getInt("numberMoves", 0);
		gameDifficulty = prefs.getInt("GameDifficulty", 3);
		for (int j = 0; j < gameDifficulty * gameDifficulty; j++) {
			ID[j] = prefs.getInt("ID" + j, j);
		}
		boardSize = gameDifficulty * gameDifficulty;
		if (images == null) {

			ImageSplit();
			grid = (GridView) findViewById(R.id.gridview);
			grid.setNumColumns(gameDifficulty);
			final GameAdapterActivity newGame = new GameAdapterActivity(this, images, ID);
			grid.setAdapter(newGame);
			grid.setOnItemClickListener(this);
		} else {

			grid = (GridView) findViewById(R.id.gridview);
			grid.setNumColumns(gameDifficulty);
			final GameAdapterActivity newGame = new GameAdapterActivity(this, images, ID);
			grid.setAdapter(newGame);
			grid.setOnItemClickListener(this);
		}

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

		extras = getIntent().getExtras();

		int screenWidth = this.getResources().getDisplayMetrics().widthPixels;
		int screenHeight = this.getResources().getDisplayMetrics().heightPixels;

		boardSize = gameDifficulty * gameDifficulty;
		blankTile = boardSize - 1;

		// retrieve the ImagetoDisplay ID from the extras bundle
		resource = (int) extras.getLong("imageToDisplay");

		// Retrieve blanktile from drawable
		blanksrc = BitmapFactory.decodeResource(getResources(),
				R.drawable.blanktile);

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
		// we chose the optimal scale to draw the board
		if (sHeight < sWidth)
			scale = sHeight;
		else
			scale = sWidth;

		width = (int) Math.round(width * scale);
		height = (int) Math.round(height * scale);

		// scaling the image retrieved
		Bitmap newBitmap = Bitmap.createScaledBitmap(background, width, height,
				true);

		// the length and width of the tile is caculated
		int tileWidth = (int) Math.round(width / gameDifficulty);
		int tileHeight = (int) Math.round(height / gameDifficulty);

		images = new Bitmap[boardSize];
		int count = 0;

		// Cropping the tiles and placing it in a images array
		for (int x = 0; x < gameDifficulty; x++) {
			for (int y = 0; y < gameDifficulty; y++) {
				int xCoordinate = x * tileWidth;
				int yCoordinate = y * tileHeight;
				Bitmap cropped = Bitmap.createBitmap(newBitmap, xCoordinate,
						yCoordinate, tileWidth, tileHeight);
				int position = y * gameDifficulty + x;
				images[position] = cropped;
				ID[count] = count;
				count++;
			}
		}
		// adding my blanktile to the array
		Bitmap ovobmp = Bitmap.createScaledBitmap(blanksrc, tileWidth,
				tileHeight, true);
		
		// image that I want to use as a blanktile is added to the array
		images[blankTile] = ovobmp;

		// no longer need larger image
		background.recycle();

		
		// Shows the image for the user to see the solution, before game starts.
		ImagePreview(newBitmap);
		grid = (GridView) findViewById(R.id.gridview);
		grid.setNumColumns(gameDifficulty);
		final GameAdapterActivity newGame = new GameAdapterActivity(this, images, ID);
		grid.setAdapter(newGame);
		grid.setOnItemClickListener(this);
		Shuffle();
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
		 * can be chosen. grid.invalidateViews is initialized to notify my
		 * gridapter that the settings is changed.
		 */

		switch (item.getItemId()) {

		case R.id.settingseasy:
			setGameDifficulty(3);
			grid.setNumColumns(gameDifficulty);
			ImageSplit();
			grid.setAdapter(new GameAdapterActivity(this, images, ID));
			grid.invalidateViews();
			return false;
		case R.id.settingsmedium:
			setGameDifficulty(4);
			grid.setNumColumns(gameDifficulty);
			ImageSplit();
			grid.setAdapter(new GameAdapterActivity(this, images, ID));
			numberMoves = 0;
			grid.invalidateViews();
			return false;
		case R.id.settingshard:
			setGameDifficulty(5);
			grid.setNumColumns(gameDifficulty);
			ImageSplit();
			grid.setAdapter(new GameAdapterActivity(this, images, ID));
			numberMoves = 0;
			grid.invalidateViews();
			return false;
		case R.id.shuffle:
			Shuffle();
			grid.setAdapter(new GameAdapterActivity(this, images, ID));
			numberMoves = 0;
			grid.invalidateViews();
			return false;
		}

		return true;
	}
	
	// Save the number of moves and difficulty of the 
	// difficulty selected.
	private void setGameDifficulty(int j) {
		SharedPreferences prefs = getSharedPreferences("gameState",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();

		numberMoves = 0;
		gameDifficulty = j;
		editor.putInt("GameDifficulty", j);
		editor.commit();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// This loop searches the position of the blanktile everytime there is a
		// swap. When the equation is true the game has the position of the
		// blanktile
		// because of the ID tag I gave it. Then we can swap according to the
		// rules of
		// the game.
		for (int x = 0; x < boardSize; x++) {
			if ((int) grid.getChildAt(x).getTag() == boardSize - 1) {
				blankpos1 = x;
			}
		}
		click1 = position;
		if (position - gameDifficulty == blankpos1) {

			Swap();
			checkWin();
		}

		else if (position + gameDifficulty == blankpos1) {

			Swap();
			checkWin();
		}

		else if (position == blankpos1 + 1 && position % gameDifficulty != 0) {

			Swap();
			checkWin();
		}

		else if (position == blankpos1 - 1
				&& position % gameDifficulty != gameDifficulty - 1) {
			Swap();
			checkWin();
		} else {
			Toast.makeText(GamePlayActivity.this, "Invalid Move", Toast.LENGTH_SHORT)
					.show();
		}
	}

	// this is my swap function. I swap the ID's of the tiles.
	// my adapter is the notified and searches for the right
	// image attached to this Id.
	private void Swap() {
		int temp = ID[click1];
		ID[click1] = ID[blankpos1];
		ID[blankpos1] = temp;
		numberMoves++;

		grid.invalidateViews();
	}

	// Shuffle function
	private void Shuffle() {

		for (int x = 0; x < boardSize / 2; x++) {
			temp = images[x];
			images[x] = images[boardSize - x - 2];
			images[boardSize - x - 2] = temp;
		}
		// special case when the board is even
		if (boardSize % 2 == 0) {
			temp = images[boardSize - 2];
			images[boardSize - 2] = images[boardSize - 3];
			images[boardSize - 3] = temp;

		}
		numberMoves = 0;
	}

	// My checkwin function to check if the game is one.
	// It loops through the board everytime the boards changes
	// and checks if the board is in the right order.
	// If it is then the game is won and we go back to the YouWin.class
	// We also send the number of moves made.
	public boolean checkWin() {

		for (int x = 0; x < boardSize; x++) {
			if (x != (int) grid.getAdapter().getView(x, null, null).getTag()) {// getChildAt(x).getTag()){
				return false;
			}
		}
		Intent intent = new Intent(this, YouWinActivity.class);
		intent.putExtra("imageToDisplay", resource);
		intent.putExtra("numberOfMoves", numberMoves);
		startActivity(intent);
		finish();
		Toast.makeText(GamePlayActivity.this, "You won", Toast.LENGTH_SHORT).show();
		return true;
	}

	/*
	 * In here I saved the GameDifficulty, the image we are playing with, the
	 * number of moves made and also the Ids of the images. This is then
	 * commited.
	 */
	@Override
	public void onPause() {
		super.onPause();
		SharedPreferences prefs = this.getSharedPreferences("gameState",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.clear();
		editor.putInt("GameDifficulty", gameDifficulty);
		editor.putInt("Resource", resource);
		editor.putInt("Moves", numberMoves);
		editor.putBoolean("gameIsActive", true);
		for (int k = 0; k < gameDifficulty * gameDifficulty; k++) {
			editor.putInt("ID" + k, ID[k]);
		}

		editor.commit();
	}

	/*
	 * In onResume the data saved is retrieved from local storage so we can play
	 * the game when the state is resumed.
	 */
	@Override
	public void onResume() {
		super.onResume();
		SharedPreferences prefs = this.getSharedPreferences("gameState",
				MODE_PRIVATE);
		gameDifficulty = prefs.getInt("GameDifficulty", 3);
		numberMoves = prefs.getInt("Moves", 0);
		resource = prefs.getInt("Resource", -1);
		for (int j = 0; j < gameDifficulty * gameDifficulty; j++) {
			ID[j] = prefs.getInt("ID" + j, j);
			grid.invalidateViews();
		}
	}

	// A toast for the user to see the solution of the game before
	// the game is started.
	private void ImagePreview(Bitmap background) {

		ImageView preview = new ImageView(this);
		preview.setImageBitmap(background);
		Toast toast = new Toast(this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(preview);
		toast.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
