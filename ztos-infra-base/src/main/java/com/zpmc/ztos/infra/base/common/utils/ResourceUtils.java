package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;

public class ResourceUtils {

    public static Resource loadResource(String inLocation) {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        return resourceLoader.getResource(inLocation);
    }

    public static Resource loadSerializableInputStreamResource(InputStream inInputStream) throws IOException {
  //      return new SerializableByteArrayResource(inInputStream);
        return null;
    }

    public static Resource loadSerializableResource(String inLocation) throws IOException, IllegalArgumentException {
        Resource resource = ResourceUtils.loadResource(inLocation);
        Assert.isTrue((boolean)resource.exists(), (String)("Resource location '" + inLocation + "' does not exist."));
        return ResourceUtils.loadSerializableInputStreamResource(resource.getInputStream());
    }

    public static String loadResourceAsString(String inLocation) {
        String dataStr = null;
        InputStream fileStream = null;
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        try {
            Resource resource = resourceLoader.getResource(BeanFactoryUtil.getClassPathFileName(inLocation));
            fileStream = resource.getInputStream();
            dataStr = IOUtils.toString((InputStream)fileStream);
        }
        catch (IOException e) {
            try {
                throw BizFailure.create("Failed to open file" + inLocation + " due to " + e);
            }
            catch (Throwable throwable) {
                IOUtils.closeQuietly(fileStream);
                throw throwable;
            }
        }
        IOUtils.closeQuietly((InputStream)fileStream);
        return dataStr;
    }
}
