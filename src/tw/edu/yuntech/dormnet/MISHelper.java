package tw.edu.yuntech.dormnet;

import javax.swing.text.DefaultFormatter;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;

/**
 * Created by Clode on 2017/3/25.
 */
public class MISHelper {

    static GuiMain guiMain;
    static SystemInfo systemInfo = new SystemInfo();
    static ArrayList<String> interfaceNameList = new ArrayList<String>();

    public static void main(String args[]) {

        captureInterface();

        guiMain = new GuiMain(systemInfo, interfaceNameList);

    }

    static void captureInterface() {
        try {
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface face = (NetworkInterface) interfaces.nextElement();
                //if(!(face.isLoopback() || face.getDisplayName().contains("WAN") || face.getDisplayName().contains("LAN") || face.getDisplayName().contains("WiFi"))) {
                if (face.isUp() && !face.isLoopback() && face.supportsMulticast() && !face.getDisplayName().contains("VM")) {
                    //System.out.println((i++) + " : Display Name:" + face.getDisplayName() + " + Name: " + face.getName());
                    interfaceNameList.add(face.getDisplayName() + " / " + face.getName());
                    System.out.println(face);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean processUpdateSystemInterfaceInfo(String ip) {
        String ipReg =
                "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
        if(!ip.matches(ipReg)) return false;
        try {
            String cmd = "netsh interface ipv4 set address " + ip + " ";
            Runtime.getRuntime().exec("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}

class IPAddressFormatter extends DefaultFormatter {
    public String valueToString(Object value) throws ParseException {
        if (!(value instanceof byte[]))
            throw new ParseException("Not a byte[]", 0);
        byte[] a = (byte[]) value;
        if (a.length != 4)
            throw new ParseException("Length != 4", 0);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int b = a[i];
            if (b < 0)
                b += 256;
            builder.append(String.valueOf(b));
            if (i < 3)
                builder.append('.');
        }
        return builder.toString();
    }

    public Object stringToValue(String text) throws ParseException {
        StringTokenizer tokenizer = new StringTokenizer(text, ".");
        byte[] a = new byte[4];
        for (int i = 0; i < 4; i++) {
            int b = 0;
            if (!tokenizer.hasMoreTokens())
                throw new ParseException("Too few bytes", 0);
            try {
                b = Integer.parseInt(tokenizer.nextToken());
            } catch (NumberFormatException e) {
                throw new ParseException("Not an integer", 0);
            }
            if (b < 0 || b >= 256)
                throw new ParseException("Byte out of range", 0);
            a[i] = (byte) b;
        }
        if (tokenizer.hasMoreTokens())
            throw new ParseException("Too many bytes", 0);
        return a;
    }
}
