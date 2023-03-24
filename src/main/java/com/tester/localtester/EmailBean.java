package com.tester.localtester;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailBean {
    private String file;
    private String fileName;
    private String toEmail;
    private String managerEmail;
}
