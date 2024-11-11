package application;

import java.util.List;
import java.util.Scanner;
import entities.Bot;
import entities.Card;
import entities.Deck;
import entities.Player;

public class Truco {
	static String vira;
	static String manilhas;
	static int pontuacao1 = 0;
	static int pontuacao2 = 0;
	static int rodada1 = 0;
	static int rodada2 = 0;
	static int rodadas = 0;

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

		System.out.printf("\tSerão 1 ou 2 jogadores? ");
		int jog = sc.nextInt();
		sc.nextLine();

		if (jog == 1) {
			Player jogador1 = new Player("Jogador 1");
			Bot bot1 = new Bot();
			Bot bot2 = new Bot();
			Bot bot3 = new Bot();

			while (pontuacao1 < 12 && pontuacao2 < 12) {

				distribuirCartas1(deck, jogador1, bot1, bot2, bot3);

				System.out.println("\n\tO vira é: " + vira);
				System.out.println("\n\tAs manilhas são:");
				System.out.println("\n\t●" + manilhas + " de Copas\n\t●" + manilhas + " de Ouro\n\t●" + manilhas
						+ " de Paus\n\t●" + manilhas + " de Espadas\n");

				jogador1.mostraraMao(jogador1.getMao());

				rodada1 = 0;
				rodada2 = 0;

				while (rodada1 < 2 && rodada2 < 2) {
					realizarRodada1(jogador1, bot1, bot2, bot3, sc);
				}

				System.out.println("\n\tPontuação atual: \n\tTime 1: " + pontuacao1 + "\n\tTime 2: " + pontuacao2);

				resetarRodada1(deck, jogador1, bot1, bot2, bot3);
			}

			if (pontuacao1 >= 12) {
				System.out.println("\n\tTime 1 venceu o jogo!");
			} else {
				System.out.println("\n\tTime 2 venceu o jogo!");
			}
			pontuacao1 = 0;
			pontuacao2 = 0;
		} else if (jog == 2) {
			Player jogador1 = new Player("Jogador 1");
			Bot bot1 = new Bot();
			Player jogador2 = new Player("Jogador 2");
			Bot bot3 = new Bot();

			while (pontuacao1 < 12 && pontuacao2 < 12) {

				distribuirCartas2(deck, jogador1, bot1, jogador2, bot3);

				System.out.println("\n\tO vira é: " + vira);
				System.out.println("\n\tAs manilhas são:");
				System.out.println("\n\t●" + manilhas + " de Copas\n\t●" + manilhas + " de Ouro\n\t●" + manilhas
						+ " de Paus\n\t●" + manilhas + " de Espadas\n");

				jogador1.mostraraMao(jogador1.getMao());

				rodada1 = 0;
				rodada2 = 0;
				rodadas = 0;

				while (rodada1 < 2 && rodada2 < 2) {
					realizarRodada2(jogador1, bot1, jogador2, bot3, sc);
				}

				System.out.println("\n\tPontuação atual: \n\tTime 1: " + pontuacao1 + "\n\tTime 2: " + pontuacao2);

				resetarRodada2(deck, jogador1, bot1, jogador2, bot3);
			}

			if (pontuacao1 >= 12) {
				System.out.println("\n\tTime 1 venceu o jogo!");
			} else {
				System.out.println("\n\tTime 2 venceu o jogo!");
			}
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
	static int pontoEmpate = 0;
	static int primeirovencedor;

	public static void realizarRodada1(Player jogador1, Bot bot1, Bot bot2, Bot bot3, Scanner sc) {
		int pontosRodada = 1;
		boolean trucoAtivo = false;
		boolean pedidodeTruco = false;
		boolean aceitaTruco = false;
		int pontoEmpate = 0;
		int primeirovencedor = 0;

		while (rodada1 < 2 && rodada2 < 2) {

			if (trucoAtivo == false) {
				pedidodeTruco = jogador1.truco(sc);
				if (pedidodeTruco) {

					pontosRodada = 3;

					System.out.println("\n\tTruco!!!");
					aceitaTruco = bot1.aceitarTruco();
					if (!aceitaTruco) {
						System.out.println("\n\tO Bot recusou o truco. O time 1 ganha a rodada.");
						rodada1 = 2;
						pontuacao1 += 1;
						return;
					}
					trucoAtivo = true;
				}

			}
			int resultadoPlayer = jogador1.playerTurn(jogador1.getMao(), sc);
			if (trucoAtivo == false) {
				pedidodeTruco = bot1.pedirTruco();
				if (pedidodeTruco) {

					pontosRodada = 3;
					System.out.println("\n\tTruco!!!");
					aceitaTruco = bot2.aceitarTruco();
					if (!aceitaTruco) {
						System.out.println("\n\tO Bot recusou o truco. O time 2 ganha a rodada.");
						rodada2 = 2;
						pontuacao2 += 1;
						return;
					}
					trucoAtivo = true;
				}

			}
			int resultadoBot1 = bot1.jogarCarta(bot1.getMaodobot());
			if (trucoAtivo == false) {
				pedidodeTruco = bot2.pedirTruco();
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
			int resultadoBot2 = bot2.jogarCarta(bot2.getMaodobot());
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
			int resultadoBot3 = bot3.jogarCarta(bot3.getMaodobot());
			int maiorCartaTime1 = Math.max(resultadoPlayer, resultadoBot2);
			int maiorCartaTime2 = Math.max(resultadoBot1, resultadoBot3);

			if (rodadas < 1) {
				// System.out.println("\n\tEntrei no primeiro vencedor.");
				if (maiorCartaTime1 > maiorCartaTime2) {
					primeirovencedor = 1;
				} else if (maiorCartaTime1 < maiorCartaTime2) {
					primeirovencedor = 2;
				}
			}

			if (maiorCartaTime1 > maiorCartaTime2) {
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

			if (rodadas == 3 && pontoEmpate == 1) {
				// System.out.println("\n\tEntrei caso tenham empatado 1 rodada.");
				if (primeirovencedor == 1) {
					if (trucoAtivo) {
						rodada1 = 2;
						System.out.println("\n\tO time 1 ganha o ponto por ter ganho a primeira rodada.");
						pontuacao1 += pontosRodada;

					} else {
						rodada1 = 2;
						System.out.println("\n\tO time 1 ganha o ponto por ter ganho a primeira rodada.");
						pontuacao1 += pontoEmpate;

					}
				} else if (primeirovencedor == 2) {
					if (trucoAtivo) {
						rodada2 = 2;
						System.out.println("\n\tO time 2 ganha o ponto por ter ganho a primeira rodada.");
						pontuacao2 += pontosRodada;

					} else {
						rodada2 = 2;
						System.out.println("\n\tO time 2 ganha o ponto por ter ganho a primeira rodada.");
						pontuacao2 += pontoEmpate;

					}
				}

				if (pontoEmpate == 3 && rodadas == 3) {
					// System.out.println("\n\tEntrei caso tenham empatado 3 rodadas");
					System.out.println("A rodada interia melou, ninguém recebe ponto.");
				}
			}

			if (rodadas == 2 && pontoEmpate == 1) {
				// System.out.println("\n\tEntrei caso o primeiroVencedor tenha empatado na
				// segunda rodada.");
				if (primeirovencedor == 1) {
					rodada1 = 2;
					System.out.println("\n\tO time 1 ganha o ponto por ter ganho a primeira rodada.");

				} else if (primeirovencedor == 2) {
					rodada2 = 2;
					System.out.println("\n\tO time 2 ganha o ponto por ter ganho a primeira rodada.");

				}
			}

			if (rodada1 == 2) {
				pontuacao1 += pontosRodada;
			} else if (rodada2 == 2) {
				pontuacao2 += pontosRodada;
			}
		}
		rodadas = 0;
		pontoEmpate = 0;
		return;
	}

	public static void realizarRodada2(Player jogador1, Bot bot1, Player jogador2, Bot bot3, Scanner sc) {
		int pontosRodada = 1;
		boolean trucoAtivo = false;
		boolean pedidodeTruco = false;
		boolean aceitaTruco = false;
		int pontoEmpate = 0;
		int primeirovencedor = 0;

		while (rodada1 < 2 && rodada2 < 2) {

			if (trucoAtivo == false) {
				pedidodeTruco = jogador1.truco(sc);
				if (pedidodeTruco) {

					pontosRodada = 3;

					System.out.println("\n\tTruco!!!");
					aceitaTruco = bot1.aceitarTruco();
					if (!aceitaTruco) {
						System.out.println("\n\tO Bot recusou o truco. Time 1 ganha a rodada.");
						rodada1 = 2;
						pontuacao1 += 1;
						return;
					}
					trucoAtivo = true;
				}

			}
			int resultadoPlayer1 = jogador1.playerTurn(jogador1.getMao(), sc);
			if (trucoAtivo == false) {
				pedidodeTruco = bot1.pedirTruco();
				if (pedidodeTruco) {

					pontosRodada = 3;
					System.out.println("\n\tTruco!!!");
					jogador2.mostraraMao(jogador2.getMao());
					aceitaTruco = jogador2.aceitarTruco(sc);
					if (!aceitaTruco) {
						System.out.println("\n\tO Bot recusou o truco. Time 2 ganha a rodada.");
						rodada2 = 2;
						pontuacao2 += 1;
						return;
					}
					trucoAtivo = true;
				}

			}
			int resultadoBot1 = bot1.jogarCarta(bot1.getMaodobot());
			if (trucoAtivo == false) {
				pedidodeTruco = jogador2.truco(sc);
				if (pedidodeTruco) {

					pontosRodada = 3;
					System.out.println("\n\tTruco!!!");
					aceitaTruco = bot3.aceitarTruco();
					if (!aceitaTruco) {
						System.out.println("\n\tO Jogador 2 recusou o truco. Time 1 ganha a rodada.");
						rodada1 = 2;
						pontuacao1 += 1;
						return;
					}
					trucoAtivo = true;
				}

			}
			int resultadoPlayer2 = jogador2.playerTurn(jogador2.getMao(), sc);
			if (trucoAtivo == false) {
				pedidodeTruco = bot3.pedirTruco();
				if (pedidodeTruco) {

					pontosRodada = 3;
					System.out.println("\n\tTruco!!!");
					aceitaTruco = jogador1.aceitarTruco(sc);
					if (!aceitaTruco) {
						System.out.println("\n\tO Jogador 1 recusou o truco. Time 2 ganha a rodada.");
						rodada2 = 2;
						pontuacao2 += 1;
						return;
					}
					trucoAtivo = true;
				}

			}
			int resultadoBot3 = bot3.jogarCarta(bot3.getMaodobot());
			int maiorCartaTime1 = Math.max(resultadoPlayer1, resultadoPlayer2);
			int maiorCartaTime2 = Math.max(resultadoBot1, resultadoBot3);

			if (rodada1 + rodada2 == 0) {
				if (maiorCartaTime1 > maiorCartaTime2) {
					primeirovencedor = 1;
				} else if (maiorCartaTime1 < maiorCartaTime2) {
					primeirovencedor = 2;
				}
			}

			if (maiorCartaTime1 > maiorCartaTime2) {
				rodadas++;
				rodada1++;
				System.out.println("\n\tO time 1 ganhou a rodada!");
			} else if (maiorCartaTime1 < maiorCartaTime2) {
				rodada2++;
				rodadas++;
				System.out.println("\n\tO time 2 ganhou a rodada!");
			} else {
				System.out.println("\n\tA rodada melou!");
				pontoEmpate++;
				rodadas++;

				if (rodadas == 3) {
					if (primeirovencedor == 1) {
						rodada1 = 2;
						System.out.println("\n\tO time 1 ganha o ponto por ter ganho a primeira rodada.");
						pontuacao1 += pontoEmpate;
						return;
					} else if (primeirovencedor == 2) {
						rodada2 = 2;
						System.out.println("\n\tO time 2 ganha o ponto por ter ganho a primeira rodada.");
						pontuacao2 += pontoEmpate;
						return;
					} else if (rodada1 > rodada2) {
						rodada1 = 2;
						System.out.println("\n\tO time 1 ganha o ponto por ter ganho uma rodada.");
						pontuacao1 += pontosRodada;
						return;
					} else if (rodada1 < rodada2) {
						rodada2 = 2;
						System.out.println("\n\tO time 2 ganha o ponto por ter ganho uma rodada.");
						pontuacao2 += pontosRodada;
						return;
					} else if (pontoEmpate == 3) {
						System.out.println("A rodada inteira melou, ninguém recebe ponto.");
						return;
					}
					pontoEmpate = 0;
					return;

				} else if (rodadas == 2) {
					if (pontoEmpate == 1) {
						if (rodada1 == 1) {
							rodada1 = 2;
							System.out.println("\n\tO time 1 ganha o ponto por ter ganho a primeira rodada.");
							return;
						} else {
							rodada2 = 2;
							System.out.println("\n\tO time 2 ganha o ponto por ter ganho a primeira rodada.");
							return;
						}
					}
				}
			}

			if (rodada1 == 2) {
				pontuacao1 += pontosRodada;
			} else if (rodada2 == 2) {
				pontuacao2 += pontosRodada;
			}
		}
	}

	public static void resetarRodada1(Deck deck, Player jogador1, Bot bot1, Bot bot2, Bot bot3) {
		deck.reset();
		distribuirCartas1(deck, jogador1, bot1, bot2, bot3);
		rodada1 = 0;
		rodada2 = 0;
	}

	public static void resetarRodada2(Deck deck, Player jogador1, Bot bot1, Player jogador2, Bot bot3) {
		deck.reset();
		distribuirCartas2(deck, jogador1, bot1, jogador2, bot3);
		rodada1 = 0;
		rodada2 = 0;
		System.out.println("\n\tO vira é: " + vira);
	}

}
