
package thakur.rahul.colourmemory.controller;

import thakur.rahul.colourmemory.view.CardView;
import android.app.Activity;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.widget.ImageView;

public class CardController {

	private Activity activity;
	private CardView cardView;
	private SparseArray<ImageView> cardViewMap;
	private SparseIntArray cardSwapMap;
	private volatile AnimationController animationController;
	private volatile GameController gameController;
	private Thread animationThread;

	public CardController(Activity activity) {

		this.activity = activity;
		cardView = new CardView(activity);
		cardViewMap = cardView.getCardViewMap();
		cardSwapMap = cardView.getCardSwapMap();
		new InitialBoardSetupController(cardView.getCardViewArray(), activity.getResources()).generateNewGrid();
	}

	public void startAnimation(ImageView image1) {

		ImageView image2 = cardViewMap.get(cardSwapMap.get(image1.getId()));
		animationController = new AnimationController(activity, image1, image2);
		animationThread = new Thread(animationController);
		animationThread.start();
	}

	public void firstImage(ImageView image0) {

		startAnimation(image0);
		gameController = new GameController();
		gameController.setFirstImage(cardViewMap.get(cardSwapMap.get(image0.getId())));
		image0.setClickable(false);
	}

	public void checkForMatch(ImageView image0) {

		startAnimation(image0);
		gameController.setSecondImage(cardViewMap.get(cardSwapMap.get(image0.getId())));
		if (gameController.isNotAMatch()) {
			startAnimation(gameController.getFirstImage());
			cardViewMap.get(cardSwapMap.get(gameController.getFirstImage().getId())).setClickable(true);
			startAnimation(gameController.getSecondImage());
		} else
			AnimationController.fixForMatch = true;
	}
}
