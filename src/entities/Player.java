package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player extends Deck {
	private int score;
	private String name;
	private List<Card> mao;

	public Player(String name) {
		this.name = name;
		this.mao = new ArrayList<>();
	}

	public void receberCartas(List<Card> mao) {
		this.mao = mao;
	}

	public void mostraraMao (List<Card> mao) {
		System.out.println("Mão do jogador:");
		for (Card carta : mao) {
			System.out.println(carta);
		}
	}
	
	public void PlayerTurn(Deck deck, Scanner scanner) {
		String response;
		while (score < 21) {
			System.out.printf("\tDeseja pegar mais uma carta? (s/n):");
			response = scanner.nextLine();

			if (response.equalsIgnoreCase("s")) {
				score += getCardValue(dealCard(), score);
				System.out.println("\n\t" + name + " agora tem " + score + " pontos.");
			} else if (response.equalsIgnoreCase("n")) {
				break;
			} else {
				System.out.println("\n\tResposta inválida!!");
			}
		}
	}

	public int getScore() {
		return score;
	}
}
