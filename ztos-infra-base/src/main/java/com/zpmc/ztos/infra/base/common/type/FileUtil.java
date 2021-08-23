package com.zpmc.ztos.infra.base.common.type;

import com.zpmc.ztos.infra.base.business.interfaces.IArgoPropertyKeys;
import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.ResourceLoader;
import org.dom4j.Document;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.*;
import java.nio.channels.FileLock;

public class FileUtil {
    private static final String DEFAULT_ENCODING = System.getProperty("file.encoding");
    private static final Logger LOGGER = Logger.getLogger((String) FileUtil.class.getName());
    private static final ResourceLoader LOADER = (ResourceLoader) new DefaultResourceLoader();
    private static String buildFolder;
    private static final String BUILD_FOLDER = "build";

    private FileUtil() {
    }

    public static File getFile(String inFileName) throws BizFailure {
        try {
            Resource resource = null;
            File file = new File(inFileName);
            if (file.exists()) {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info((Object)("file exists in the absolute path :" + inFileName));
                }
                return file;
            }
            file = new File(FileUtil.getClassPath() + File.separator + inFileName);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("Build path is " + file));
            }
            if (file.exists()) {
                LOGGER.info((Object)"file exists in the <app>/build");
                return file;
            }
            LOGGER.info((Object)"file doesn't exists in the absolute path,loading from <app>/build");
            file = new File(FileUtil.getBuildFolderPath() + inFileName);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("Build path is " + file));
            }
            if (file.exists()) {
                LOGGER.info((Object)"file exists in the <app>/build");
                return file;
            }
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("file doesn't exists in the <app>/build, loading from class path:" + inFileName));
            }
