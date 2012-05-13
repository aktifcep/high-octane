/**
 * 
 */
package android.game.highoctane;

import android.util.Log;

public class GameThread extends Thread {

	//GameManager gameManager;
	private static final String TAG = GameThread.class.getSimpleName();
	private boolean running;
	
	// this is called from surfaceCreated
	public GameThread(GameSurfaceView gamePanel) {
		super();
		
		//gameManager = GameManager.getSingleton();
		GameManager.getSingleton().setGamePanel(gamePanel);
		
		Vehicle.SetUp(gamePanel.getWidth(), gamePanel.getHeight());
		
		Log.d(TAG, "active threads = " + Thread.activeCount());
		Log.d(TAG, "active Priority = " + this.getPriority());
		Log.d(TAG, "max Priority = " + Thread.MAX_PRIORITY);
		Log.d(TAG, "min Priority = " + Thread.MIN_PRIORITY);
	}
	
	@Override
	public void run() {
		Log.d(TAG, "Starting GameThread");
		while (running) {
			GameManager.getSingleton().ProcessGame();
		}
		Log.d(TAG, "Finnished GameThread");
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
