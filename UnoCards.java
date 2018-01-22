/*
 Riwaz Awasthi
 110746533
 CSE114- Final Project
 */
import java.util.ArrayList;
public class UnoCards{
	public ArrayList<String> deck= new ArrayList<String>();
	public ArrayList<String> discard= new ArrayList<String>();
	public UnoCards(){
		deck.add("1_plus_blue.jpg");
		deck.add("1_plus_red.jpg");
		deck.add("2_plus_green.jpg");
		deck.add("2_plus_yellow.jpg");
		for(int i=1;i<=7;i++){
			deck.add(i+"_Red.jpg");
			deck.add(i+"_Blue.jpg");
			deck.add(i+"_Green.jpg");
			deck.add(i+"_Yellow.jpg");
		}
		for(int i=0;i<4;i++){
			deck.add("wild.jpg");
		}
		shuffle(deck);
		
	}
	public ArrayList<String> getDeck(){
		return deck;
	}
	public static void shuffle(ArrayList<String> a){
		java.util.Collections.shuffle(a);
	}
	public String drawCard(){
		String a= deck.get(0);
		deck.remove(0);
		return a;
	}
	
}

