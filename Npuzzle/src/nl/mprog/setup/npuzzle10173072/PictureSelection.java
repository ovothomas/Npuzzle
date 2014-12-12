package nl.mprog.setup.npuzzle10173072;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class PictureSelection extends ActionBarActivity implements OnItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_selection);
	
		//find the grid layout and assign the ImageAdapter
		//add an onclickListener
		GridView grid = (GridView) findViewById(R.id.gridview);
	
		grid.setAdapter(new ImageAdapter (this));
		grid.setOnItemClickListener(this);
	}

	
/* When the picture is tapped or clicked it has to go to a new 
 * activity which is the DisplaySettings view
 */
public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    	
    	// create the Intent to open our ShowImage activity.
    	Intent i = new Intent(this, GamePlay.class);
   
    	// pass a key:value pair into the 'extra' bundle for
    	// the intent so the activity is made aware which
    	// photo was selected.
    	i.putExtra("imageToDisplay", id);

    	// start our activity
    	startActivity(i);
    }
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture_selection, menu);
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
