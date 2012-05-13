/**
 * 
 */
package android.game.highoctane;

import android.content.Context;
import android.game.highoctane.ImageManager.Bank;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = GameSurfaceView.class.getSimpleName();
	private GameThread thread;

	public GameSurfaceView(Context context) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
	}

	public void DrawHelper() {
		Canvas canvas = null;
		SurfaceHolder surfaceHolder = getHolder();
		try {
			canvas = surfaceHolder.lockCanvas();
			synchronized (surfaceHolder) {
				onDraw(canvas);
			}
		} finally {
			if (canvas != null) {
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}

    int x=0;
	@Override
	protected void onDraw(Canvas canvas) {
		if (canvas != null) {
			canvas.drawColor(Color.BLACK);

			GameLogic gameLogic = GameManager.getSingleton().getGameLogic();
			ImageManager imageManager = GameManager.getSingleton().getImageManager();
			
		    x++;
			int icon=0;
			Bank imageBank = imageManager.GetBank(1);
			for (int iy = 0; iy < (this.getHeight()/16)/2; iy++) {
				for (int ix = 0; ix < (this.getWidth()/16)/2; ix++) {
					canvas.drawBitmap(imageBank.GetImage(icon%1000), x+ (ix*16),iy*16, null);
					icon++;
				}
			}
			
			
			imageBank = imageManager.GetBank(0);
			synchronized (gameLogic.vehicleContainer) {
				for (Vehicle iter : gameLogic.vehicleContainer) {
					canvas.drawBitmap(imageBank.GetImage(iter.getFrame()), iter.getX(), iter.getY(), null);
				}
			}

	
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			canvas.drawText("hello", 20, 20, paint);
		}
	}

	// see: http://developer.android.com/reference/android/view/MotionEvent.html
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		final int action = event.getAction() & MotionEvent.ACTION_MASK;
		if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {

			GameLogic gameLogic = GameManager.getSingleton().getGameLogic();
			synchronized (gameLogic.vehicleContainer) {
				final int pointerCount = event.getPointerCount();
				final float centreX = this.getWidth() / 2;
				final float centreY = this.getHeight() / 2;

				for (int i = 0; i < pointerCount; i++) {

					float vx = centreX - event.getX(i);
					float vy = centreY - event.getY(i);
					vx *= 0.01f;
					vy *= 0.01f;
					GameManager.getSingleton().getGameLogic().AddVehicle(event.getX(i), event.getY(i), vx, vy);
				}
			}
		}
		// dumpEvent(event);
		return true; // true since we have handled this MotionEvent
	}

	// **********************************************************************
	// This next section implements the 'SurfaceHolder.Callback' interface to
	// receive information about changes to the surface
	// **********************************************************************

	// This is called immediately after any structural changes (format or size)
	// have been made to the surface. You should at this point update the
	// imagery in the surface. This method is always called at least once, after
	// surfaceCreated(SurfaceHolder).
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	// This is called immediately after the surface is first created.
	// Implementations of this should start up whatever rendering code they
	// desire. Note that only one thread can ever draw into a Surface, so you
	// should not draw into the Surface here if your normal rendering will be in
	// another thread.
	public void surfaceCreated(SurfaceHolder holder) {

		thread = new GameThread(this);

		thread.setRunning(true);
		thread.start();
	}

	// This is called immediately before a surface is being destroyed. After
	// returning from this call, you should no longer try to access this
	// surface. If you have a rendering thread that directly accesses the
	// surface, you must ensure that thread is no longer touching the Surface
	// before returning from this function.
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "******* Surface is being destroyed");
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				Log.d(TAG, "thread.join()");
			}
		}
		Log.d(TAG, "******* Thread cleanly shut down");
	}

	/** Show an event in the LogCat view, for debugging */
	private void dumpEvent(MotionEvent event) {
		String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE", "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
		StringBuilder sb = new StringBuilder();
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		sb.append("event ACTION_").append(names[actionCode]);
		if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP) {
			sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
			sb.append(")");
		}
		sb.append("[");
		for (int i = 0; i < event.getPointerCount(); i++) {
			sb.append("#").append(i);
			sb.append("(pid ").append(event.getPointerId(i));
			sb.append(")=").append((int) event.getX(i));
			sb.append(",").append((int) event.getY(i));
			if (i + 1 < event.getPointerCount())
				sb.append(";");
		}
		sb.append("]");
		Log.d(TAG, sb.toString());
	}
}
