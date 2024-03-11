package com.toedter.calendar;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.JSpinner.DateEditor;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JSpinnerDateEditor extends JSpinner implements IDateEditor, FocusListener, ChangeListener {
   private static final long serialVersionUID = 5692204052306085316L;
   protected Date date;
   protected String dateFormatString;
   protected SimpleDateFormat dateFormatter = (SimpleDateFormat)DateFormat.getDateInstance(2);

   public JSpinnerDateEditor() {
      super(new SpinnerDateModel());
      ((DateEditor)this.getEditor()).getTextField().addFocusListener(this);
      DateUtil dateUtil = new DateUtil();
      this.setMinSelectableDate(dateUtil.getMinSelectableDate());
      this.setMaxSelectableDate(dateUtil.getMaxSelectableDate());
      this.addChangeListener(this);
   }

   public Date getDate() {
      return this.date == null ? null : ((SpinnerDateModel)this.getModel()).getDate();
   }

   public void setDate(Date date) {
      this.setDate(date, true);
   }

   public void setDate(Date date, boolean updateModel) {
      Date oldDate = this.date;
      this.date = date;
      if (date == null) {
         ((DateEditor)this.getEditor()).getFormat().applyPattern("");
         ((DateEditor)this.getEditor()).getTextField().setText("");
      } else if (updateModel) {
         if (this.dateFormatString != null) {
            ((DateEditor)this.getEditor()).getFormat().applyPattern(this.dateFormatString);
         }

         this.getModel().setValue(date);
         ((DateEditor)this.getEditor()).getTextField().setText(this.dateFormatter.format(date));
      }

      this.firePropertyChange("date", oldDate, date);
   }

   public void setDateFormatString(String dateFormatString) {
      try {
         this.dateFormatter.applyPattern(dateFormatString);
      } catch (RuntimeException var3) {
         this.dateFormatter = (SimpleDateFormat)DateFormat.getDateInstance(2);
         this.dateFormatter.setLenient(false);
      }

      this.dateFormatString = this.dateFormatter.toPattern();
      this.setToolTipText(this.dateFormatString);
      if (this.date != null) {
         ((DateEditor)this.getEditor()).getFormat().applyPattern(this.dateFormatString);
      } else {
         ((DateEditor)this.getEditor()).getFormat().applyPattern("");
      }

      if (this.date != null) {
         String text = this.dateFormatter.format(this.date);
         ((DateEditor)this.getEditor()).getTextField().setText(text);
      }

   }

   public String getDateFormatString() {
      return this.dateFormatString;
   }

   public void setDateFormatCalendar(Calendar calendar) {
      this.dateFormatter.setCalendar(calendar != null ? calendar : Calendar.getInstance());
   }

   public Calendar getDateFormatCalendar() {
      return this.dateFormatter.getCalendar();
   }

   public JComponent getUiComponent() {
      return this;
   }

   public void setLocale(Locale locale) {
      super.setLocale(locale);
      this.dateFormatter = (SimpleDateFormat)DateFormat.getDateInstance(2, locale);
      this.setDateFormatString(this.dateFormatter.toPattern());
   }

   public void focusLost(FocusEvent focusEvent) {
      String text = ((DateEditor)this.getEditor()).getTextField().getText();
      if (text.length() == 0) {
         this.setDate((Date)null);
      }

   }

   public void focusGained(FocusEvent e) {
   }

   public void setEnabled(boolean b) {
      super.setEnabled(b);
      if (!b) {
         ((DateEditor)this.getEditor()).getTextField().setBackground(UIManager.getColor("TextField.inactiveBackground"));
      }

   }

   public Date getMaxSelectableDate() {
      return (Date)((SpinnerDateModel)this.getModel()).getEnd();
   }

   public Date getMinSelectableDate() {
      return (Date)((SpinnerDateModel)this.getModel()).getStart();
   }

   public void setMaxSelectableDate(Date max) {
      ((SpinnerDateModel)this.getModel()).setEnd(max);
   }

   public void setMinSelectableDate(Date min) {
      ((SpinnerDateModel)this.getModel()).setStart(min);
   }

   public void setSelectableDateRange(Date min, Date max) {
      this.setMaxSelectableDate(max);
      this.setMinSelectableDate(min);
   }

   public void stateChanged(ChangeEvent e) {
      this.setDate(((SpinnerDateModel)this.getModel()).getDate(), false);
   }
}
