package com.task.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqRegister {

    private String name;

    private String country;

    private String address;

    private String phone;

    private String password;

}
