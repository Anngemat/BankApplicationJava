package com.toedter.calendar;

import com.toedter.components.JSpinField;
import java.util.Calendar;
import javax.swing.JFrame;

public class JYearChooser extends JSpinField {
   private static final long serialVersionUID = 2648810220491090064L;
   protected JDayChooser dayChooser;
   protected int oldYear;
   protected int startYear;
   protected int endYear;

   public JYearChooser() {
      this.setName("JYearChooser");
      Calendar calendar = Calendar.getInstance();
      this.dayChooser = null;
      this.setMinimum(calendar.getMinimum(1));
      this.setMaximum(calendar.getMaximum(1));
      this.setValue(calendar.get(1));
   }

   public void setYear(int y) {
      super.setValue(y, true, false);
      if (this.dayChooser != null) {
         this.dayChooser.setYear(this.value);
      }

      this.spinner.setValue(new Integer(this.value));
      this.firePropertyChange("year", this.oldYear, this.value);
      this.oldYear = this.value;
   }

   public void setValue(int value) {
      this.setYear(value);
   }

   public int getYear() {
      return super.getValue();
   }

   public void setDayChooser(JDayChooser dayChooser) {
      this.dayChooser = dayChooser;
   }

   public int getEndYear() {
      return this.getMaximum();
   }

   public void setEndYear(int endYear) {
      this.setMaximum(endYear);
   }

   public int getStartYear() {
      return this.getMinimum();
   }

   public void setStartYear(int startYear) {
      this.setMinimum(startYear);
   }

   public static void main(String[] s) {
      JFrame frame = new JFrame("JYearChooser");
      frame.getContentPane().add(new JYearChooser());
      frame.pack();
      frame.setVisible(true);
   }
}
