import java.io.Serializable;

public class Item implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private double price;
	private String desc;
	//used to set up image processing https://www.geeksforgeeks.org/image-processing-in-java-read-and-write/
	private String imageURL;
	
	public Item(String name, double price, String desc, String image) {
		this.name = name;
		this.price = price;
		this.desc = desc;
		this.imageURL = image;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getPrice() {
		return Math.round(this.price * 100) / 100;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public String getImage() {
		return this.imageURL;
	}
}
