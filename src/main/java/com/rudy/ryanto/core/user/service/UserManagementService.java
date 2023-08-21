package com.rudy.ryanto.core.user.service;

import com.rudy.ryanto.core.user.domain.UserCommonReq;
import com.rudy.ryanto.core.user.domain.UserCommonRes;
import com.rudy.ryanto.core.user.exception.CoreUserServiceException;
import com.rudy.ryanto.core.user.util.JSonResponseValidator;
import com.rudy.ryanto.core.user.util.UserConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserManagementService {

    @Value("${host.user.management}")
    private String host;

    @Value("${endpoint.searchById}")
    private String endpointSearchById;

    @Value("${endpoint.create}")
    private String endpointCreate;

    @Value("${endpoint.update}")
    private String endpointUpdate;

    @Autowired
    private RestTemplate restTemplate;

    public UserCommonRes searchById(UserCommonReq userCommonReq) {
        log.info("in method UserManagementService searchById");
        UserCommonRes userCommonRes = null;
        userCommonRes = doProcess(userCommonReq, userCommonRes);
        return userCommonRes;
    }

    private UserCommonRes doProcess(UserCommonReq userCommonReq, UserCommonRes userCommonRes) {
        String finalUrl  = getUri(userCommonReq);
        try{
            var response = restTemplate.postForEntity(finalUrl, userCommonReq,UserCommonRes.class);
            if(JSonResponseValidator.doValidateResponse(response)){
                userCommonRes = response.getBody();
            }

        }catch (CoreUserServiceException e){
            throw e;
        }
        return userCommonRes;
    }

    private String getUri(UserCommonReq userCommonReq) {
        String uri = "";
        if(StringUtils.isEmpty(userCommonReq.getOperation()))
            uri =  host.concat(endpointSearchById);
        else if(UserConstant.operation.CREATE.getCode().equals(userCommonReq.getOperation()))
            uri =  host.concat(endpointCreate);
        else if(UserConstant.operation.UPDATE.getCode().equals(userCommonReq.getOperation()))
            uri =  host.concat(endpointUpdate);
        return uri;
    }

    public UserCommonRes doProcess(UserCommonReq req) {
        return null;
    }
}
