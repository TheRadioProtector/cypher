package cz.jh.cypher.backend;

import cz.jh.cypher.gui.CypherUI;

import java.awt.*;

public class CypherMain {
   public static void main(String[] args) {
      EventQueue.invokeLater(CypherUI::new);
   }
}
