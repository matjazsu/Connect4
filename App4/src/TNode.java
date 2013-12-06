import java.util.ArrayList;
import java.util.List;

public class TNode {

	private PossibleMove move;
	private IgralnoPolje model;
	private int minimaxValue; //minimax vrednost
	private TNode stars; //predhodnik
	private List<TNode> otroci; //otroci
	private boolean isLeaf = false;
	private int igralec;

	public TNode(PossibleMove m_move, IgralnoPolje igralnoPolje, int m_igralec){
		this.move = m_move;
		this.model = igralnoPolje;
		this.igralec = m_igralec;
		otroci = new ArrayList<TNode>();
		this.minimaxValue = MINIMAXRATING.DEFAULMINIMAXVALUE; //Default value
	}

	//Get-Set

	public IgralnoPolje getModel(){
		return this.model;
	}

	public int getCurrentIgralec(){
		return this.igralec;
	}

	public void setCurrentIgralec(int m_igralec){
		this.igralec = m_igralec;
	}

	public void setOce(TNode m_stars){
		this.stars = m_stars;
	}

	public TNode getOce(){
		return this.stars;
	}

	public void setLeaf(){
		this.isLeaf = true;
	}

	public boolean isIsLeaf(){
		return this.isLeaf;
	}

	public List<TNode> getOtroci(){
		return this.otroci;
	}

	public PossibleMove getMove(){
		return this.move;
	}

	public List<TNode> setOtroci(int player, boolean setList){
		if(!this.isIsLeaf()){
			for(int i = 0; i < this.model.vrniSteviloStolpcev(); i++){
				IgralnoPolje m_igralnoPolje = model.Copy();

				TNode otrok = new TNode(new PossibleMove(i), m_igralnoPolje, player);
				otrok.setOce(this);
				boolean niList = otrok.makeMove(this.igralec);

				if(!niList){
					otrok.setLeaf();
				}

				if(setList){
					otrok.setLeaf();
				}

				this.otroci.add(otrok);
			}
		}

		return this.otroci;
	}

	private boolean makeMove(int igralec){
		return model.nastaviStatusPolja(this.getMove(), igralec);
	}

	public void setMinIMaxValue(int value){
		this.minimaxValue = value;
	}

	public int getMinIMaxValue(){
		return this.minimaxValue;
	}

	public void setMinValue(){
		if(this.getOtroci() != null && this.getOtroci().size() > 0){

			int min = MINIMAXRATING.WIN;

			for(TNode otrok : this.getOtroci()){
				if(otrok.getMinIMaxValue() < min){
					min = otrok.getMinIMaxValue();
				}
			}

			this.setMinIMaxValue(min);
		}
	}

	public void setMaxValue(){
		if(this.getOtroci() != null && this.getOtroci().size() > 0){

			int max = MINIMAXRATING.LOSE;

			for(TNode otrok : this.getOtroci()){
				if(otrok.getMinIMaxValue() > max){
					max = otrok.getMinIMaxValue();
				}
			}

			this.setMinIMaxValue(max);
		}
	}

