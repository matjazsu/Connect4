
public class RandomPlayer{
	
	public RandomPlayer(){}

	public int vrniStolpec(int[][] theArray) {
		// TODO Auto-generated method stub
		
		boolean nasel = false;
		int column = 1;
		
		while(!nasel){
			column = (int)(Math.random()*(theArray[0].length)) + 1;
			
			for(int i = theArray.length - 1; i >= 0; i--){
				if(theArray[i][column - 1] == 0){
					nasel = true;
					break;
				}
			}
		}
		
		return column;
	}
	
}
