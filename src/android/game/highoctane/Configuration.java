package android.game.highoctane;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

public class Configuration {

	private static final String TAG = HighOctaneActivity.class.getSimpleName();
	static Properties properties = null;

	static void Init(Context context, String PropertiesFile) {
		
		Resources resources = context.getResources();
		AssetManager assetManager = resources.getAssets();

		properties = new Properties();
		try {
			InputStream inputStream = assetManager.open(PropertiesFile);
			properties.load(inputStream);
			Log.d(TAG, "===properties: " + properties);
		} catch (IOException e) {
			Log.d(TAG, "failed to find/open game.properties");
			e.printStackTrace();
		}
	}

	static int GetProperty(String Name, int DefaultValue) {
		int value = 0xbadf00d;
		if (properties!=null) {
			try {
				value = Integer.parseInt(properties.getProperty(Name));
			} catch (NumberFormatException nfe) {
				value = DefaultValue;
			}
		}
		else
		{
			Log.d(TAG, "Did you call init before using GetProperty() ?");
		}
		return value;	
	}
}
