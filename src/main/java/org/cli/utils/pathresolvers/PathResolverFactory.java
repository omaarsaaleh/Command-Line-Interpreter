package org.cli.utils.pathresolvers;

import org.cli.commands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PathResolverFactory {
    private static final Map<String, Supplier<PathResolver>> resolverMap = new HashMap<>();

    static {
        resolverMap.put("Linux", LinuxPathResolver::getInstance);
    }

    public static PathResolver getResolver() {
        Supplier<PathResolver> resolverSupplier = resolverMap.get( System.getProperty("os.name") );

        if (resolverSupplier != null) {
            return resolverSupplier.get();
        }
        else {
            throw new IllegalArgumentException("os not supported");
        }
    }
}
