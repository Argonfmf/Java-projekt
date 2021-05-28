package KrizciKrozci2;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class Krizci_in_krozci implements ActionListener{

	Random random = new Random();
	JFrame frame = new JFrame();
	JPanel north = new JPanel();
	JPanel subnorth1 = new JPanel();
	JPanel subnorth2 = new JPanel();
	JPanel south = new JPanel();
	JPanel subsouth1 = new JPanel();
	JPanel subsouth2 = new JPanel();
	JPanel subsouth3 = new JPanel();
	JPanel button_panel = new JPanel();
	JLabel title = new JLabel();
	JLabel turn = new JLabel();
	JLabel stats_x = new JLabel();
	JLabel stats_o = new JLabel();
	JButton[] buttons = new JButton[9];
	JButton button = new JButton("Restart");
	JButton button1 = new JButton("Reset");
	JCheckBox checkbox = new JCheckBox("AI hard", false);
	JCheckBox checkbox1 = new JCheckBox("AI easy", false);
	boolean player1_turn;
	//Pomožna spremenljivka, ki se spremeni v 1, èe kdo zmaga.
	int z = 0;
	//Število zmag za X in O.
	int zmage_x;
	int zmage_o;
	//Pove v katerem "turnu" smo.
	int t = 0;
	//Pomaga pri tem, èe je paten AI že naredil potezo ta "turn".
	int s = 0;

	Krizci_in_krozci(){

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(800,850));
		frame.setMinimumSize(new Dimension(650, 700));
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);

		//Naslov "Križci in Krožci".
		title.setBackground(Color.black);
		title.setForeground(Color.white);
		title.setFont(new Font("Times new roman",Font.BOLD,40));
		title.setText("Križci in Krožci");
		title.setOpaque(true);
		//Napis, ki pove kdo je na vrsti.
		turn.setBackground(Color.black);
		turn.setForeground(Color.white);
		turn.setFont(new Font("Times new roman",Font.BOLD,30));
		turn.setText("");
		turn.setOpaque(true);
		//Napis, ki pove koliko zmag ima X igralec, šteje se samo zmage kadar prava igralca igrata drug proti drugemu.
		stats_x.setBackground(Color.black);
		stats_x.setForeground(Color.white);
		stats_x.setFont(new Font("Times new roman",Font.BOLD,20));
		stats_x.setText("X wins: " + zmage_x);
		//Napis, ki pove koliko zmag ima O igralec, enako kot pri X.
		stats_o.setBackground(Color.black);
		stats_o.setForeground(Color.white);
		stats_o.setFont(new Font("Times new roman",Font.BOLD,20));
		stats_o.setText("O wins: " + zmage_o);

		button_panel.setLayout(new GridLayout(3,3));
		
		//Dodajanje 9 igralnih gumbov.
		for(int i=0;i<9;i++) {
			buttons[i] = new JButton();
			button_panel.add(buttons[i]);
			buttons[i].setFont(new Font("Comic Sans MS",Font.BOLD,150));
			buttons[i].setBackground(Color.white);
			buttons[i].setFocusable(false);
			buttons[i].addActionListener(this);
		}
		
		frame.setLayout(new BorderLayout());
		frame.add(north, BorderLayout.NORTH);
		frame.add(south, BorderLayout.SOUTH);
		frame.add(button_panel, BorderLayout.CENTER);
		
		//Gumb, ki na novo zaène igro (ko je ta že konèana ali pa sredi igre).
		button.setPreferredSize(new Dimension(96, 40));
		button.setFocusable(false);
		button.setBackground(Color.white);
		button.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		       firstTurn();
		       for(int i=0;i<9;i++) {
		    	   buttons[i].setText("");
		    	   buttons[i].setEnabled(true);
		    	   buttons[i].setBackground(Color.white);   	   
		       }
		       z = 0;
		       t = 0;
		       s = 0;
		       if (checkbox.isSelected()) {
		    	   turn.setText("X turn");
		       }
		    }
		});
		
		//Gumb, ki resetira statistiko zmag.
		button1.setPreferredSize(new Dimension(70, 20));
		button1.setFocusable(false);
		button1.setBackground(Color.white);
		button1.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	zmage_x = 0;
		    	zmage_o = 0;
		    	stats_x.setText("X wins: " + zmage_x);
		    	stats_o.setText("O wins: " + zmage_o);
		    }
		});
		
		//Èe se obkljuka checkbox, se igra na nove zaène proti težkemu AI nasprotniku.
		checkbox.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        firstTurn();
		        for(int i=0;i<9;i++) {
			    	   buttons[i].setText("");
			    	   buttons[i].setEnabled(true);
			    	   buttons[i].setBackground(Color.white);
		        }
		        z = 0;
		        t = 0;
			    s = 0;
			    checkbox1.setSelected(false);
		        turn.setText("X turn");
		    }
		});
		checkbox.setFocusable(false);
		checkbox.setBackground(Color.white);
		
		//Èe se obkljuka checkbox, se igra na nove zaène proti lahkemu AI nasprotniku.
		checkbox1.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        firstTurn();
		        for(int i=0;i<9;i++) {
			    	   buttons[i].setText("");
			    	   buttons[i].setEnabled(true);
			    	   buttons[i].setBackground(Color.white);
		        }
		        z = 0;
		        t = 0;
			    s = 0;
			    checkbox.setSelected(false);
		        turn.setText("X turn");
		    }
		});
		checkbox1.setFocusable(false);
		checkbox1.setBackground(Color.white);
		
		//Severni panel, ki mu dodamo ime igre, napis kdo je na vrsti, oba AI nasprotnika in restart gumb.
		north.setLayout(new GridLayout(2,1));
		
		subnorth1.setBackground(Color.black);
		subnorth2.setBackground(Color.black);
		subnorth1.add(title);
		subnorth1.add(button);
		subnorth2.add(checkbox1);
		subnorth2.add(turn);
		subnorth2.add(checkbox);
		north.add(subnorth1);
		north.add(subnorth2);
		
		//Južni panel, ki mu dodamo napis, ki pove število zmag X in O, ter gumb ki resetira to statistiko.
		south.setLayout(new GridLayout(3, 1));

		subsouth1.setBackground(Color.black);
		subsouth2.setBackground(Color.black);
		subsouth3.setBackground(Color.black);
		subsouth1.add(stats_x);
		subsouth2.add(stats_o);
		subsouth3.add(button1);
		south.add(subsouth1);
		south.add(subsouth2);
		south.add(subsouth3);
		
		firstTurn();
	}
	
	//Pove kaj se dogaja z glavnimi devetimi gumbi (igralno plošèo), pri prvem if-u kadar igra èlovek proti AI-ju,
	//pri else-u pa kadar dva èloveka igrata drug proti drugemu.
	@Override
	public void actionPerformed(ActionEvent e) {
		if (checkbox.isSelected() || checkbox1.isSelected()) {
        	List<Integer> avalibleCells = new ArrayList<Integer>();
    		for(int i=0;i<9;i++) {
    			if(buttons[i].getText()=="") {
    				avalibleCells.add(i);
    			}
    		}
    		int velikost = avalibleCells.size();
        	for(int i=0;i<9;i++) {
    			if(e.getSource()==buttons[i]) {
    				if(player1_turn || velikost == 9) {
    					if(buttons[i].getText()=="") {
    						buttons[i].setForeground(new Color(255,0,0));
    						buttons[i].setText("X");
    						player1_turn=false;
    						turn.setText("O turn");
    						check();
    					}
    				}
    				if(velikost !=1 && z == 0) {
    					computer_turn();
    				}
    			}
        	}
    	}
		else {
			for(int i=0;i<9;i++) {
				if(e.getSource()==buttons[i]) {
					if(player1_turn) {
						if(buttons[i].getText()=="") {
							buttons[i].setForeground(new Color(255,0,0));
							buttons[i].setText("X");
							player1_turn=false;
							turn.setText("O turn");
							check();
						}
					}	
					else {
						if(buttons[i].getText()=="") {
							buttons[i].setForeground(new Color(0,0,255));
							buttons[i].setText("O");
							player1_turn=true;
							turn.setText("X turn");
							check();
						}
					}
				}			
			}
		}
	}
	
	//Kadar se klièe ta metoda, lahek AI ali težek AI izvede svojo poteza, odvisno kateri checkbox je obkljukan.
	public void computer_turn(){
		if(player1_turn == false) {
			t = t + 1;
			if (checkbox.isSelected()) {
				bestMove();
			}
			else {
				randomO();
			}
		}
	}
	
	//Metoda, ki naredi nakljuèno potezo, kadar je lahek AI na vrsti.
	public void randomO() {
		int random1 = (int) (Math.random()*9);
		int a = 0;
		while(a != 1){
			if (buttons[random1].getText()=="") {
				buttons[random1].setForeground(Color.BLACK);
				buttons[random1].setText("O");
				a = 1;				
			}
			else {
				random1 = (int) (Math.random()*9);
			}
		}
		player1_turn=true;
		turn.setText("X turn");
		check();
	}
	
	//Gleda èe se pojavi zaporedje dveh O ali dveh X, da lahko zmagamo oziroma blokiramo.
	public void zapDveh(String znaki) {
		s = 0;
		// po vrsticah
		for (int i=0;i<9;i+=3) {
			if ((buttons[i].getText() + buttons[i+1].getText()).equals(znaki) && buttons[i+2].getText() == "") {
				buttons[i+2].setText("O");
				s = 1;
			}
			else if((buttons[i+1].getText() + buttons[i+2].getText()).equals(znaki) && buttons[i].getText() == "") {
				buttons[i].setText("O");
				s = 1;
			}
			else if((buttons[i].getText() + buttons[i+2].getText()).equals(znaki) && buttons[i+1].getText() == "") {
				buttons[i+1].setText("O");
				s = 1;
			}
		}
		if (s == 0) {
			// po stolpcih
			for (int i=0;i<3;i+=1) {
				if((buttons[i].getText() + buttons[i+3].getText()).equals(znaki) && buttons[i+6].getText() == "") {
					buttons[i+6].setText("O");
					s = 1;
				}
				else if((buttons[i+3].getText() + buttons[i+6].getText()).equals(znaki) && buttons[i].getText() == "") {
					buttons[i].setText("O");
					s = 1;
				}
				else if((buttons[i].getText() + buttons[i+6].getText()).equals(znaki) && buttons[i+3].getText() == "") {
					buttons[i+3].setText("O");
					s = 1;
				}
			}
		}

		if (s == 0) {
			// po diagonalah
			for (int i=0;i<1;i+=1) {
				if((buttons[i].getText() + buttons[i+4].getText()).equals(znaki) && buttons[i+8].getText() == "") {
					buttons[i+8].setText("O");
					s = 1;
				}
				else if((buttons[i+4].getText() + buttons[i+8].getText()).equals(znaki) && buttons[i].getText() == "") {
					buttons[i].setText("O");
					s = 1;
				}
				else if((buttons[i].getText() + buttons[i+8].getText()).equals(znaki) && buttons[i+4].getText() == "") {
					buttons[i+4].setText("O");
					s = 1;
				}
				else if((buttons[i+2].getText() + buttons[i+4].getText()).equals(znaki) && buttons[i+6].getText() == "") {
					buttons[i+6].setText("O");
					s = 1;
				}
				else if((buttons[i+4].getText() + buttons[i+6].getText()).equals(znaki) && buttons[i+2].getText() == "") {
					buttons[i+2].setText("O");
					s = 1;
				}
				else if((buttons[i+2].getText() + buttons[i+6].getText()).equals(znaki) && buttons[i+4].getText() == "") {
					buttons[i+4].setText("O");
					s = 1;
				}
			}
		}
	}
	
	public void preverimoZaKogaGledamo(boolean obramba){
	    if (obramba == true){
	    	String znaki = "XX";
	    	zapDveh(znaki);
	    }
	    else {
	    	String znaki = "OO";
	    	zapDveh(znaki);
	    }
	}
	
	public void preprecimoPoljaZDvemaMoznimaZmagama() {
		for(int i=0;i<9;i+=1) {
			if(i==0 && s == 0) {
				if((buttons[i+1].getText() + buttons[i+2].getText()).equals("X") && (buttons[i+3].getText() + buttons[i+6].getText()).equals("X")) {
					buttons[i].setText("O");
					s = 1;
				}
			}
			else if(i==2 && s == 0) {
				if((buttons[i-2].getText() + buttons[i-1].getText()).equals("X") && (buttons[i+3].getText() + buttons[i+6].getText()).equals("X")){
					buttons[i].setText("O");
					s = 1;
				}
			}
			else if(i==6 && s == 0) {
				if((buttons[i-6].getText() + buttons[i-3].getText()).equals("X") && (buttons[i+1].getText() + buttons[i+2].getText()).equals("X")) {
					buttons[i].setText("O");
					s = 1;
				}
			}
			else if(i==8 && s == 0) {
				if((buttons[i-6].getText() + buttons[i-3].getText()).equals("X") && (buttons[i-2].getText() + buttons[i-1].getText()).equals("X")) {
					buttons[i].setText("O");
					s = 1;
				}
			}
		}
	}
	
	//Èe se ne zgodi nobeden od drugih scenarijev, poskušamo dobiti tri v vrsto, tako da naredimo zaporedje dveh.
	public void zaporedjeEnega() {
        for (int i=0;i<9;i+=3) {
            if((buttons[i].getText() + buttons[i+1].getText() + buttons[i+2].getText()).equals("O")){
                if(buttons[i].getText() == "") {
                    buttons[i].setText("O");
                    s = 1;
                }
                else if (buttons[i+2].getText() == ""){
                    buttons[i+2].setText("O");
                    s = 1;
                }
            }
        }
        for (int i=0;i<3;i+=1) {
            if((buttons[i].getText() + buttons[i+3].getText() + buttons[i+6].getText()).equals("O")){
                if(buttons[i].getText() == "") {
                    buttons[i].setText("O");
                    s = 1;
                }
                else if (buttons[i+6].getText() == ""){
                    buttons[i+6].setText("O");
                    s = 1;
                }
            }
        }
    }
	
	//Metoda, kjer pameten AI naredi naèeloma najboljšo možno potezo.
	public void bestMove() {
		for(int ind=0;ind<9;ind++) {
			if(buttons[ind].getText()=="") {
				buttons[ind].setForeground(Color.BLACK);
			}
		}
		if(t==1) {
			if(buttons[4].getText() == "") {
				buttons[4].setText("O");
			}
			else {
				int[] array = {0,2,6,8};
				int ind = new Random().nextInt(array.length);
				buttons[array[ind]].setText("O");
			}
		}
		if(t==2) {
			preverimoZaKogaGledamo(true);
			if (s==0) {
				if ((buttons[0].getText() + buttons[8].getText()).equals("XX") || (buttons[2].getText() + buttons[6].getText()).equals("XX")) {
                    buttons[3].setText("O");
                    s = 1;
				}
			}
			if (s == 0) {
				preprecimoPoljaZDvemaMoznimaZmagama();
			}
			if (s == 0 && buttons[4].getText().equals("X")) {
				if (buttons[0].getText().equals("X") || buttons[2].getText().equals("X") || buttons[6].getText().equals("X") || buttons[8].getText().equals("X")) {
					for (int i=0;i<9;i=+2) {
						if (buttons[i].getText().equals("")) {
							buttons[i].setText("O");
							s = 1;
							break;
						}
					}
				}				
			}
			if (s==0) {
				if(buttons[3].getText() == "") {
					buttons[3].setText("O");
				}
				else {
					buttons[1].setText("O");
				}
			}
		}
		if(t==3) {
			preverimoZaKogaGledamo(false);
			if (s == 0) {
				preverimoZaKogaGledamo(true);
			}
			if (s == 0 && buttons[4].getText() == "O") {
                if((buttons[0].getText() + buttons[4].getText() + buttons[8].getText()).length() == 3){
                    buttons[2].setText("O");
                    s = 1;
                }
                else if((buttons[2].getText() + buttons[4].getText() + buttons[6].getText()).length() == 3){
                    buttons[8].setText("O");
                    s = 1;
                }
                else if((buttons[0].getText() + buttons[4].getText() + buttons[8].getText()).length() != 3) {
                    if(buttons[5].getText() == "") {
                        buttons[5].setText("O");
                        s = 1;
                    }
                    else if(buttons[7].getText() == ""){
                        buttons[7].setText("O");
                        s = 1;
                    }
                }
			}
			if (s == 0) {
				zaporedjeEnega();
			}
		}
		if(t==4) {
			preverimoZaKogaGledamo(false);
			if (s == 0) {
				preverimoZaKogaGledamo(true);
			}
			if (s == 0) {
				for(int i=0;i<9;i++) {
					if(buttons[i].getText()=="") {
						buttons[i].setText("O");
		    			break;
		    		}
				}
			}
		}
		player1_turn=true;
		turn.setText("X turn");
		check();
	}
	
	//Ko se igra zaène se žreba kdo zaène, samo kadar prava igralca igrata med seboj.
	public void firstTurn() {
		if(random.nextInt(2) == 0) {
			player1_turn = true;
			turn.setText("X turn");
		}

		else {
			player1_turn = false;
			turn.setText("O turn");
		}
	}
	
	//Metoda pogleda, èe imamo zmagovalca oziroma neodloèen rezultat.
	public void check() {
		for (int i=0;i<7;i+=3) {
			String vrstica = buttons[i].getText() + buttons[i+1].getText() + buttons[i+2].getText();
			if (vrstica.equals("XXX")) {
				xWins(i,i+1,i+2);
			}
			if (vrstica.equals("OOO")) {
				oWins(i,i+1,i+2);
			}
		}
		for (int i=0;i<3;i+=1) {
			String stolpec = buttons[i].getText() + buttons[i+3].getText() + buttons[i+6].getText();
			if (stolpec.equals("XXX")){
				xWins(i,i+3,i+6);
			}
			if (stolpec.equals("OOO")){
				oWins(i,i+3,i+6);
			}
		}
		for (int i=0;i<1;i+=1) {
			String diagonala1 = buttons[i].getText() + buttons[i+4].getText() + buttons[i+8].getText();
			String diagonala2 = buttons[i+2].getText() + buttons[i+4].getText() + buttons[i+6].getText();
			if (diagonala1.equals("XXX")){
				xWins(i,i+4,i+8);
			}
			if (diagonala1.equals("OOO")){
				oWins(i,i+4,i+8);
			}
			if (diagonala2.equals("XXX")){
				xWins(i+2,i+4,i+6);
			}
			if (diagonala2.equals("OOO")){
				oWins(i+2,i+4,i+6);
			}
		}
		if(z == 0) {
			tie();
		}
	}
	
	//Èe X zmaga, metoda izpiše zmagovalca, pokaže zmagovalna polja ter zablokira gumbe.
	public void xWins(int a,int b,int c) {
		z = 1;
		buttons[a].setBackground(Color.GREEN);
		buttons[b].setBackground(Color.GREEN);
		buttons[c].setBackground(Color.GREEN);		

		for(int i=0;i<9;i++) {
			buttons[i].setEnabled(false);
		}
		if (checkbox.isSelected() == false && checkbox1.isSelected() == false) {
			zmage_x++;
		}	
		turn.setText("X wins");
		stats_x.setText("X wins: " + zmage_x);
	}
	
	//Enako kot za X, samo da je za O.
	public void oWins(int a,int b,int c) {
		z = 1;
		buttons[a].setBackground(Color.GREEN);
		buttons[b].setBackground(Color.GREEN);
		buttons[c].setBackground(Color.GREEN);

		for(int i=0;i<9;i++) {
			buttons[i].setEnabled(false);
		}
		if (checkbox.isSelected() == false && checkbox1.isSelected() == false) {
			zmage_o++;
		}
		turn.setText("O wins");
		stats_o.setText("O wins: " + zmage_o);
	}
	
	//Metoda, ki ugotovi èe je neodloèen rezultat, kar se tudi izpiše, gumbi ratajo zablokirani.
	public void tie() {
		int izenacenje = 0;
		for (int i=0;i<9;i+=1) {
			if (buttons[i].getText() == "O" || buttons[i].getText() == "X") {
				izenacenje++;
			}
			else {
				break;
			}
			if (izenacenje == 9) {
				for (int ind=0;ind<9;ind++) {
					buttons[ind].setEnabled(false);
				}
				turn.setText("It's a tie");
			}
		}
	}
}