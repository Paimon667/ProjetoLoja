import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Login extends JPanel {

	private JTextField Usuario;
	private JPasswordField senha;
	

	public JTextField getUsuario() {
		return Usuario;
	}


	public void setUsuario(JTextField usuario) {
		Usuario = usuario;
	}


	public JPasswordField getSenha() {
		return senha;
	}


	public void setSenha(JPasswordField senha) {
		this.senha = senha;
	}


	public Login() {
		setLayout(new BorderLayout());
		Usuario = new JTextField(15);
		Box box = Box.createVerticalBox();

		box.add(box.createVerticalStrut(30));

		JLabel bemvindo = new JLabel("       Bem Vindo ! ");
		bemvindo.setFont(new Font("SansSerif", Font.BOLD, 30));
		box.add(bemvindo);
		box.add(box.createVerticalStrut(10));

		JPanel org = new JPanel(new FlowLayout());

		JLabel login = new JLabel(new ImageIcon(getClass().getResource("login.png")));

		org.add(login);
		box.add(org);

		box.add(box.createVerticalStrut(30));
		JLabel digite = new JLabel(" Digite seu usuário e senha para continuar");
		digite.setFont(new Font("SansSerif", Font.BOLD, 15));
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
		txt.setFont(new Font("Serif", Font.BOLD, 20));
		txt.setEditable(false);
		txt.setBackground(this.getBackground());
		txt.setSelectionColor(this.getBackground());
		this.add(box, BorderLayout.CENTER);
		this.add(txt, BorderLayout.SOUTH);

	}
}
