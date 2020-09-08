import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Application {
	
//    static int[] availableCurrencys= new int[] {20000,10000,5000,2000,1000,500,200,100,50,20,10,5};
    static int[] availableCurrencys= new int[] {4,3,1};
    protected static String errorMessage = "Hibas input";

	public static void main(String[] args) {
		int payableAmount = 6;
		int payedAmount= 12;
		checkIfAmountIsCorrect(payableAmount,payedAmount);
		ArrayList<HashMap<Integer,Integer>> allChangePossibilities=getAllChangesForCurrencys(availableCurrencys,payableAmount,payedAmount);

		HashMap<Integer, Integer> minimalChange = getMinimalChangeFromAllChanges(allChangePossibilities);
		for (Integer i : minimalChange.keySet()) {
	    	  System.out.println("Bankjegy: " + i + " bankjegy száma: " + minimalChange.get(i));
	    }
	}
	
	static boolean checkIfAmountIsCorrect(int payableAmount,int payedAmount) throws IllegalArgumentException {
		if(payableAmount>payedAmount) {
			throw new IllegalArgumentException(errorMessage);
		}
		return true;
		
	}
	
	static ArrayList<HashMap<Integer, Integer>> getAllChangesForCurrencys(int[] currencyList,int payableAmount,int payedAmount) {
		//végigiterálunk az összes bankjegyen olyan módon, hogy mindig kivesszük a legnagyobb bankjegyet az elejérõl, így
		//tulajdonképpen minden lehetséges visszaadási lehetõséget megkapunk. Ezt összegyûjtjük egy Map-be, amibõl kiválasztjuk majd a legkevesebb
		//bankjeggyel járó lehetõséget.
		ArrayList<HashMap<Integer,Integer>> changes = new ArrayList<HashMap<Integer,Integer>>();
		for(int i=0;i<currencyList.length;i++) {
			changes.add(getChangeForGivenCurrency(payableAmount, payedAmount, Arrays.copyOfRange(currencyList,i,currencyList.length)));
		}
		return changes;
	}
	
	static HashMap<Integer, Integer> getChangeForGivenCurrency(int payableAmount, int payedAmount, int[] currencyArray){

		    HashMap<Integer, Integer> solutionNoteNumbers = new HashMap<Integer, Integer>();
		    
		    int changeAmount = payedAmount-payableAmount;
		    //Itt kerekítésre van szükség, hogy pontosan vissza lehessen adni.
		    //Bár az osztás úgyis az alsó egészrészt nézi, szóval valószínûleg ez itt nem annyira fontos
		    int lastCurrency=currencyArray[currencyArray.length-1];
		    if(changeAmount%lastCurrency!=0) {
		    	int multiplier = (int) Math.floor(changeAmount/lastCurrency);
		    	changeAmount = multiplier*lastCurrency;
		    }
		    
		    //megnézi, hogy az adott bankjegy kisebb-e (vagy egyenlõ) mint a visszajáró összege, és ha igen, akkor abból kiszámolja, hogy mennyi a legtöbb összeg
		    //amit be lehet váltani belõle
		    for(int i=0;i<currencyArray.length;i++) {
		    	if(changeAmount>=currencyArray[i]) {
		    		int currencyCount = changeAmount/currencyArray[i];
		    		solutionNoteNumbers.put(currencyArray[i],currencyCount);
		    		changeAmount = changeAmount-currencyCount*currencyArray[i];
		    	}
		    }
		    
		    return solutionNoteNumbers;
		    
	 }
	
	static HashMap<Integer,Integer> getMinimalChangeFromAllChanges(ArrayList<HashMap<Integer,Integer>> allPossibilities) {
		HashMap<Integer, Integer> minimalChange = new HashMap<Integer,Integer>();
		minimalChange=allPossibilities.get(0);
		if(allPossibilities.size()==1) {
			return minimalChange;
		}
		int noteCountForMinimalChange=0;
		for (Integer i : minimalChange.keySet()) {
			noteCountForMinimalChange+=minimalChange.get(i);
	    }
		
		for(int i=0;i<allPossibilities.size();i++) {
			HashMap<Integer,Integer> currentChanges = allPossibilities.get(i);
			int noteCountForCurrentChange=0;
			for (Integer j : currentChanges.keySet()) {
				noteCountForCurrentChange+=currentChanges.get(j);
		    }
			if(noteCountForCurrentChange<noteCountForMinimalChange) {
				minimalChange=currentChanges;
				noteCountForMinimalChange=noteCountForCurrentChange;
			}
		}
		
		return minimalChange;
		
	}

}
