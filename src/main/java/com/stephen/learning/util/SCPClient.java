package com.stephen.learning.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

/**
 * @author jack
 */
@Slf4j
public class SCPClient {
    static private SCPClient instance;

    static synchronized public SCPClient getInstance(String IP, int port, String username, String passward) {
        if (instance == null) {
            instance = new SCPClient(IP, port, username, passward);
        }
        return instance;
    }

    public SCPClient(String IP, int port, String username, String passward) {
        this.ip = IP;
        this.port = port;
        this.username = username;
        this.password = passward;
    }

    /**
     * 远程拷贝文件
     *
     * @param remoteFile           远程源文件路径
     * @param localTargetDirectory 本地存放文件路径
     */
    public boolean getFile(String remoteFile, String localTargetDirectory) {
        Connection conn = new Connection(ip, port);
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username,
                    password);
            if (isAuthenticated == false) {
                log.info("authentication failed");
                return false;
            }
            ch.ethz.ssh2.SCPClient client = new ch.ethz.ssh2.SCPClient(conn);
            client.get(remoteFile, localTargetDirectory);
            conn.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }


    /**
     * 远程上传文件
     *
     * @param localFile             本地文件路径
     * @param remoteTargetDirectory 远程存放文件路径
     */
    public void putFile(String localFile, String remoteTargetDirectory) {
        Connection conn = new Connection(ip, port);
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username,
                    password);
            if (isAuthenticated == false) {
                log.info("authentication failed");
            }
            ch.ethz.ssh2.SCPClient client = new ch.ethz.ssh2.SCPClient(conn);
            client.put(localFile, remoteTargetDirectory);
            conn.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 远程上传文件并对上传文件重命名
     *
     * @param localFile             本地文件路径
     * @param remoteFileName        远程文件名
     * @param remoteTargetDirectory 远程存放文件路径
     * @param mode                  默认"0600"，length=4
     */
    public void putFile(String localFile, String remoteFileName, String remoteTargetDirectory, String mode) {
        Connection conn = new Connection(ip, port);
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username,
                    password);
            if (isAuthenticated == false) {
                log.info("authentication failed");
            }
            ch.ethz.ssh2.SCPClient client = new ch.ethz.ssh2.SCPClient(conn);
            if ((mode == null) || (mode.length() == 0)) {
                mode = "0600";
            }
            client.put(localFile, remoteFileName, remoteTargetDirectory, mode);

            //重命名
            Session sess = conn.openSession();
            String tmpPathName = remoteTargetDirectory + File.separator + remoteFileName;
            String newPathName = tmpPathName.substring(0, tmpPathName.lastIndexOf("."));
            sess.execCommand("mv " + remoteFileName + " " + newPathName);

            conn.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String ip;
    private int port;
    private String username;
    private String password;
}
