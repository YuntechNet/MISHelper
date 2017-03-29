package tw.edu.yuntech.dormnet;

/**
 * Created by Clode on 2017/3/25.
 */
public class MISHelper {

    static GuiMain guiMain;
    static SystemInfo systemInfo = new SystemInfo();

    public static void main(String args[]) {
        AdapterInfo.captureAdapters(systemInfo);

        guiMain = new GuiMain(systemInfo, AdapterInfo.getDisplayName());
    }

    boolean checkIpFormat(String ip) {
        String ipReg =
                "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
        if(!ip.matches(ipReg)) return false;
        return true;
    }

    void processStaticIpUpdate(String adapter, String ip) {
        try {
            String gate = ip.split("\\.")[0] + "." + ip.split("\\.")[1] + "." + ip.split("\\.")[2] + ".254";
            String addCmd = "cmd.exe /C netsh interface ipv4 set address \"" + adapter + "\" static " + ip + " 255.255.255.0 " + gate;
            String mainDnsCmd = "cmd.exe /C netsh interface ipv4 set dnsservers \"" + adapter + "\" static 140.125.252.1";
            String otherDnsCmd = "cmd.exe /C netsh interface ipv4 add dnsservers \"" + adapter + "\" 140.125.253.2";
            System.out.println(addCmd);
            System.out.println(mainDnsCmd);
            System.out.println(otherDnsCmd);
            Runtime.getRuntime().exec(addCmd);
            Runtime.getRuntime().exec(mainDnsCmd);
            Runtime.getRuntime().exec(otherDnsCmd);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}