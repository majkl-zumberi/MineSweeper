package minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class UiGame extends JFrame  {

	
	int row;
	int col;
	JButton [][] btns;
	MioMouse mouse;
	JPanel pnl1;
	public UiGame(int row ,int col) {
		super("Minesweeper");
		Container c =this.getContentPane();
		this.row=row;
		this.col=col;
		btns=new JButton[row][col];
		mouse=new MioMouse(btns);
		pnl1= new JPanel();
		pnl1.setLayout(new GridLayout(this.row,this.col));
	
		for (int i = 0; i < btns.length; i++) {
			for (int j = 0; j < btns[0].length; j++) {
				try {
				    UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
				 } catch (Exception e) {
				            e.printStackTrace();
				 }
				btns[i][j]=new JButton("");//i+"-"+j
				btns[i][j].setBackground(Color.GRAY);
				btns[i][j].setForeground(Color.LIGHT_GRAY);
				btns[i][j].setName("0");
				btns[i][j].setOpaque(true);
				btns[i][j].addActionListener(mouse);
				pnl1.add(btns[i][j]);
			}
			
			
		}
		
		c.add(pnl1);
		this.setBounds(0, 0, 750, 750);
		loadBombs(10);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public void loadBombs(int bombs) {
		int X;
		int Y;
		int mines=0;
		do
		{
			do
			{
				X = (int) (Math.random()*this.row); // get a random row
			    Y = (int) (Math.random()*this.col); // get a random column
			    System.out.println("x: "+X+" Y: "+Y);
			}while (!btns[X][Y].getName().equalsIgnoreCase("0")); // do again if a mine is located there already
			btns[X][Y].setName("B"); // place a mine at location
			mines++; // increase mine count
			System.out.println(mines);
		} while (mines < bombs); // Keep doing this until we have n mines

		for (int i = 0; i < btns.length; i++) {
			for (int j = 0; j < btns[0].length; j++) {
				System.out.print(btns[i][j].getText()+"  ");
			}
		}
		calc_bombs();
	}
	public void disableAll(boolean won) {
		for (int i = 0; i < btns.length; i++) {
			for (int j = 0; j < btns[0].length; j++) {
				//btns[i][j].doClick();
//				
				btns[i][j].setText(btns[i][j].getName());
				btns[i][j].setEnabled(false); 
				if(btns[i][j].getText().equals("B") && !won) {
					btns[i][j].setBackground(Color.RED);
				}
				
			}
		}
		if(!won) {
			new JOptionPane().showMessageDialog(null, "hai perso", "fail", JOptionPane.ERROR_MESSAGE);
		}
		else {
			new JOptionPane().showMessageDialog(null, "hai vinto", "success", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void calc_bombs() {
		for (int i = 0; i < btns.length; i++) {
			for (int j = 0; j < btns[0].length; j++) {
				System.out.println(btns[i][j].getText()+" "+btns[i][j].getText().equalsIgnoreCase("B"));
				if(!btns[i][j].getName().equalsIgnoreCase("B")) {
					btns[i][j].setName(calculate_bombs(btns, j, i)+"");
				}
			}
		}
	}
	public void didHeWon(int mines) {
		int counter=0;
		for (int i = 0; i < btns.length; i++) {
			for (int j = 0; j < btns[0].length; j++) {
				if(!btns[i][j].isEnabled()) {
					counter++;
				}
			}
		}
		if(counter==((btns.length*btns[0].length)-mines)){
			disableAll(true);
		}
	}
	public int calculate_bombs(JButton [][] button,int x,int y) {//mette i numeri quante sono le bombe adiacenti
		int num_b=0;
		System.out.println(" la y è: "+y+" la x è: "+x);
		if(x==0 && y==0) {// angolo 1
			if(button[y][x+1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y+1][x+1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y+1][x].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
		}
		else if(x==button[0].length-1 && y==0) {//angolo 2
			if(button[y][x-1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y+1][x-1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y+1][x].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
		}
		else if(y==button.length-1 && x==0) {// angolo 3
			if(button[y-1][x].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y-1][x+1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y][x+1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
		}
		else if(y==button.length-1 && x==button[0].length-1) {//angolo4
			if(button[y][x-1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y-1][x-1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y-1][x].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
		}
		else if(x>=1 && y==0) {
			if(button[y][x-1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y+1][x-1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y+1][x].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y+1][x+1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y][x+1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
		}
		else if((y>=1 && y<button.length-1)&& x==0) {
			if(button[y-1][x].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y-1][x+1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y][x+1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y+1][x+1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y+1][x].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
		}
		else if(y==button.length-1 && (x>=1 && x<button[0].length)) {
			if(button[y][x-1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y-1][x-1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y-1][x].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y-1][x+1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y][x+1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
		}
		else if(x==button[0].length-1 &&( y>=1 && y<button.length-1)) {
			if(button[y-1][x].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y-1][x-1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y][x-1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y+1][x-1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y+1][x].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
		}
		else {
			if(button[y-1][x-1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y-1][x].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y-1][x+1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y][x-1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y][x+1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y+1][x-1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y+1][x].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
			if(button[y+1][x+1].getName().equalsIgnoreCase("B")) {
				num_b++;
			}
		}
		
		return num_b;
	}
	public class MioMouse implements ActionListener
	{
		JButton[][] bttn;
		public MioMouse(JButton[][] bttn) {
			this.bttn=bttn;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
			for (int i = 0; i < this.bttn.length; i++) {
				for (int j = 0; j < btns[0].length; j++) {
					if(bttn[i][j]== e.getSource()) {
						repaint();
						revalidate();
						System.out.println("clicked!"+bttn[i][j].getName()+" "+bttn[i][j].getText());
//						bttn[i][j].setBackground(Color.RED);
						bttn[i][j].setText(bttn[i][j].getName());
						bttn[i][j].setEnabled(false);
						if(bttn[i][j].getName().equalsIgnoreCase("B")) {
							bttn[i][j].setBackground(Color.RED);
							disableAll(false);
						}
						else {
							didHeWon(10);
						}
						
					}
				}
			}
			
			
			
		}
		
	}	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new UiGame(10,7);
		
		}

	
	
	

}
