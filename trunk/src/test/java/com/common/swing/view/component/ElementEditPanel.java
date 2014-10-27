package com.common.swing.view.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.common.swing.domain.exception.SwingException;
import com.common.swing.domain.model.Element;
import com.common.swing.view.component.panel.BaseEditPanel;

/**
 * El panel de edici�n de entidades.
 * 
 * @since 24/10/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public class ElementEditPanel extends BaseEditPanel<Element> {
	private static final long serialVersionUID = 1L;

	private JTextField textField;
	private JLabel progressLabel;

	@Override
	protected void init() {
		this.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		this.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 15, 48, 14);
		lblNombre.setFont(new Font("Arial", Font.BOLD, 11));
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(lblNombre);

		this.textField = new JTextField();
		this.textField.setBounds(68, 11, 222, 22);
		this.textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textField.selectAll();
			}
		});
		this.add(this.textField);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 44, 280, 2);
		this.add(separator);

		this.progressLabel = new JLabel("");
		this.progressLabel.setBounds(109, 57, 82, 32);
		this.progressLabel.setVisible(true);
		this.progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(this.progressLabel);
	}

	@Override
	public void emptyFields() {
		this.textField.setText("");
	}

	@Override
	public Element getBean() throws SwingException {
		Element element = new Element();
		element.setName(this.textField.getText());
		return element;
	}
}