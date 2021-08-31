package cop2805;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.List;

public class Server {
	// constants
	private static final String CHROMOSOME_FILE = "chr1.fa";
	private static final int PORT = 5896;

	public static void main(String[] args) throws IOException {

		// create a searcher
		GenomeSearcher genomeSearcher = new GenomeSearcher(Paths.get(CHROMOSOME_FILE));

		// create server socket
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server running...");
		while (true) {
			
			try {
				// wait for client
				Socket clientSocket = serverSocket.accept();

				// open streams
				PrintWriter outputStream = new PrintWriter(clientSocket.getOutputStream());
				BufferedReader inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				// read gene
				String gene = inputStream.readLine();

				// search and send results
				List<Integer> indices = genomeSearcher.search(gene);
				for (Integer index : indices) {
					outputStream.println(index);
				}

				outputStream.close();
				inputStream.close();
				clientSocket.close();
			} catch (IOException e) { // if problem in accepting connections
				System.out.println(e.getMessage());
			}

		}

	}
}
