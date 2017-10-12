import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.LinkedHashMap;

class DBGui extends JFrame {

    // Main JFrame Components
    private JPanel mainMode_status = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JLabel mainMode_statusL = new JLabel("Status...");
    private JLabel mainMode_headL = new JLabel("<html><b>::: INVENTORY DATABASE :::</b></html>");
    private JLabel mainMode_inputL = new JLabel("Input File:");
    private JLabel mainMode_outputL = new JLabel("Output File:");
    private JTextField mainMode_inputT = new JTextField();
    private JTextField mainMode_outputT = new JTextField();
    private JButton mainMode_processBt = new JButton("RUN");
    private JButton mainMode_editBt = new JButton("EDIT");
    private JButton mainMode_exitBt = new JButton("EXIT");

    // Edit mode JFrame Components
    private JFrame editMode_Frame = new JFrame();
    private JLabel editMode_Header = new JLabel("<html><b>::: EDIT MODE :::</b></html>");
    private JButton editMode_addEntry = new JButton("ADD ITEM");
    private JButton editMode_modifyEntry = new JButton("MODIFY ITEM");
    private JButton editMode_viewEntry = new JButton("VIEW ITEM");
    private JButton editMode_deleteEntry = new JButton("DELETE ITEM");
    private JButton editMode_gobackBt = new JButton("BACK TO MAIN MENU");

    // Add record JFrame Components
    private JPanel addMode_status = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JLabel addMode_statusL = new JLabel("Status...");
    private JFrame addMode_Frame = new JFrame();
    private JLabel addMode_Header = new JLabel("<html><b>::: ADD RECORD :::</b></html>");
    private JTextField addMode_recNameT = new JTextField();
    private JTextField addMode_recDescT = new JTextField();
    private JTextField addMode_recQtyT = new JTextField();
    private JTextField addMode_recPriceT = new JTextField();
    private JTextField addMode_recCostT = new JTextField();
    private JTextField addMode_recPurchT = new JTextField();
    private JLabel addMode_recNameL = new JLabel("Name: ");
    private JLabel addMode_recDescL = new JLabel("Description: ");
    private JLabel addMode_recQtyL = new JLabel("Quantity: ");
    private JLabel addMode_recPriceL = new JLabel("Price: ");
    private JLabel addMode_recCostL = new JLabel("Cost: ");
    private JLabel addMode_recPurchL = new JLabel("Purchased: ");
    private JButton addMode_recAddL = new JButton("ADD RECORD");
    private JButton addMode_gobackBt = new JButton("GO BACK");

    // Modify record JFrame Components
    private JFrame modMode_Frame = new JFrame();
    private JPanel modMode_status = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JLabel modMode_statusL = new JLabel("Status...");
    private JLabel modMode_Header = new JLabel ("<html><b>::: MODIFY RECORD :::</b></html>");
    private JLabel modMode_editRecL = new JLabel("SELECT ITEM: ");
    private JTextField modMode_editRecT = new JTextField();
    private JButton modMode_modBt = new JButton("MODIFY");
    private JButton modMode_gobackBt = new JButton("GO BACK");

    // Modify record JFrame Components #2
    private JPanel modMode2_status = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JLabel modMode2_statusL = new JLabel("Status...");
    private JFrame modMode2_Frame = new JFrame();
    private JLabel modMode2_Header = new JLabel("<html><b>::: MODIFY RECORD :::</b></html>");
    private JTextField modMode_recNameT = new JTextField();
    private JTextField modMode_recDescT = new JTextField();
    private JTextField modMode_recQtyT = new JTextField();
    private JTextField modMode_recPriceT = new JTextField();
    private JTextField modMode_recCostT = new JTextField();
    private JTextField modMode_recPurchT = new JTextField();
    private JLabel modMode_recNameL = new JLabel("Name: ");
    private JLabel modMode_recDescL = new JLabel("Description: ");
    private JLabel modMode_recQtyL = new JLabel("Quantity: ");
    private JLabel modMode_recPriceL = new JLabel("Price: ");
    private JLabel modMode_recCostL = new JLabel("Cost: ");
    private JLabel modMode_recPurchL = new JLabel("Purchased: ");
    private JButton modMode_recAddL = new JButton("EDIT RECORD");
    private JButton modMode2_gobackBt = new JButton("GO BACK");

    // View record JFrame Components
    private JFrame viewMode_Frame = new JFrame();
    private JPanel viewMode_status = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JLabel viewMode_statusL = new JLabel("Status...");
    private JLabel viewMode_Header = new JLabel ("<html><b>::: VIEW RECORD :::</b></html>");
    private JLabel viewMode_viewRecL = new JLabel("SELECT ITEM: ");
    private JTextField viewMode_viewRecT = new JTextField();
    private JButton viewMode_viewBt = new JButton("VIEW");
    private JButton viewMode_gobackBt = new JButton("GO BACK");

