package server.control;

import server.server.Server;
import javax.swing.*;
import java.awt.*;

public class ServerDriver {

    public static void main(String[] args) {

        setLookAndFeel();

        Server s = new Server();
        s.startServer();

    }

    public static void setLookAndFeel() {

        UIManager.put("OptionPane.background", Color.BLACK);
        UIManager.put("OptionPane.foreground", Color.WHITE);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);

        UIManager.getLookAndFeelDefaults().put("Panel.background", Color.DARK_GRAY);
        UIManager.getLookAndFeelDefaults().put("Panel.foreground", Color.WHITE);

        UIManager.getLookAndFeelDefaults().put("Frame.background", Color.DARK_GRAY);
        UIManager.getLookAndFeelDefaults().put("Frame.foreground", Color.WHITE);

        UIManager.getLookAndFeelDefaults().put("Label.background", Color.BLACK);
        UIManager.getLookAndFeelDefaults().put("Label.foreground", Color.WHITE);

        UIManager.getLookAndFeelDefaults().put("Button.background", Color.DARK_GRAY);
        UIManager.getLookAndFeelDefaults().put("Button.select", Color.BLACK);
        UIManager.getLookAndFeelDefaults().put("Button.shadow", Color.BLACK);
        UIManager.getLookAndFeelDefaults().put("Button.foreground", Color.WHITE);

        UIManager.getLookAndFeelDefaults().put("TextField.background", Color.GRAY);
        UIManager.getLookAndFeelDefaults().put("TextField.foreground", Color.WHITE);

        UIManager.getLookAndFeelDefaults().put("MenuBar.background", Color.DARK_GRAY);
        UIManager.getLookAndFeelDefaults().put("MenuBar.foreground", Color.WHITE);

        UIManager.getLookAndFeelDefaults().put("Menu.background", Color.DARK_GRAY);
        UIManager.getLookAndFeelDefaults().put("Menu.foreground", Color.WHITE);

        UIManager.getLookAndFeelDefaults().put("ComboBox.background", Color.BLACK);
        UIManager.getLookAndFeelDefaults().put("ComboBox.foreground", Color.WHITE);

        UIManager.getLookAndFeelDefaults().put("MenuItem.background", Color.DARK_GRAY);
        UIManager.getLookAndFeelDefaults().put("MenuItem.foreground", Color.WHITE);

    }

}
