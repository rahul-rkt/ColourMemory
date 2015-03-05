
package thakur.rahul.colourmemory.view.Animation;

import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public final class SwapViews implements Runnable {

	private boolean mIsFirstView;
	private ImageView image1;
	private ImageView image2;

	public SwapViews(boolean isFirstView, ImageView image1, ImageView image2) {

		mIsFirstView = isFirstView;
		this.image1 = image1;
		this.image2 = image2;
	}

	@Override
	public void run() {

		final float centerX = image1.getWidth() / 2.0f;
		final float centerY = image1.getHeight() / 2.0f;
		Flip3dAnimation rotation;
		if (mIsFirstView) {
			image1.setVisibility(View.INVISIBLE);
			image2.setVisibility(View.VISIBLE);
			image2.requestFocus();
			rotation = new Flip3dAnimation( -90, 0, centerX, centerY);
		} else {
			image2.setVisibility(View.INVISIBLE);
			image1.setVisibility(View.VISIBLE);
			image1.requestFocus();
			rotation = new Flip3dAnimation(90, 0, centerX, centerY);
		}
		rotation.setDuration(300);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new DecelerateInterpolator());
		if (mIsFirstView)
			image2.startAnimation(rotation);
		else
			image1.startAnimation(rotation);
	}
}