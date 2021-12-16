import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Item implements Serializable{
	String name;
	double price;
	String desc;
	//used to set up image processing https://www.geeksforgeeks.org/image-processing-in-java-read-and-write/
	BufferedImage image;
	
	public Item(String name, double price, String desc, BufferedImage image) {
		this.name = name;
		this.price = price;
		this.desc = desc;
		this.image = image;
	}
	
	//TODO
	public boolean verify() {
		return false;
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
	
	public BufferedImage getImage() {
		return this.image;
	}
}
