package pl.kubag5.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Arrays;

public class TrainReader {

    public static void main(String[] args) {
        String url = "https://www.intercity.pl/pl/aktualna-dostepnosc-pociagow-pkp-intercity-od-13.12.2020-roku/pociagi-kursujace-od-13-12-2020/";
        String[][] trainData = getTrainsData(url);

        if (trainData != null) {
            System.out.println("Train data:");
            System.out.println(Arrays.deepToString(trainData));
        } else {
            System.out.println("No train data found.");
        }
    }

    public static String[][] getTrainsData(String url) {
        try {
            Document doc = Jsoup.connect(url).get();

            Element myTable = doc.select("table").first();
            if (myTable == null) {
                System.out.println("nie znaleziono tabeli");
                return null;
            }

            Elements rows = myTable.select("tr");

            int numColumns = 0;
            for (Element row : rows) {
                Elements columns = row.select("td");
                if (columns.size() > numColumns) {
                    numColumns = columns.size();
                }
            }

            String[][] tableData = new String[rows.size()][numColumns];

            int rowIndex = 0;
            for (Element row : rows) {
                Elements columns = row.select("td");
                for (int colIndex = 0; colIndex < columns.size(); colIndex++) {
                    tableData[rowIndex][colIndex] = columns.get(colIndex).text();
                }
                rowIndex++;
            }

            return tableData;

        } catch (IOException e) {
            System.err.println("błąd - nie znaleziono strony: " + e.getMessage());
        }
        return null;
    }
}

