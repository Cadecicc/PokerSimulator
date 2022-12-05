import java.util.Scanner;

public class PokerSimulator {
	
	// Constants to define the various hand types
	public static final int HIGH_CARD = 0;
	public static final int ONE_PAIR = 1;
	public static final int TWO_PAIR = 2;
	public static final int THREE_OF_A_KIND = 3;
	public static final int STRAIGHT = 4;
	public static final int FLUSH = 5;
	public static final int FULL_HOUSE = 6;
	public static final int FOUR_OF_A_KIND = 7;
	public static final int STRAIGHT_FLUSH = 8;
	public static final int ROYAL_FLUSH = 9;

	public static void main(String[] args) {
		// Asks the user how many hands they want tested
		Scanner kb = new Scanner(System.in);
		System.out.print("How many poker hands should I deal? ");
		int runs = kb.nextInt();
		
		// Gets the start time of the simulations
		long start = System.nanoTime();
		
		// Runs the simulations and closes the scanner
		displayStats(runSimulations(runs));
		kb.close();
		
		// Calculates the end time and finds the difference between it
		// and the start time in seconds
		long end = System.nanoTime();
		float totalTime = end - start;
		float seconds = totalTime/1000000000;
		
		// Prints the last line of the output
		System.out.print(runs + " hands were analyzed in " + seconds + " seconds");
	}
	
	// Bubble sort of the hand
	public static void sort(Card[] hand) {
		boolean madeSwap = true;
		while (madeSwap) {
			madeSwap = false;
			for (int i = 0; i < 4; i++) {
				if (hand[i].getValue() > hand[i + 1].getValue()) {
					Card temp = new Card(hand[i]);
					hand[i] = hand[i + 1];
					hand[i + 1] = temp;
					madeSwap = true;
				}
			}
		}
	}
	
	// Deals a random, sorted poker hand
	public static Card[] randomSortedHand() {
		PokerDeck deck = new PokerDeck();
		deck.shuffle();
		
		Card[] hand = new Card[5];
		
		for (int i = 0; i < 5; i++) {
			hand[i] = deck.deal();
		}
		sort(hand);
		return hand;
	}
	
	// Turns the hand to a string that is easy for the user to read
	public static String makeString(Card[] hand) {
		String s = "";
		for (int i = 0; i < hand.length; i++) {
			s += hand[i].toString() + " ";
		}
		return s;
	}
	
	// Actually runs the simulations and calculates the totals
	// of each hand type
	public static int[] runSimulations(int n) {
		int highCardCount = 0;
		int onePairCount = 0;
		int twoPairCount = 0;
		int threeOfAKindCount = 0;
		int straightCount = 0;
		int flushCount = 0;
		int fullHouseCount = 0;
		int fourOfAKindCount = 0;
		int straightFlushCount = 0;
		int royalFlushCount = 0;
		
		for (int i = 0; i < n; i++) {
			Card[] hand = randomSortedHand();
			
			if (evaluateOnePokerHand(hand) == ROYAL_FLUSH) {
				royalFlushCount++;
			} else if (evaluateOnePokerHand(hand) == STRAIGHT_FLUSH) {
				straightFlushCount++;
			} else if (evaluateOnePokerHand(hand) == FOUR_OF_A_KIND) {
				fourOfAKindCount++;
			} else if (evaluateOnePokerHand(hand) == FULL_HOUSE) {
				fullHouseCount++;
			} else if (evaluateOnePokerHand(hand) == FLUSH) {
				flushCount++;
			} else if (evaluateOnePokerHand(hand) == STRAIGHT) {
				straightCount++;
			} else if (evaluateOnePokerHand(hand) == THREE_OF_A_KIND) {
				threeOfAKindCount++;
			} else if (evaluateOnePokerHand(hand) == TWO_PAIR) {
				twoPairCount++;
			} else if (evaluateOnePokerHand(hand) == ONE_PAIR) {
				onePairCount++;
			} else if (evaluateOnePokerHand(hand) == HIGH_CARD) {
				highCardCount++;
			}
		}
		
		int[] results = {
				highCardCount,
				onePairCount,
				twoPairCount,
				threeOfAKindCount,
				straightCount,
				flushCount,
				fullHouseCount,
				fourOfAKindCount,
				straightFlushCount,
				royalFlushCount 
		};
		
		return results;
	}
	
	// Displays the stats found from the simulations
	public static void displayStats(int[] stats) {
		
		for (int j = 0; j < 40; j++) {
			System.out.print("-");
		}
		System.out.println();
		
		double runs = 0.0;
		for (int i = 0; i < stats.length; i++) {
			runs += stats[i];
		}
		
		runs /= 100.0;
		
		String s = "High Card";
		int d = stats[0];
		System.out.printf("%16s: %8d %10.5f%%%n", s, d, d / runs);
		System.out.println();
		s = "One Pair";
		d = stats[1];
		System.out.printf("%16s: %8d %10.5f%%%n", s, d, d / runs);
		System.out.println();
		s = "Two Pair";
		d = stats[2];
		System.out.printf("%16s: %8d %10.5f%%%n", s, d, d / runs);
		System.out.println();
		s = "Three of a Kind";
		d = stats[3];
		System.out.printf("%16s: %8d %10.5f%%%n", s, d, d / runs);
		System.out.println();
		s = "Straight";
		d = stats[4];
		System.out.printf("%16s: %8d %10.5f%%%n", s, d, d / runs);
		System.out.println();
		s = "Flush";
		d = stats[5];
		System.out.printf("%16s: %8d %10.5f%%%n", s, d, d / runs);
		System.out.println();
		s = "Full House";
		d = stats[6];
		System.out.printf("%16s: %8d %10.5f%%%n", s, d, d / runs);
		System.out.println();
		s = "Four of a Kind";
		d = stats[7];
		System.out.printf("%16s: %8d %10.5f%%%n", s, d, d / runs);
		System.out.println();
		s = "Straight Flush";
		d = stats[8];
		System.out.printf("%16s: %8d %10.5f%%%n", s, d, d / runs);
		System.out.println();
		s = "Royal Flush";
		d = stats[9];
		System.out.printf("%16s: %8d %10.5f%%%n", s, d, d / runs);
		
		for (int j = 0; j < 40; j++) {
			System.out.print("-");
		}
		
		System.out.println();
	}
	
