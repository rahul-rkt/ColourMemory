
package thakur.rahul.colourmemory.model;

import thakur.rahul.colourmemory.R;
import android.util.SparseArray;

public enum CardModel {
	CARD1(1, R.drawable.colour1),
	CARD2(2, R.drawable.colour2),
	CARD3(3, R.drawable.colour3),
	CARD4(4, R.drawable.colour4),
	CARD5(5, R.drawable.colour5),
	CARD6(6, R.drawable.colour6),
	CARD7(7, R.drawable.colour7),
	CARD8(8, R.drawable.colour8);

	private static final SparseArray<CardModel> byId = new SparseArray<CardModel>();
	static {
		for (CardModel c : CardModel.values())
			byId.put(c.getId(), c);
	}
	private int id;
	private int type;

	public static CardModel getById(int id) {

		return byId.get(id);
	}

	CardModel(int id, int type) {

		this.id = id;
		this.type = type;
	}

	public int getType() {

		return type;
	}

	public int getId() {

		return id;
	}
}