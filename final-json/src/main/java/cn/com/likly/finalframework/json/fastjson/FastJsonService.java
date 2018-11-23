package cn.com.likly.finalframework.json.fastjson;

import cn.com.likly.finalframework.json.JsonService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 01:42:54
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class FastJsonService implements JsonService {
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put(null, 123);
        System.out.println(JSON.toJSONString(map, SerializerFeature.WriteNonStringKeyAsString));
    }

    @Override
    public String toJson(Object object) throws Throwable {
        return JSON.toJSONString(object, SerializerFeature.WriteNonStringKeyAsString);
    }

    @Override
    public <T> T parse(String json, Class<T> classOfT) throws Throwable {
        return JSON.parseObject(json, classOfT);
    }

    @Override
    public <T> T parse(String json, Type typeOfT) throws Throwable {
        return JSON.parseObject(json, typeOfT);
    }

    @Override
    public <E, T extends Collection<E>> T parse(String json, Class<T> collectionClass, Class<E> elementClass) throws Throwable {

        if (List.class.isAssignableFrom(collectionClass)) {
            return (T) JSON.parseArray(json, elementClass);
        } else if (Set.class.isAssignableFrom(collectionClass)) {
            return (T) new HashSet<E>(JSON.parseArray(json, elementClass));
        }
        throw new UnsupportedOperationException("fast json not support parse " + collectionClass.getName());
    }
}