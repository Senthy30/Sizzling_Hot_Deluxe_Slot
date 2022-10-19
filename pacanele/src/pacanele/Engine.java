package pacanele;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.swing.Timer;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Engine extends JPanel implements KeyListener, Action {
	
	private boolean play = false, changeBet = false, start = false, winner, startCollect, gamble, exitFromGamble = false;
	private Timer timer;
	private int delay = 8;
	private int distanceTop = 170, distanceLeft = 280, distanceBetween = 40;
	private int dLeft = 10;
	private int width = 150, height = 150;
	public static int bet = 5;
	public static int credit = 10000, preCredit;
	private static int mCredit = credit;
	private static int colChange = 0, colDecision = 0;
	private static int history[] = new int[10];
	static Random rand = new Random();
	
	private Vector <BufferedImage> fruit = new Vector<BufferedImage>(10);
	private Vector <BufferedImage> fruitWin = new Vector<BufferedImage>(10);
	private BufferedImage preBlue, preRed, black, red, Sizzling;
	private GenerateTable GTable = new GenerateTable();
	
	private void drawFruits(){
		try {
			fruit.add(ImageIO.read(new File("src\\fruits\\seven.jpg")));
			fruit.add(ImageIO.read(new File("src\\fruits\\melon.jpg")));
			fruit.add(ImageIO.read(new File("src\\fruits\\grapes.jpg")));
			fruit.add(ImageIO.read(new File("src\\fruits\\plum.jpg")));
			fruit.add(ImageIO.read(new File("src\\fruits\\orange.jpg")));
			fruit.add(ImageIO.read(new File("src\\fruits\\lemon.jpg")));
			fruit.add(ImageIO.read(new File("src\\fruits\\cherry.jpg")));
			fruitWin.add(ImageIO.read(new File("src\\fruits\\win\\seven.jpg")));
			fruitWin.add(ImageIO.read(new File("src\\fruits\\win\\melon.jpg")));
			fruitWin.add(ImageIO.read(new File("src\\fruits\\win\\grapes.jpg")));
			fruitWin.add(ImageIO.read(new File("src\\fruits\\win\\plum.jpg")));
			fruitWin.add(ImageIO.read(new File("src\\fruits\\win\\orange.jpg")));
			fruitWin.add(ImageIO.read(new File("src\\fruits\\win\\lemon.jpg")));
			fruitWin.add(ImageIO.read(new File("src\\fruits\\win\\cherry.jpg")));
			Sizzling = ImageIO.read(new File("src\\fruits\\sizzling.jpg"));
			preBlue = ImageIO.read(new File("src\\fruits\\gamble\\blue.jpg"));
			preRed = ImageIO.read(new File("src\\fruits\\gamble\\red.jpg"));
			black = ImageIO.read(new File("src\\fruits\\gamble\\black.png"));
			red = ImageIO.read(new File("src\\fruits\\gamble\\cRed.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Engine(){
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		drawFruits();
		for(int i = 0; i <= 4; i++)
			history[i] = rand.nextInt(2) + 1;
		try {
			GTable.read_from_file();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		timer = new Timer(delay, this);
		timer.start();
		if(!start) GTable.generateTable();
	}
	
	protected void paintComponent(Graphics g){
		Color background = new Color(66, 7, 105);
		Color square = new Color(118, 105, 128);
		g.setColor(background);
		g.fillRect(0, 0, 1550, 1000);
		g.drawImage(Sizzling, 0, 0, 1550, 100, this);
		g.setColor(square);
		for(int i = 0; i < 5; i++)
			g.fillRect(distanceLeft - dLeft + i * width + i * distanceBetween, distanceTop - dLeft, width + 2 * dLeft, 3 * height + 2 * dLeft);
		square = new Color(158, 11, 11); g.setColor(square);
		g.fillRect(0, 700, 1550, 130);
		square = new Color(124, 125, 145); g.setColor(square);
		g.fillRect(0, 830, 1550, 170);
		square = new Color(64, 65, 89); g.setColor(square);
		g.fillRect(0, 840, 1550, 105);
		g.setColor(Color.red);
		if(!changeBet && !winner && play && !exitFromGamble){
			GTable.generateTable();
			/*
			int nrrep = 0;
			while(GTable.win < 1000 * GTable.bet[bet]){
				GTable.generateTable();
				nrrep++;
			}
			System.out.print(nrrep + " ");
			*/
			if(start && GTable.win > 0) {
				winner = true;
				preCredit = credit + GTable.win;
			} else GTable.win = 0;
		}
		if(!gamble || exitFromGamble){
			if(exitFromGamble) exitFromGamble = false;
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 5; j++)
					if(!GTable.winning[i][j] || !start)
						g.drawImage(fruit.get(GTable.table[i][j]), j * width + distanceLeft + j * distanceBetween, i * height + distanceTop, width, height, this);
					else if(start)
						g.drawImage(fruitWin.get(GTable.table[i][j]), j * width + distanceLeft + j * distanceBetween, i * height + distanceTop, width, height, this);
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 5; j++)	
					if(GTable.winning[i][j] == true && start)
						g.drawRect(j * width + distanceLeft + j * distanceBetween, i * height + distanceTop, width, height);
		} else {
			Color backGamble = new Color(94, 13, 158);
			g.setColor(backGamble); g.fillRect(0, 100, 1550, 600);
			Color gambleBk = new Color(71, 14, 117);
			g.setColor(gambleBk); g.fillRect(200, 100, 1050, 600);
			colChange = 1 - colChange;
			g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		    g.setColor(Color.white);
		    g.fillRect(590, 350, 200, 300);
		    if(colChange == 1)
				g.drawImage(preBlue, 600, 360, 180, 280, this);
			else g.drawImage(preRed, 600, 360, 180, 280, this);
			g.drawString("Gamble amount", 270, 150); g.drawString(String.valueOf(GTable.win), 490, 150);
			g.drawString("Gamble to win", 270, 200); g.drawString(String.valueOf(2 * GTable.win), 490, 200);
			g.drawString("Previous cards", 1050, 260);
			for(int i = 0; i <= 4; i++)
				if(history[i] == 1)
					g.drawImage(black, i * 70 + 900, 130, 60, 100, this);
				else g.drawImage(red, i * 70 + 900, 130, 60, 100, this);
			if(colDecision != 0){
				int x = rand.nextInt(2); x++;
				if(x == 1)
					g.drawImage(black, 600, 360, 180, 280, this);
				else g.drawImage(red, 600, 360, 180, 280, this);
				for(int i = 4; i > 0; i--)
					history[i] = history[i - 1];
				history[0] = x;
				if(x == colDecision)
					GTable.win *= 2;
				else {
					GTable.win = 0;
					gamble = false;
					exitFromGamble = true;
					winner = false;
				}
				colDecision = 0;
			}
		}
		g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
	    g.setColor(Color.white);
		g.drawString(String.valueOf(GTable.win), 1200, 875);
		g.drawString("WIN", 1178, 925);
		g.drawString(String.valueOf(credit), 300, 875);
		g.drawString("CREDIT", 260, 925);
		g.drawString("BET", 620, 780);
		g.drawString(String.valueOf(GTable.bet[bet]), 710, 780);
		g.dispose();
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(play) play = false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(startCollect){
			if(credit < preCredit && GTable.win > 0){
				int dif = 1;
				if(GTable.win >= 1000) dif = 1000;
				else if(GTable.win >= 100) dif = 100;
				else if(GTable.win >= 10) dif = 10;
				GTable.win -= dif;
				credit += dif;
				repaint();
			} else {
				startCollect = false;
				winner = false;
			}
		} else if(play || gamble || exitFromGamble) {
			repaint();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(!gamble){
			if(e.getKeyCode() == KeyEvent.VK_SPACE){
				if(!winner){
					if(!start) start = true;
					if(changeBet) changeBet = false;
					if(credit - GTable.bet[bet] >= 0){
						credit -= GTable.bet[bet];
						play = true;
					}
				} else {
					if(!startCollect) startCollect = true;
					else{
						winner = false;
						play = false;
						credit += GTable.win;
						GTable.win = 0;
					}
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_UP){
				if(bet < 17) bet++;
				else bet = 0;
				changeBet = true; play = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN){
				if(bet > 0) bet--;
				else bet = 17;
				changeBet = true; play = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_ENTER && winner){
				colDecision = 0;
				gamble = true;
				play = true;
			}
		} else {
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
				colDecision = 1;
			if(e.getKeyCode() == KeyEvent.VK_RIGHT)
				colDecision = 2;
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				credit += GTable.win;
				gamble = false;
				exitFromGamble = true;
				winner = false;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	@Override
	public Object getValue(String key) {
		
		return null;
	}

	@Override
	public void putValue(String key, Object value) {
		
		
	}

}
