package entities;

import java.util.ArrayList;
import java.util.List;
import java.security.SecureRandom;

public class Deck {
	private Card[] deck;
	private String vira;
	private int currentCard;
	private static final int NUMBER_OF_CARDS = 40;
	private static final SecureRandom randomNumbers = new SecureRandom();

	public Deck() {
		String[] faces = { "As", "Dois", "Tres", "Quatro", "Cinco", "Seis", "Sete", "Valete", "Dama", "Rei" };
		String[] suits = { "Copas", "Ouro", "Paus", "Espadas" };
		deck = new Card[NUMBER_OF_CARDS];
		currentCard = 0;

		for (int count = 0; count < deck.length; count++)
			deck[count] = new Card(faces[count % 10], suits[count / 10]);
	}

	public void shuffle() {
		currentCard = 0;

		for (int first = 0; first < deck.length; first++) {
			int second = randomNumbers.nextInt(NUMBER_OF_CARDS);
			Card temp = deck[first];
			deck[first] = deck[second];
			deck[second] = temp;
		}
	}

	public String getVira() {
		if (vira == null) {
			vira = dealCard().toString();
		}
		return vira;
	}

	public String[] manilha(String vira) {
		String manilha = "";
		switch (vira) {
		case "As":
			manilha = "Dois";
			break;
		case "Dois":
			manilha = "Tres";
			break;
		case "Tres":
			manilha = "Quatro";
			break;
		case "Quatro":
			manilha = "Cinco";
			break;
		case "Cinco":
			manilha = "Seis";
			break;
		case "Seis":
			manilha = "Sete";
			break;
		case "Sete":
			manilha = "Dama";
			break;
		case "Dama":
			manilha = "Valete";
			break;
		case "Valete":
			manilha = "Rei";
			break;
		case "Rei":
			manilha = "As";
			break;
		}
		return new String[] { manilha + " de Copas", manilha + " de Ouro", manilha + " de Paus",
				manilha + "de Espadas" };
	}

	public List<Card> Mao(int num) {
		List<Card> mao = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			mao.add(dealCard());
		}
		return mao;
	}

	public Card dealCard() {
		if (currentCard < deck.length)
			return deck[currentCard++];
		else
			return null;
	}

	public int getCardValue(Card card, int currentScore) {
		String face = card.toString().split(" ")[0];
		int cardValue;

		switch (face) {
		case "As":
			cardValue = 8;
			break;
		case "Dois":
			cardValue = 9;
			break;
		case "Tres":
			cardValue = 10;
			break;
		case "Quatro":
			cardValue = 1;
			break;
		case "Cinco":
			cardValue = 2;
			break;
		case "Seis":
			cardValue = 3;
			break;
		case "Sete":
			cardValue = 4;
			break;
		case "Valete":
			cardValue = 6;
			break;
		case "Dama":
			cardValue = 5;
			break;
		case "Rei":
			cardValue = 7;
			break;
		default:
			cardValue = 0;
			break;
		}
		return cardValue;
	}

	public int manilhaValue(Card card, String[] manilha) {
		int cardValue = 0;
		String suits = card.toString().split(" ")[0];

		switch (suits) {
		case "Copas":
			cardValue = 90;
			break;
		case "Ouro":
			cardValue = 80;
		case "Paus":
			cardValue = 100;
		case "Espadas":
			cardValue = 70;
			break;
		}

		return cardValue;
	}
}
