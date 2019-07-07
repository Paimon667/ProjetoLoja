import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;


interface ProdutoGenerico {
	
	public String getNome();
	public void setNome(String nome);
	public int getPreco();
	public void setPreco(int preco);
	public int getCodigo();
	public void setCodigo(int codigo);
	public JTextArea getTexto();
	public void setTexto(JTextArea texto);
	public String getImagem();
	public void setImagem(String imagem);
	public JButton getComprar();
	public void setComprar(JButton comprar);
	public String getInfo();
	public void setInfo(String info);

	
}
