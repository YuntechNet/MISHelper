package tw.edu.yuntech.dormnet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Clode on 2017/3/28.
 */
public class GuiMain extends MISHelper {

    private MainListener mainListener;

    private JFrame frame;
    private JLabel[] labels = {
            new JLabel("網卡: "), new JLabel("IP位置: ")
    };
    private JLabel[] lblSys = {
            new JLabel("作業系統: {OS_NAME}  "), new JLabel("系統版本: {OS_VERSION}  "), new JLabel("Java版本: {JAVA_VERSION}  ")
    };
    private JComboBox<?> interfaceBox;
    private JTextField ipBox = new JTextField(10);
    private JButton[] btns = {
            new JButton("確認"), new JButton("取消"), new JButton("關於")
    };

    public GuiMain(SystemInfo systemInfo, ArrayList<String> adapters) {
        this(systemInfo, adapters.toArray(new String[adapters.size()]));
    }

    public GuiMain(SystemInfo systemInfo, String[] adapters) {
        lblSysUpdate(systemInfo);
        mainListener = new MainListener();

        interfaceBox = new JComboBox<>(adapters);

        frame = new JFrame("MISHelper");
        frame.setSize(new Dimension(600, 200));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        lblGenerate();
        comboGenerate();
        fieldGenerate();
        btnGenerate();

        frame.setVisible(true);
    }

    private void lblSysUpdate(SystemInfo systemInfo) {
        lblSys[0].setText(lblSys[0].getText().replace("{OS_NAME}", systemInfo.getOsName()));
        lblSys[1].setText(lblSys[1].getText().replace("{OS_VERSION}", systemInfo.getOsVersion()));
        lblSys[2].setText(lblSys[2].getText().replace("{JAVA_VERSION}", systemInfo.getJavaVersion()));
        return;
    }

    private void lblGenerate() {
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        for(int i = 0; i < lblSys.length; ++i) {
            GridBagConstraints pg = new GridBagConstraints();
            pg.gridx = i;
            pg.fill = GridBagConstraints.HORIZONTAL;
            p.add(lblSys[i], pg);
        }
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 3;
        g.insets = new Insets(5, 5, 5, 5);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;
        frame.add(p, g);

        for(int i = 0; i < labels.length; ++i) {
            GridBagConstraints g2 = new GridBagConstraints();
            g2.gridx = 0;
            g2.gridy = i + 1;
            g2.insets = new Insets(5, 5, 5, 5);
            g2.anchor = GridBagConstraints.WEST;
            frame.add(labels[i], g2);
        }

    }

    private void comboGenerate() {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 1;
        g.gridy = 1;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        frame.add(interfaceBox, g);
    }

    private void fieldGenerate() {
        for(int i = 0; i < 1; ++i) {
            ipBox.setHorizontalAlignment(JTextField.CENTER);

            GridBagConstraints g = new GridBagConstraints();
            g.gridx = 1;
            g.gridy = i + 2;
            g.anchor = GridBagConstraints.WEST;
            frame.add(ipBox, g);
        }
    }

    private void btnGenerate() {
        JPanel p = new JPanel();
        for(int i = 0; i < 3; ++i) {
            btns[i].addActionListener(mainListener);
            btns[i].setActionCommand(btns[i].getText());

            p.add(btns[i]);
        }

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 2;
        g.gridy = 2;
        frame.add(p, g);
    }


    class MainListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            if(cmd.equals(btns[0].getText())) {
                // Confirm
                if(!checkIpFormat(ipBox.getText()))
                    ipBox.setText("");
                processStaticIpUpdate(AdapterInfo.getAdapterName(interfaceBox.getSelectedItem().toString()), ipBox.getText());
            } else if(cmd.equals(btns[1].getText())) {
                // Cancel
                ipBox.setText("");
            } else if(cmd.equals(btns[2].getText())) {
                // About

            }
        }
    }

}
