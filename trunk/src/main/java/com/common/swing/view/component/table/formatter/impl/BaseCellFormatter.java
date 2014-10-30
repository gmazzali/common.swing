package com.common.swing.view.component.table.formatter.impl;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.common.swing.view.component.table.formatter.CellFormatter;

/**
 * La clase base para los formateadores de las celdas, ya sea para visualizaci�n o edici�n de las mismas.
 * 
 * @see LongCellFormatter
 * @see BigDecimalCellFormatter
 * @see DateCellFormatter
 * @see StringCellFormatter
 * 
 * @since 30/10/2014
 * @author Guillermo Mazzali
 * @version 1.0
 * 
 * @param <C>
 *            La clase de componente que vamos a usar para editar la celda.
 */
public abstract class BaseCellFormatter<C extends Component> extends AbstractCellEditor implements CellFormatter {
	private static final long serialVersionUID = 1L;

	/**
	 * El render base para visulizaci�n.
	 */
	private DefaultTableCellRenderer renderer;
	/**
	 * El componente para editar.
	 */
	protected C componentEditor;

	/**
	 * Constructor de un formateador de celda que recibe el campo con el que va a editarse la misma.
	 * 
	 * @param editor
	 *            El componente de edici�n.
	 */
	public BaseCellFormatter(C editor) {
		this(editor, null, null);
	}

	/**
	 * Constructor de un formateador de celda que recibe el campo con el que va a editarse la misma y la orientaci�n en la visualizaci�n del valor.
	 * 
	 * @param editor
	 *            El componente de edici�n.
	 * @param horizontalAlignment
	 *            La alineaci�n horizontal del componente de visualizaci�n.
	 * @param verticalAlignment
	 *            La alineaci�n vertical del componente de visualizaci�n.
	 */
	public BaseCellFormatter(C editor, Integer horizontalAlignment, Integer verticalAlignment) {
		this.componentEditor = editor;
		this.renderer = new DefaultTableCellRenderer();
		if (horizontalAlignment == null) {
			horizontalAlignment = SwingConstants.LEFT;
		}
		if (verticalAlignment == null) {
			verticalAlignment = SwingConstants.CENTER;
		}
		this.renderer.setHorizontalAlignment(horizontalAlignment);
		this.renderer.setVerticalAlignment(verticalAlignment);
	}

	/**
	 * Permite definir que la celda va a poder editarse solo mediante 2 clicks del mouse.
	 */
	@Override
	public boolean isCellEditable(EventObject anEvent) {
		if (anEvent instanceof MouseEvent) {
			return ((MouseEvent) anEvent).getClickCount() >= this.getClickCount();
		}
		return false;
	}

	/**
	 * Retorna la cantidad de clicks que deben hacerse en la celda para activar la edici�n.
	 * 
	 * @return La cantidad de click sobre la celda para activar la edici�n.
	 */
	protected int getClickCount() {
		return CLICK_COUNT;
	}

	/**
	 * Permite recuperar el valor editado previamente convertido al tipo correcto.
	 * 
	 * @return El valor convertido obtenido desde el campo de edici�n.
	 */
	@Override
	public Object getCellEditorValue() {
		return this.parse(this.componentEditor);
	}

	/**
	 * Crea el render para visualizaci�n del contenido de la celda.
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		return this.renderer.getTableCellRendererComponent(table, this.format(value), isSelected, hasFocus, row, column);
	}

	/**
	 * Crea el campo de edici�n y lo carga para poder editar.
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		this.setValueToEditComponent(value, this.componentEditor);
		return this.componentEditor;
	}

	/**
	 * Permite formatear el objeto que corresponde a la celda. Previamente ya se valida que el mismo no sea <code>null</code>.
	 * 
	 * @param beanValue
	 *            El valor que vamos a formatear. Puede ser <code>null</code>.
	 * @return El objeto que corresponde a lo que vamos a desplegar en la celda.
	 */
	public abstract Object format(Object beanValue);

	/**
	 * Permite parsear el valor ingreado en la celda al valor de la variable.
	 * 
	 * @param editComponent
	 *            El componente de la celda donde estamos editando.
	 * @return El valor que tenemos dentro del campo de edici�n. La clase de retorno debe coincidir con la variable del bean a la que vamos a la que
	 *         va a asignarse.
	 */
	public abstract Object parse(C editComponent);

	/**
	 * Permite cargar dentro del campo de edici�n el valor de la variable.
	 * 
	 * @param beanValue
	 *            El valor de la variable.
	 * @param editComponent
	 *            El componente de edici�n.
	 */
	public abstract void setValueToEditComponent(Object beanValue, C editComponent);
}