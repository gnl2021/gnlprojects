package cop2805;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Client extends JFrame {

	// constants
	private final int SERVER_PORT = 5896;

	// GUI components
	private JTextField txtGene;
	private JList<Integer> lstIndices;
	private JLabel lblTotalResults;
	private JButton btnTransmit;

	public Client() {
		super("Client GUI");

		// north panel
		JPanel pnlNorth = new JPanel();
		pnlNorth.add(new JLabel("Enter the gene sequence here: "));
		txtGene = new JTextField(40);
		pnlNorth.add(txtGene);
		btnTransmit = new JButton("Transmit");
		pnlNorth.add(btnTransmit);
		add(pnlNorth, BorderLayout.NORTH);

		// center panel
		JPanel pnlCenter = new JPanel(new BorderLayout());
		lstIndices = new JList<>();
		pnlCenter.add(new JScrollPane(lstIndices));
		add(pnlCenter, BorderLayout.CENTER);

		// bottom panel
		JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
		lblTotalResults = new JLabel("Total results: 0");
		pnlBottom.add(lblTotalResults);
		add(pnlBottom, BorderLayout.SOUTH);

		// transmit action listener
		btnTransmit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String gene = txtGene.getText().trim();
				try {
					// create a socket
					Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), SERVER_PORT);

					// open streams
					PrintWriter outputStream = new PrintWriter(socket.getOutputStream());
					BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					outputStream.flush();

					// send gene
					outputStream.println(gene);
					outputStream.flush();

					// search and display results
					String index;
					Vector<Integer> vector = new Vector<>();
					while ((index = inputStream.readLine()) != null) {
						vector.add(Integer.parseInt(index));
					}

					lstIndices.setListData(vector);
					lblTotalResults.setText("Total Results: " + vector.size());
					

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

	}

	public static void main(String[] args) {
		Client client = new Client();
		client.pack();
		client.setLocationRelativeTo(null);
		client.setDefaultCloseOperation(EXIT_ON_CLOSE);
		client.setVisible(true);
	}

}
