package org.finalframework.example.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/22 13:25:35
 * @since 1.0
 */
@RestController
public class RedefineController {

    @GetMapping("/api/redefine")
    public String redefine() {
        return "redefine";
    }

}