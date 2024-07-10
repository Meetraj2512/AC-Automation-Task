package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CSVDataProvider {
    @DataProvider(name = "csvData")
    public Iterator<Object[]> readCSV() throws IOException, CsvValidationException {
        List<Object[]> data = new ArrayList<>();
        String filePath = "src/test/resources/Untitled spreadsheet - Sheet1.csv"; // update with your actual CSV file path
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] values;
            boolean isFirstRow = true;
            while ((values = csvReader.readNext()) != null) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue; // skip the first row
                }
                data.add(values);
            }
        }
        return data.iterator();
    }
}
