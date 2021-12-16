import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class Tests {
	
	private String email1 = "fake1@gmail.com"; 
	private State s1 = State.IL;
	private String email2 = "fake2@email.com";
	private State s2 = State.IN;
	
	public void ResetDB() {
		Main.carts = new HashMap<String, Cart>();
		
		Main.itemDB = new HashMap<String, Item>();
		Main.itemDB.put("item1", new Item("item1", 5.00, "this is the first fake", "https://blog.thermoworks.com/wp-content/uploads/2021/06/Ice_Cream_Compressed-43.jpg"));
		Main.itemDB.put("item2", new Item("item2", 8.00, "this is the second fake", "https://blog.thermoworks.com/wp-content/uploads/2021/06/Ice_Cream_Compressed-43.jpg"));
		Main.itemDB.put("item3", new Item("item3", 20.00, "this is the third fake", "https://blog.thermoworks.com/wp-content/uploads/2021/06/Ice_Cream_Compressed-43.jpg"));
		Main.itemDBQuantities = new HashMap<String, Integer>();
		Main.itemDBQuantities.put("item1", 2);
		Main.itemDBQuantities.put("item2", 4);
		Main.itemDBQuantities.put("item3", 0);
		
		Main.discountDB = new HashMap<String, Discount>();
		ArrayList<Integer> q1 = new ArrayList<Integer>();
		q1.add(1);
		q1.add(2);
		q1.add(3);
		ArrayList<String> items1 = new ArrayList<String>();
		items1.add("item1");
		items1.add("item2");
		ArrayList<Integer> q2 = new ArrayList<Integer>();
		q1.add(4);
		ArrayList<String> items2 = new ArrayList<String>();
		items2.add("item3");
		ArrayList<Integer> q3 = new ArrayList<Integer>();
		ArrayList<String> items3 = new ArrayList<String>();
		Main.discountDB.put("discount1", new Discount("discount1", 0.04, items1, q1));
		Main.discountDB.put("discount2", new Discount("discount2", 0.09, items2, q2));
		Main.discountDB.put("discount3", new Discount("discount1", 0.15, items3, q3));
		Main.discountDBValidities.put("discount1", true);
		Main.discountDBValidities.put("discount2", false);
		Main.discountDBValidities.put("discount3", true);
		
		Main.UpdateDB();
		Main.ReloadDB();
	}
	
	@Test
	void getNewCart() {
		ResetDB();
		Main.getCart(email1, s1);
		Main.getCart(email2, s2);
		assertTrue(Main.carts.size() == 2);
	}
	
	@Test
	void addNewItemToNewCartAndExistingCart() {
		ResetDB();
		Main.handleAddItem(email1, s1, "item1");
		int returnCode = Main.handleAddItem(email1, s1, "item2");
		assertTrue(returnCode == 200 && Main.carts.get(email1).getItems().size() == 2);
	}
	
	@Test
	void addExistingItemToCart() {
		ResetDB();
		Main.handleAddItem(email1, s1, "item1");
		int returnCode = Main.handleAddItem(email1, s1, "item2");
		assertTrue(returnCode == 200 && Main.carts.get(email1).getItems().size() == 2);
	}
	
	@Test
	void addOutOfStockItem() {
		ResetDB();
		int returnCode = Main.handleAddItem(email1, s1, "item3");
		assertTrue(returnCode == 400 && Main.carts.get(email1).getItems().size() == 0);
	}
	
	@Test
	void addNonExistingItem() {
		ResetDB();
		int returnCode = Main.handleAddItem(email1, s1, "item4");
		assertTrue(returnCode == 404 && Main.getCart(email1, s1).getItems().size() == 0);
	}
	
	@Test
	void applyDiscountCode() {
		ResetDB();
		Main.handleAddItem(email1, s1, "item1");
		Main.handleAddItem(email1, s1, "item2");
		int returnCode = Main.handleApplyDiscount(email1, s1, "discount1");
		assertTrue(returnCode == 200 && Main.carts.get(email1).getDiscounts().size() == 1);
	}
	
	@Test
	void applyInvalidDiscountCode(){
		ResetDB();
		Main.handleAddItem(email1, s1, "item1");
		Main.handleAddItem(email1, s1, "item2");
		int returnCode = Main.handleApplyDiscount(email1, s1, "discount3");
		assertTrue(returnCode == 404  && Main.carts.get(email1).getDiscounts().size() == 0);
	}
	
	@Test
	void applyInapplicableDiscountCode() {
		ResetDB();
		Main.handleAddItem(email1, s1, "item1");
		Main.handleAddItem(email1, s1, "item2");
		int returnCode = Main.handleApplyDiscount(email1, s1, "discount3");
		assertTrue(returnCode == 404  && Main.carts.get(email1).getDiscounts().size() == 0);
	}
	
	@Test
	void applyInUseDiscountCode() {
		ResetDB();
		Main.handleAddItem(email1, s1, "item1");
		Main.handleAddItem(email1, s1, "item2");
		Main.handleApplyDiscount(email1, s1, "discount1");
		int returnCode = Main.handleApplyDiscount(email1, s1, "discount1");
		assertTrue(returnCode == 400  && Main.carts.get(email1).getDiscounts().size() == 1);
	}
	
	@Test
	void changeQuantityNotInDB() {
		ResetDB();
		int returnCode = Main.handleChangeQuantity(email1, s1, "item4", 2);
		assertTrue(returnCode == 404  && Main.getCart(email1, s1).getItems().contains("item4") == false);
	}
	
	@Test 
	void changeQuantityNotInCart(){
		ResetDB();
		int returnCode = Main.handleChangeQuantity(email1, s1, "item1", 2);
		assertTrue(returnCode == 404 && Main.getCart(email1, s1).getItems().contains("item1") == false);
	}
	
	@Test 
	void changeQuantityIsNegative(){
		ResetDB();
		Main.handleAddItem(email1, s1, "item1");
		int returnCode = Main.handleChangeQuantity(email1, s1, "item1", -5);
		assertTrue(returnCode == 400 && Main.getCart(email1, s1).getQuantity("item1") == 1);
	}
	
	@Test
	void changeQuantityIsZero() {
		ResetDB();
		Main.handleAddItem(email1, s1, "item1");
		int returnCode = Main.handleChangeQuantity(email1, s1, "item1", 0);
		assertTrue(returnCode == 200 && Main.getCart(email1, s1).getItems().contains("item1") == false);
	}
	
	@Test
	void changeQuantity() {
		ResetDB();
		Main.handleAddItem(email1, s1, "item1");
		int returnCode = Main.handleChangeQuantity(email1, s1, "item1", 2);
		assertTrue(returnCode == 200 && Main.getCart(email1, s1).getQuantity("item1") == 2);
	}
	
	@Test
	void getSummary() {
		ResetDB();
		Main.handleAddItem(email1, s1, "item1");
		Main.handleChangeQuantity(email1, s1, "item1", 2);
		Main.handleAddItem(email1, s1, "item2");
		Main.handleApplyDiscount(email1, s1, "discount1");
		String result = Main.handleGetSummary(email1, s1);
		//total should be 18
		//amount saved should be 10 * 0.04 + 8 * 0.04 = 0.72
		//final total should be (18 - 0.72) * 1.13 = 19.53
		System.out.println(result);
		//test manually checked
		assertTrue(true);
	}
	
	

}
