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

	private JTextField nome;
	private JTextField cpf;
	private JTextField cartao;
	private JTextField email;
	private JPasswordField senha;
	public JButton Finalizar;
	public JButton Cancelar;
	

	public JTextField getNome() {
		return nome;
	}


	public void setNome(JTextField nome) {
		this.nome = nome;
	}


	public JTextField getCpf() {
		return cpf;
	}


	public void setCpf(JTextField cpf) {
		this.cpf = cpf;
	}


	public JTextField getCartao() {
		return cartao;
	}


	public void setCartao(JTextField cartao) {
		this.cartao = cartao;
	}


	public JTextField getEmail() {
		return email;
	}


	public void setEmail(JTextField email) {
		this.email = email;
	}


	public JPasswordField getSenha() {
		return senha;
	}


	public void setSenha(JPasswordField senha) {
		this.senha = senha;
	}


	public ConfirmarCompra() {

		super("Confirmação");
		setSize(250, 300);
		setVisible(true);
		setResizable(false);
		setLayout(new BorderLayout());
		Box b = Box.createVerticalBox();
		nome = new JTextField(4);
		cpf = new JTextField(4);
		cartao = new JTextField(4);
		email = new JTextField(6);
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
		Cartao.add(new JLabel("CD CartÃ£o           "));

		Box Senha = Box.createHorizontalBox();
		Senha.add(Box.createHorizontalStrut(15));
		Senha.add(senha);
		Senha.add(Box.createHorizontalStrut(10));
		Senha.add(new JLabel("Senha                                            "));

		Box email = Box.createHorizontalBox();
		email.add(Box.createHorizontalStrut(15));
		email.add(this.email);
		email.add(Box.createHorizontalStrut(10));
		email.add(new JLabel("Email             "));
		
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
		b.add(email);
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