import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Save implements Serializable{

	private Map<Integer, Produto> produtoz;
	private ArrayList<Produto> selecionados;
	
	
	
	public Map<Integer, Produto> getProdutoz() {
		return produtoz;
	}



	public void setProdutoz(Map<Integer, Produto> produtoz) {
		this.produtoz = produtoz;
	}



	public ArrayList<Produto> getSelecionados() {
		return selecionados;
	}



	public void setSelecionados(ArrayList<Produto> selecionados) {
		this.selecionados = selecionados;
	}



	public Save(Map<Integer,Produto> p, ArrayList<Produto> s) {
		produtoz=p;
		selecionados=s;
	}
	
}
