import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Cart implements Serializable{
	private static final long serialVersionUID = 1L;
	private String email;
	private State state;
	private HashMap<String, Integer> items;
	private ArrayList<String> discounts;
	
	public Cart(String email, State state) {
		this.email = email;
		this.state = state;
		this.items = new HashMap<String, Integer>();
		this.discounts = new ArrayList<String>();
	}
	
	public String getSummary() {
		StringBuilder sb = new StringBuilder();
		
		//reverify items
		for(String item : items.keySet()) {
			int quantity = items.get(item);
			
			//if item not in db, or not enough stock
			if(!Main.itemDB.containsKey(item) || Main.itemDBQuantities.get(item) < quantity) {
				items.remove(item);
			}
		}
		
		//reverify discounts
		for(String discount : discounts) {
			if(!Main.discountDB.containsKey(discount) || Main.discountDBValidities.get(discount) == false) {
				discounts.remove(discount);
			}
			//discount exists and is valid
			boolean isValid = false;
			Discount d = Main.discountDB.get(discount);
			
			//if it works for an item and quantity, it is valid
			for(String item : items.keySet()) {
				if(d.getValidItems().contains(item) && d.getValidQuantities().contains(this.items.get(item))) {
					isValid = true;
				}
			}
			if(!isValid) {
				discounts.remove(discount);
			}
		
		}
		
		//add items
		sb.append("Items: \n");
		for(String item : items.keySet()) {
			sb.append(item + " : " + items.get(item) + "\n");
		}
		
		//add sum of discounts
		ArrayList<Double> result = sumDiscountsAndTotal();
		double discount = result.get(0);
		double total = result.get(1);
		
		sb.append("Sum of discounts: " + discount + "\n");
		
		//add total after tax
		double newRate = total - discount;
		sb.append("Total cost after discount: " + 
					(newRate + Math.round(100* Tax.calculateTax(newRate, this.state)) / 100.0)) ;
		return sb.toString();
	}
	
	public ArrayList<Double> sumDiscountsAndTotal() {
		double total = 0;
		double discountSum = 0;
		
		//for each item, check if any discounts apply for that item and quantity
		for(String item : items.keySet()) {
			int quantity = items.get(item);
			
			for(String discount : discounts) {
				Discount d = Main.discountDB.get(discount);
				//if the item has a discount, add original to total and discounted to discount and break
				if(d.getValidItems().contains(item) &&
				   d.getValidQuantities().contains(quantity)) {
					discountSum +=  Main.itemDB.get(item).getPrice() * d.getDiscountPercentage() * quantity;
					break;
				}
			}
			//if no discount, add original totasl to both
			total += Main.itemDB.get(item).getPrice() * quantity;
		}
		ArrayList<Double> toReturn = new ArrayList<Double>();
		toReturn.add(discountSum);
		toReturn.add(total);
		return toReturn;
	}
	
	public int addItem(String item) {
		//check item exists
		if(!Main.itemDB.containsKey(item)) {
			return 404;
		}
		
		//determine desired quantity to check for. 1 if its not in the cart, or quantity + 1 if it is
		int desiredQuantity = 1;
		if(this.items.containsKey(item)) {
			desiredQuantity = this.items.get(item) + 1;
		}
		
		//check if enough of item quantity exists
		if(Main.itemDBQuantities.get(item) < desiredQuantity) {
			return 400;
		}
		
		//check if item is in cart, if it is then add one to the quantity. if it isnt then add one
		if(this.items.containsKey(item)) {
			int oldQuantity = items.get(item);
			items.put(item, oldQuantity + 1);
		} else {
			items.put(item, 1);
		}
		
		return 200;
	}
	
	public int changeQuantity(String item, int quantity) {
		//if item is not in the database
		if(!Main.itemDB.containsKey(item)) {
			return 404;
		}
		
		//if item is not in cart
		if(!this.items.containsKey(item)) {
			return 404;
		}
		
		//if quantity is negative
		if(quantity < 0) {
			return 400;
		}
		
		//if quantity is 0
		if(quantity == 0) {
			items.remove(item);
			return 200;
		}
		
		//if not enough in stock 
		if(Main.itemDBQuantities.get(item) < quantity) {
			return 400;
		}
		
		this.items.put(item, quantity);
		return 200;
	}
	
	public int getQuantity(String item) {
		if(!this.items.containsKey(item)) {
			return -1;
		}
		return this.items.get(item);
	}
	
	public int applyDiscount(String discount) {
		
		//check discount code exists
		if(!Main.discountDB.containsKey(discount)) {
			return 404;
		}
		
		//check discount code is valid
		if(!Main.discountDBValidities.get(discount)) {
			return 404;
		}
		
	
		//check if discount code is already in cart
		if(this.discounts.contains(discount)) {
			return 400;
		}
		
		Discount dis = Main.discountDB.get(discount);
		//check discount code applies, else return 404
		//for each item in the cart, check if it applies to the discount code
		//if it does, check that its quantity applies
		for(String item : this.items.keySet()) {
			if(dis.getValidItems().contains(item) && dis.getValidQuantities().contains(this.items.get(item))) {
				this.discounts.add(discount);
				return 200;
			}
		}
		
		return 404;
	}
	
	public Set<String> getItems() {
		return this.items.keySet();
	}
	
	public ArrayList<String> getDiscounts() {
		return this.discounts;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public State getState() {
		return this.state;
	}
}
