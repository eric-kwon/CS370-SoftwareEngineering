/**     Name: Eric Kwon
 *      Project Phase 3 (GUI.java)
 */

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.ScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableModel;

@SuppressWarnings("unchecked")
public class GUI {
	
	// JFrame Components
	private JFrame GUI_frame;
	private JTextField main_keyword;
	private JTextField main_itemID;
	private JTextField main_min_jtext;
	private JTextField main_max_jtext;
	private final ButtonGroup button_openorclose = new ButtonGroup();
	private final ButtonGroup button_onlineoroffline = new ButtonGroup();
	private final ButtonGroup button_ascordesc = new ButtonGroup();
	private JTextField main_input;
	private JTextField main_output;
	
	// Database Related Components
	private String inputName;
	private String outputName;
	private HTInstance hashtableObj;
	private FetchURLData fetchObj;
	private LinkedHashMap<String,LinkedHashMap<String,String>> table;
	private Boolean isInternetAlive = false;
	private CreateTemplate tempMaker;
	
	// Items for JTable Search Result Display
	String[] columns = {"ITEM ID","ITEM TITLE","ITEM PRICE","AUCTION DATE","ITEM SELLER","PICTURE URL","QUERY DATE"};
	Object[][] offdisplay2D;
	Object[][] ondisplay2D;
	String search_term;
	private DefaultTableModel online_model;
	private JTable online_table;
	private DefaultTableModel offline_model;
	private JTable offline_table;
	private DefaultTableModel offline_model_ID;
	
	// JCombobox Components for sellers and categories
	private FetchCategories fetchCats;
	private FetchSellers fetchSel;
	String[] orderBy = {"Item Title","Price","Auction Date"};
	String[] catArray;
	int[] catIDArray;
	String[] selArray;
	int[] selIDArray;
	
