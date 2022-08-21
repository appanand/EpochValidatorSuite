package common;

import java.io.FileInputStream;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelOperations {

	public static String readDataFromExcelForTestcase(String sheetName, String executedTestcase,
			String requiredDataColumn) {

		String url = System.getProperty("user.dir") + "//resources//testdata.xlsx";
		try {
			FileInputStream fis = new FileInputStream(url);

			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			int firstRow = sheet.getFirstRowNum();

			HashMap<String, Integer> testDataColumns = new HashMap<String, Integer>();
			for (int i = 0; i < sheet.getRow(firstRow).getLastCellNum(); i++) {

				testDataColumns.put(sheet.getRow(firstRow).getCell(i).getStringCellValue(), i);

			}
			System.out.println(testDataColumns);
			HashMap<String, Integer> testcases = new HashMap<String, Integer>();

			System.out.println("get last row num" + sheet.getLastRowNum());

			for (int i = 0; i <= sheet.getLastRowNum(); i++) {

				testcases.put(sheet.getRow(i).getCell(testDataColumns.get("testcase")).getStringCellValue(), i);

			}
			System.out.println(testcases);

			Cell dataCell = sheet.getRow(testcases.get(executedTestcase))
					.getCell(testDataColumns.get(requiredDataColumn));

			DataFormatter df = new DataFormatter();
			
			workbook.close();
			
			return df.formatCellValue(dataCell);

		} catch (Exception e) {
			System.out.println("error reading data from excel");
			e.printStackTrace();
			return "";

		}

	}

}
