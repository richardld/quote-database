import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class QuoteDB {
	HashMap<String,ArrayList<Quote>> authors = new HashMap<>();
	HashMap<String,ArrayList<Quote>> genres = new HashMap<>();
	
	
	public QuoteDB(){
		Scanner scan= new Scanner(this.getClass().getResourceAsStream("asd.txt"));
		
		
			
			
			while(scan.hasNextLine()) {
				String temp = scan.nextLine();
				String[] arr = temp.split(";");
				arr[2] = arr[2].replaceAll(",+", "");
				//arr[1] = arr[1].toLowerCase();
				arr[2] = arr[2].toLowerCase();
				Quote q = new Quote(arr[0], arr[1], arr[2]);
				if(!authors.containsKey(arr[1].toLowerCase())) {
					ArrayList<Quote> t = new ArrayList<>();
					t.add(q);
					authors.put(arr[1].toLowerCase(), t);
				} else {
					authors.get(arr[1].toLowerCase()).add(q);

				}
				
				if(!genres.containsKey(arr[2])) {
					ArrayList<Quote> t = new ArrayList<>();
					t.add(q);
					genres.put(arr[2], t);
				} else {
					genres.get(arr[2]).add(q);
				}
				
			}
		
		scan.close();
		
		
		
		
	}
	
	
	public ArrayList<Quote> getAllGenres(String g) {
		g = g.toLowerCase();
		if(genres.containsKey(g) ) {
			return genres.get(g);
		} else {
			return new ArrayList<Quote>();
		}
	}
	public ArrayList<Quote> getAllAuthors(String g) {
		g = g.toLowerCase();
		if(authors.containsKey(g) ) {
			return authors.get(g);
		} else {
			return new ArrayList<Quote>();
		}
	}
	
	public ArrayList<Quote> thisWillWork(String g) {
		SimilarityStrategy strategy = new JaroStrategy();
		String target = g;
		String source = "";
		StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
		
		Map<Double, Quote> map = new TreeMap<Double, Quote>();

		for(ArrayList<Quote> value : authors.values()) {
			for(Quote q : value) {
				source = q.toString();
				double score = service.score(source, target);
				map.put(1/score, q);
			}
		}
		
		ArrayList<Quote> ret = new ArrayList<Quote>();
		
		int i = 0;
		
		for(Quote q : map.values()) {
			if(i < 1000) {
				ret.add(q);
				i++;
			} else {
				break;
			}
		}
		
		return ret;
	}
	
	public Quote getRandom() {
		ArrayList<String> keys = new ArrayList<>(authors.keySet());
		ArrayList<Quote> temp = authors.get(keys.get((int) (Math.random()*keys.size())));
		return temp.get((int) (Math.random()*temp.size()));
		
	}
	
	
	
}
