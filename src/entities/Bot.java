package entities;

import java.security.SecureRandom;
import java.util.List;

public class Bot extends Deck {

	private List<Card> maodobot;

	public void receberCartas(List<Card> maodobot) {
		this.setMaodobot(maodobot);
	}

	public List<Card> getMaodobot() {
		return maodobot;
	}

	public void setMaodobot(List<Card> maodobot) {
		this.maodobot = maodobot;
	}

	public void botTurn() {
		System.out.println("\n\tVez do bot.");
		pedirTruco();
		jogarCarta(maodobot);
	}

	public boolean pedirTruco() {
		SecureRandom random = new SecureRandom();
		return random.nextBoolean();
	}

	public boolean aceitarTruco() {
		SecureRandom random = new SecureRandom();
		boolean resp = random.nextBoolean();
		if (resp) {
			System.out.println("\n\tO Bot aceitou o truco!!");
		} else {
			System.out.println("\n\tO Bot não aceitou o truco!!");
		}
		return resp;
	}

	public int jogarCarta(List<Card> maodoBot) {
		SecureRandom random = new SecureRandom();
		int escolha = random.nextInt(maodobot.size());
		Card cartaEscolhida = maodobot.remove(escolha);
		System.out.println("\n\tO Bot jogou " + cartaEscolhida);
		int valorCarta = getCardValue(cartaEscolhida);
		return valorCarta;
	}
}
