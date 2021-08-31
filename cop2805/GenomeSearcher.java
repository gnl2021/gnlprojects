package cop2805;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class GenomeSearcher {
	
	// the chromosome read from file
	private StringBuilder chromosome;

	public GenomeSearcher(Path file) throws IOException {
		// to read from file
		BufferedReader br = new BufferedReader(new FileReader(file.toFile()));
		
		// read the file and add to string builder
		chromosome = new StringBuilder();
		String line;
		while((line = br.readLine()) != null) {
			chromosome.append(line.toUpperCase());
		}
		
		br.close();
		
		
	}
	
	public List<Integer> search(String gene){
		gene = gene.toUpperCase();
		
		// find all the indices
		int index = chromosome.indexOf(gene);
		List<Integer> results = new LinkedList<>();
		while(index >= 0) {
			results.add(index);
			index = chromosome.indexOf(gene, index + 1);	
		}
		
		return results;
	}
	
	
	
	
	
}