    // View record JFrame Components #2
    private JFrame viewMode2_Frame = new JFrame();
    private JLabel viewMode2_Header = new JLabel("<html><b>::: VIEW RECORD :::</b></html>");
    private JTextField viewMode_recNameT = new JTextField();
    private JTextField viewMode_recDescT = new JTextField();
    private JTextField viewMode_recQtyT = new JTextField();
    private JTextField viewMode_recPriceT = new JTextField();
    private JTextField viewMode_recCostT = new JTextField();
    private JTextField viewMode_recPurchT = new JTextField();
    private JTextField viewMode_recModifiedT = new JTextField();
    private JLabel viewMode_recNameL = new JLabel("Name: ");
    private JLabel viewMode_recDescL = new JLabel("Description: ");
    private JLabel viewMode_recQtyL = new JLabel("Quantity: ");
    private JLabel viewMode_recPriceL = new JLabel("Price: ");
    private JLabel viewMode_recCostL = new JLabel("Cost: ");
    private JLabel viewMode_recPurchL = new JLabel("Purchased: ");
    private JLabel viewMode_recModifiedL = new JLabel("Modified: ");
    private JButton viewMode2_gobackBt = new JButton("GO BACK");

    // Delete record JFrame Components
    private JFrame deleteMode_Frame = new JFrame();
    private JPanel deleteMode_status = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JLabel deleteMode_statusL = new JLabel("Status...");
    private JLabel deleteMode_Header = new JLabel("<html><b>::: DELETE RECORD :::</b></html>");
    private JLabel deleteMode_deleteRecL = new JLabel("SELECT ITEM: ");
    private JTextField deleteMode_deleteRecT = new JTextField();
    private JButton deleteMode_deleteBt = new JButton("DELETE");
    private JButton deleteMode_gobackBt = new JButton("GO BACK");

    // Miscellaneous Components
    static String inputName;
    static String outputName;
    database newDBInstance = new database();
    LinkedHashMap<String, LinkedHashMap<String, String>> products;

    private void setComponents(String input, String output) {

        // Main Frame Size & Positions
        mainMode_status.setBorder(new BevelBorder(BevelBorder.LOWERED));
        mainMode_status.setBounds(0, 250, 300, 30);
        mainMode_headL.setBounds(5,5,290,40);
        mainMode_inputL.setBounds(5, mainMode_headL.getY()+mainMode_headL.getHeight()+5, mainMode_outputL.getPreferredSize().width, mainMode_inputT.getPreferredSize().height);
        mainMode_inputT.setBounds(mainMode_inputL.getX()+mainMode_inputL.getWidth()+5, mainMode_inputL.getY(), 290-mainMode_inputL.getWidth(), mainMode_inputT.getPreferredSize().height);
        mainMode_outputL.setBounds(5, mainMode_inputL.getY()+mainMode_inputL.getHeight()+5, mainMode_outputL.getPreferredSize().width, mainMode_inputT.getHeight());
        mainMode_outputT.setBounds(mainMode_inputT.getX(), mainMode_outputL.getY(), mainMode_inputT.getWidth(), mainMode_inputT.getHeight());
        mainMode_processBt.setBounds(5, mainMode_outputL.getY()+mainMode_outputL.getHeight()+20, 90, mainMode_processBt.getPreferredSize().height);
        mainMode_editBt.setBounds(105, mainMode_processBt.getY(), mainMode_processBt.getWidth(), mainMode_processBt.getHeight());
        mainMode_exitBt.setBounds(205, mainMode_processBt.getY(), mainMode_processBt.getWidth(), mainMode_processBt.getHeight());

        mainMode_headL.setHorizontalAlignment(JLabel.CENTER);
        mainMode_inputL.setVerticalAlignment(JLabel.CENTER);
        mainMode_outputL.setVerticalAlignment(JLabel.CENTER);

        mainMode_inputT.setText(input);
        mainMode_outputT.setText(output);

        mainMode_inputT.setEditable(false);
        mainMode_outputT.setEditable(false);

        add(mainMode_headL);
        add(mainMode_inputL);
        add(mainMode_inputT);
        add(mainMode_outputL);
        add(mainMode_outputT);
        add(mainMode_processBt);
        add(mainMode_editBt);
        add(mainMode_exitBt);
        add(mainMode_status);
        mainMode_status.add(mainMode_statusL);
    }

    private void newEditWindow() {

        // Edit Mode Frame Attributes
        editMode_Frame.setTitle("Edit Inventory");
        editMode_Frame.setSize(300,300);
        editMode_Frame.setLocationRelativeTo(null);
        editMode_Frame.setLayout(null);
        editMode_Frame.setResizable(false);
    }

