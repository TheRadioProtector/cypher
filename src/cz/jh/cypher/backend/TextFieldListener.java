package cz.jh.cypher.backend;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.function.Consumer;

public class TextFieldListener implements DocumentListener {
	private final Consumer<DocumentEvent> consumer;

	public TextFieldListener(Consumer<DocumentEvent> consumer) {
		this.consumer = consumer;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		update(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		update(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		update(e);
	}

	private void update(DocumentEvent e) {
		consumer.accept(e);
	}
}
