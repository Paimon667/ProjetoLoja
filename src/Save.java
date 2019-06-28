import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Save implements Serializable{

	Map<Integer, Produto> produtoz;
	ArrayList<Produto> selecionados;
	
	public Save(Map<Integer,Produto> p, ArrayList<Produto> s) {
		produtoz=p;
		selecionados=s;
	}
	
}
