package pacanele;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class GenerateTable {
	
	public static int[][][] check = new int[2][6][6];
	public static int[][] rewards = new int[8][6];
	public static int[][] table = new int[10][10];
	public static boolean[][] winning = new boolean[10][10];
	public static int win = 0, typeBet;
	public static int bet[] = new int [20];
	static Random rand = new Random();
	
	public static int generateNumber(){
		int x = rand.nextInt(91);
		if(x <= 5) return 0;
		else if(x <= 15) return 1;
		else if(x <= 30) return 2;
		else if(x <= 45) return 3;
		else if(x <= 60) return 4;
		else if(x <= 75) return 5;
		else return 6;
	}
	
	public static void read_from_file() throws Exception{
		Scanner fr = new Scanner(new File("src\\check\\win.txt"));
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++){
				check[0][i][j] = fr.nextInt();
				check[1][i][j] = fr.nextInt();
			}
		Scanner frp = new Scanner(new File("src\\check\\rewards.txt"));
		for(int i = 0; i < 7; i++)
			for(int j = 0; j < 5; j++)
				rewards[i][j] = frp.nextInt();
		Scanner frw = new Scanner(new File("src\\bet\\bet.txt"));
		for(int i = 0; i <= 17; i++)
			bet[i] = frw.nextInt();
	}
	
	public static void check_for_win(){
		typeBet = Engine.bet;
		for(int i = 0; i < 5; i++){
			int x = table[check[0][i][0]][check[1][i][0]];
			int cnt = 1;
			boolean ok = true;
			for(int j = 1; j < 5 && ok == true; j++){
				if(table[check[0][i][j]][check[1][i][j]] == x)
					cnt++;
				else ok = false;
			}
			win += rewards[x][cnt - 1] * bet[typeBet];
			if(rewards[x][cnt - 1] * bet[typeBet] > 0){
				for(int j = 0; j < cnt; j++)
					winning[check[0][i][j]][check[1][i][j]] = true;
			}
		}
	}
	
	public static void generateTable(){
		win = 0;
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 5; j++){
				table[i][j] = generateNumber();
				winning[i][j] = false;
			}
		check_for_win();
	}
	
	public static void main(String[] args) throws Exception{
		read_from_file(); generateTable();
	}
}
