package com.stephen.learning.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author jack
 */
@Data
@Builder
public class User {
    private String name;
    private String sex;
    private Integer age;
    private String email;
    private String address;
}
