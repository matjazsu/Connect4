
public class IgralnoPolje {

	private Element[][] igralnoPolje;
	
	public IgralnoPolje(){
		igralnoPolje = new Element[6][7];
		InicializirajIP();
	}
	
	public IgralnoPolje(Element[][] m_igralnoPolje){
		igralnoPolje = new Element[6][7];
		InicializirajIP(m_igralnoPolje);
	}

	private void InicializirajIP(Element[][] m_igralnoPolje){
		for(int i = 0; i < this.igralnoPolje.length; i++){
			for(int j = 0; j < this.igralnoPolje[0].length; j++){
				this.igralnoPolje[i][j] = m_igralnoPolje[i][j];
			}
		}
	}
	
	private void InicializirajIP() {
		for(int i = 0; i < this.igralnoPolje.length; i++){
			for(int j = 0; j < this.igralnoPolje[0].length; j++){
				this.igralnoPolje[i][j] = new Element();
			}
		}
	}

	public boolean nastaviStatusPolja(PossibleMove move, int igralec) {
		
		boolean uspel = false;
		Element elt = new Element(igralec);
		
		for(int i = igralnoPolje.length - 1; i >= 0; i--){
			if(igralnoPolje[i][move.getMove()].vrniIgralca() == 0){
				igralnoPolje[i][move.getMove()] = elt;
				uspel = true;
				break;
			}
		}
		
		return uspel;
	}

	public IgralnoPolje Copy() {
	
		IgralnoPolje copy = new IgralnoPolje();
		
		for(int i = 0; i < igralnoPolje.length; i++){
			for(int j = 0; j < igralnoPolje[0].length; j++){
				copy.igralnoPolje[i][j] = this.igralnoPolje[i][j];
			}
		}
		
		return copy;
	}
	
	public int vrniSteviloStolpcev(){
		return this.igralnoPolje[0].length;
	}
	
	public int vrniSteviloVrstic(){
		return this.igralnoPolje.length;
	}
	
	public Element[][] vrniIgralnoPolje(){
		return this.igralnoPolje;
	}
	
}
