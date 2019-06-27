import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Principal {

	static JanelaPrincipal t;

	public static void main(String[] args) {
		t = new JanelaPrincipal();
		t.salvar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent x) {
				try {
					ObjectOutputStream salvar = new ObjectOutputStream(new FileOutputStream("deus.ser"));
					salvar.writeObject(t);
					salvar.close();
				} catch (Exception h) {

				}
			}

		});

		try {
			ObjectInputStream ler = new ObjectInputStream(new FileInputStream("deus.ser"));
			t.dispose();
			t = (JanelaPrincipal) ler.readObject();
			t.setVisible(true);
			t.salvar.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent x) {
					try {
						ObjectOutputStream salvar = new ObjectOutputStream(new FileOutputStream("deus.ser"));
						salvar.writeObject(t);
						salvar.close();
					} catch (Exception h) {

					}
				}
 
			});
			t.apagar.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent x) {
					t.dispose();
					t = new JanelaPrincipal();
					t.setVisible(true);
					t.R();
					t.A();
					t.salvar.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent x) {
							try {
								ObjectOutputStream salvar = new ObjectOutputStream(new FileOutputStream("deus.ser"));
								salvar.writeObject(t);
								salvar.close();
							} catch (Exception h) {

							}
						}

					});
					t.apagar.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent x) {
							t.dispose();
							t = new JanelaPrincipal();
							t.setVisible(true);
							t.R();
							t.A();
							t.salvar.addActionListener(new ActionListener() {

								public void actionPerformed(ActionEvent x) {
									try {
										ObjectOutputStream salvar = new ObjectOutputStream(
												new FileOutputStream("deus.ser"));
										salvar.writeObject(t);
										salvar.close();
									} catch (Exception h) {

									}
								}

							});

						}

					});
				}
			});
			t.R();
			t.A();
		} catch (Exception x) {
			t.setVisible(true);
			t.R();
			t.A();
		}

	}
}
