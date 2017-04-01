package tw.edu.yuntech.dormnet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Clode on 2017/3/28.
 */
public class GuiMain extends MISHelper {

    private MainListener mainListener;

    private JFrame frame;
    private JLabel[] labels = {
            new JLabel(r.getString("lblInterface") + ": "), new JLabel(r.getString("lblIP") + ": ")
    };
    private JLabel[] lblSys = {
            new JLabel(r.getString("lblOS") + ": {OS_NAME}  "), new JLabel(r.getString("lblSysVersion") + ": {OS_VERSION}  "), new JLabel(r.getString("lblJavaVersion") + ": {JAVA_VERSION}  ")
    };
    private JComboBox<String> interfaceBox;
    private JTextField ipBox = new JTextField(10);
    private JButton[] btns = {
            new JButton(r.getString("btnRefresh")), new JButton(r.getString("btnConfirm")), new JButton(r.getString("btnClear")), new JButton(r.getString("btnAbout")), new JButton(r.getString("btnExit"))
    };

    public GuiMain(SystemInfo systemInfo, String[] adapters) {
        lblSysUpdate(systemInfo);
        mainListener = new MainListener();

        interfaceBox = new JComboBox<>(adapters);

        frame = new JFrame("MISHelper");
        frame.setSize(new Dimension(650, 200));
        frame.setResizable(false);
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
        for (int i = 0; i < lblSys.length; ++i) {
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

        for (int i = 0; i < labels.length; ++i) {
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
        for (int i = 0; i < 1; ++i) {
            ipBox.setHorizontalAlignment(JTextField.CENTER);

            GridBagConstraints g = new GridBagConstraints();
            g.gridx = 1;
            g.gridy = i + 2;
            g.anchor = GridBagConstraints.WEST;
            frame.add(ipBox, g);
        }
    }

    private void btnGenerate() {
        btns[0].addActionListener(mainListener);
        btns[0].setActionCommand(btns[0].getText());
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 3;
        g.gridy = 1;
        g.insets = new Insets(5, 5, 5, 5);
        frame.add(btns[0], g);

        JPanel p = new JPanel();
        for (int i = 1; i < btns.length; ++i) {
            btns[i].addActionListener(mainListener);
            btns[i].setActionCommand(btns[i].getText());

            p.add(btns[i]);
        }

        GridBagConstraints g2 = new GridBagConstraints();
        g2.gridx = 2;
        g2.gridy = 2;
        g2.gridwidth = 2;
        frame.add(p, g2);
    }

    public static void message(String title, String message, int icon) {
        if (!terminal)
            JOptionPane.showMessageDialog(new JFrame(), message, title, icon);
        System.out.println(title + " : " + message);
        return;
    }

    class MainListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            if (cmd.equals(btns[0].getText())) {
                // Refresh
                AdapterInfo.captureAdapters(systemInfo);
                interfaceBox.removeAllItems();
                for (int i = 0; i < AdapterInfo.getDisplayNameArray().length; ++i)
                    interfaceBox.addItem(AdapterInfo.getDisplayNameArray()[i]);
            } else if (cmd.equals(btns[1].getText())) {
                // Confirm
                if (!checkIpFormat(ipBox.getText())) {
                    ipBox.setText("");
                    GuiMain.message(r.getString("msgTitIpFormatErr"), r.getString("msgIpFormatErr"), JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (processStaticIpUpdate(AdapterInfo.getAdapterName(interfaceBox.getSelectedItem().toString()), ipBox.getText()))
                    GuiMain.message(r.getString("msgTitUpdateSuc"), r.getString("msgUpdateSuc"), JOptionPane.PLAIN_MESSAGE);
                else
                    GuiMain.message(r.getString("msgTitUpdateFail"), r.getString("msgUpdateFail"), JOptionPane.WARNING_MESSAGE);
            } else if (cmd.equals(btns[2].getText())) {
                // Clear
                ipBox.setText("");
            } else if (cmd.equals(btns[3].getText())) {
                // About
                new GuiAbout();
            } else if (cmd.equals(btns[4].getText())) {
                // Exit
                System.exit(0);
            }
        }
    }

}
