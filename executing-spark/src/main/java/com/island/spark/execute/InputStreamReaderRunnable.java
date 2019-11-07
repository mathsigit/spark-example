package com.island.spark.execute;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

public class InputStreamReaderRunnable implements Runnable {
    static final Logger logger = Logger.getLogger(InputStreamReaderRunnable.class);

    private String name = null;
    private BufferedReader reader = null;


    public InputStreamReaderRunnable(InputStream is, String name) {
        this.name = name;
        this.reader = new BufferedReader(new InputStreamReader(is));
        logger.info("InputStreamReaderRunnable:  name=" + name);
    }

    @Override
    public void run() {
        try {
            String line = reader.readLine();
            while (line != null) {
                logger.info(line);
                line = reader.readLine();
            }
        }
        catch (Exception e) {
            logger.error("run() failed. for name="+ name, e);
        }
        finally {
            InputOutputUtil.close(reader);
        }
    }
}
