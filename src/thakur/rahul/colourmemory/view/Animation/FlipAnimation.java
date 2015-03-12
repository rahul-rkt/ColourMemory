
package thakur.rahul.colourmemory.view.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Creates Flip Animation rotation.
 *
 * @author rahulthakur
 */
public class FlipAnimation extends Animation {

	private final float fromDegrees;
	private final float toDegrees;
	private final float centerX;
	private final float centerY;
	private Camera camera;

	public FlipAnimation(float fromDegrees, float toDegrees, float centerX, float centerY) {

		this.fromDegrees = fromDegrees;
		this.toDegrees = toDegrees;
		this.centerX = centerX;
		this.centerY = centerY;
	}

	@Override
	public void initialize(int width, int height, int parentWidth, int parentHeight) {

		super.initialize(width, height, parentWidth, parentHeight);
		camera = new Camera();
	}

	/**
	 * Applies transformation values w.r.t. a virtual camera
	 */
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {

		final float fromDegrees = this.fromDegrees;
		float degrees = fromDegrees + ((toDegrees - fromDegrees) * interpolatedTime);
		final float centerX = this.centerX;
		final float centerY = this.centerY;
		final Camera camera = this.camera;
		final Matrix matrix = t.getMatrix();
		camera.save();
		camera.rotateY(degrees);
		camera.getMatrix(matrix);
		camera.restore();
		matrix.preTranslate( -centerX, -centerY);
		matrix.postTranslate(centerX, centerY);
	}
}