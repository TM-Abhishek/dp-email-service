package com.tester.localtester.sftp;

import com.jcraft.jsch.*;

public class SftpTester {

    /*public static boolean doesFolderExist(ChannelSftp channelSftp, String path) {
        String[] folders = path.split("/");
        for (String folder : folders) {
            if (folder.length() > 0 && !folder.contains(".")) {
                try {
                    channelSftp.cd(folder);
                } catch (SftpException e) {
                    return false;
                }
            }
        }
        return true;
    }*/
    public static void main(String[] args) {
        String localPath = "/home/pravendra/Desktop/Pravendra/Turtlemint/Workspace/Java_WS/test/tmp";
        String fileName =  "file.txt";
        String sftpPath = "/turtlefin/upload/test/test2";
        String sftpHost = "192.168.1.61";
        String sftpPort = "22";
        String sftpUser = "turtlefin";
        String sftpPassword = "turtlefin";

        try{
            /**
             * Open session to sftp server
             */
            JSch jsch = new JSch();
            Session session = jsch.getSession(sftpUser, sftpHost, Integer.valueOf(sftpPort));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(sftpPassword);
            System.out.println("Connecting------");
            session.connect();
            System.out.println("Established Session");

            Channel channel = session.openChannel("sftp");
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.connect();
            //System.out.println(doesFolderExist(sftpChannel,sftpPath));
            System.out.println("Opened sftp Channel");

            /**
             * Do everything you need in sftp
             */
            System.out.println("Copying file to Host");

            sftpChannel.put(localPath+"/"+fileName, sftpPath);
            System.out.println("Copied file to Host");

            System.out.println("Copying file from Host to Local");
            sftpChannel.get(sftpPath+"/"+fileName, localPath);
            System.out.println("Copied file from Host to local");

            sftpChannel.disconnect();
            session.disconnect();

            System.out.println("Disconnected from sftp");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
