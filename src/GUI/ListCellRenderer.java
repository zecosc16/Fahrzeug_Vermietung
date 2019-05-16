/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import fahrzeug_vermietung.Vehicle;
import java.awt.Color;
import java.awt.Component;
import java.time.LocalDate;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author oskar
 */
public class ListCellRenderer implements javax.swing.ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setBackground(Color.white);
        Vehicle v = (Vehicle) value;

        label.setText(v.toString());
        if (v.getBorrowTill() != null) {
            if (v.getBorrowTill().isBefore(LocalDate.now())) {
                label.setBackground(Color.red);
            } else if (v.getBorrowTill().equals(LocalDate.now())) {
                label.setBackground(Color.yellow);
            }
        }

        if (isSelected) {
            label.setBackground(new Color(57, 105, 138));
            label.setForeground(Color.white);
        }

        return label;
    }

}
