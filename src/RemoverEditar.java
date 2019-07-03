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

	public JTextField codigo;
	public JButton remover;
	public JButton salvar;
	public JTextField NovoNome;
	public JTextField NovoPreco;
	public JTextField NovaInfo;
	public Login login;
	public JButton procurar;
	public JLabel imagem;

	public RemoverEditar(boolean Gerente) {

		this.setLayout(new FlowLayout());

		if (Gerente) {
			Box central = Box.createVerticalBox();
			central.add(Box.createVerticalStrut(20));
			procurar = new JButton("   Procurar   ");
			procurar.setFont(new Font("AmericanTypewriter",Font.BOLD,12));
			codigo = new JTextField(6);
			JLabel Informacao1 = new JLabel("Favor inserir o código       ");
			JLabel Informacao2 = new JLabel("  do produto desejado        ");
			Informacao1.setFont(new Font("TimesRoman",Font.ITALIC,15));
			Informacao2.setFont(new Font("TimesRoman",Font.ITALIC,15));
			central.add(Informacao1);
			central.add(Informacao2);
			central.add(Box.createVerticalStrut(10));
			central.add(codigo);
			central.add(Box.createVerticalStrut(10));
			central.add(procurar);
			central.add(Box.createVerticalStrut(60));
			Box centro = Box.createHorizontalBox();
			Box direitaCentro = Box.createVerticalBox();
			
			NovoPreco = new JTextField(6);
			NovoNome = new JTextField(6);
			NovaInfo = new JTextField(6);
			direitaCentro.add(new JLabel("Nome: "));
			direitaCentro.add(NovoNome);
			direitaCentro.add(Box.createVerticalStrut(10));
			direitaCentro.add(new JLabel("Preco: "));
			direitaCentro.add(NovoPreco);
			direitaCentro.add(Box.createVerticalStrut(10));
			direitaCentro.add(new JLabel("Informações: "));
			direitaCentro.add(NovaInfo);

			imagem = new JLabel(new ImageIcon(getClass().getResource("branco.png")));
			centro.add(imagem);
			centro.add(Box.createHorizontalStrut(15));
			centro.add(direitaCentro);
			central.add(centro);
			JLabel infor1 = new JLabel("       Insira o que deseja atualizar");
			infor1.setFont(new Font("TimesRoman",Font.ITALIC,12));
			central.add(Box.createVerticalStrut(15));
			central.add(infor1);
			central.add(Box.createVerticalStrut(100));
			JPanel embaixo = new JPanel(new FlowLayout());
			remover = new JButton("Remover");
			remover.setToolTipText("Pressione para remover o produto selecionado");
			remover.setFont(new Font("TimesRoman",Font.BOLD,16));
			
			salvar = new JButton("    Salvar    ");
			salvar.setFont(new Font("TimesRoman",Font.BOLD,16));
			salvar.setToolTipText("Pressione para salvar as alterações");
			
			Box organizarEmbaixo = Box.createHorizontalBox();
			organizarEmbaixo.add(Box.createHorizontalStrut(180));
			organizarEmbaixo.add(salvar);
			organizarEmbaixo.add(Box.createHorizontalStrut(20));
			organizarEmbaixo.add(remover);
			organizarEmbaixo.add(Box.createHorizontalStrut(150));
			add(central);
			add(organizarEmbaixo);
			
		
		}
		else {
			login = new Login();
			add(login,BorderLayout.CENTER);
		}	

	}

}