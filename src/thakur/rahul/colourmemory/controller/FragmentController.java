
package thakur.rahul.colourmemory.controller;

import thakur.rahul.colourmemory.view.HighScoreListFragment;
import thakur.rahul.colourmemory.view.NewHighScoreFragment;
import android.app.Activity;
import android.app.Fragment;

/**
 * Handles fragment creation and display.
 *
 * @author rahulthakur
 */
public class FragmentController {

	public static final String FRAGMENT_TAG_HIGH_SCORE_LIST = "HighScoreList";
	public static final String FRAGMENT_TAG_NEW_HIGH_SCORE = "NewHighScore";
	private Activity activity;

	public FragmentController(Activity activity) {

		this.activity = activity;
	}

	/**
	 * Attaches High Score List Fragment to the supplied container. Notifies parent activity on fragment detach.
	 */
	public void showHighScoreList(int containerID, boolean notifyOnDetach) {

		activity.getFragmentManager()
		        .beginTransaction()
		        .add(containerID, HighScoreListFragment.instantiate(notifyOnDetach), FRAGMENT_TAG_HIGH_SCORE_LIST)
		        .addToBackStack(null)
		        .commit();
	}

	/**
	 * Attaches High Score List Fragment to the supplied container. Does not notify parent activity on fragment detach.
	 *
	 * @see #showHighScoreList(int, boolean)
	 */
	public void showHighScoreList(int containerID) {

		activity.getFragmentManager()
		        .beginTransaction()
		        .add(containerID, HighScoreListFragment.instantiate(false), FRAGMENT_TAG_HIGH_SCORE_LIST)
		        .addToBackStack(null)
		        .commit();
	}

	/**
	 * Attaches New High Score Fragment to the supplied container.
	 */
	public void showNewHighScoreInput(int containerID) {

		activity.getFragmentManager()
		        .beginTransaction()
		        .add(containerID, Fragment.instantiate(activity, NewHighScoreFragment.class.getName()), FRAGMENT_TAG_NEW_HIGH_SCORE)
		        .commit();
	}
}
