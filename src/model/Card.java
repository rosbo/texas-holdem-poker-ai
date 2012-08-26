package model;

public class Card {

	private String suit;
	private int value;
	
	public Card(String suit, int value) {
		this.suit = suit;
		this.value = value;
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		String s = "";
		String[] names = new String[4];
		names[0] = "jack";
		names[1] = "queen";
		names[2] = "king";
		names[3] = "ace";
		if (this.value < 10)
			//s= "Card [suit=" + suit + ", value=" + value + "]";
			s = this.suit + " " + this.value;
		else
			s = this.suit + " " + names[this.value-10];
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (suit == null) {
			if (other.suit != null)
				return false;
		} else if (!suit.equals(other.suit))
			return false;
		if (value != other.value)
			return false;
		return true;
	}



}