	// Check if internet connection is active
	public void reachable() {
		
		// Try block
		try {
			final URL url = new URL("https://shopgoodwill.com");
			final URLConnection u = url.openConnection();
			u.connect();
			isInternetAlive = true;
		}
		
		// Exception handler
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	// GUI Class Instance Initialization
	public GUI(String input, String output) {
		
		// Set input and output files
		inputName = input;
		outputName = output;
		
		// Instantiate the database classes and read file
		fetchObj = new FetchURLData();
		hashtableObj = new HTInstance();
		tempMaker = new CreateTemplate();
		table = hashtableObj.readFile(inputName);
		
		// Check for internet connection
		reachable();
		
			// If internet connection is alive, set JComboBox for the sellers and categories
		if (isInternetAlive) {
			
			// Fetch categories
			fetchCats = new FetchCategories();
			fetchCats.fetchCats();
			catArray = fetchCats.getCatArray();
			catIDArray = fetchCats.getIDArray();
			
			// Fetch sellers
			fetchSel = new FetchSellers();
			fetchSel.fetchSellers();
			selArray = fetchSel.getSellerArray();
			selIDArray = fetchSel.getIDArray();
		}
		
		// Initialize the GUI frame
		initialize();
		GUI_frame.setVisible(true);
		
	}

	// GUI Frame Instance Initialization
	private void initialize() {
		GUI_frame = new JFrame();
		GUI_frame.setTitle("Database Program");
		GUI_frame.setBounds(100, 100, 1023, 837);
		GUI_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI_frame.getContentPane().setLayout(new CardLayout(0, 0));
		GUI_frame.setLocationRelativeTo(null);
		
		JPanel main_panel = new JPanel();
		GUI_frame.getContentPane().add(main_panel, "name_136217859691122");
		main_panel.setLayout(null);
		
		JLabel main_header = new JLabel("Shopgoodwill Search Inventory Manager");
		main_header.setHorizontalAlignment(SwingConstants.CENTER);
		main_header.setFont(new Font("Tahoma", Font.BOLD, 24));
		main_header.setBounds(0, 5, 999, 60);
		main_panel.add(main_header);
		
		main_keyword = new JTextField();
		main_keyword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_keyword.setBounds(15, 164, 469, 30);
		main_panel.add(main_keyword);
		main_keyword.setColumns(10);
		
		JPanel online_panel = new JPanel();
		GUI_frame.getContentPane().add(online_panel, "name_18315470693347");
		online_panel.setLayout(null);
		
		JLabel online_header = new JLabel("Online Search Results");
		online_header.setBounds(0, 5, 999, 60);
		online_panel.add(online_header);
		online_header.setHorizontalAlignment(SwingConstants.CENTER);
		online_header.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		DefaultTableModel model = new DefaultTableModel(ondisplay2D,columns);
		online_table = new JTable(model);
		online_table.setBounds(5, 60, 2, 2);
		online_panel.add(online_table);
		
		DefaultTableModel model2 = new DefaultTableModel(offdisplay2D,columns);
		
		JScrollPane online_scrollpane = new JScrollPane();
		online_scrollpane.setBounds(5, 60, 989, 400);
		online_panel.add(online_scrollpane);
		
		JComboBox main_category_combo = new JComboBox(catArray);
		main_category_combo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_category_combo.setBackground(Color.WHITE);
		main_category_combo.setBounds(15, 360, 469, 30);
		main_panel.add(main_category_combo);
		
		JComboBox main_sellers_combo = new JComboBox(selArray);
		main_sellers_combo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_sellers_combo.setBackground(Color.WHITE);
		main_sellers_combo.setBounds(15, 442, 469, 30);
		main_panel.add(main_sellers_combo);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(15, 126, 969, 10);
		main_panel.add(separator);
		
		JPanel offline_panel = new JPanel();
		GUI_frame.getContentPane().add(offline_panel, "name_103110489918867");
		offline_panel.setLayout(null);
		offline_table = new JTable(model2);
		offline_table.setBounds(5, 60, 2, 2);
		offline_panel.add(offline_table);
		
		JScrollPane offline_scrollpane = new JScrollPane();
		offline_scrollpane.setBounds(5, 60, 989, 400);
		offline_panel.add(offline_scrollpane);
		
		JLabel offline_header = new JLabel("Offline Search Results");
		offline_header.setBounds(0, 5, 999, 60);
		offline_header.setHorizontalAlignment(SwingConstants.CENTER);
		offline_header.setFont(new Font("Tahoma", Font.BOLD, 24));
		offline_panel.add(offline_header);
		
		JLabel main_desc1 = new JLabel("Find Items by Title, Item Number, or Category");
		main_desc1.setFont(new Font("Tahoma", Font.BOLD, 18));
		main_desc1.setBounds(15, 91, 447, 30);
		main_panel.add(main_desc1);
		
		JLabel main_desc2 = new JLabel("Search By Keywords");
		main_desc2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_desc2.setBounds(15, 132, 469, 30);
		main_panel.add(main_desc2);
		
		JCheckBox main_desctoo = new JCheckBox("Search Titles & Descriptions");
		main_desctoo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_desctoo.setBounds(15, 196, 469, 30);
		main_panel.add(main_desctoo);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBounds(15, 240, 469, 10);
		main_panel.add(separator_1);
		
		JLabel main_desc3 = new JLabel("Search By Item Number");
		main_desc3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_desc3.setBounds(15, 246, 469, 30);
		main_panel.add(main_desc3);
		
		main_itemID = new JTextField();
		main_itemID.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_itemID.setColumns(10);
		main_itemID.setBounds(15, 278, 469, 30);
		main_panel.add(main_itemID);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.BLACK);
		separator_2.setBounds(15, 322, 469, 10);
		main_panel.add(separator_2);
		
		JLabel main_desc4 = new JLabel("Category");
		main_desc4.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_desc4.setBounds(15, 328, 469, 30);
		main_panel.add(main_desc4);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.BLACK);
		separator_3.setBounds(15, 404, 469, 10);
		main_panel.add(separator_3);
		
