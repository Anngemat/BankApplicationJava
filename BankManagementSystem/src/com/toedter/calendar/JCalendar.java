package com.toedter.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JCalendar extends JPanel implements PropertyChangeListener {
   private static final long serialVersionUID = 8913369762644440133L;
   private Calendar calendar;
   protected JDayChooser dayChooser;
   private boolean initialized;
   protected boolean weekOfYearVisible;
   protected Locale locale;
   protected JMonthChooser monthChooser;
   private JPanel monthYearPanel;
   protected JYearChooser yearChooser;
   protected Date minSelectableDate;
   protected Date maxSelectableDate;

   public JCalendar() {
      this((Date)null, (Locale)null, true, true);
   }

   public JCalendar(Date date) {
      this(date, (Locale)null, true, true);
   }

   public JCalendar(Calendar calendar) {
      this((Date)null, (Locale)null, true, true);
      this.setCalendar(calendar);
   }

   public JCalendar(Locale locale) {
      this((Date)null, locale, true, true);
   }

   public JCalendar(Date date, Locale locale) {
      this(date, locale, true, true);
   }

   public JCalendar(Date date, boolean monthSpinner) {
      this(date, (Locale)null, monthSpinner, true);
   }

   public JCalendar(Locale locale, boolean monthSpinner) {
      this((Date)null, locale, monthSpinner, true);
   }

   public JCalendar(boolean monthSpinner) {
      this((Date)null, (Locale)null, monthSpinner, true);
   }

   public JCalendar(Date date, Locale locale, boolean monthSpinner, boolean weekOfYearVisible) {
      this.initialized = false;
      this.weekOfYearVisible = true;
      this.setName("JCalendar");
      this.dayChooser = null;
      this.monthChooser = null;
      this.yearChooser = null;
      this.weekOfYearVisible = weekOfYearVisible;
      this.locale = locale;
      if (locale == null) {
         this.locale = Locale.getDefault();
      }

      this.calendar = Calendar.getInstance();
      this.setLayout(new BorderLayout());
      this.monthYearPanel = new JPanel();
      this.monthYearPanel.setLayout(new BorderLayout());
      this.monthChooser = new JMonthChooser(monthSpinner);
      this.yearChooser = new JYearChooser();
      this.monthChooser.setYearChooser(this.yearChooser);
      this.monthYearPanel.add(this.monthChooser, "West");
      this.monthYearPanel.add(this.yearChooser, "Center");
      this.monthYearPanel.setBorder(BorderFactory.createEmptyBorder());
      this.dayChooser = new JDayChooser(weekOfYearVisible);
      this.dayChooser.addPropertyChangeListener(this);
      this.monthChooser.setDayChooser(this.dayChooser);
      this.monthChooser.addPropertyChangeListener(this);
      this.yearChooser.setDayChooser(this.dayChooser);
      this.yearChooser.addPropertyChangeListener(this);
      this.add(this.monthYearPanel, "North");
      this.add(this.dayChooser, "Center");
      if (date != null) {
         this.calendar.setTime(date);
      }

      this.initialized = true;
      this.setCalendar(this.calendar);
   }

   public static void main(String[] s) {
      JFrame frame = new JFrame("JCalendar");
      JCalendar jcalendar = new JCalendar();
      frame.getContentPane().add(jcalendar);
      frame.pack();
      frame.setVisible(true);
   }

   public Calendar getCalendar() {
      return this.calendar;
   }

   public JDayChooser getDayChooser() {
      return this.dayChooser;
   }

   public Locale getLocale() {
      return this.locale;
   }

   public JMonthChooser getMonthChooser() {
      return this.monthChooser;
   }

   public JYearChooser getYearChooser() {
      return this.yearChooser;
   }

   public boolean isWeekOfYearVisible() {
      return this.dayChooser.isWeekOfYearVisible();
   }

   public void propertyChange(PropertyChangeEvent evt) {
      if (this.calendar != null) {
         Calendar c = (Calendar)this.calendar.clone();
         if (evt.getPropertyName().equals("day")) {
            c.set(5, (Integer)evt.getNewValue());
            this.setCalendar(c, false);
         } else if (evt.getPropertyName().equals("month")) {
            c.set(2, (Integer)evt.getNewValue());
            this.setCalendar(c, false);
         } else if (evt.getPropertyName().equals("year")) {
            c.set(1, (Integer)evt.getNewValue());
            this.setCalendar(c, false);
         } else if (evt.getPropertyName().equals("date")) {
            c.setTime((Date)evt.getNewValue());
            this.setCalendar(c, true);
         }
      }

   }

   public void setBackground(Color bg) {
      super.setBackground(bg);
      if (this.dayChooser != null) {
         this.dayChooser.setBackground(bg);
      }

   }

   public void setCalendar(Calendar c) {
      this.setCalendar(c, true);
   }

   private void setCalendar(Calendar c, boolean update) {
      if (c == null) {
         this.setDate((Date)null);
      }

      Calendar oldCalendar = this.calendar;
      this.calendar = c;
      if (update) {
         this.yearChooser.setYear(c.get(1));
         this.monthChooser.setMonth(c.get(2));
         this.dayChooser.setDay(c.get(5));
      }

      this.firePropertyChange("calendar", oldCalendar, this.calendar);
   }

   public void setEnabled(boolean enabled) {
      super.setEnabled(enabled);
      if (this.dayChooser != null) {
         this.dayChooser.setEnabled(enabled);
         this.monthChooser.setEnabled(enabled);
         this.yearChooser.setEnabled(enabled);
      }

   }

   public boolean isEnabled() {
      return super.isEnabled();
   }

   public void setFont(Font font) {
      super.setFont(font);
      if (this.dayChooser != null) {
         this.dayChooser.setFont(font);
         this.monthChooser.setFont(font);
         this.yearChooser.setFont(font);
      }

   }

   public void setForeground(Color fg) {
      super.setForeground(fg);
      if (this.dayChooser != null) {
         this.dayChooser.setForeground(fg);
         this.monthChooser.setForeground(fg);
         this.yearChooser.setForeground(fg);
      }

   }

   public void setLocale(Locale l) {
      if (!this.initialized) {
         super.setLocale(l);
      } else {
         Locale oldLocale = this.locale;
         this.locale = l;
         this.dayChooser.setLocale(this.locale);
         this.monthChooser.setLocale(this.locale);
         this.firePropertyChange("locale", oldLocale, this.locale);
      }

   }

   public void setWeekOfYearVisible(boolean weekOfYearVisible) {
      this.dayChooser.setWeekOfYearVisible(weekOfYearVisible);
      this.setLocale(this.locale);
   }

   public boolean isDecorationBackgroundVisible() {
      return this.dayChooser.isDecorationBackgroundVisible();
   }

   public void setDecorationBackgroundVisible(boolean decorationBackgroundVisible) {
      this.dayChooser.setDecorationBackgroundVisible(decorationBackgroundVisible);
      this.setLocale(this.locale);
   }

   public boolean isDecorationBordersVisible() {
      return this.dayChooser.isDecorationBordersVisible();
   }

   public void setDecorationBordersVisible(boolean decorationBordersVisible) {
      this.dayChooser.setDecorationBordersVisible(decorationBordersVisible);
      this.setLocale(this.locale);
   }

   public Color getDecorationBackgroundColor() {
      return this.dayChooser.getDecorationBackgroundColor();
   }

   public void setDecorationBackgroundColor(Color decorationBackgroundColor) {
      this.dayChooser.setDecorationBackgroundColor(decorationBackgroundColor);
   }

   public Color getSundayForeground() {
      return this.dayChooser.getSundayForeground();
   }

   public Color getWeekdayForeground() {
      return this.dayChooser.getWeekdayForeground();
   }

   public void setSundayForeground(Color sundayForeground) {
      this.dayChooser.setSundayForeground(sundayForeground);
   }

   public void setWeekdayForeground(Color weekdayForeground) {
      this.dayChooser.setWeekdayForeground(weekdayForeground);
   }

   public Date getDate() {
      return new Date(this.calendar.getTimeInMillis());
   }

   public void setDate(Date date) {
      Date oldDate = this.calendar.getTime();
      this.calendar.setTime(date);
      int year = this.calendar.get(1);
      int month = this.calendar.get(2);
      int day = this.calendar.get(5);
      this.yearChooser.setYear(year);
      this.monthChooser.setMonth(month);
      this.dayChooser.setCalendar(this.calendar);
      this.dayChooser.setDay(day);
      this.firePropertyChange("date", oldDate, date);
   }

   public void setSelectableDateRange(Date min, Date max) {
      this.dayChooser.setSelectableDateRange(min, max);
   }

   public Date getMaxSelectableDate() {
      return this.dayChooser.getMaxSelectableDate();
   }

   public Date getMinSelectableDate() {
      return this.dayChooser.getMinSelectableDate();
   }

   public void setMaxSelectableDate(Date max) {
      this.dayChooser.setMaxSelectableDate(max);
   }

   public void setMinSelectableDate(Date min) {
      this.dayChooser.setMinSelectableDate(min);
   }

   public int getMaxDayCharacters() {
      return this.dayChooser.getMaxDayCharacters();
   }

   public void setMaxDayCharacters(int maxDayCharacters) {
      this.dayChooser.setMaxDayCharacters(maxDayCharacters);
   }
}
