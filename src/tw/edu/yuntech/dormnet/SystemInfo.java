package tw.edu.yuntech.dormnet;

/**
 * Created by Clode on 2017/3/28.
 */
public class SystemInfo {

    private String osName = "";

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    private String osVersion = "";
    private String javaVersion = "";

    public SystemInfo() {
        osName = System.getProperty("os.name");
        osVersion = System.getProperty("os.version");
        javaVersion = System.getProperty("java.version");
    }


}
