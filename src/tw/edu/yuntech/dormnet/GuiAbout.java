package tw.edu.yuntech.dormnet;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Clode on 2017/3/30.
 */
public class GuiAbout extends MISHelper {

    public GuiAbout() {
        JFrame frame = new JFrame("MISHelper - About");
        frame.setSize(new Dimension(400, 200));

        JTextArea textArea = new JTextArea();
        textArea.append("MISHelper\n");
        textArea.append("Version: v1.0\n");
        textArea.append("Author: Norman Clode\n");


        frame.add(textArea);
        frame.setVisible(true);
    }

}
