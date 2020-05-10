package org.finalframework.data.api.controller;

import org.finalframework.data.api.serivce.EntityService;
import org.finalframework.data.api.serivce.query.EntityQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-09 21:02:00
 * @since 1.0
 */
@RestController
@RequestMapping("/api/entities")
public class EntityApiController {
    public static final Logger logger = LoggerFactory.getLogger(EntityApiController.class);
    @Resource
    private EntityService entityService;


    @GetMapping
    public List<Class<?>> query(EntityQuery query) {
        return entityService.query(query);
    }

}

