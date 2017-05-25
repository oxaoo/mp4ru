package com.github.oxaoo.mp4ru.common;

import com.github.oxaoo.mp4ru.exceptions.ResourceResolverException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * @author Alexander Kuleshov
 * @version 0.1.0
 * @since 01.04.2017
 */
public class ResourceResolver {
    public static String getAbsolutePath(String resource) throws ResourceResolverException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(resource);
        if (url == null) throw new ResourceResolverException("Resource [" + resource + "] could not be found.");
        try {
            return Paths.get(url.toURI()).toAbsolutePath().toString();
        } catch (URISyntaxException e) {
            throw new ResourceResolverException("Failed to get resource URI [" + url.toString() + "].", e);
        }
    }

    public static URL getUrl(String resource) throws ResourceResolverException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(resource);
        if (url == null) throw new ResourceResolverException("Resource [" + resource + "] could not be found.");
        return url;
    }

    public static InputStreamReader getResourceAsStreamReader(String resource) throws ResourceResolverException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
        if (is == null) throw new ResourceResolverException("Resource [" + resource + "] could not be found.");
        return new InputStreamReader(is);
    }
}
