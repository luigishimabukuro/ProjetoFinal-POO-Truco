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
		sc.nextLine();
		if (jog == 1) {
			Player jogador1 = new Player("Jogador 1");
			Bot bot1 = new Bot();
			Bot bot2 = new Bot();
			Bot bot3 = new Bot();
			do {
				distribuirCartas1(deck, jogador1, bot1, bot2, bot3);
				System.out.println("\n\tO vira é: " + vira);
				String[] manilhas = deck.manilha(vira.split(" ")[0]);

				rodada1 = 0;
				rodada2 = 0;
				boolean truco = jogador1.truco(sc);
				if (truco && bot1.aceitarTruco()) {
					do {
						realizarRodada(jogador1, bot1, bot2, bot3, sc);
					} while (rodada1 < 2 && rodada2 < 2);

					if (rodada1 == 2) {
						pontuacao1 += 3;
						System.out.println("\n\tO time 1 ganhou a rodada!");
					} else if (rodada2 == 2) {
						pontuacao2 += 3;
						System.out.println("\n\tO time 2 ganhou a rodada!");
					}
					resetarRodada(deck, jogador1, bot1, bot2, bot3);

				} else if (truco && !bot1.aceitarTruco()) {
					pontuacao1 += 1;
					System.out.println("\n\tO time 1 ganhou a rodada!");
					resetarRodada(deck, jogador1, bot1, bot2, bot3);
				} else {
					do {
						realizarRodada(jogador1, bot1, bot2, bot3, sc);
					} while (rodada1 < 2 && rodada2 < 2);

					if (rodada1 == 2) {
						pontuacao1++;
						System.out.println("\n\tO time 1 ganhou um ponto!");
					} else if (rodada2 == 2) {
						pontuacao2++;
						System.out.println("\n\tO time 2 ganhou um ponto!");
					}
					resetarRodada(deck, jogador1, bot1, bot2, bot3);
				}

				System.out.println("Pontuação atual: \n\tTime 1: " + pontuacao1 + "\n\tTime 2: " + pontuacao2);

			} while (pontuacao1 < 12 && pontuacao2 < 12);

			if (pontuacao1 >= 12) {
				System.out.println("\n\tTime 1 venceu o jogo!");
			} else {
				System.out.println("\n\tTime 2 venceu o jogo!");
			}
		} else if (jog == 2) {

		}
	}

	public static void distribuirCartas1(Deck deck, Player jogador1, Bot bot1, Bot bot2, Bot bot3) {
		jogador1.receberCartas(deck.Mao(3));
		bot1.receberCartas(deck.Mao(3));
		bot2.receberCartas(deck.Mao(3));
		bot3.receberCartas(deck.Mao(3));

		vira = deck.getVira();
		manilhas = deck.manilha(vira.split(" ")[0]);
	}

	public static void distribuirCartas2(Deck deck, Player jogador1, Bot bot1, Player jogador2, Bot bot3) {
		jogador1.receberCartas(deck.Mao(3));
		bot1.receberCartas(deck.Mao(3));
		jogador2.receberCartas(deck.Mao(3));
		bot3.receberCartas(deck.Mao(3));

		vira = deck.getVira();
		manilhas = deck.manilha(vira.split(" ")[0]);
	}

	static int pontos = 0;

	public static void realizarRodada(Player jogador1, Bot bot1, Bot bot2, Bot bot3, Scanner sc) {
		int resultadoPlayer = jogador1.playerTurn(jogador1.getMao(), sc);
		int resultadoBot1 = bot1.jogarCarta(bot1.getMaodobot());
		int resultadoBot2 = bot2.jogarCarta(bot2.getMaodobot());
		int resultadoBot3 = bot3.jogarCarta(bot3.getMaodobot());

		int pontosTime1 = resultadoPlayer + resultadoBot2;
		int pontosTime2 = resultadoBot1 + resultadoBot3;

		if (pontosTime1 > pontosTime2) {
			rodada1++;
			System.out.println("\n\tO time 1 ganhou a rodada!");
			pontuacao1 += pontos;

		} else if (pontosTime1 < pontosTime2) {
			rodada2++;
			System.out.println("\n\tO time 2 ganhou a rodada!");
			pontuacao2 += pontos;

		} else {
			System.out.println("\n\tA rodada foi empate! O ponto acumula para a próxima rodada.");
			pontos += 1;
		}
	}

	public static void resetarRodada(Deck deck, Player jogador1, Bot bot1, Bot bot2, Bot bot3) {
		deck.reset();
		distribuirCartas1(deck, jogador1, bot1, bot2, bot3);
		rodada1 = 0;
		rodada2 = 0;
		System.out.println("\n\tO vira é: " + vira);
	}

	public void mostraraMao(List<Card> mao) {
		System.out.println("\n\tMão do jogador:");
		for (Card carta : mao) {
			System.out.println(carta);
		}
	}
}
