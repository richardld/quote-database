
public class Quote {
	private String quote, genre, author;
	
	public Quote(String quote, String author, String genre) {
		this.quote = quote;
		this.genre = genre;
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public String getQuote() {
		return quote;
	}

	public String getGenre() {
		return genre;
	}
	
	public String toString() {
		return(quote+" - "+author);
	}


	
	
}
