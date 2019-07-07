import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Catalogo extends JPanel {

	public Catalogo(Map<Integer, Produto> produtoz) {
		setLayout(new GridLayout(3, 3));
		int l = 0;
		int c = 0;
		for (Integer i : produtoz.keySet()) {
			if(c<3) {
				add(produtoz.get(i),l,c);
				c++;
			}
			if(c>2) {
				c=0;
				l++;
			}
			if(l>2) {
				break;
			}
		}
	}
}
