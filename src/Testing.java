import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class Testing {
	   
	   @Test
	   void testForNormalCurrencys() {
		  int[] currencyList= new int[] {20000,10000,5000,2000,1000,500,200,100,50,20,10,5};
		  int payableAmount =3000;
		  int payedAmount=5000;
		  ArrayList<HashMap<Integer,Integer>> allChangePossibilities=Application.getAllChangesForCurrencys(currencyList, payableAmount, payedAmount);
		  HashMap<Integer, Integer> minimalChange = Application.getMinimalChangeFromAllChanges(allChangePossibilities);
		  
		  HashMap<Integer, Integer> minimalTestChange = new HashMap<Integer,Integer>();
		  minimalTestChange.put(2000, 1);
		  assertEquals(minimalChange, minimalTestChange);     
	   }
	   
	   
	   //6-os visszaj�r� eset�n egy sima algoritmus ilyen �rm�kn�l 1 db 4-est �s 2db 1-est dobna vissza, ami nem a legoptim�lisabb
	   //Ha j� az algoritmus akkor 6-os �rt�kn�l 2db 3-ast kell visszaadni mert ennyi a minim�lis coin sz�m.
	   @Test
	   void testForOddCurrencys() {
		   int[] currencyList= new int[] {4,3,1};
		   int payableAmount =6;
		   int payedAmount=12;
		   ArrayList<HashMap<Integer,Integer>> allChangePossibilities=Application.getAllChangesForCurrencys(currencyList, payableAmount, payedAmount);
		   HashMap<Integer, Integer> minimalChange = Application.getMinimalChangeFromAllChanges(allChangePossibilities);
			  
		   HashMap<Integer, Integer> minimalTestChange = new HashMap<Integer,Integer>();
		   minimalTestChange.put(3, 2);
		   assertEquals(minimalChange, minimalTestChange); 
	   }
	   
	   @Test
	   void testForInvalidInput() {
		   int[] currencyList= new int[] {4,3,1};
		   int payableAmount =20;
		   int payedAmount=10;
		   Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			   Application.checkIfAmountIsCorrect(payableAmount, payedAmount);
		    });
		   
		   String expectedMessage = Application.errorMessage;
		   String actualMessage = exception.getMessage();
		 
		   assertTrue(actualMessage.equals(expectedMessage));
		  

	   }
	   
	   @Test
	   void testForSpeed() {
		   //5 milliszekundum alatt kell lefutnia sok visszaj�r�val (irre�lisan sokkal) �s sok bankjeggyel
		   int timeThreshold=5;
		   
		   long startTime = System.currentTimeMillis();
		   int[] currencyList= new int[] {20000,10000,5000,2000,1000,500,200,100,50,20,15,10,5,4,3,2,1};
		   int payableAmount =6;
		   int payedAmount=12123422;
		   
		   
		   ArrayList<HashMap<Integer,Integer>> allChangePossibilities=Application.getAllChangesForCurrencys(currencyList, payableAmount, payedAmount);
		   HashMap<Integer, Integer> minimalChange = Application.getMinimalChangeFromAllChanges(allChangePossibilities);
		   long endTime = System.currentTimeMillis();
		   long runTime = endTime-startTime;
		  
		   System.out.println("Runtime: "+runTime+ " ms");
		   assertTrue(runTime<timeThreshold);
		   
	   }

}
