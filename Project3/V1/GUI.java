/**     Name: Eric Kwon
 *      Project Phase 3 (GUI.java)
 * 
 *      Purpose of this File:
 *      1) Store data into a hashtable
 *      2) Data will be 
 */

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GUI extends JFrame {

    // Hashtable Instance & FetchURLData Instance
    HTInstance hashtableObj;
    FetchURLData fetchObj;

    // The hashtable to manipulate data
    LinkedHashMap<String,LinkedHashMap<String,String>> table;

    // Input and Output
    String inputName;
    String outputName;

    // Search Result Objects
    Object[][] offdisplay2D;
    Object[][] display2D;
    String[] columns = {"ITEM ID","ITEM TITLE","ITEM PRICE","AUCTION DATE","ITEM SELLER","PICTURE","QUERY TIME"};
    String search_term;

    // Check if components were added already
    Boolean main_added = false;
    Boolean online_added = false;
    Boolean onresult_added = false;
    Boolean offline_added = false;
    Boolean offresult_added = false;
    Boolean setting_added = false;
    Boolean add_added = false;
    Boolean modify_added = false;

    public GUI(String input, String output) {

        // Initialize the input and the output
        inputName = input;
        outputName = output;

        // Instantiate the hashtable object
        hashtableObj = new HTInstance();
        table = hashtableObj.readFile(inputName);

        // Set initial window size and position
        setTitle("Search Database");
        setSize(600,600);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the main window
        setMainWindow(); 
    }

    // Function to set the window defaults to the new JFrame's
    public static void setWindow(JFrame x, String y, int width, int height) {
        x.setTitle(y);
        x.setSize(width,height);
        x.setLocationRelativeTo(null);
        x.setLayout(null);
        x.setResizable(false);
        x.setVisible(true);
        x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Main JFrame Components
    private JLabel main_header = new JLabel("<html><b>SHOPGOODWILL Search Database</b></html>");
    private JButton main_online = new JButton("Online Search");
    private JButton main_offline = new JButton("Offline Search");
    private JButton main_settings = new JButton("Settings");
    private JButton main_exit = new JButton("Exit Program");
    private JLabel main_currentinouts = new JLabel("Current Input/Output");
    private JLabel main_in_text = new JLabel("Input: ");
    private JLabel main_out_text = new JLabel("Output: ");
    private JTextField main_in = new JTextField();
    private JTextField main_out = new JTextField();
    private JFileChooser main_jfc_in = new JFileChooser();
    private JFileChooser main_jfc_out = new JFileChooser();
    private JButton main_in_sel = new JButton("...");
    private JButton main_out_sel = new JButton("...");
    private JButton main_reprocess = new JButton("Re-evaluate Data Files");

    // Online Search Components
    private JFrame online_frame = new JFrame();
    private JLabel online_header = new JLabel("<html><b>SHOPGOODWILL Online Search</b></html>");
    private JLabel online_disclaimer = new JLabel("You need to have active internet connection to perform this task!");
    private JTextField online_searchTerm = new JTextField();
    private JButton online_search = new JButton("Search");
    private JCheckBox online_desctoo = new JCheckBox("Also Search Descriptions");
    private JLabel online_additional = new JLabel("<html><b>Additional Filters</b></html>");
    private JLabel online_lp = new JLabel("Lowest Price: ");
    private JLabel online_hp = new JLabel("Highest Price: ");
    private JTextField online_lowprice = new JTextField();
    private JTextField online_highprice = new JTextField();
    private JCheckBox online_buynow = new JCheckBox("Buy Now");
    private JCheckBox online_pickup = new JCheckBox("Local Pickup");
    private JCheckBox online_nonpickup = new JCheckBox("Exclude Pickup");
    private JCheckBox online_freeshipping = new JCheckBox("Free Shipping");
    private JCheckBox online_canada = new JCheckBox("Ship Canada");
    private JCheckBox online_intl = new JCheckBox("Ship Intl.");
    private JButton online_back = new JButton("Back to Menu");

    // Online Result Components
    private JFrame onresult_frame = new JFrame();
    private JLabel onresult_header = new JLabel("<html><b>Online Search Result");
    private JTable onresult_table;
    private JScrollPane onresult_scroll;
    private JButton onresult_back = new JButton("Go Back");
    
    // Offline Search Components
    private JFrame offline_frame = new JFrame();
    private JLabel offline_header = new JLabel("<html><b>SHOPGOODWILL Offline Search</b></html>");
    private JLabel offline_disclaimer = new JLabel("Offline search can only search by title");
    private JTextField offline_searchTerm = new JTextField();
    private JButton offline_search = new JButton("Search");
    private JButton offline_back = new JButton("Back to Menu");

    // Offline Result Components
    private JFrame offresult_frame = new JFrame();
    private JLabel offresult_header = new JLabel("<html><b>Offline Search Result");
    private JTable offresult_table;
    private JScrollPane offresult_scroll;
    private JButton offresult_back = new JButton("Go Back");

    // Settings Component
    private JFrame setting_frame = new JFrame();
    private JLabel setting_header = new JLabel("<html><b>Database Settings</b></html>");
    private JButton setting_add = new JButton("Add Record");
    private JButton setting_modify = new JButton("Modify / Delete Record");
    private JButton setting_reconstruct = new JButton("Reconstruct DB");
    private JButton setting_back = new JButton("Back to Menu");

    // Add Record Component
    private JFrame add_frame = new JFrame();
    private JLabel add_header = new JLabel("<html><b>Add Individual Record</b></html>");
    private JLabel add_id = new JLabel("Item ID: ");
    private JTextField add_id_in = new JTextField();
    private JLabel add_title = new JLabel("Item Title: ");
    private JTextField add_title_in = new JTextField();
    private JLabel add_price = new JLabel("Item Price: ");
    private JTextField add_price_in = new JTextField();
    private JLabel add_auction = new JLabel("Auction Date: ");
    private JTextField add_auction_in = new JTextField();
    private JLabel add_seller = new JLabel("Seller: ");
    private JTextField add_seller_in = new JTextField();
    private JLabel add_pic = new JLabel("Picture: ");
    private JTextField add_pic_in = new JTextField();
    private JButton add_pic_attach = new JButton("...");
    private JFileChooser add_pic_jfc = new JFileChooser();
    private JLabel add_query = new JLabel("Query On: ");
    private JTextField add_query_in = new JTextField();
    private JButton add_add = new JButton("Add Record");
    private JButton add_back = new JButton("Go Back");

    // Modify Record Component
    private JFrame modify_frame = new JFrame();
    private JLabel modify_header = new JLabel("<html><b>Modify Individual Record</b></html>");
    private JLabel modify_id = new JLabel("Item ID: ");
    private JTextField modify_id_in = new JTextField();
    private JButton modify_id_find = new JButton("Find");
    private JLabel modify_title = new JLabel("Item Title: ");
    private JTextField modify_title_in = new JTextField();
    private JLabel modify_price = new JLabel("Item Price: ");
    private JTextField modify_price_in = new JTextField();
    private JLabel modify_auction = new JLabel("Auction Date: ");
    private JTextField modify_auction_in = new JTextField();
    private JLabel modify_seller = new JLabel("Seller: ");
    private JTextField modify_seller_in = new JTextField();
    private JLabel modify_pic = new JLabel("Picture: ");
    private JTextField modify_pic_in = new JTextField();
    private JButton modify_pic_attach = new JButton("...");
    private JFileChooser modify_pic_jfc = new JFileChooser();
    private JLabel modify_query = new JLabel("Query On: ");
    private JTextField modify_query_in = new JTextField();
    private JButton modify_modify = new JButton("Modify Record");
    private JButton modify_delete = new JButton("Delete Record");
    private JButton modify_back = new JButton("Go Back");

    // Main Window Composition
    private void setMainWindow() {

        // Position and size
        main_header.setBounds(5,5,590,40);
        main_online.setBounds(155,55,300,50);
        main_offline.setBounds(155,105,300,50);
        main_settings.setBounds(155,155,300,50);
        main_exit.setBounds(155,205,300,50);
        main_currentinouts.setBounds(5,275,590,40);
        main_in_text.setBounds(155,315,160,30);
        main_out_text.setBounds(155,355,160,30);
        main_in.setBounds(230,315,160,30);
        main_out.setBounds(230,355,160,30);
        main_in_sel.setBounds(395,315,60,30);
        main_out_sel.setBounds(395,355,60,30);
        main_reprocess.setBounds(155,395,300,50);
        
        // Alignments
        main_header.setHorizontalAlignment(JLabel.CENTER);
        main_in_text.setVerticalAlignment(JLabel.CENTER);
        main_out_text.setVerticalAlignment(JLabel.CENTER);
        main_currentinouts.setHorizontalAlignment(JLabel.CENTER);

        // Set textfield to input and output names
        main_in.setText(inputName);
        main_out.setText(outputName);

        // Add to the frame
        if (!main_added) {
            main_added = true;
            add(main_header);
            add(main_online);
            add(main_offline);
            add(main_settings);
            add(main_exit);
            add(main_currentinouts);
            add(main_in_text);
            add(main_out_text);
            add(main_in);
            add(main_out);
            add(main_in_sel);
            add(main_out_sel);
            add(main_reprocess);
        }

        // --- Action Listeners START

        // Online Button
        main_online.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                setOnlineWindow();
            }
        });
        
        // Offline Button
        main_offline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                setOfflineWindow();
            }
        });

        // Setting Button
        main_settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                setSettingsWindow();
            }
        });
        // Exit Button
        main_exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hashtableObj.writeFile(table,outputName);
                System.exit(0);
            }
        });

        // Input Select Button
        main_in_sel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main_jfc_in.setCurrentDirectory(new File("."));
                int returnValue = main_jfc_in.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = main_jfc_in.getSelectedFile();
                    inputName = selectedFile.getName();
                    main_in.setText(inputName);
                }
            }
        });

        // Output Select Button
        main_out_sel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main_jfc_out.setCurrentDirectory(new File("."));
                int returnValue = main_jfc_out.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = main_jfc_out.getSelectedFile();
                    outputName = selectedFile.getName();
                    main_out.setText(outputName);
                }
            }
        });

        // Re-evaluation Button
        main_reprocess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                table = hashtableObj.readFile(inputName);
                JOptionPane.showMessageDialog(null, "Data files have been re-evaluated");
            }
        });

        // -- Action Listeners END
    }

    // Online Search Window Composition
    private void setOnlineWindow() {

        // Set the default window
        setWindow(online_frame,"Online Search",600,600);

        // Position and size
        online_header.setBounds(5,5,590,20);
        online_disclaimer.setBounds(5,25,590,20);
        online_searchTerm.setBounds(5,65,590,20);
        online_desctoo.setBounds(5,85,200,20);
        online_additional.setBounds(5,125,590,20);
        online_lp.setBounds(5,155,100,20);
        online_lowprice.setBounds(105,155,100,20);
        online_hp.setBounds(5,175,100,20);
        online_highprice.setBounds(105,175,100,20);
        online_buynow.setBounds(215,155,120,20);
        online_pickup.setBounds(215,175,120,20);
        online_nonpickup.setBounds(335,155,130,20);
        online_freeshipping.setBounds(335,175,130,20);
        online_canada.setBounds(465,155,130,20);
        online_intl.setBounds(465,175,130,20);
        online_search.setBounds(155,205,300,50);
        online_back.setBounds(155,255,300,50);

        // Alignment
        online_header.setHorizontalAlignment(JLabel.CENTER);
        online_disclaimer.setHorizontalAlignment(JLabel.CENTER);
        online_additional.setHorizontalAlignment(JLabel.CENTER);

        // Adding to frame
        if (!online_added) {
            online_added = true;
            online_frame.add(online_header);
            online_frame.add(online_disclaimer);
            online_frame.add(online_searchTerm);
            online_frame.add(online_desctoo);
            online_frame.add(online_additional);
            online_frame.add(online_lp);
            online_frame.add(online_lowprice);
            online_frame.add(online_hp);
            online_frame.add(online_highprice);
            online_frame.add(online_buynow);
            online_frame.add(online_pickup);
            online_frame.add(online_nonpickup);
            online_frame.add(online_freeshipping);
            online_frame.add(online_canada);
            online_frame.add(online_intl);
            online_frame.add(online_search);
            online_frame.add(online_back);
        }

        // --- Action Listeners START

        // Search Button
        online_search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Check if a search term is provided and has active internet connection
                if (reachable() && !online_searchTerm.getText().isEmpty()) {

                    // Instantiate the fetchObj and set the search term
                    fetchObj = new FetchURLData();
                    search_term = online_searchTerm.getText();
                    search_term = search_term.replace(" ","%20");
                    fetchObj.set_searchTerm(search_term);

                    // Setting the parameters
                    if (online_desctoo.isSelected())
                        fetchObj.set_searchDesc(true);
                    if (!online_lowprice.getText().isEmpty())
                        fetchObj.set_lowPrice(Integer.valueOf(online_lowprice.getText()));
                    if (!online_highprice.getText().isEmpty())
                        fetchObj.set_highPrice(Integer.valueOf(online_highprice.getText()));
                    if (online_buynow.isSelected())
                        fetchObj.set_buyNow(true);
                    if (online_pickup.isSelected())
                        fetchObj.set_pickup(true);
                    if (online_nonpickup.isSelected())
                        fetchObj.set_nonPickup(true);
                    if (online_freeshipping.isSelected())
                        fetchObj.set_oneCentShip(true);
                    if (online_canada.isSelected())
                        fetchObj.set_canadaShip(true);
                    if (online_intl.isSelected())
                        fetchObj.set_intlShip(true);

                    // Fetch the data
                    String queryLocation = fetchObj.fetchData();
                    int queries = fetchObj.getQueryCount();
                    display2D = hashtableObj.readQuery(queryLocation, table, queries);

                    // Convert to result window
                    setonResultWindow(columns, display2D);
                }

                // Else show a warning message
                else {
                    if (!reachable())
                        JOptionPane.showMessageDialog(null, "Shopgoodwill.com is not reachable");
                    else
                        JOptionPane.showMessageDialog(null, "Please provide a search term");
                }
            }
        });

        // Back to Menu Button
        online_back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                online_frame.dispose();
                setVisible(true);
            }
        });

        // -- Action Listeners END

    }

    // Online Search Result Window Composition
    private void setonResultWindow(String[] x, Object[][] y) {

        // Set the default window and hide the current window
        online_frame.dispose();
        setWindow(onresult_frame,"Online Search Result",1000,600);

        // Set the JTable and JScrollPane
        onresult_table = new JTable(y,x);
        onresult_table.setFillsViewportHeight(true);
        onresult_scroll = new JScrollPane(onresult_table);

        // Position and Size
        onresult_header.setBounds(5,5,990,20);
        onresult_scroll.setBounds(5,25,990,450);
        onresult_back.setBounds(350,500,300,50);
        
        // JScrollPane Options
        onresult_scroll.setViewportView(onresult_table);
        onresult_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Alignment
        onresult_header.setHorizontalAlignment(JLabel.CENTER);

        // Add to the frame
        if (!onresult_added) {
            onresult_added = true;
            onresult_frame.add(onresult_header);
            onresult_frame.add(onresult_scroll,BorderLayout.CENTER);
            onresult_frame.add(onresult_back);
        }

        // --- Action Listeners START

        // Back Button
        onresult_back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onresult_table.removeAll();
                onresult_scroll.removeAll();
                onresult_frame.dispose();            
                online_frame.setVisible(true);
            }
        });

        // -- Action Listeners END
    }

    // Offline Search Window Composition
    private void setOfflineWindow() {
        
        // Set the default window
        setWindow(offline_frame,"Offline Search",600,600);

        // Position and size
        offline_header.setBounds(5,5,590,20);
        offline_disclaimer.setBounds(5,25,590,20);
        offline_searchTerm.setBounds(5,65,590,20);
        offline_search.setBounds(155,95,300,50);
        offline_back.setBounds(155,145,300,50);

        // Alignment
        offline_header.setHorizontalAlignment(JLabel.CENTER);
        offline_disclaimer.setHorizontalAlignment(JLabel.CENTER);

        // Add to Frame
        if (!offline_added) {
            offline_added = true;
            offline_frame.add(offline_header);
            offline_frame.add(offline_disclaimer);
            offline_frame.add(offline_searchTerm);
            offline_frame.add(offline_search);
            offline_frame.add(offline_back);
        }

        // --- Action Listeners START

        // Search Button
        offline_search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Check if a keyword is given
                if (!offline_searchTerm.getText().isEmpty()) {

                    // Save the search term
                    search_term = offline_searchTerm.getText();
                    
                    // Resulting arrays
                    offdisplay2D = hashtableObj.searchDB(search_term,table);
                    setoffResultWindow(columns, offdisplay2D);                     
                }
            }
        });
        
        // Back Button
        offline_back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                offline_frame.dispose();
                setVisible(true);
            }
        });

        // -- Action Listeners END
    }

    // Offline Search Result Window Composition
    private void setoffResultWindow(String[] x, Object[][] y) {

        // Set the default window and hide the current window
        offline_frame.dispose();
        setWindow(offresult_frame,"Offline Search Result",1000,600);

        // Set the JTable and JScrollPane
        offresult_table = new JTable(y,x);
        offresult_table.setFillsViewportHeight(true);
        offresult_scroll = new JScrollPane(offresult_table);

        // Position and Size
        offresult_header.setBounds(5,5,990,20);
        offresult_scroll.setBounds(5,25,990,450);
        offresult_back.setBounds(350,500,300,50);

        // JScrollPane Options
        offresult_scroll.setViewportView(offresult_table);
        offresult_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Alignment
        offresult_header.setHorizontalAlignment(JLabel.CENTER);

        // Add to the frame
        if (!offresult_added) {
            offresult_added = true;
            offresult_frame.add(offresult_header);
            offresult_frame.add(offresult_scroll,BorderLayout.CENTER);
            offresult_frame.add(offresult_back);
        }

        // --- Action Listeners START

        // Back Button
        offresult_back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                offresult_frame.dispose();         
                offline_frame.setVisible(true);
            }
        });

        // -- Action Listeners END
    }

    // Settings Window Composition
    private void setSettingsWindow() {

        // Set the default view
        setWindow(setting_frame,"Setting",600,600);

        // Position and size
        setting_header.setBounds(5,5,590,20);
        setting_add.setBounds(155,55,300,50);
        setting_modify.setBounds(155,105,300,50);
        setting_reconstruct.setBounds(155,155,300,50);
        setting_back.setBounds(155,205,300,50);

        // Alignments
        setting_header.setHorizontalAlignment(JLabel.CENTER);

        // Add to frame
        if (!setting_added) {
            setting_added = true;
            setting_frame.add(setting_header);
            setting_frame.add(setting_add);
            setting_frame.add(setting_modify);
            setting_frame.add(setting_reconstruct);
            setting_frame.add(setting_back);
        }

        // --- Action Listeners START

        // Add Record Button
        setting_add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setting_frame.dispose();        
                setAddWindow();

                // Set the query date to when the button is clicked
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                add_query_in.setText(dateFormat.format(new Date()));
            }
        });

        // Modify Record Button
        setting_modify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setting_frame.dispose();         
                setModifyWindow();
            }
        });

        // Back Button
        setting_back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setting_frame.dispose();          
                setVisible(true);
            }
        });

        // -- Action Listeners END
    }

    // Add Record Window Composition
    private void setAddWindow() {

        // Set the default window
        setWindow(add_frame,"Add Record",600,600);

        // Position and Size
        add_header.setBounds(5,5,590,40);
        add_id.setBounds(150,65,150,30);
        add_id_in.setBounds(300,65,150,30);
        add_title.setBounds(150,95,150,30);
        add_title_in.setBounds(300,95,150,30);
        add_price.setBounds(150,125,150,30);
        add_price_in.setBounds(300,125,150,30);
        add_auction.setBounds(150,155,150,30);
        add_auction_in.setBounds(300,155,150,30);
        add_seller.setBounds(150,185,150,30);
        add_seller_in.setBounds(300,185,150,30);
        add_pic.setBounds(150,215,150,30);
        add_pic_in.setBounds(300,215,150,30);
        add_pic_attach.setBounds(450,215,60,30);
        add_query.setBounds(150,245,150,30);
        add_query_in.setBounds(300,245,150,30);
        add_add.setBounds(155,285,300,50);
        add_back.setBounds(155,335,300,50);

        // Alignments
        add_header.setHorizontalAlignment(JLabel.CENTER);

        // Add to frame
        if (!add_added) {
            add_added = true;
            add_frame.add(add_header);
            add_frame.add(add_id);
            add_frame.add(add_id_in);
            add_frame.add(add_title);
            add_frame.add(add_title_in);
            add_frame.add(add_price);
            add_frame.add(add_price_in);
            add_frame.add(add_auction);
            add_frame.add(add_auction_in);
            add_frame.add(add_seller);
            add_frame.add(add_seller_in);
            add_frame.add(add_pic);
            add_frame.add(add_pic_in);
            add_frame.add(add_pic_attach);
            add_frame.add(add_query);
            add_frame.add(add_query_in);
            add_frame.add(add_add);
            add_frame.add(add_back);
        }

        // ETC Setting
        add_query_in.setEditable(false);

        // --- Action Listeners START

        // Add Button
        add_add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Get the data entered and make a string array
                String[] data = new String[7];
                data[0] = add_id_in.getText();
                data[1] = add_title_in.getText();
                data[2] = add_price_in.getText();  
                data[3] = add_auction_in.getText();
                data[4] = add_seller_in.getText();
                data[5] = add_pic_in.getText();
                data[6] = add_query_in.getText();

                // Insert into the table
                table.put(data[0],hashtableObj.insert(data));

                // Display a message
                JOptionPane.showMessageDialog(null, "Record has been added");

                // Clear the textfield
                add_id_in.setText("");
                add_title_in.setText("");
                add_price_in.setText("");    
                add_auction_in.setText("");
                add_seller_in.setText("");
                add_pic_in.setText("");
                add_query_in.setText(""); 
            }
        });

        // Attach Button
        add_pic_attach.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                add_pic_jfc.setCurrentDirectory(new File("."));
                int returnValue = add_pic_jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = add_pic_jfc.getSelectedFile();
                    String picPath = selectedFile.getPath();
                    String[] picArr = picPath.split("\\./");
                    picPath = picArr[1];
                    picPath = picPath.replace("img1.jpg","");
                    add_pic_in.setText(picPath);
                }
            }
        });

        // Back Button
        add_back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                add_id_in.setText("");
                add_title_in.setText("");
                add_price_in.setText("");    
                add_auction_in.setText("");
                add_seller_in.setText("");
                add_pic_in.setText("");
                add_query_in.setText(""); 
                add_frame.dispose();            
                setting_frame.setVisible(true);
            }
        });

        // -- Action Listeners END
    }

    // Modify Window Composition
    private void setModifyWindow() {

        // Set the default window
        setWindow(modify_frame,"Modify Record",600,600);

        // Position and Size
        modify_header.setBounds(5,5,590,40);
        modify_id.setBounds(150,65,150,30);
        modify_id_in.setBounds(300,65,150,30);
        modify_id_find.setBounds(450,65,60,30);
        modify_title.setBounds(150,95,150,30);
        modify_title_in.setBounds(300,95,150,30);
        modify_price.setBounds(150,125,150,30);
        modify_price_in.setBounds(300,125,150,30);
        modify_auction.setBounds(150,155,150,30);
        modify_auction_in.setBounds(300,155,150,30);
        modify_seller.setBounds(150,185,150,30);
        modify_seller_in.setBounds(300,185,150,30);
        modify_pic.setBounds(150,215,150,30);
        modify_pic_in.setBounds(300,215,150,30);
        modify_pic_attach.setBounds(450,215,60,30);
        modify_query.setBounds(150,245,150,30);
        modify_query_in.setBounds(300,245,150,30);
        modify_modify.setBounds(155,285,300,50);
        modify_delete.setBounds(155,335,300,50);
        modify_back.setBounds(155,385,300,50);

        // Alignments
        modify_header.setHorizontalAlignment(JLabel.CENTER);
        
        // Add to frame
        if (!modify_added) {
            modify_added = true;
            modify_frame.add(modify_header);
            modify_frame.add(modify_id);
            modify_frame.add(modify_id_in);
            modify_frame.add(modify_id_find);
            modify_frame.add(modify_title);
            modify_frame.add(modify_title_in);
            modify_frame.add(modify_price);                
            modify_frame.add(modify_price_in);
            modify_frame.add(modify_auction);
            modify_frame.add(modify_auction_in);
            modify_frame.add(modify_seller);
            modify_frame.add(modify_seller_in);
            modify_frame.add(modify_pic);
            modify_frame.add(modify_pic_in);
            modify_frame.add(modify_pic_attach);
            modify_frame.add(modify_query);
            modify_frame.add(modify_query_in);
            modify_frame.add(modify_modify);
            modify_frame.add(modify_delete);
            modify_frame.add(modify_back);
        }
        
        // ETC Setting
        modify_query_in.setEditable(false);

        // --- Action Listeners START

        // Find Button
        modify_id_find.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Fetch the ID and query
                String modItemId = modify_id_in.getText();
                String[] modResult = hashtableObj.findID(modItemId, table);

                // If the query returns a table
                if (modResult[0] == "f") {
                    modify_title_in.setText(modResult[1]);
                    modify_price_in.setText(modResult[2]);
                    modify_auction_in.setText(modResult[3]);
                    modify_seller_in.setText(modResult[4]);
                    modify_pic_in.setText(modResult[5]);
                    modify_query_in.setText(modResult[6]);
                }

                // If the ID is not found
                else {
                    JOptionPane.showMessageDialog(null, "Requested ID not found");
                }
            }
        });

        // Modify Button
        modify_modify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Get the data entered and make a string array
                String[] data = new String[7];
                data[0] = modify_id_in.getText();
                data[1] = modify_title_in.getText();
                data[2] = modify_price_in.getText();  
                data[3] = modify_auction_in.getText();
                data[4] = modify_seller_in.getText();
                data[5] = modify_pic_in.getText();
                data[6] = modify_query_in.getText();

                // Insert into the table
                table.put(data[0],hashtableObj.insert(data));

                // Clear the textfield
                modify_id_in.setText("");
                modify_title_in.setText("");
                modify_price_in.setText("");    
                modify_auction_in.setText("");
                modify_seller_in.setText("");
                modify_pic_in.setText("");
                modify_query_in.setText("");

                // Display Message
                JOptionPane.showMessageDialog(null, "Record has been modified");
            }   
        });

        // Delete Button
        modify_delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Get the data entered and make a string array
                String keytoDel = modify_id_in.getText();

                // Remove the key from the table;
                table.remove(keytoDel);

                // Display Message
                JOptionPane.showMessageDialog(null, "Record has been removed");
            }   
        });

        // Attach Button
        modify_pic_attach.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modify_pic_jfc.setCurrentDirectory(new File("."));
                int returnValue = modify_pic_jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = modify_pic_jfc.getSelectedFile();
                    String picPath = selectedFile.getPath();
                    String[] picArr = picPath.split("\\./");
                    picPath = picArr[1];
                    picPath = picPath.replace("img1.jpg","");
                    modify_pic_in.setText(picPath);
                }
            }
        });

        // Back Button
        modify_back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modify_id_in.setText("");
                modify_title_in.setText("");
                modify_price_in.setText("");    
                modify_auction_in.setText("");
                modify_seller_in.setText("");
                modify_pic_in.setText("");
                modify_query_in.setText(""); 
                modify_frame.dispose();            
                setting_frame.setVisible(true);
            }
        });

        // -- Action Listeners END
    }

    // Function to check if shopgoodwill.com is reachable (have internet connection)
    public static Boolean reachable() {
        try {
            final URL url = new URL("https://www.shopgoodwill.com");
            final URLConnection u = url.openConnection();
            u.connect();
            return true;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
 }