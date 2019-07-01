import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
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
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
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

import com.sun.glass.events.WindowEvent;

public class JanelaPrincipal extends JFrame implements Serializable {

	Map<Integer, Produto> produtoz; // produtos que tem no catalogo
	ArrayList<Produto> selecionados; // produtos que estao no carrinho do cliente
	Catalogo catalogo; // panel catalogo que exibe os produtos
	Adicionar adicionar; // panel adicionar para adicionar novos produtos
	Carrinho carrinho; // panel carrinho para ver os produtos escolhidos e seu preco final
	RemoverEditar edit; // panel para remover ou editar produtos
	JTabbedPane abas; // as abas para fixar os panels no frame principal (esse)
	boolean AcessoGerente; // boolean permitindo acesso a funcoes especiais (editar/remover/adicionar)
	JMenu MenuPrincipal; // um menu para adicionar as opicoes
	JMenuItem salvar; // opcao de salvar no menu principal
	JMenuItem apagar; // opcao de apagar no menu principal
	JMenuBar menu; // menu principal
	int posicao = 404; // posi��o inicial � usada no procurar produto na aba editar para dar erro
						// caso
						// n�o encontre o produto
	boolean primeiraExec;

	public JanelaPrincipal() {

		// instancio os objectos e coloco-os em seus devidos lugares
		super("Sistema digital da P.O.S");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(600, 600);
		this.setResizable(false);
		try {
			ObjectInputStream ler = new ObjectInputStream(new FileInputStream("Save.ser"));
			Save backup = (Save) ler.readObject();
			produtoz = backup.produtoz;
			selecionados = backup.selecionados;
			for (int i : produtoz.keySet()) {
				produtoz.get(i).getComprar().addActionListener(new AdicionarCarrinho());
			}
			// os botoes de comprar estaavm perdendo a��es ao serem lidos, ai readicionamos
			// quando � lido
		} catch (Exception x) {
			produtoz = new HashMap<Integer, Produto>();
			selecionados = new ArrayList<Produto>();
		} finally {
			abas = new JTabbedPane();
			AcessoGerente = false;
			MenuPrincipal = new JMenu("Op��es");
			salvar = new JMenuItem("Salvar");
			apagar = new JMenuItem("Apagar");
			MenuPrincipal.add(salvar);
			MenuPrincipal.add(apagar);
			menu = new JMenuBar();
			menu.add(MenuPrincipal);
			salvar.addActionListener(new BotoesMenu());
			apagar.addActionListener(new BotoesMenu());
			setJMenuBar(menu);
			primeiraExec = true;
			A();
			primeiraExec = false;
			this.add(abas);
		}
	}

	// recebe um produto e adiciona-o ao map de produtos do catalogo, sendo
	// atribuido como chave o seu c�digo
	public void AdicionarProduto(Produto produto) {
		produtoz.put(produto.getCodigo(), produto);
	}

