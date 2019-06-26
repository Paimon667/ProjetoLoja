import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RemoverEditar extends JPanel {

	JTextField codigo;
	JButton remover;
	JButton editarPreco;
	JButton editarNome;
	JTextField Usuario;
	JPasswordField senha;
	JButton login;
	JButton procurar;
	JLabel imagem;
	JLabel pn;
	
	public RemoverEditar(boolean Gerente) {

		this.setLayout(new FlowLayout());
		
		if(Gerente) {
			Box box = Box.createVerticalBox();
			box.add(new JLabel("Insira o Código do Produto"));
			box.add(Box.createVerticalStrut(5));
			codigo = new JTextField(10);
			box.add(codigo);
			procurar = new JButton("Procurar");
			box.add(box.createVerticalStrut(10));
			box.add(procurar);
			box.add(box.createVerticalStrut(40));
			imagem = new JLabel("Nenhum produto selecionado");
			box.add(imagem);
			pn = new JLabel("");
			box.add(pn);
			box.add(box.createVerticalStrut(40));
			remover = new JButton("Remover");
			editarPreco = new JButton("Editar Preço");
			editarNome = new JButton("Editar Nome");
			box.add(remover);
			box.add(box.createVerticalStrut(15));
			box.add(editarPreco);
			box.add(box.createVerticalStrut(15));
			box.add(editarNome);	
			this.add(box);
		}
		
		else {
			Box box = Box.createVerticalBox();
			Usuario = new JTextField(10);
			senha = new JPasswordField(10);
			login = new JButton("Login");
			box.add(Usuario);
			box.add(box.createVerticalStrut(5));
			box.add(senha);
			box.add(box.createVerticalStrut(5));
			box.add(login);
			this.add(box);
		}
		
	}

}
