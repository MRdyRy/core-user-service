package com.rudy.ryanto.core.user.controller;

import com.rudy.ryanto.core.user.domain.UserCommonReq;
import com.rudy.ryanto.core.user.domain.UserCommonRes;
import com.rudy.ryanto.core.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @ResponseBody
    @PostMapping(value = "/getById",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserCommonRes findCustomerById(UserCommonReq userCommonReq, HttpServletRequest servletRequest){
      log.info("findCustomerById : {}",userCommonReq);
      return userService.findUserById(userCommonReq,servletRequest);
    }
}
