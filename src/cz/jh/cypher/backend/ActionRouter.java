package cz.jh.cypher.backend;

import cz.jh.cypher.gui.elements.ExecuteButton;
import cz.jh.cypher.gui.elements.FCButton;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ActionRouter {
	public static final String TEXTFIELD_PROPERTY = "textfield";
	private static final Color SUCCESS_COLOR = new Color(46, 139, 87);
	private static final Color FAIL_COLOR = new Color(244, 72, 66);
	private final Cypher cypher;
	private final JFormattedTextField statusField;

	public ActionRouter(JTable table, JFormattedTextField statusField) {
		cypher = new Cypher(table);
		this.statusField = statusField;
	}

	public void chooseFile(ActionEvent event) {
		FCButton button = (FCButton) event.getSource();
		int returnVal = button.getFc().showOpenDialog(null);
		if (returnVal == 0) {
			button.getTextField().setText(button.getFc().getSelectedFile().getAbsolutePath());
		}

	}

	public void handleCypher(ActionEvent event) {
		cypher((ExecuteButton) event.getSource(), ConvertMode.CYPHER);
	}

	public void handleDecypher(ActionEvent event) {
		cypher((ExecuteButton) event.getSource(), ConvertMode.DECYPHER);
	}

	public void updateKeywordRow(DocumentEvent event) {
		JTextField field = (JTextField) event.getDocument().getProperty(TEXTFIELD_PROPERTY);
		cypher.updateKeymap(field.getText());
	}

	public void updateCompleteRow(DocumentEvent event) {
		JTextField field = (JTextField) event.getDocument().getProperty(TEXTFIELD_PROPERTY);
		String text = field.getText().trim();
		int offset = 0;
		if (text.length() != 1 || text.charAt(0) != '-') {
			if (!text.isEmpty()) {
				offset = Integer.parseInt(text);
			}

			cypher.updateCompleteMap(offset);
		}
	}

	private void cypher(ExecuteButton button, ConvertMode mode) {
		try {
			int i = cypher.convert(button.getTarget().getFc().getSelectedFile(),
					button.getSource().getFc().getSelectedFile(), mode);
			statusField.setForeground(SUCCESS_COLOR);
			statusField.setValue(String.format("Úspìch! %d øádkù hotovo", i));
		} catch (IOException e) {
			statusField.setForeground(FAIL_COLOR);
			statusField.setValue(String.format("Pøi ètení/zápisu souboru došlo k chybì: %s", e.getMessage()));
			e.printStackTrace();
		}
	}
}
