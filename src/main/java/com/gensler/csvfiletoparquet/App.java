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
  
  private static final int fileSizeMegs = 1000;

    public static void main( String[] args ) throws Exception
    {
        File csvTestFile = Utils.createTestFile(fileSizeMegs);

         File parquetTestFile = Utils.getParquetOutputFile("perf", String.valueOf(fileSizeMegs), true);
         long startTime = System.currentTimeMillis();
         ConvertUtils.convertCsvToParquet(csvTestFile, parquetTestFile);
         long endTime = System.currentTimeMillis();

         long totalTime = (endTime - startTime);
         LOG.info("Write Time: " + totalTime ); 

         Utils.writePerfResult("write", totalTime);

         LOG.info("Time taken to write " + fileSizeMegs + " sized csv file : " + fileSizeMegs);

         assertTrue(totalTime < 60000);
    
        // It should not be slower than previous versions
        for(String version : Utils.getAllPreviousVersionDirs()) {
          long totalTimeForVersion = Utils.readPerfResult(version, "write");
          LOG.info("Time taken to write with version "+ version + ": " + totalTimeForVersion);
          assertTrue(totalTime < 1.1 * totalTimeForVersion);
        }
 
    }
}
