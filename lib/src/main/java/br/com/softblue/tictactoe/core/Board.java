package br.com.softblue.tictactoe.core;

import br.com.softblue.tictactoe.Constantes;
import br.com.softblue.tictactoe.ui.UI;

public class Board {
	
	private char [][] matrix; 
	
	public Board() {
		matrix = new char [Constantes.BOARD_SIZE][Constantes.BOARD_SIZE];
		clean();
	}
	
	//coloca um espaço em branco nas posiçoes do Vetor
	public void clean() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length;j++) {
				matrix[i][j] = ' ';
			}
		}
		
	}
	
	//Imprime o Tabuleiro
	public void print() {
		UI.printNewLine();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length;j++) {
				UI.printTextWithNoNewLine(String.valueOf(matrix[i][j]));
				if(j < matrix[i].length - 1) {
				UI.printTextWithNoNewLine(" | ");
				}
				
			}
				UI.printNewLine();
				if(i < matrix[i].length - 1) {
				UI.printText("---------");
				}
		}
		UI.printNewLine();
		
	}
	
	public boolean isFull(){
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length;j++) {
				if (matrix[i][j] == ' ') {
					return false;
				   }
				}
		}
		return true;
	}			
	
	public boolean play (Player player, Move move) throws InvalidMoveException{
		
		int i = move.getI();
		int j = move.getJ();
		
		if (i < 0 || j < 0 || i >= Constantes.BOARD_SIZE || j >= Constantes.BOARD_SIZE) {
			throw new InvalidMoveException("O intervalo da Jogada é inválido");
		}
		
		if (matrix[i][j] == ' ' ) {
		
			matrix[i][j] = player.getSymbol();//guarda o simbolo no índice
		
		}else {
			throw new InvalidMoveException("Essa jogada já foi realizada");
		}
		return checkRows(player) || checkCols(player) || checkDiagonal2(player) || checkDigonal1(player);
	}
	
	private boolean checkRows (Player player) {
		for (int i = 0; i < Constantes.BOARD_SIZE; i++) {
			if (checkRow(i, player)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean checkRow(int i, Player player) {
		char symbol = player.getSymbol();
		for (int j = 0; j < Constantes.BOARD_SIZE; j++) {
			if (matrix[i][j] != symbol) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean checkCols(Player player) {
		for (int j = 0; j < Constantes.BOARD_SIZE; j++) {
			if (checkCol(j, player)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean checkCol(int j, Player player) {
		char symbol = player.getSymbol();
		for (int i = 0; i < Constantes.BOARD_SIZE; i++) {
			if (matrix[i][j] != symbol) {
				return false;
			}
		}
		
		return true;
	}
	
	
	private boolean checkDigonal1(Player player) {
		char symbol = player.getSymbol();
		
		for (int i = 0; i < Constantes.BOARD_SIZE; i++) {
			if (matrix[i][i] != symbol) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean checkDiagonal2(Player player) {
		char symbol = player.getSymbol();
		int lastLine = Constantes.BOARD_SIZE - 1;
		
		for (int i = lastLine, j = 0; i >= 0; i--, j++) {
			if (matrix[i][j] != symbol) {
				return false;
			}
		}
		
		return true;
	}
	

}
