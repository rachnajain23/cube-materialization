import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileChooser extends JFrame implements ActionListener {

    public void actionPerformed(ActionEvent actionEvent) {
        String com = actionEvent.getActionCommand();
        System.out.println("got the string open");
        if (com.equals("open")) {
            // create an object of JFileChooser class
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION)
            {
                String filepath = j.getSelectedFile().getAbsolutePath();
                System.out.println(filepath);
                boolean v = validateFileType(filepath);
                if(v) {
                    try {
                        SheetSelector s = new SheetSelector();
                        s.selectSheet(filepath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InvalidFormatException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("Please select proper file type");
                }
            }
            else
                System.out.println("the user cancelled the operation");
        }


    }

    private boolean validateFileType(String filepath) {
        if(filepath.endsWith(".xls") || filepath.endsWith(".xlsx"))
            return true;
        else
            return false;
    }
}
