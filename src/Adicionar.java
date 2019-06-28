import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Adicionar extends JPanel {

	JTextField[] campo;
	JLabel[] palavras;
	JPanel[] paineis;
	String[] strings = { "Nome", "Preco", "Codigo" };
	JButton Adicionar;
	JLabel ImgEscolhida;
	JButton Imagem;
	File file;
	///
	JTextField Usuario;
	JPasswordField senha;

	public Adicionar(boolean Gerente) {
		this.setLayout(new FlowLayout());

		// Se quem acessa � o gerente
		if (Gerente) {
			Box boxEs = Box.createVerticalBox();
			campo = new JTextField[3];
			palavras = new JLabel[3];
			paineis = new JPanel[3];
			Adicionar = new JButton("Adicionar");
			for (int i = 0; i < 3; i++) {
				palavras[i] = new JLabel(strings[i]);
				paineis[i] = new JPanel();
				campo[i] = new JTextField(10);
				paineis[i].add(campo[i], BorderLayout.NORTH);
				paineis[i].add(palavras[i], BorderLayout.WEST);
				boxEs.add(paineis[i]);
				boxEs.add(Box.createVerticalStrut(5));
			}
			Imagem = new JButton("Procurar Imagem");
			Imagem.addActionListener(new ProcurarImagem());
			boxEs.add(Imagem);
			boxEs.add(Box.createVerticalStrut(25));
			Adicionar = new JButton("Adicionar");
			boxEs.add(Adicionar);
			boxEs.add(Box.createVerticalStrut(60));
			this.add(boxEs);
			ImgEscolhida = new JLabel("Nenhuma Imagem Selecionada");
			this.add(ImgEscolhida);
			JTextArea texto = new JTextArea("eh recomendado "
					+ "que as imagens \nestejam armazenadas em uma pasta especifica \ne que nao possa ser movida, \nalem do que, as imagens devem ter obrigatariamente 50x50 pixels");
			texto.setEditable(false);
			texto.setBackground(this.getBackground());
			texto.setSelectionColor(this.getBackground());
			this.add(texto);
		}
		//

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

	public class ProcurarImagem implements ActionListener {

		public void actionPerformed(ActionEvent x) {
			JFileChooser arquivo = new JFileChooser();
			arquivo.setFileSelectionMode(arquivo.FILES_ONLY);
			arquivo.showOpenDialog(null);
			file = arquivo.getSelectedFile();
			try {
				ImgEscolhida.setText("");
				ImgEscolhida.setIcon(new ImageIcon(file.getPath()));
			} catch (NullPointerException erro) {
				ImgEscolhida.setIcon(null);
				ImgEscolhida.setText("Nenhuma Imagem Selecionada");
			}

		}

	}

}
