package android.game.highoctane;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

// An activity has essentially four states:
// - If an activity in the foreground of the screen (at the top of the stack),
// it is active or running.
// - If an activity has lost focus but is still visible (that is, a new
// non-full-sized or transparent activity has focus on top of your activity), it
// is paused. A paused activity is completely alive (it maintains all state and
// member information and remains attached to the window manager), but can be
// killed by the system in extreme low memory situations.
// - If an activity is completely obscured by another activity, it is stopped.
// It still retains all state and member information, however, it is no longer
// visible to the user so its window is hidden and it will often be killed by
// the system when memory is needed elsewhere.
// - If an activity is paused or stopped, the system can drop the activity from
// memory by either asking it to finish, or simply killing its process. When it
// is displayed again to the user, it must be completely restarted and restored
// to its previous state.

public class HighOctaneActivity extends Activity {

	private static final String TAG = HighOctaneActivity.class.getSimpleName();

	// Called when the activity is first created. This is where you should do
	// all of your normal static set up: create views, bind data to lists, etc.
	// This method also provides you with a Bundle containing the activity's
	// previously frozen state, if there was one. Always followed by onStart().
	@Override
	public void onCreate(Bundle savedInstanceState) {

		Log.d(TAG, "**************************************");
		Log.d(TAG, "********* Creating Activity **********");
		Log.d(TAG, "**************************************");
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(new GameSurfaceView(this));
		
		//TEST..looks like this works, will can in handy later
		Configuration.Init(this,"game.properties");
		Log.d(TAG, "test: " + Configuration.GetProperty("test ", 99));
		
		
		// ------------------------------------------------------------------------------
		// cut out all graphics to required sizes from larger images
		ImageManager imageManager = GameManager.getSingleton().getImageManager();

		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_frames);	
		int bankNumber = imageManager.CreateBank(bitmap,32,32);
		
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scene_blocks01);	
		bankNumber = imageManager.CreateBank(bitmap,16,16);
	}

	// The final call you receive before your activity is destroyed. This can
	// happen either because the activity is finishing (someone called finish()
	// on it, or because the system is temporarily destroying this instance of
	// the activity to save space. You can distinguish between these two
	// scenarios with the isFinishing() method.
	@Override
	protected void onDestroy() {

		if (isFinishing()) {
			Log.d(TAG, "this instance called finish()");
		} else {
			Log.d(TAG, "system is temporarily destroying this instance");
		}
		
		Log.d(TAG, "****************************************");
		Log.d(TAG, "********* Destroying Activity **********");
		Log.d(TAG, "****************************************");
		
		super.onDestroy();
	}

	// Called when the activity is no longer visible to the user, because
	// another activity has been resumed and is covering this one. This may
	// happen either because a new activity is being started, an existing one is
	// being brought in front of this one, or this one is being destroyed.
	// Followed by either onRestart() if this activity is coming back to
	// interact with the user, or onDestroy() if this activity is going away.
	@Override
	protected void onStop() {
		Log.d(TAG, "Stopping Activity");
		super.onStop();
	}

	// Called when the activity is becoming visible to the user. Followed by
	// onResume() if the activity comes to the foreground, or onStop() if it
	// becomes hidden.
	@Override
	protected void onStart() {
		Log.d(TAG, "Starting Activity");
		super.onStart();
	}

	// Called after your activity has been stopped, prior to it being started
	// again. Always followed by onStart()
	@Override
	protected void onRestart() {
		Log.d(TAG, "Restarting Activity");
		super.onRestart();
	}

	// Called when the activity will start interacting with the user. At this
	// point your activity is at the top of the activity stack, with user input
	// going to it. Always followed by onPause().
	@Override
	protected void onResume() {
		Log.d(TAG, "Resuming Activity");
		super.onResume();
	}

	// Called when the system is about to start resuming a previous activity.
	// This is typically used to commit unsaved changes to persistent data, stop
	// animations and other things that may be consuming CPU, etc.
	// Implementations of this method must be very quick because the next
	// activity will not be resumed until this method returns. Followed by
	// either onResume() if the activity returns back to the front, or onStop()
	// if it becomes invisible to the user.
	@Override
	protected void onPause() {
		
		Log.d(TAG, "****************************************");
		Log.d(TAG, "*********  Pausing Activity   **********");
		Log.d(TAG, "****************************************");
		super.onPause();
	}
}
