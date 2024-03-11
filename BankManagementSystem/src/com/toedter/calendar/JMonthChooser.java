package com.toedter.calendar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JMonthChooser extends JPanel implements ItemListener, ChangeListener {
   private static final long serialVersionUID = -2028361332231218527L;
   protected boolean hasSpinner;
   private Locale locale;
   private int month;
   private int oldSpinnerValue;
   private JDayChooser dayChooser;
   private JYearChooser yearChooser;
   private JComboBox comboBox;
   private JSpinner spinner;
   private boolean initialized;
   private boolean localInitialize;

   public JMonthChooser() {
      this(true);
   }

   public JMonthChooser(boolean hasSpinner) {
      this.oldSpinnerValue = 0;
      this.setName("JMonthChooser");
      this.hasSpinner = hasSpinner;
      this.setLayout(new BorderLayout());
      this.comboBox = new JComboBox();
      this.comboBox.addItemListener(this);
      this.locale = Locale.getDefault();
      this.initNames();
      if (hasSpinner) {
         this.spinner = new JSpinner() {
            private static final long serialVersionUID = 1L;
            private JTextField textField = new JTextField();

            public Dimension getPreferredSize() {
               Dimension size = super.getPreferredSize();
               return new Dimension(size.width, this.textField.getPreferredSize().height);
            }
         };
         this.spinner.addChangeListener(this);
         this.spinner.setEditor(this.comboBox);
         this.comboBox.setBorder(new EmptyBorder(0, 0, 0, 0));
         this.updateUI();
         this.add(this.spinner, "West");
      } else {
         this.add(this.comboBox, "West");
      }

      this.initialized = true;
      this.setMonth(Calendar.getInstance().get(2));
   }

   public void initNames() {
      this.localInitialize = true;
      DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(this.locale);
      String[] monthNames = dateFormatSymbols.getMonths();
      if (this.comboBox.getItemCount() == 12) {
         this.comboBox.removeAllItems();
      }

      for(int i = 0; i < 12; ++i) {
         this.comboBox.addItem(monthNames[i]);
      }

      this.localInitialize = false;
      this.comboBox.setSelectedIndex(this.month);
   }

   public void stateChanged(ChangeEvent e) {
      SpinnerNumberModel model = (SpinnerNumberModel)((JSpinner)e.getSource()).getModel();
      int value = model.getNumber().intValue();
      boolean increase = value > this.oldSpinnerValue;
      this.oldSpinnerValue = value;
      int month = this.getMonth();
      int year;
      if (increase) {
         ++month;
         if (month == 12) {
            month = 0;
            if (this.yearChooser != null) {
               year = this.yearChooser.getYear();
               ++year;
               this.yearChooser.setYear(year);
            }
         }
      } else {
         --month;
         if (month == -1) {
            month = 11;
            if (this.yearChooser != null) {
               year = this.yearChooser.getYear();
               --year;
               this.yearChooser.setYear(year);
            }
         }
      }

      this.setMonth(month);
   }

   public void itemStateChanged(ItemEvent e) {
      if (e.getStateChange() == 1) {
         int index = this.comboBox.getSelectedIndex();
         if (index >= 0 && index != this.month) {
            this.setMonth(index, false);
         }
      }

   }

   private void setMonth(int newMonth, boolean select) {
      if (this.initialized && !this.localInitialize) {
         int oldMonth = this.month;
         this.month = newMonth;
         if (select) {
            this.comboBox.setSelectedIndex(this.month);
         }

         if (this.dayChooser != null) {
            this.dayChooser.setMonth(this.month);
         }

         this.firePropertyChange("month", oldMonth, this.month);
      }
   }

   public void setMonth(int newMonth) {
      if (newMonth >= 0 && newMonth != Integer.MIN_VALUE) {
         if (newMonth > 11) {
            this.setMonth(11, true);
         } else {
            this.setMonth(newMonth, true);
         }
      } else {
         this.setMonth(0, true);
      }

   }

   public int getMonth() {
      return this.month;
   }

   public void setDayChooser(JDayChooser dayChooser) {
      this.dayChooser = dayChooser;
   }

   public void setYearChooser(JYearChooser yearChooser) {
      this.yearChooser = yearChooser;
   }

   public Locale getLocale() {
      return this.locale;
   }

   public void setLocale(Locale l) {
      if (!this.initialized) {
         super.setLocale(l);
      } else {
         this.locale = l;
         this.initNames();
      }

   }

   public void setEnabled(boolean enabled) {
      super.setEnabled(enabled);
      this.comboBox.setEnabled(enabled);
      if (this.spinner != null) {
         this.spinner.setEnabled(enabled);
      }

   }

   public Component getComboBox() {
      return this.comboBox;
   }

   public Component getSpinner() {
      return this.spinner;
   }

   public boolean hasSpinner() {
      return this.hasSpinner;
   }

   public void setFont(Font font) {
      if (this.comboBox != null) {
         this.comboBox.setFont(font);
      }

      super.setFont(font);
   }

   public void updateUI() {
      JSpinner testSpinner = new JSpinner();
      if (this.spinner != null) {
         if ("Windows".equals(UIManager.getLookAndFeel().getID())) {
            this.spinner.setBorder(testSpinner.getBorder());
         } else {
            this.spinner.setBorder(new EmptyBorder(0, 0, 0, 0));
         }
      }

   }

   public static void main(String[] s) {
      JFrame frame = new JFrame("MonthChooser");
      frame.getContentPane().add(new JMonthChooser());
      frame.pack();
      frame.setVisible(true);
   }
}