	// remove todas as abas
	// reinstancia todos os panels e adiciona-os as abas novamente
	// se for a primeira execucao ele n�o remove as abas pois elas nem foram
	// adicionadas
	public void A() {
		int foco = abas.getSelectedIndex();
		if (primeiraExec == false) {
			abas.remove(catalogo);
			abas.remove(adicionar);
			abas.remove(edit);
			abas.remove(carrinho);
		}
		carrinho = new Carrinho(selecionados);
		carrinho.remover.addActionListener(new RemoverCarrinho());
		carrinho.comprar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent x) {

				if (selecionados.size() != 0) {
					ConfirmarCompra c = new ConfirmarCompra();
					c.Finalizar.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent y) {
							CriarNotaFiscal(c.nome.getText(), c.cpf.getText(), c.cartao.getText(), c.senha.getText(),
									c);
						}

					});

				} else {
					JOptionPane.showMessageDialog(carrinho,
							"Por favor certifique-se de adicionar produtos no carrinho"
									+ "\n                   antes de efetuar a compra",
							"Nenhum produto no carrinho", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		catalogo = new Catalogo(produtoz);
		abas.addTab("Catalogo", catalogo);
		adicionar = new Adicionar(AcessoGerente);
		edit = new RemoverEditar(AcessoGerente);
		if (AcessoGerente) { // h� panels que tem certo comportamento quando a variavel gerente � true
			adicionar.Adicionar.addActionListener(new AdicionarAdd());
			edit.procurar.addActionListener(new editar());
			edit.remover.addActionListener(new editar());
			edit.salvar.addActionListener(new editar());
		} else {
			adicionar.login.senha.addActionListener(new LoginGerente());
			edit.login.senha.addActionListener(new LoginGerente());
		}
		abas.addTab("Carrinho", carrinho);
		abas.addTab("Adicionar", adicionar);
		abas.addTab("Editar", edit);
		if (primeiraExec == false) {
			abas.setSelectedIndex(foco);
		}
	}

	// recebe todos os valores colocados nos campos de digita��o da aba
	// Adicionar e
	// cria um produto, caso algo seja preenchido incorretamente ele d� erro
	public class AdicionarAdd implements ActionListener {

		public void actionPerformed(ActionEvent x) {
			if (produtoz.size() < 9) {
				String nome = adicionar.campo[0].getText();
				for (int i = 0; i < 3; i++) {
					adicionar.palavras[i].setForeground(Color.BLACK);
				}
				try {
					if (adicionar.ImgEscolhida.getIcon() == null || nome.length() < 3
							|| adicionar.campo[1].getText().length() == 0 || adicionar.campo[2].getText().length() == 0
							|| adicionar.ImgEscolhida.getIcon().getIconHeight() != 50
							|| adicionar.ImgEscolhida.getIcon().getIconWidth() != 50) {
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
					A();
				} catch (NumberFormatException y) {
					JOptionPane.showMessageDialog(adicionar,
							"Algum dos valores correspondentes ao\npreco ou codigo foram preenchidos\nincorretamente",
							"Valor inv�lido", JOptionPane.ERROR_MESSAGE);
				} catch (Exception erro) {
					if (adicionar.ImgEscolhida.getIcon().getIconHeight() != 50
							|| adicionar.ImgEscolhida.getIcon().getIconWidth() != 50) {
						JOptionPane.showMessageDialog(adicionar,
								"A imagem nao comporta o tamanho especificado nas restri��es", "Imagem Incomp�tivel",
								JOptionPane.ERROR_MESSAGE);
						adicionar.ImgEscolhida.setIcon(null);
					} else {
						JOptionPane.showMessageDialog(adicionar, "Favor insira um valor nos campos em branco",
								"Campos sem valores", JOptionPane.ERROR_MESSAGE);
						if (nome.length() < 3) {
							adicionar.palavras[0].setForeground(Color.red);
						}
						if (adicionar.campo[1].getText().length() == 0) {
							adicionar.palavras[1].setForeground(Color.red);
						}
						if (adicionar.campo[2].getText().length() == 0) {
							adicionar.palavras[2].setForeground(Color.red);
						}
					}
				}

			} else {
				A();
			}
		}
	}

	// checa se os valores colocados nos campos de login e senha est�o corretos e
	// altera a variavel acesso gerente para true caso sim
	public class LoginGerente implements ActionListener {

		public void actionPerformed(ActionEvent x) {

			String login = "azul";
			String senha = "123";

			if ((adicionar.login.Usuario.getText().equals(login) && adicionar.login.senha.getText().equals(senha))
					|| edit.login.Usuario.getText().equals(login) && edit.login.senha.getText().equals(senha)) {
				AcessoGerente = true;
				JOptionPane.showMessageDialog(abas.getSelectedComponent(), "Login efetuado com sucesso");
				A();
			} else {
				JOptionPane.showMessageDialog(abas.getSelectedComponent(), "Usuario ou senha invalido");
			}

		}
	}

	// l� o valor colocado no campo de pesquisa e procura o produto em quest�o,
	// se
	// encontrar, exibir� seu valor, nome e imagem e ser� permitido alterar algo
	// dele
	// caso n�o encontre ele permanece com a posi��o 404 definida no inicio do
	// c�digo para lan�ar uma exce��o posteriormente caso tente alterar algo
	public class editar implements ActionListener {

		public void actionPerformed(ActionEvent x) {

			try {
				if (x.getSource() == edit.procurar) {
					for (int i : produtoz.keySet()) {
						if (produtoz.get(i).getCodigo() == Integer.parseInt(edit.codigo.getText())) {
							posicao = i;
							edit.imagem.setIcon(produtoz.get(i).getComprar().getIcon());
							edit.imagem
									.setToolTipText(produtoz.get(i).getNome() + " R$: " + produtoz.get(i).getPreco());
							break;
						}
					}

				} else {
					if (posicao == 404) {
						throw new Exception();
					}
					if (x.getSource() == edit.remover) {
						produtoz.remove(posicao);
						A();
					} else {
						if (edit.NovoPreco.getText().length() > 0) {
							produtoz.get(posicao).setPreco(Integer.parseInt(edit.NovoPreco.getText()));
						}
						if (edit.NovoNome.getText().length() != 0) {
							produtoz.get(posicao).setNome(edit.NovoNome.getText());
						}
						produtoz.get(posicao).texto.setText(
								produtoz.get(posicao).getNome() + "    R$: " + produtoz.get(posicao).getPreco());
						posicao = 404;
						A();

					}
				}
			} catch (NumberFormatException erro) {
				JOptionPane.showMessageDialog(edit,
						"O Valor inserido em algum dos campos n�mericos\nfoi digitado incorretamente",
						"Car�teres inv�lidos", JOptionPane.ERROR_MESSAGE);
			} catch (Exception erro2) {
				JOptionPane.showMessageDialog(edit, "Nenhum produto foi selecionado", "Sem produto selecionado",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	// remove produtos da lista de produtos do carrinho
	public class RemoverCarrinho implements ActionListener {

		public void actionPerformed(ActionEvent x) {

			try {
				for (Produto p : selecionados) {

					if (p.getNome()
							.equalsIgnoreCase(carrinho.produtoz[carrinho.lista.getSelectedIndex()].split(" ")[0])) {
						selecionados.remove(p);
						A();
						break;
					}
				}

			} catch (Exception erro) {
				JOptionPane.showMessageDialog(carrinho, "Ocorreu um erro ao remover o produto", "Produto Inv�lido",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	// adiciona produtos do catalogo a lista de carrinho

	public class AdicionarCarrinho implements ActionListener {

		public void actionPerformed(ActionEvent x) {

			if (selecionados.size() < 20) {
				for (Integer i : produtoz.keySet()) {
					if (x.getSource() == produtoz.get(i).getComprar()) {
						selecionados.add(produtoz.get(i));
						A();
						break;
					}
				}
			} else {
				JOptionPane.showMessageDialog(catalogo,
						"Nossa loja permite at� um m�ximo\nde 20 produtos por cliente! Obrigado.",
						"Limite m�ximo atingido", JOptionPane.INFORMATION_MESSAGE);
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
			if (senhaI < 100 || cartaoI < 100000 || cpfI < 1000000000 || nome.length() < 3) { // se digitar senha
				// diferente de 3 digitos ou cartao com menos de 6 ou cpf menos de 10
				throw new Exception();
			}
			JFileChooser EscolheDiretorio = new JFileChooser();
			EscolheDiretorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			EscolheDiretorio.setDialogTitle("Escolha o local para salvar sua Nota Fiscal");
			EscolheDiretorio.showSaveDialog(null);

			String diretorio = EscolheDiretorio.getSelectedFile().getPath();
			Formatter escrever = new Formatter(diretorio + "/NotaFiscal.txt");
			escrever.format("%s\n", "----------------------------LOJA P.O.S------------------------");
			escrever.format("%s\n%s\n%s\n", "Nome: " + nomeI, "Cartao: " + cartaoI, "CPF: " + cpfI);
			escrever.format("%s\n", "--------------------------------------------------------------");
			escrever.format("%s\n", "                            NOTA FISCAL");
			escrever.format("%s\n", "--------------------------------------------------------------");
			int contador = 1;
			int precoI = 0;
			for (Produto p : selecionados) {
				escrever.format("%d %s\n", contador, "Cod: " + p.getCodigo() + " - - - - - - - - - - " + p.getNome());
				contador++;
				precoI += p.getPreco();
			}
			escrever.format("%s\n", "--------------------------------------------------------------");
			escrever.format("%s\n", "                                            Total: R$" + precoI);
			escrever.format("%s\n", "--------------------------------------------------------------");
			escrever.format("%s\n", "CNPJ: 985.154.584");
			escrever.format("%s\n", "P.O.S LTDA");
			escrever.format("%s\n", "--------------------------------------------------------------");
			escrever.format("%s\n", "Obrigado pela Preferencia");
			escrever.close();
			JOptionPane.showMessageDialog(abas.getSelectedComponent(), "Compra efetuada com sucesso");
			c.dispose();
			selecionados = new ArrayList<Produto>();
			A();

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(abas.getSelectedComponent(), "Campos preenchidos incorretamente");
		}

	}

	public class BotoesMenu implements ActionListener {

		public void actionPerformed(ActionEvent x) {

			if (x.getSource() == apagar) {
				if (AcessoGerente) {
					produtoz = new HashMap<Integer, Produto>();
					selecionados = new ArrayList<Produto>();
					try {
						ObjectOutputStream escrever = new ObjectOutputStream(new FileOutputStream("Save.ser"));
						escrever.writeObject(new Save(produtoz, selecionados));
						escrever.close();
					} catch (Exception y) {

					}
					A();
				} else {
					JOptionPane.showMessageDialog(null, "Voc� precisa estar logado para acessar esta fun��o",
							"Acesso Negado", JOptionPane.ERROR_MESSAGE);
					abas.setSelectedComponent(adicionar);
				}

			}

			else  {
				try {
					ObjectOutputStream escrever = new ObjectOutputStream(new FileOutputStream("Save.ser"));
					escrever.writeObject(new Save(produtoz, selecionados));
					escrever.close();
				} catch (Exception y) {

				}
			}

		}

	}
}
