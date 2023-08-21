package com.rudy.ryanto.core.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rudy.ryanto.core.user.domain.AuditDto;
import com.rudy.ryanto.core.user.domain.UserCommonReq;
import com.rudy.ryanto.core.user.domain.UserCommonRes;
import com.rudy.ryanto.core.user.exception.CoreUserServiceException;
import com.rudy.ryanto.core.user.util.UserConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class AuditTrailService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${topic.audit}")
    private String auditTopic;
    @Autowired
    private ObjectMapper objectMapper;

    public void sendAuditData(UserCommonReq req, UserCommonRes res, String activity, HttpServletRequest request) {
      log.info("sending audit ");
      try{
          AuditDto auditDto = generateAuditLog(req,res, request,activity);
          kafkaTemplate.send(auditTopic, generateAuditLog(req,res,request,activity));
      }catch (Exception e){
          throw new CoreUserServiceException(UserConstant.ERROR_DESCRIPTION.ERROR_SENDING_MESSAGE,e);
      }
    }

    private AuditDto generateAuditLog(UserCommonReq req, UserCommonRes res, HttpServletRequest servletRequest,String activity) throws JsonProcessingException {
        return AuditDto.builder()
                .userId(req.getUserId())
                .activity(activity)
                .data1(objectMapper.writeValueAsString(req))
                .data2(objectMapper.writeValueAsString(res))
                .build();
    }
}
