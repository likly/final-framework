

package org.finalframework.data.query;


import lombok.Data;
import org.finalframework.annotation.query.Equal;
import org.finalframework.annotation.query.OR;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-07 00:15:52
 * @since 1.0
 */
@Data
@OR
public class QueryEntityQuery {
    @Equal
    private String name;
    @Equal
    private Integer age;
}
