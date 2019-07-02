import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;
import javax.swing.ImageIcon;
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
		setLayout(new FlowLayout());
		Box b = Box.createVerticalBox();
		lista = new JList();
		produtoz = new String[20];
		int total = 0;
		int i = 0;
		if (produtos.size() > 0) { 
			for (Produto p : produtos) {
				produtoz[i] = p.getCodigo()+" - "+p.getNome()+"                                                       ";
				char[] arrumar = produtoz[i].toCharArray();
				arrumar[40]='R';
				arrumar[41]='$';
				String jota = ""+p.getPreco();
				for(int zzz=43;zzz<jota.length()+43;zzz++) {
					arrumar[zzz]=jota.charAt(zzz-43);
				}
				produtoz[i]= new String(arrumar);
				i++;
				total += p.getPreco();
			}
		}
		lista.setListData(produtoz);
		lista.setVisibleRowCount(10);
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lista.setFixedCellHeight(20);
		lista.setFixedCellWidth(175);
		
		Box organizar = Box.createHorizontalBox();
		comprar = new JButton("Comprar");
		comprar.setFont(new Font("AmericanTypewriter",Font.ITALIC,17));
		remover = new JButton("Remover");
		remover.setFont(new Font("AmericanTypewriter",Font.ITALIC,17));
		this.total = new JLabel("Total R$: " + total);
		this.total.setFont(new Font("TimesRoman",Font.BOLD,20));
		organizar.add(Box.createHorizontalStrut(20));
		organizar.add(new JLabel(new ImageIcon(getClass().getResource("carrinho.png"))));
		organizar.add(Box.createHorizontalStrut(20));
		organizar.add(comprar);
		organizar.add(Box.createHorizontalStrut(20));
		organizar.add(remover);
		organizar.add(Box.createHorizontalStrut(20));
		organizar.add(this.total);
		b.add(Box.createVerticalStrut(29));
        JLabel labeltopo = new JLabel("Seu carrinho de compras:                               ");
        labeltopo.setFont(new Font("TimesRoman",Font.BOLD,20));
        JLabel infor = new JLabel(produtos.size()>1 || produtos.size()==0? "Seu carrinho possui "+produtos.size()+" produtos               "
        		: "Seu carrinho possui "+produtos.size()+" produto");
        infor.setFont(new Font("TimesRoman",Font.BOLD,15));
        b.add(labeltopo);
  
        b.add(Box.createVerticalStrut(29));
		b.add((new JScrollPane(lista)));
		b.add(infor);
		b.add(Box.createVerticalStrut(160));
		b.add(organizar);
		add(b);
	}

}