    private void setEditWindow() {

        // Edit Mode Size & Positions
        editMode_Header.setBounds(5,5,290,40);
        editMode_addEntry.setBounds(5, editMode_Header.getY()+45, editMode_Header.getWidth(),editMode_Header.getHeight());
        editMode_modifyEntry.setBounds(5, editMode_addEntry.getY()+45, editMode_Header.getWidth(),editMode_Header.getHeight());
        editMode_viewEntry.setBounds(5, editMode_modifyEntry.getY()+45, editMode_Header.getWidth(),editMode_Header.getHeight());
        editMode_deleteEntry.setBounds(5, editMode_viewEntry.getY()+45, editMode_Header.getWidth(), editMode_Header.getHeight());
        editMode_gobackBt.setBounds(5, editMode_deleteEntry.getY()+45, editMode_Header.getWidth(), editMode_Header.getHeight());

        editMode_Header.setHorizontalAlignment(JLabel.CENTER);

        editMode_Frame.add(editMode_Header);
        editMode_Frame.add(editMode_addEntry);
        editMode_Frame.add(editMode_modifyEntry);
        editMode_Frame.add(editMode_viewEntry);
        editMode_Frame.add(editMode_deleteEntry);
        editMode_Frame.add(editMode_gobackBt);
    }

    private void newAddWindow() {

        // Add Mode Frame Attributes
        addMode_Frame.setTitle("Add Record");
        addMode_Frame.setSize(300,300);
        addMode_Frame.setLocationRelativeTo(null);
        addMode_Frame.setLayout(null);
        addMode_Frame.setResizable(false);
    }

    private void setAddWindow() {

        // Add Mode Size & Positions
        addMode_status.setBorder(new BevelBorder(BevelBorder.LOWERED));
        addMode_status.setBounds(0, 250, 300, 30);
        addMode_Header.setBounds(5, 5, 290, 40);

        addMode_recNameL.setBounds(5,addMode_Header.getY()+addMode_Header.getHeight()+5, addMode_recDescL.getPreferredSize().width, addMode_recNameL.getPreferredSize().height+5);
        addMode_recDescL.setBounds(5,addMode_recNameL.getY()+addMode_recNameL.getHeight()+5, addMode_recNameL.getWidth(), addMode_recNameL.getHeight());
        addMode_recQtyL.setBounds(5,addMode_recDescL.getY()+addMode_recDescL.getHeight()+5, addMode_recNameL.getWidth(), addMode_recNameL.getHeight());
        addMode_recPriceL.setBounds(5,addMode_recQtyL.getY()+addMode_recQtyL.getHeight()+5, addMode_recNameL.getWidth(), addMode_recNameL.getHeight());
        addMode_recCostL.setBounds(5,addMode_recPriceL.getY()+addMode_recPriceL.getHeight()+5, addMode_recNameL.getWidth(), addMode_recNameL.getHeight());
        addMode_recPurchL.setBounds(5,addMode_recCostL.getY()+addMode_recCostL.getHeight()+5, addMode_recNameL.getWidth(), addMode_recNameL.getHeight());

        addMode_recNameT.setBounds(addMode_recNameL.getX()+addMode_recNameL.getWidth()+5, addMode_recNameL.getY(), 285-addMode_recNameL.getWidth(), addMode_recNameL.getHeight());
        addMode_recDescT.setBounds(addMode_recNameT.getX(), addMode_recDescL.getY(), addMode_recNameT.getWidth(), addMode_recNameT.getHeight());
        addMode_recQtyT.setBounds(addMode_recNameT.getX(), addMode_recQtyL.getY(), addMode_recNameT.getWidth(), addMode_recNameT.getHeight());
        addMode_recPriceT.setBounds(addMode_recNameT.getX(), addMode_recPriceL.getY(), addMode_recNameT.getWidth(), addMode_recNameT.getHeight());
        addMode_recCostT.setBounds(addMode_recNameT.getX(), addMode_recCostL.getY(), addMode_recNameT.getWidth(), addMode_recNameT.getHeight());
        addMode_recPurchT.setBounds(addMode_recNameT.getX(), addMode_recPurchL.getY(), addMode_recNameT.getWidth(), addMode_recNameT.getHeight());

        addMode_recAddL.setBounds(15, addMode_recPurchL.getY()+addMode_recPurchL.getHeight()+5,120,addMode_recAddL.getPreferredSize().height);
        addMode_gobackBt.setBounds(addMode_recAddL.getX()+addMode_recAddL.getWidth()+30,addMode_recAddL.getY(),addMode_recAddL.getWidth(),addMode_recAddL.getHeight());

        addMode_Frame.add(addMode_recNameL);
        addMode_Frame.add(addMode_recDescL);
        addMode_Frame.add(addMode_recQtyL);
        addMode_Frame.add(addMode_recPriceL);
        addMode_Frame.add(addMode_recCostL);
        addMode_Frame.add(addMode_recPurchL);

        addMode_Header.setHorizontalAlignment(JLabel.CENTER);

        addMode_recNameL.setVerticalAlignment(JLabel.CENTER);
        addMode_recDescL.setVerticalAlignment(JLabel.CENTER);
        addMode_recQtyL.setVerticalAlignment(JLabel.CENTER);
        addMode_recPriceL.setVerticalAlignment(JLabel.CENTER);
        addMode_recCostL.setVerticalAlignment(JLabel.CENTER);
        addMode_recPurchL.setVerticalAlignment(JLabel.CENTER);

        addMode_Frame.add(addMode_Header);

        addMode_Frame.add(addMode_recNameT);
        addMode_Frame.add(addMode_recDescT);
        addMode_Frame.add(addMode_recQtyT);
        addMode_Frame.add(addMode_recPriceT);
        addMode_Frame.add(addMode_recCostT);
        addMode_Frame.add(addMode_recPurchT);

        addMode_Frame.add(addMode_recAddL);
        addMode_Frame.add(addMode_gobackBt);
        addMode_Frame.add(addMode_status);
        addMode_status.add(addMode_statusL);
    }

