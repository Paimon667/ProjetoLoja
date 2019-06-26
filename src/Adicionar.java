import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
	String[] strings = { "Nome", "Preço", "Código"};
	JButton Adicionar;
	JLabel ImgEscolhida;
	JButton Imagem;
	File file;
	///
	JTextField Usuario;
	JPasswordField senha;
	JButton login;

	public Adicionar(boolean Gerente) {
		this.setLayout(new FlowLayout());
		
		//Se quem acessa é o gerente
		if(Gerente) {
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
		JTextArea texto = new JTextArea("É recomendado "
				+ "que as imagens \nestejam armazenadas em uma pasta específica \ne que não possa ser movida, \nalém do que, as imagens devem ter obrigatóriamente 50x50 pixels");
		texto.setEditable(false);
		texto.setBackground(this.getBackground());
		texto.setSelectionColor(this.getBackground());
		this.add(texto);
		}
		//
		
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
	
	
	public class ProcurarImagem implements ActionListener {
		
		public void actionPerformed(ActionEvent x) {
			JFileChooser arquivo = new JFileChooser();
			arquivo.setFileSelectionMode(arquivo.FILES_ONLY);
			arquivo.showOpenDialog(null);
			file = arquivo.getSelectedFile();
			try {
			ImgEscolhida.setText("");
			ImgEscolhida.setIcon(new ImageIcon(file.getPath()));
			}
			catch(NullPointerException erro) {
				ImgEscolhida.setIcon(null);
				ImgEscolhida.setText("Nenhuma Imagem Selecionada");
			}
			
		}
		
	}

}