		JLabel main_desc5 = new JLabel("Sellers");
		main_desc5.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_desc5.setBounds(15, 410, 469, 30);
		main_panel.add(main_desc5);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setForeground(Color.BLACK);
		separator_4.setBounds(15, 486, 970, 10);
		main_panel.add(separator_4);
		
		JLabel main_desc6 = new JLabel("Status");
		main_desc6.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_desc6.setBounds(15, 492, 469, 30);
		main_panel.add(main_desc6);
		
		JRadioButton main_open_radio = new JRadioButton("Open Auction");
		button_openorclose.add(main_open_radio);
		main_open_radio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_open_radio.setBounds(15, 524, 155, 30);
		main_panel.add(main_open_radio);
		main_open_radio.setSelected(true);
		
		JRadioButton main_closed_radio = new JRadioButton("Closed Auction");
		button_openorclose.add(main_closed_radio);
		main_closed_radio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_closed_radio.setBounds(177, 524, 155, 30);
		main_panel.add(main_closed_radio);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setForeground(Color.BLACK);
		separator_5.setOrientation(SwingConstants.VERTICAL);
		separator_5.setBounds(499, 492, 10, 280);
		main_panel.add(separator_5);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setForeground(Color.BLACK);
		separator_6.setBounds(15, 568, 469, 10);
		main_panel.add(separator_6);
		
		JLabel main_desc7 = new JLabel("Filter by Price");
		main_desc7.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_desc7.setBounds(15, 574, 469, 30);
		main_panel.add(main_desc7);
		
		main_min_jtext = new JTextField();
		main_min_jtext.setText("Minimum");
		main_min_jtext.setToolTipText("");
		main_min_jtext.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_min_jtext.setBounds(15, 606, 146, 30);
		main_panel.add(main_min_jtext);
		main_min_jtext.setColumns(10);
		
		main_max_jtext = new JTextField();
		main_max_jtext.setText("Maximum");
		main_max_jtext.setToolTipText("");
		main_max_jtext.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_max_jtext.setColumns(10);
		main_max_jtext.setBounds(186, 606, 146, 30);
		main_panel.add(main_max_jtext);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setForeground(Color.BLACK);
		separator_7.setBounds(15, 650, 469, 10);
		main_panel.add(separator_7);
		
		JLabel main_desc8 = new JLabel("Order By");
		main_desc8.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_desc8.setBounds(15, 656, 469, 30);
		main_panel.add(main_desc8);
		
		JComboBox main_order_combo = new JComboBox(orderBy);
		main_order_combo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_order_combo.setBackground(Color.WHITE);
		main_order_combo.setBounds(15, 688, 469, 30);
		main_panel.add(main_order_combo);
		
		JRadioButton main_order_asc_radio = new JRadioButton("Ascending");
		button_ascordesc.add(main_order_asc_radio);
		main_order_asc_radio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_order_asc_radio.setBounds(15, 720, 155, 30);
		main_panel.add(main_order_asc_radio);
		main_order_asc_radio.setSelected(true);
		
		JRadioButton main_order_desc_radio = new JRadioButton("Descending");
		button_ascordesc.add(main_order_desc_radio);
		main_order_desc_radio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_order_desc_radio.setBounds(177, 720, 155, 30);
		main_panel.add(main_order_desc_radio);
		
		JLabel main_desc9 = new JLabel("Filter by Attribute");
		main_desc9.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_desc9.setBounds(514, 492, 469, 30);
		main_panel.add(main_desc9);
		
		JCheckBox main_check_buynow = new JCheckBox("Search for Buy Now Listings");
		main_check_buynow.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_check_buynow.setBounds(514, 524, 469, 30);
		main_panel.add(main_check_buynow);
		
		JCheckBox main_check_pickup = new JCheckBox("Search for Pickup Items Only");
		main_check_pickup.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_check_pickup.setBounds(514, 556, 469, 30);
		main_panel.add(main_check_pickup);
		
