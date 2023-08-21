package com.rudy.ryanto.core.user.util;

import com.rudy.ryanto.core.user.exception.CoreUserServiceException;
import org.springframework.http.ResponseEntity;

public class JSonResponseValidator {

    public interface RESPONSE_CODE {
        public final int RC_SUCCESS = 200;
        public final int RC_BAD_REQ = 400;
        public final int RC_INTERNAL_ERROR = 500;
        public final int RC_UNAVAILABLE = 503;
        public final int RC_FAILED_DB = 11;
    }

    public static Boolean doValidateResponse(ResponseEntity<?> o) {
        Boolean isValid = Boolean.FALSE;
        if (null == o)
            throw new CoreUserServiceException(UserConstant.ERROR_DESCRIPTION.GENERAL_EXCEPTION);

        if (RESPONSE_CODE.RC_SUCCESS != o.getStatusCode().value())
            isValid = Boolean.TRUE;

        return isValid;
    }
}
