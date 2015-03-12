
package thakur.rahul.colourmemory;

import thakur.rahul.colourmemory.controller.FragmentController;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * <b>ColourMemory Main Activity</b><br>
 * Starting point of the application. Shows main menu and the high score list.<br>
 * <br>
 * <b>API:</b> 16+ <i>(Android v4.1.1)</i><br>
 * <b>Build Tool:</b> 21.1.2
 *
 * @author rahulthakur
 */
public class MainActivity extends Activity {

	private static volatile Context appContext;

	/**
	 * Returns application context.
	 */
	public static Context getAppContext() {

		return appContext;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		appContext = this.getApplicationContext();
	}

	/**
	 * onClickMethod which starts the game in a new activity.
	 */
	public void startGame(View v) {

		Intent gameBoardActivity = new Intent(this, GameBoardActivity.class);
		startActivity(gameBoardActivity);
	}

	/**
	 * onClickMethod which shows the high score list as a fragment.
	 */
	public void showScores(View v) {

		Log.d("DD", "MainActivity showScores");
		new FragmentController(this).showHighScoreList(R.id.fragment_container_high_score_list_main);
	}

	/**
	 * onClickMethod which ends the game and quits to android.
	 */
	public void closeApplication(View v) {

		finish();
		System.exit(0);
	}
}
