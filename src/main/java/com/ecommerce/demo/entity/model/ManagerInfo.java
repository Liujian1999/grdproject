package com.ecommerce.demo.entity.model;

import lombok.Data;

@Data
public class ManagerInfo {
    /**
     * 管理员id
     */
    private Integer id;

    /**
     * 管理员账号用户名
     */
    private String managerName;
    /**
     * 管理员账号用户密码
     */
    private String password;
}
