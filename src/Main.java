import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main {

	public static void main(String[] args) {

		try {

			ObjectInputStream ler = new ObjectInputStream(new FileInputStream("a.ser"));
			Teste t = (Teste) ler.readObject();
			t.setDefaultCloseOperation(t.EXIT_ON_CLOSE);
			t.setSize(600, 600);
			t.setVisible(true);
			t.item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent x) {
					if (x.getSource() == t.item) {
						try {
							ObjectOutputStream escrever = new ObjectOutputStream(new FileOutputStream("a.ser"));
							escrever.writeObject(t);
							escrever.close();
						} catch (Exception a) {

						}

					}
				}
			});
			t.apagar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent x) {
					if (x.getSource() == t.apagar) {
						t.dispose();
						Teste t = new Teste();
						try {
							ObjectOutputStream escrever = new ObjectOutputStream(new FileOutputStream("a.ser"));
							escrever.writeObject(t);
							escrever.close();
						} catch (Exception a) {

						}
					}
				}
			});
			t.R();
			t.A();
		}
		catch (Exception erro) {
			Teste t = new Teste();
			t.item.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent x) {

					try {

						ObjectOutputStream escrever = new ObjectOutputStream(new FileOutputStream("a.ser"));
						escrever.writeObject(t);
						escrever.close();

					} catch (Exception a) {

					}

				}

			});

		}

	}
}
