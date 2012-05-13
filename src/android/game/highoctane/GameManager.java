package android.game.highoctane;

public class GameManager {

	private static final String TAG = GameSurfaceView.class.getSimpleName();
	
	// --------------------------------------------
	// SINGLETON CLASS
	private static GameManager singleton = null;

	private GameManager() {
	} // avoids using default by mistake

	public static GameManager getSingleton() {
		if (singleton == null) {
			singleton = new GameManager();
		}
		return singleton;
	}
	// ------------------------------------------------
	
	ImageManager imageManager = new ImageManager();
	
	public ImageManager getImageManager() {
		return imageManager;
	}

	// ------------------------------------------------
	private GameSurfaceView gamePanel;

	public void setGamePanel(GameSurfaceView gamePanel) {
		this.gamePanel = gamePanel;
	}

	// ------------------------------------------------
	private GameLogic gameLogic = new GameLogic();

	public GameSurfaceView getGamePanel() {
		return gamePanel;
	}

	public GameLogic getGameLogic() {
		return gameLogic;
	}

	// ------------------------------------------------

	private final static int 	MAX_FPS = 60;
	private final static int	MAX_FRAME_SKIPS = 5;
	private final static int	FRAME_PERIOD = 1000 / MAX_FPS;	

	public void ProcessGame() {
		
		long startTime = System.currentTimeMillis();
		long skippedFrame = 0;
		
		gameLogic.DoLogic();
		gamePanel.DrawHelper();
		
		long timeTakenForLogicAndRender = System.currentTimeMillis() - startTime;
		int spareToleftOverTime = (int)(FRAME_PERIOD - timeTakenForLogicAndRender);

		if (spareToleftOverTime > 0) {
			try {
				Thread.sleep(spareToleftOverTime);
			} catch (InterruptedException e) {}
		}

		while ((spareToleftOverTime < 0) && (skippedFrame < MAX_FRAME_SKIPS)) {
			gameLogic.DoLogic(); // update just game logic without rendering the screen
			spareToleftOverTime += FRAME_PERIOD;  // attempt next frame
			skippedFrame++;
		}
	}
}
