package main;

import model.Juri;
import config.dbConnection;
import controller.*;
import model.*;
import java.util.*;
import ui.*;
import javax.swing.SwingUtilities;
import javax.swing.*;

public class main {
    public static void main(String[] args) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            new login_fix().setVisible(true);
        });
    }
}