	public int FunkcijaKoristi() {

		
		
		/*
		 * WIN 4 in a row
		 */

		// horizontal rows
		for (int row=0; row<model.vrniSteviloVrstic(); row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-3; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER 
						&& curr == model.vrniIgralnoPolje()[row][col+1].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row][col+2].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row][col+3].vrniIgralca()) {
					return MINIMAXRATING.WIN;
				}
			}
		}
		// vertical columns
		for (int col=0; col<model.vrniSteviloStolpcev(); col++) {
			for (int row=0; row<model.vrniSteviloVrstic()-3; row++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& curr == model.vrniIgralnoPolje()[row+1][col].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row+2][col].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row+3][col].vrniIgralca())
					return MINIMAXRATING.WIN;
			}
		}
		// diagonal lower left to upper right
		for (int row=0; row<model.vrniSteviloVrstic()-3; row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-3; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& curr == model.vrniIgralnoPolje()[row+1][col+1].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row+2][col+2].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row+3][col+3].vrniIgralca())
					return MINIMAXRATING.WIN;
			}
		}
		// diagonal upper left to lower right
		for (int row=model.vrniSteviloVrstic()-1; row>=3; row--) {
			for (int col=0; col<model.vrniSteviloStolpcev()-3; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& curr == model.vrniIgralnoPolje()[row-1][col+1].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row-2][col+2].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row-3][col+3].vrniIgralca())
					return MINIMAXRATING.WIN;
			}
		}
		
		/*
		 * 3 in a row for max
		 */

		// horizontal rows
		for (int row=0; row<model.vrniSteviloVrstic(); row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-2; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER 
						&& curr == model.vrniIgralnoPolje()[row][col+1].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row][col+2].vrniIgralca()) {
					return MINIMAXRATING.ALMOSTWIN;
				}
			}
		}
		// vertical columns
		for (int col=0; col<model.vrniSteviloStolpcev(); col++) {
			for (int row=0; row<model.vrniSteviloVrstic()-2; row++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& curr == model.vrniIgralnoPolje()[row+1][col].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row+2][col].vrniIgralca())
					return MINIMAXRATING.ALMOSTWIN;
			}
		}
		// diagonal lower left to upper right
		for (int row=0; row<model.vrniSteviloVrstic()-2; row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-2; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& curr == model.vrniIgralnoPolje()[row+1][col+1].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row+2][col+2].vrniIgralca())
					return MINIMAXRATING.ALMOSTWIN;
			}
		}
		// diagonal upper left to lower right
		for (int row=model.vrniSteviloVrstic()-1; row>=2; row--) {
			for (int col=0; col<model.vrniSteviloStolpcev()-2; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& curr == model.vrniIgralnoPolje()[row-1][col+1].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row-2][col+2].vrniIgralca())
					return MINIMAXRATING.ALMOSTWIN;
			}
		}
		
		/*
		 * 3-X for max
		 */
		// horizontal rows
		for (int row=0; row<model.vrniSteviloVrstic(); row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-3; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER 
						&& model.vrniIgralnoPolje()[row][col+1].vrniIgralca() == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row][col+2].vrniIgralca() == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row][col+3].vrniIgralca() == Players.MINPLAYER) {
					return MINIMAXRATING.BREAKMINTHREE;
				}
			}
		}
		// vertical columns
		for (int col=0; col<model.vrniSteviloStolpcev(); col++) {
			for (int row=0; row<model.vrniSteviloVrstic()-3; row++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row+1][col].vrniIgralca() == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row+2][col].vrniIgralca() == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row+3][col].vrniIgralca() == Players.MINPLAYER)
					return MINIMAXRATING.BREAKMINTHREE;
			}
		}
		// diagonal lower left to upper right
		for (int row=0; row<model.vrniSteviloVrstic()-3; row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-3; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row+1][col+1].vrniIgralca() == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row+2][col+2].vrniIgralca() == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row+3][col+3].vrniIgralca() == Players.MINPLAYER)
					return MINIMAXRATING.BREAKMINTHREE;
			}
		}
		// diagonal upper left to lower right
		for (int row=model.vrniSteviloVrstic()-1; row>=3; row--) {
			for (int col=0; col<model.vrniSteviloStolpcev()-3; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row-1][col+1].vrniIgralca() == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row-2][col+2].vrniIgralca() == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row-3][col+3].vrniIgralca() == Players.MINPLAYER)
					return MINIMAXRATING.BREAKMINTHREE;
			}
		}
		
		/*
		 * 2-X for max
		 */
		// horizontal rows
		for (int row=0; row<model.vrniSteviloVrstic(); row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-2; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER 
						&& model.vrniIgralnoPolje()[row][col+1].vrniIgralca() == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row][col+2].vrniIgralca() == Players.MINPLAYER) {
					return MINIMAXRATING.BREAKMINTWO;
				}
			}
		}
		// vertical columns
		for (int col=0; col<model.vrniSteviloStolpcev(); col++) {
			for (int row=0; row<model.vrniSteviloVrstic()-2; row++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row+1][col].vrniIgralca() == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row+2][col].vrniIgralca() == Players.MINPLAYER)
					return MINIMAXRATING.BREAKMINTWO;
			}
		}
		// diagonal lower left to upper right
		for (int row=0; row<model.vrniSteviloVrstic()-2; row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-2; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row+1][col+1].vrniIgralca() == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row+2][col+2].vrniIgralca() == Players.MINPLAYER)
					return MINIMAXRATING.BREAKMINTWO;
			}
		}
		// diagonal upper left to lower right
		for (int row=model.vrniSteviloVrstic()-1; row>=2; row--) {
			for (int col=0; col<model.vrniSteviloStolpcev()-2; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row-1][col+1].vrniIgralca() == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row-2][col+2].vrniIgralca() == Players.MINPLAYER)
					return MINIMAXRATING.BREAKMINTWO;
			}
		}
		
		/*
		 * 1-X for max
		 */
		// horizontal rows
		for (int row=0; row<model.vrniSteviloVrstic(); row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-1; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER 
						&& model.vrniIgralnoPolje()[row][col+1].vrniIgralca() == Players.MINPLAYER) {
					return MINIMAXRATING.BREAKMINONE;
				}
			}
		}
		// vertical columns
		for (int col=0; col<model.vrniSteviloStolpcev(); col++) {
			for (int row=0; row<model.vrniSteviloVrstic()-1; row++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row+1][col].vrniIgralca() == Players.MINPLAYER)
					return MINIMAXRATING.BREAKMINONE;
			}
		}
		// diagonal lower left to upper right
		for (int row=0; row<model.vrniSteviloVrstic()-1; row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-1; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row+1][col+1].vrniIgralca() == Players.MINPLAYER)
					return MINIMAXRATING.BREAKMINONE;
			}
		}
		// diagonal upper left to lower right
		for (int row=model.vrniSteviloVrstic()-1; row>=1; row--) {
			for (int col=0; col<model.vrniSteviloStolpcev()-1; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row-1][col+1].vrniIgralca() == Players.MINPLAYER)
					return MINIMAXRATING.BREAKMINONE;
			}
		}
		
		/*
		 * 2 in a row for max
		 */

		// horizontal rows
		for (int row=0; row<model.vrniSteviloVrstic(); row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-1; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER 
						&& curr == model.vrniIgralnoPolje()[row][col+1].vrniIgralca()) {
					return MINIMAXRATING.GOODGAMEWIN;
				}
			}
		}
		// vertical columns
		for (int col=0; col<model.vrniSteviloStolpcev(); col++) {
			for (int row=0; row<model.vrniSteviloVrstic()-1; row++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& curr == model.vrniIgralnoPolje()[row+1][col].vrniIgralca())
					return MINIMAXRATING.GOODGAMEWIN;
			}
		}
		// diagonal lower left to upper right
		for (int row=0; row<model.vrniSteviloVrstic()-1; row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-1; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& curr == model.vrniIgralnoPolje()[row+1][col+1].vrniIgralca())
					return MINIMAXRATING.GOODGAMEWIN;
			}
		}
		// diagonal upper left to lower right
		for (int row=model.vrniSteviloVrstic()-1; row>=1; row--) {
			for (int col=0; col<model.vrniSteviloStolpcev()-1; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MAXPLAYER
						&& curr == model.vrniIgralnoPolje()[row-1][col+1].vrniIgralca())
					return MINIMAXRATING.GOODGAMEWIN;
			}
		}

		/*
		 * LOSE
		 */

		// horizontal rows
		for (int row=0; row<model.vrniSteviloVrstic(); row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-3; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER 
						&& curr == model.vrniIgralnoPolje()[row][col+1].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row][col+2].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row][col+3].vrniIgralca()) {
					return MINIMAXRATING.LOSE;
				}
			}
		}
		// vertical columns
		for (int col=0; col<model.vrniSteviloStolpcev(); col++) {
			for (int row=0; row<model.vrniSteviloVrstic()-3; row++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& curr == model.vrniIgralnoPolje()[row+1][col].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row+2][col].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row+3][col].vrniIgralca())
					return MINIMAXRATING.LOSE;
			}
		}
		// diagonal lower left to upper right
		for (int row=0; row<model.vrniSteviloVrstic()-3; row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-3; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& curr == model.vrniIgralnoPolje()[row+1][col+1].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row+2][col+2].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row+3][col+3].vrniIgralca())
					return MINIMAXRATING.LOSE;
			}
		}
		// diagonal upper left to lower right
		for (int row=model.vrniSteviloVrstic()-1; row>=3; row--) {
			for (int col=0; col<model.vrniSteviloStolpcev()-3; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& curr == model.vrniIgralnoPolje()[row-1][col+1].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row-2][col+2].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row-3][col+3].vrniIgralca())
					return MINIMAXRATING.LOSE;
			}
		}

		/*
		 * 3 in a row for min
		 */

		// horizontal rows
		for (int row=0; row<model.vrniSteviloVrstic(); row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-2; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER 
						&& curr == model.vrniIgralnoPolje()[row][col+1].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row][col+2].vrniIgralca()) {
					return MINIMAXRATING.ALMOSTLOSE;
				}
			}
		}
		// vertical columns
		for (int col=0; col<model.vrniSteviloStolpcev(); col++) {
			for (int row=0; row<model.vrniSteviloVrstic()-2; row++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& curr == model.vrniIgralnoPolje()[row+1][col].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row+2][col].vrniIgralca())
					return MINIMAXRATING.ALMOSTLOSE;
			}
		}
		// diagonal lower left to upper right
		for (int row=0; row<model.vrniSteviloVrstic()-2; row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-2; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& curr == model.vrniIgralnoPolje()[row+1][col+1].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row+2][col+2].vrniIgralca())
					return MINIMAXRATING.ALMOSTLOSE;
			}
		}
		// diagonal upper left to lower right
		for (int row=model.vrniSteviloVrstic()-1; row>=2; row--) {
			for (int col=0; col<model.vrniSteviloStolpcev()-2; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& curr == model.vrniIgralnoPolje()[row-1][col+1].vrniIgralca()
						&& curr == model.vrniIgralnoPolje()[row-2][col+2].vrniIgralca())
					return MINIMAXRATING.ALMOSTLOSE;
			}
		}
		
		/*
		 * 3-O for min
		 */
		// horizontal rows
		for (int row=0; row<model.vrniSteviloVrstic(); row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-3; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER 
						&& model.vrniIgralnoPolje()[row][col+1].vrniIgralca() == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row][col+2].vrniIgralca() == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row][col+3].vrniIgralca() == Players.MAXPLAYER) {
					return MINIMAXRATING.BREAKMAXTHREE;
				}
			}
		}
		// vertical columns
		for (int col=0; col<model.vrniSteviloStolpcev(); col++) {
			for (int row=0; row<model.vrniSteviloVrstic()-3; row++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row+1][col].vrniIgralca() == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row+2][col].vrniIgralca() == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row+3][col].vrniIgralca() == Players.MAXPLAYER)
					return MINIMAXRATING.BREAKMAXTHREE;
			}
		}
		// diagonal lower left to upper right
		for (int row=0; row<model.vrniSteviloVrstic()-3; row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-3; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row+1][col+1].vrniIgralca() == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row+2][col+2].vrniIgralca() == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row+3][col+3].vrniIgralca() == Players.MAXPLAYER)
					return MINIMAXRATING.BREAKMAXTHREE;
			}
		}
		// diagonal upper left to lower right
		for (int row=model.vrniSteviloVrstic()-1; row>=3; row--) {
			for (int col=0; col<model.vrniSteviloStolpcev()-3; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row-1][col+1].vrniIgralca() == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row-2][col+2].vrniIgralca() == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row-3][col+3].vrniIgralca() == Players.MAXPLAYER)
					return MINIMAXRATING.BREAKMAXTHREE;
			}
		}
		
		/*
		 * 2-O for min
		 */
		// horizontal rows
		for (int row=0; row<model.vrniSteviloVrstic(); row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-2; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER 
						&& model.vrniIgralnoPolje()[row][col+1].vrniIgralca() == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row][col+2].vrniIgralca() == Players.MAXPLAYER) {
					return MINIMAXRATING.BREAKMAXTWO;
				}
			}
		}
		// vertical columns
		for (int col=0; col<model.vrniSteviloStolpcev(); col++) {
			for (int row=0; row<model.vrniSteviloVrstic()-2; row++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row+1][col].vrniIgralca() == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row+2][col].vrniIgralca() == Players.MAXPLAYER)
					return MINIMAXRATING.BREAKMAXTWO;
			}
		}
		// diagonal lower left to upper right
		for (int row=0; row<model.vrniSteviloVrstic()-2; row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-2; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row+1][col+1].vrniIgralca() == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row+2][col+2].vrniIgralca() == Players.MAXPLAYER)
					return MINIMAXRATING.BREAKMAXTWO;
			}
		}
		// diagonal upper left to lower right
		for (int row=model.vrniSteviloVrstic()-1; row>=2; row--) {
			for (int col=0; col<model.vrniSteviloStolpcev()-2; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row-1][col+1].vrniIgralca() == Players.MAXPLAYER
						&& model.vrniIgralnoPolje()[row-2][col+2].vrniIgralca() == Players.MAXPLAYER)
					return MINIMAXRATING.BREAKMAXTWO;
			}
		}
		
		/*
		 * 1-O for min
		 */
		// horizontal rows
		for (int row=0; row<model.vrniSteviloVrstic(); row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-1; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER 
						&& model.vrniIgralnoPolje()[row][col+1].vrniIgralca() == Players.MAXPLAYER) {
					return MINIMAXRATING.BREAKMAXONE;
				}
			}
		}
		// vertical columns
		for (int col=0; col<model.vrniSteviloStolpcev(); col++) {
			for (int row=0; row<model.vrniSteviloVrstic()-1; row++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row+1][col].vrniIgralca() == Players.MAXPLAYER)
					return MINIMAXRATING.BREAKMAXONE;
			}
		}
		// diagonal lower left to upper right
		for (int row=0; row<model.vrniSteviloVrstic()-1; row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-1; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row+1][col+1].vrniIgralca() == Players.MAXPLAYER)
					return MINIMAXRATING.BREAKMAXONE;
			}
		}
		// diagonal upper left to lower right
		for (int row=model.vrniSteviloVrstic()-1; row>=1; row--) {
			for (int col=0; col<model.vrniSteviloStolpcev()-1; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& model.vrniIgralnoPolje()[row-1][col+1].vrniIgralca() == Players.MAXPLAYER)
					return MINIMAXRATING.BREAKMAXONE;
			}
		}

		/*
		 * 2 in a row for min
		 */

		// horizontal rows
		for (int row=0; row<model.vrniSteviloVrstic(); row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-1; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER 
						&& curr == model.vrniIgralnoPolje()[row][col+1].vrniIgralca()) {
					return MINIMAXRATING.GOODGAMELOSE;
				}
			}
		}
		// vertical columns
		for (int col=0; col<model.vrniSteviloStolpcev(); col++) {
			for (int row=0; row<model.vrniSteviloVrstic()-1; row++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& curr == model.vrniIgralnoPolje()[row+1][col].vrniIgralca())
					return MINIMAXRATING.GOODGAMELOSE;
			}
		}
		// diagonal lower left to upper right
		for (int row=0; row<model.vrniSteviloVrstic()-1; row++) {
			for (int col=0; col<model.vrniSteviloStolpcev()-1; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& curr == model.vrniIgralnoPolje()[row+1][col+1].vrniIgralca())
					return MINIMAXRATING.GOODGAMELOSE;
			}
		}
		// diagonal upper left to lower right
		for (int row=model.vrniSteviloVrstic()-1; row>=1; row--) {
			for (int col=0; col<model.vrniSteviloStolpcev()-1; col++) {
				int curr = model.vrniIgralnoPolje()[row][col].vrniIgralca();
				if (curr == Players.MINPLAYER
						&& curr == model.vrniIgralnoPolje()[row-1][col+1].vrniIgralca())
					return MINIMAXRATING.GOODGAMELOSE;
			}
		}
		
		/*
		 * EQUAL
		 */

		return MINIMAXRATING.EQUAL;
	}

}