    private void newModWindow() {

        // Modify Mode Frame Attributes
        modMode_Frame.setTitle("Modify Record");
        modMode_Frame.setSize(300,300);
        modMode_Frame.setLocationRelativeTo(null);
        modMode_Frame.setLayout(null);
        modMode_Frame.setResizable(false);
    }

    private void setModWindow() {

        // Modify Mode Size & Positions
        modMode_status.setBorder(new BevelBorder(BevelBorder.LOWERED));
        modMode_status.setBounds(0, 250, 300, 30);

        modMode_Header.setBounds(5,5,290,40);
        modMode_editRecL.setBounds(5, modMode_Header.getY()+modMode_Header.getHeight()+5, modMode_editRecL.getPreferredSize().width,modMode_editRecT.getPreferredSize().height);
        modMode_editRecT.setBounds(modMode_editRecL.getX()+modMode_editRecL.getWidth()+5, modMode_editRecL.getY(),290-modMode_editRecL.getWidth()-modMode_editRecL.getX(), modMode_editRecL.getHeight());
        modMode_modBt.setBounds(20,modMode_editRecL.getY()+modMode_editRecL.getHeight()+5, 110, modMode_modBt.getPreferredSize().height);
        modMode_gobackBt.setBounds(170, modMode_modBt.getY(),110, modMode_modBt.getHeight());

        modMode_Header.setHorizontalAlignment(JLabel.CENTER);
        modMode_editRecL.setVerticalAlignment(JLabel.CENTER);

        modMode_Frame.add(modMode_Header);
        modMode_Frame.add(modMode_editRecL);
        modMode_Frame.add(modMode_editRecT);
        modMode_Frame.add(modMode_modBt);
        modMode_Frame.add(modMode_gobackBt);
        modMode_Frame.add(modMode_status);
        modMode_status.add(modMode_statusL);
    }

    private void newMod2Window() {

        // Modify Mode #2 Frame Attributes
        modMode2_Frame.setTitle("Modify Record");
        modMode2_Frame.setSize(300,300);
        modMode2_Frame.setLocationRelativeTo(null);
        modMode2_Frame.setLayout(null);
        modMode2_Frame.setResizable(false);
    }

