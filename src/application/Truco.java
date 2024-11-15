package application;

import java.util.Scanner;
import entities.Bot;
import entities.Card;
import entities.Deck;
import entities.Player;

public class Truco {
	static Card vira;
	static String manilhas;
	static int pontuacao1 = 0;
	static int pontuacao2 = 0;
	static int rodada1 = 0;
	static int rodada2 = 0;
	static int rodadas = 0;
	static int pontos = 0;
	static int pontoEmpate = 0;
	static int primeirovencedor;
	static final String ANSI_RESET = "\u001B[0m";
	static final String ANSI_RED = "\u001B[31m";
	static final String ANSI_GREEN = "\u001B[32m";
	static final String ANSI_MAGENTA = "\u001B[35m";
	static final String ANSI_CYAN = "\u001B[36m";
	static final String ANSI_YELLOW = "\u001B[33m";
	static final String ANSI_BLACK = "\u001B[30m";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		char resp;
		do { // Inicializa o jogo.
			System.out.println("\t\t=========Menu=========");
			System.out.printf("\tDeseja jogar Truco? (s/n): ");
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
		do {
			System.out.printf("\tSerão 1 ou 2 jogadores? ");
			jog = sc.nextInt();
			sc.nextLine();
		} while (jog != 1 && jog != 2);
		if (jog == 1) { // Inicializa os objetos.
			Player jogador1 = new Player(deck, "Jogador 1");
			Bot bot1 = new Bot();
			Bot bot2 = new Bot();
			Bot bot3 = new Bot();

			while (pontuacao1 < 12 && pontuacao2 < 12) { // Enquanto a pontuação for menor que 12, o jogo continua.

				distribuirCartas1(deck, jogador1, bot1, bot2, bot3); // Distribui as cartas pros jogadores.
				System.out.println("\n\tO vira é: " + vira);
				System.out.println("\n\tAs manilhas são:");
				System.out.println(
						"\n\t●" + manilhas + " de " + ANSI_RED + "Copas ♥" + ANSI_RESET + "\n\t●" + manilhas + " de "
								+ ANSI_BLACK + "Paus ♣" + ANSI_RESET + "\n\t●" + manilhas + " de " + ANSI_RED + "Ouro ♦"
								+ ANSI_RESET + "\n\t●" + manilhas + " de " + ANSI_BLACK + "Espadas ♠\n" + ANSI_RESET);
				System.out.println(jogador1.toString());
				rodada1 = 0;
				rodada2 = 0;

				while (rodada1 < 2 && rodada2 < 2) {

					realizarRodada1(jogador1, bot1, bot2, bot3, sc); // Função que realiza a rodada com apenas um
																		// jogador.
				}

				System.out.println("\n\t" + ANSI_YELLOW + "Pontuação atual: \n\t" + ANSI_GREEN + "Time 1: " + pontuacao1
						+ ANSI_RESET + "\n\t" + ANSI_RED + "Time 2: " + pontuacao2 + ANSI_RESET);

				resetarRodada1(deck, jogador1, bot1, bot2, bot3); // Reseta a rodada, resetando a mão e o deck.
			}

			if (pontuacao1 >= 12) {
				System.out.println("\n\t" + ANSI_GREEN + "Time 1 venceu o jogo!" + ANSI_RESET);
			} else {
				System.out.println("\n\t" + ANSI_RED + "Time 2 venceu o jogo!" + ANSI_RESET);
			}
			pontuacao1 = 0;
			pontuacao2 = 0;

			System.out.println(
					ANSI_BLACK + "===================================================================" + ANSI_RESET);
			System.out.println("\n\tDeseja jogar novamente?");
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

		} else if (jog == 2) {
			Player jogador1 = new Player(deck, "Jogador 1");
			Bot bot1 = new Bot();
			Player jogador2 = new Player(deck, "Jogador 2");
			Bot bot2 = new Bot();

			while (pontuacao1 < 12 && pontuacao2 < 12) {

				distribuirCartas2(deck, jogador1, bot1, jogador2, bot2);

				System.out.println("\n\tO vira é: " + vira);
				System.out.println("\n\tAs manilhas são:");
				System.out.println(
						"\n\t●" + manilhas + " de " + ANSI_RED + "Copas ♥" + ANSI_RESET + "\n\t●" + manilhas + " de "
								+ ANSI_RED + "Ouro ♦" + ANSI_RESET + "\n\t●" + manilhas + " de " + ANSI_BLACK + "Paus ♣"
								+ ANSI_RESET + "\n\t●" + manilhas + " de " + ANSI_BLACK + "Espadas ♠\n" + ANSI_RESET);
				System.out.println(jogador1.toString());
				rodada1 = 0;
				rodada2 = 0;
				rodadas = 0;

				while (rodada1 < 2 && rodada2 < 2) {
					realizarRodada2(jogador1, bot1, jogador2, bot2, sc); // Função que realiza a rodada com dois
																			// jogadores.
				}

				System.out.println("\n\t" + ANSI_YELLOW + "Pontuação atual:" + ANSI_GREEN + "\n\tTime 1: " + pontuacao1
						+ ANSI_RESET + ANSI_RED + "\n\tTime 2: " + pontuacao2 + ANSI_RESET);

				resetarRodada2(deck, jogador1, bot1, jogador2, bot2); // Reseta a rodada, resetando a mão e o deck.
			}

			if (pontuacao1 >= 12) {
				System.out.println("\n\t" + ANSI_GREEN + "O Time 1 venceu o jogo!");
			} else {
				System.out.println("\n\t" + ANSI_RED + "O Time 2 venceu o jogo!");
			}
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

		vira = deck.dealCard();
		deck.setVira(vira);
		manilhas = deck.getManilha();
	}

	public static void distribuirCartas2(Deck deck, Player jogador1, Bot bot1, Player jogador2, Bot bot3) {
		// Distribui as cartas para os jogadores.
		jogador1.setPlayerHand(deck);
		jogador1.getPlayerHand();
		bot1.setBotHand(deck);
		bot1.getBotHand();
		jogador2.setPlayerHand(deck);
		jogador2.getPlayerHand();
		bot3.setBotHand(deck);
		bot3.getBotHand();

		vira = deck.dealCard();
		deck.setVira(vira);
		manilhas = deck.getManilha();
	}

	public static void realizarRodada1(Player jogador1, Bot bot1, Bot bot2, Bot bot3, Scanner sc) {
		// Rezliza a rodada com 1 jogador só.
		int pontosRodada = 1;
		boolean trucoAtivo = false;
		boolean pedidodeTruco = false;
		boolean aceitaTruco = false;
		int pontoEmpate = 0;
		int primeirovencedor = 0;
		// Até rodada 1 ou rodada 2 menor que 2, continua a rodada.
		while (rodada1 < 2 && rodada2 < 2) {
			if (trucoAtivo == false) {
				pedidodeTruco = jogador1.truco(sc); // Verificação se vai pedir ou não truco.
				if (pedidodeTruco) { // Caso aceite, a rodada vale 3.
					pontosRodada = 3;
					System.out.println("\n\tTruco!!!");
					aceitaTruco = bot1.aceitarTruco(); // Se houver a recusa, o outro time ganha a rodada
														// automaticamente.
					if (!aceitaTruco) {
						System.out.println("\n\tO Bot recusou o truco. O" + ANSI_GREEN + " Time 1" + ANSI_RESET
								+ " ganha a rodada.");
						rodada1 = 2;
						pontuacao1 += 1;
						return;
					}
					trucoAtivo = true;
				}

			}
			int resultadoPlayer = jogador1.playerTurn(jogador1.getPlayerHand(), sc);
			if (trucoAtivo == false) { // De agora em diante, faz a mesma verificação.
				pedidodeTruco = bot1.pedirTruco();
				if (pedidodeTruco) {
					pontosRodada = 3;
					System.out.println("\n\tTruco!!!");
					aceitaTruco = bot2.aceitarTruco();
					if (!aceitaTruco) {
						System.out.println("\n\tO Bot recusou o truco." + ANSI_RED + " O Time 2" + ANSI_RESET
								+ " ganha a rodada.");
						rodada2 = 2;
						pontuacao2 += 1;
						return;
					}
					trucoAtivo = true;
				}

			}
			int resultadoBot1 = bot1.jogarCarta(bot1.getBotHand());
			if (trucoAtivo == false) {
				pedidodeTruco = bot2.pedirTruco();
				if (pedidodeTruco) {
					pontosRodada = 3;
					System.out.println("\n\tTruco!!!");
					aceitaTruco = bot3.aceitarTruco();
					if (!aceitaTruco) {
						System.out.println(
								"\n\t O Bot recusou o truco. O" + ANSI_GREEN + " Time 1 ganha a rodada." + ANSI_RESET);
						rodada1 = 2;
						pontuacao1 += 1;
						return;
					}
					trucoAtivo = true;
				}

			}
			int resultadoBot2 = bot2.jogarCarta(bot2.getBotHand());
			if (trucoAtivo == false) {
				pedidodeTruco = bot3.pedirTruco();
				if (pedidodeTruco) {
					pontosRodada = 3;
					System.out.println("\n\tTruco!!!");
					aceitaTruco = jogador1.aceitarTruco(sc);
					if (!aceitaTruco) {
						System.out.println(
								"\n\tO Jogador recusou o truco. O" + ANSI_RED + " Time 2 ganha a rodada." + ANSI_RESET);
						rodada2 = 2;
						pontuacao2 += 1;
						return;
					}
					trucoAtivo = true;
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
					System.out.println("\n\t" + ANSI_GREEN + " Time 1" + ANSI_RESET
							+ " ganha a rodada, por ter vencido a única rodada que não melou.");

				} else if (rodada2 > rodada1) {
					rodada2 = 2;
					System.out.println("\n\t" + ANSI_RED + " Time 2" + ANSI_RESET
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

	public static void realizarRodada2(Player jogador1, Bot bot1, Player jogador2, Bot bot3, Scanner sc) {
		// Realiza a rodada com 1 jogador só.
		int pontosRodada = 1;
		boolean trucoAtivo = false;
		boolean pedidodeTruco = false;
		boolean aceitaTruco = false;
		int pontoEmpate = 0;
		int primeirovencedor = 0;
		// Até rodada 1 ou rodada 2 menor que 2, continua a rodada.
		while (rodada1 < 2 && rodada2 < 2) {
			if (trucoAtivo == false) {
				pedidodeTruco = jogador1.truco(sc); // Verificação se vai pedir ou não truco.
				if (pedidodeTruco) { // Caso aceite, a rodada vale 3.

					pontosRodada = 3;

					System.out.println("\n\tTruco!!!");
					aceitaTruco = bot1.aceitarTruco(); // Se houver a recusa, o outro time ganha a rodada
														// automaticamente.
					if (!aceitaTruco) {
						System.out.println("\n\tO Bot recusou o truco. O time 1 ganha a rodada.");
						rodada1 = 2;
						pontuacao1 += 1;
						return;
					}
					trucoAtivo = true;
				}

			}
			int resultadoPlayer = jogador1.playerTurn(jogador1.getPlayerHand(), sc);
			if (trucoAtivo == false) { // De agora em diante, faz a mesma verificação.
				pedidodeTruco = bot1.pedirTruco();
				if (pedidodeTruco) {

					pontosRodada = 3;
					System.out.println("\n\tTruco!!!");
					aceitaTruco = jogador2.aceitarTruco(sc);
					if (!aceitaTruco) {
						System.out.println("\n\tO Bot recusou o truco. O time 2 ganha a rodada.");
						rodada2 = 2;
						pontuacao2 += 1;
						return;
					}
					trucoAtivo = true;
				}

			}
			int resultadoBot1 = bot1.jogarCarta(bot1.getBotHand());
			if (trucoAtivo == false) {
				pedidodeTruco = jogador2.truco(sc);
				if (pedidodeTruco) {

					pontosRodada = 3;
					System.out.println("\n\tTruco!!!");
					aceitaTruco = bot3.aceitarTruco();
					if (!aceitaTruco) {
						System.out.println("\n\tO Bot recusou o truco. O time 1 ganha a rodada.");
						rodada1 = 2;
						pontuacao1 += 1;
						return;
					}
					trucoAtivo = true;
				}

			}
			int resultadoBot2 = jogador2.playerTurn(jogador2.getPlayerHand(), sc);
			if (trucoAtivo == false) {
				pedidodeTruco = bot3.pedirTruco();
				if (pedidodeTruco) {

					pontosRodada = 3;
					System.out.println("\n\tTruco!!!");
					aceitaTruco = jogador1.aceitarTruco(sc);
					if (!aceitaTruco) {
						System.out.println("\n\tO Jogador recusou o truco. O time 2 ganha a rodada.");
						rodada2 = 2;
						pontuacao2 += 1;
						return;
					}
					trucoAtivo = true;
				}

			}
			int resultadoBot3 = bot3.jogarCarta(bot3.getBotHand());
			int maiorCartaTime1 = Math.max(resultadoPlayer, resultadoBot2); // Compara qual é a maior carta jogada pelo
																			// time.
			int maiorCartaTime2 = Math.max(resultadoBot1, resultadoBot3);

			if (rodadas < 1) { // Faz a verificação do vencedor da primeira rodada, para caso melar a segunda
								// rodada ou a última, o time que ganhou a primeira rodada ganhe o ponto
				System.out.println("\n\tEntrei no primeiro vencedor.");
				if (maiorCartaTime1 > maiorCartaTime2) {
					primeirovencedor = 1;
				} else if (maiorCartaTime1 < maiorCartaTime2) {
					primeirovencedor = 2;
				}
			}

			if (maiorCartaTime1 > maiorCartaTime2) { // Compara o valor das cartas para decidir o vencedor
				rodadas++;
				rodada1++;
				System.out.println("\n\tO time 1 ganhou a rodada!");
			} else if (maiorCartaTime1 < maiorCartaTime2) {
				rodada2++;
				rodadas++;
				System.out.println("\n\tO time 2 ganhou a rodada!");
			} else if (maiorCartaTime1 == maiorCartaTime2) {
				System.out.println("\n\tA rodada melou!");
				pontoEmpate++;
				rodadas++;
			}

			if (rodadas == 3 && pontoEmpate == 1) { // Caso a rodada 3 empate, o primeiro vencedor vence.
				System.out.println("\n\tEntrei caso tenham empatado 1 rodada.");
				if (primeirovencedor == 1) {
					if (trucoAtivo) {
						rodada1 = 2;
						System.out.println("\n\tO time 1 ganha o ponto por ter ganho a primeira rodada.");

					} else {
						rodada1 = 2;
						System.out.println("\n\tO time 1 ganha o ponto por ter ganho a primeira rodada.");

					}
				} else if (primeirovencedor == 2) {
					if (trucoAtivo) {
						rodada2 = 2;
						System.out.println("\n\tO time 2 ganha o ponto por ter ganho a primeira rodada.");

					} else {
						rodada2 = 2;
						System.out.println("\n\tO time 2 ganha o ponto por ter ganho a primeira rodada.");

					}
				}

				if (pontoEmpate == 3 && rodadas == 3) { // Caso todas as rodadas melem, a rodada inteira mela e ninguém
														// ganha o ponto.
					System.out.println("\n\tEntrei caso tenham empatado 3 rodadas");
					System.out.println("A rodada interia melou, ninguém recebe ponto.");
				}
			}

			if (rodadas == 2 && pontoEmpate == 1) { // Caso o mele a segunda rodada, o vencedor será o da primeira
													// rodada.
				System.out.println("\n\tEntrei caso o primeiroVencedor tenha empatado na segunda rodada.");
				if (primeirovencedor == 1) {
					rodada1 = 2;
					System.out.println("\n\tO time 1 ganha o ponto por ter ganho a primeira rodada.");

				} else if (primeirovencedor == 2) {
					rodada2 = 2;
					System.out.println("\n\tO time 2 ganha o ponto por ter ganho a primeira rodada.");

				}
			}

			if (rodadas == 3 && pontoEmpate == 2) { // Caso a primeiras duas rodadas empatarem, o ganhador da última
													// será o vencedor.
				if (rodada1 > rodada2) {
					if (trucoAtivo) {
						rodada1 = 2;
						System.out.println("\n\t Time 1 ganha a rodada, por ter vencido a única rodada que não melou.");
					} else {
						rodada1 = 2;
						System.out.println("\n\t Time 1 ganha a rodada, por ter vencido a única rodada que não melou.");
					}
				} else if (rodada2 > rodada1) {
					if (trucoAtivo) {
						rodada2 = 2;
						System.out.println("\n\t Time 2 ganha a rodada, por ter vencido a única rodada que não melou.");
					} else {
						rodada2 = 2;
						System.out.println("\n\t Time 2 ganha a rodada, por ter vencido a única rodada que não melou.");
					}
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

	public static void resetarRodada1(Deck deck, Player jogador1, Bot bot1, Bot bot2, Bot bot3) {
		deck.reset();
		jogador1.reset();
		bot1.reset();
		bot2.reset();
		bot3.reset();

		rodada1 = 0;
		rodada2 = 0;
	}

	public static void resetarRodada2(Deck deck, Player jogador1, Bot bot1, Player jogador2, Bot bot3) {
		deck.reset();
		jogador1.reset();
		bot1.reset();
		jogador2.reset();
		bot3.reset();

		rodada1 = 0;
		rodada2 = 0;
		System.out.println("\n\tO vira é: " + vira);
	}

}
