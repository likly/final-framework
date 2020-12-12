package org.ifinal.finalframework.json;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Json 序列化与反序列化服务接口
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JsonService {

    /**
     * 将数据序列化为Json串
     *
     * @param object 数据对象
     * @return json 串
     * @throws JsonException json JsonException
     */
    default String toJson(final @Nullable Object object) {

        return toJson(object, null);
    }

    /**
     * return the json string of {@linkplain Object object} with the {@linkplain Class view}.
     *
     * @param object the object
     * @param view   the view
     * @return the json string.
     * @throws JsonException json JsonException
     */
    String toJson(final @Nullable Object object, final @Nullable Class<?> view);

    /**
     * return json {@linkplain Object value} of json string
     *
     * @param json     json string
     * @param classOfT value type
     * @param <T>      type
     * @return json value
     * @throws JsonException json JsonException
     */
    default <T> T toObject(final @Nullable String json, final @NonNull Class<T> classOfT) {

        return toObject(json, classOfT, null);
    }

    /**
     * return json {@linkplain Object value} of json string
     *
     * @param json     json string
     * @param classOfT value type
     * @param view     json view
     * @param <T>      json type
     * @return json value
     * @throws JsonException json JsonException
     */
    <T> T toObject(final @Nullable String json, final @NonNull Class<T> classOfT, final @Nullable Class<?> view);

    /**
     * return json {@linkplain Object value} of json string
     *
     * @param json    json string
     * @param typeOfT value type
     * @param <T>     json type
     * @return json value
     * @throws JsonException json JsonException
     */
    default <T> T toObject(final @Nullable String json, final @NonNull Type typeOfT) {

        return toObject(json, typeOfT, null);
    }

    /**
     * return json {@linkplain Object value} of json string
     *
     * @param json    json string
     * @param typeOfT value type
     * @param view    json view
     * @param <T>     json type
     * @return json value
     * @throws JsonException json JsonException
     */
    <T> T toObject(final @Nullable String json, final @NonNull Type typeOfT, final @Nullable Class<?> view);

    /**
     * return json {@linkplain List value} of json string
     *
     * @param json         json string
     * @param elementClass json element type
     * @param <E>          json type
     * @return json value
     * @throws JsonException json JsonException
     */
    @SuppressWarnings("unchecked")
    default <E> List<E> toList(final @Nullable String json, final @NonNull Class<E> elementClass) {

        return toCollection(json, List.class, elementClass, null);
    }

    /**
     * return json {@linkplain List value} of json string
     *
     * @param json         json string
     * @param elementClass json element type
     * @param view         json view
     * @param <E>          json type
     * @return json value
     * @throws JsonException json JsonException
     */
    @SuppressWarnings("unchecked")
    default <E> List<E> toList(final @Nullable String json, final @NonNull Class<E> elementClass, final @Nullable Class<?> view) {

        return toCollection(json, List.class, elementClass, view);
    }

    /**
     * return json {@linkplain Set value} of json string
     *
     * @param json         json string
     * @param elementClass json element type
     * @param <E>          json type
     * @return json value
     * @throws JsonException json JsonException
     */
    @SuppressWarnings("unchecked")
    default <E> Set<E> toSet(final @Nullable String json, final @NonNull Class<E> elementClass) {

        return toCollection(json, Set.class, elementClass, null);
    }

    /**
     * return json {@linkplain Set value} of json string
     *
     * @param json         json string
     * @param elementClass json element type
     * @param view         json view
     * @param <E>          json type
     * @return json value
     * @throws JsonException json JsonException
     */
    @SuppressWarnings("unchecked")
    default <E> Set<E> toSet(final @Nullable String json, final @NonNull Class<E> elementClass, final @Nullable Class<?> view) {

        return toCollection(json, Set.class, elementClass, view);
    }

    /**
     * return json {@linkplain Collection value} of json string
     *
     * @param json            json string
     * @param collectionClass json collection type
     * @param elementClass    json element type
     * @param <E>             json element type
     * @param <T>             json collection type
     * @return json value
     * @throws JsonException json JsonException
     */
    default <E, T extends Collection<E>> T toCollection(final @Nullable String json, final @NonNull Class<T> collectionClass, final @NonNull Class<E> elementClass) {

        return toCollection(json, collectionClass, elementClass, null);
    }

    /**
     * return json {@linkplain Collection value} of json string
     *
     * @param json            json string
     * @param collectionClass json collection type
     * @param elementClass    json element type
     * @param <E>             json element type
     * @param <T>             json collection type
     * @return json value
     * @throws JsonException json JsonException
     */
    <E, T extends Collection<E>> T toCollection(final @Nullable String json, final @NonNull Class<T> collectionClass, final @NonNull Class<E> elementClass, final @Nullable Class<?> view);
}