    private void setMod2Window() {

        // Modify Mode #2 Size & Positions
        modMode2_status.setBorder(new BevelBorder(BevelBorder.LOWERED));
        modMode2_status.setBounds(0, 250, 300, 30);
        modMode2_Header.setBounds(5, 5, 290, 40);

        modMode_recNameL.setBounds(5,modMode2_Header.getY()+modMode2_Header.getHeight()+5, modMode_recDescL.getPreferredSize().width, modMode_recNameL.getPreferredSize().height+5);
        modMode_recDescL.setBounds(5,modMode_recNameL.getY()+modMode_recNameL.getHeight()+5, modMode_recNameL.getWidth(), modMode_recNameL.getHeight());
        modMode_recQtyL.setBounds(5,modMode_recDescL.getY()+modMode_recDescL.getHeight()+5, modMode_recNameL.getWidth(), modMode_recNameL.getHeight());
        modMode_recPriceL.setBounds(5,modMode_recQtyL.getY()+modMode_recQtyL.getHeight()+5, modMode_recNameL.getWidth(), modMode_recNameL.getHeight());
        modMode_recCostL.setBounds(5,modMode_recPriceL.getY()+modMode_recPriceL.getHeight()+5, modMode_recNameL.getWidth(), modMode_recNameL.getHeight());
        modMode_recPurchL.setBounds(5,modMode_recCostL.getY()+modMode_recCostL.getHeight()+5, modMode_recNameL.getWidth(), modMode_recNameL.getHeight());

        modMode_recNameT.setBounds(modMode_recNameL.getX()+modMode_recNameL.getWidth()+5, modMode_recNameL.getY(), 285-modMode_recNameL.getWidth(), modMode_recNameL.getHeight());
        modMode_recDescT.setBounds(modMode_recNameT.getX(), modMode_recDescL.getY(), modMode_recNameT.getWidth(), modMode_recNameT.getHeight());
        modMode_recQtyT.setBounds(modMode_recNameT.getX(), modMode_recQtyL.getY(), modMode_recNameT.getWidth(), modMode_recNameT.getHeight());
        modMode_recPriceT.setBounds(modMode_recNameT.getX(), modMode_recPriceL.getY(), modMode_recNameT.getWidth(), modMode_recNameT.getHeight());
        modMode_recCostT.setBounds(modMode_recNameT.getX(), modMode_recCostL.getY(), modMode_recNameT.getWidth(), modMode_recNameT.getHeight());
        modMode_recPurchT.setBounds(modMode_recNameT.getX(), modMode_recPurchL.getY(), modMode_recNameT.getWidth(), modMode_recNameT.getHeight());

        modMode_recAddL.setBounds(15, modMode_recPurchL.getY()+modMode_recPurchL.getHeight()+5,120,modMode_recAddL.getPreferredSize().height);
        modMode2_gobackBt.setBounds(modMode_recAddL.getX()+modMode_recAddL.getWidth()+30,modMode_recAddL.getY(),modMode_recAddL.getWidth(),modMode_recAddL.getHeight());

        modMode2_Frame.add(modMode_recNameL);
        modMode2_Frame.add(modMode_recDescL);
        modMode2_Frame.add(modMode_recQtyL);
        modMode2_Frame.add(modMode_recPriceL);
        modMode2_Frame.add(modMode_recCostL);
        modMode2_Frame.add(modMode_recPurchL);

        modMode2_Header.setHorizontalAlignment(JLabel.CENTER);

        modMode_recNameL.setVerticalAlignment(JLabel.CENTER);
        modMode_recDescL.setVerticalAlignment(JLabel.CENTER);
        modMode_recQtyL.setVerticalAlignment(JLabel.CENTER);
        modMode_recPriceL.setVerticalAlignment(JLabel.CENTER);
        modMode_recCostL.setVerticalAlignment(JLabel.CENTER);
        modMode_recPurchL.setVerticalAlignment(JLabel.CENTER);

        modMode2_Frame.add(modMode2_Header);

        modMode2_Frame.add(modMode_recNameT);
        modMode2_Frame.add(modMode_recDescT);
        modMode2_Frame.add(modMode_recQtyT);
        modMode2_Frame.add(modMode_recPriceT);
        modMode2_Frame.add(modMode_recCostT);
        modMode2_Frame.add(modMode_recPurchT);

        modMode2_Frame.add(modMode_recAddL);
        modMode2_Frame.add(modMode2_gobackBt);
        modMode2_Frame.add(modMode2_status);
        modMode2_status.add(modMode2_statusL);
    }

    private void newViewWindow() {

        // View Mode Frame Attribute
        viewMode_Frame.setTitle("View Record");
        viewMode_Frame.setSize(300,300);
        viewMode_Frame.setLocationRelativeTo(null);
        viewMode_Frame.setLayout(null);
        viewMode_Frame.setResizable(false);
    }

    private void setViewWindow() {

        // View Mode Size & Positions
        viewMode_status.setBorder(new BevelBorder(BevelBorder.LOWERED));
        viewMode_status.setBounds(0, 250, 300, 30);

        viewMode_Header.setBounds(5,5,290,40);
        viewMode_viewRecL.setBounds(5, viewMode_Header.getY()+viewMode_Header.getHeight()+5, viewMode_viewRecL.getPreferredSize().width,viewMode_viewRecT.getPreferredSize().height);
        viewMode_viewRecT.setBounds(viewMode_viewRecL.getX()+viewMode_viewRecL.getWidth()+5, viewMode_viewRecL.getY(),290-viewMode_viewRecL.getWidth()-viewMode_viewRecL.getX(),viewMode_viewRecL.getHeight());
        viewMode_viewBt.setBounds(20,viewMode_viewRecL.getY()+viewMode_viewRecL.getHeight()+5, 110, viewMode_viewBt.getPreferredSize().height);
        viewMode_gobackBt.setBounds(170, viewMode_viewBt.getY(),110, viewMode_viewBt.getHeight());

        viewMode_viewRecL.setVerticalAlignment(JLabel.CENTER);
        viewMode_Header.setHorizontalAlignment(JLabel.CENTER);

        viewMode_Frame.add(viewMode_Header);
        viewMode_Frame.add(viewMode_viewRecL);
        viewMode_Frame.add(viewMode_viewRecT);
        viewMode_Frame.add(viewMode_viewBt);
        viewMode_Frame.add(viewMode_gobackBt);
        viewMode_Frame.add(viewMode_status);
        viewMode_status.add(viewMode_statusL);
    }

    private void newView2Window() {

        // View Mode #2 Frame Attributes
        viewMode2_Frame.setTitle("View Record");
        viewMode2_Frame.setSize(300,300);
        viewMode2_Frame.setLocationRelativeTo(null);
        viewMode2_Frame.setLayout(null);
        viewMode2_Frame.setResizable(false);
    }

