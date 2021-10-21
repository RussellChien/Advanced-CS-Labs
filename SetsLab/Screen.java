import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Screen extends JPanel{
	private ArrayList<Pair<Item, Integer>> Cart;
	private Database db;
	private JTextArea itemTextArea;
	private JTextArea CartTextArea;
	private Color white;
	private Color black;

	public Screen(){
		Cart = new ArrayList<Pair<Item, Integer>>();
		itemTextArea  = new JTextArea(390, 250);
		CartTextArea = new JTextArea(390, 250);
		db = new Database("StoreA.txt");
		white = new Color(255, 255, 255);
		black = new Color(0, 0, 0);

		this.itemTextArea.setEditable(false);
		JScrollPane itemScrollPane = new JScrollPane(itemTextArea);
		itemScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		itemScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		itemScrollPane.setBounds(400, 50, 390, 350);
		this.add(itemScrollPane);
		this.populateItems();

		this.CartTextArea.setEditable(false);
		JScrollPane CartScrollPane = new JScrollPane(CartTextArea);
		CartScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		CartScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		CartScrollPane.setBounds(400, 460, 390, 350);
		this.add(CartScrollPane);
		this.populateCart();
		
		JTextField productNameTextField = new JTextField(20);
		productNameTextField.setBounds(10, 50, 250, 30);
		this.add(productNameTextField);

		JTextField priceTextField = new JTextField(20);
		priceTextField.setBounds(10, 150, 240, 30);
		this.add(priceTextField);

		JTextField quantityTextField = new JTextField(20);
		quantityTextField.setBounds(10, 250, 250, 30);
		this.add(quantityTextField);

		JButton addItemToCart = new JButton("Find and Add to Cart");
		addItemToCart.setBounds(10, 300, 200, 30);
		addItemToCart.addActionListener(e -> {

			Item i = new Item(productNameTextField.getText(), Double.parseDouble(priceTextField.getText()));
			db.addToCart(i, Integer.parseInt(quantityTextField.getText()));
			this.populateCart();
		});
		this.add(addItemToCart);

		JTextField productNameStockTextField = new JTextField(20);
		productNameStockTextField.setBounds(10, 450, 250, 30);
		this.add(productNameStockTextField);

		JTextField priceStockTextField = new JTextField(20);
		priceStockTextField.setBounds(10, 550, 250, 30);
		this.add(priceStockTextField);

		JButton addItem = new JButton("Add to Inventory");
		addItem.setBounds(10, 600, 175, 30);
		addItem.addActionListener(e -> {
			Item item = new Item(productNameStockTextField.getText(), Double.parseDouble(priceStockTextField.getText()));
			db.addToInventory(item);
			this.populateItems();

		});
		this.add(addItem);

		JButton removeItem = new JButton("Remove from Inventory");
		removeItem.setBounds(200, 600, 175, 30);
		removeItem.addActionListener(e -> {
			Item item = new Item(productNameStockTextField.getText(), Double.parseDouble(priceStockTextField.getText()));
			db.removeFromInventory(item);
			this.populateItems();

		});
		this.add(removeItem);
		
		this.setLayout(null);
		this.setFocusable(true);
	}

	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(black);
		g.setFont(new Font("Times Roman", Font.PLAIN, 18));
		g.drawString("Product", 15, 40);
		g.drawString("Price ($)", 15, 140);
		g.drawString("Quantity", 15, 240);
		g.drawString("Product", 15, 440);
		g.drawString("Price ($)", 15, 540);
		g.drawString("Inventory", 400, 40);
		g.drawString("Your Shopping Cart", 400, 440);

	}

	public void populateCart() {
		this.CartTextArea.setText(db.cartToString());
	}

	public void populateItems() {
		this.itemTextArea.setText(db.getAllItems());
	}
}