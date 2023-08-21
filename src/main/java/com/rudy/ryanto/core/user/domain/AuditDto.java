package com.rudy.ryanto.core.user.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditDto {
    private String activity;
    private String userId;
    private String transactionId;
    private String data1;
    private String data2;
    private Date createDate;
    private Date UpdateDate;
    private String createBy;
    private String updateBy;
}
