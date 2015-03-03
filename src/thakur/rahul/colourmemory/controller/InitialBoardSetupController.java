
package thakur.rahul.colourmemory.controller;

import java.util.Random;

import thakur.rahul.colourmemory.model.CardModel;
import android.content.res.Resources;
import android.util.SparseIntArray;
import android.widget.ImageView;

public class InitialBoardSetupController {

	private Resources resources;
	private ImageView[][][] cardViewArray;
	private SparseIntArray cardCounter;
	private int randomCardID;
	private Random randomGenerator;

	public InitialBoardSetupController(ImageView[][][] cardViewArray, Resources resources) {

		this.cardViewArray = cardViewArray;
		this.resources = resources;
		cardCounter = new SparseIntArray();
		randomGenerator = new Random();
	}

	public void generateNewGrid() {

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				int cardDrawableID = CardModel.getById(randomiser()).getType();
				cardViewArray[i][j][1].setImageDrawable(resources.getDrawable(cardDrawableID));
				cardViewArray[i][j][1].setTag(cardDrawableID);
			}
	}

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
