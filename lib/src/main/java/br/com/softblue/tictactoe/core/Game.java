package br.com.softblue.tictactoe.core;

import java.io.IOException;

import br.com.softblue.tictactoe.Constantes;
import br.com.softblue.tictactoe.score.FileScoreManager;
import br.com.softblue.tictactoe.score.ScoreManager;
import br.com.softblue.tictactoe.ui.UI;
import br.*;

public class Game {

	private Board board = new Board();
	private Player[] players = new Player[Constantes.SYMBOL_PLAYERS.length];
	private int currentPlayer = 0;
	private ScoreManager scoreManager;
	
	public void play() throws IOException {
		//Cria um arquivo de texto, se não existir, e lê a pontuação do Usuário.
		scoreManager = createScoreManager();
		
		//Imprime o cabeçalho do jogo.
		UI.printGameTitle();
		
		//Define X e O.
		//Chama o método para criar os Usuários.
		for (int i = 0; i < players.length; i++) {
			players[i] = createPlayer(i);
		}
		
		//Comeca o Jogo e Troca o Player
		boolean gameEnded = false;
		Player currentPlayer = nextPlayer();
		Player winner = null;
		
		//Imprime o Tabuleiro
		while(!gameEnded) {
			board.print();
			boolean sequenceFound;
			
			try {
				sequenceFound = currentPlayer.play();
			
			}catch (InvalidMoveException e){
				UI.printText("Erro: " + e.getMessage());
				continue;
			}
			if(sequenceFound) {
				gameEnded = true;
				winner = currentPlayer;
				
			}else if(board.isFull()) {
				gameEnded = true;
			}else {			
			currentPlayer = nextPlayer();
			}
		}
		
		if (winner == null) {
			UI.printText("O jogo terminou empatado");
		}else {
			UI.printText("O jogador " + winner.getName() + "' venceu o jogo!");
			scoreManager.saveScore(winner);
		}
		
		board.print();
		UI.printText("Fim do Jogo!");
		
	}
	
	//Cria 2 Players
	//Verifica se o Usuário já tem pontuação
	private Player createPlayer(int index) {
		String name = UI.readInput("Jogador " + (index + 1) + "=>");
		char simbol = Constantes.SYMBOL_PLAYERS[index];
		Player player = new Player(name, board, simbol );
		
		Integer score = scoreManager.getScore(player);
		
		if (score != null) {
			UI.printText("O jogador " + name + " já possui " + score + " vitória(s)!");
		}
		
		UI.printText("O Jogador " + name + " vai usar o símbolo " + simbol);
		return player;
	}
	
	private  Player nextPlayer() {
		currentPlayer++;
		
		if (currentPlayer >= players.length) {
			currentPlayer = 0;
		}
		
	//	currentPlayer =  (currentPlayer + 1) % players.length;
		return players[currentPlayer];
	
	}
	
	private ScoreManager createScoreManager() throws IOException {
		return new FileScoreManager();
	}
}
