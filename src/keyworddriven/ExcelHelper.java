package keyworddriven;

import java.io.File;
import java.io.FileInputStream;

import jxl.Sheet;
import jxl.Workbook;

public class ExcelHelper {
	Workbook book;
	Sheet sh;

	// open excel to read the data
	public void openExcel(String folderName, String fileName, String sheetName) {
		try {
			FileInputStream fis = new FileInputStream(GenericHelper.getFilePath(folderName, fileName));
			book = Workbook.getWorkbook(fis);
			sh = book.getSheet(sheetName);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// get the number of rows
	public int rowCount() {
		return sh.getRows();
	}

	// get the number of columns
	public int columnCount() {
		return sh.getColumns();
	}

	// read the data from the cell
	public String readData(int rnum, int cnum) {
		return sh.getCell(cnum, rnum).getContents();
	}

	// close the book
	public void closeExcel() {
		book.close();
	}

	public static void main(String[] args) {
		ExcelHelper excel = new ExcelHelper();
		excel.openExcel("", "input.xls", "testsuite");
		int nor = excel.rowCount();
		System.out.println(nor);
		for (int i = 1; i < nor; i++) {
			System.out.print(excel.readData(i, 0)+"\t");
			System.out.println(excel.readData(i, 1));
		}
	}
}
