package src;

public class Hero implements Comparable<Hero> {
	public String date;
	public int score;

	public Hero(int score, String date) {
		this.score = score;
		this.date = date;

	}

	public Hero(String data) {
		String[] arr = data.split("\\s+");//多空格正则式
		this.score = Integer.valueOf(arr[0]);
		this.date = arr[1];
	}

	public String toString() {
		return score + "      " + date;
	}

	public int compareTo(Hero hero) {
		if (this.score > hero.score)
			return -1;
		else if (this.score < hero.score)
			return 1;
		else
			return 0;
	}
}