    private void setView2Window() {

        // View Mode #2 Size & Positions
        viewMode2_Header.setBounds(5, 5, 290, 40);

        viewMode_recNameL.setBounds(5,viewMode2_Header.getY()+viewMode2_Header.getHeight()+5, viewMode_recDescL.getPreferredSize().width, viewMode_recNameL.getPreferredSize().height+5);
        viewMode_recDescL.setBounds(5,viewMode_recNameL.getY()+viewMode_recNameL.getHeight()+5, viewMode_recNameL.getWidth(), viewMode_recNameL.getHeight());
        viewMode_recQtyL.setBounds(5,viewMode_recDescL.getY()+viewMode_recDescL.getHeight()+5, viewMode_recNameL.getWidth(), viewMode_recNameL.getHeight());
        viewMode_recPriceL.setBounds(5,viewMode_recQtyL.getY()+viewMode_recQtyL.getHeight()+5, viewMode_recNameL.getWidth(), viewMode_recNameL.getHeight());
        viewMode_recCostL.setBounds(5,viewMode_recPriceL.getY()+viewMode_recPriceL.getHeight()+5, viewMode_recNameL.getWidth(), viewMode_recNameL.getHeight());
        viewMode_recPurchL.setBounds(5,viewMode_recCostL.getY()+viewMode_recCostL.getHeight()+5, viewMode_recNameL.getWidth(), viewMode_recNameL.getHeight());
        viewMode_recModifiedL.setBounds(5, viewMode_recPurchL.getY()+viewMode_recPurchL.getHeight()+5, viewMode_recNameL.getWidth(), viewMode_recNameL.getHeight());

        viewMode_recNameT.setBounds(viewMode_recNameL.getX()+viewMode_recNameL.getWidth()+5, viewMode_recNameL.getY(), 285-viewMode_recNameL.getWidth(), viewMode_recNameL.getHeight());
        viewMode_recDescT.setBounds(viewMode_recNameT.getX(), viewMode_recDescL.getY(), viewMode_recNameT.getWidth(), viewMode_recNameT.getHeight());
        viewMode_recQtyT.setBounds(viewMode_recNameT.getX(), viewMode_recQtyL.getY(), viewMode_recNameT.getWidth(), viewMode_recNameT.getHeight());
        viewMode_recPriceT.setBounds(viewMode_recNameT.getX(), viewMode_recPriceL.getY(), viewMode_recNameT.getWidth(), viewMode_recNameT.getHeight());
        viewMode_recCostT.setBounds(viewMode_recNameT.getX(), viewMode_recCostL.getY(), viewMode_recNameT.getWidth(), viewMode_recNameT.getHeight());
        viewMode_recPurchT.setBounds(viewMode_recNameT.getX(), viewMode_recPurchL.getY(), viewMode_recNameT.getWidth(), viewMode_recNameT.getHeight());
        viewMode_recModifiedT.setBounds(viewMode_recNameT.getX(), viewMode_recModifiedL.getY(), viewMode_recNameT.getWidth(), viewMode_recNameT.getHeight());

        viewMode2_gobackBt.setBounds(15, viewMode_recModifiedL.getY()+viewMode_recModifiedL.getHeight()+5,120,viewMode2_gobackBt.getPreferredSize().height);

        viewMode2_Frame.add(viewMode_recNameL);
        viewMode2_Frame.add(viewMode_recDescL);
        viewMode2_Frame.add(viewMode_recQtyL);
        viewMode2_Frame.add(viewMode_recPriceL);
        viewMode2_Frame.add(viewMode_recCostL);
        viewMode2_Frame.add(viewMode_recPurchL);
        viewMode2_Frame.add(viewMode_recModifiedL);

        viewMode2_Header.setHorizontalAlignment(JLabel.CENTER);

        viewMode_recNameL.setVerticalAlignment(JLabel.CENTER);
        viewMode_recDescL.setVerticalAlignment(JLabel.CENTER);
        viewMode_recQtyL.setVerticalAlignment(JLabel.CENTER);
        viewMode_recPriceL.setVerticalAlignment(JLabel.CENTER);
        viewMode_recCostL.setVerticalAlignment(JLabel.CENTER);
        viewMode_recPurchL.setVerticalAlignment(JLabel.CENTER);
        viewMode_recModifiedL.setVerticalAlignment(JLabel.CENTER);

        viewMode2_Frame.add(viewMode2_Header);

        viewMode2_Frame.add(viewMode_recNameT);
        viewMode2_Frame.add(viewMode_recDescT);
        viewMode2_Frame.add(viewMode_recQtyT);
        viewMode2_Frame.add(viewMode_recPriceT);
        viewMode2_Frame.add(viewMode_recCostT);
        viewMode2_Frame.add(viewMode_recPurchT);
        viewMode2_Frame.add(viewMode_recModifiedT);

        viewMode2_Frame.add(viewMode2_gobackBt);
    }

