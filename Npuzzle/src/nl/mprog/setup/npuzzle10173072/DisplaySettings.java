package nl.mprog.setup.npuzzle10173072;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class DisplaySettings extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_settings);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_settings, menu);
		return true;
	}
	
	/** Called when the user clicks the button */
	public void openGameplayEasy(View view) {
	    // open display settings view when the button is clicked
		Intent intent = new Intent(this, GameplayEasy.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the button */
	public void openGameplayMedium(View view) {
	    // open display settings view when the button is clicked
		Intent intent = new Intent(this, GameplayMedium.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the button */
	public void openGameplayHard(View view) {
	    // open display settings view when the button is clicked
		Intent intent = new Intent(this, GameplayHard.class);
		startActivity(intent);
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
