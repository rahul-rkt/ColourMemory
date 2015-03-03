
package thakur.rahul.colourmemory;

import thakur.rahul.colourmemory.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void startGame(View v) {

		Intent i = new Intent(this, GameBoard.class);
		startActivity(i);
	}

	public void showScores(View v) {

	}

	public void closeApplication(View v) {

		finish();
		System.exit(0);
	}
}
