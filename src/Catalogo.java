import java.awt.GridLayout;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Catalogo extends JPanel {

	public Catalogo(Map<Integer, Produto> produtoz) {
		setLayout(new GridLayout(3, 3));
		int l = 0;
		int c = 0;
		for (Integer i : produtoz.keySet()) {
			if (c < 3) {
				add(produtoz.get(i), l, c);
				c++;
			} else if (l < 3) {
				c = 0;
				add(produtoz.get(i), l, c);
				l++;
			}
			else {
				break;
			}
		}
	}
}
