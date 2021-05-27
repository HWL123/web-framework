package org.example.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public final class StreamUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    /**
     * read string from inputStream
     * @param inputStream
     * @return
     */
    public static String getStringFromInputStream(InputStream inputStream){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }
        }catch (Exception e){
            LOGGER.error("read string from inputStream failure",e);
            throw new RuntimeException(e);
        }

        return sb.toString();
    }

    public static void copyStream(InputStream inputStream, OutputStream outputStream){
        byte[] buffer = new byte[4 * 1024];
        try{
            int length;
            while((length = inputStream.read(buffer,0, buffer.length)) != -1){
                outputStream.write(buffer,0, buffer.length);
            }
            outputStream.flush();
        }catch (Exception e){
            LOGGER.error("copy stream failure",e);
            throw new RuntimeException(e);
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                LOGGER.error("close stream failure",e);
                throw new RuntimeException(e);
            }

        }
    }
}
