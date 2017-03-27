package tw.edu.yuntech.dormnet;

import javax.swing.*;
import java.awt.*;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by Clode on 2017/3/25.
 */
public class MISHelper {

    public static int i = 1;
    static JFrame frame;
    static JLabel[] labels = {new JLabel("網卡:"), new JLabel("IP位置:"), new JLabel("網路遮罩:"), new JLabel("預設閘道:"), new JLabel("慣用DNS:"), new JLabel("備用DNS:")};
    static JTextField[][] fields = {
            {new JTextField(2), new JTextField(2), new JTextField(2), new JTextField(2)},
            {new JTextField(2), new JTextField(2), new JTextField(2), new JTextField(2)},
            {new JTextField(2), new JTextField(2), new JTextField(2), new JTextField(2)},
            {new JTextField(2), new JTextField(2), new JTextField(2), new JTextField(2)},
            {new JTextField(2), new JTextField(2), new JTextField(2), new JTextField(2)}};
    ArrayList<String> interfaceNameList = new ArrayList<String>();

    public static void main(String args[]) {
        frame = new JFrame("MIS Helper");
        frame.setSize(new Dimension(400, 200));
        frame.setLayout(new GridBagLayout());

        labelGenerate();
        textFieldGenerate();

        frame.setVisible(true);
    }

    void captureInterface() {
        try {
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface face = (NetworkInterface) interfaces.nextElement();
                //if(!(face.isLoopback() || face.getDisplayName().contains("WAN") || face.getDisplayName().contains("LAN") || face.getDisplayName().contains("WiFi"))) {
                if (face.isUp() && !face.isLoopback() && face.supportsMulticast() && !face.getDisplayName().contains("VM")) {
                    System.out.println((i++) + " : Display Name:" + face.getDisplayName() + " + Name: " + face.getName());
                    interfaceNameList.add(face.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void labelGenerate() {
        for(int i = 0; i < labels.length; ++i) {
            GridBagConstraints g = new GridBagConstraints();
            g.gridx = 0;
            g.gridy = i;
            g.gridwidth = 1;
            g.gridheight = 1;
            g.anchor = GridBagConstraints.WEST;
            frame.add(labels[i], g);
        }
    }

    static void textFieldGenerate() {
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 4; ++j) {
                JTextField txtField = fields[i][j];
                txtField.setHorizontalAlignment(JTextField.RIGHT);

                GridBagConstraints g = new GridBagConstraints();
                g.gridx = 1 + 2 * j;
                g.gridy = i + 1;
                g.gridwidth = 1;
                g.gridheight = 1;
                frame.add(fields[i][j], g);

                if(j == 3) break;
                GridBagConstraints g2 = new GridBagConstraints();
                g2.gridx = 2 * (j + 1);
                g2.gridy = i + 1;
                g2.gridwidth = 1;
                g2.gridheight = 1;
                frame.add(new JLabel("."), g2);
            }
        }
    }
}
