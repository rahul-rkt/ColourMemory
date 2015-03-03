
package thakur.rahul.colourmemory.controller;

import java.util.ArrayList;
import java.util.List;

import thakur.rahul.colourmemory.view.CardView;
import android.app.Activity;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.widget.ImageView;

public class CardController {

	private CardView cardView;
	private SparseArray<ImageView> cardViewMap;
	private SparseIntArray cardSwapMap;
	private AnimationController animationController;
	private GameController gameController;
	private List<Thread> animationThreads;

	public CardController(Activity activity) {

		cardView = new CardView(activity);
		cardViewMap = cardView.getCardViewMap();
		cardSwapMap = cardView.getCardSwapMap();
		new InitialBoardSetupController(cardView.getCardViewArray(), activity.getResources()).generateNewGrid();
		animationController = new AnimationController(activity);
		animationThreads = new ArrayList<Thread>();
	}

	public void startAnimation(final ImageView image1) {

		final ImageView image2 = cardViewMap.get(cardSwapMap.get(image1.getId()));
		Runnable animate = new Runnable() {

			@Override
			public void run() {

				animationController.animate(image1, image2);
			}
		};
		Thread animationThread = new Thread(animate);
		animationThread.start();
		while (animationThread.isAlive()) {
			// sit tight
		}
		/*		try {
					animationThread.join();
				} catch (InterruptedException e) {
					Log.d("ERROR", e.toString());
				}
		 */}

	public void firstImage(ImageView image0) {

		gameController = new GameController();
		gameController.setFirstImage(cardViewMap.get(cardSwapMap.get(image0.getId())));
	}

	public void checkForMatch(ImageView image0) {

		gameController.setSecondImage(cardViewMap.get(cardSwapMap.get(image0.getId())));
		if (gameController.isMatch()) {
			//
		} else {
			startAnimation(gameController.getFirstImage());
			cardViewMap.get(cardSwapMap.get(gameController.getFirstImage().getId())).setClickable(true);
			startAnimation(gameController.getSecondImage());
			cardViewMap.get(cardSwapMap.get(image0.getId())).setClickable(true);
		}
	}
}