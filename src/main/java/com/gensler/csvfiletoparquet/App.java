/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gensler.csvfiletoparquet;

import java.io.File;
import static org.junit.Assert.assertTrue;
import parquet.Log;


/**
 *
 * @author Eric Theis <eric_theis@gensler.com>
 */
public class App {
    private static final Log LOG = Log.getLog(App.class);

    private static final String testPath = "/Users/10843/Develop/MarketingFiles/";
    private static final String testFile = "indexer_file_test";

    public static void main( String[] args ) throws Exception
    {
        File csvTestFile = new File(testPath, testFile + ".csv");

        File parquetTestFile = getParquetOutputFile(true);

        long startTime = System.currentTimeMillis();

        ConvertUtils.convertCsvToParquet(csvTestFile, parquetTestFile);

        long endTime = System.currentTimeMillis();

        long totalTime = (endTime - startTime);
        LOG.info("Write Time: " + totalTime ); 

    }


    public static File getParquetOutputFile(boolean deleteIfExists) {
        File outputFile = new File(testPath, testFile + ".parquet");
        outputFile.getParentFile().mkdirs();
        if(deleteIfExists) {
            outputFile.delete();
        }

        return outputFile;
    }
    
}
