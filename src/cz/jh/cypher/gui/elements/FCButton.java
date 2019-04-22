package cz.jh.cypher.gui.elements;

import javax.swing.*;

public class FCButton extends JButton {
   private static final long serialVersionUID = 2901459798660372788L;
   private final JFileChooser fc;
   private final JTextField textField;

   public FCButton(String text, JFileChooser fc, JTextField textField) {
      super(text);
      this.fc = fc;
      this.textField = textField;
   }

   public JFileChooser getFc() {
      return this.fc;
   }

   public JTextField getTextField() {
      return this.textField;
   }
}
