
package com.sevolutivo.demo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/greeting/{username}")
    public String greeting(@PathVariable String username){
        return "hello " + username;
    }
}
