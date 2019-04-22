package cz.jh.cypher.gui;

import cz.jh.cypher.backend.ActionRouter;
import cz.jh.cypher.backend.TextFieldListener;
import cz.jh.cypher.gui.elements.ExecuteButton;
import cz.jh.cypher.gui.elements.FCButton;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CypherUI {
	private final ActionRouter actionRouter;
	private JFrame frame;
	private JFormattedTextField statusField;
	private JTextField textSource;
	private JTextField textTarget;
	private JTextField textKey;
	private JTextField textOffset;
	private JTable charTable;
	private JLabel labelOriginal;
	private JLabel labelKey;
	private JLabel labelMapKey;
	private JLabel labelMapOffset;
	private JLabel labelOffset;
	private ExecuteButton btnCypher;
	private ExecuteButton btnDecypher;
	private FCButton btnSource;
	private FCButton btnTarget;

	public CypherUI() {
		initCharTable();
		initStatusField();
		actionRouter = new ActionRouter(charTable, statusField);
		initRest();
		frame.setVisible(true);
	}

	private void initRest() {
		initFrame();
		initTextSource();
		initTextTarget();
		initLabelKey();
		initTextKey();
		initLabelOffset();
		initTextOffset();
		initLabelOriginal();
		initLabelMapKey();
		initLabelMapOffset();
		initBtnSource();
		initBtnTarget();
		initBtnCypher();
		initBtnDecypher();
	}

	private void initStatusField() {
		statusField = new JFormattedTextField();
		statusField.setEditable(false);
		statusField.setBounds(220, 140, 1075, 30);
	}

	private void initLabelMapOffset() {
		labelMapOffset = new JLabel("Posun:");
		labelMapOffset.setHorizontalAlignment(11);
		labelMapOffset.setBounds(10, 105, 50, 30);
		frame.getContentPane().add(labelMapOffset);
	}

	private void initLabelMapKey() {
		labelMapKey = new JLabel("Klíè:");
		labelMapKey.setHorizontalAlignment(11);
		labelMapKey.setBounds(10, 75, 50, 30);
		frame.getContentPane().add(labelMapKey);
	}

	private void initLabelOriginal() {
		labelOriginal = new JLabel("Originál:");
		labelOriginal.setHorizontalAlignment(11);
		labelOriginal.setBounds(10, 45, 50, 30);
		frame.getContentPane().add(labelOriginal);
	}

	private void initCharTable() {
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(0);
		charTable = new JTable(3, 41);
		charTable.setRowSelectionAllowed(false);
		charTable.setFont(new Font("Consolas", Font.BOLD, 14));
		charTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		charTable.setBounds(65, 45, 1230, 90);
		charTable.setRowHeight(30);
		charTable.setDefaultRenderer(charTable.getColumnClass(0), renderer);
	}

	private void initTextOffset() {
		textOffset = new JTextField();
		textOffset.setBounds(325, 10, 55, 30);
		frame.getContentPane().add(textOffset);
		textOffset.setColumns(10);
		textOffset.getDocument().putProperty(ActionRouter.TEXTFIELD_PROPERTY, textOffset);
		textOffset.getDocument().addDocumentListener(new TextFieldListener(actionRouter::updateCompleteRow));
	}

	private void initLabelOffset() {
		labelOffset = new JLabel("Posun:");
		labelOffset.setHorizontalAlignment(11);
		labelOffset.setBounds(270, 10, 50, 30);
		frame.getContentPane().add(labelOffset);
	}

	private void initTextKey() {
		textKey = new JTextField();
		textKey.setBounds(65, 10, 200, 30);
		frame.getContentPane().add(textKey);
		textKey.setColumns(10);
		textKey.getDocument().putProperty(ActionRouter.TEXTFIELD_PROPERTY, textKey);
		textKey.getDocument().addDocumentListener(new TextFieldListener(actionRouter::updateKeywordRow));
	}

	private void initLabelKey() {
		labelKey = new JLabel("Klíè:");
		labelKey.setHorizontalAlignment(11);
		labelKey.setBounds(10, 10, 50, 30);
		frame.getContentPane().add(labelKey);
	}

	private void initTextTarget() {
		textTarget = new JTextField();
		textTarget.setEditable(false);
		textTarget.setBounds(960, 10, 335, 30);
		frame.getContentPane().add(textTarget);
		textTarget.setColumns(10);
	}

	private void initTextSource() {
		textSource = new JTextField();
		textSource.setEditable(false);
		textSource.setBounds(505, 10, 335, 30);
		frame.getContentPane().add(textSource);
		textSource.setColumns(10);
	}

	private void initBtnTarget() {
		btnTarget = new FCButton("Cíl...", new JFileChooser(), textTarget);
		btnTarget.setBounds(855, 10, 100, 30);
		btnTarget.addActionListener(actionRouter::chooseFile);
		frame.getContentPane().add(btnTarget);
	}

	private void initBtnSource() {
		btnSource = new FCButton("Zdroj...", new JFileChooser(), textSource);
		btnSource.setBounds(400, 10, 100, 30);
		btnSource.addActionListener(actionRouter::chooseFile);
		frame.getContentPane().add(btnSource);
	}

	private void initBtnCypher() {
		btnCypher = new ExecuteButton("TAM", btnSource, btnTarget);
		btnCypher.setBounds(10, 140, 100, 30);
		btnCypher.addActionListener(actionRouter::handleCypher);
		frame.getContentPane().add(btnCypher);
	}

	private void initBtnDecypher() {
		btnDecypher = new ExecuteButton("ZPÌT", btnSource, btnTarget);
		btnDecypher.addActionListener(actionRouter::handleDecypher);
		btnDecypher.setBounds(115, 140, 100, 30);
		frame.getContentPane().add(btnDecypher);
	}

	private void initFrame() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1320, 220);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(charTable);
		frame.getContentPane().add(statusField);
	}

	public JTable getCharTable() {
		return charTable;
	}

	public JFrame getFrame() {
		return frame;
	}

	public JTextField getTextSource() {
		return textSource;
	}

	public JTextField getTextTarget() {
		return textTarget;
	}

	public JTextField getTextKey() {
		return textKey;
	}

	public JTextField getTextOffset() {
		return textOffset;
	}

	public JLabel getLabelOriginal() {
		return labelOriginal;
	}

	public JLabel getLabelMapKey() {
		return labelMapKey;
	}

	public JLabel getLabelMapOffset() {
		return labelMapOffset;
	}

	public JButton getBtnExecute() {
		return btnCypher;
	}
}
