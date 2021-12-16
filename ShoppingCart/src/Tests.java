import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class Tests {
	
	private BufferedImage buffImg;
	private String email1 = "fake1@gmail.com"; 
	private State s1 = State.IL;
	private String email2 = "fake2@email.com";
	private State s2 = State.IN;
	
	
	@Before public void setUp() {
		File img = new File("ice_cream.jpg");
		try {
			buffImg = ImageIO.read(img);
		} catch(IOException e) {
			System.out.println("no img");	
		}
		this.buffImg = new BufferedImage(100,100,BufferedImage.TYPE_INT_ARGB);	
	}
	
	public void ResetDB() {
		Main.carts = new HashMap<String, Cart>();
		
		Main.itemDB = new HashMap<String, Item>();
		Main.itemDB.put("item1", new Item("item1", 5.00, "this is the first fake", buffImg));
		Main.itemDB.put("item2", new Item("item2", 8.00, "this is the second fake", buffImg));
		Main.itemDB.put("item3", new Item("item3", 20.00, "this is the third fake", buffImg));
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
		Main.handleAddItem(email1, s1, "item2");
		assertTrue(Main.carts.get(email1).getItems().size() == 2);
	}
	
	@Test
	void addExistingItemToCart() {
		ResetDB();
		Main.handleAddItem(email1, s1, "item1");
		Main.handleAddItem(email1, s1, "item2");
		assertTrue(Main.carts.get(email1).getItems().size() == 2);
	}
	
	@Test
	void addOutOfStockItem() {
		ResetDB();
		assertTrue(Main.handleAddItem(email1, s1, "item3") == 400);
	}
	
	@Test
	void addNonExistingItem() {
		ResetDB();
		assertTrue(Main.handleAddItem(email1, s1, "item4") == 404);
	}
	
	@Test
	void applyDiscountCode() {
		ResetDB();
		Main.handleAddItem(email1, s1, "item1");
		Main.handleAddItem(email1, s1, "item2");
		assertTrue(Main.handleApplyDiscount(email1, s1, "discount1") == 200);
	}
	
	@Test
	void applyInvalidDiscountCode(){
		ResetDB();
		Main.handleAddItem(email1, s1, "item1");
		Main.handleAddItem(email1, s1, "item2");
		assertTrue(Main.handleApplyDiscount(email1, s1, "discount3") == 404);
	}
	
	@Test
	void applyInapplicableDiscountCode() {
		ResetDB();
		Main.handleAddItem(email1, s1, "item1");
		Main.handleAddItem(email1, s1, "item2");
		assertTrue(Main.handleApplyDiscount(email1, s1, "discount3") == 404);
	}
	
	@Test
	void applyInUseDiscountCode() {
		ResetDB();
		Main.handleAddItem(email1, s1, "item1");
		Main.handleAddItem(email1, s1, "item2");
		Main.handleApplyDiscount(email1, s1, "discount1");
		assertTrue(Main.handleApplyDiscount(email1, s1, "discount1") == 400);
	}
	

}
