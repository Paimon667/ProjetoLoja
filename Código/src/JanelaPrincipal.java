import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
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
import com.sun.prism.Image;

public class JanelaPrincipal extends JFrame implements Serializable {

	private Map<Integer, Produto> produtoz;  // produtos que tem no catalogo
	private ArrayList<Produto> selecionados; // produtos que estão no carrinho do cliente
	private Catalogo catalogo;               // panel catalogo que exibe os produtos
	private Adicionar adicionar;             // panel adicionar para adicionar novos produtos
	private Carrinho carrinho;               // panel carrinho para ver os produtos escolhidos e seu preco final
	private RemoverEditar edit;              // panel para remover ou editar produtos
	private JTabbedPane abas;                // as abas para fixar os panels no frame principal (esse)
	private boolean AcessoGerente;           // boolean permitindo acesso a funções especiais (editar/remover/adicionar)
	private JMenu MenuPrincipal;             // um menu para adicionar as opções
	private JMenuItem salvar;                // opção de salvar no menu principal
	private JMenuItem apagar;                // opção de apagar no menu principal
	private JMenuItem ConectarDesconectar;   // opção de conectar ou desconectar da conta de gerente
	private JMenuBar menu;                   // menu principal
	private int posicao = -1;               // posição padrão para lançar uma exceção em um certo método
											 // se produto não for encontrado numa dada pesquisa essa posição é responsável por lançar uma exceção
	private boolean primeiraExec;            // boolean pra saber quando for a primeira execução, necessário para o método A (atualizar) para evitar
											 // com que ele remova as páginas sem nem terem sido instanciadas

