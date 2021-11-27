package com.company;

import java.awt.*;
import javax.swing.*;
import java.net.*;
import javax.jnlp.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestJnlp {
    static BasicService basicService = null;
    public static void main(String args[]) {
        JFrame frame = new JFrame("Jnlp Hello World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel();
        Container content = frame.getContentPane();
        content.add(label, BorderLayout.CENTER);
        String message = "Jnlp Hello World";

        label.setText(message);

        try {
            basicService = (BasicService)
                    ServiceManager.lookup("javax.jnlp.BasicService");
        } catch (UnavailableServiceException e) {
            System.err.println("Lookup failed: " + e);
        }

        JButton button = new JButton("Jnlp Hello World");

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    URL url = new URL(actionEvent.getActionCommand());
                    basicService.showDocument(url);
                } catch (MalformedURLException ignored) {
                }
            }
        };

        button.addActionListener(listener);

        content.add(button, BorderLayout.SOUTH);
        frame.pack();
        frame.show();
    }
}