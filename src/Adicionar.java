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

	public JTextField[] campo;
	public JLabel[] palavras;
	public JPanel[] paineis;
	private String[] strings = { "Nome", "Pre�o", "Cod. ","Info. " };
	public JButton Adicionar;
	public JLabel ImgEscolhida;
	private JButton Imagem;
	public File file;
	///
	Login login;

	public Adicionar(boolean Gerente) {
		this.setLayout(new FlowLayout());

		// S� quem acessa � o gerente
		if (Gerente) {
			setLayout(new BorderLayout());
			campo = new JTextField[4];
			palavras = new JLabel[4];
			paineis = new JPanel[4];
			Adicionar = new JButton("        Adicionar        ");
			Adicionar.setFont(new Font("TimesRoman",Font.BOLD,17));
			Box esquerda = Box.createVerticalBox();
			esquerda.add(Box.createVerticalStrut(30));
			for (int i = 0; i < 4; i++) {
				palavras[i] = new JLabel(strings[i]);
				paineis[i] = new JPanel();
				campo[i] = new JTextField(10);
				paineis[i].add(campo[i], BorderLayout.NORTH);
				paineis[i].add(palavras[i], BorderLayout.WEST);
				esquerda.add(paineis[i]);
				esquerda.add(Box.createVerticalStrut(20));
			}
			Imagem = new JButton("Procurar Imagem");
			Imagem.setFont(new Font("AmericanTypewriter",Font.ITALIC,15));
			Imagem.addActionListener(new ProcurarImagem());
			ImgEscolhida = new JLabel();
			esquerda.add(Imagem);
			esquerda.add(Box.createVerticalStrut(60));
			esquerda.add(ImgEscolhida);
			esquerda.add(Box.createVerticalStrut(250));
			this.add(esquerda, BorderLayout.WEST);
			JTextArea textoInformativo = new JTextArea();
			textoInformativo.setEditable(false);
			textoInformativo.setColumns(15);
			textoInformativo.setRows(3);
			textoInformativo.setSelectionColor(this.getBackground());
			textoInformativo.setBackground(this.getBackground());
			textoInformativo.setFont(new Font("TimesRoman",Font.ITALIC,15));
			textoInformativo.setText("\n\nSomente ser�o aceitas imagens            "
					+ "\ncom o formato de 50x50 pixels     "
					+ "\n\nProdutos com c�digos iguais"
					+ "\nser�o substitu�dos       "
					+ "\n\nLimite m�ximo de produtos s�o 9 "
					+ "\nacima disso ser� desconsiderado ");
			
			this.add(textoInformativo,BorderLayout.EAST);
			Box OrganizarBotaoAdd = Box.createHorizontalBox();
			OrganizarBotaoAdd.add(Box.createHorizontalStrut(220));
			OrganizarBotaoAdd.add(Adicionar);
			this.add(OrganizarBotaoAdd, BorderLayout.SOUTH);
		
		
		
		}
		//

		else {
		
			login = new Login();
			this.add(login, BorderLayout.CENTER);
			
			
		}

	}

	public class ProcurarImagem implements ActionListener {

		public void actionPerformed(ActionEvent x) {
			JFileChooser arquivo = new JFileChooser();
			arquivo.setFileSelectionMode(arquivo.FILES_ONLY);
			arquivo.showOpenDialog(null);
			file = arquivo.getSelectedFile();
			try {
				if(new ImageIcon(file.getPath()).getIconHeight()!=50 || new ImageIcon(file.getPath()).getIconWidth()!=50){
					throw new Exception();
				}
				ImgEscolhida.setText("");
				ImgEscolhida.setIcon(new ImageIcon(file.getPath()));
			} catch (NullPointerException erro) {
				ImgEscolhida.setIcon(null);
				ImgEscolhida.setText("");
			}
			catch(Exception imagemerro) {
				JOptionPane.showMessageDialog(null, "A imagem selecionada não comporta as dimensões permitidas","Erro na imagem",JOptionPane.ERROR_MESSAGE);
			}

		}

	}

}