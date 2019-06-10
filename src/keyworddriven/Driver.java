package keyworddriven;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Driver {

	public static void main(String[] args) {
		// create ExtentReports class object
		ExtentReports report = new ExtentReports(GenericHelper.getFilePath("reports", "report.html"));
		// create an object of Keywords class
		Keywords keywords = new Keywords();
		// get all the methods in Keywords class
		Method[] methods = keywords.getClass().getMethods();
		ExcelHelper tcdExcel = new ExcelHelper();
		tcdExcel.openExcel("", "input.xls", "testcase");
		ExcelHelper tsuiteExcel = new ExcelHelper();
		tsuiteExcel.openExcel("", "input.xls", "testsuite");
		int nor = tsuiteExcel.rowCount();
		for (int i = 1; i < nor; i++) {
			String tsd_tcname = tsuiteExcel.readData(i, 0);
			String runMode = tsuiteExcel.readData(i, 1);
			if (runMode.equalsIgnoreCase("yes")) {
				ExtentTest test = report.startTest(tsd_tcname);
				int rows = tcdExcel.rowCount();
				// iterate over every row
				for (int j = 1; j < rows; j++) {
					// retrieve the name of test case in test case sheet
					String tcd_tcname = tcdExcel.readData(j, 0).trim();
					if (tsd_tcname.equals(tcd_tcname)) {
						// retrieve remaining columns data from test case document
						String testStepName = tcdExcel.readData(j, 1).trim();
						String locType = tcdExcel.readData(j, 2).trim();
						String locValue = tcdExcel.readData(j, 3).trim();
						String keyword = tcdExcel.readData(j, 4).trim();
						String testData = tcdExcel.readData(j, 5).trim();
						test.log(LogStatus.INFO, testStepName + " using " + testData);
						// iterate over methods array
						for (Method method : methods) {
							if (method.getName().equals(keyword)) {
								try {
									Object result = method.invoke(keywords, locType, locValue, testData);
									if (keyword.startsWith("verify")) {
										if ((Boolean) result) {
											test.log(LogStatus.PASS, tcd_tcname +" passed");
										}else {
											test.log(LogStatus.FAIL, tcd_tcname+" failed");
											
										}
									}
									break;
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
					report.endTest(test);
				}
			}
		}
		report.flush();
		report.close();

	}

}
