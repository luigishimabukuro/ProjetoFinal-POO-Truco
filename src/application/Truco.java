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
	static int pontuacao1 = 0;
	static int pontuacao2 = 0;
	static int rodada1 = 0;
	static int rodada2 = 0;
	static int prodada1 = 0;
	static int prodada2 = 0;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		char resp;
		do {
			System.out.println("\t\t=========Menu=========");
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

			do {
				List<Card> maodoPlayer = deck.Mao(3);
				jogador1.receberCartas(maodoPlayer);

				List<Card> maodoBot1 = deck.Mao(3);
				bot1.receberCartas(maodoBot1);

				List<Card> maodoBot2 = deck.Mao(3);
				bot2.receberCartas(maodoBot2);

				List<Card> maodoBot3 = deck.Mao(3);
				bot3.receberCartas(maodoBot3);

				vira = deck.getVira();
				System.out.println("\t\nO vira é: " + vira);

				String[] manilhas = deck.manilha(vira.split(" ")[0]);
				// System.out.println("As manilhas são:");
				// for (String manilha : manilhas) {
				// System.out.println(manilha);
				// }

				rodada1 = 0;
				rodada2 = 0;

				do {
					prodada1 = 0;
					prodada2 = 0;

					prodada1 += jogador1.playerTurn(maodoPlayer, sc);
					prodada2 += bot1.jogarCarta(maodoBot1);
					prodada1 += bot2.jogarCarta(maodoBot2);
					prodada2 += bot3.jogarCarta(maodoBot3);

					if (prodada1 > prodada2) {
						System.out.println("\n\tO time 1 ganhou a rodada!");
						rodada1++;
					} else {
						System.out.println("\n\tO time 2 ganhou a rodada!");
						rodada2++;
					}

				} while (rodada1 < 2 && rodada2 < 2);

				if (rodada1 == 2) {
					System.out.println("\n\tUm ponto para o Time 1!");
					pontuacao1++;
				} else {
					System.out.println("\n\tUm ponto para o Time 2!");
					pontuacao2++;
				}

				deck.reset();
				List<Card> maodoPlayer1 = deck.Mao(3);
				jogador1.receberCartas(maodoPlayer1);

				List<Card> maodoBot11 = deck.Mao(3);
				bot1.receberCartas(maodoBot11);

				List<Card> maodoBot21 = deck.Mao(3);
				bot2.receberCartas(maodoBot21);

				List<Card> maodoBot31 = deck.Mao(3);
				bot3.receberCartas(maodoBot31);
			} while (pontuacao1 < 12 && pontuacao2 < 12);

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
			System.out.println("\n\tO vira é: " + vira);

			String[] manilhas = deck.manilha(vira.split(" ")[0]);
			// System.out.println("As manilhas são:");
			// for (String manilha : manilhas) {
			// System.out.println(manilha);
			// }

			// jogador1.playerTurn(maodoPlayer1, sc, deck, vira);
			// jogador2.playerTurn(maodoPlayer2, sc, deck, vira);
		}
	}
	
}
