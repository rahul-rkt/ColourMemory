
package thakur.rahul.colourmemory.controller;

import thakur.rahul.colourmemory.view.Animation.DisplayNextView;
import thakur.rahul.colourmemory.view.Animation.Flip3dAnimation;
import android.app.Activity;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

public class AnimationController implements Runnable {

	private Activity activity;
	private ImageView image1;
	private ImageView image2;
	private boolean isFirstImage = true;
	private volatile static int numThread = 1;
	private volatile static int threadAllowedToRun = 1;
	private int myThreadID;
	private volatile static Object myLock = new Object();
	private volatile static boolean hasNotSlept = true;

	public AnimationController(Activity activity, ImageView image1, ImageView image2) {

		this.activity = activity;
		this.image1 = image1;
		this.image2 = image2;
		this.myThreadID = numThread++;
	}

	@Override
	public void run() {

		synchronized (myLock) {
			while (myThreadID != threadAllowedToRun)
				try {
					myLock.wait();
				} catch (InterruptedException e) {
				} catch (Exception e) {
				}
			animate();
			if (myThreadID % 2 == 0)
				if (hasNotSlept)
					try {
						Thread.sleep(1000);
						hasNotSlept = !hasNotSlept;
					} catch (InterruptedException e) {
					}
				else
					hasNotSlept = !hasNotSlept;
			myLock.notifyAll();
			threadAllowedToRun++;
		}
	}

	public void animate() {

		if (isFirstImage) {
			applyRotation(0, 90);
			isFirstImage = !isFirstImage;
		} else {
			applyRotation(0, -90);
			isFirstImage = !isFirstImage;
		}
	}

	private void applyRotation(float start, float end) {

		final float centerX = image1.getWidth() / 2.0f;
		final float centerY = image1.getHeight() / 2.0f;
		final Flip3dAnimation rotation = new Flip3dAnimation(start, end, centerX, centerY);
		rotation.setDuration(300);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView(isFirstImage, image1, image2));
		if (isFirstImage)
			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {

					image1.startAnimation(rotation);
				}
			});
		else
			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {

					image2.startAnimation(rotation);
				}
			});
	}
}
