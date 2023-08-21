package com.rudy.ryanto.core.user.util;

public class UserConstant {

    public interface ERROR_DESCRIPTION{
        public final String DATA_NOT_FOUND = "Data Not Found!";
        public final String GENERAL_EXCEPTION = "Failed to process, general error!";
        public final String ERROR_SENDING_MESSAGE = "Failed sending message!";
        public final String ERROR_CREATE_DATA = "Failed to create data!";
        public final String ERROR_UPDATE_DATA = "Failed to update data!";
    }

    public interface ACTIVITY_MODULE_USER{
        public final String INQUIRY = "INQUIRY_USER";
        public final String UPDATE = "UPDATE_DATA";
        public final String DEACTIVED = "UPDATE_NON_ACTIVE";
        public final String ACTIVED = "UPDATE_TO_ACTIVE";
        public final String CREATED = "CREATE_USER";
    }

    public enum operation{
        CREATE("0","Created"),
        UPDATE("1","Update"),
        DELETE("2","Delete");

        private String codeOps=null,description=null;

        private operation(String code,String description){
            this.codeOps=code;
            this.description=description;
        }

        public String getCode(){
            return this.codeOps;
        }

        public String getDescription(){
            return this.description;
        }
    }
}