    private void newDeleteWindow() {

        // Delete Mode Frame Attributes
        deleteMode_Frame.setTitle("Delete Record");
        deleteMode_Frame.setSize(300,300);
        deleteMode_Frame.setLocationRelativeTo(null);
        deleteMode_Frame.setLayout(null);
        deleteMode_Frame.setResizable(false);
    }

    private void setDeleteWindow() {

        // Delete Mode Size & Positions
        deleteMode_status.setBorder(new BevelBorder(BevelBorder.LOWERED));
        deleteMode_status.setBounds(0, 250, 300, 30);

        deleteMode_Header.setBounds(5,5,290,40);
        deleteMode_deleteRecL.setBounds(5, deleteMode_Header.getY()+deleteMode_Header.getHeight()+5, deleteMode_deleteRecL.getPreferredSize().width, deleteMode_deleteRecT.getPreferredSize().height);
        deleteMode_deleteRecT.setBounds(deleteMode_deleteRecL.getX()+deleteMode_deleteRecL.getWidth(), deleteMode_deleteRecL.getY(), 290-deleteMode_deleteRecL.getWidth()-deleteMode_deleteRecL.getX(), deleteMode_deleteRecL.getHeight());
        deleteMode_deleteBt.setBounds(20, deleteMode_deleteRecL.getY()+deleteMode_deleteRecL.getHeight()+5, 110, deleteMode_deleteBt.getPreferredSize().height);
        deleteMode_gobackBt.setBounds(170, deleteMode_deleteBt.getY(), 110, deleteMode_deleteBt.getHeight());

        deleteMode_deleteRecL.setVerticalAlignment(JLabel.CENTER);
        deleteMode_Header.setHorizontalAlignment(JLabel.CENTER);

        deleteMode_Frame.add(deleteMode_Header);
        deleteMode_Frame.add(deleteMode_deleteRecL);
        deleteMode_Frame.add(deleteMode_deleteRecT);
        deleteMode_Frame.add(deleteMode_deleteBt);
        deleteMode_Frame.add(deleteMode_gobackBt);
        deleteMode_Frame.add(deleteMode_status);
        deleteMode_status.add(deleteMode_statusL);
    }

