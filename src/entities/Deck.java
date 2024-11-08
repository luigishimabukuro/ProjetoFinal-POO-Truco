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

	public void reset() {
		String[] faces = { "As", "Dois", "Tres", "Quatro", "Cinco", "Seis", "Sete", "Valete", "Dama", "Rei" };
		String[] suits = { "Copas", "Ouro", "Paus", "Espadas" };
		for (int count = 0; count < deck.length; count++) {
			deck[count] = new Card(faces[count % 10], suits[count / 10]);
		}
		currentCard = 0;
		vira = null;
		shuffle();
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

	public String manilha(String vira) {
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
		return manilha;
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

	public int getCardValue(Card card) {
		String[] parts = card.toString().split(" de ");
		String face = parts[0];
		String suit = parts[1];
		String manilhaFace = manilha(getVira());

		int faceValue;
		int suitValue = 0;

		switch (face) {
		case "As":
			faceValue = 8;
			break;
		case "Dois":
			faceValue = 9;
			break;
		case "Tres":
			faceValue = 10;
			break;
		case "Quatro":
			faceValue = 1;
			break;
		case "Cinco":
			faceValue = 2;
			break;
		case "Seis":
			faceValue = 3;
			break;
		case "Sete":
			faceValue = 4;
			break;
		case "Dama":
			faceValue = 5;
			break;
		case "Valete":
			faceValue = 6;
			break;
		case "Rei":
			faceValue = 7;
			break;
		default:
			faceValue = 0;
			break;
		}

		if (face.equals(manilhaFace)) {
			switch (suit) {
			case "Copas":
				suitValue = 10;
				break;
			case "Ouro":
				suitValue = 20;
				break;
			case "Paus":
				suitValue = 30;
				break;
			case "Espadas":
				suitValue = 40;
				break;
			default:
				suitValue = 0;
				break;
			}
		}
		return faceValue + suitValue;
	}

}