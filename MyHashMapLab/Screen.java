import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Screen extends JPanel implements ActionListener {

	private MyHashMap <Country, MyImage> countries;
	private JTextArea textArea;
	private JTextField searchCountry, imageURL, imageCaption, imageDate;
	private JButton search, back, next, prev, addImage;
	private JScrollPane scrollPane;
	private JLabel searchResult;
	private String currentImageURL = "", caption = "";
	private int activeImage = 0;
	private Country country;
	private FileWriter writer;
	private FileReader reader;
	private File f;

	public Screen() {
		this.setLayout(null);

		File f = new File("./saved/data.txt");

		countries = new MyHashMap<Country, MyImage>();

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 400, 600);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane);

		textArea = new JTextArea();
		textArea.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		textArea.setBackground(Color.white);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		searchCountry = new JTextField("Enter a country");
		searchCountry.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		searchCountry.setBounds(450, 50, 150, 30);
		this.add(searchCountry);

		imageURL = new JTextField("Image URL");
		imageURL.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		imageURL.setBounds(10, 460, 175, 30);
		this.add(imageURL);

		imageCaption = new JTextField("Caption");
		imageCaption.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		imageCaption.setBounds(10, 490, 175, 30);
		this.add(imageCaption);

		imageDate = new JTextField("mm/dd/yyyy");
		imageDate.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		imageDate.setBounds(10, 520, 175, 30);
		this.add(imageDate);

		search = new JButton("Search");
		search.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		search.addActionListener(this);
		search.setBounds(450, 100, 100, 30);
		this.add(search);

		back = new JButton("Back");
		back.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		back.addActionListener(this);
		back.setBounds(700, 10, 70, 30);
		back.setVisible(false);
		this.add(back);

		next = new JButton("Next");
		next.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		next.addActionListener(this);
		next.setBounds(401, 455, 70, 30);
		next.setVisible(false);
		this.add(next);

		prev = new JButton("Prev");
		prev.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		prev.addActionListener(this);
		prev.setBounds(329, 455, 70, 30);
		prev.setVisible(false);
		this.add(prev);

		addImage = new JButton("Add");
		addImage.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		addImage.addActionListener(this);
		addImage.setBounds(50, 550, 70, 30);
		addImage.setVisible(false);
		this.add(addImage);

		searchResult = new JLabel();
		searchResult.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		searchResult.setBounds(20, 20, 400, 50);
		this.add(searchResult);

		try {
			Scanner scan = new Scanner(new FileReader("countries.txt"));
			int size = 246;
			String[] line = new String[size];
            while (scan.hasNextLine()) {
               line = scan.nextLine().split(",");
               countries.put(new Country(line[0], line[1]), null);
            }
            Country c = null;
			String caption = "", date = "", url = "", lineString;
			int i = 0;
			Scanner scan2 = new Scanner(new FileReader("countries.txt"));
			while (scan2.hasNextLine()) {
				lineString = scan2.nextLine();
				if (i % 5 == 4) {
					if (countries.get(c).get(0) != null) {
						countries.put(c, null);
					} else {
						countries.get(c).set(0, null);
					}
				} else if (lineString.equals("")) {
					continue;
				} else if (i % 5 == 0) {
					line = lineString.split(",");
					c = new Country(line[0], line[1]);
				} else if (i % 5 == 1) {
					url = lineString;
				} else if (i % 5 == 2) {
					caption = lineString;
				} else if (i % 5 == 3) {
					date = lineString;
				}
				i++;
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		countries.get(new Country("cn", "China")).set(0, new MyImage("https://images.immediate.co.uk/production/volatile/sites/7/2016/07/GettyImages-481614053-484c86d.jpg", "The Great Wall", "06/09/2018"));
		countries.put(new Country("cn", "China"), new MyImage("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/Forbidden_City_Beijing_Shenwumen_Gate.JPG/1200px-Forbidden_City_Beijing_Shenwumen_Gate.JPG", "Forbbiden City", "02/28/2017"));
		countries.put(new Country("cn", "China"), new MyImage("https://upload.wikimedia.org/wikipedia/commons/thumb/d/df/Pudong_Shanghai_November_2017_panorama.jpg/800px-Pudong_Shanghai_November_2017_panorama.jpg", "Shanghai, China", "12/25/2020"));

		countries.get(new Country("jp", "Japan")).set(0, new MyImage("https://www.japan-guide.com/g18/6933_01.jpg", "Mount Fuji", "08/01/2015"));
		countries.put(new Country("jp", "Japan"), new MyImage("https://i.ytimg.com/vi/kv4gVHdOMA0/maxresdefault.jpg", "Nishiki Food Market", "05/14/2019"));
		countries.put(new Country("jp", "Japan"), new MyImage("https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/b1w71r2sdpjsnzzfpqf9.jpg", "Ramen", "12/26/2018"));

		countries.get(new Country("es", "Spain")).set(0, new MyImage("https://letsbebackpackers.com/wp-content/uploads/2015/06/sagrada-familia-552084_1920-1024x857.jpg", "Sagarda Familia", "01/04/2020"));
		countries.put(new Country("es", "Spain"), new MyImage("https://www.planetware.com/photos-large/E/spain-madrid-puerta-del-sol.jpg", "Madrid", "11/28/2020"));
		countries.put(new Country("es", "Spain"), new MyImage("https://www.planetware.com/photos-large/E/spain-beaches-costa-brava-tossa-de-mar.jpg", "Beach", "06/29/2020"));

		displayCountries();

		this.setFocusable(true);
	}

	public Dimension getPreferredSize() {
		return new Dimension(800, 600);
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (!currentImageURL.equals("")) {
			try {
				URL url = new URL(currentImageURL);
				Image imagePre = ImageIO.read(url).getScaledInstance(600, 350, Image.SCALE_DEFAULT);
				BufferedImage image = new BufferedImage(imagePre.getWidth(null), imagePre.getHeight(null), BufferedImage.TYPE_INT_ARGB);
				g.drawImage(imagePre, 100, 100, null);
				g.drawImage(image, 100, 100, null);
			} catch(IOException e) {
				System.out.println(e);
			}
			g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
			g.drawString(caption, 295 + 40 - caption.length(), 500);
		}
	}

	public void displayCountries() {
		String text = "";
		HashTable <Country> keys = countries.getKeys();
		for (int i = 0; i < keys.SIZE; i++) {
			if (keys.get(i).size() > 0 && countries.get(keys.get(i).get(0)).get(0) != null) {
				text += keys.get(i).get(0) + ". Images: ";
				if (countries.get(keys.get(i).get(0)).get(0) != null) {
					text += countries.get(keys.get(i).get(0)).size() + "\n";
				}
			}
		}
		textArea.setText(text);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == search) {
			DLList < Country > list = countries.getKeys().get(new Country(searchCountry.getText().toLowerCase(), ""));
			if (list != null) {
				country = list.get(0);
				String text = "";
				text += country.toString();
				searchResult.setText(text);
				if (countries.get(list.get(0)).get(0) != null) {
					displayImages();
				}
				menuView(false);
				repaint();
			}

		} else if (e.getSource() == back) {
			menuView(true);
		} else if (e.getSource() == next) {
			if (activeImage + 1 < countries.get(country).size()) {
				currentImageURL = countries.get(country).get(++activeImage).getUrl();
				caption = countries.get(country).get(activeImage).getCaption() + "\t\t" + countries.get(country).get(activeImage).getDate();
				repaint();
			}
		} else if (e.getSource() == prev) {
			if (activeImage - 1 >= 0) {
				currentImageURL = countries.get(country).get(--activeImage).getUrl();
				caption = countries.get(country).get(activeImage).getCaption() + "\t\t" + countries.get(country).get(activeImage).getDate();
				repaint();
			}
		} else if (e.getSource() == addImage) {
			if (countries.get(country).get(0) != null) {
				countries.put(country, new MyImage(imageURL.getText(), imageCaption.getText(), imageDate.getText()));
			} else {
				countries.get(country).set(0, new MyImage(imageURL.getText(), imageCaption.getText(), imageDate.getText()));
				displayImages();
			}
			try {
				writer = new FileWriter(f, true);
				writer.write(country.getAb() + "-" + country.getName() + "\n" + imageURL.getText() + "\n" + imageCaption.getText() + "\n" + imageDate.getText() + "\n\n");
				writer.flush();
				writer.close();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
			repaint();
			imageURL.setText("");
			imageCaption.setText("");
			imageDate.setText("");
			displayCountries();
		}
	}

	public void menuView(boolean in) {
		scrollPane.setVisible(in);
		search.setVisible(in);
		searchCountry.setVisible(in);
		back.setVisible(!in);
		next.setVisible(!in);
		prev.setVisible(!in);
		addImage.setVisible(!in);
		imageURL.setVisible(!in);
		if (in) {
			currentImageURL = "";
		}
	}

	public void displayImages() {
		currentImageURL = countries.get(country).get(0).getUrl();
		activeImage = 0;
		caption = countries.get(country).get(0).getCaption() + "\t\t" + countries.get(country).get(0).getDate();
	}

}