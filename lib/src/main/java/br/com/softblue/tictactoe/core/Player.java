package br.com.softblue.tictactoe.core;

import br.com.softblue.tictactoe.ui.UI;

public class Player {
	
	private String name;
	
	private Board board;
	
	private char symbol;
	
	
	
	public Player(String name, Board board, char symbol) {
		this.name = name;
		this.board = board;
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public Board getBoard() {
		return board;
	}

	public char getSymbol() {
		return symbol;
	}
	
	//Atribui as coordenadas ao tabuleiro
	private Move inputMove() throws InvalidMoveException{
		String moveStr = UI.readInput("Jogador " + getName() + "' =>");
		return new Move(moveStr);
	}
	
	//passas os movimentos
	public boolean play() throws InvalidMoveException{
		Move move = inputMove();
		return board.play(this, move);
		
	}

}
