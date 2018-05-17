package com.wsx.springbootTest.tool;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.io.VFS;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class SpringBootVFS extends VFS {

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public List<String> list(final URL url, final String path) throws IOException {
		ClassLoader cl = this.getClass().getClassLoader();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
		Resource[] resources = resolver.getResources(path + "/**/*.class");
		final List<String> resourcePaths = Arrays.asList(resources).stream().map(resource -> {
			try {
				return preserveSubpackageName(resource.getURI(), path);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}).collect(toList());
		return resourcePaths;
	}

	protected static String preserveSubpackageName(final URI uri, final String rootPath) {
		final String uriStr = uri.toString();
		// we must return the uri with everything before the rootpath stripped
		// off
		final int start = uriStr.indexOf(rootPath);
		return uriStr.substring(start, uriStr.length());
	}
}