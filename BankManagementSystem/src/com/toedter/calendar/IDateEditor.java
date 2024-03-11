package com.toedter.calendar;

import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JComponent;

public interface IDateEditor {
   Date getDate();

   void setDate(Date var1);

   void setDateFormatCalendar(Calendar var1);

   Calendar getDateFormatCalendar();

   void setDateFormatString(String var1);

   String getDateFormatString();

   void setSelectableDateRange(Date var1, Date var2);

   Date getMaxSelectableDate();

   Date getMinSelectableDate();

   void setMaxSelectableDate(Date var1);

   void setMinSelectableDate(Date var1);

   JComponent getUiComponent();

   void setLocale(Locale var1);

   void setEnabled(boolean var1);

   void addPropertyChangeListener(PropertyChangeListener var1);

   void addPropertyChangeListener(String var1, PropertyChangeListener var2);

   void removePropertyChangeListener(PropertyChangeListener var1);

   void removePropertyChangeListener(String var1, PropertyChangeListener var2);
}
