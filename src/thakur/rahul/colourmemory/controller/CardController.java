
package thakur.rahul.colourmemory.controller;

import java.util.Random;

import thakur.rahul.colourmemory.model.CardModel;
import thakur.rahul.colourmemory.view.CardView;
import android.app.Activity;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.widget.ImageView;

/**
 * Creates new game. Delegates each turn. Maintains game state.
 *
 * @author rahulthakur
 */
public class CardController {

	private Activity activity;
	private ImageView[][][] cardViewArray;
	private SparseArray<ImageView> cardViewMap;
	private SparseIntArray cardSwapMap;
	private ImageView[][] turnKeeper;
	private int currentScore = 0;
	private int matchCounter = 8; // TODO change to 0

	public CardController(Activity activity) {

		this.activity = activity;
		CardView cardView = new CardView(activity);
		cardViewArray = cardView.getCardViewArray();
		cardViewMap = cardView.getCardViewMap();
		cardSwapMap = cardView.getCardSwapMap();
		turnKeeper = new ImageView[2][2];
		new GameBoardSetupHandler().makeNewGame();
	}

	/**
	 * Delegates first selection of each turn.
	 */
	public void firstSelection(ImageView cardFaceDown) {

		ImageView cardFaceUp = cardViewMap.get(cardSwapMap.get(cardFaceDown.getId()));
		turnKeeper[0][0] = cardFaceDown;
		turnKeeper[0][1] = cardFaceUp;
		new GamePlayController(activity, turnKeeper, null, GamePlayController.FIRST_MOVE).evaluate();
	}

	/**
	 * Delegates second selection of each turn.
	 */
	public void secondSelection(ImageView cardFaceDown) {

		ImageView cardFaceUp = cardViewMap.get(cardSwapMap.get(cardFaceDown.getId()));
		turnKeeper[1][0] = cardFaceDown;
		turnKeeper[1][1] = cardFaceUp;
		if (TextUtils.equals(turnKeeper[0][1].getTag().toString(), turnKeeper[1][1].getTag().toString())) {
			currentScore += 2;
			new GamePlayController(activity, turnKeeper, currentScore, GamePlayController.SECOND_MOVE_MATCHED).evaluate();
			matchCounter++;
		} else {
			currentScore--;
			new GamePlayController(activity, turnKeeper, currentScore, GamePlayController.SECOND_MOVE_MISMATCHED).evaluate();
		}
	}

	/**
	 * Checks if game has ended.
	 */
	public boolean hasGameEnded() {

		return matchCounter == 8 ? true : false;
	}

	/**
	 * Returns final score of the game once it has ended.
	 */
	public int getFinalScore() {

		if (hasGameEnded())
			return currentScore;
		return -666; // The Number of the Beast passed instead of throwing Exception. \m/
	}

	/**
	 * Randomises and makes new game.
	 *
	 * @author rahulthakur
	 */
	private class GameBoardSetupHandler {

		private SparseIntArray cardCounter;
		private int randomCardID;
		private Random randomGenerator;

		private GameBoardSetupHandler() {

			cardCounter = new SparseIntArray();
			randomGenerator = new Random();
		}

		/**
		 * Lays out cards for the new game layout.
		 */
		private void makeNewGame() {

			for (int i = 0; i < 4; i++)
				for (int j = 0; j < 4; j++) {
					int cardDrawableID = CardModel.getById(randomiser()).getType();
					cardViewArray[i][j][1].setImageDrawable(activity.getResources().getDrawable(cardDrawableID));
					// The drawable is set as the tag for easily retrieving the type
					cardViewArray[i][j][1].setTag(cardDrawableID);
				}
		}

		/**
		 * Randomises cards w.r.t. their IDs.
		 */
		private int randomiser() {

			int random = randomGenerator.nextInt(8) + 1;
			if (randomCardID != random) {
				randomCardID = random;
				if (cardCounter.indexOfKey(randomCardID) >= 0) {
					if (cardCounter.get(randomCardID) < 2) {
						cardCounter.put(randomCardID, cardCounter.get(randomCardID) + 1);
						return randomCardID;
					} else {
						randomCardID = randomiser();
						return randomCardID;
					}
				} else {
					cardCounter.put(randomCardID, 1);
					return randomCardID;
				}
			} else {
				randomCardID = randomiser();
				return randomCardID;
			}
		}
	}
}
