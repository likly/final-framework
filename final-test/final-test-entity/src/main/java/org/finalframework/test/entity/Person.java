package org.finalframework.test.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.finalframework.data.annotation.ColumnView;
import org.finalframework.data.annotation.FunctionColumn;
import org.finalframework.data.annotation.ReadOnly;
import org.finalframework.data.annotation.Transient;
import org.finalframework.data.entity.AbsRecord;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:25
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Person extends AbsRecord {
    private static final long serialVersionUID = -8785625823175210092L;
    //    @PrimaryKey(insertable = true)
//    private Long id;
    @JsonView(Person.class)
    @NotNull
    @ColumnView(Person.class)
    private String name;
    @JsonView(Person.class)
    private int age;
    @ReadOnly
    @FunctionColumn(reader = "MAX(age)")
    @ColumnView(Person.class)
    private int maxAge;
    @ColumnView(Person.class)
    private List<String> stringList;
    private List<Integer> intList;
    private Map<String, Object> properties;
    //    @NonColumn
    @Transient
    private Date date = new Date();

//    private YN yn;


}
