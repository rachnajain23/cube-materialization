package Junk;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SheetSelector implements ActionListener {

    private JList list;
    private JFrame f;

    private String filepath;
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("process")) {
            int index = list.getSelectedIndex();
            System.out.println("Index Selected: " + index);
            String s = (String) list.getSelectedValue();
            System.out.println("Value Selected: " + s);

            Input x = new Input();
            try {
                System.out.println(true==x.readFromExcel(filepath, index)
                    ? "successful base cube creation": "unsuccessful base cube creation");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            f.dispose();
//            for(int i=0; i<x.r;++i){
//                for(int j=0; j<x.c; ++j){
//                    System.out.print(x.baseCuboid[i][j]);
//                }
//                System.out.println();
//            }
        }
    }

    public void selectSheet(String file) throws IOException, InvalidFormatException {
        filepath = file;
        Workbook wb = WorkbookFactory.create(new File(file));
        List<String> sheetNames = new ArrayList<String>();
        for (int i=0; i<wb.getNumberOfSheets(); i++) {
            sheetNames.add( wb.getSheetName(i) );
        }
        f = new JFrame("Sheet List");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        list = new JList(sheetNames.toArray());
        JScrollPane scrollableList = new JScrollPane(list);
        JButton open = new JButton("process");
        JPanel listPanel = new JPanel(new FlowLayout());
        listPanel.add(scrollableList);
        listPanel.add(open);
        f.add(listPanel);
//        Container contentPane = f.getContentPane();
//        f.add(scrollableList);
//        f.add(open);

        f.setSize(500, 500);
        f.setVisible(true);
        open.addActionListener(this);
    }
}
