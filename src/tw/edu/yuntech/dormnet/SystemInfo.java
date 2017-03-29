package tw.edu.yuntech.dormnet;

/**
 * Created by Clode on 2017/3/28.
 */
public class SystemInfo {

    public String getOsName() {
        return osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    private String osName = "";
    private String osVersion = "";
    private String javaVersion = "";

    public SystemInfo() {
        osName = System.getProperty("os.name");
        osVersion = System.getProperty("os.version");
        javaVersion = System.getProperty("java.version");
    }

    boolean isWindows() { return osName.toLowerCase().contains("win"); }
    boolean isMac() { return osName.toLowerCase().contains("mac"); }
    boolean isLinux() { return osName.toLowerCase().contains("nix") || osName.toLowerCase().contains("nux") || osName.toLowerCase().contains("aix"); }

}