		JCheckBox main_check_nopickup = new JCheckBox("Do Not Include Pickup Only Items");
		main_check_nopickup.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_check_nopickup.setBounds(514, 588, 469, 30);
		main_panel.add(main_check_nopickup);
		
		JCheckBox main_check_freeship = new JCheckBox("Search for 1-Cent Shipping Only");
		main_check_freeship.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_check_freeship.setBounds(514, 620, 469, 30);
		main_panel.add(main_check_freeship);
		
		JSeparator separator_8 = new JSeparator();
		separator_8.setForeground(Color.BLACK);
		separator_8.setBounds(514, 664, 469, 10);
		main_panel.add(separator_8);
		
		JLabel main_desc10 = new JLabel("International Shipping");
		main_desc10.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_desc10.setBounds(514, 670, 469, 30);
		main_panel.add(main_desc10);
		
		JCheckBox main_check_canada = new JCheckBox("Canada");
		main_check_canada.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_check_canada.setBounds(514, 706, 469, 30);
		main_panel.add(main_check_canada);
		
		JCheckBox main_check_intl = new JCheckBox("Outside US & Canada");
		main_check_intl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_check_intl.setBounds(514, 742, 469, 30);
		main_panel.add(main_check_intl);
		
		JRadioButton main_online_radio = new JRadioButton("Online Query");
		main_online_radio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main_category_combo.setEnabled(true);
				main_sellers_combo.setEnabled(true);
				main_open_radio.setEnabled(true);
				main_closed_radio.setEnabled(true);
				main_min_jtext.setEnabled(true);
				main_max_jtext.setEnabled(true);
				main_order_combo.setEnabled(true);
				main_order_asc_radio.setEnabled(true);
				main_order_desc_radio.setEnabled(true);
				main_check_buynow.setEnabled(true);
				main_check_pickup.setEnabled(true);
				main_check_nopickup.setEnabled(true);
				main_check_freeship.setEnabled(true);
				main_check_canada.setEnabled(true);
				main_check_intl.setEnabled(true);
			}
		});
		button_onlineoroffline.add(main_online_radio);
		main_online_radio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_online_radio.setBounds(584, 91, 200, 29);
		main_panel.add(main_online_radio);
		if (isInternetAlive) main_online_radio.setSelected(true);
		
		JRadioButton main_offline_radio = new JRadioButton("Offline Query");
		main_offline_radio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main_category_combo.setEnabled(false);
				main_sellers_combo.setEnabled(false);
				main_open_radio.setEnabled(false);
				main_closed_radio.setEnabled(false);
				main_min_jtext.setEnabled(false);
				main_max_jtext.setEnabled(false);
				main_order_combo.setEnabled(false);
				main_order_asc_radio.setEnabled(false);
				main_order_desc_radio.setEnabled(false);
				main_check_buynow.setEnabled(false);
				main_check_pickup.setEnabled(false);
				main_check_nopickup.setEnabled(false);
				main_check_freeship.setEnabled(false);
				main_check_canada.setEnabled(false);
				main_check_intl.setEnabled(false);
			}
		});
		button_onlineoroffline.add(main_offline_radio);
		main_offline_radio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_offline_radio.setBounds(784, 92, 200, 29);
		main_panel.add(main_offline_radio);
		if (!isInternetAlive) main_offline_radio.setSelected(true);
		
		JButton main_searchbutton = new JButton("SEARCH");
		main_searchbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int switchCase = main_online_radio.isSelected() ? 0 : 1;
				
				switch(switchCase) {
				
				case 0:
				
					if(main_keyword.getText().equals("") && main_itemID.getText().equals("")) {
						JOptionPane.showMessageDialog(GUI_frame.getContentPane(), "Please enter search keyword or item ID");
					}
					else if ((!main_keyword.getText().equals("") && !main_itemID.getText().equals("")) || !main_itemID.getText().equals("")){
						if (!main_keyword.getText().equals("") && !main_itemID.getText().equals(""))
							JOptionPane.showMessageDialog(GUI_frame.getContentPane(), "Note: If both keyword and item ID was entered, search will be based on item ID");
						main_panel.setVisible(false);
						online_panel.setVisible(true);
					}
					else {
						
						// Collect the data from the main window
						fetchObj = new FetchURLData();
						fetchObj.set_searchTerm(main_keyword.getText().replaceAll(" ", "%20"));
						if (main_category_combo.getSelectedIndex() != 0)
							fetchObj.set_category(catIDArray[main_category_combo.getSelectedIndex()]);
						if (main_sellers_combo.getSelectedIndex() != 0)
							fetchObj.set_seller(selIDArray[main_sellers_combo.getSelectedIndex()]);
						
						if (main_closed_radio.isSelected())
							fetchObj.set_closedAuction(true);
						else
							fetchObj.set_closedAuction(false);
						
						if (main_min_jtext.getText().matches("[-+]?\\d*\\.?\\d+")) 
							fetchObj.set_lowPrice(Integer.valueOf(main_min_jtext.getText()));
						if (main_max_jtext.getText().matches("[-+]?\\d*\\.?\\d+"))
							fetchObj.set_highPrice(Integer.valueOf(main_max_jtext.getText()));
						
						if (main_order_combo.getSelectedIndex() == 0)
							fetchObj.set_sortBy(2);
						else if (main_order_combo.getSelectedIndex() == 1)
							fetchObj.set_sortBy(4);
						else
							fetchObj.set_sortBy(1);
						
						if (main_order_asc_radio.isSelected())
							fetchObj.set_descending(false);
						else
							fetchObj.set_descending(true);
						
						fetchObj.set_buyNow(main_check_buynow.isSelected());
						fetchObj.set_pickup(main_check_pickup.isSelected());
						fetchObj.set_nonPickup(main_check_nopickup.isSelected());
						fetchObj.set_oneCentShip(main_check_freeship.isSelected());
						fetchObj.set_canadaShip(main_check_canada.isSelected());
						fetchObj.set_intlShip(main_check_intl.isSelected());
						
						String queryLocation = fetchObj.fetchData();
						int queries = fetchObj.getQueryCount();
						ondisplay2D = hashtableObj.readQuery(queryLocation, table, queries);
						
						online_model = new DefaultTableModel(ondisplay2D,columns);
						online_table = new JTable(online_model);
						online_scrollpane.setViewportView(online_table);
						
						main_panel.setVisible(false);
						online_panel.setVisible(true);
					}
					break;
					
				case 1:
					
					if(main_keyword.getText().equals("") && main_itemID.getText().equals("")) {
						JOptionPane.showMessageDialog(GUI_frame.getContentPane(), "Please enter search keyword or item ID");
					}
					else if ((!main_keyword.getText().equals("") && !main_itemID.getText().equals("")) || main_keyword.getText().equals("")){
						if (!main_keyword.getText().equals("") && !main_itemID.getText().equals(""))
							JOptionPane.showMessageDialog(GUI_frame.getContentPane(), "Note: If both keyword and item ID was entered, search will be based on item ID");
						
						offdisplay2D = hashtableObj.findID2D(main_itemID.getText(),table);
						
						if (offdisplay2D[0][0] == "n") {
							JOptionPane.showMessageDialog(GUI_frame.getContentPane(), "Item ID not found");

						}
						else {
							offline_model_ID = new DefaultTableModel(offdisplay2D,columns);
							offline_table = new JTable(offline_model_ID);
							offline_table.revalidate();
							offline_scrollpane.setViewportView(offline_table);
							offline_scrollpane.repaint();
							main_panel.setVisible(false);
							online_panel.setVisible(true);
						}
						
					}
					else {
						offdisplay2D = hashtableObj.searchDB(main_keyword.getText(), table);
						
						offline_model = new DefaultTableModel(offdisplay2D,columns);
						offline_model.fireTableDataChanged();
						offline_table = new JTable(offline_model);
						offline_scrollpane.setViewportView(offline_table);
						
						main_panel.setVisible(false);
						offline_panel.setVisible(true);	
					}
					
				default: break;
				}
			}
		});
		main_searchbutton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		main_searchbutton.setBounds(514, 132, 469, 50);
		main_panel.add(main_searchbutton);
		
		JButton main_exitbutton = new JButton("EXIT PROGRAM");
		main_exitbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hashtableObj.writeFile(table, outputName);
				System.exit(0);
			}
		});
		main_exitbutton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		main_exitbutton.setBounds(514, 252, 469, 50);
		main_panel.add(main_exitbutton);
		
		JButton main_settingbutton = new JButton("SETTINGS");
		main_settingbutton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		main_settingbutton.setBounds(514, 192, 469, 50);
		main_panel.add(main_settingbutton);
		
		JLabel main_input_label = new JLabel("Input File: ");
		main_input_label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_input_label.setBounds(514, 392, 100, 30);
		main_panel.add(main_input_label);
		
		JLabel main_output_label = new JLabel("Output File:");
		main_output_label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_output_label.setBounds(514, 428, 100, 30);
		main_panel.add(main_output_label);
		
		main_input = new JTextField();
		main_input.setToolTipText("");
		main_input.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_input.setColumns(10);
		main_input.setBounds(627, 392, 150, 30);
		main_panel.add(main_input);
		main_input.setText(inputName);
		
		main_output = new JTextField();
		main_output.setToolTipText("");
		main_output.setFont(new Font("Tahoma", Font.PLAIN, 18));
		main_output.setColumns(10);
		main_output.setBounds(627, 428, 150, 30);
		main_panel.add(main_output);
		main_output.setText(outputName);
		
		JButton main_setbutton = new JButton("SET");
		main_setbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table = hashtableObj.readFile(inputName);
				JOptionPane.showMessageDialog(null, "Data files have been re-evaluated");
			}
		});
		main_setbutton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		main_setbutton.setBounds(854, 328, 130, 130);
		main_panel.add(main_setbutton);
		
		JLabel main_desc11 = new JLabel("Input / Output Files");
		main_desc11.setFont(new Font("Tahoma", Font.BOLD, 18));
		main_desc11.setBounds(514, 328, 313, 30);
		main_panel.add(main_desc11);
		
		JSeparator separator_11 = new JSeparator();
		separator_11.setForeground(Color.BLACK);
		separator_11.setBounds(514, 364, 313, 10);
		main_panel.add(separator_11);
		
		JSeparator separator_12 = new JSeparator();
		separator_12.setForeground(Color.BLACK);
		separator_12.setOrientation(SwingConstants.VERTICAL);
		separator_12.setBounds(499, 132, 10, 348);
		main_panel.add(separator_12);
		
		JSeparator separator_13 = new JSeparator();
		separator_13.setForeground(Color.BLACK);
		separator_13.setBounds(514, 322, 469, 10);
		main_panel.add(separator_13);
		
		JButton main_in_attach = new JButton("...");
		main_in_attach.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser in_jfc = new JFileChooser();
				in_jfc.setCurrentDirectory(new File("."));
				int returnValue = in_jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = in_jfc.getSelectedFile();
                    inputName = selectedFile.getName();
                    main_input.setText(inputName);
				}
			}
		});
		main_in_attach.setBounds(782, 392, 50, 30);
		main_panel.add(main_in_attach);
		
		JButton main_out_attach = new JButton("...");
		main_out_attach.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser out_jfc = new JFileChooser();
				out_jfc.setCurrentDirectory(new File("."));
				int returnValue = out_jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = out_jfc.getSelectedFile();
                    outputName = selectedFile.getName();
                    main_output.setText(outputName);
				}
			}
		});
		main_out_attach.setBounds(782, 428, 50, 30);
		main_panel.add(main_out_attach);
		
		JButton online_goback = new JButton("BACK TO MENU");
		online_goback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main_keyword.setText("");
				main_itemID.setText("");
				main_category_combo.setSelectedIndex(0);
				main_sellers_combo.setSelectedIndex(0);
				main_open_radio.setSelected(true);
				main_min_jtext.setText("Minimum");
				main_max_jtext.setText("Maximum");
				main_order_combo.setSelectedIndex(0);
				main_order_asc_radio.setSelected(true);
				main_check_buynow.setSelected(false);
				main_check_pickup.setSelected(false);
				main_check_nopickup.setSelected(false);
				main_check_freeship.setSelected(false);
				main_check_canada.setSelected(false);
				main_check_intl.setSelected(false);
				
				online_panel.setVisible(false);
				main_panel.setVisible(true);
			}
		});
		
		JButton online_printFriendly = new JButton("PRINT");
		online_printFriendly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selColumn = 0;
				int selRow = online_table.getSelectedRow();
				String selValue = online_table.getModel().getValueAt(selRow, selColumn).toString();
				String display = "File has been created: assets/" + selValue + "/print.html";
				String xid = selValue;
				String xtitle = table.get(xid).get("itemTitle");
				String ximg = table.get(xid).get("itemPic");
				String xprice = table.get(xid).get("itemPrice");
				String xseller = table.get(xid).get("itemSeller");
				String xauction = table.get(xid).get("itemAuction");
				String xquery = table.get(xid).get("itemQueryDT");
				int zip1 = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Your Zipcode: "));
				int zip2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Seller's Zipcode: "));
				tempMaker.createTemp(xid, xtitle, ximg, xprice, xseller, xauction, xquery, zip1, zip2);
				JOptionPane.showMessageDialog(null, display);
				String printFilePath = "assets/" + xid + "/print.html";
				File htmlFile = new File(printFilePath);
				try {
					Desktop.getDesktop().browse(htmlFile.toURI());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		online_printFriendly.setFont(new Font("Tahoma", Font.PLAIN, 22));
		online_printFriendly.setBounds(265, 625, 469, 50);
		online_panel.add(online_printFriendly);
		online_goback.setFont(new Font("Tahoma", Font.PLAIN, 22));
		online_goback.setBounds(265, 691, 469, 50);
		online_panel.add(online_goback);
		
		JButton offline_goback = new JButton("BACK TO MENU");
		offline_goback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main_keyword.setText("");
				main_itemID.setText("");
				
				offline_panel.setVisible(false);
				main_panel.setVisible(true);
			}
		});
		offline_goback.setBounds(265, 691, 469, 50);
		offline_goback.setFont(new Font("Tahoma", Font.PLAIN, 22));
		offline_panel.add(offline_goback);
		
		JButton offline_print = new JButton("PRINT");
		offline_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selColumn = 0;
				int selRow = offline_table.getSelectedRow();
				String selValue = offline_table.getModel().getValueAt(selRow, selColumn).toString();
				String display = "File has been created: assets/" + selValue + "/print.html";
				String xid = selValue;
				String xtitle = table.get(xid).get("itemTitle");
				String ximg = table.get(xid).get("itemPic");
				String xprice = table.get(xid).get("itemPrice");
				String xseller = table.get(xid).get("itemSeller");
				String xauction = table.get(xid).get("itemAuction");
				String xquery = table.get(xid).get("itemQueryDT");
				int zip1 = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Your Zipcode: "));
				int zip2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Seller's Zipcode: "));
				tempMaker.createTemp(xid, xtitle, ximg, xprice, xseller, xauction, xquery, zip1, zip2);
				JOptionPane.showMessageDialog(null, display);
				String printFilePath = "assets/" + xid + "/print.html";
				File htmlFile = new File(printFilePath);
				try {
					Desktop.getDesktop().browse(htmlFile.toURI());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		offline_print.setFont(new Font("Tahoma", Font.PLAIN, 22));
		offline_print.setBounds(265, 625, 469, 50);
		offline_panel.add(offline_print);
	}
}