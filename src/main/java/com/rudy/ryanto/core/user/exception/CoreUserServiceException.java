package com.rudy.ryanto.core.user.exception;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class CoreUserServiceException extends RuntimeException{

    public CoreUserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoreUserServiceException(String message) {
        super(message);
    }
}
