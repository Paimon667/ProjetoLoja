import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Produto extends JPanel implements ProdutoGenerico {

	private String Nome;
	private int Preco;
	private int Codigo;
	JButton comprar;
	private String imagem;
	public JLabel texto; 

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

	
	public Produto(String nome, int preco, int codigo, JButton botaocomprar,String imagem) {
		this.Nome=nome;
		this.Preco=preco;
		this.Codigo=codigo; 
		this.imagem=imagem;
		this.comprar=botaocomprar;
		setLayout(new FlowLayout());
		texto = new JLabel(this.Nome+"    R$: "+this.Preco);
		texto.setFont(new Font("AppleGhotic",Font.PLAIN,12));
		add(botaocomprar);
		add(texto);
		
	}
	

}
