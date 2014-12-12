package nl.mprog.setup.npuzzle10173072;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	Button resumeButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//resumeButton = (Button) findViewById(R.id.button2);
		//SharedPreferences prefs = this.getSharedPreferences("savedInfo", MODE_PRIVATE);
		//resumeButton.setVisibility(View.VISIBLE);
		//resumeButton.setVisibility(View.INVISIBLE);
	}
	
	/** Called when the user clicks the play button */
	public void openSettings(View view) {
	    // open settings view when the button is clicked
		Intent intent = new Intent(this, PictureSelection.class);
		startActivity(intent);
	}
	
	public void openresume(View view) {
	    // open settings view when the button is clicked
		Intent intent = new Intent(this, GamePlay.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
