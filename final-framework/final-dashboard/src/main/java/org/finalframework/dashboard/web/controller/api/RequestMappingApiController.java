package org.finalframework.dashboard.web.controller.api;

import org.finalframework.dashboard.web.entity.RequestHandler;
import org.finalframework.dashboard.web.entity.RequestPattern;
import org.finalframework.dashboard.web.service.RequestMappingService;
import org.finalframework.dashboard.web.service.query.RequestPatternQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-14 09:29:20
 * @since 1.0
 */
@RestController
@org.springframework.web.bind.annotation.RequestMapping("/api/request")
public class RequestMappingApiController {
    public static final Logger logger = LoggerFactory.getLogger(RequestMappingApiController.class);

    @Resource
    private RequestMappingService requestMappingService;

    @GetMapping(name = "find RequestMapping of query", path = "/patterns")
    public List<RequestPattern> patterns(RequestPatternQuery query) {
        return requestMappingService.query(query);
    }

    @GetMapping(name = "find RequestMapping of pattern and method", path = "/mapping")
    public RequestHandler mapping(String pattern, RequestMethod method) {
        return requestMappingService.find(pattern, method);
    }


}
