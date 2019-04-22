package cz.jh.cypher.gui.elements;

import javax.swing.*;

public class ExecuteButton extends JButton {
   private static final long serialVersionUID = -2823329895163019318L;
   private final FCButton source;
   private final FCButton target;

   public ExecuteButton(String text, FCButton source, FCButton target) {
      super(text);
      this.source = source;
      this.target = target;
   }

   public FCButton getSource() {
      return this.source;
   }

   public FCButton getTarget() {
      return this.target;
   }
}
