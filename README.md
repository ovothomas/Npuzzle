Npuzzle
=======

The game Npuzzle for NativeApp

MainActivityView


SettignsView






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
- Use bitmap.createBitmap(Bitmap Bitmap, int x, int y,  int width, int height to crop the image


EasyView

MediumView 

HardView 

YouWinView:
- YouWin extends Activity implements OnClickListener
setOnClickListener(View, view):
this attaches an evnet handler that fires when its tapped.
It the reveals the object that was tapped.


