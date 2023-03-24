package com.tester.localtester.sftp;

import com.jcraft.jsch.SftpException;

import static com.tester.localtester.sftp.SFTPServiceUtils.*;

public class Tester {

    public static void main(String[] args) throws SftpException {
        String localeDirectory = "tmp";
        String file = "mis.xlsx";
        String remoteDirectory = "/turtlefin/upload";
        uploadFile(localeDirectory+"/"+file, remoteDirectory);
        //downloadFile(remoteDirectory+"/"+file, localeDirectory);
    }
}
