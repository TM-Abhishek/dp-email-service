package com.tester.localtester.sftp;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class SFTPServiceUtils {

    private static Logger logger = LoggerFactory.getLogger(SFTPServiceUtils.class);

    @Value("${sftp.host}")
    private static String host = "127.0.0.1";

    @Value("${sftp.port}")
    private static Integer port = 22;

    @Value("${sftp.username}")
    private static String username = "turtlefin";

    @Value("${sftp.password}")
    private static String password = "turtlefin";

    @Value("${sftp.sessionTimeout}")
    private static Integer sessionTimeout = 150000;

    @Value("${sftp.channelTimeout}")
    private static Integer channelTimeout = 150000;


    /*public static boolean doesFolderExist(ChannelSftp channelSftp, String path) throws SftpException {
        String[] folders = path.split("/");
        for (String folder : folders) {
            if (folder.length() > 0 && !folder.contains(".")) {
                try {
                    channelSftp.cd(folder);
                } catch (SftpException e) {
                    channelSftp.cd("/" + channelSftp);
                    return false;
                }
            }
        }
        channelSftp.cd("/");
        return true;
    }*/

    public static void validateRemoteFolder(ChannelSftp channelSftp, String path) throws SftpException {
        String[] folders = path.split("/");
        for (String folder : folders) {
            if (folder.length() > 0 && !folder.contains(".")) {
                try {
                    channelSftp.cd(folder);
                } catch (SftpException e) {
                    logger.info("Creating folder : {}", folder);
                    channelSftp.mkdir(folder);
                    channelSftp.cd(folder);
                }
            }
        }
    }

    public static ChannelSftp createChannelSftp() {
        try {
            JSch jSch = new JSch();
            Session session = jSch.getSession(username, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect(sessionTimeout);
            logger.info("Established Session");
            Channel channel = session.openChannel("sftp");
            channel.connect(channelTimeout);
            logger.info("Opened sftp Channel");
            return (ChannelSftp) channel;
        } catch (JSchException ex) {
            logger.error("Create ChannelSftp error", ex);
        }

        return null;
    }

    public static void disconnectChannelSftp(ChannelSftp channelSftp) {
        try {
            if (channelSftp == null)
                return;

            if (channelSftp.isConnected())
                channelSftp.disconnect();

            if (channelSftp.getSession() != null)
                channelSftp.getSession().disconnect();

        } catch (Exception ex) {
            logger.error("SFTP disconnect error", ex);
        }
    }

    public static void uploadFile(String localFilePath, String remoteFilePath) {
        ChannelSftp channelSftp = createChannelSftp();
        try {
            validateRemoteFolder(channelSftp, remoteFilePath);
            logger.info("Uploading file to SFTP");
            channelSftp.put(localFilePath, remoteFilePath);
            logger.info("file uploaded to SFTP");
        } catch (SftpException ex) {
            logger.error("Error upload file", ex);
        } finally {
            disconnectChannelSftp(channelSftp);
        }
    }
    public static void downloadFile(String remoteFilePath, String localFilePath) {
        ChannelSftp channelSftp = createChannelSftp();
        try {
            channelSftp.get(remoteFilePath, localFilePath);
            logger.info("file downloaded from SFTP");
        } catch (SftpException ex) {
            logger.error("Error in file download", ex);
        } finally {
            disconnectChannelSftp(channelSftp);
        }
    }
}


