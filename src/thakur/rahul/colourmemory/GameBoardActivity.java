
package thakur.rahul.colourmemory;

import java.util.List;

import thakur.rahul.colourmemory.controller.CardController;
import thakur.rahul.colourmemory.controller.FragmentController;
import thakur.rahul.colourmemory.model.ScoreModel;
import thakur.rahul.colourmemory.model.ScorePojo;
import thakur.rahul.colourmemory.view.HighScoreListFragment.HighScoreListFragmentToActivityCommunicator;
import thakur.rahul.colourmemory.view.NewHighScoreFragment.NewHighScoreFragmentToActivityCommunicator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Activity where the game runs. Handles onClick events for all turns. Shows high score list. Delegates new high score
 * if any.
 *
 * @author rahulthakur
 */
public class GameBoardActivity extends Activity implements NewHighScoreFragmentToActivityCommunicator, HighScoreListFragmentToActivityCommunicator {

	private CardController cardController;
	private boolean isFirstImage;
	private int finalScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_board);
		cardController = new CardController(this);
		isFirstImage = true;
	}

	/**
	 * onClickMethod which shows the high score list as a fragment.
	 */
	public void showScores(View v) {

		new FragmentController(this).showHighScoreList(R.id.fragment_container_high_score_list_gameboard);
	}

	/**
	 * onClickMethod associated with every ImageView in the <i>include_grid_0.xml</i> layout file
	 */
	public void imageClick(View v) {

		if (isFirstImage) {
			cardController.firstSelection((ImageView) v);
			isFirstImage = !isFirstImage;
		} else {
			cardController.secondSelection((ImageView) v);
			isFirstImage = !isFirstImage;
		}
		if (cardController.hasGameEnded()) {
			finalScore = cardController.getFinalScore();
			List<ScorePojo> scoresList = ScoreModel.getScoresList();
			if (scoresList.size() > 10)
				if (finalScore < ScoreModel.getCurrentMinimumScoreInDB())
					return;
			new FragmentController(this).showNewHighScoreInput(R.id.fragment_container_new_high_score);
		}
	}

	@Override
	public void getPlayerName(String playerName) {

		ScoreModel.addScore(playerName, finalScore);
		new FragmentController(this).showHighScoreList(R.id.fragment_container_high_score_list_gameboard, true);
	}

	@Override
	public void hasDetached() {

		this.finish();
	}
}
