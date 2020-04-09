package UI;

/**
 *
 * @author rachnajain
 */
import Pojo.Attribute;
import Pojo.Dimension;
import Pojo.StarSchema;

import javax.swing.JTextField;

public class DimensionTables extends javax.swing.JFrame {


    StarSchema globalSchema;
    Dimension d;
    int number_attri, numberDimension, numberFactsVar;

    JTextField jt[] = new JTextField[100];
    int x, y;

    public DimensionTables(StarSchema s, int numberDimension, int numberFactsVar) {
        initComponents();
        globalSchema = s;
        this.numberDimension = numberDimension;
        this.numberFactsVar = numberFactsVar;
        d = new Dimension();
        x = 100;
        y = 310;
    }

    public void insert(int number) {
        for (int i = 0; i < number; i++) {
            if (this.getHeight() <= y + 50) {
                x = x + 200;
                y = 100;
            }
            jt[i] = new JTextField();
            jt[i].setBounds(x, y, 100, 30);
            add(jt[i]);
            repaint();
            y = y + 50;

        }
    }
    public int NumDimensions(){
        NewCube abc = new NewCube();
        int a = abc.number_Dimension;
        return a;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameDimension = new javax.swing.JLabel();
        numberAttributes = new javax.swing.JLabel();
        addAttributes = new javax.swing.JButton();
        Dim = new javax.swing.JLabel();
        headDim = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        numAttr = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        nextButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dimension Table Details");

        nameDimension.setText("Name of Dimension Table:");

        numberAttributes.setText("Number of Attributes");

        addAttributes.setText("Add Attributes");
        addAttributes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAttributesActionPerformed(evt);
            }
        });

        Dim.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        Dim.setText("Dimension" + numberDimension);

        headDim.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        headDim.setText("Dimension" + this.numberDimension);

        name.setText("Name");

        numAttr.setText("Number of Attributes");

        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Name of Attributes:");

        {
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jSeparator1)
                                                    .addContainerGap())
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(headDim)
                                                            .addComponent(Dim, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                    .addComponent(numAttr, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                                                                    .addComponent(name, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(nameDimension)
                                                            .addComponent(numberAttributes))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 247, Short.MAX_VALUE)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                            .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                                                            .addComponent(jTextField2))
                                                    .addGap(111, 111, 111))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addGap(0, 0, Short.MAX_VALUE)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                            .addComponent(nextButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(addAttributes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGap(26, 26, 26))
                                            .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jLabel1)
                                                    .addGap(0, 0, Short.MAX_VALUE))))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(headDim)
                                    .addGap(39, 39, 39)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(nameDimension)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(23, 23, 23)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(numberAttributes)
                                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(11, 11, 11)
                                    .addComponent(addAttributes)
                                    .addGap(4, 4, 4)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(4, 4, 4)
                                    .addComponent(Dim)
                                    .addGap(18, 18, 18)
                                    .addComponent(name)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(numAttr)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                                    .addComponent(nextButton)
                                    .addGap(38, 38, 38))
            );
        }

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addAttributesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAttributesActionPerformed
        // TODO add your handling code here:
        String nameDim = new String();
        int r = 1;

        nameDim = this.jTextField2.getText();
        d.setName(nameDim);

        number_attri = Integer.parseInt(
                this.jTextField3.getText());

        this.headDim.setText("Dimension" + r);
        this.Dim.setText("Dimension" + r);
        this.name.setText("Name                                                           :" + nameDim);
        this.numAttr.setText("Number of Attributes                                :" + number_attri);

        insert(number_attri);

        //To get names of attributes run a for loop
        //int i = 0;
        //jt[i].getText();

    }//GEN-LAST:event_addAttributesActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        for (int i = 0; i < number_attri; i++) {
            String attribute = jt[i].getText();
            d.addAttribute(new Attribute(attribute));
        }
        globalSchema.addSingleDimension(d);
        this.dispose();
        System.out.println(numberDimension);
        if(numberDimension != 1) {
            DimensionTables obj = new DimensionTables(this.globalSchema, this.numberDimension-1, numberFactsVar);
            obj.setVisible(true);
            obj.pack();
        }
        else {
//            this.dispose();

            FactVariables f = new FactVariables(globalSchema, numberFactsVar);
            f.setVisible(true);
            f.pack();
            this.dispose();
        }

    }

    private javax.swing.JLabel Dim;
    private javax.swing.JButton addAttributes;
    private javax.swing.JLabel headDim;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel name;
    private javax.swing.JLabel nameDimension;
    private javax.swing.JButton nextButton;
    private javax.swing.JLabel numAttr;
    private javax.swing.JLabel numberAttributes;

}
