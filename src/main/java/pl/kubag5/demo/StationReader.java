package pl.kubag5.demo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;


public class StationReader {


    public static void main(String[] args) throws FileNotFoundException {
        String[][] stationTable = getStationData("src/main/resources/Stations.xlsx");
        System.out.println("Station Table:");
        System.out.println(Arrays.deepToString(stationTable));
    }

    public static String[][] getStationData(String filePath) throws FileNotFoundException {
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            int numRows = sheet.getPhysicalNumberOfRows();
            int numColumns = sheet.getRow(0).getPhysicalNumberOfCells();

            String[][] data = new String[numRows - 3][4];
            int[] columnsToSkip = {0, 1, 2, 4, 6};

            for (int i = 3; i < numRows; i++) {
                Row row = sheet.getRow(i);
                int colIndexInData = 0;

                for (int j = 0; j < numColumns; j++) {
                    int finalJ = j;
                    if (Arrays.stream(columnsToSkip).anyMatch(skipCol -> skipCol == finalJ)) {
                        continue;
                    }

                    Cell cell = row.getCell(j);

                    if (cell == null) {
                        data[i - 3][colIndexInData] = "unknown";
                    } else {
                        switch (cell.getCellType()) {
                            case STRING -> {
                                String stringDataCell = cell.getStringCellValue();
                                data[i - 3][colIndexInData] = switch (stringDataCell) {
                                    case "TAK" -> "true";
                                    case "NIE" -> "false";
                                    default -> stringDataCell;
                                };
                            }
                            case NUMERIC -> {
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    data[i - 3][colIndexInData] = cell.getDateCellValue().toString();
                                } else {
                                    data[i - 3][colIndexInData] = String.valueOf(cell.getNumericCellValue());
                                }
                            }
                            case BOOLEAN -> data[i - 3][colIndexInData] = String.valueOf(cell.getBooleanCellValue());
                            default -> data[i - 3][colIndexInData] = "unknown";
                        }
                    }
                    colIndexInData++;
                }
            }

            return data;

        } catch (IOException e) {
            System.err.println("Error reading station data: " + e.getMessage());
        }

        return null;
    }




}
