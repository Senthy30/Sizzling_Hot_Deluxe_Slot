package test;

import java.util.Random;

public class das {
	public static int[][] table;
	static Random rand = new Random();
	
	public static int generateNumber(){
		int x = rand.nextInt(90);
		if(x <= 5) return 0;
		else if(x <= 15) return 1;
		else if(x <= 30) return 2;
		else if(x <= 45) return 3;
		else if(x <= 60) return 4;
		else if(x <= 75) return 5;
		else return 6;
	}
	
	public static void generateTable(){
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 5; j++)
				table[i][j] = generateNumber();
	}
	
	public static void main(String args){
		generateTable();
		System.out.println(table[0][0]);
	}
}
