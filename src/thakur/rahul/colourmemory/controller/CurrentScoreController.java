
package thakur.rahul.colourmemory.controller;

import thakur.rahul.colourmemory.R;
import android.app.Activity;
import android.widget.Button;

public class CurrentScoreController {

	private Activity activity;
	private Button currentScoreButton;
	private int score;

	public CurrentScoreController(Activity activity, int score) {

		this.activity = activity;
		currentScoreButton = (Button) activity.findViewById(R.id.currentScoreButton);
		this.score = score;
	}

	public void updateCurrentScore() {

		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {

				currentScoreButton.setText(String.valueOf(score));
			}
		});
	}
}
