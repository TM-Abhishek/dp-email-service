package com.tester.localtester.sftp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;

@Configuration
public class SFTPConfiguration {

    @Bean
    public DefaultSftpSessionFactory sftp() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
        factory.setHost("192.168.1.61");
        factory.setPort(22);
        factory.setAllowUnknownKeys(true);
        factory.setUser("turtlefin");
        factory.setPassword("turtlefin");
        return factory;
    }
}