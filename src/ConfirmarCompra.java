import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Formatter;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ConfirmarCompra extends JFrame {

	public JTextField nome;
	public JTextField cpf;
	public JTextField cartao;
	public JPasswordField senha;
	public JButton Finalizar;
	public JButton Cancelar;

	public ConfirmarCompra() {

		super("Confirma��o");
		setSize(250, 250);
		setVisible(true);
		setResizable(false);
		setLayout(new BorderLayout());
		Box b = Box.createVerticalBox();
		nome = new JTextField(4);
		cpf = new JTextField(4);
		cartao = new JTextField(4);
		senha = new JPasswordField(4);
		Finalizar = new JButton("Finalizar Compra");	
		Finalizar.setFont(new Font("AmericanTypewriter",Font.ITALIC,12));
     	Cancelar = new JButton("Cancelar");
     	Cancelar.setFont(new Font("AmericanTypewriter",Font.ITALIC,12));
		Cancelar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent x) {
				dispose();
			}

		});

		Box Nome = Box.createHorizontalBox();
		Nome.add(Box.createHorizontalStrut(15));
		Nome.add(nome);
		Nome.add(Box.createHorizontalStrut(10));
		Nome.add(new JLabel("Nome     "));

		Box Cpf = Box.createHorizontalBox();
		Cpf.add(Box.createHorizontalStrut(15));
		Cpf.add(cpf);
		Cpf.add(Box.createHorizontalStrut(10));
		Cpf.add(new JLabel("CPF         "));

		Box Cartao = Box.createHorizontalBox();
		Cartao.add(Box.createHorizontalStrut(15));
		Cartao.add(cartao);
		Cartao.add(Box.createHorizontalStrut(10));
		Cartao.add(new JLabel("CD Cart�o           "));

		Box Senha = Box.createHorizontalBox();
		Senha.add(Box.createHorizontalStrut(15));
		Senha.add(senha);
		Senha.add(Box.createHorizontalStrut(10));
		Senha.add(new JLabel("Senha                                            "));

		Box Botoes = Box.createHorizontalBox();
		Botoes.add(Box.createHorizontalStrut(10));
		Botoes.add(Finalizar);
		Botoes.add(Box.createHorizontalStrut(10));
		Botoes.add(Cancelar);

		b.add(Box.createVerticalStrut(17));
		JTextArea texto = new JTextArea();
		texto.setEditable(false);
		texto.setSelectionColor(this.getBackground());
		texto.setText("   	Por favor insira seus\n	dados para continuar");
		texto.setFont(new Font("TimesRoman",Font.ITALIC,13));
		texto.setBackground(this.getBackground());
		b.add(texto);
		b.add(Box.createVerticalStrut(10));
		b.add(Nome);
		b.add(Box.createVerticalStrut(10));
		b.add(Cpf);
		b.add(Box.createVerticalStrut(10));
		b.add(Cartao);
		b.add(Box.createVerticalStrut(10));
		b.add(Senha);
		b.add(Box.createVerticalStrut(10));
		b.add(Botoes);
		b.add(Box.createVerticalStrut(10));
		add(b, BorderLayout.WEST);

	}

}
