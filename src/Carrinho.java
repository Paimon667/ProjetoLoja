import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class Carrinho extends JPanel {

	JList lista;
	JLabel total;
	JButton remover;
	JButton comprar;
	String[] produtoz;

	public Carrinho(ArrayList<Produto> produtos) {
		lista = new JList();
		produtoz = new String[20];
		int total = 0;
		int i = 0;
		if (produtos.size() > 0) {
			for (Produto p : produtos) {
				produtoz[i] = p.getNome() + " .............. R$: " + p.getPreco();
				i++;
				total += p.getPreco();
			}
		}
		lista.setListData(produtoz);
		lista.setVisibleRowCount(10);
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lista.setFixedCellHeight(30);
		lista.setFixedCellWidth(100);
		comprar = new JButton("Comprar");
		remover = new JButton("Remover");
		this.total = new JLabel("R$: " + total);
		add(new JScrollPane(lista), BorderLayout.EAST);
		add(this.total, BorderLayout.WEST);
		add(comprar, BorderLayout.NORTH);
		add(remover, BorderLayout.SOUTH);
	}

}
