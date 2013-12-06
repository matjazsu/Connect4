
public class MinMaxPlayer{
	
	//Private fields
	private Tree minimaxTree;
	private Element[][] igralnoPolje;
	
	//Konstruktor
	public MinMaxPlayer(int m_globina, int[][] m_igralnoPolje){
		inicializirajIgralnoPolje(m_igralnoPolje);
		minimaxTree = new Tree(m_globina, igralnoPolje);
	}
	
	private void inicializirajIgralnoPolje(int[][] m_igralnoPolje){
		igralnoPolje = new Element[m_igralnoPolje.length][m_igralnoPolje[0].length];
		for(int i = 0; i < igralnoPolje.length; i++){
			for(int j = 0; j < igralnoPolje[0].length; j++){
				igralnoPolje[i][j] = new Element(m_igralnoPolje[i][j]);
			}
		}
	}
	
	public int vrniStolpec() {
		return minimaxTree.makeBestMove() + 1;
	}
}
