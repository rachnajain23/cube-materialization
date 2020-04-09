package Junk;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class Input extends SheetSelector {
    String baseCuboid[][];
    int r, c;
    String dimensions[];
    public boolean readFromExcel(String file, int sheetNum) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet sheet = workbook.getSheetAt(sheetNum);
        r = sheet.getPhysicalNumberOfRows();
        c = sheet.getRow(0).getPhysicalNumberOfCells();

        //Storing the dimensions
        dimensions = new String[c];
        for (int i = 0; i < c; ++i)
            dimensions[i] = String.valueOf(sheet.getRow(0).getCell(i));
//        System.out.print(Arrays.toString(dimensions));

        //forming the base cuboid
        r=r-1;
        System.out.println(r + " "+ c);
        baseCuboid = new String[r][c];
        for (int i=0; i<r; ++i)
        {
            System.out.println(i);
            for(int j=0; j<c;++j){
                Cell cell = sheet.getRow(i+1).getCell(j);
                switch (cell.getCellType())
                {
                    case Cell.CELL_TYPE_NUMERIC:
                        baseCuboid[i][j]= cell.getNumericCellValue() + "";
                        break;
                    case Cell.CELL_TYPE_STRING:
                        baseCuboid[i][j]= cell.getStringCellValue();
                        break;
                }
            }
        }
        return true;
    }
}
    /**
     * Java method to write dates from Excel file in Java.
     * This method write value into .XLS file in Java.
     * @param file, name of excel file to write.
     * @throws IOException
     * @throws FileNotFoundException
     */
//    @SuppressWarnings("deprecation")
//    public static void writeIntoExcel(String file) throws FileNotFoundException, IOException{
//        Workbook book = new HSSFWorkbook();
//        Sheet sheet = book.createSheet("Birthdays");
//
//        // first row start with zero
//        Row row = sheet.createRow(0);
//
//        // we will write name and birthdates in two columns
//        // name will be String and birthday would be Date
//        // formatted as dd.mm.yyyy
//        Cell name = row.createCell(0);
//        name.setCellValue("John");
//
//        Cell birthdate = row.createCell(1);
//
//        // steps to format a cell to display date value in Excel
//        // 1. Create a DataFormat
//        // 2. Create a CellStyle
//        // 3. Set format into CellStyle
//        // 4. Set CellStyle into Cell
//        // 5. Write java.util.Date into Cell
//        DataFormat format = book.createDataFormat();
//        CellStyle dateStyle = book.createCellStyle();
//        dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
//        birthdate.setCellStyle(dateStyle);
//
//        // It's very trick method, deprecated, don't use
//        // year is from 1900, month starts with zero
//        birthdate.setCellValue(new Date(110, 10, 10));
//
//        // auto-resizing columns
//        sheet.autoSizeColumn(1);
//
//        // Now, its time to write content of Excel into File
//        book.write(new FileOutputStream(file));
//        book.close();
//    }

