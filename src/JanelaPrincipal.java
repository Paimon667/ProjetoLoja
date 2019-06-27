import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class JanelaPrincipal extends JFrame implements Serializable {

	Map<Integer, Produto> produtoz; // produtos que tem no catalogo
	ArrayList<Produto> selecionados; // produtos que estao no carrinho do cliente
	Catalogo catalogo; // panel catalogo que exibe os produtos
	Adicionar adicionar; // panel adicionar para adicionar novos produtos
	Carrinho carrinho; // panel carrinho para ver os produtos escolhidos e seu preço final
	RemoverEditar edit; // panel para remover ou editar produtos
	JTabbedPane abas; // as abas para fixar os panels no frame principal (esse)
	boolean AcessoGerente; // boolean permitindo acesso à funções especiais (editar/remover/adicionar)
	JMenu MenuPrincipal; // um menu para adicionar as opções
	JMenuItem salvar; // opção de salvar no menu principal
	JMenuItem apagar; // opcao de apagar no menu principal
	JMenuBar menu; // menu principal
	int posicao = 404; // posição inicial é usada no procurar produto na aba editar para dar erro caso
						// não encontre o produto

	public JanelaPrincipal() {

		// instancio os objectos e coloco-os em seus devidos lugares

		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setSize(600, 600);
		this.setResizable(false);
		produtoz = new HashMap<Integer, Produto>();
		selecionados = new ArrayList<Produto>();
		abas = new JTabbedPane();
		AcessoGerente = false;
		MenuPrincipal = new JMenu("Arquivo");
		salvar = new JMenuItem("Salvar");
		apagar = new JMenuItem("Apagar");
		MenuPrincipal.add(salvar);
		MenuPrincipal.add(apagar);
		menu = new JMenuBar();
		menu.add(MenuPrincipal);
		setJMenuBar(menu);
		A();
		this.add(abas);
	}

	// recebe um produto e adiciona-o ao map de produtos do catalogo, sendo
	// atribuido como chave o seu código
	public void AdicionarProduto(Produto produto) {
		produtoz.put(produto.getCodigo(), produto);
	}

	// remove todas as abas
	public void R() {
		abas.remove(catalogo);
		abas.remove(adicionar);
		abas.remove(edit);
		abas.remove(carrinho);
	}

	// reinstancia todos os panels e adiciona-os às abas novamente
	public void A() {
		carrinho = new Carrinho(selecionados);
		carrinho.remover.addActionListener(new RemoverCarrinho());
		carrinho.comprar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent x) {

				ConfirmarCompra c = new ConfirmarCompra();
				c.Finalizar.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent y) {
						CriarNotaFiscal(c.nome.getText(), c.cpf.getText(), c.cartao.getText(), c.senha.getText(),c);
					}

				});

			}

		});
		catalogo = new Catalogo(produtoz);
		abas.addTab("Catalogo", catalogo);
		adicionar = new Adicionar(AcessoGerente);
		edit = new RemoverEditar(AcessoGerente);
		if (AcessoGerente) { // há panels que tem certo comportamento quando a variavel gerente é true
			adicionar.Adicionar.addActionListener(new AdicionarAdd());
			edit.procurar.addActionListener(new editar());
			edit.remover.addActionListener(new editar());
			edit.editarPreco.addActionListener(new editar());
			edit.editarNome.addActionListener(new editar());
		} else {
			adicionar.senha.addActionListener(new LoginGerente());
			edit.senha.addActionListener(new LoginGerente());
		}
		abas.addTab("Carrinho", carrinho);
		abas.addTab("Adicionar", adicionar);
		abas.addTab("Editar", edit);

	}

	// recebe todos os valores colocados nos campos de digitação da aba Adicionar e
	// cria um produto, caso algo seja preenchido incorretamente ele dá erro
	public class AdicionarAdd implements ActionListener {

		public void actionPerformed(ActionEvent x) {
			String nome = adicionar.campo[0].getText();
			try {
				if (adicionar.ImgEscolhida.getIcon() == null) {
					throw new Exception();
				}
				int preco = Integer.parseInt(adicionar.campo[1].getText());
				int codigo = Integer.parseInt(adicionar.campo[2].getText());
				JButton botao = new JButton(adicionar.ImgEscolhida.getIcon());
				botao.setToolTipText("Produto: " + codigo);
				botao.setBackground(new Color(238, 238, 238));
				botao.setForeground(new Color(238, 238, 238));
				botao.addActionListener(new AdicionarCarrinho());
				AdicionarProduto(new Produto(nome, preco, codigo, botao, adicionar.file.getPath()));
				int foco = abas.getSelectedIndex();
				R();
				A();
				abas.setSelectedIndex(foco);
			} catch (Exception erro) {
				JOptionPane.showMessageDialog(null, "Campos preenchidos incorretamente", null,
						JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	// checa se os valores colocados nos campos de login e senha estão corretos e
	// altera a variavel acesso gerente para true caso sim
	public class LoginGerente implements ActionListener {

		public void actionPerformed(ActionEvent x) {

			String login = "azul";
			String senha = "123";

			if ((adicionar.Usuario.getText().equals(login) && adicionar.senha.getText().equals(senha))
					|| edit.Usuario.getText().equals(login) && edit.senha.getText().equals(senha)) {
				AcessoGerente = true;
				JOptionPane.showMessageDialog(null, "Login efetuado com sucesso");
				int foco = abas.getSelectedIndex();
				R();
				A();
				abas.setSelectedIndex(foco);
			} else {
				JOptionPane.showMessageDialog(null, "Usuário ou senha inválido");
			}

		}
	}

	// lê o valor colocado no campo de pesquisa e procura o produto em questão, se
	// encontrar, exibirá seu valor, nome e imagem e será permitido alterar algo
	// dele
	// caso não encontre ele permanece com a posição 404 definida no inicio do
	// código para lançar uma exceção posteriormente caso tente alterar algo
	public class editar implements ActionListener {

		public void actionPerformed(ActionEvent x) {

			if (x.getSource() == edit.procurar) {

				if (produtoz.size() == 0) {
					JOptionPane.showMessageDialog(null, "Não existem produtos cadastrados");
				}

				for (Integer i : produtoz.keySet()) {
					if (produtoz.get(i).getCodigo() == Integer.parseInt(edit.codigo.getText())) {
						posicao = i;
						edit.imagem.setText("");
						edit.imagem.setIcon(new ImageIcon(produtoz.get(posicao).getImagem()));
						edit.pn.setText(
								produtoz.get(posicao).getNome() + "    R$: " + produtoz.get(posicao).getPreco());
						break;
					}
				}
			}

			else {
				try {
					if (posicao == 404) {
						throw new Exception();
					}
					if (x.getSource() == edit.remover) {
						produtoz.remove(posicao);
						posicao = 404;
						int foco = abas.getSelectedIndex();
						R();
						A();
						abas.setSelectedIndex(foco);
					} else if (x.getSource() == edit.editarNome) {
						String nome = JOptionPane.showInputDialog("Digite o novo nome");
						produtoz.get(posicao).setNome(nome);
						produtoz.get(posicao).texto.setText(
								produtoz.get(posicao).getNome() + "    R$: " + produtoz.get(posicao).getPreco());
						posicao = 404;
						int foco = abas.getSelectedIndex();
						R();
						A();
						abas.setSelectedIndex(foco);
					} else if (x.getSource() == edit.editarPreco) {
						int preco = Integer.parseInt(JOptionPane.showInputDialog("Digite o novo preço"));
						produtoz.get(posicao).setPreco(preco);
						produtoz.get(posicao).texto.setText(
								produtoz.get(posicao).getNome() + "    R$: " + produtoz.get(posicao).getPreco());
						posicao = 404;
						int foco = abas.getSelectedIndex();
						R();
						A();
						abas.setSelectedIndex(foco);
					}
				}

				catch (NumberFormatException erro) {
					JOptionPane.showMessageDialog(null, "Valor inválido inserido");
				}

				catch (Exception erro) {
					JOptionPane.showMessageDialog(null, "Nenhum produto selecionado", null, JOptionPane.ERROR_MESSAGE);
				}
			}

		}

	}

	// remove produtos da lista de produtos do carrinho
	public class RemoverCarrinho implements ActionListener {

		public void actionPerformed(ActionEvent x) {

			for (Produto p : selecionados) {

				if (p.getNome().equalsIgnoreCase(carrinho.produtoz[carrinho.lista.getSelectedIndex()].split(" ")[0])) {
					selecionados.remove(p);
					int foco = abas.getSelectedIndex();
					R();
					A();
					abas.setSelectedIndex(foco);
					break;
				}
			}

		}

	}

	// adiciona produtos do catalogo a lista de carrinho

	public class AdicionarCarrinho implements ActionListener {

		public void actionPerformed(ActionEvent x) {

			for (Integer i : produtoz.keySet()) {
				if (x.getSource() == produtoz.get(i).getComprar()) {
					selecionados.add(produtoz.get(i));
					int foco = abas.getSelectedIndex();
					R();
					A();
					abas.setSelectedIndex(foco);
					break;
				}
			}
		}

	}

	public void CriarNotaFiscal(String nome, String cpf, String cartao, String senha, ConfirmarCompra c) {

		int cpfI;
		int cartaoI;
		int senhaI;
		try {
			cpfI = Integer.parseInt(cpf);
			cartaoI = Integer.parseInt(cartao);
			senhaI = Integer.parseInt(senha);
			String nomeI = nome;
			//if(senhaI<100 || cartaoI < 100000 || cpfI<1000000000) { // se digitar senha com menos de 3 digitos ou cartao com menos de 6 ou cpf menos de 10
			//	throw new Exception();
			//}
			
			Formatter escrever = new Formatter("NotaFiscal.txt");
			escrever.format("%s\n", "                          NOME DA LOJA");
			escrever.format("%s\n%s\n%s\n", "Nome: " + nomeI, "Cartão: " + cartaoI, "CPF: " + cpfI);
			escrever.format("%s\n", "--------------------------------------------------------------");
			escrever.format("%s\n", "                            NOTA FISCAL");
			escrever.format("%s\n", "--------------------------------------------------------------");
			int contador=1;
			int precoI = 0;
			for(Produto p:selecionados) {
				escrever.format("%d %s\n",contador,"Cod: "+p.getCodigo()+" - - - - - - - - - - "+p.getNome());
				contador++;
				precoI += p.getPreco();
			}
			escrever.format("%s\n", "--------------------------------------------------------------");
			escrever.format("%s\n","                                            Total: R$"+precoI);
			escrever.format("%s\n", "--------------------------------------------------------------");
			escrever.format("%s\n","CNPJ: 985.154.584");
			escrever.format("%s\n", "NOME DA EMPRESA");
			escrever.format("%s\n", "--------------------------------------------------------------");
			escrever.format("%s\n",              "Obrigado pela Preferência");
			escrever.close();
			JOptionPane.showMessageDialog(null, "Compra efetuada com sucesso");
			c.dispose();
			selecionados = new ArrayList<Produto>();
			R();
			A();
			
			

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Campos preenchidos incorretamente");
		}

	}

}