	// Evaluates the hand type of a single poker hand
	public static int evaluateOnePokerHand(Card[] hand) {
		
		if (hasRoyalFlush(hand)) {
			return ROYAL_FLUSH;
		} else if (hasStraightFlush(hand)) {
			return STRAIGHT_FLUSH;
		} else if (hasFourOfAKind(hand)) {
			return FOUR_OF_A_KIND;
		} else if (hasFullHouse(hand)) {
			return FULL_HOUSE;
		} else if (hasFlush(hand)) {
			return FLUSH;
		} else if (hasStraight(hand)) {
			return STRAIGHT;
		} else if (hasThreeOfAKind(hand)) {
			return THREE_OF_A_KIND;
		} else if (hasTwoPair(hand)) {
			return TWO_PAIR;
		} else if (hasOnePair(hand)) {
			return ONE_PAIR;
		} else if (hasHighCard(hand)) {
			return HIGH_CARD;
		}
		return -1;
	}
	
	// Since this is last in the evaluateOnePokerHand() method,
	// we can just return true
	public static boolean hasHighCard(Card[] hand) {
		return true;
	}
	
	// Checks if the hand has one pair
	public static boolean hasOnePair(Card[] hand) {
		int pairCount = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = i + 1; j < 4; j++)
			if (hand[i].getValue() == hand[j].getValue()) {
				pairCount++;
			}
		}
		if (pairCount == 1) {
			return true;
		}
		return false;
	}
	
	// Checks if the hand has two pairs
	public static boolean hasTwoPair(Card[] hand) {
		int pairCount = 0;
		for (int i = 0; i < 4; i++) {
			if (hand[i].getValue() == hand[i + 1].getValue()) {
				pairCount++;
			}
		}
		if (pairCount == 2) {
			return true;
		}
		return false;
	}
	
	// Checks if the hand has three of a kind
	public static boolean hasThreeOfAKind(Card[] hand) {
		int count = 0;
		for (int i = 0; i < 1; i++) {
			for (int j = i + 1; j < 5; j++) {
				if (hand[i].getValue() == hand[j].getValue()) {
					count++;
				}
			}
		}
		if (count == 2) {
			return true;
		}
		return false;
	}
	
	// Checks if the hand contains a straight
	public static boolean hasStraight(Card[] hand) {
		int count = 0;
		for (int i = 0; i < 4; i++) {
			if (hand[i].getValue() + 1 == hand[i + 1].getValue()) {
				count++;
			}
		}
		if (count == 4) {
			return true;
		}
		return false;
	}
	
	// Checks if the hand contains a flush
	public static boolean hasFlush(Card[] hand) {
		int count = 0;
		for (int i = 1; i < 5; i++) {
			if (hand[0].getSuit() == hand[i].getSuit()) {
				count++;
			}
		}
		if (count == 4) {
			return true;
		}
		return false;
	}
	
	// Checks if the hand contains a full house
	public static boolean hasFullHouse(Card[] hand) {
		int count = 0;
		if (hand[0].getValue() == hand[1].getValue() && hand[1].getValue() == hand[2].getValue()) {
			for (int i = 0; i < 2; i++) {
				if (hand[i].getValue() == hand[i + 1].getValue()) {
					count++;
				}
			}
			for (int i = 3; i < 4; i++) {
				if (hand[i].getValue() == hand[i + 1].getValue()) {
					count++;
				}
			}
		}
		if (hand[0].getValue() == hand[1].getValue() && hand[1].getValue() != hand[2].getValue()) {
			for (int i = 0; i < 1; i++) {
				if (hand[i].getValue() == hand[i + 1].getValue()) {
					count++;
				}
			}
			for (int i = 2; i < 4; i++) {
				if (hand[i].getValue() == hand[i + 1].getValue()) {
					count++;
				}
			}
		}
		if (count == 3) {
			return true;
		}
		return false;
	}
	
	// Checks if the hand has four of a kind
	public static boolean hasFourOfAKind(Card[] hand) {
		int count = 0;
		for (int i = 0; i < 1; i++) {
			for (int j = i + 1; j < 5; j++) {
				if (hand[i].getValue() == hand[j].getValue()) {
					count++;
				}
			}
		}
		if (count == 3) {
			return true;
		}
		return false;
	}
	
	// Checks if the hand has a straight flush
	public static boolean hasStraightFlush(Card[] hand) {
		if (hasStraight(hand) && hasFlush(hand)) {
			return true;
		}
		return false;
	}
	
	// Checks if the hand contains a royal flush, with ace being high card
	public static boolean hasRoyalFlush(Card[] hand) {
		int count = 0;
		if (!hasFlush(hand)) {
			return false;
		}
		for (int i = 1; i < 4; i++) {
			if (hand[0].getValue() == 1 && hand[1].getValue() == 10 && hand[i].getValue() + 1 == hand[i + 1].getValue()) {
				count++;
			}
		}
		if (count == 3) {
			return true;
		}
		return false;
	}
}
