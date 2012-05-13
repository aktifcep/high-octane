package android.game.highoctane;

import android.util.Log;

public class Vehicle {
	private static final String TAG = HighOctaneActivity.class.getSimpleName();

	static int viewWidth;
	static int viewheight;

	private float x;
	private float y;
	private float mx;
	private float my;
	private float direction;
	private int frame;

	Vehicle(/*int frame,*/ float x, float y, float mx, float my) {
		this.x = x;
		this.y = y;
		this.mx = mx;
		this.my = my;
		//this.frame = frame;
	}

	static void SetUp(int w, int h) {
		viewWidth = w;
		viewheight = h;
	}

	void FrameDirection() {
		
		float ang = (float) Math.atan2(mx,my);
		this.frame = (int) ( 32.0f- (ang / (Math.PI/32))) ;
				
		//Log.d(TAG, " "+ this.frame);
	}
	
	void Move() {
		x += mx;
		y += my;
		mx *= 0.99995f;
		my *= 0.99995f;
		
		FrameDirection();
	}

	void Bounce() {
		if (x < 0) {
			mx = -mx;
		}
		if (y < 0) {
			my = -my;
		}
		if (x > viewWidth) {
			mx = -mx;
		}
		if (y > viewheight) {
			my = -my;
		}
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getMx() {
		return mx;
	}

	public void setMx(float mx) {
		this.mx = mx;
	}

	public float getMy() {
		return my;
	}

	public void setMy(float my) {
		this.my = my;
	}

	public float getDirection() {
		return direction;
	}

	public void setDirection(float direction) {
		this.direction = direction;
	}
}
