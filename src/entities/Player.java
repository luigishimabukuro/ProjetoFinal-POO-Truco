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
			System.out.print("\n\tQual carta deseja jogar? ");
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
			resp = Character.toUpperCase(sc.nextLine().charAt(0));
			if (resp == 'S') {
				truco = true;
			} else if (resp == 'N') {
				truco = false;
			} else if (resp != 'S' && resp != 'N') {
				System.out.println("\n\t Opção inválida!!!!");
			}
		} while (resp != 'S' && resp != 'N');
		return truco;
	}

	public boolean aceitarTruco(Scanner sc) {
		boolean aceitar = false;
		char resp = 0;
		do {
			System.out.printf("\n\tAceita o truco? ");
			resp = Character.toUpperCase(sc.nextLine().charAt(0));
			if (resp == 'S') {
				aceitar = true;
			} else if (resp == 'N') {
				aceitar = false;
			} else if (resp != 'S' && resp != 'N') {
				System.out.println("\n\t Opção inválida!!!!");
			}
		} while (resp != 'S' && resp != 'N');

		return aceitar;
	}

	public void reset() {
		this.maodoJogador.clear();
	}

	public boolean pedir6(Scanner sc) {
		boolean aceitar = false;
		char resp = 0;
		do {
			System.out.printf("\n\tPede 6!? ");
			resp = Character.toUpperCase(sc.nextLine().charAt(0));
			if (resp == 'S') {
				aceitar = true;
			} else if (resp == 'N') {
				aceitar = false;
			} else if (resp != 'S' && resp != 'N') {
				System.out.println("\n\t Opção inválida!!!!");
			}
		} while (resp != 'S' && resp != 'N');
		return aceitar;
	}

	public boolean pedir9(Scanner sc) {
		boolean aceitar = false;
		char resp = 0;
		do {
			System.out.printf("\n\tPede 9!? ");
			resp = Character.toUpperCase(sc.nextLine().charAt(0));
			if (resp == 'S') {
				aceitar = true;
			} else if (resp == 'N') {
				aceitar = false;
			} else if (resp != 'S' && resp != 'N') {
				System.out.println("\n\t Opção inválida!!!!");
			}
		} while (resp != 'S' && resp != 'N');
		return aceitar;
	}

	public boolean pedir12(Scanner sc) {
		boolean pedir = false;
		char resp = 0;
		do {
			System.out.printf("\n\tPede 12!? ");
			resp = Character.toUpperCase(sc.nextLine().charAt(0));
			if (resp == 'S') {
				pedir = true;
			} else if (resp == 'N') {
				pedir = false;
			} else if (resp != 'S' && resp != 'N') {
				System.out.println("\n\t Opção inválida!!!!");
			}
		} while (resp != 'S' && resp != 'N');
		return pedir;
	}

	public boolean aceitar6(Scanner sc) {
		boolean pedir = false;
		char resp = 0;
		do {
			System.out.printf("\n\tAceita o 6?!? ");
			resp = Character.toUpperCase(sc.nextLine().charAt(0));
			if (resp == 'S') {
				pedir = true;
			} else if (resp == 'N') {
				pedir = false;
			} else if (resp != 'S' && resp != 'N') {
				System.out.println("\n\t Opção inválida!!!!");
			}
		} while (resp != 'S' && resp != 'N');
		return pedir;
	}

	public boolean aceitar9(Scanner sc) {
		boolean pedir = false;
		char resp = 0;
		do {
			System.out.printf("\n\tAceita o 9?!? ");
			resp = Character.toUpperCase(sc.nextLine().charAt(0));
			if (resp == 'S') {
				pedir = true;
			} else if (resp == 'N') {
				pedir = false;
			} else if (resp != 'S' && resp != 'N') {
				System.out.println("\n\t Opção inválida!!!!");
			}
		} while (resp != 'S' && resp != 'N');
		return pedir;
	}

	public boolean aceitar12(Scanner sc) {
		boolean pedir = false;
		char resp = 0;
		do {
			System.out.printf("\n\tAceita o 12!? ");
			resp = Character.toUpperCase(sc.nextLine().charAt(0));
			if (resp == 'S') {
				pedir = true;
			} else if (resp == 'N') {
				pedir = false;
			} else if (resp != 'S' && resp != 'N') {
				System.out.println("\n\t Opção inválida!!!!");
			}
		} while (resp != 'S' && resp != 'N');
		return pedir;
	}

	public String toString() {
		String cardstring = "";
		int i = 1;
		System.out.println("\n\tMão do " + getName());
		for (Card card : maodoJogador) {
			cardstring += "\t[" + i + "] " + card.toString() + "\n";
			i++;
		}
		return cardstring;
	}

}