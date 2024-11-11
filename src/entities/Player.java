package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player extends Deck {
	private List<Card> mao;
	public String name;

	public Player(String mao) {
		this.setMao(new ArrayList<>());
	}

	public void receberCartas(List<Card> mao) {
		this.setMao(mao);
	}

	public void mostraraMao(List<Card> mao) {
		System.out.println("\n\n\tMão do jogador:");
		for (Card carta : mao) {
			System.out.println("\n\t-" + carta);
		}
	}

	public int playerTurn(List<Card> mao, Scanner sc) {
		System.out.println("\n\tCartas na mão:");
		for (int i = 0; i < mao.size(); i++) {
			System.out.printf("\t%d: %s\t\n", i + 1, mao.get(i).toString());
		}

		int pos;
		do {
			System.out.printf("\n\tQual carta deseja jogar? ");
			pos = sc.nextInt();
			sc.nextLine();

			if (pos < 1 || pos > mao.size()) {
				System.out.println("\n\tEscolha inválida!!");
			}
		} while (pos < 1 || pos > mao.size());

		Card cartaEscolhida = mao.remove(pos - 1);
		System.out.println("\n\tVocê jogou: " + cartaEscolhida.toString());

		int valor = getCardValue(cartaEscolhida);
		System.out.println("\n\tValor da carta:" + valor);
		return valor;
	}

	public boolean truco(Scanner scanner) {
		char resp;
		boolean truco = false;
		do {
			System.out.printf("\n\tDeseja trucar? (s/n): ");
			resp = scanner.nextLine().charAt(0);
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

	public List<Card> getMao() {
		return mao;
	}

	public void setMao(List<Card> mao) {
		this.mao = mao;
	}
}
