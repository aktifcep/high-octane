package android.game.highoctane;

import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;
import android.util.Log;

public class ImageManager {

	private static final String TAG = HighOctaneActivity.class.getSimpleName();

	List<Bank> bankContainer = new ArrayList<Bank>();

	int CreateBank(Bitmap bitmap, int cutOutWidth, int cutOutHeight) {
		Bank imageManagerBank = new Bank();
		imageManagerBank.CutOutFrames(bitmap, cutOutWidth, cutOutHeight);
		bankContainer.add(imageManagerBank);
		return bankContainer.size();
	}

	int AddLastBank(Bitmap bitmap, int cutOutWidth, int cutOutHeight) {
		if (bankContainer.size() == 0) {
			CreateBank(bitmap, cutOutWidth,cutOutHeight);
		} else {
			int index = bankContainer.size() - 1;
			Bank imageManagerBank = bankContainer.get(index);
			imageManagerBank.CutOutFrames(bitmap, cutOutWidth, cutOutHeight);
		}

		return bankContainer.size();
	}

	Bank GetBank(int index) {
		return bankContainer.get(index);
	}

	public class Bank {
		List<Bitmap> bitmapContainer = new ArrayList<Bitmap>();

		void AddImage(Bitmap bitmap) {
			bitmapContainer.add(bitmap);
		}

		Bitmap GetImage(int index) {
			return bitmapContainer.get(index);
		}

		void CutOutFrames(Bitmap bitmap, int cutOutWidth, int cutOutHeight) {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			for (int y = 0; y < height - cutOutHeight; y += cutOutHeight) {
				int x = 0;
				do {
					Bitmap newBitmap = Bitmap.createBitmap(bitmap, x, y, cutOutWidth, cutOutHeight);
					AddImage(newBitmap);
					x += cutOutWidth;
				} while (x < width);
			}
			Log.d(TAG, "new images added, total = " + bitmapContainer.size());
		}
	}

}
