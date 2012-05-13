package android.game.highoctane;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class GameLogic {

	private static final String TAG = GameSurfaceView.class.getSimpleName();
	
	List<Vehicle> vehicleContainer = new ArrayList<Vehicle>();

	GameLogic() {
		
		Log.d(TAG, "+++  GameLogic instance created");
		
		AddVehicle(100.0f, 100.0f, 4, 0);
//		AddVehicle(100.0f, 100.0f, 0, 4);
//		AddVehicle(100.0f, 100.0f, 4, 6);
//		AddVehicle(120.0f, 160.0f, -2, 7);
//		AddVehicle(233.0f, 320.0f, -2.0f, -1.2f);
	}

	public void DoLogic() {

		synchronized (vehicleContainer) {
			for (Vehicle iter : vehicleContainer) {
				iter.Move();
				iter.Bounce();
			}
		}
	}

	void AddVehicle( float x, float y, float mx, float my) {
		vehicleContainer.add(new Vehicle(x, y, mx, my));
	}
}
