/*
 * Copyright (c) 2017 The Semux Developers
 *
 * Distributed under the MIT software license, see the accompanying file
 * LICENSE or https://opensource.org/licenses/mit-license.php
 */
package org.semux.utils;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemUtil {
    private static final Logger logger = LoggerFactory.getLogger(SystemUtil.class);

    public static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Get the operating system name.
     * 
     * @return
     */
    public static String getOSName() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.indexOf("win") >= 0) {
            return "Windows";
        } else if (os.indexOf("linux") >= 0) {
            return "Linux";
        } else if (os.indexOf("mac") >= 0) {
            return "MacOS";
        } else {
            return "Unkown";
        }
    }

    /**
     * Get the operating system architecture
     * 
     * @return
     */
    public static String getOSArch() {
        return System.getProperty("os.arch");
    }

    /**
     * Get the IP address of this peer.
     * 
     * @return the public IP address if available, otherwise local address
     */
    public static String getIp() {
        try {
            URL url = new URL("http://checkip.amazonaws.com/");
            URLConnection con = url.openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String ip = reader.readLine();
            reader.close();

            return ip;
        } catch (Exception e) {
            logger.debug("Failed to retrieve public IP address");
        }

        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            // last chance
            return InetAddress.getLoopbackAddress().getHostAddress();
        }
    }

    /**
     * Read a password from console.
     * 
     * @return
     */
    public static String readPassword() {
        Console console = System.console();

        if (console == null) {
            System.out.print("Please enter your password: ");
            System.out.flush();

            return SCANNER.nextLine();
        }

        return new String(console.readPassword("Please enter you password: "));
    }
}
