package application;

import java.util.Scanner;
import entities.Bot;
import entities.Deck;
import entities.Player;

public class Truco {
	public static String manilhas;
	public static int pontuacao1 = 0;
	public static int pontuacao2 = 0;
	public static int rodada1 = 0;
	public static int rodada2 = 0;
	public static int rodadas = 0;
	public static int pontos = 0;
	public static int pontoEmpate = 0;
	public static int primeirovencedor = 0;
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_MAGENTA = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLACK = "\u001B[30m";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		char resp;
		do { // Inicializa o jogo.
			System.out.println("\t\t\t\t===========Menu===========");
			System.out.printf("\t\t\t\tDeseja jogar Truco? (s/n): ");
			resp = Character.toUpperCase(sc.nextLine().charAt(0));
			if (resp == 'S') {
				jogar(sc); // Chama a função jogar.
			} else if (resp != 'N') {
				System.out.println("\tOpção inválida!");
			}
		} while (resp != 'N' && resp != 'S');
		System.out.println("\t" + ANSI_CYAN + "Saindo...");
		sc.close();
	}

	public static void jogar(Scanner sc) { // Função que define se vão ser 1 ou dois jogadores.
		Deck deck = new Deck();
		deck.shuffle();
		int jog;
		pontuacao1 = 11;
		pontuacao2 = 11;
		do {
			System.out.printf("\t\t\t\tSerão 1 ou 2 jogadores? ");
			jog = sc.nextInt();
			sc.nextLine();
		} while (jog != 1 && jog != 2);
		if (jog == 2) { // Inicializa os objetos.
			Player jogador1 = new Player(deck, "Jogador 1");
			Bot bot1 = new Bot(deck, "Bot 1");
			Player jogador2 = new Player(deck, "Jogador 2");
			Bot bot3 = new Bot(deck, "Bot 3");

			System.out.println(ANSI_GREEN + "\n\t\t\tTime 1:" + ANSI_RESET + "\n\t\t\tJogador 1\n\t\t\tJogador 2"
					+ ANSI_RED + "\n\n\t\t\tTime 2:" + ANSI_RESET + "\n\t\t\tBot 1\n\t\t\tBot 3");

			while (pontuacao1 < 12 && pontuacao2 < 12) { // Enquanto a pontuação for menor que 12, o jogo continua.

				distribuirCartas2(deck, jogador1, bot1, jogador2, bot3); // Distribui as cartas pros jogadores.
				System.out.println("\n\t\tO vira é: \n\t" + deck.showVira());
				System.out.println("\n\t\tAs manilhas são:");
				System.out.println("\n\t\t●" + manilhas + " de " + ANSI_BLACK + "Paus ♣" + ANSI_RESET + "\n\t\t●"
						+ manilhas + " de " + ANSI_RED + "Copas ♥" + ANSI_RESET + "\n\t\t●" + manilhas + " de "
						+ ANSI_BLACK + "Espadas ♠" + ANSI_RESET + "\n\t\t●" + manilhas + " de " + ANSI_RED + "Ouro ♦"
						+ ANSI_RESET);

				while (rodada1 < 2 && rodada2 < 2) {

					if (pontuacao1 == 11 && pontuacao2 < 11) {
						System.out.println("\n\tMão de Onze!!");
						System.out.print(jogador1.printCardsSideBySide(jogador1.getPlayerHand()));
						System.out.print(jogador2.printCardsSideBySide(jogador2.getPlayerHand()));
						System.out.print(ANSI_YELLOW + "\n\t\t\t\tDeseja jogar a rodada? " + ANSI_RESET);
						char resp3;
						do {
							resp3 = Character.toUpperCase(sc.nextLine().charAt(0));
							if (resp3 == 'S') {
								MaodeOnze2(jogador1, bot1, jogador2, bot3, sc);
							} else if (resp3 == 'N') {
								pontuacao2 += 1;
								rodada2 = 2;
							} else if (resp3 != 'N') {
								System.out.println("\tOpção inválida!");
							}
						} while (resp3 != 'N' && resp3 != 'S');
					} else if (pontuacao1 < 11 && pontuacao2 == 11) {
						boolean aceitar = bot1.aceitar();
						if (aceitar) {
							MaodeOnze2(jogador1, bot1, jogador2, bot3, sc);
						} else {
							pontuacao1 += 1;
							rodada1 = 2;
						}
					} else if (pontuacao1 == 11 && pontuacao2 == 11) {
						MaodeFerro2(jogador1, bot1, jogador2, bot3, sc);
					} else if (pontuacao1 < 11 && pontuacao2 < 11) {
						realizarRodada2(jogador1, bot1, jogador2, bot3, sc); // Função que realiza a rodada com apenas
																				// um
																				// jogador.
					}
				}
				System.out.println("\n\t" + ANSI_YELLOW + "Pontuação atual: \n\t" + ANSI_GREEN + "Time 1: " + pontuacao1
						+ ANSI_RESET + "\n\t" + ANSI_RED + "Time 2: " + pontuacao2 + ANSI_RESET + "\n\n");
				resetarRodada2(deck, jogador1, bot1, jogador2, bot3); // Reseta a rodada, resetando a mão e o deck.
			}

			if (pontuacao1 >= 12) {
				System.out.println("\n\t" + ANSI_GREEN + "Time 1 venceu o jogo!" + ANSI_RESET);
			} else {
				System.out.println("\n\t" + ANSI_RED + "Time 2 venceu o jogo!" + ANSI_RESET);
			}

			System.out.println(ANSI_BLACK
					+ "========================================================================================================="
					+ ANSI_RESET);
			System.out.printf("\n\t\t\t\t\tDeseja jogar novamente? ");
			char resp2;
			do { // Pergunta se quer jogar de novo.
				resp2 = Character.toUpperCase(sc.nextLine().charAt(0));
				if (resp2 == 'S') {
					jogar(sc); // Chama a função jogar.
				} else if (resp2 != 'N') {
					System.out.println("\tOpção inválida!");
				}
			} while (resp2 != 'N' && resp2 != 'S');
			System.out.println("\t" + ANSI_CYAN + "Saindo...");
			sc.close();

		} else if (jog == 1) {
			Player jogador1 = new Player(deck, "Jogador 1");
			Bot bot1 = new Bot(deck, "Bot 1");
			Bot bot2 = new Bot(deck, "Bot 2");
			Bot bot3 = new Bot(deck, "Bot 3");

			System.out.println(ANSI_GREEN + "\n\t\t\tTime 1:" + ANSI_RESET + "\n\t\t\tJogador 1\n\t\t\tBot 2" + ANSI_RED
					+ "\n\n\t\t\tTime 2:" + ANSI_RESET + "\n\t\t\tBot 1\n\t\t\tBot 3");

			while (pontuacao1 < 12 && pontuacao2 < 12) { // Enquanto a pontuação for menor que 12, o jogo continua.

				distribuirCartas1(deck, jogador1, bot1, bot2, bot3); // Distribui as cartas pros jogadores.
				System.out.println("\n\t\tO vira é: \n\t" + deck.showVira());
				System.out.println("\n\t\tAs manilhas são:");
				System.out.println("\n\t\t●" + manilhas + " de " + ANSI_BLACK + "Paus ♣" + ANSI_RESET + "\n\t\t●"
						+ manilhas + " de " + ANSI_RED + "Copas ♥" + ANSI_RESET + "\n\t\t●" + manilhas + " de "
						+ ANSI_BLACK + "Espadas ♠" + ANSI_RESET + "\n\t\t●" + manilhas + " de " + ANSI_RED + "Ouro ♦"
						+ ANSI_RESET);

				while (rodada1 < 2 && rodada2 < 2) {

					if (pontuacao1 == 11 && pontuacao2 < 11) {
						System.out.println("\n\tMão de Onze!!");
						System.out.print(jogador1.printCardsSideBySide(jogador1.getPlayerHand()));
						System.out.print(bot2.printCardsSideBySide(bot2.getBotHand()));
						System.out.print(ANSI_YELLOW + "\n\t\t\t\tDeseja jogar a rodada? " + ANSI_RESET);
						char resp3;
						do {
							resp3 = Character.toUpperCase(sc.nextLine().charAt(0));
							if (resp3 == 'S') {
								MaodeOnze1(jogador1, bot1, bot2, bot3, sc);
							} else if (resp3 == 'N') {
								pontuacao2 += 1;
								rodada2 = 2;
							} else if (resp3 != 'N') {
								System.out.println("\tOpção inválida!");
							}
						} while (resp3 != 'N' && resp3 != 'S');
					} else if (pontuacao1 < 11 && pontuacao2 == 11) {
						boolean aceitar = bot1.aceitar();
						if (aceitar) {
							MaodeOnze1(jogador1, bot1, bot2, bot3, sc);
						} else {
							pontuacao1 += 1;
							rodada1 = 2;
						}
					} else if (pontuacao1 == 11 && pontuacao2 == 11) {
						MaodeFerro1(jogador1, bot1, bot2, bot3, sc);
					} else if (pontuacao1 < 11 && pontuacao2 < 11) {
						realizarRodada1(jogador1, bot1, bot2, bot3, sc); // Função que realiza a rodada com apenas um
																			// jogador.
					}
				}
				System.out.println("\n\t" + ANSI_YELLOW + "Pontuação atual: \n\t" + ANSI_GREEN + "Time 1: " + pontuacao1
						+ ANSI_RESET + "\n\t" + ANSI_RED + "Time 2: " + pontuacao2 + ANSI_RESET + "\n\n");
				resetarRodada1(deck, jogador1, bot1, bot2, bot3); // Reseta a rodada, resetando a mão e o deck.
			}

			if (pontuacao1 >= 12) {
				System.out.println("\n\t" + ANSI_GREEN + "Time 1 venceu o jogo!" + ANSI_RESET);
			} else {
				System.out.println("\n\t" + ANSI_RED + "Time 2 venceu o jogo!" + ANSI_RESET);
			}

			System.out.println(ANSI_BLACK
					+ "========================================================================================================="
					+ ANSI_RESET);
			System.out.printf("\n\t\t\t\t\tDeseja jogar novamente? ");
			char resp2;
			do { // Pergunta se quer jogar de novo.
				resp2 = Character.toUpperCase(sc.nextLine().charAt(0));
				if (resp2 == 'S') {
					jogar(sc); // Chama a função jogar.
				} else if (resp2 != 'N') {
					System.out.println("\tOpção inválida!");
				}
			} while (resp2 != 'N' && resp2 != 'S');
			System.out.println("\t" + ANSI_CYAN + "Saindo...");
			sc.close();
		}
	}

	public static void distribuirCartas1(Deck deck, Player jogador1, Bot bot1, Bot bot2, Bot bot3) {
		// Distribui as cartas para os jogadores.
		jogador1.setPlayerHand(deck);
		jogador1.getPlayerHand();
		bot1.setBotHand(deck);
		bot1.getBotHand();
		bot2.setBotHand(deck);
		bot2.getBotHand();
		bot3.setBotHand(deck);
		bot3.getBotHand();

		deck.setVira();
		manilhas = deck.getManilha();
	}

	public static void distribuirCartas2(Deck deck, Player jogador1, Bot bot1, Player jogador2, Bot bot2) {
		// Distribui as cartas para os jogadores.
		jogador1.setPlayerHand(deck);
		jogador1.getPlayerHand();
		bot1.setBotHand(deck);
		bot1.getBotHand();
		jogador2.setPlayerHand(deck);
		jogador2.getPlayerHand();
		bot2.setBotHand(deck);
		bot2.getBotHand();

		deck.setVira();
		manilhas = deck.getManilha();
	}

	public static void MaodeFerro2(Player jogador1, Bot bot1, Player jogador2, Bot bot2, Scanner sc) {

		int maiorCartaTime1 = 0;
		int maiorCartaTime2 = 0;
		int pontosRodada = 3;

		while (rodada1 < 2 && rodada2 < 2) {

			int resultadoPlayer = jogador1.playerTurnF(jogador1.getPlayerHand(), sc);
			int resultadoBot1 = bot1.jogarCartaF(bot1.getBotHand());
			int resultadoBot2 = jogador2.playerTurnF(jogador2.getPlayerHand(), sc);
			int resultadoBot3 = bot2.jogarCartaF(bot2.getBotHand());

			maiorCartaTime1 = Math.max(resultadoPlayer, resultadoBot2);
			maiorCartaTime2 = Math.max(resultadoBot1, resultadoBot3);

			if (rodadas < 1) { // Faz a verificação do vencedor da primeira rodada, para caso melar a segunda
				// rodada ou a última, o time que ganhou a primeira rodada ganhe o ponto
				if (maiorCartaTime1 > maiorCartaTime2) {
					primeirovencedor = 1;
				} else if (maiorCartaTime1 < maiorCartaTime2) {
					primeirovencedor = 2;
				}
			}

			if (maiorCartaTime1 > maiorCartaTime2) { // Compara o valor das cartas para decidir o vencedor
				rodadas++;
				rodada1++;
				System.out.println("\n\tO " + ANSI_GREEN + "Time 1" + ANSI_RESET + " ganhou a rodada!");
			} else if (maiorCartaTime1 < maiorCartaTime2) {
				rodada2++;
				rodadas++;
				System.out.println("\n\tO " + ANSI_RED + " Time 2" + ANSI_RESET + " ganhou a rodada!");
			} else if (maiorCartaTime1 == maiorCartaTime2) {
				System.out.println(ANSI_MAGENTA + "\n\tA rodada melou!" + ANSI_RESET);
				pontoEmpate++;
				rodadas++;
			}

			if (rodadas == 3 && pontoEmpate == 1) { // Caso a rodada 3 empate, o primeiro vencedor vence.
				if (primeirovencedor == 1) {
					rodada1 = 2;
					pontuacao1 = pontosRodada;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (primeirovencedor == 2) {

					rodada2 = 2;
					pontuacao2 = pontosRodada;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				}

				if (pontoEmpate == 3 && rodadas == 3) { // Caso todas as rodadas melem, a rodada inteira mela e ninguém
														// ganha o ponto.

					System.out.println(ANSI_MAGENTA + "A rodada interia melou, ninguém recebe ponto." + ANSI_RESET);
					return;
				}
			}

			if (rodadas == 2 && pontoEmpate == 1) { // Caso o mele a segunda rodada, o vencedor será o da primeira
				// rodada.
				if (primeirovencedor == 1) {
					rodada1 = 2;
					pontuacao1 += pontosRodada;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (primeirovencedor == 2) {
					rodada2 = 2;
					pontuacao2 += pontosRodada;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (rodada1 > rodada2) { // Caso mele a primeira rodada, o time que ganhar a próxima rodada
												// ganhará o ponto.
					rodada1 = 2;
					pontuacao1 += pontosRodada;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a rodada após a primeira ter melado.");
				} else if (rodada1 < rodada2) {
					rodada2 = 2;
					pontuacao2 += pontosRodada;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a rodada após a primeira ter melado.");
				}
			}

			if (rodadas == 3 && pontoEmpate == 2) { // Caso a primeiras duas rodadas empatarem, o ganhador da última
													// será o vencedor.
				if (rodada1 > rodada2) {
					rodada1 = 2;
					pontuacao1 += pontosRodada;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha a rodada, por ter vencido a única rodada que não melou.");

				} else if (rodada2 > rodada1) {
					rodada2 = 2;

					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha a rodada, por ter vencido a única rodada que não melou.");
				}
			}

			if (rodada1 == 2) { // Define o ganhador da rodada.
				pontuacao1 += pontosRodada;
			} else if (rodada2 == 2) {
				pontuacao2 += pontosRodada;
			}
		} // Reseta os valores que são precisos para ter o critério de desempate
		rodadas = 0;
		pontoEmpate = 0;
		return;
	}

	public static void MaodeFerro1(Player jogador1, Bot bot1, Bot bot2, Bot bot3, Scanner sc) {
		int rodadas = 0;
		int pontoEmpate = 0;
		int maiorCartaTime1 = 0;
		int maiorCartaTime2 = 0;
		int primeirovencedor = 0;
		int pontosRodada = 3;

		while (rodada1 < 2 && rodada2 < 2) {

			int resultadoPlayer = jogador1.playerTurnF(jogador1.getPlayerHand(), sc);
			int resultadoBot1 = bot1.jogarCartaF(bot1.getBotHand());
			int resultadoBot2 = bot2.jogarCartaF(bot2.getBotHand());
			int resultadoBot3 = bot3.jogarCartaF(bot3.getBotHand());

			maiorCartaTime1 = Math.max(resultadoPlayer, resultadoBot2);
			maiorCartaTime2 = Math.max(resultadoBot1, resultadoBot3);

			if (rodadas < 1) { // Faz a verificação do vencedor da primeira rodada, para caso melar a segunda
				// rodada ou a última, o time que ganhou a primeira rodada ganhe o ponto
				if (maiorCartaTime1 > maiorCartaTime2) {
					primeirovencedor = 1;
				} else if (maiorCartaTime1 < maiorCartaTime2) {
					primeirovencedor = 2;
				}
			}

			if (maiorCartaTime1 > maiorCartaTime2) { // Compara o valor das cartas para decidir o vencedor
				rodadas++;
				rodada1++;
				System.out.println("\n\tO " + ANSI_GREEN + "Time 1" + ANSI_RESET + " ganhou a rodada!");
			} else if (maiorCartaTime1 < maiorCartaTime2) {
				rodada2++;
				rodadas++;
				System.out.println("\n\tO " + ANSI_RED + " Time 2" + ANSI_RESET + " ganhou a rodada!");
			} else if (maiorCartaTime1 == maiorCartaTime2) {
				System.out.println(ANSI_MAGENTA + "\n\tA rodada melou!" + ANSI_RESET);
				pontoEmpate++;
				rodadas++;
			}

			if (rodadas == 3 && pontoEmpate == 1) { // Caso a rodada 3 empate, o primeiro vencedor vence.
				if (primeirovencedor == 1) {
					rodada1 = 2;
					pontuacao1 = pontosRodada;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (primeirovencedor == 2) {

					rodada2 = 2;
					pontuacao2 = pontosRodada;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				}

				if (pontoEmpate == 3 && rodadas == 3) { // Caso todas as rodadas melem, a rodada inteira mela e ninguém
														// ganha o ponto.

					System.out.println(ANSI_MAGENTA + "A rodada interia melou, ninguém recebe ponto." + ANSI_RESET);
				}
			}

			if (rodadas == 2 && pontoEmpate == 1) { // Caso o mele a segunda rodada, o vencedor será o da primeira
				// rodada.
				if (primeirovencedor == 1) {
					rodada1 = 2;
					pontuacao1 = pontosRodada;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (primeirovencedor == 2) {
					rodada2 = 2;
					pontuacao2 = pontosRodada;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (rodada1 > rodada2) { // Caso mele a primeira rodada, o time que ganhar a próxima rodada
												// ganhará o ponto.
					rodada1 = 2;
					pontuacao1 = pontosRodada;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a rodada após a primeira ter melado.");
				} else if (rodada1 < rodada2) {
					rodada2 = 2;
					pontuacao2 = pontosRodada;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a rodada após a primeira ter melado.");
				}
			}

			if (rodadas == 3 && pontoEmpate == 2) { // Caso a primeiras duas rodadas empatarem, o ganhador da última
													// será o vencedor.
				if (rodada1 > rodada2) {
					rodada1 = 2;
					pontuacao1 = pontosRodada;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha a rodada, por ter vencido a única rodada que não melou.");

				} else if (rodada2 > rodada1) {
					rodada2 = 2;
					pontuacao2 = pontosRodada;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha a rodada, por ter vencido a única rodada que não melou.");
				}
			}

			if (rodada1 == 2) { // Define o ganhador da rodada.
				pontuacao1 += pontosRodada;
			} else if (rodada2 == 2) {
				pontuacao2 += pontosRodada;
			}
		} // Reseta os valores que são precisos para ter o critério de desempate
		rodadas = 0;
		pontoEmpate = 0;
		return;
	}

	public static void MaodeOnze2(Player jogador1, Bot bot1, Player jogador2, Bot bot2, Scanner sc) {
		int rodadas = 0;
		int rodada1 = 0;
		int rodada2 = 0;
		int maiorCarta1 = 0;
		int maiorCarta2 = 0;
		int primeirovencedor = 0;
		int pontosRodada = 3;

		while (rodada1 < 2 && rodada2 < 2) {

			int resultadoPlayer = jogador1.playerTurn(jogador1.getPlayerHand(), sc);
			int resultadoBot1 = bot1.jogarCarta(bot1.getBotHand());
			int resultadoPlayer2 = jogador2.playerTurn(jogador1.getPlayerHand(), sc);
			int resultadoBot3 = bot2.jogarCarta(bot1.getBotHand());

			maiorCarta1 = Math.max(resultadoPlayer, resultadoPlayer2);
			maiorCarta2 = Math.max(resultadoBot1, resultadoBot3);

			if (rodadas < 1) { // Faz a verificação do vencedor da primeira rodada, para caso melar a segunda
				// rodada ou a última, o time que ganhou a primeira rodada ganhe o ponto
				if (maiorCarta1 > maiorCarta2) {
					primeirovencedor = 1;
				} else if (maiorCarta1 < maiorCarta2) {
					primeirovencedor = 2;
				}
			}

			if (maiorCarta1 > maiorCarta2) { // Compara o valor das cartas para decidir o vencedor
				rodadas++;
				rodada1++;
				System.out.println("\n\tO " + ANSI_GREEN + "Time 1" + ANSI_RESET + " ganhou a rodada!");
			} else if (maiorCarta1 < maiorCarta2) {
				rodada2++;
				rodadas++;
				System.out.println("\n\tO " + ANSI_RED + " Time 2" + ANSI_RESET + " ganhou a rodada!");
			} else if (maiorCarta1 == maiorCarta2) {
				System.out.println(ANSI_MAGENTA + "\n\tA rodada melou!" + ANSI_RESET);
				pontoEmpate++;
				rodadas++;
			}

			if (rodadas == 3 && pontoEmpate == 1) { // Caso a rodada 3 empate, o primeiro vencedor vence.
				if (primeirovencedor == 1) {
					rodada1 = 2;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (primeirovencedor == 2) {

					rodada2 = 2;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				}

				if (pontoEmpate == 3 && rodadas == 3) { // Caso todas as rodadas melem, a rodada inteira mela e ninguém
														// ganha o ponto.

					System.out.println(ANSI_MAGENTA + "A rodada interia melou, ninguém recebe ponto." + ANSI_RESET);
				}
			}

			if (rodadas == 2 && pontoEmpate == 1) { // Caso o mele a segunda rodada, o vencedor será o da primeira
				// rodada.
				if (primeirovencedor == 1) {
					rodada1 = 2;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (primeirovencedor == 2) {
					rodada2 = 2;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (rodada1 > rodada2) { // Caso mele a primeira rodada, o time que ganhar a próxima rodada
												// ganhará o ponto.
					rodada1 = 2;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a rodada após a primeira ter melado.");
				} else if (rodada1 < rodada2) {
					rodada2 = 2;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a rodada após a primeira ter melado.");
				}
			}

			if (rodadas == 3 && pontoEmpate == 2) { // Caso a primeiras duas rodadas empatarem, o ganhador da última
				// será o vencedor.
				if (rodada1 > rodada2) {
					rodada1 = 2;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha a rodada, por ter vencido a única rodada que não melou.");

				} else if (rodada2 > rodada1) {
					rodada2 = 2;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha a rodada, por ter vencido a única rodada que não melou.");
				}
			}

			if (rodada1 == 2) { // Define o ganhador da rodada.
				pontuacao1 += pontosRodada;
			} else if (rodada2 == 2) {
				pontuacao2 += pontosRodada;
			}
		} // Reseta os valores que são precisos para ter o critério de desempate
		rodadas = 0;
		pontoEmpate = 0;
		return;
	}

	public static void MaodeOnze1(Player jogador1, Bot bot1, Bot bot2, Bot bot3, Scanner sc) {
		int maiorCartaTime1 = 0;
		int maiorCartaTime2 = 0;
		int pontosRodada = 3;

		while (rodada1 < 2 && rodada2 < 2) {
			System.out.println("Rodada1: " + rodada1 + " Rodada2: " + rodada2 + " Rodadas: " + rodadas);
			int resultadoPlayer = jogador1.playerTurn(jogador1.getPlayerHand(), sc);
			int resultadoBot1 = bot1.jogarCarta(bot1.getBotHand());
			int resultadoBot2 = bot2.jogarCarta(bot2.getBotHand());
			int resultadoBot3 = bot3.jogarCarta(bot3.getBotHand());

			maiorCartaTime1 = Math.max(resultadoPlayer, resultadoBot2);
			maiorCartaTime2 = Math.max(resultadoBot1, resultadoBot3);

			if (rodadas < 1) { // Faz a verificação do vencedor da primeira rodada, para caso melar a segunda
				// rodada ou a última, o time que ganhou a primeira rodada ganhe o ponto
				System.out.println("Entrei no primeiro vencedor");
				if (maiorCartaTime1 > maiorCartaTime2) {
					primeirovencedor = 1;
				} else if (maiorCartaTime1 < maiorCartaTime2) {
					primeirovencedor = 2;
				}
			}

			if (maiorCartaTime1 > maiorCartaTime2) { // Compara o valor das cartas para decidir o vencedor
				System.out.println("Maior carta do time 1");
				rodadas++;
				rodada1++;

				System.out.println("\n\tO " + ANSI_GREEN + "Time 1" + ANSI_RESET + " ganhou a rodada!");
			} else if (maiorCartaTime1 < maiorCartaTime2) {
				System.out.println("Maior carta do time 2");
				rodada2++;
				rodadas++;
				System.out.println("\n\tO " + ANSI_RED + " Time 2" + ANSI_RESET + " ganhou a rodada!");
			} else if (maiorCartaTime1 == maiorCartaTime2) {
				System.out.println(ANSI_MAGENTA + "\n\tA rodada melou!" + ANSI_RESET);
				pontoEmpate++;
				rodadas++;
			}

			if (rodadas == 3 && pontoEmpate == 1) { // Caso a rodada 3 empate, o primeiro vencedor vence.
				if (primeirovencedor == 1) {

					rodada1 = 2;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (primeirovencedor == 2) {

					rodada2 = 2;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				}

				if (pontoEmpate == 3 && rodadas == 3) { // Caso todas as rodadas melem, a rodada inteira mela e ninguém
														// ganha o ponto.

					System.out.println(ANSI_MAGENTA + "A rodada interia melou, ninguém recebe ponto." + ANSI_RESET);
					return;
				}
			}

			if (rodadas == 2 && pontoEmpate == 1) { // Caso o mele a segunda rodada, o vencedor será o da primeira
				// rodada.
				if (primeirovencedor == 1) {
					rodada1 = 2;
					pontuacao1 = pontosRodada;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (primeirovencedor == 2) {
					rodada2 = 2;
					pontuacao2 = pontosRodada;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (rodada1 > rodada2) { // Caso mele a primeira rodada, o time que ganhar a próxima rodada
												// ganhará o ponto.
					rodada1 = 2;
					pontuacao1 = pontosRodada;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a rodada após a primeira ter melado.");
				} else if (rodada1 < rodada2) {
					rodada2 = 2;
					pontuacao2 = pontosRodada;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a rodada após a primeira ter melado.");
				}
			}

			if (rodadas == 3 && pontoEmpate == 2) { // Caso a primeiras duas rodadas empatarem, o ganhador da última
													// será o vencedor.
				if (rodada1 > rodada2) {
					rodada1 = 2;
					pontuacao1 = pontosRodada;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha a rodada, por ter vencido a única rodada que não melou.");

				} else if (rodada2 > rodada1) {
					rodada2 = 2;
					pontuacao2 = pontosRodada;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha a rodada, por ter vencido a única rodada que não melou.");
				}
			}

			if (rodada1 == 2) { // Define o ganhador da rodada.
				pontuacao1 += pontosRodada;

			} else if (rodada2 == 2) {
				pontuacao2 += pontosRodada;
			}
		} // Reseta os valores que são precisos para ter o critério de desempate
		rodadas = 0;
		pontoEmpate = 0;
	}

	public static void realizarRodada1(Player jogador1, Bot bot1, Bot bot2, Bot bot3, Scanner sc) {
		// Realiza a rodada com 1 jogador só.
		int flag = 0;
		int pontosRodada = 1;
		boolean pedir61 = false;
		boolean pedir62 = false;
		boolean pedir91 = false;
		boolean pedir92 = false;
		boolean pedir121 = false;
		boolean pedir122 = false;
		boolean aceitar61 = false;
		boolean aceitar62 = false;
		boolean aceitar91 = false;
		boolean aceitar92 = false;
		boolean aceitar121 = false;
		boolean aceitar122 = false;
		boolean trucoAtivo = false;
		boolean pedidodeTruco = false;
		boolean aceitaTruco = false;
		int pontoEmpate = 0;
		int primeirovencedor = 0;

		System.out.println(jogador1.printCardsSideBySide(jogador1.getPlayerHand()));
		// Até rodada1 ou rodada2 menor que 2, continua a rodada.
		while (rodada1 < 2 && rodada2 < 2) {
			if (trucoAtivo == false) {
				pedidodeTruco = jogador1.truco(sc); // Verificação se vai pedir ou não truco.
				if (pedidodeTruco) { // Caso aceite, a rodada vale 3.
					flag = 1;
					System.out.println(ANSI_GREEN + "\n\tTruco!!!" + ANSI_RESET);
					aceitaTruco = bot1.aceitarTruco(); // Se houver a recusa, o outro time ganha a rodada
														// automaticamente.
					if (aceitaTruco) {
						pontosRodada = 3;
						pedir62 = bot1.pedir6(); // Condições para saber o valor da rodada.
						if (pedir62) {
							aceitar61 = jogador1.aceitar6(sc);
							if (aceitar61) {
								pontosRodada = 6;
								pedir91 = jogador1.pedir9(sc);
								if (pedir91) {
									aceitar92 = bot1.aceitar9();
									if (aceitar92) {
										pontosRodada = 9;
										pedir122 = bot1.pedir12();
										if (pedir122) {
											aceitar121 = jogador1.aceitar12(sc);
											if (aceitar121) {
												pontosRodada = 12;
											} else {
												System.out.println("\n\tO Jogador recusou o 12, O" + ANSI_RED
														+ " Time 2" + ANSI_RESET + " ganha a rodada.");
												rodada2 = 2;
												pontuacao2 += pontosRodada;
												return;
											}
										}
									} else {
										System.out.println("\n\tO Bot recusou o 9, O" + ANSI_GREEN + " Time 1"
												+ ANSI_RESET + " ganha a rodada.");
										rodada1 = 2;
										pontuacao1 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\n\tO Jogador recusou o 6, O" + ANSI_RED + " Time 2" + ANSI_RESET
										+ " ganha a rodada.");
								rodada2 = 2;
								pontuacao2 += pontosRodada;
								return;
							}
						}

					} else {
						System.out.println("\n\tO Bot recusou o truco. O" + ANSI_GREEN + " Time 1" + ANSI_RESET
								+ " ganha a rodada.");
						rodada1 = 2;
						pontuacao1 += pontosRodada;
						return;
					}
					trucoAtivo = true;
				}
			} else if (trucoAtivo == true) {
				if (flag == 1) {

					if (pedir62 && !pedir91 && !pedir122) {
						pedir91 = jogador1.pedir9(sc);
						if (pedir91) {
							aceitar92 = bot1.aceitar9();
							if (aceitar92) {
								pontosRodada = 9;
								pedir122 = bot1.pedir12();
								if (pedir122) {
									aceitar121 = jogador1.aceitar12(sc);
									if (aceitar121) {
										pontosRodada = 12;
									} else {
										System.out.println("\n\tO Jogador recusou o 12, O" + ANSI_RED + " Time 2"
												+ ANSI_RESET + " ganha a rodada.");
										rodada2 = 2;
										pontuacao2 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\n\tO Bot recusou o 9, O" + ANSI_GREEN + " Time 1" + ANSI_RESET
										+ " ganha a rodada.");
								rodada1 = 2;
								pontuacao1 += pontosRodada;
								return;
							}
						}
					}
				} else if (flag == 2) {
					if (!pedir61 && !pedir92 && !pedir121) {
						pedir61 = jogador1.pedir6(sc);
						if (pedir61) {
							aceitar62 = bot1.aceitar6();
							if (aceitar62) {
								pontosRodada = 6;
								pedir92 = bot1.pedir9();
								if (pedir92) {
									aceitar91 = jogador1.aceitar9(sc);
									if (aceitar91) {
										pontosRodada = 9;
										pedir121 = jogador1.pedir12(sc);
										if (pedir121) {
											aceitar122 = bot1.aceitar12();
											if (aceitar122) {
												pontosRodada = 12;
											} else {
												System.out.println("\n\tO Bot recusou o 12, O" + ANSI_GREEN + " Time 1"
														+ ANSI_RESET + " ganha a rodada.");
												rodada1 = 2;
												pontuacao1 += pontosRodada;
												return;
											}

										}
									} else {
										System.out.println("\n\tO Jogador recusou o 9, O" + ANSI_RED + " Time 2"
												+ ANSI_RESET + " ganha a rodada.");
										rodada2 = 2;
										pontuacao2 += pontosRodada;
										return;
									}
								}

							} else {
								System.out.println("\n\tO Bot recusou o 6, O" + ANSI_GREEN + " Time 1" + ANSI_RESET
										+ " ganha a rodada.");
								rodada1 = 2;
								pontuacao1 += pontosRodada;
								return;
							}
						}
					} else if (pedir61 && pedir92 && !pedir121) {
						pedir121 = jogador1.pedir12(sc);
						if (pedir121) {
							aceitar122 = bot1.aceitar12();
							if (aceitar122) {
								pontosRodada = 12;
							} else {
								System.out.println("\n\tO Bot recusou o 12, O" + ANSI_GREEN + " Time 1" + ANSI_RESET
										+ " ganha a rodada.");
								rodada1 = 2;
								pontuacao1 += pontosRodada;
								return;
							}
						}
					}
				}
			}
			int resultadoPlayer = jogador1.playerTurn(jogador1.getPlayerHand(), sc);
			if (trucoAtivo == false) { // De agora em diante, faz a mesma verificação.
				pedidodeTruco = bot1.pedirTruco(); // Verificação se vai pedir ou não truco.
				if (pedidodeTruco) { // Caso aceite, a rodada vale 3.
					flag = 2;
					System.out.println(ANSI_RED + "\n\tTruco!!!" + ANSI_RESET);
					aceitaTruco = bot2.aceitarTruco(); // Se houver a recusa, o outro time ganha a rodada
														// automaticamente.
					if (aceitaTruco) {
						pontosRodada = 3;
						pedir61 = bot2.pedir6(); // Condições para saber o valor da rodada.
						if (pedir61) {
							aceitar62 = bot2.aceitar6();
							if (aceitar62) {
								pontosRodada = 6;
								pedir92 = bot1.pedir9();
								if (pedir92) {
									aceitar91 = bot2.aceitar9();
									if (aceitar91) {
										pontosRodada = 9;
										pedir121 = bot2.pedir12();
										if (pedir121) {
											aceitar122 = bot1.aceitar12();
											if (aceitar121) {
												pontosRodada = 12;
											} else {
												System.out.println("\tO Bot recusou o 12, O" + ANSI_GREEN + " Time 1"
														+ ANSI_RESET + " ganha a rodada.");
												rodada1 = 2;
												pontuacao1 += pontosRodada;
												return;
											}
										}
									} else {
										System.out.println("\tO Bot recusou o 9, O" + ANSI_RED + " Time 2" + ANSI_RESET
												+ " ganha a rodada.");
										rodada2 = 2;
										pontuacao2 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\tO Bot recusou o 6, O" + ANSI_GREEN + " Time 1" + ANSI_RESET
										+ " ganha a rodada.");
								rodada1 = 2;
								pontuacao1 += pontosRodada;
								return;
							}
						}

					} else {
						System.out.println(
								"\tO Bot recusou o truco. O" + ANSI_RED + " Time 2" + ANSI_RESET + " ganha a rodada.");
						rodada2 = 2;
						pontuacao2 += pontosRodada;
						return;
					}
					trucoAtivo = true;
				}
			} else if (trucoAtivo == true) {
				if (flag == 2) {
					if (pedir61 && !pedir92 && !pedir121) {
						pedir92 = bot1.pedir9();
						if (pedir92) {
							aceitar91 = bot2.aceitar9();
							if (aceitar91) {
								pontosRodada = 9;
								pedir121 = bot2.pedir12();
								if (pedir121) {
									aceitar122 = bot1.aceitar12();
									if (aceitar122) {
										pontosRodada = 12;
									} else {
										System.out.println("\tO Bot recusou o 12, O" + ANSI_GREEN + " Time 1"
												+ ANSI_RESET + " ganha a rodada.");
										rodada1 = 2;
										pontuacao1 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\tO Bot recusou o 9, O" + ANSI_RED + " Time 2" + ANSI_RESET
										+ " ganha a rodada.");
								rodada2 = 2;
								pontuacao2 += pontosRodada;
								return;
							}
						}
					}
				} else if (flag == 1) {
					if (!pedir62 && !pedir91 && !pedir122) {
						pedir62 = bot1.pedir6();
						if (pedir62) {
							aceitar61 = bot2.aceitar6();
							if (aceitar61) {
								pontosRodada = 6;
								pedir92 = bot1.pedir9();
								if (pedir92) {
									aceitar91 = bot2.aceitar9();
									if (aceitar91) {
										pontosRodada = 9;
										pedir122 = bot1.pedir12();
										if (pedir122) {
											aceitar121 = bot2.aceitar12();
											if (aceitar121) {
												pontosRodada = 12;
											} else {
												System.out.println("\tO Bot recusou o 12, O" + ANSI_RED + " Time 2"
														+ ANSI_RESET + " ganha a rodada.");
												rodada2 = 2;
												pontuacao2 += pontosRodada;
												return;
											}
										}
									} else {
										System.out.println("\tO Bot recusou o 9, O" + ANSI_RED + " Time 2" + ANSI_RESET
												+ " ganha a rodada.");
										rodada1 = 2;
										pontuacao2 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\tO Bot recusou o 6, O" + ANSI_RED + " Time 2" + ANSI_RESET
										+ " ganha a rodada.");
								rodada2 = 2;
								pontuacao2 += pontosRodada;
								return;
							}
						}
					} else if (pedir91 && !pedir122) {
						pedir122 = bot1.pedir12();
						if (pedir122) {
							aceitar121 = bot2.aceitar12();
							if (aceitar121) {
								pontosRodada = 12;
							} else {
								System.out.println("\tO Bot recusou o 12, O" + ANSI_RED + " Time 2" + ANSI_RESET
										+ " ganha a rodada.");
								rodada2 = 2;
								pontuacao2 += pontosRodada;
								return;
							}
						}
					}
				}
			}
			int resultadoBot1 = bot1.jogarCarta(bot1.getBotHand());
			if (trucoAtivo == false) { // De agora em diante, faz a mesma verificação.
				pedidodeTruco = bot2.pedirTruco(); // Verificação se vai pedir ou não truco.
				if (pedidodeTruco) { // Caso aceite, a rodada vale 3.
					flag = 1;
					System.out.println(ANSI_GREEN + "\n\tTruco!!!" + ANSI_RESET);
					aceitaTruco = bot3.aceitarTruco(); // Se houver a recusa, o outro time ganha a rodada
														// automaticamente.
					if (aceitaTruco) {
						pontosRodada = 3;
						pedir62 = bot3.pedir6(); // Condições para saber o valor da rodada.
						if (pedir62) {
							aceitar61 = bot2.aceitar6();
							if (aceitar61) {
								pontosRodada = 6;
								pedir91 = bot2.pedir9();
								if (pedir91) {
									aceitar92 = bot3.aceitar9();
									if (aceitar92) {
										pontosRodada = 9;
										pedir122 = bot3.pedir12();
										if (pedir121) {
											aceitar121 = bot2.aceitar12();
											if (aceitar121) {
												pontosRodada = 12;
											} else {
												System.out.println("\tO Bot recusou o 12, O" + ANSI_RED + " Time 2"
														+ ANSI_RESET + " ganha a rodada.");
												rodada2 = 2;
												pontuacao2 += pontosRodada;
												return;
											}
										}
									} else {
										System.out.println("\tO Bot recusou o 9, O" + ANSI_GREEN + " Time 1"
												+ ANSI_RESET + " ganha a rodada.");
										rodada1 = 2;
										pontuacao1 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\tO Bot recusou o 6, O" + ANSI_RED + " Time 2" + ANSI_RESET
										+ " ganha a rodada.");
								rodada2 = 2;
								pontuacao2 += pontosRodada;
								return;
							}
						}

					} else {
						System.out.println("\tO Bot recusou o truco. O" + ANSI_GREEN + " Time 1" + ANSI_RESET
								+ " ganha a rodada.");
						rodada1 = 2;
						pontuacao1 += pontosRodada;
						return;
					}
					trucoAtivo = true;
				}
			} else if (trucoAtivo == true) {
				if (flag == 2) {
					if (pedir62 && !pedir91 && !pedir122) {
						pedir91 = bot2.pedir9();
						if (pedir91) {
							aceitar92 = bot3.aceitar9();
							if (aceitar92) {
								pontosRodada = 9;
								pedir122 = bot3.pedir12();
								if (pedir122) {
									aceitar121 = bot2.aceitar12();
									if (aceitar121) {
										pontosRodada = 12;
									} else {
										System.out.println("\tO Bot recusou o 12, O" + ANSI_RED + " Time 2" + ANSI_RESET
												+ " ganha a rodada.");
										rodada2 = 2;
										pontuacao2 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\tO Bot recusou o 9, O" + ANSI_GREEN + " Time 1" + ANSI_RESET
										+ " ganha a rodada.");
								rodada1 = 2;
								pontuacao1 += pontosRodada;
								return;
							}
						}
					}
				} else if (flag == 1) {
					if (pedir92 && !pedir121) {
						pedir121 = bot2.pedir12();
						if (pedir121) {
							aceitar122 = bot3.aceitar12();
							if (aceitar122) {
								pontosRodada = 12;
							} else {
								System.out.println("\tO Bot recusou o 12, O" + ANSI_GREEN + " Time 1" + ANSI_RESET
										+ " ganha a rodada.");
								rodada1 = 2;
								pontuacao1 += pontosRodada;
								return;
							}

						}
					}
				}
			}
			int resultadoBot2 = bot2.jogarCarta(bot2.getBotHand());
			if (trucoAtivo == false) { // De agora em diante, faz a mesma verificação.
				pedidodeTruco = bot3.pedirTruco(); // Verificação se vai pedir ou não truco.
				if (pedidodeTruco) { // Caso aceite, a rodada vale 3.
					flag = 2;
					System.out.println(ANSI_RED + "\n\tTruco!!!" + ANSI_RESET);
					aceitaTruco = jogador1.aceitarTruco(sc); // Se houver a recusa, o outro time ganha a rodada
					// automaticamente.
					if (aceitaTruco) {
						pontosRodada = 3;
						pedir61 = jogador1.pedir6(sc); // Condições para saber o valor da rodada.
						if (pedir61) {
							aceitar62 = bot3.aceitar6();
							if (aceitar62) {
								pontosRodada = 6;
								pedir92 = bot3.pedir9();
								if (pedir92) {
									aceitar91 = jogador1.aceitar9(sc);
									if (aceitar91) {
										pontosRodada = 9;
										pedir121 = jogador1.pedir12(sc);
										if (pedir121) {
											aceitar122 = bot3.aceitar12();
											if (aceitar121) {
												pontosRodada = 12;
											} else {
												System.out.println("\tO Bot recusou o 12, O" + ANSI_GREEN + " Time 1"
														+ ANSI_RESET + " ganha a rodada.");
												rodada1 = 2;
												pontuacao1 += pontosRodada;
												return;
											}
										}
									} else {
										System.out.println("\tO Jogador recusou o 9, O" + ANSI_RED + " Time 2"
												+ ANSI_RESET + " ganha a rodada.");
										rodada2 = 2;
										pontuacao2 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\tO Bot recusou o 6, O" + ANSI_GREEN + " Time 1" + ANSI_RESET
										+ " ganha a rodada.");
								rodada1 = 2;
								pontuacao1 += pontosRodada;
								return;
							}
						}

					} else {
						System.out.println(
								"\tO Bot recusou o truco. O" + ANSI_RED + " Time 2" + ANSI_RESET + " ganha a rodada.");
						rodada2 = 2;
						pontuacao2 += pontosRodada;
						return;
					}
					trucoAtivo = true;
				}
			} else if (trucoAtivo == true) {
				if (flag == 2) {
					if (pedir61 && !pedir92 && !pedir121) {
						pedir92 = bot3.pedir9();
						if (pedir92) {
							aceitar91 = jogador1.aceitar9(sc);
							if (aceitar91) {
								pontosRodada = 9;
								pedir121 = jogador1.pedir12(sc);
								if (pedir121) {
									aceitar122 = bot3.aceitar12();
									if (aceitar122) {
										pontosRodada = 12;
									} else {
										System.out.println("\tO Bot recusou o 12, O" + ANSI_GREEN + " Time 1"
												+ ANSI_RESET + " ganha a rodada.");
										rodada1 = 2;
										pontuacao1 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\tO Jogador recusou o 9, O" + ANSI_RED + " Time 2" + ANSI_RESET
										+ " ganha a rodada.");
								rodada2 = 2;
								pontuacao2 += pontosRodada;
								return;
							}
						}
					} else if (pedir92 && !pedir121) {
						pedir121 = bot2.pedir12();
						if (pedir121) {
							aceitar122 = bot3.aceitar12();
							if (aceitar122) {
								pontosRodada = 12;
							} else {
								System.out.println("\tO Bot recusou o 12, O" + ANSI_GREEN + " Time 1" + ANSI_RESET
										+ " ganha a rodada.");
								rodada1 = 2;
								pontuacao1 += pontosRodada;
								return;
							}
						}
					}
				}
				if (flag == 1) {
					if (!pedir62 && pedir91 && !pedir122) {
						pedir62 = bot3.pedir6();
						if (pedir62) {
							aceitar61 = jogador1.aceitar6(sc);
							if (aceitar61) {
								pontosRodada = 6;
								pedir91 = jogador1.pedir9(sc);
								if (pedir91) {
									aceitar92 = bot3.aceitar9();
									if (aceitar92) {
										pontosRodada = 9;
										pedir122 = bot3.pedir12();
										if (pedir122) {
											aceitar121 = jogador1.aceitar12(sc);
											if (aceitar121) {
												pontosRodada = 12;
											} else {
												System.out.println("\tO Jogador recusou o 12, O" + ANSI_RED + " Time 2"
														+ ANSI_RESET + " ganha a rodada.");
												rodada2 = 2;
												pontuacao2 += pontosRodada;
												return;
											}
										}
									} else {
										System.out.println("\tO Bot recusou o 9, O" + ANSI_GREEN + " Time 1"
												+ ANSI_RESET + " ganha a rodada.");
										rodada2 = 2;
										pontuacao1 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\tO Jogador recusou o 6, O" + ANSI_RED + " Time 2" + ANSI_RESET
										+ " ganha a rodada.");
								rodada2 = 2;
								pontuacao2 += pontosRodada;
								return;
							}

						}
					}
				}
			}
			int resultadoBot3 = bot3.jogarCarta(bot3.getBotHand());
			int maiorCartaTime1 = Math.max(resultadoPlayer, resultadoBot2); // Compara qual é a maior carta jogada pelo
																			// time.
			int maiorCartaTime2 = Math.max(resultadoBot1, resultadoBot3);

			if (rodadas < 1) { // Faz a verificação do vencedor da primeira rodada, para caso melar a segunda
								// rodada ou a última, o time que ganhou a primeira rodada ganhe o ponto
				if (maiorCartaTime1 > maiorCartaTime2) {
					primeirovencedor = 1;
				} else if (maiorCartaTime1 < maiorCartaTime2) {
					primeirovencedor = 2;
				}
			}

			if (maiorCartaTime1 > maiorCartaTime2) { // Compara o valor das cartas para decidir o vencedor
				rodadas++;
				rodada1++;
				System.out.println("\n\tO " + ANSI_GREEN + "Time 1" + ANSI_RESET + " ganhou a rodada!");
			} else if (maiorCartaTime1 < maiorCartaTime2) {
				rodada2++;
				rodadas++;
				System.out.println("\n\tO " + ANSI_RED + " Time 2" + ANSI_RESET + " ganhou a rodada!");
			} else if (maiorCartaTime1 == maiorCartaTime2) {
				System.out.println(ANSI_MAGENTA + "\n\tA rodada melou!" + ANSI_RESET);
				pontoEmpate++;
				rodadas++;
			}

			if (rodadas == 3 && pontoEmpate == 1) { // Caso a rodada 3 empate, o primeiro vencedor vence.
				if (primeirovencedor == 1) {
					rodada1 = 2;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (primeirovencedor == 2) {

					rodada2 = 2;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				}

				if (pontoEmpate == 3 && rodadas == 3) { // Caso todas as rodadas melem, a rodada inteira mela e ninguém
														// ganha o ponto.

					System.out.println(ANSI_MAGENTA + "A rodada interia melou, ninguém recebe ponto." + ANSI_RESET);
					return;
				}
			}

			if (rodadas == 2 && pontoEmpate == 1) { // Caso o mele a segunda rodada, o vencedor será o da primeira
													// rodada.
				if (primeirovencedor == 1) {
					rodada1 = 2;
					pontuacao1 = pontosRodada;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (primeirovencedor == 2) {
					rodada2 = 2;
					pontuacao2 = pontosRodada;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (rodada1 > rodada2) { // Caso mele a primeira rodada, o time que ganhar a próxima rodada
												// ganhará o ponto.
					rodada1 = 2;
					pontuacao1 = pontosRodada;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a rodada após a primeira ter melado.");
				} else if (rodada1 < rodada2) {
					rodada2 = 2;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a rodada após a primeira ter melado.");
				}
			}

			if (rodadas == 3 && pontoEmpate == 2) { // Caso a primeiras duas rodadas empatarem, o ganhador da última
													// será o vencedor.
				if (rodada1 > rodada2) {
					rodada1 = 2;
					pontuacao1 = pontosRodada;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha a rodada, por ter vencido a única rodada que não melou.");

				} else if (rodada2 > rodada1) {
					rodada2 = 2;
					pontuacao2 = pontosRodada;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha a rodada, por ter vencido a única rodada que não melou.");
				}
			}

			if (rodada1 == 2) { // Define o ganhador da rodada.
				pontuacao1 += pontosRodada;
			} else if (rodada2 == 2) {
				pontuacao2 += pontosRodada;
			}
		} // Reseta os valores que são precisos para ter o critério de desempate
		rodadas = 0;
		pontoEmpate = 0;
		return;
	}

	public static void realizarRodada2(Player jogador1, Bot bot1, Player jogador2, Bot bot2, Scanner sc) {
		// Realiza a rodada com 1 jogador só.
		int flag = 0;
		int pontosRodada = 1;
		boolean pedir61 = false;
		boolean pedir62 = false;
		boolean pedir91 = false;
		boolean pedir92 = false;
		boolean pedir121 = false;
		boolean pedir122 = false;
		boolean aceitar61 = false;
		boolean aceitar62 = false;
		boolean aceitar91 = false;
		boolean aceitar92 = false;
		boolean aceitar121 = false;
		boolean aceitar122 = false;
		boolean trucoAtivo = false;
		boolean pedidodeTruco = false;
		boolean aceitaTruco = false;
		int pontoEmpate = 0;
		int primeirovencedor = 0;

		System.out.println(jogador1.printCardsSideBySide(jogador1.getPlayerHand()));
		// Até rodada1 ou rodada2 menor que 2, continua a rodada.
		while (rodada1 < 2 && rodada2 < 2) {
			if (trucoAtivo == false) {
				pedidodeTruco = jogador1.truco(sc); // Verificação se vai pedir ou não truco.
				if (pedidodeTruco) { // Caso aceite, a rodada vale 3.
					flag = 1;
					System.out.println(ANSI_GREEN + "\n\tTruco!!!" + ANSI_RESET);
					aceitaTruco = bot1.aceitarTruco(); // Se houver a recusa, o outro time ganha a rodada
														// automaticamente.
					if (aceitaTruco) {
						pontosRodada = 3;
						pedir62 = bot1.pedir6(); // Condições para saber o valor da rodada.
						if (pedir62) {
							aceitar61 = jogador1.aceitar6(sc);
							if (aceitar61) {
								pontosRodada = 6;
								pedir91 = jogador1.pedir9(sc);
								if (pedir91) {
									aceitar92 = bot1.aceitar9();
									if (aceitar92) {
										pontosRodada = 9;
										pedir122 = bot1.pedir12();
										if (pedir122) {
											aceitar121 = jogador1.aceitar12(sc);
											if (aceitar121) {
												pontosRodada = 12;
											} else {
												System.out.println("\n\tO Jogador recusou o 12, O" + ANSI_RED
														+ " Time 2" + ANSI_RESET + " ganha a rodada.");
												rodada2 = 2;
												pontuacao2 += pontosRodada;
												return;
											}
										}
									} else {
										System.out.println("\n\tO Bot recusou o 9, O" + ANSI_GREEN + " Time 1"
												+ ANSI_RESET + " ganha a rodada.");
										rodada1 = 2;
										pontuacao1 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\n\tO Jogador recusou o 6, O" + ANSI_RED + " Time 2" + ANSI_RESET
										+ " ganha a rodada.");
								rodada2 = 2;
								pontuacao2 += pontosRodada;
								return;
							}
						}

					} else {
						System.out.println("\n\tO Bot recusou o truco. O" + ANSI_GREEN + " Time 1" + ANSI_RESET
								+ " ganha a rodada.");
						rodada1 = 2;
						pontuacao1 += pontosRodada;
						return;
					}
					trucoAtivo = true;
				}
			} else if (trucoAtivo == true) {
				if (flag == 1) {

					if (pedir62 && !pedir91 && !pedir122) {
						pedir91 = jogador1.pedir9(sc);
						if (pedir91) {
							aceitar92 = bot1.aceitar9();
							if (aceitar92) {
								pontosRodada = 9;
								pedir122 = bot1.pedir12();
								if (pedir122) {
									aceitar121 = jogador1.aceitar12(sc);
									if (aceitar121) {
										pontosRodada = 12;
									} else {
										System.out.println("\n\tO Jogador recusou o 12, O" + ANSI_RED + " Time 2"
												+ ANSI_RESET + " ganha a rodada.");
										rodada2 = 2;
										pontuacao2 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\n\tO Bot recusou o 9, O" + ANSI_GREEN + " Time 1" + ANSI_RESET
										+ " ganha a rodada.");
								rodada1 = 2;
								pontuacao1 += pontosRodada;
								return;
							}
						}
					}
				} else if (flag == 2) {
					if (!pedir61 && !pedir92 && !pedir121) {
						pedir61 = jogador1.pedir6(sc);
						if (pedir61) {
							aceitar62 = bot1.aceitar6();
							if (aceitar62) {
								pontosRodada = 6;
								pedir92 = bot1.pedir9();
								if (pedir92) {
									aceitar91 = jogador1.aceitar9(sc);
									if (aceitar91) {
										pontosRodada = 9;
										pedir121 = jogador1.pedir12(sc);
										if (pedir121) {
											aceitar122 = bot1.aceitar12();
											if (aceitar122) {
												pontosRodada = 12;
											} else {
												System.out.println("\n\tO Bot recusou o 12, O" + ANSI_GREEN + " Time 1"
														+ ANSI_RESET + " ganha a rodada.");
												rodada1 = 2;
												pontuacao1 += pontosRodada;
												return;
											}

										}
									} else {
										System.out.println("\n\tO Jogador recusou o 9, O" + ANSI_RED + " Time 2"
												+ ANSI_RESET + " ganha a rodada.");
										rodada2 = 2;
										pontuacao2 += pontosRodada;
										return;
									}
								}

							} else {
								System.out.println("\n\tO Bot recusou o 6, O" + ANSI_GREEN + " Time 1" + ANSI_RESET
										+ " ganha a rodada.");
								rodada1 = 2;
								pontuacao1 += pontosRodada;
								return;
							}
						}
					} else if (pedir61 && pedir92 && !pedir121) {
						pedir121 = jogador1.pedir12(sc);
						if (pedir121) {
							aceitar122 = bot1.aceitar12();
							if (aceitar122) {
								pontosRodada = 12;
							} else {
								System.out.println("\n\tO Bot recusou o 12, O" + ANSI_GREEN + " Time 1" + ANSI_RESET
										+ " ganha a rodada.");
								rodada1 = 2;
								pontuacao1 += pontosRodada;
								return;
							}
						}
					}
				}
			}
			int resultadoPlayer = jogador1.playerTurn(jogador1.getPlayerHand(), sc);
			if (trucoAtivo == false) { // De agora em diante, faz a mesma verificação.
				pedidodeTruco = bot1.pedirTruco(); // Verificação se vai pedir ou não truco.
				if (pedidodeTruco) { // Caso aceite, a rodada vale 3.
					flag = 2;
					System.out.println(ANSI_RED + "\n\tTruco!!!" + ANSI_RESET);
					aceitaTruco = jogador2.aceitarTruco(sc); // Se houver a recusa, o outro time ganha a rodada
					// automaticamente.
					if (aceitaTruco) {
						pontosRodada = 3;
						pedir61 = jogador2.pedir6(sc); // Condições para saber o valor da rodada.
						if (pedir61) {
							aceitar62 = jogador2.aceitar6(sc);
							if (aceitar62) {
								pontosRodada = 6;
								pedir92 = bot1.pedir9();
								if (pedir92) {
									aceitar91 = jogador2.aceitar9(sc);
									if (aceitar91) {
										pontosRodada = 9;
										pedir121 = jogador2.pedir12(sc);
										if (pedir121) {
											aceitar122 = bot1.aceitar12();
											if (aceitar121) {
												pontosRodada = 12;
											} else {
												System.out.println("\tO Bot recusou o 12, O" + ANSI_GREEN + " Time 1"
														+ ANSI_RESET + " ganha a rodada.");
												rodada1 = 2;
												pontuacao1 += pontosRodada;
												return;
											}
										}
									} else {
										System.out.println("\tO Bot recusou o 9, O" + ANSI_RED + " Time 2" + ANSI_RESET
												+ " ganha a rodada.");
										rodada2 = 2;
										pontuacao2 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\tO Bot recusou o 6, O" + ANSI_GREEN + " Time 1" + ANSI_RESET
										+ " ganha a rodada.");
								rodada1 = 2;
								pontuacao1 += pontosRodada;
								return;
							}
						}

					} else {
						System.out.println(
								"\tO Bot recusou o truco. O" + ANSI_RED + " Time 2" + ANSI_RESET + " ganha a rodada.");
						rodada2 = 2;
						pontuacao2 += pontosRodada;
						return;
					}
					trucoAtivo = true;
				}
			} else if (trucoAtivo == true) {
				if (flag == 2) {
					if (pedir61 && !pedir92 && !pedir121) {
						pedir92 = bot1.pedir9();
						if (pedir92) {
							aceitar91 = jogador2.aceitar9(sc);
							if (aceitar91) {
								pontosRodada = 9;
								pedir121 = jogador2.pedir12(sc);
								if (pedir121) {
									aceitar122 = bot1.aceitar12();
									if (aceitar122) {
										pontosRodada = 12;
									} else {
										System.out.println("\tO Bot recusou o 12, O" + ANSI_GREEN + " Time 1"
												+ ANSI_RESET + " ganha a rodada.");
										rodada1 = 2;
										pontuacao1 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\tO Bot recusou o 9, O" + ANSI_RED + " Time 2" + ANSI_RESET
										+ " ganha a rodada.");
								rodada2 = 2;
								pontuacao2 += pontosRodada;
								return;
							}
						}
					}
				} else if (flag == 1) {
					if (!pedir62 && !pedir91 && !pedir122) {
						pedir62 = bot1.pedir6();
						if (pedir62) {
							aceitar61 = jogador2.aceitar6(sc);
							if (aceitar61) {
								pontosRodada = 6;
								pedir92 = bot1.pedir9();
								if (pedir92) {
									aceitar91 = jogador2.aceitar9(sc);
									if (aceitar91) {
										pontosRodada = 9;
										pedir122 = bot1.pedir12();
										if (pedir122) {
											aceitar121 = jogador2.aceitar12(sc);
											if (aceitar121) {
												pontosRodada = 12;
											} else {
												System.out.println("\tO Bot recusou o 12, O" + ANSI_RED + " Time 2"
														+ ANSI_RESET + " ganha a rodada.");
												rodada2 = 2;
												pontuacao2 += pontosRodada;
												return;
											}
										}
									} else {
										System.out.println("\tO Bot recusou o 9, O" + ANSI_RED + " Time 2" + ANSI_RESET
												+ " ganha a rodada.");
										rodada1 = 2;
										pontuacao2 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\tO Bot recusou o 6, O" + ANSI_RED + " Time 2" + ANSI_RESET
										+ " ganha a rodada.");
								rodada2 = 2;
								pontuacao2 += pontosRodada;
								return;
							}
						}
					} else if (pedir91 && !pedir122) {
						pedir122 = bot1.pedir12();
						if (pedir122) {
							aceitar121 = jogador2.aceitar12(sc);
							if (aceitar121) {
								pontosRodada = 12;
							} else {
								System.out.println("\tO Bot recusou o 12, O" + ANSI_RED + " Time 2" + ANSI_RESET
										+ " ganha a rodada.");
								rodada2 = 2;
								pontuacao2 += pontosRodada;
								return;
							}
						}
					}
				}
			}
			int resultadoBot1 = bot1.jogarCarta(bot1.getBotHand());
			if (trucoAtivo == false) { // De agora em diante, faz a mesma verificação.
				pedidodeTruco = jogador2.truco(sc); // Verificação se vai pedir ou não truco.
				if (pedidodeTruco) { // Caso aceite, a rodada vale 3.
					flag = 1;
					System.out.println(ANSI_GREEN + "\n\tTruco!!!" + ANSI_RESET);
					aceitaTruco = bot2.aceitarTruco(); // Se houver a recusa, o outro time ganha a rodada
														// automaticamente.
					if (aceitaTruco) {
						pontosRodada = 3;
						pedir62 = bot2.pedir6(); // Condições para saber o valor da rodada.
						if (pedir62) {
							aceitar61 = jogador2.aceitar6(sc);
							if (aceitar61) {
								pontosRodada = 6;
								pedir91 = jogador2.pedir9(sc);
								if (pedir91) {
									aceitar92 = bot2.aceitar9();
									if (aceitar92) {
										pontosRodada = 9;
										pedir122 = bot2.pedir12();
										if (pedir121) {
											aceitar121 = jogador2.aceitar12(sc);
											if (aceitar121) {
												pontosRodada = 12;
											} else {
												System.out.println("\tO Bot recusou o 12, O" + ANSI_RED + " Time 2"
														+ ANSI_RESET + " ganha a rodada.");
												rodada2 = 2;
												pontuacao2 += pontosRodada;
												return;
											}
										}
									} else {
										System.out.println("\tO Bot recusou o 9, O" + ANSI_GREEN + " Time 1"
												+ ANSI_RESET + " ganha a rodada.");
										rodada1 = 2;
										pontuacao1 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\tO Bot recusou o 6, O" + ANSI_RED + " Time 2" + ANSI_RESET
										+ " ganha a rodada.");
								rodada2 = 2;
								pontuacao2 += pontosRodada;
								return;
							}
						}

					} else {
						System.out.println("\tO Bot recusou o truco. O" + ANSI_GREEN + " Time 1" + ANSI_RESET
								+ " ganha a rodada.");
						rodada1 = 2;
						pontuacao1 += pontosRodada;
						return;
					}
					trucoAtivo = true;
				}
			} else if (trucoAtivo == true) {
				if (flag == 2) {
					if (pedir62 && !pedir91 && !pedir122) {
						pedir91 = jogador2.pedir9(sc);
						if (pedir91) {
							aceitar92 = bot2.aceitar9();
							if (aceitar92) {
								pontosRodada = 9;
								pedir122 = bot2.pedir12();
								if (pedir122) {
									aceitar121 = jogador2.aceitar12(sc);
									if (aceitar121) {
										pontosRodada = 12;
									} else {
										System.out.println("\tO Bot recusou o 12, O" + ANSI_RED + " Time 2" + ANSI_RESET
												+ " ganha a rodada.");
										rodada2 = 2;
										pontuacao2 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\tO Bot recusou o 9, O" + ANSI_GREEN + " Time 1" + ANSI_RESET
										+ " ganha a rodada.");
								rodada1 = 2;
								pontuacao1 += pontosRodada;
								return;
							}
						}
					}
				} else if (flag == 1) {
					if (pedir92 && !pedir121) {
						pedir121 = jogador2.pedir12(sc);
						if (pedir121) {
							aceitar122 = bot2.aceitar12();
							if (aceitar122) {
								pontosRodada = 12;
							} else {
								System.out.println("\tO Bot recusou o 12, O" + ANSI_GREEN + " Time 1" + ANSI_RESET
										+ " ganha a rodada.");
								rodada1 = 2;
								pontuacao1 += pontosRodada;
								return;
							}

						}
					}
				}
			}
			int resultadoJogador2 = jogador2.playerTurn(jogador2.getPlayerHand(), sc);
			if (trucoAtivo == false) { // De agora em diante, faz a mesma verificação.
				pedidodeTruco = bot2.pedirTruco(); // Verificação se vai pedir ou não truco.
				if (pedidodeTruco) { // Caso aceite, a rodada vale 3.
					flag = 2;
					System.out.println(ANSI_RED + "\n\tTruco!!!" + ANSI_RESET);
					aceitaTruco = jogador1.aceitarTruco(sc); // Se houver a recusa, o outro time ganha a rodada
					// automaticamente.
					if (aceitaTruco) {
						pontosRodada = 3;
						pedir61 = jogador1.pedir6(sc); // Condições para saber o valor da rodada.
						if (pedir61) {
							aceitar62 = bot2.aceitar6();
							if (aceitar62) {
								pontosRodada = 6;
								pedir92 = bot2.pedir9();
								if (pedir92) {
									aceitar91 = jogador1.aceitar9(sc);
									if (aceitar91) {
										pontosRodada = 9;
										pedir121 = jogador1.pedir12(sc);
										if (pedir121) {
											aceitar122 = bot2.aceitar12();
											if (aceitar121) {
												pontosRodada = 12;
											} else {
												System.out.println("\tO Bot recusou o 12, O" + ANSI_GREEN + " Time 1"
														+ ANSI_RESET + " ganha a rodada.");
												rodada1 = 2;
												pontuacao1 += pontosRodada;
												return;
											}
										}
									} else {
										System.out.println("\tO Jogador recusou o 9, O" + ANSI_RED + " Time 2"
												+ ANSI_RESET + " ganha a rodada.");
										rodada2 = 2;
										pontuacao2 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\tO Bot recusou o 6, O" + ANSI_GREEN + " Time 1" + ANSI_RESET
										+ " ganha a rodada.");
								rodada1 = 2;
								pontuacao1 += pontosRodada;
								return;
							}
						}

					} else {
						System.out.println(
								"\tO Bot recusou o truco. O" + ANSI_RED + " Time 2" + ANSI_RESET + " ganha a rodada.");
						rodada2 = 2;
						pontuacao2 += pontosRodada;
						return;
					}
					trucoAtivo = true;
				}
			} else if (trucoAtivo == true) {
				if (flag == 2) {
					if (pedir61 && !pedir92 && !pedir121) {
						pedir92 = bot2.pedir9();
						if (pedir92) {
							aceitar91 = jogador1.aceitar9(sc);
							if (aceitar91) {
								pontosRodada = 9;
								pedir121 = jogador1.pedir12(sc);
								if (pedir121) {
									aceitar122 = bot2.aceitar12();
									if (aceitar122) {
										pontosRodada = 12;
									} else {
										System.out.println("\tO Bot recusou o 12, O" + ANSI_GREEN + " Time 1"
												+ ANSI_RESET + " ganha a rodada.");
										rodada1 = 2;
										pontuacao1 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\tO Jogador recusou o 9, O" + ANSI_RED + " Time 2" + ANSI_RESET
										+ " ganha a rodada.");
								rodada2 = 2;
								pontuacao2 += pontosRodada;
								return;
							}
						}
					} else if (pedir92 && !pedir121) {
						pedir121 = bot2.pedir12();
						if (pedir121) {
							aceitar122 = bot2.aceitar12();
							if (aceitar122) {
								pontosRodada = 12;
							} else {
								System.out.println("\tO Bot recusou o 12, O" + ANSI_GREEN + " Time 1" + ANSI_RESET
										+ " ganha a rodada.");
								rodada1 = 2;
								pontuacao1 += pontosRodada;
								return;
							}
						}
					}
				}
				if (flag == 1) {
					if (!pedir62 && pedir91 && !pedir122) {
						pedir62 = bot2.pedir6();
						if (pedir62) {
							aceitar61 = jogador1.aceitar6(sc);
							if (aceitar61) {
								pontosRodada = 6;
								pedir91 = jogador1.pedir9(sc);
								if (pedir91) {
									aceitar92 = bot2.aceitar9();
									if (aceitar92) {
										pontosRodada = 9;
										pedir122 = bot2.pedir12();
										if (pedir122) {
											aceitar121 = jogador1.aceitar12(sc);
											if (aceitar121) {
												pontosRodada = 12;
											} else {
												System.out.println("\tO Jogador recusou o 12, O" + ANSI_RED + " Time 2"
														+ ANSI_RESET + " ganha a rodada.");
												rodada2 = 2;
												pontuacao2 += pontosRodada;
												return;
											}
										}
									} else {
										System.out.println("\tO Bot recusou o 9, O" + ANSI_GREEN + " Time 1"
												+ ANSI_RESET + " ganha a rodada.");
										rodada2 = 2;
										pontuacao1 += pontosRodada;
										return;
									}
								}
							} else {
								System.out.println("\tO Jogador recusou o 6, O" + ANSI_RED + " Time 2" + ANSI_RESET
										+ " ganha a rodada.");
								rodada2 = 2;
								pontuacao2 += pontosRodada;
								return;
							}

						}
					}
				}
			}
			int resultadoBot3 = bot2.jogarCarta(bot2.getBotHand());
			int maiorCartaTime1 = Math.max(resultadoPlayer, resultadoJogador2); // Compara qual é a maior carta jogada
																				// pelo
			// time.
			int maiorCartaTime2 = Math.max(resultadoBot1, resultadoBot3);

			if (rodadas < 1) { // Faz a verificação do vencedor da primeira rodada, para caso melar a segunda
								// rodada ou a última, o time que ganhou a primeira rodada ganhe o ponto
				if (maiorCartaTime1 > maiorCartaTime2) {
					primeirovencedor = 1;
				} else if (maiorCartaTime1 < maiorCartaTime2) {
					primeirovencedor = 2;
				}
			}

			if (maiorCartaTime1 > maiorCartaTime2) { // Compara o valor das cartas para decidir o vencedor
				rodadas++;
				rodada1++;
				System.out.println("\n\tO " + ANSI_GREEN + "Time 1" + ANSI_RESET + " ganhou a rodada!");
			} else if (maiorCartaTime1 < maiorCartaTime2) {
				rodada2++;
				rodadas++;
				System.out.println("\n\tO " + ANSI_RED + " Time 2" + ANSI_RESET + " ganhou a rodada!");
			} else if (maiorCartaTime1 == maiorCartaTime2) {
				System.out.println(ANSI_MAGENTA + "\n\tA rodada melou!" + ANSI_RESET);
				pontoEmpate++;
				rodadas++;
			}

			if (rodadas == 3 && pontoEmpate == 1) { // Caso a rodada 3 empate, o primeiro vencedor vence.
				if (primeirovencedor == 1) {
					rodada1 = 2;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (primeirovencedor == 2) {

					rodada2 = 2;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				}

				if (pontoEmpate == 3 && rodadas == 3) { // Caso todas as rodadas melem, a rodada inteira mela e ninguém
														// ganha o ponto.

					System.out.println(ANSI_MAGENTA + "A rodada interia melou, ninguém recebe ponto." + ANSI_RESET);
					return;
				}
			}

			if (rodadas == 2 && pontoEmpate == 1) { // Caso o mele a segunda rodada, o vencedor será o da primeira
													// rodada.
				if (primeirovencedor == 1) {
					rodada1 = 2;
					pontuacao1 = pontosRodada;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (primeirovencedor == 2) {
					rodada2 = 2;
					pontuacao2 = pontosRodada;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a primeira rodada.");

				} else if (rodada1 > rodada2) { // Caso mele a primeira rodada, o time que ganhar a próxima rodada
												// ganhará o ponto.
					rodada1 = 2;
					pontuacao1 = pontosRodada;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha o ponto por ter ganho a rodada após a primeira ter melado.");
				} else if (rodada1 < rodada2) {
					rodada2 = 2;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha o ponto por ter ganho a rodada após a primeira ter melado.");
				}
			}

			if (rodadas == 3 && pontoEmpate == 2) { // Caso a primeiras duas rodadas empatarem, o ganhador da última
													// será o vencedor.
				if (rodada1 > rodada2) {
					rodada1 = 2;
					pontuacao1 = pontosRodada;
					System.out.println("\n\tO" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha a rodada, por ter vencido a única rodada que não melou.");

				} else if (rodada2 > rodada1) {
					rodada2 = 2;
					pontuacao2 = pontosRodada;
					System.out.println("\n\tO" + ANSI_RED + " Time 2" + ANSI_RESET
							+ " ganha a rodada, por ter vencido a única rodada que não melou.");
				}
			}

			if (rodada1 == 2) { // Define o ganhador da rodada.
				pontuacao1 += pontosRodada;
			} else if (rodada2 == 2) {
				pontuacao2 += pontosRodada;
			}
		} // Reseta os valores que são precisos para ter o critério de desempate
		rodadas = 0;
		pontoEmpate = 0;
		return;
	}

	public static void resetarRodada1(Deck deck, Player jogador1, Bot bot1, Bot bot2, Bot bot3) { // função que reseta
																									// as mãos e o
																									// baralho
		deck.reset();
		jogador1.reset();
		bot1.reset();
		bot2.reset();
		bot3.reset();

		rodada1 = 0;
		rodada2 = 0;
	}

	public static void resetarRodada2(Deck deck, Player jogador1, Bot bot1, Player jogador2, Bot bot3) {// função que
																										// reseta as
																										// mãos
																										// e o baralho
		deck.reset();
		jogador1.reset();
		bot1.reset();
		jogador2.reset();
		bot3.reset();

		rodada1 = 0;
		rodada2 = 0;
	}

}