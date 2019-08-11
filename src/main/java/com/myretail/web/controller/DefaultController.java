package com.myretail.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class DefaultController {

  @RequestMapping(method = RequestMethod.GET)
  public String swaggerUi() {
    return "redirect:/swagger-ui.html";
  }
}
