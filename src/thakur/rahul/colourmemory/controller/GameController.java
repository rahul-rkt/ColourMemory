
package thakur.rahul.colourmemory.controller;

import android.widget.ImageView;

public class GameController {

	private ImageView image1, image2;
	private int image1Type, image2Type;

	public void setFirstImage(ImageView image1) {

		this.image1 = image1;
		image1Type = Integer.parseInt(image1.getTag().toString());
	}

	public ImageView getFirstImage() {

		return image1;
	}

	public void setSecondImage(ImageView image2) {

		this.image2 = image2;
		image2Type = Integer.parseInt(image2.getTag().toString());
	}

	public ImageView getSecondImage() {

		return image2;
	}

	public boolean isNotAMatch() {

		return !(image1Type == image2Type);
	}
}
