package com.stephen.learning.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author jack
 */

public class RemoteShellTool {
    private Connection conn;
    private String ipAddr;
    private String userName;
    private String password;
    private String charset = Charset.defaultCharset().toString();

    public RemoteShellTool(String ipAddr, String userName, String password, String charset) {
        this.ipAddr = ipAddr;
        this.userName = userName;
        this.password = password;
        if (StringUtils.isNotBlank(charset)) {
            this.charset = charset;
        }
    }

    public boolean login() throws IOException {
        conn = new Connection(ipAddr);
        //连接
        conn.connect();
        //认证
        return conn.authenticateWithPassword(userName, password);
    }


    public String exec(String cmds) {
        InputStream in = null;
        String result = "";
        try {
            // 打开一个会话
            if (this.login()) {
                Session session = conn.openSession();
                session.execCommand(cmds);

                in = session.getStdout();
                result = this.processStdout(in, this.charset);
                session.close();
                conn.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return result;
    }

    public String processStdout(InputStream in, String charset) {
        byte[] buf = new byte[1024];
        StringBuffer sb = new StringBuffer();
        try {
            while (in.read(buf) != -1) {
                sb.append(new String(buf, charset));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
