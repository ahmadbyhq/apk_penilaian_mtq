package main;

import model.Juri;
import config.dbConnection;
import controller.*;
import model.*;
import java.util.*;
import ui.*;
import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {
            new login_fix().setVisible(true);
        });
    }
}
