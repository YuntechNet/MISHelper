package tw.edu.yuntech.dormnet;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * Created by Clode on 2017/3/25.
 */
public class MISHelper {

    static boolean terminal = false;

    static ResourceBundle r = ResourceBundle.getBundle("Lang", Locale.getDefault());
    //static ResourceBundle r = ResourceBundle.getBundle("Lang", new Locale("en", "US"));
    static GuiMain guiMain;
    static SystemInfo systemInfo = new SystemInfo();

    public static void main(String args[]) {
        for (String arg : args) {
            if (arg.equals("terminal") || arg.equals("debug") || arg.equals("ter"))
                terminal = true;
        }

        checkAdmin();
        if (!terminal) {
            AdapterInfo.captureAdapters(systemInfo);
            guiMain = new GuiMain(systemInfo, AdapterInfo.getDisplayNameArray());
        } else {
            if (!checkIpFormat(args[2]))
                GuiMain.message(r.getString("msgTitIpFormatErr"), r.getString("msgIpFormatErr"), JOptionPane.WARNING_MESSAGE);

            if (processStaticIpUpdate(args[1].replace("\"", ""), args[2]))
                GuiMain.message(r.getString("msgTitUpdateSuc"), r.getString("msgUpdateSuc"), JOptionPane.PLAIN_MESSAGE);
            else
                GuiMain.message(r.getString("msgTitUpdateFail"), r.getString("msgUpdateFail"), JOptionPane.WARNING_MESSAGE);

        }

    }

    static void checkAdmin() {
        if (systemInfo.isWindows() || systemInfo.isLinux()) {
            Preferences prefs = Preferences.systemRoot();
            try {
                prefs.put("foo", "bar"); // SecurityException on Windows
                prefs.remove("foo");
                prefs.flush(); // BackingStoreException on Linux
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (systemInfo.isMac()) {

        }
        GuiMain.message(r.getString("msgTitPerDenied"), r.getString("msgPerDenied"), JOptionPane.WARNING_MESSAGE);
        System.exit(0);
    }

    static boolean checkIpFormat(String ip) {
        String ipReg =
                "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
        if (!ip.matches(ipReg))
            return false;
        return true;
    }

    static boolean processStaticIpUpdate(String adapter, String ip) {
        String gate = ip.split("\\.")[0] + "." + ip.split("\\.")[1] + "." + ip.split("\\.")[2] + ".254";

        if (systemInfo.isWindows()) {
            try {
                String addCmd = "cmd.exe /C netsh interface ipv4 set address \"" + adapter + "\" static " + ip + " 255.255.255.0 " + gate;
                String mainDnsCmd = "cmd.exe /C netsh interface ipv4 set dnsservers \"" + adapter + "\" static 140.125.252.1";
                String otherDnsCmd = "cmd.exe /C netsh interface ipv4 add dnsservers \"" + adapter + "\" 140.125.253.2";
                System.out.println(addCmd);
                Runtime.getRuntime().exec(addCmd);
                System.out.println(mainDnsCmd);
                Runtime.getRuntime().exec(mainDnsCmd);
                System.out.println(otherDnsCmd);
                Runtime.getRuntime().exec(otherDnsCmd);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}