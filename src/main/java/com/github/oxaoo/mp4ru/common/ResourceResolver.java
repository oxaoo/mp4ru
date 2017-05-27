package com.github.oxaoo.mp4ru.common;

import com.github.oxaoo.mp4ru.exceptions.ResourceResolverException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Alexander Kuleshov
 * @version 0.1.0
 * @since 01.04.2017
 */
public class ResourceResolver {
    public static String getAbsolutePath(String resource) throws ResourceResolverException {
        try {
            return new File(resource).getCanonicalPath();
        } catch (IOException e) {
            throw new ResourceResolverException("Failed to get resource absolute path [" + resource + "].", e);
        }
    }

    public static InputStreamReader getResourceAsStreamReader(String resource) throws ResourceResolverException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
        if (is == null) throw new ResourceResolverException("Resource [" + resource + "] could not be found.");
        return new InputStreamReader(is);
    }
}
