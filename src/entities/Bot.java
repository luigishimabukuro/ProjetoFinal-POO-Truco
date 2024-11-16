package entities;

import java.security.SecureRandom;
import java.util.ArrayList;

public class Bot {

	public ArrayList<Card> maodoBot = new ArrayList<>();
	private int cards_value;

	public ArrayList<Card> getBotHand() {
		return maodoBot;
	}

	public void setBotHand(Deck Deck) { // Seta a mão do bot com 3 cartas.
		maodoBot.add(Deck.dealCard());
		maodoBot.add(Deck.dealCard());
		maodoBot.add(Deck.dealCard());
		;
	}

	public int getValue() {
		return cards_value;
	}

	public int jogarCarta(ArrayList<Card> maodoBot) { // Joga uma carta aleatória do bot.
		SecureRandom random = new SecureRandom();
		int escolha = random.nextInt(maodoBot.size());
		Card cartaEscolhida = maodoBot.remove(escolha);
		System.out.println("\n\tO Bot jogou " + cartaEscolhida);
		int valorCarta = cartaEscolhida.calculateCardValue();
		return valorCarta;
	}

	public boolean aceitarTruco() { // Define aleatoriamente se o bot aceita ou não o truco (50/50).
		SecureRandom random = new SecureRandom();
		boolean resp = random.nextBoolean();
		if (resp) {
			System.out.println("\n\tO Bot aceitou o truco!!");
		} else {
			System.out.println("\n\tO Bot não aceitou o truco!!");
		}
		return resp;
	}

	public boolean pedirTruco() { // Define aleatoriamente se o bot pede ou não o truco (50/50).
		SecureRandom random = new SecureRandom();
		return random.nextBoolean();
	}

	public boolean pedir6() {
		SecureRandom random = new SecureRandom();
		boolean resp = random.nextBoolean();
		if (resp) {
			System.out.println("\n\tO Bot pediu 6!!");
		}
		return resp;
	}

	public boolean pedir9() {
		SecureRandom random = new SecureRandom();
		boolean resp = random.nextBoolean();
		if (resp) {
			System.out.println("\n\tO Bot pediu 9!!");
		}
		return resp;
	}

	public boolean pedir12() {
		SecureRandom random = new SecureRandom();
		boolean resp = random.nextBoolean();
		if (resp) {
			System.out.println("\n\tO Bot pediu 12!!");
		}
		return resp;
	}

	public boolean aceitar6() {
		SecureRandom random = new SecureRandom();
		boolean resp = random.nextBoolean();
		if (resp) {
			System.out.println("\n\tO Bot aceitou o 6!!");
		}
		return resp;
	}

	public boolean aceitar9() {
		SecureRandom random = new SecureRandom();
		boolean resp = random.nextBoolean();
		if (resp) {
			System.out.println("\n\tO Bot aceitou o 9!!");
		}
		return resp;
	}

	public boolean aceitar12() {
		SecureRandom random = new SecureRandom();
		boolean resp = random.nextBoolean();
		if (resp) {
			System.out.println("\n\tO Bot aceitou o 12!!");
		}
		return resp;
	}

	public void botTurn() { // Vez do Bot jogar.
		System.out.println("\n\tVez do bot.");
		pedirTruco();
		jogarCarta(maodoBot);
	}

	public void reset() { // Limpa a mão do Bot para inserir a nova mão.
		this.maodoBot.clear();
	}
}
