package UI.CuboidSpecification;

import Pojo.Schema.Attribute;
import Services.CuboidSpecService;
import UI.SchemaCreation.FirstPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

public class GenSecond extends JFrame implements ActionListener {
    private JList leftlist;
    private JList rightlist;

    DefaultListModel available, selected;

    private JButton addbutton;
    private JButton createConfig;
    private JLabel totalLabel;
    String schemaName;
    JLabel customName;
    JTextField customSchemaName;
    CuboidSpecService cs;


    public GenSecond(String s) {

        super("Cuboid Specification");
        schemaName = s;

        setLayout(new FlowLayout(FlowLayout.CENTER,80,50));

        cs = new CuboidSpecService(schemaName);
        LinkedHashMap<Attribute, String> attr = cs.getAttributesService();

         available = new DefaultListModel();
         selected = new DefaultListModel();

        System.out.println(attr);

        Vector<ListItem> items = new Vector<>();
        for (Map.Entry<Attribute, String> entry : attr.entrySet()) {
            items.add(new ListItem(entry.getKey(), entry.getValue()));
        }

        for(int i = 0; i<items.size();i++){
            available.addElement(items.get(i));
        }

        JLabel r = new JLabel("                                   Name of the Attributes                                    Selected Attributes                                         ");
        r.setFont(new Font("Arial", Font.PLAIN, 18));
        add(r);

        leftlist = new JList(available);
        leftlist.setVisibleRowCount(10);
        leftlist.setFixedCellWidth(200);
        leftlist.setFixedCellHeight(20);
        leftlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        add(new JScrollPane(leftlist));

        addbutton = new JButton("ADD");
        addbutton.addActionListener(this);
        add(addbutton);

        rightlist = new JList(selected);
        rightlist.setVisibleRowCount(10);
        rightlist.setFixedCellWidth(200);
        rightlist.setFixedCellHeight(20);
        add(new JScrollPane(rightlist));



        customName = new JLabel("Enter the name of the specification");
        add(customName);


        JLabel blank = new JLabel("          ");
        add(blank);

        customSchemaName = new JTextField(18);
        add(customSchemaName);

        createConfig = new JButton("Create specification");
        createConfig.addActionListener(this);
        add(createConfig);




    }
    public void actionPerformed(ActionEvent e){

        int p;
        if(e.getSource() == addbutton){
            //to preserve the previously selected list items

            int[] fromindex = leftlist.getSelectedIndices();
            Object[] from = leftlist.getSelectedValues();

            for(p=0; p<from.length;p++){
                selected.addElement(from[p]);
            }

            int size = rightlist.getModel().getSize();
            Set objects = new LinkedHashSet();
            for (int i = 0; i < size; i++) {
                objects.add(rightlist.getModel().getElementAt(i));
                }

            objects.addAll(Arrays.asList(leftlist.getSelectedValues()));
            System.out.println();
            rightlist.setListData(objects.toArray());

            for(p = fromindex.length-1;p>=0;p--){
                available.remove(fromindex[p]);
            }


        }

        if(e.getSource() == createConfig){

            HashMap<Attribute, String> newMap = new LinkedHashMap<>();
            for (int i = 0; i <rightlist.getModel().getSize(); i++) {
                Object item =rightlist.getModel().getElementAt(i);
                ListItem listItem = (ListItem) item;
                newMap.put(listItem.getAttribute(),listItem.getName());
                System.out.println("Item = " + item);
            }

            String message = cs.checkConfigExistService(newMap);
            if(message.equalsIgnoreCase("Ready to create spec")){

                System.out.println(customSchemaName.getText());
                System.out.println(newMap);
                boolean ans = cs.writeSpecService(newMap,customSchemaName.getText());

                if(ans){
                    JOptionPane.showMessageDialog(this, "Successfully generated Lattice of Cuboid");
                    this.setVisible(false);
                    FirstPage f = new FirstPage();
                    f.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(this, "Error in generating Lattice of Cuboid");
                    this.setVisible(false);
                    try {
                        GenFirst f = new GenFirst();
                        f.setBounds(300,90,900, 600);
                        f.setVisible(true);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Config already exists");
                this.setVisible(false);
                try {
                    GenFirst f = new GenFirst();
                    f.setBounds(300,90,900, 600);
                    f.setVisible(true);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        }
    }
    public static void main(String[] args) {

        GenSecond list = new GenSecond("SchemaName");
        list.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        list.setBounds(300,90,900, 600);
        list.setVisible(true);
    }
}

class ListItem {

    private Attribute attribute;
    private String name;

    ListItem(Attribute attribute, String name) {
        this.attribute = attribute;
        this.name = name;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        // You can change this to suite the presentation of a list item
        return attribute.getName() + " - " + name;
    }
}