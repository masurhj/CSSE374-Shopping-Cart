import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {
	static HashMap<String, Cart> carts = new HashMap<String, Cart>();
	static String cartDBPath = "CartDB.txt";
	//fake dbs for testing
	static HashMap<String, Item> itemDB = new HashMap<String,Item>();
	static HashMap<String, Integer> itemDBQuantities = new HashMap<String,Integer>();
	static HashMap<String, Discount> discountDB = new HashMap<String,Discount>();
	static HashMap<String, Boolean> discountDBValidities = new HashMap<String,Boolean>();

	public static int handleAddItem(String email, State state, String item) {
		//get cart or create new one
		Cart c = getCart(email, state);
		//add to cart
		int returnCode = c.addItem(item);
		
		if(returnCode != 200) {
			return returnCode;
		}
		
		carts.put(email, c);
		UpdateDB();
		ReloadDB();
		return returnCode;
		
	}
	
	public static Cart getCart(String email, State state) {
		//if cart exists, grab it
		if(carts.containsKey(email)) {
			System.out.println("Existing cart");
			return carts.get(email);
		}
		//else make a new one
		System.out.println("new cart");
		Cart newCart = new Cart(email, state);
		carts.put(email, newCart);
		UpdateDB();
		ReloadDB();
		return newCart;
	}
	
	//TODO get summary
	public void handleGetSummary() {
		
	}
	
	//TODO apply discount
	public static int handleApplyDiscount(String email, State state, String discount) {
		//get cart or create new one
		Cart c = getCart(email, state);
		//add to cart
		int returnCode = c.applyDiscount(discount);
		
		if(returnCode != 200) {
			return returnCode;
		}
		
		carts.put(email, c);
		UpdateDB();
		ReloadDB();
		return returnCode;
	}
	
	//TODO change quantity
	public void handleChangeQuantity() {
		
	}
	
	//serialzing and storing/reading object aided by https://examples.javacodegeeks.com/core-java/io/fileoutputstream/how-to-write-an-object-to-file-in-java/
	public static void UpdateDB() {
		try {
			FileOutputStream fileOut = new FileOutputStream(cartDBPath);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(carts);
			objectOut.close();
			System.out.println("wrote object");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("failed to write");
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void ReloadDB() {
		try {
			FileInputStream fileIn = new FileInputStream(cartDBPath);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			carts = (HashMap<String, Cart>) objectIn.readObject();
			objectIn.close();
			System.out.println("read object");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("failed to write");
		}
	}

}
