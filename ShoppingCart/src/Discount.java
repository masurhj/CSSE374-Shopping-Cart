import java.util.ArrayList;

public class Discount {
	String discountCode;
	double discountPercentage;
	ArrayList<String> validItems;
	ArrayList<Integer> validQuantities;
	
	public Discount(String dc, double dp, ArrayList<String> vi, ArrayList<Integer> vq) {
		this.discountCode = dc;
		this.discountPercentage = dp;
		this.validItems = vi;
		this.validQuantities = vq;
	}
	
	//TODO verify
	public void verify() {
		
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