//            if ((resource = LOADER.getResource(inFileName)) == null) {
//                LOGGER.error((Object)("unable to load the resource, since the file " + inFileName + "not found in class path"));
//                throw new BizFailure(IArgoPropertyKeys.UNABLE_TO_LOAD_FILE, (Throwable)new NullPointerException(), null);
//            }
            InputStream fileStream = resource.getInputStream();
            if (fileStream == null) {
                LOGGER.error((Object)("unable to load the given file " + inFileName + " as stream"));
                throw new BizFailure(IArgoPropertyKeys.UNABLE_TO_LOAD_FILE, (Throwable)new NullPointerException(), null);
            }
            StringBuffer buffer = new StringBuffer();
            BufferedReader in = null;
            try {
                String line;
                in = new BufferedReader(new InputStreamReader(fileStream));
                while ((line = in.readLine()) != null) {
                    buffer.append(line);
                    buffer.append(System.getProperty("line.separator"));
                }
            }
            catch (IOException e) {
                LOGGER.error((Object)("unable to load the given file " + inFileName + " as stream due to " + e));
                throw new BizFailure(IArgoPropertyKeys.UNABLE_TO_LOAD_FILE, (Throwable)new NullPointerException(), null);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    }
                    catch (IOException iOException) {}
                }
            }
            String fileContent = buffer.toString();
            FileUtil.writeToFile(fileContent, file);
            return file;
        }
        catch (IOException e) {
            LOGGER.error((Object)("unable to load the given file " + inFileName + " because of " + e));
            throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.UNABLE_TO_LOAD_FILE, (Throwable)e, (Object)inFileName);
        }
    }

    public static Document loadDocumentFromClasspath(String inXmlFileName) {
        InputStream inputStream;
        DefaultResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource(inXmlFileName);
        try {
            inputStream = resource.getInputStream();
        }
        catch (IOException e) {
            LOGGER.error((Object)("FileUtil.getClassPath().." + FileUtil.getClassPath() + "..." + FileUtil.getBuildFolderPath()));
            throw BizFailure.create((String)("could not open file: " + e));
        }
//        try {
//            SAXBuilder builder = new SAXBuilder();
//            Document document = builder.build(inputStream);
//            return document;
//        }
//        catch (Exception e) {
//            throw BizFailure.create((String)("could not build JDOM Tree from file content: " + e));
//        }
//        finally {
//            try {
//                inputStream.close();
//            }
//            catch (IOException iOException) {}
//        }
        return null;
    }

    public static String getFileAsStringFromSystem(String inFileName) throws BizFailure {
        try {
            File file = new File(inFileName);
            boolean isExist = file.exists();
            if (!isExist) {
                file = FileUtil.getFile(inFileName);
            }
            return FileUtil.getFileAsString(file);
        }
        catch (Exception e) {
            LOGGER.error((Object)("unable to load the given file : " + inFileName + " as string due to " + e));
            throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.UNABLE_TO_LOAD_FILE, (Throwable)e, (Object)inFileName);
        }
    }

    public static void writeToFile(String inStr, File inFile) throws BizFailure {
        OutputStreamWriter writer = null;
        try {
            String parentPath = inFile.getParent();
            File parentDir = new File(parentPath);
            if (!parentDir.exists() && !parentDir.mkdirs()) {
                parentDir.mkdirs();
            }
            writer = new FileWriter(inFile);
            writer.write(inStr);
        }
        catch (IOException e) {
            LOGGER.error((Object)("unable to write the content to file : " + inFile + " due to " + e));
            throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.UNABLE_TO_WRITE_FILE, (Throwable)e, (Object)inFile);
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                }
                catch (IOException iOException) {}
            }
        }
    }

    public static void writeToFile(InputStream inStream, File inFile) throws BizFailure {
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            String line;
            reader = new BufferedReader(new InputStreamReader(inStream));
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        }
        catch (IOException e) {
            LOGGER.error((Object)("unable to write the content to file : " + inFile + " due to " + e));
            throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.UNABLE_TO_WRITE_FILE, (Throwable)e, (Object)inFile);
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException iOException) {}
            }
        }
        FileUtil.writeToFile(buffer.toString(), inFile);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writeFile(OutputStream inOutputStream, String inContent) throws IOException {
        BufferedOutputStream bufferedOutput = null;
        try {
            bufferedOutput = new BufferedOutputStream(inOutputStream);
            bufferedOutput.write(inContent.getBytes());
            bufferedOutput.flush();
        }
        finally {
            try {
                if (bufferedOutput != null) {
                    bufferedOutput.close();
                }
            }
            catch (IOException ex) {
                LOGGER.error((Object)("unable to write the content to the OutputStream because of " + ex));
            }
        }
    }

    public static String convertStreamToString(InputStream inStream) throws BizFailure {
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            String line;
            reader = new BufferedReader(new InputStreamReader(inStream));
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
        }
        catch (IOException e) {
            LOGGER.error((Object)("unable to convert the given Stream to String because of " + e));
            throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.CONVERT_FAILURE, (Throwable)e, (Object)inStream);
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException iOException) {}
            }
        }
        return buffer.toString();
    }

    public static String getFileAsString(File inFile) throws BizFailure {
        StringBuffer contents = new StringBuffer();
        BufferedReader in = null;
        try {
            String line;
            in = new BufferedReader(new FileReader(inFile));
            while ((line = in.readLine()) != null) {
                contents.append(line);
                contents.append(System.getProperty("line.separator"));
            }
        }
        catch (IOException e) {
            LOGGER.error((Object)("unable to load the given file : " + inFile + " as string due to " + e));
            throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.UNABLE_TO_LOAD_FILE, (Throwable)e, (Object)inFile);
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException iOException) {}
            }
        }
        return contents.toString();
    }

    public static byte[] loadFile(File inFile) throws BizFailure {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BufferedReader fileReader = null;
        try {
            String line;
            fileReader = new BufferedReader(new FileReader(inFile));
            while ((line = fileReader.readLine()) != null) {
                stream.write(line.getBytes());
                stream.write(10);
            }
        }
        catch (Exception ex) {
            LOGGER.error((Object)("unable to load the given file : " + inFile + " as byte[] due to " + ex));
            throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.UNABLE_TO_LOAD_FILE, (Throwable)ex, (Object)inFile);
        }
        finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            }
            catch (IOException ex) {
                throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.UNABLE_TO_LOAD_FILE, (Throwable)ex, (Object)inFile);
            }
        }
        return stream.toByteArray();
    }

    public static byte[] loadStream(InputStream inStream) throws BizFailure {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BufferedReader fileReader = null;
        try {
            String line;
            fileReader = new BufferedReader(new InputStreamReader(inStream, DEFAULT_ENCODING));
            while ((line = fileReader.readLine()) != null) {
                stream.write(line.getBytes(DEFAULT_ENCODING));
                stream.write(10);
            }
            byte[] arrby = stream.toByteArray();
            return arrby;
        }
        catch (Exception ex) {
            LOGGER.error((Object)("unable to load the given InputStream as byte[] due to " + ex));
            throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.UNABLE_TO_RETRIEVE_FILE, (Throwable)ex);
        }
        finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
                if (stream != null) {
                    stream.close();
                }
                if (inStream != null) {
                    inStream.close();
                }
            }
            catch (IOException ex) {
                throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.UNABLE_TO_RETRIEVE_FILE, (Throwable)ex);
            }
        }
    }

    public static String getBuildFolderPath() throws BizFailure {
        if (buildFolder == null) {
            File parent = null;
            try {
   //             parent = new File(LOADER.getResource("").getFile().getParent());
                buildFolder = parent.getParent() + File.separator + BUILD_FOLDER + File.separator;
                return buildFolder;
            }
            catch (Exception e) {
                LOGGER.error((Object)("unable to get the build folder path due to : " + e));
                throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.UNABLE_TO_LOAD_FILE, (Throwable)e, (Object)parent);
            }
        }
        return buildFolder;
    }

    public static String locateFile(String inFileName) {
        String classpath = FileUtil.getClassPath();
        File file = new File(classpath + File.separator + inFileName);
        if (!file.exists()) {
            file = FileUtil.getFile(inFileName);
        }
        return file.getAbsolutePath();
    }

    public static boolean releaseLock(File inFile) {
        try {
            RandomAccessFile randomAcessFile = new RandomAccessFile(inFile, "rw");
            FileLock lock = randomAcessFile.getChannel().lock();
            lock.release();
            return true;
        }
        catch (Exception e) {
            LOGGER.info((Object)"Exception Occured while releasing File Lock");
            return false;
        }
    }

    public static String getClassPath() {
        String path = "";
        try {
     //       path = LOADER.getResource("").getFile().getParent();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)("Class path is" + path));
            }
        }
        catch (Exception io) {
            LOGGER.error((Object)("IO Exception, unable to load the class path.." + io));
        }
        return path;
    }

    public static void writeFileWithEncoding(String inStr, File inFile, String inEncoding) throws BizFailure {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(inFile), inEncoding));
            writer.write(inStr);
        }
        catch (IOException e) {
            LOGGER.error((Object)("unable to write the content to file : " + inFile));
            throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.UNABLE_TO_WRITE_FILE, (Throwable)e, (Object)inFile);
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                }
                catch (IOException iOException) {}
            }
        }
    }

}
