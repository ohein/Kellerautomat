import java.util.*;

public class Kellerautomat {
	
	private Stack<String> stack = new Stack<>();

	public void auswerten(String ausdruck) {
		stack.clear();
		try {
			int pos = 0;
			while(pos < ausdruck.length()) {
				char c = ausdruck.charAt(pos);
				if(Character.isDigit(c)) {
					pos = leseZahl(pos, ausdruck);
				}
				else {
					leseZeichen(c);
					pos++;
				}
			}
			
			float ergebnis = Float.valueOf(stack.pop());
			if(stack.isEmpty()) {
				System.out.println("Ergebnis = " + ergebnis);
			}
			else {
				System.out.println(ausdruck + " ist nicht vollständig geklammert");
			}
		}
		
		catch(Exception ex) {
			System.out.println("Ungültiger Ausdruck! " + ex.getMessage());
		}		
	}
	
	private void leseZeichen(char c) throws Exception {
		switch(c) {
			case '(':
			case ' ': break; // öffnende Klammer ignorieren
			case '+':
			case '-':
			case '*':
			case '/': stack.push(String.valueOf(c)); // Operatoren merken
				break;
			case ')': klammerAuswerten();
				break;
			default: throw new Exception("Unbekanntes Zeichen: " + c);
		}
	}
	
	private void klammerAuswerten() throws Exception {
		float zahl_rechts = Float.valueOf(stack.pop());
		char op = String.valueOf(stack.pop()).charAt(0);
		float zahl_links = Float.valueOf(stack.pop());
		float ergebnis;
		switch(op) {
			case '+': ergebnis = zahl_links + zahl_rechts;
				break;
			case '-': ergebnis = zahl_links - zahl_rechts;
				break;
			case '*': ergebnis = zahl_links * zahl_rechts;
				break;
			case '/': ergebnis = zahl_links / zahl_rechts;
				break;
			default: throw new Exception("Unbekannter Operator: " + op);
		}
		stack.push(String.valueOf(ergebnis)); // Ergebnis merken
	}
	
	private int leseZahl(int startPos, String ausdruck) {
		int ende = startPos+1;
		for(int j = ende; j < ausdruck.length(); j++) {
			char d = ausdruck.charAt(j);
			if(!Character.isDigit(d) && !(d == '.')) {
				ende = j;
				break;
			}
		}
		stack.push(ausdruck.substring(startPos, ende));
		return ende;
	}
}
