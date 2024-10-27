package application;

import java.util.List;
import java.util.Scanner;

import entities.Bot;
import entities.Card;
import entities.Deck;
import entities.Player;

public class Truco {
	static String vira;
	static String[] manilhas;
	int pontuacao1 = 0;
	int pontuacao2 = 0;
	int rodada1 = 0;
	int rodada2 = 0;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		char resp;

		do {
			System.out.println("\t\t\t\t=========Menu=========");
			System.out.printf("\tDeseja jogar Truco? (s/n):");
			resp = sc.nextLine().charAt(0);

			if (resp == 's') {
				jogar(sc);
			} else if (resp != 'n') {
				System.out.println("\tOpção inválida!");
			}

		} while (resp != 'n');

		System.out.println("\tSaindo...");

		sc.close();
	}

	public static void jogar(Scanner sc) {
		Deck deck = new Deck();
		deck.shuffle();

		System.out.println("\tSerão 1 ou 2 jogadores?");
		int jog = sc.nextInt();


		if (jog == 1) {

			Player jogador1 = new Player("Jogador 1");
			Bot bot1 = new Bot();
			Bot bot2 = new Bot();
			Bot bot3 = new Bot();

			List<Card> maodoPlayer = deck.Mao(3);
			jogador1.receberCartas(maodoPlayer);
			jogador1.mostraraMao(maodoPlayer);
			List<Card> maodoBot1 = deck.Mao(3);
			bot1.receberCartas(maodoBot1);
			List<Card> maodoBot2 = deck.Mao(3);
			bot2.receberCartas(maodoBot2);
			List<Card> maodoBot3 = deck.Mao(3);
			bot3.receberCartas(maodoBot3);
			vira = deck.getVira();
			System.out.println("\tO vira é " + vira);
			String[] manilhas = deck.manilha(vira.split(" ")[0]);

			System.out.println("As manilhas são:");
			for (String manilha : manilhas) {
				System.out.println(manilha);
			}

		} else if (jog == 2) {
			Player jogador1 = new Player("Jogador 1");
			Player jogador2 = new Player("Jogador 2");
			Bot bot1 = new Bot();
			Bot bot2 = new Bot();

			List<Card> maodoPlayer1 = deck.Mao(3);
			jogador1.receberCartas(maodoPlayer1);
			jogador1.mostraraMao(maodoPlayer1);
			List<Card> maodoPlayer2 = deck.Mao(3);
			jogador2.receberCartas(maodoPlayer2);
			jogador2.mostraraMao(maodoPlayer2);
			List<Card> maodoBot1 = deck.Mao(3);
			bot1.receberCartas(maodoBot1);
			List<Card> maodoBot2 = deck.Mao(3);
			bot2.receberCartas(maodoBot2);
			vira = deck.getVira();
			System.out.println("\tO vira é " + vira);
			String[] manilhas = deck.manilha(vira.split(" ")[0]);

			System.out.println("As manilhas são:");
			for (String manilha : manilhas) {
				System.out.println(manilha);
			}

		}
	}
}
