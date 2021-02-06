package org.bohr.gui.laf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalButtonUI;
import javax.swing.plaf.metal.MetalComboBoxEditor;
import javax.swing.plaf.metal.MetalComboBoxUI;

import org.bohr.gui.uiUtils.FontUtils;

public class HalfComBoBoxUI extends MetalComboBoxUI {
	class SelectedListCellRender extends JLabel implements ListCellRenderer {

		private static final long serialVersionUID = -1296293768041139988L;

		public SelectedListCellRender() {
			super();
			setOpaque(true);

			setBorder(new EmptyBorder(0, 0, 0, 0));
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {

			setText(value.toString());

			setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
			setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());

			return this;
		}
	}

	@Override
	protected ComboBoxEditor createEditor() {
		return new halfComboBoxEditor();
	}

	class halfComboBoxEditor extends MetalComboBoxEditor {
		halfComboBoxEditor() {
			super();
			editor.setBorder(new EmptyBorder(0, 10, 0, 0));
			editor.setOpaque(false);
		}
	}

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		comboBox.setOpaque(false);
		FontUtils.setPLAINFont(comboBox, 16);
		listBox.setBackground(new Color(0xf2f3f6));
		listBox.setForeground(new Color(0x323349));
		listBox.setCellRenderer(new SelectedListCellRender());
		listBox.setSelectionBackground(new Color(0x0ebdf9));
		listBox.setFont(listBox.getFont().deriveFont(10F));
	}

	@Override
	public void update(Graphics g, JComponent c) {
		paintBackground(g, c.getBounds());
		paint(g, c);
	}

	@Override
	public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
//		super.paintCurrentValueBackground(g, bounds, hasFocus);
	}

	public void paintBackground(Graphics g, Rectangle bounds) {
		if (g != null && g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			RoundRectangle2D.Double rect = new RoundRectangle2D.Double();
			rect.setRoundRect(0, 0, bounds.width + 20, bounds.height, 15, 15);

			g2.setColor(new Color(0xf2f3f6));
			g2.fill(rect);
		}
	}

	@Override
	public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
		bounds.x = 10;
		super.paintCurrentValue(g, bounds, false);
	}

	@Override
	protected Rectangle rectangleForCurrentValue() {
		int width = comboBox.getWidth();
		int height = comboBox.getHeight();
		Insets insets = getInsets();
		int buttonSize = height - (insets.top + insets.bottom);
		if (arrowButton != null) {
			buttonSize = arrowButton.getWidth();
		}
		if (isLeftToRight(comboBox)) {
			return new Rectangle(insets.left, insets.top, width - (insets.left + insets.right + buttonSize) + 10,
					height - (insets.top + insets.bottom));
		} else {
			return new Rectangle(insets.left + buttonSize, insets.top,
					width - (insets.left + insets.right + buttonSize) + 10, height - (insets.top + insets.bottom));
		}
	}

	boolean isLeftToRight(Component c) {
		return c.getComponentOrientation().isLeftToRight();
	}

	@Override
	protected JButton createArrowButton() {
		JButton b = new JButton();
		b.setUI(new MetalButtonUI() {
			@Override
			public void paint(Graphics g, JComponent c) {
				if (g != null && g instanceof Graphics2D) {
					Graphics2D g2 = (Graphics2D) g;

					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

					g2.setColor(new Color(0x444659));
					g2.drawLine(21, 27, 14, 20);
					g2.drawLine(21, 27, 28, 20);

				}

			}

			@Override
			public Dimension getMaximumSize(JComponent c) {
				return new Dimension(45, super.getMaximumSize(c).height);
			}

			@Override
			public Dimension getMinimumSize(JComponent c) {
				return new Dimension(45, super.getMinimumSize(c).height);
			}

			@Override
			public Dimension getPreferredSize(JComponent c) {
				return new Dimension(45, super.getPreferredSize(c).height);
			}
		});

		b.setBorder(null);
		b.setOpaque(false);
		b.setFocusPainted(false);
		b.setFocusable(false);
		b.setContentAreaFilled(false);
		return b;
	}
}
