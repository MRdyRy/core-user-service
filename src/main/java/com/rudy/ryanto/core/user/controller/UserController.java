package com.rudy.ryanto.core.user.controller;

import com.rudy.ryanto.core.user.domain.UserCommonReq;
import com.rudy.ryanto.core.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @ResponseBody
    @PostMapping(value = "/getById", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object doProcessReq(@RequestBody UserCommonReq userCommonReq, HttpServletRequest servletRequest) {
        log.info("doProcessReq : {}", userCommonReq);
        Object result = null;
        if (StringUtils.isEmpty(userCommonReq.getOperation()))
            result = userService.findUserById(userCommonReq, servletRequest);
        else {
            result = userService.operationUser(userCommonReq,servletRequest);
        }
        return result;
    }
}
