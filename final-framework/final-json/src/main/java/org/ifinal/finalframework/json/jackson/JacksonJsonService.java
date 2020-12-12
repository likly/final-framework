package org.ifinal.finalframework.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.ifinal.finalframework.json.JsonException;
import org.ifinal.finalframework.json.JsonService;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class JacksonJsonService implements JsonService {

    @Setter
    private ObjectMapper objectMapper;

    public JacksonJsonService(final ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    public JacksonJsonService() {
        this(new ObjectMapperFactory().create());
    }


    @Override
    public String toJson(final @Nullable Object object, final @Nullable Class<?> view) {

        try {
            if (Asserts.isNull(view)) {
                return objectMapper.writeValueAsString(object);
            }
            return objectMapper.writerWithView(view).writeValueAsString(object);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    @Override
    public <T> T toObject(final @Nullable String json, final @NonNull Class<T> classOfT, final @Nullable Class<?> view) {

        try {
            if (Asserts.isNull(json)) {
                return null;
            }

            if (Asserts.isNull(view)) {
                return objectMapper.readValue(json, classOfT);
            } else {
                return objectMapper.readerWithView(view).readValue(json, classOfT);
            }
        } catch (Exception e) {
            throw new JsonException(e);
        }

    }

    @Override
    public <T> T toObject(final @Nullable String json, final @NonNull Type typeOfT, final @Nullable Class<?> view) {

        try {
            if (Asserts.isNull(json)) {
                return null;
            }

            if (Asserts.isNull(view)) {
                return objectMapper.readValue(json, objectMapper.getTypeFactory().constructType(typeOfT));
            } else {
                return objectMapper.readerWithView(view)
                        .forType(objectMapper.getTypeFactory().constructType(typeOfT))
                        .readValue(json);
            }
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    @Override
    public <E, T extends Collection<E>> T toCollection(final @Nullable String json, final @NonNull Class<T> collectionClass, final @NonNull Class<E> elementClass, final @Nullable Class<?> view) {

        try {
            if (Asserts.isNull(json)) {
                return null;
            }

            if (Asserts.isNull(view)) {
                return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(collectionClass, elementClass));
            } else {
                return objectMapper.readerWithView(view)
                        .forType(objectMapper.getTypeFactory().constructCollectionType(collectionClass, elementClass))
                        .readValue(json);
            }
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }
}
