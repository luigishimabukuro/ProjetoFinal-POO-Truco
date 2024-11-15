package entities;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {

	private ArrayList<Card> maodoJogador = new ArrayList<>();
	private String name;
	private int cards_value;

	public Player(Deck Deck, String name) {

		setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPlayerHand(Deck Deck) {
		maodoJogador.add(Deck.dealCard());
		maodoJogador.add(Deck.dealCard());
		maodoJogador.add(Deck.dealCard());

	}

	public ArrayList<Card> getPlayerHand() {
		return maodoJogador;
	}

	public int getValue() {
		return cards_value;
	}

	public int playerTurn(ArrayList<Card> PlayerDeck, Scanner sc) {
		int valor = 0;
		System.out.println(toString());
		int pos = 0;
		do {
			System.out.println("\n\tQual carta deseja jogar?");
			pos = sc.nextInt();
			sc.nextLine();
			if (pos < 1 || pos > PlayerDeck.size()) {
				System.out.println("\n\tOpção inválida!!");
			}
		} while (pos < 1 || pos > PlayerDeck.size());

		Card cartaEscolhida = PlayerDeck.remove(pos - 1);
		valor = cartaEscolhida.calculateCardValue();
		System.out.println("\n\tValor da Carta: " + valor);

		return valor;
	}

	public boolean truco(Scanner sc) {
		char resp;
		boolean truco = false;
		do {

			System.out.printf("\n\tDeseja trucar? (s/n): ");
			resp = sc.nextLine().charAt(0);
			if (resp == 's') {
				truco = true;
			} else if (resp == 'n') {
				truco = false;
			}
		} while (resp != 's' && resp != 'n');
		return truco;
	}

	public boolean aceitarTruco(Scanner scanner) {
		boolean aceitar = false;
		char resp = 0;
		do {
			System.out.println("\n\tAceita o truco?");
			resp = scanner.nextLine().charAt(0);
			if (resp == 's') {
				aceitar = true;
			} else if (resp == 'n') {
				aceitar = false;
			}
		} while (resp != 's' && resp != 'n');

		return aceitar;
	}

	public void reset() {
		this.maodoJogador.clear();
	}

	public String toString() {
		String cardstring = "";
		int i = 1;
		for (Card card : maodoJogador) {
			cardstring += "\t[" + i + "] " + card.toString() + "\n";
			i++;
		}
		return cardstring;
	}

}