	public JanelaPrincipal() {

		super("Sistema digital da PruuChau");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(600, 600);
		this.setResizable(false);
		java.awt.Image iconePrincipal = Toolkit.getDefaultToolkit().getImage(getClass().getResource("logo.png")); 
		this.setIconImage(iconePrincipal); // adicionando icone ao Frame
		
		try {
			// tentando ler um arquivo chamado "save.ser" onde tem informações de possível salvamento de dados
			ObjectInputStream ler = new ObjectInputStream(new FileInputStream("Save.ser"));
			Save backup = (Save) ler.readObject();
			// se não houver erros na leitura o Frame será iniciado com  os atributos já dados pelo arquivo lido
			produtoz = backup.getProdutoz();
			selecionados = backup.getSelecionados();
			// reatribuindo as ações para oss botões dos produtos
			for (int i : produtoz.keySet()) {
				produtoz.get(i).getComprar().addActionListener(new AdicionarCarrinho());
			}
		} catch (Exception x) { // caso não haja arquivo para leitura ele zera os valores
			produtoz = new HashMap<Integer, Produto>();
			selecionados = new ArrayList<Produto>();
		} finally {
			// com ou sem leitura o programa termina o construtor instanciando as partes
			// principais do programa e atribuindo algumas ações simples
			abas = new JTabbedPane();
			AcessoGerente = false;
			MenuPrincipal = new JMenu("Opções");
			salvar = new JMenuItem("Salvar");
			apagar = new JMenuItem("Apagar");
			ConectarDesconectar = new JMenuItem("Conectar/Desconectar");
			//ação para o item desconectar do Menu
			ConectarDesconectar.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent x) {

					if (AcessoGerente) {
						AcessoGerente = false;
						JOptionPane.showMessageDialog(abas.getSelectedComponent(), "Desconectado com sucesso",
								"Desconetado", JOptionPane.INFORMATION_MESSAGE);
						A();
					} else if (AcessoGerente == false) {
						abas.setSelectedComponent(adicionar);
					}

				}

			});
			//adicionando os itens ao menu Opções
			MenuPrincipal.add(salvar);
			MenuPrincipal.add(apagar);
			MenuPrincipal.add(ConectarDesconectar);
			menu = new JMenuBar();
		// adicionando o menu opções ao menu principal
			menu.add(MenuPrincipal); 
			salvar.addActionListener(new BotoesMenu()); 
			apagar.addActionListener(new BotoesMenu());
			setJMenuBar(menu);
			primeiraExec = true;
			A(); // método de atualização dos panels, remove todos os componentes e os reinstancia sem perder os atributos
				 // quando algum item for modificado em qualquer lugar
			primeiraExec = false;
			this.add(abas);
		}
	}

	// recebe um produto e adiciona-o ao map de produtos do catalogo, sendo
	// atribuido como chave o seu código
	public void AdicionarProduto(Produto produto) {
		produtoz.put(produto.getCodigo(), produto);
	}

	// remove todas as abas
	// reinstancia todos os panels e adiciona-os as abas novamente
	// se for a primeira execucao ele não remove as abas pois elas nem foram
	// adicionadas
	// serve para atualizar o programa toda vez que algo importante é feito
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
							CriarNotaFiscal(c.getNome().getText(), c.getCpf().getText(), c.getCartao().getText(), c.getSenha().getText(), c,
									c.getEmail().getText());
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
		if (AcessoGerente) { // há panels que tem certo comportamento quando a variavel gerente for true
			adicionar.Adicionar.addActionListener(new AdicionarAdd());
			edit.procurar.addActionListener(new editar());
			edit.remover.addActionListener(new editar());
			edit.salvar.addActionListener(new editar());
		} else {
			adicionar.login.getSenha().addActionListener(new LoginGerente());
			edit.login.getSenha().addActionListener(new LoginGerente());
		}
		abas.addTab("Carrinho", carrinho);
		abas.addTab("Adicionar", adicionar);
		abas.addTab("Editar", edit);
		if (primeiraExec == false) {
			abas.setSelectedIndex(foco);
		}
	}

	// ação do botão Adicionar da aba adicionar
	// ao ser iniciado ele inicia uma serie de testes para checar os campos preenchidos na aba adicionar
	// qualquer exceção será tratada, cada qual com sua maneira, sejam erros numéricos, campos vazios ou imagem inválida
	public class AdicionarAdd implements ActionListener {

		public void actionPerformed(ActionEvent x) {
			if (produtoz.size() < 9) { // caso o limite de produtos não seja excedido, caso sim, ele só atualiza a página

				String nome = adicionar.campo[0].getText();
				int preco = 0;
				int codigo = 0;

				int problema = 0;      // para personalizar a solução abaixo

				// devolve a cor preta para as palavras caso tenha dado um erro anteriormente
				for (int i = 0; i < 3; i++) {
					adicionar.palavras[i].setForeground(Color.BLACK);
				}

				try {
					problema = 1;   // se o problema acontecer no preço, ele nem vai mudar a variavel problema pra 2
									// entao posso personalizar a solução
					preco = Integer.parseInt(adicionar.campo[1].getText());

					problema = 2;

					codigo = Integer.parseInt(adicionar.campo[2].getText());

					// faz as devidas checagens
					if (adicionar.ImgEscolhida.getIcon() == null || nome.length() == 0 || preco <= 0 || codigo <= 0) {
						throw new Exception();
					}
					JButton botao = new JButton(adicionar.ImgEscolhida.getIcon()); // coloca a imagem selecionada no botão
					botao.setToolTipText("Produto : "+codigo);					   // adiciona o código à dica
					botao.setBackground(new Color(238, 238, 238));                 // muda as cores para o fundo padrão do panel
					botao.setForeground(new Color(238, 238, 238));                 // para ficar mais natural possível
					
					botao.addActionListener(new AdicionarCarrinho());              // adiciona a ação ao botão, ele serve para adicionar ao carrinho o produto
					 
					// agora cria um produto e executa o método já explicado anteriormente, com as devidas informações
					AdicionarProduto(new Produto(nome, preco, codigo, botao, adicionar.file.getPath(),adicionar.campo[3].getText()));
					A(); // atualiza o sistema.

				} catch (NumberFormatException y) {
					if (problema == 1) {
						adicionar.palavras[1].setForeground(Color.RED);
						JOptionPane.showMessageDialog(adicionar, "Preço inválido", "Erro", JOptionPane.ERROR_MESSAGE);
					} else {
						adicionar.palavras[2].setForeground(Color.red);
						JOptionPane.showMessageDialog(adicionar, "Código inválido", "Erro", JOptionPane.ERROR_MESSAGE);
					}

				} catch (Exception erro) {
					if (nome.length() == 0) {
						adicionar.palavras[0].setForeground(Color.red);
						JOptionPane.showMessageDialog(adicionar, "Nome inválido", "Erro", JOptionPane.ERROR_MESSAGE);
					} else if (preco <= 0) {
						adicionar.palavras[1].setForeground(Color.red);
						JOptionPane.showMessageDialog(adicionar, "Preço inválido", "Erro", JOptionPane.ERROR_MESSAGE);
					} else if (codigo <= 0) {
						adicionar.palavras[2].setForeground(Color.red);
						JOptionPane.showMessageDialog(adicionar, "Código inválido", "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}

			} else {
				JOptionPane.showMessageDialog(adicionar, "Número máximo de produtos esgotado", "Limite de estoque",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// checa se os valores colocados nos campos de login e senha estãoo corretos e
	// altera a variavel acesso gerente para true caso sim
	public class LoginGerente implements ActionListener {

		public void actionPerformed(ActionEvent x) {

			String login = "azul";
			String senha = "123";

			if ((adicionar.login.getUsuario().getText().equals(login) && adicionar.login.getSenha().getText().equals(senha))
					|| edit.login.getUsuario().getText().equals(login) && edit.login.getSenha().getText().equals(senha)) {
				AcessoGerente = true;
				JOptionPane.showMessageDialog(abas.getSelectedComponent(), "Login efetuado com sucesso");
				A();
			} else {
				JOptionPane.showMessageDialog(abas.getSelectedComponent(), "Usuario ou senha invalido");
			}

		}
	}

// Se o botão pressionado for o de Pesquisar ele procura o produto na lista do catálogo, caso não encontre somente ignora, a variável posição então continua como era
// Se o botão pressionado for o de Editar ou Remover, caso a variável posição seja -1, no caso sem produto selecionado ou encontrado, ele lança uma exceção
// Se for remover ele simplesmente remove o produto daquela posição
// Se for editar ele checa qual dos campos fora preenchido, atualizando então as informações de acordo com isso
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
					if (posicao == -1) {
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
						if(edit.NovaInfo.getText().length() !=0) {
							produtoz.get(posicao).setInfo(edit.NovaInfo.getText());
						}
						produtoz.get(posicao).getTexto().setText(("R$: "+produtoz.get(posicao).getPreco()+
								"\n"+produtoz.get(posicao).getNome()+"\n"+produtoz.get(posicao).getInfo()));
						posicao = 404;
						A();

					}
				}
			} catch (NumberFormatException erro) {
				JOptionPane.showMessageDialog(edit,
						"O Valor inserido em algum dos campos numéricos\nfoi digitado incorretamente",
						"Caracteres inválidos", JOptionPane.ERROR_MESSAGE);
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
					// compara o código de cada produto do carrinho com a primeira String antes do primeiro espaço (onde está o código) na lista
					if (p.getCodigo()== Integer.parseInt(carrinho.produtoz[carrinho.lista.getSelectedIndex()].split(" ")[0])) {
						selecionados.remove(p);
						A();
						break;
					}
				}

			} catch (Exception erro) {
				JOptionPane.showMessageDialog(carrinho, "Ocorreu um erro ao remover o produto", "Produto Inválido",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}

// adiciona produtos do catalogo a lista de carrinho, mas somente se não tiver esgotado o limite de 20 produtos
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
						"Nossa loja permite até um máximo\nde 20 produtos por cliente! Obrigado.",
						"Limite máximo atingido", JOptionPane.INFORMATION_MESSAGE);
			}

		}
	}

// cria a nota fiscal de acordo com as informações fornecidas pelo Frame confirmarCompra
	public void CriarNotaFiscal(String nome, String cpf, String cartao, String senha, ConfirmarCompra c, String email) {

		int cpfI;
		int cartaoI;
		int senhaI;
		try {
			cpfI = Integer.parseInt(cpf);
			cartaoI = Integer.parseInt(cartao);
			senhaI = Integer.parseInt(senha);
			String nomeI = nome;

			boolean emailValido = false; // para testar email

			String[] testarEmail = { "hotmail.com", "gmail.com", "ufsc.br", "outlook.com", "hotmail.com.br" };

			// procura pelo @ no email, ao encontrar, checa se seu dominio está na lista dos
			// permitidos, caso sim
			// emailValido recebe true, evitando posterior erro
			for (int i = 0; i < email.length(); i++) {
				if (email.charAt(i) == '@') {
					for (String x : testarEmail) {
						if (x.equalsIgnoreCase(email.substring(i + 1, email.length()))) {
							emailValido = true;
							break;
						}
					}
				}
			}

			if (senhaI < 100) {
				throw new Exception("senha");
			}
			if (cartaoI < 10000) {
				throw new Exception("cartão");
			}
			if (cpfI < 1000000000) {
				throw new Exception("CPF");
			}
			if (nome.length() < 3) {
				throw new Exception("nome");
			}
			if (emailValido == false) {
				throw new Exception("email");
			}

			// escolhe lugar para salvar a nota fiscal, indicado salvar numa pasta especifica de backup, algo assim
			JFileChooser EscolheDiretorio = new JFileChooser();
			EscolheDiretorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			EscolheDiretorio.setDialogTitle("Escolha o local para salvar sua Nota Fiscal");
			EscolheDiretorio.showSaveDialog(null);

			String diretorio = EscolheDiretorio.getSelectedFile().getPath();
			Formatter escrever = new Formatter(diretorio + "/NotaFiscal.txt");
			escrever.format("%s\n", "----------------------------LOJA PruuChau---------------------");
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
			escrever.format("%s\n", "PruuChau LTDA");
			escrever.format("%s\n", "--------------------------------------------------------------");
			escrever.format("%s\n", "Obrigado pela Preferencia");
			escrever.close();
			JOptionPane.showMessageDialog(abas.getSelectedComponent(), "Compra efetuada com sucesso");
			c.dispose();
			selecionados = new ArrayList<Produto>();
			A();

		} catch (NumberFormatException erro2) {
			JOptionPane.showMessageDialog(carrinho, "Valor numérico inválido inserido em algum dos campos", "Erro",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception erro) {
			String[] erros = { "senha", "nome", "cartão", "CPF", "email" };
			for (int i = 0; i < erros.length; i++) {
				if (erro.getMessage().equalsIgnoreCase(erros[i])) {
					JOptionPane.showMessageDialog(carrinho, "Valor inserido no campo de " + erros[i] + " inválido",
							"Erro", JOptionPane.ERROR_MESSAGE);
					break;
				}
			}

		}

	}
// ações do menu Salvar e Apagar
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
					JOptionPane.showMessageDialog(abas.getSelectedComponent(),
							"Você precisa estar logado para acessar esta função", "Acesso Negado",
							JOptionPane.ERROR_MESSAGE);
					abas.setSelectedComponent(adicionar);
				}

			}

			else {
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