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

		int value;

		switch (face) {
		case "As":
			value = 8;
			break;
		case "Dois":
			value = 9;
			break;
		case "Tres":
			value = 10;
			break;
		case "Quatro":
			value = 1;
			break;
		case "Cinco":
			value = 2;
			break;
		case "Seis":
			value = 3;
			break;
		case "Sete":
			value = 4;
			break;
		case "Dama":
			value = 5;
			break;
		case "Valete":
			value = 6;
			break;
		case "Rei":
			value = 7;
			break;
		default:
			value = 0;
			break;
		}
		boolean manilha = eManilha(card);
		if (manilha) {
			System.out.println("\n\tEntrei pq é manilha");
			value += manilhaValue(card);
		}

		return value;
	}

	private boolean eManilha(Card card) {
		boolean eManilha = false;
		String[] parts = card.toString().split(" de ");
		String face = parts[0];
		String manilhaFace = manilha(getVira());
		if (face.equals(manilhaFace)) {
			eManilha = true;
		}
		eManilha = false;

		return eManilha;

	}

	public int manilhaValue(Card card) {
		String[] parts = card.toString().split(" de ");
		String suit = parts[1];
		int valor = 0;
		switch (suit) {
		case "Copas":
			valor = 30;
		case "Ouro":
			valor = 20;
		case "Paus":
			valor = 40;
		case "Espadas":
			valor = 10;
		default:
			valor = 0;
		}
		return valor;
	}

}