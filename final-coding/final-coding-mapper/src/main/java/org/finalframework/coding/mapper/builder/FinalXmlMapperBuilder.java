package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.core.Assert;
import org.springframework.lang.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-11 15:24:42
 * @since 1.0
 */
public class FinalXmlMapperBuilder implements XmlMapperBuilder {

    private final ProcessingEnvironment processingEnvironment;

    public FinalXmlMapperBuilder(ProcessingEnvironment processingEnvironment) {
        this.processingEnvironment = processingEnvironment;
    }

    private List<ResultMapXmlMapperBuilder> resultMapXmlMapperBuilders = new ArrayList<>(1);
    private List<MethodXmlMapperBuilder> methodXmlMapperBuilders = new ArrayList<>(8);
    private List<SqlFragmentXmlMapperBuilder> sqlFragmentXmlMapperBuilders = new ArrayList<>();

    {
        resultMapXmlMapperBuilders.add(new DefaultResultMapXmlMapperBuilder());

        methodXmlMapperBuilders.add(new InsertMethodXmlMapperBuilder());
        methodXmlMapperBuilders.add(new UpdateMethodXmlMapperBuilder());
        methodXmlMapperBuilders.add(new DeleteMethodXmlMapperBuilder());

        methodXmlMapperBuilders.add(new SelectMethodXmlMapperBuilder());
        methodXmlMapperBuilders.add(new SelectIdsMethodXmlMapperBuilder());
        methodXmlMapperBuilders.add(new SelectCountMethodXmlMapperBuilder());

        methodXmlMapperBuilders.add(new TruncateMethodXmlMapperBuilder());

        sqlFragmentXmlMapperBuilders.add(new SqlTableFragmentXmlMapperBuilder());
        sqlFragmentXmlMapperBuilders.add(new SqlWhereIdFragmentXmlMapperBuilder());
        sqlFragmentXmlMapperBuilders.add(new SqlWhereIdsFragmentXmlMapperBuilder());
        sqlFragmentXmlMapperBuilders.add(new SqlSelectColumnsFragmentXmlMapperBuilder());

        sqlFragmentXmlMapperBuilders.add(new SqlCriterionConditionFragmentXmlMapperBuilder());

//        sqlFragmentXmlMapperBuilders.add(new SqlCriteriaFragmentXmlMapperBuilder());
        sqlFragmentXmlMapperBuilders.add(new SqlGroupFragmentXmlMapperBuilder());
        sqlFragmentXmlMapperBuilders.add(new SqlOrderFragmentXmlMapperBuilder());
        sqlFragmentXmlMapperBuilders.add(new SqlLimitFragmentXmlMapperBuilder());
        sqlFragmentXmlMapperBuilders.add(new SqlQueryFragmentXmlMapperBuilder());

    }

    @Override
    public void build(Node root, Document document, TypeElement mapper, Entity<Property> entity) {
        generateStartComment(root, document);
        resultMapXmlMapperBuilders.forEach(item -> item.build(root, document, entity));

        buildMapperMethods(root, document, getMapperInterfaces(mapper), entity);

        root.appendChild(document.createComment("=============================================================================================================="));
        root.appendChild(document.createComment("======FRAGMENT===========================GENERATED-BY-FINAL-FRAMEWORK===========================FRAGMENT======"));
        root.appendChild(document.createComment("=============================================================================================================="));

        sqlFragmentXmlMapperBuilders.forEach(item -> item.build(root, document, entity));

        generateEndComment(root, document);

    }

    private List<TypeElement> getMapperInterfaces(TypeElement mapper) {
        return mapper.getInterfaces().stream()
                .map(it -> (TypeElement) ((DeclaredType) it).asElement())
                .collect(Collectors.toList());
    }

    private void buildMapperMethods(Node root, Document document, List<TypeElement> mappers, Entity<Property> entity) {
        if (Assert.isEmpty(mappers)) return;

        mappers.forEach(mapper -> {
            buildMapperMethods(root, document, mapper, entity);
            buildMapperMethods(root, document, getMapperInterfaces(mapper), entity);
        });

    }

    private void buildMapperMethods(Node root, Document document, TypeElement mapper, Entity<Property> entity) {
        mapper.getEnclosedElements()
                .stream()
                .filter(it -> it.getKind() == ElementKind.METHOD)
                .map(it -> (ExecutableElement) it)
                .filter(it -> !it.isDefault())
                .forEach(method -> {
                    for (MethodXmlMapperBuilder methodXmlMapperBuilder : methodXmlMapperBuilders) {
                        if (methodXmlMapperBuilder.supports(method)) {
                            methodXmlMapperBuilder.build(root, document, method, entity);
                        }
                    }
                });
    }

    private void generateStartComment(@NonNull Node root, @NonNull Document document) {
        root.appendChild(document.createTextNode("\n\t"));
        root.appendChild(document.createComment("=============================================================================================================="));
        root.appendChild(document.createTextNode("\n\t"));
        root.appendChild(document.createComment("=====START===============================GENERATED-BY-FINAL-FRAMEWORK===============================START====="));
        root.appendChild(document.createTextNode("\n\t"));
        root.appendChild(document.createComment("=============================================================================================================="));
        root.appendChild(document.createTextNode("\n\t"));
    }

    private void generateEndComment(@NonNull Node root, @NonNull Document document) {
        root.appendChild(document.createComment("=============================================================================================================="));
        root.appendChild(document.createComment("======END================================GENERATED-BY-FINAL-FRAMEWORK================================END======"));
        root.appendChild(document.createComment("=============================================================================================================="));
    }


}
