package server.server;

import util.MySQLTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ServerLog extends Container {

    private final TextArea log;
    private final JTextField console;
    private final Server serverPointer;

    public ServerLog(Server serverPointer) {

        log = new TextArea();
        console = new JTextField();
        console.addKeyListener(new ConsoleListener());
        this.serverPointer = serverPointer;

        log.setColumns(70);
        log.setRows(30);
        log.setBackground(Color.GRAY);
        log.setForeground(Color.WHITE);
        log.setEditable(false);

        console.setColumns(40);
        console.setText(">>>");

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;
        add(log, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        add(console, gc);

    }

    public void log(String s) {

        log.append(s + "\n");

    }

    private class ConsoleListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {

            if(e.getSource() == console) {

                if(e.getKeyChar() == KeyEvent.VK_ENTER) {

                    MySQLTool sql = serverPointer.getSql();

                    String consoleCommand = console.getText();
                    consoleCommand = consoleCommand.replace(">>>", "");
                    String[] cmds = consoleCommand.split(" ");

                    if(cmds[0].equals("SELECT")) {
                        log("CONSOLE: " + consoleCommand);
                        boolean successful = sql.query(consoleCommand);
                        if(successful) log("Query Successful");
                        else log("Query Unsuccessful");
                    }
                    else if(cmds[0].equals("INSERT") || cmds[0].equals("UPDATE")) {
                        log("CONSOLE: " + consoleCommand);
                        int rowsChanged = sql.update(consoleCommand);
                        if(rowsChanged != 0) log("Update Successful");
                        else log("Update Unsuccessful");
                    }
                    else if(cmds[0].equals("clear")) {
                        log.setText("");
                    }
                    else {
                        log("Unknown command: " + cmds[0]);
                    }

                    console.setText(">>>");

                }

            }

        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }


}
