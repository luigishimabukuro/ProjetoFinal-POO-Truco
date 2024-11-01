package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player extends Deck {
	private int score;
	private List<Card> mao;

	public Player(String name) {
		this.setMao(new ArrayList<>());
	}

	public void receberCartas(List<Card> mao) {
		this.setMao(mao);
	}

	public void mostraraMao(List<Card> mao) {
		System.out.println("\n\tMão do jogador:");
		for (Card carta : mao) {
			System.out.println(carta);
		}
	}

	public int playerTurn(List<Card> mao, Scanner sc) {
		System.out.println("\n\tCartas na mão:");
		for (int i = 0; i < mao.size(); i++) {
			System.out.printf("%d: %s\t\n", i + 1, mao.get(i).toString());
		}

		int pos;
		do {
			System.out.printf("\n\tQual carta deseja jogar? (Escolha o número da carta): ");
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

	public int getScore() {
		return score;
	}

	public List<Card> getMao() {
		return mao;
	}

	public void setMao(List<Card> mao) {
		this.mao = mao;
	}
}
