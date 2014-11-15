Npuzzle
=======

The game Npuzzle for NativeApp

MainActivityView:

This will be the homepage with only one button when clicked will go the Difficulty selection view.
setOnClickListener.
Create PlayButton
Will use RelativeLayout to set the PlayButton at desired place on screen. 


SettingsView
Public class SettingsView
Create three different buttons
 - Easy
 - Medium
 - Hard






ImageSelection:

- ListView: Will display a scrollable list of pictures which the user can choose from to continue the game
- Implement some scrollable list of pictures
- ListActivity ArrayAdapter with OnItemClickListener
- Lists will be displayed in Gridview
- ArrayAdapter to hold the images

-Bitmap class
BitmapFactory.decodeResource(Resources r, int id)
-scale the picture to fit on screen
Bitmap.createScaledBitmap(Bitmap bitmap, int width, int height, booelan filter);
bitmap: image to scale
width: desired with of image
height: desired height of image
- Use bitmap.createBitmap(Bitmap Bitmap, int x, int y,  int width, int height to crop the image:
Bitmap: image to crop
x, y: origin for cropped image
width, height: dimensions for cropped image in pixels.
bitmap.recycle() when the rest of the image is no longer needed


Gameplay:
Shuffle the images befor gameplay
Public class Gameplay
Associate the number of tiles in each respective difficulty with numbers. This setups up the board in its intiate state
and helps determine when the board is won.
Imageview(setImageBitmap(), setImageDrawable to change image by moving it psyhically by swapping tiles. 

YouWin:
The Game is won when the image is reconstructed. Because the cropped images are connected to numbers it will be equivalent to increasing order according to your selected difficulty. So the game is not won when the tile is not in order
create a public class YouWin
create an intent to swap through activities
When the game is won a congratulatory message is shown.
- YouWin extends Activity implements OnClickListener
setOnClickListener(View, view):
this attaches an evnet handler that fires when its tapped.
It the reveals the object that was tapped.


