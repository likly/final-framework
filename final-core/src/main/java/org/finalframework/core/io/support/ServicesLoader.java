package org.finalframework.core.io.support;


import org.springframework.core.io.UrlResource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-08-31 15:54:29
 * @see java.util.ServiceLoader
 * @since 1.0
 */
public final class ServicesLoader {
    private static final Map<ClassLoader, MultiValueMap<String, String>> cache = new ConcurrentReferenceHashMap<>();

    private static final String META_INF = "/META-INF";


    private ServicesLoader() {
    }

    public static List<String> load(@NonNull Class<?> service) {
        return load(service.getCanonicalName());
    }

    public static List<String> load(@NonNull String service) {
        return load(service, META_INF + "/services/" + service);
    }

    public static List<String> load(@NonNull String service, @NonNull String serviceResourceLocation) {
        return load(service, null, serviceResourceLocation);
    }

    public static List<String> load(@NonNull String service, @Nullable ClassLoader classLoader, @NonNull String propertiesResourceLocation) {
        return loadServices(service, classLoader, propertiesResourceLocation);
    }

    private static List<String> loadServices(@NonNull String service, @Nullable ClassLoader classLoader, String propertiesResourceLocation) {
        final MultiValueMap<String, String> result = cache.computeIfAbsent(classLoader, key -> new LinkedMultiValueMap<>());

        return result.computeIfAbsent(service, key -> {
            final List<String> services = new ArrayList<>();

            try {
                Enumeration<URL> urls = (classLoader != null ?
                        classLoader.getResources(propertiesResourceLocation) :
                        ClassLoader.getSystemResources(propertiesResourceLocation));
                while (urls.hasMoreElements()) {
                    URL url = urls.nextElement();
                    UrlResource resource = new UrlResource(url);


                    try (BufferedReader r = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                        String line;
                        while ((line = r.readLine()) != null) {
                            int commentStart = line.indexOf('#');
                            if (commentStart >= 0) {
                                line = line.substring(0, commentStart);
                            }
                            line = line.trim();
                            if (!line.isEmpty()) {
                                services.add(line);
                            }
                        }
                    }


                }
                cache.put(classLoader, result);
            } catch (IOException ex) {
                throw new IllegalArgumentException("Unable to load factories from location [" +
                        propertiesResourceLocation + "]", ex);
            }

            return services;
        });

    }
}

