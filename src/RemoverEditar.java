import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RemoverEditar extends JPanel {

	JTextField codigo;
	JButton remover;
	JButton editarPreco;
	JButton editarNome;
	JTextField Usuario;
	JPasswordField senha;
	JButton procurar;
	JLabel imagem;
	JLabel pn;

	public RemoverEditar(boolean Gerente) {

		this.setLayout(new FlowLayout());

		if (Gerente) {
			Box box = Box.createVerticalBox();
			box.add(new JLabel("Insira o Codigo do Produto"));
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
			editarPreco = new JButton("Editar Preco");
			editarNome = new JButton("Editar Nome");
			box.add(remover);
			box.add(box.createVerticalStrut(15));
			box.add(editarPreco);
			box.add(box.createVerticalStrut(15));
			box.add(editarNome);
			this.add(box);
		}

		else {
			Usuario = new JTextField(15);
			Box box = Box.createVerticalBox();
			
			box.add(box.createVerticalStrut(30));
			
			JLabel bemvindo = new JLabel("       Bem Vindo ! ");
			bemvindo.setFont(new Font("SansSerif",Font.BOLD,30));
			box.add(bemvindo);
			box.add(box.createVerticalStrut(10));
			
			JPanel org = new JPanel(new FlowLayout());
			
			JLabel login = new JLabel(new ImageIcon(getClass().getResource("login.png")));

			org.add(login);
			box.add(org);
			
			box.add(box.createVerticalStrut(30));
			JLabel digite = new JLabel(" Digite seu usuario e senha para continuar");
			digite.setFont(new Font("SansSerif",Font.BOLD,15));
			box.add(digite);
			
			box.add(box.createVerticalStrut(10));
			

			JPanel logarU = new JPanel(new FlowLayout());
			logarU.add(new JLabel(new ImageIcon(getClass().getResource("user.png"))));
			logarU.add(Usuario);
			box.add(logarU);

			JPanel logarS = new JPanel(new FlowLayout());
			senha = new JPasswordField(15);
			logarS.add(new JLabel(new ImageIcon(getClass().getResource("cadeado.png"))));
			logarS.add(senha);
			box.add(logarS);
			box.add(box.createVerticalStrut(150));
			
			this.add(box.createVerticalStrut(30), BorderLayout.WEST);
			this.add(box.createVerticalStrut(30), BorderLayout.EAST);
			
			JTextArea txt = new JTextArea("Acesso restrito, apenas pessoal autorizado");
			txt.setFont(new Font("Serif",Font.BOLD,20));
			txt.setEditable(false);
			txt.setBackground(this.getBackground());
			txt.setSelectionColor(this.getBackground());
			
			this.add(box, BorderLayout.CENTER);
			this.add(txt,BorderLayout.SOUTH);
		}

	}

}
