import java.util.ArrayList;

public class Discount {
	private String discountCode;
	private double discountPercentage;
	private ArrayList<String> validItems;
	private ArrayList<Integer> validQuantities;
	
	public Discount(String dc, double dp, ArrayList<String> vi, ArrayList<Integer> vq) {
		this.discountCode = dc;
		this.discountPercentage = dp;
		this.validItems = vi;
		this.validQuantities = vq;
	}
	
	public String getDiscountCode() {
		return this.discountCode;
	}
	
	public double getDiscountPercentage() {
		return this.discountPercentage;
	}
	
	public ArrayList<String> getValidItems() {
		return this.validItems;
	}
	
	public ArrayList<Integer> getValidQuantities() {
		return this.validQuantities;
	}
}
