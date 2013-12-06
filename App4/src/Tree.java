import java.util.ArrayList;
import java.util.List;


public class Tree {

	//Public properties
	public int maxGlobina = 0;

	//Private properties
	private TNode rootNode;
	private List<List<TNode>> nivoji;
	
	public Tree(int m_globina, Element[][] m_igralnoPolje){
		maxGlobina = m_globina;

		nivoji = new ArrayList<List<TNode>>();
		for(int i = 0; i < maxGlobina; i++){
			nivoji.add(new ArrayList<TNode>());
		}

		rootNode = new TNode(null, new IgralnoPolje(m_igralnoPolje), Players.MAXPLAYER);
		getLevel(0).add(rootNode);

		izdelajDrevo(maxGlobina);
	}

	private void izdelajDrevo(int globina) {

		//Zgradimo drevo moznosti
		for(int i = 0; i <= globina - 2; i++){
			List<TNode> trenutniNivo = getLevel(i);
			List<TNode> naslednjiNivo = getLevel(i + 1);

			int player = Players.MAXPLAYER;

			if((i+1)%2 == 0){
				player = Players.MAXPLAYER;
			}
			else{
				player = Players.MINPLAYER;
			}

			if(i == globina - 2){
				for(TNode node : trenutniNivo){
					naslednjiNivo.addAll(node.setOtroci(player, true)); //otroci so hkrati listi
				}
			}
			else{
				for(TNode node : trenutniNivo){
					naslednjiNivo.addAll(node.setOtroci(player, false));
				}
			}
		}

		//Liste drevesa obogatimo z minimax vrednostjo
		dolociMinIMaxValue(this.rootNode);
	}

	// Metoda, vsakemu vozliscu priredi minimax vrednost
	private int dolociMinIMaxValue(TNode rootNode){
		if(rootNode.isIsLeaf()){
			rootNode.setMinIMaxValue(rootNode.FunkcijaKoristi());
			return rootNode.getMinIMaxValue();
		}
		else{
			
			if(rootNode.getCurrentIgralec() == Players.MAXPLAYER){
				int max = MINIMAXRATING.LOSE;
				for(TNode otrok : rootNode.getOtroci()){
					int maxR = dolociMinIMaxValue(otrok);
					if(maxR > max){
						max = maxR;
					}
				}
				rootNode.setMinIMaxValue(max);
				return max;
			}
			else{
				int min = MINIMAXRATING.WIN;
				for(TNode otrok : rootNode.getOtroci()){
					int minR = dolociMinIMaxValue(otrok);
					if(minR < min){
						min = minR;
					}
				}
				rootNode.setMinIMaxValue(min);
				return min;
			}
		}
	}

	public List<TNode> getLevel(int index){
		return nivoji.get(index);
	}
	
	public int makeBestMove(){
		boolean nasel = false;
		int bestColumn = 0;
		List<TNode> seznamObdelanih = new ArrayList<TNode>();
		
		while(nasel != true){
			int maxValue = MINIMAXRATING.LOSE;
			TNode maxNode = null;

			for(TNode node : rootNode.getOtroci()){
				if(!seznamObdelanih.contains(node) && node.getMinIMaxValue() >= maxValue){
					maxValue = node.getMinIMaxValue();
					maxNode = node;
				}
			}
			
			bestColumn = maxNode.getOce().getOtroci().indexOf(maxNode);
			
			if(StolpecJeNaVoljo(bestColumn, maxNode)){
				nasel = true;
			}
			else{
				seznamObdelanih.add(maxNode);
			}
		}
		
		return bestColumn;
	}

	private boolean StolpecJeNaVoljo(int bestColumn, TNode node) {
		
		boolean nasel = false;
		
		for(int i = node.getModel().vrniIgralnoPolje().length - 1; i >= 0; i--){
			if(node.getModel().vrniIgralnoPolje()[i][bestColumn].vrniIgralca() == 0){
				nasel = true;
				break;
			}
		}
		
		return nasel;
	}
}
