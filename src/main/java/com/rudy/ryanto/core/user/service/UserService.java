package com.rudy.ryanto.core.user.service;

import com.rudy.ryanto.core.user.domain.UserCommonReq;
import com.rudy.ryanto.core.user.domain.UserCommonRes;
import com.rudy.ryanto.core.user.exception.CoreUserServiceException;
import com.rudy.ryanto.core.user.util.UserConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Service
@Slf4j
public class UserService {

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private UserManagementService userManagementService;

    public UserCommonRes findUserById(UserCommonReq userCommonReq, HttpServletRequest request) {
        log.info("in method find by user id");
        UserCommonRes userCommonRes = new UserCommonRes();
        try {
            userCommonRes = userManagementService.searchById(userCommonReq);
            if (null == userCommonRes) {
                throw new CoreUserServiceException(UserConstant.ERROR_DESCRIPTION.DATA_NOT_FOUND);
            }
        } catch (CoreUserServiceException e) {
            log.error(UserConstant.ERROR_DESCRIPTION.GENERAL_EXCEPTION + " caused : ", e.getCause());
            throw new CoreUserServiceException(UserConstant.ERROR_DESCRIPTION.GENERAL_EXCEPTION, e);
        } catch (Exception e) {
            throw new RuntimeException(UserConstant.ERROR_DESCRIPTION.GENERAL_EXCEPTION, e);
        } finally {
            auditTrailService.sendAuditData(userCommonReq, userCommonRes, UserConstant.ACTIVITY_MODULE_USER.INQUIRY, request);
        }
        return userCommonRes;
    }

    /**
     * this
     * @param req operation (0 = create,  1 = update, 2 = delete)
     * @param servletRequest
     * @return Boolean
     */
    public Boolean operationUser(UserCommonReq req, HttpServletRequest servletRequest) {
        Boolean isSuccess = Boolean.FALSE;
        var activity = Arrays.stream(UserConstant.operation.values())
                .filter(operation -> operation.getCode().equals(req.getOperation()))
                .findFirst().get();
        log.info("in method operation user with activity : {}", activity);
        try {

            UserCommonRes userCommonRes = userManagementService.doProcess(req);
            if (null == userCommonRes)
                throw new CoreUserServiceException(UserConstant.ERROR_DESCRIPTION.DATA_NOT_FOUND);

            isSuccess = Boolean.TRUE;
        } catch (CoreUserServiceException e) {
            if (activity.getCode().equals("0"))
                throw new CoreUserServiceException(UserConstant.ERROR_DESCRIPTION.ERROR_CREATE_DATA);
            if (activity.getCode().equals("1"))
                throw new CoreUserServiceException(UserConstant.ERROR_DESCRIPTION.ERROR_UPDATE_DATA);
            if (activity.getCode().equals("2"))
                throw new CoreUserServiceException(UserConstant.ERROR_DESCRIPTION.GENERAL_EXCEPTION);

        } catch (Exception e) {
            throw new RuntimeException(UserConstant.ERROR_DESCRIPTION.GENERAL_EXCEPTION, e);
        } finally {
            auditTrailService.sendAuditData(req, null, activity.getDescription(), servletRequest);
        }
        return isSuccess;
    }

}
