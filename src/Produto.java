import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Produto extends JPanel implements ProdutoGenerico {

	private String Nome;
	private int Preco;
	private int Codigo;
	JButton comprar;
	private String imagem;
	public String info;
	public JTextArea texto;

	public String getNome() {
		return this.Nome;
	}

	public void setNome(String nome) {
		this.Nome = nome;
	}

	public int getPreco() {
		return this.Preco;
	}

	public void setPreco(int preco) {
		this.Preco = preco;
	}

	public int getCodigo() {
		return this.Codigo;
	}

	public void setCodigo(int codigo) {
		this.Codigo = codigo;
	}	
	
	public String getImagem() {
		return imagem;
	}
	
	public JButton getComprar() {
		return comprar;
	}

	public void setComprar(JButton comprar) {
		this.comprar = comprar;
	}

	public Produto() {
		
	}

	
	public Produto(String nome, int preco, int codigo, JButton botaocomprar,String imagem,String info) {
		this.Nome=nome;
		this.Preco=preco;
		this.Codigo=codigo; 
		this.imagem=imagem;
		this.comprar=botaocomprar;
		this.info=info;
		setLayout(new FlowLayout());
		Box organizador = Box.createVerticalBox();
		Box organizaBotao = Box.createHorizontalBox();
		texto = new JTextArea("R$: "+this.Preco+"\n"+this.Nome+"\n"+this.info);
		texto.setEditable(false);
		texto.setSelectedTextColor(this.getBackground());
		texto.setBackground(this.getBackground());
		texto.setFont(new Font("TimesRman",Font.BOLD,14));
		organizaBotao.add(this.comprar);
		organizaBotao.add(Box.createHorizontalStrut(5));
		organizador.add(organizaBotao);
		organizador.add(Box.createVerticalStrut(10));
		organizador.add(this.texto);
		add(organizador);
		
	}
	

}