    private void setActions() {

        // --- Window Listeners BEGIN

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        editMode_Frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        addMode_Frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        modMode_Frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        modMode2_Frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        viewMode_Frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        viewMode2_Frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        deleteMode_Frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // --- Window Listeners END

        // --- Main Mode Button Listeners BEGIN

        // Edit Mode Button
        mainMode_editBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                newEditWindow();
                setEditWindow();
                editMode_Frame.setVisible(true);
            }
        });

        // Process Button
        mainMode_processBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newDBInstance.writeFile(products,outputName);
                mainMode_statusL.setText("<html><b>Output File Created: " + outputName +"</b></html>");
            }
        });

        // Exit Button
        mainMode_exitBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // --- Main Mode Button Listeners END

        // --- Edit Mode Button Listeners BEGIN

        // Add Entry Button
        editMode_addEntry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editMode_Frame.setVisible(false);
                newAddWindow();
                setAddWindow();
                addMode_Frame.setVisible(true);
            }
        });

        // Modify Entry Button
        editMode_modifyEntry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modMode_statusL.setText("Status...");
                modMode_editRecT.setText("");
                editMode_Frame.setVisible(false);
                newModWindow();
                setModWindow();
                modMode_Frame.setVisible(true);
            }
        });

        // View Entry Button
        editMode_viewEntry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewMode_statusL.setText("Status...");
                viewMode_viewRecT.setText("");
                editMode_Frame.setVisible(false);
                newViewWindow();
                setViewWindow();
                viewMode_Frame.setVisible(true);
            }
        });

        // Delete Entry Button
        editMode_deleteEntry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteMode_statusL.setText("Status...");
                deleteMode_deleteRecT.setText("");
                editMode_Frame.setVisible(false);
                newDeleteWindow();
                setDeleteWindow();
                deleteMode_Frame.setVisible(true);
            }
        });

        // Go Back Button
        editMode_gobackBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainMode_statusL.setText("Status...");
                editMode_Frame.setVisible(false);
                setVisible(true);
            }
        });

        // --- Edit Mode Button Listeners END

        // --- Add Mode Button Listeners BEGIN

        // Add Record Button
        addMode_recAddL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] data = new String[7];
                data[0] = addMode_recNameT.getText();
                data[1] = addMode_recDescT.getText();
                data[2] = addMode_recQtyT.getText();
                data[3] = addMode_recPriceT.getText();
                data[4] = addMode_recCostT.getText();
                data[5] = addMode_recPurchT.getText();
                data[6] = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                LinkedHashMap<String,String> product = new LinkedHashMap<String, String>();
                product.put ("itemDesc" , data[1]);
                product.put ("itemQty" , data[2]);
                product.put ("itemPrice" , data[3]);
                product.put ("itemCost" , data[4]);
                product.put ("datePurchased" , data[5]);
                product.put ("dateModified" , data[6]);

                products.put(data[0], product);
                addMode_statusL.setText("Product Added...");

                addMode_recNameT.setText("");
                addMode_recDescT.setText("");
                addMode_recQtyT.setText("");
                addMode_recPriceT.setText("");
                addMode_recCostT.setText("");
                addMode_recPurchT.setText("");
            }
        });

        // Go Back Button
        addMode_gobackBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addMode_Frame.setVisible(false);
                editMode_Frame.setVisible(true);
            }
        });

        // --- Add Mode Button Listeners END

        // --- Modify Mode Button Listeners BEGIN

        // Modify Button
        modMode_modBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String x = modMode_editRecT.getText().toUpperCase();
                if (products.containsKey(x)) {
                    modMode_Frame.setVisible(false);
                    newMod2Window();
                    setMod2Window();
                    modMode2_Frame.setVisible(true);

                    modMode_recNameT.setText(x);
                    modMode_recDescT.setText(products.get(x).get("itemDesc"));
                    modMode_recQtyT.setText(products.get(x).get("itemQty"));
                    modMode_recPriceT.setText(products.get(x).get("itemPrice"));
                    modMode_recCostT.setText(products.get(x).get("itemCost"));
                    modMode_recPurchT.setText(products.get(x).get("datePurchased"));
                }
                else
                    modMode_statusL.setText("Product Not Found...");
            }
        });

        // Modify Commit Button
        modMode_recAddL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] data = new String[7];
                data[0] = modMode_recNameT.getText();
                data[1] = modMode_recDescT.getText();
                data[2] = modMode_recQtyT.getText();
                data[3] = modMode_recPriceT.getText();
                data[4] = modMode_recCostT.getText();
                data[5] = modMode_recPurchT.getText();
                data[6] = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                LinkedHashMap<String,String> product = new LinkedHashMap<String, String>();
                product.put ("itemDesc" , data[1]);
                product.put ("itemQty" , data[2]);
                product.put ("itemPrice" , data[3]);
                product.put ("itemCost" , data[4]);
                product.put ("datePurchased" , data[5]);
                product.put ("dateModified" , data[6]);

                products.put(data[0], product);

                modMode2_statusL.setText("Product Modified...");
            }
        });

        // Go Back Button
        modMode_gobackBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modMode_Frame.setVisible(false);
                editMode_Frame.setVisible(true);
            }
        });

        // Go Back Button (From Secondary Modify Window)
        modMode2_gobackBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modMode_statusL.setText("Status...");
                modMode2_statusL.setText("Status...");
                modMode2_Frame.setVisible(false);
                modMode_Frame.setVisible(true);
                modMode_editRecT.setText("");
            }
        });

        // --- Modify Mode Button Listeners END

        // --- View Mode Button Listeners BEGIN

        // View Button
        viewMode_viewBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String x = viewMode_viewRecT.getText().toUpperCase();
                if (products.containsKey(x)) {
                    viewMode_Frame.setVisible(false);
                    newView2Window();
                    setView2Window();
                    viewMode2_Frame.setVisible(true);

                    viewMode_recNameT.setText(x);
                    viewMode_recDescT.setText(products.get(x).get("itemDesc"));
                    viewMode_recQtyT.setText(products.get(x).get("itemQty"));
                    viewMode_recPriceT.setText(products.get(x).get("itemPrice"));
                    viewMode_recCostT.setText(products.get(x).get("itemCost"));
                    viewMode_recPurchT.setText(products.get(x).get("datePurchased"));
                    viewMode_recModifiedT.setText(products.get(x).get("dateModified"));
                }
                else
                    viewMode_statusL.setText("Product Not Found...");
            }
        });

        // Go Back Button (From Inner View Window)
        viewMode2_gobackBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewMode_statusL.setText("Status...");
                viewMode2_Frame.setVisible(false);
                viewMode_Frame.setVisible(true);
                viewMode_viewRecT.setText("");
            }
        });

        // Go Back Button
        viewMode_gobackBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewMode_Frame.setVisible(false);
                editMode_Frame.setVisible(true);
            }
        });

        // --- View Mode Button Listeners END

        // --- Delete Mode Button Listeners BEGIN

        // Delete Button
        deleteMode_deleteBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String x = deleteMode_deleteRecT.getText().toUpperCase();
                if (products.containsKey(x)) {
                    products.remove(x);
                    deleteMode_statusL.setText("Product Deleted...");
                }
                else
                    deleteMode_statusL.setText("Product Not Found...");
            }
        });

        // Go Back Button
        deleteMode_gobackBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteMode_Frame.setVisible(false);
                editMode_Frame.setVisible(true);
            }
        });

        // --- Delete Mode Button Listeners END
    }

    public DBGui(String input, String output) {

        // GUI Instantiation
        inputName = input;
        outputName = output;

        products = newDBInstance.readFile(inputName);

        setTitle("Inventory Database");
        setSize(300,300);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        setComponents(input, output);
        setActions();
    }
}
