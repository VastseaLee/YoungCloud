package com.young.other.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YoungUser {
    private String name;

    private Integer age;

    private String address;
}
