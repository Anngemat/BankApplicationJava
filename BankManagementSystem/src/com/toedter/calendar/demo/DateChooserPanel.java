package com.toedter.calendar.demo;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JSpinnerDateEditor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.Locale;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DateChooserPanel extends JPanel implements PropertyChangeListener {
   private static final long serialVersionUID = -1282280858252793253L;
   private JComponent[] components;

   public DateChooserPanel() {
      this.setName("JDateChooser");
      GridBagLayout gridbag = new GridBagLayout();
      GridBagConstraints c = new GridBagConstraints();
      c.fill = 1;
      this.setLayout(gridbag);
      this.components = new JComponent[5];
      this.components[0] = new JDateChooser();
      this.components[1] = new JDateChooser(new Date());
      this.components[2] = new JDateChooser((JCalendar)null, (Date)null, (String)null, new JSpinnerDateEditor());
      this.components[3] = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
      this.components[4] = new DemoTable();
      this.addEntry("Default", this.components[0], gridbag);
      this.addEntry("Default with date set", this.components[1], gridbag);
      this.addEntry("Spinner Editor", this.components[2], gridbag);
      this.addEntry("Explicite date pattern and mask", this.components[3], gridbag);
      this.addEntry("Table with date editors", this.components[4], gridbag);
   }

   private void addEntry(String text, JComponent component, GridBagLayout grid) {
      JLabel label = new JLabel(text + ": ", (Icon)null, 4);
      GridBagConstraints c = new GridBagConstraints();
      c.weightx = 1.0D;
      c.fill = 1;
      grid.setConstraints(label, c);
      this.add(label);
      c.gridwidth = 0;
      grid.setConstraints(component, c);
      this.add(component);
      JPanel blankLine = new JPanel() {
         private static final long serialVersionUID = 4514530330521503732L;

         public Dimension getPreferredSize() {
            return new Dimension(10, 3);
         }
      };
      grid.setConstraints(blankLine, c);
      this.add(blankLine);
   }

   public String getDateFormatString() {
      return ((JDateChooser)this.components[1]).getDateFormatString();
   }

   public void setDateFormatString(String dfString) {
      for(int i = 0; i < 4; ++i) {
         ((JDateChooser)this.components[i]).setDateFormatString(dfString);
      }

   }

   public Date getDate() {
      return ((JDateChooser)this.components[1]).getDate();
   }

   public void setDate(Date date) {
      for(int i = 0; i < 4; ++i) {
         ((JDateChooser)this.components[i]).setDate(date);
      }

   }

   public void propertyChange(PropertyChangeEvent evt) {
      if (evt.getPropertyName().equals("date")) {
         this.setDate((Date)evt.getNewValue());
      }

   }

   public Locale getLocale() {
      return ((JDateChooser)this.components[0]).getLocale();
   }

   public void setLocale(Locale locale) {
      for(int i = 0; i < 5; ++i) {
         this.components[i].setLocale(locale);
      }

   }

   public boolean isEnabled() {
      return ((JDateChooser)this.components[0]).isEnabled();
   }

   public void setEnabled(boolean enabled) {
      for(int i = 0; i < 5; ++i) {
         this.components[i].setEnabled(enabled);
      }

   }

   public Date getMinSelectableDate() {
      return ((JDateChooser)this.components[0]).getMinSelectableDate();
   }

   public void setMinSelectableDate(Date date) {
      for(int i = 0; i < 4; ++i) {
         ((JDateChooser)this.components[i]).setMinSelectableDate(date);
      }

   }

   public Date getMaxSelectableDate() {
      return ((JDateChooser)this.components[0]).getMaxSelectableDate();
   }

   public void setMaxSelectableDate(Date date) {
      for(int i = 0; i < 4; ++i) {
         ((JDateChooser)this.components[i]).setMaxSelectableDate(date);
      }

   }
}
