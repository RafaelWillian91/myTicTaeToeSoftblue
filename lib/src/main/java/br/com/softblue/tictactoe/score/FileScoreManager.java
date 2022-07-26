package br.com.softblue.tictactoe.score;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.com.softblue.tictactoe.core.Player;

public class FileScoreManager implements ScoreManager{
	
	private static final Path SCORE_FILE = Path.of("score.txt");
	private Map<String, Integer> scoreMap = new HashMap<String, Integer>();
	
	public FileScoreManager() throws IOException {
		setup();
	}

	
	private void setup() throws IOException {
		if (!Files.exists(SCORE_FILE)) {
			Files.createFile(SCORE_FILE);
		}
		
		try (BufferedReader reader = Files.newBufferedReader(SCORE_FILE)){//Uso de TryIfResources para garantir o fechamento de IO
			
			String line;
			
			while((line = reader.readLine()) != null) {//readLine quando é chamado lê a proxima linha
				String[] tokens = line.split("\\|");
					
					scoreMap.put(tokens[0], Integer.parseInt(tokens[1]));//put guarda no Map.
				
				}
			}
			
		}
	

	@Override
	public Integer getScore(Player player) {
		return scoreMap.get(player.getName().toUpperCase());//retorna a pontuacao de acordo com o nome
	}

	@Override
	public void saveScore(Player player) throws IOException {
		
		Integer score = getScore(player);//traz o score atual
		
		if (score == null) {
			score = 0;
		}//nao posso entrar com null na conta
		
		scoreMap.put(player.getName(), score + 1);//Salvo que ele venceu mais uma vez
		
		try(BufferedWriter writer = Files.newBufferedWriter(SCORE_FILE)){
			Set<Map.Entry<String, Integer>> entries = scoreMap.entrySet();
			for (Map.Entry<String, Integer> entry : entries) {
				String name = entry.getKey().toUpperCase();
				Integer s = entry.getValue();
				
				writer.write(name + "|" + s);
				writer.newLine();
				
			}	
		}		
	}
}
