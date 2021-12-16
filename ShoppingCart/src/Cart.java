import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Cart implements Serializable{
	private static final long serialVersionUID = 1L;
	private String email;
	private State state;
	HashMap<String, Integer> items;
	ArrayList<String> discounts;
	
	public Cart(String email, State state) {
		this.email = email;
		this.state = state;
		this.items = new HashMap<String, Integer>();
		this.discounts = new ArrayList<String>();
	}
	
	//TODO get total
	public void getSummary() {
		
	}
	
	//TODO caalculate total
	public void calculateTotal() {
		
	}
	
	//TODO sum discounts
	public void sumDiscounts() {
		
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
	
	//TODO remove item
	public void removeItem(String item) {
		items.remove(item);
	}
	
	//TODO change quantity
	public void changeQuantity(String item, int quantity) {
		
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
			if(dis.validItems.contains(item) && dis.validQuantities.contains(this.items.get(item))) {
				this.discounts.add(discount);
				return 200;
			}
		}
		
		return 404;
	}
	
	//TODO remove discount
	public void removeDiscounts(String discount) {
		
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
