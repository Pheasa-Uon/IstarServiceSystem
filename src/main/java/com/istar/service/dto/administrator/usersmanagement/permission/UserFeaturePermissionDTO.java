package com.istar.service.dto.administrator.usersmanagement.permission;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserFeaturePermissionDTO {
    private String code;
    private Boolean isSearch;
    private Boolean isAdd;
    private Boolean isViewed;
    private Boolean isEdit;
    private Boolean isApprove;
    private Boolean isReject;
    private Boolean isDeleted;
    private Boolean isSave;
    private Boolean isClear;
    private Boolean isCancel;
    private Boolean isProcess;
    private Boolean isImport;
    private Boolean isExport;
}
