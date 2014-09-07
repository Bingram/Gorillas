package media;

public class numberTest {
	
	public static int numberTest(int[] A){
		if(A.length == 1){return A[0];}
		else{
			int temp = numberTest(removeEnd(A, A[A.length-1]));
				if(temp <= A[A.length-1]){return temp;}
				else{return A[A.length-1];}
		}
	}
	
	public static int[] removeEnd(int[] B, int C){
		int[] temp = new int[B.length - 1];
		int s = 0;
		for(int i: B){
			if(i!=C){
				temp[s] = i;
				s++;
			}
		}
		
		return temp;
	}
	
	public static void main(String[] args){
		int[] temp = new int[5];
		temp[0] = 53;
		temp[1] = 40;
		temp[2] = 32;
		temp[3] = 21;
		temp[4] = 12;
		
		System.out.println("Output: " + numberTest(temp));
	}

}
