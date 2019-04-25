
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String temp = "Age is an issue of mind over matter. If you don't mind, it doesn't matter.;Mark Twain;age,,,,,,,,,,"; 
		String[] arr = temp.split(";");
		arr[2] = arr[2].replaceAll(",+", "");
		System.out.println(arr[2]);
	}

}
