Npuzzle
=======

The game Npuzzle for NativeApp

MainActivityView


SettingsView






PossiblePicturesView:
- ListView: Will display a scrollable list of pictures which the user can choose from to continue the game
- Implement some scrollable list of pictures
- ListActivity ArrayAdapter with OnItemClickListener
- Lists will be displayed in Gridview

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
width, height: dimensions for cropped image in pixels


EasyView, MediumView ,HardView:
Associate the number of tiles in each respective difficulty with numbers. This setups up the board in its intiate state
and helps determine when the board is won.

Imageview(setImageBitmap(), setImageDrawable to change image by moving it psyhically by swapping tiles. 

YouWinView:
- YouWin extends Activity implements OnClickListener
setOnClickListener(View, view):
this attaches an evnet handler that fires when its tapped.
It the reveals the object that was tapped.


