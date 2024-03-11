package com.toedter.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JSpinField extends JPanel implements ChangeListener, CaretListener, ActionListener, FocusListener {
   private static final long serialVersionUID = 1694904792717740650L;
   protected JSpinner spinner;
   protected JTextField textField;
   protected int min;
   protected int max;
   protected int value;
   protected Color darkGreen;

   public JSpinField() {
      this(Integer.MIN_VALUE, Integer.MAX_VALUE);
   }

   public JSpinField(int min, int max) {
      this.setName("JSpinField");
      this.min = min;
      if (max < min) {
         max = min;
      }

      this.max = max;
      this.value = 0;
      if (this.value < min) {
         this.value = min;
      }

      if (this.value > max) {
         this.value = max;
      }

      this.darkGreen = new Color(0, 150, 0);
      this.setLayout(new BorderLayout());
      this.textField = new JTextField();
      this.textField.addCaretListener(this);
      this.textField.addActionListener(this);
      this.textField.setHorizontalAlignment(4);
      this.textField.setBorder(BorderFactory.createEmptyBorder());
      this.textField.setText(Integer.toString(this.value));
      this.textField.addFocusListener(this);
      this.spinner = new JSpinner() {
         private static final long serialVersionUID = -6287709243342021172L;
         private JTextField textField = new JTextField();

         public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            return new Dimension(size.width, this.textField.getPreferredSize().height);
         }
      };
      this.spinner.setEditor(this.textField);
      this.spinner.addChangeListener(this);
      this.add(this.spinner, "Center");
   }

   public void adjustWidthToMaximumValue() {
      JTextField testTextField = new JTextField(Integer.toString(this.max));
      int width = testTextField.getPreferredSize().width;
      int height = testTextField.getPreferredSize().height;
      this.textField.setPreferredSize(new Dimension(width, height));
      this.textField.revalidate();
   }

   public void stateChanged(ChangeEvent e) {
      SpinnerNumberModel model = (SpinnerNumberModel)this.spinner.getModel();
      int value = model.getNumber().intValue();
      this.setValue(value);
   }

   protected void setValue(int newValue, boolean updateTextField, boolean firePropertyChange) {
      int oldValue = this.value;
      if (newValue < this.min) {
         this.value = this.min;
      } else if (newValue > this.max) {
         this.value = this.max;
      } else {
         this.value = newValue;
      }

      if (updateTextField) {
         this.textField.setText(Integer.toString(this.value));
         this.textField.setForeground(Color.black);
      }

      if (firePropertyChange) {
         this.firePropertyChange("value", oldValue, this.value);
      }

   }

   public void setValue(int newValue) {
      this.setValue(newValue, true, true);
      this.spinner.setValue(new Integer(this.value));
   }

   public int getValue() {
      return this.value;
   }

   public void setMinimum(int newMinimum) {
      this.min = newMinimum;
   }

   public int getMinimum() {
      return this.min;
   }

   public void setMaximum(int newMaximum) {
      this.max = newMaximum;
   }

   public void setHorizontalAlignment(int alignment) {
      this.textField.setHorizontalAlignment(alignment);
   }

   public int getMaximum() {
      return this.max;
   }

   public void setFont(Font font) {
      if (this.textField != null) {
         this.textField.setFont(font);
      }

   }

   public void setForeground(Color fg) {
      if (this.textField != null) {
         this.textField.setForeground(fg);
      }

   }

   public void caretUpdate(CaretEvent e) {
      try {
         int testValue = Integer.valueOf(this.textField.getText());
         if (testValue >= this.min && testValue <= this.max) {
            this.textField.setForeground(this.darkGreen);
            this.setValue(testValue, false, true);
         } else {
            this.textField.setForeground(Color.red);
         }
      } catch (Exception var3) {
         if (var3 instanceof NumberFormatException) {
            this.textField.setForeground(Color.red);
         }
      }

      this.textField.repaint();
   }

   public void actionPerformed(ActionEvent e) {
      if (this.textField.getForeground().equals(this.darkGreen)) {
         this.setValue(Integer.valueOf(this.textField.getText()));
      }

   }

   public void setEnabled(boolean enabled) {
      super.setEnabled(enabled);
      this.spinner.setEnabled(enabled);
      this.textField.setEnabled(enabled);
      if (!enabled) {
         this.textField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
      }

   }

   public Component getSpinner() {
      return this.spinner;
   }

   public static void main(String[] s) {
      JFrame frame = new JFrame("JSpinField");
      frame.getContentPane().add(new JSpinField());
      frame.pack();
      frame.setVisible(true);
   }

   public void focusGained(FocusEvent e) {
   }

   public void focusLost(FocusEvent e) {
      this.actionPerformed((ActionEvent)null);
   }
}
