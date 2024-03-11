package com.toedter.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.MaskFormatter;

public class JTextFieldDateEditor extends JFormattedTextField implements IDateEditor, CaretListener, FocusListener, ActionListener {
   private static final long serialVersionUID = -8901842591101625304L;
   protected Date date;
   protected SimpleDateFormat dateFormatter;
   protected MaskFormatter maskFormatter;
   protected String datePattern;
   protected String maskPattern;
   protected char placeholder;
   protected Color darkGreen;
   protected DateUtil dateUtil;
   private boolean isMaskVisible;
   private boolean ignoreDatePatternChange;

   public JTextFieldDateEditor() {
      this(false, (String)null, (String)null, ' ');
   }

   public JTextFieldDateEditor(String datePattern, String maskPattern, char placeholder) {
      this(true, datePattern, maskPattern, placeholder);
   }

   public JTextFieldDateEditor(boolean showMask, String datePattern, String maskPattern, char placeholder) {
      this.dateFormatter = (SimpleDateFormat)DateFormat.getDateInstance(2);
      this.dateFormatter.setLenient(false);
      this.setDateFormatString(datePattern);
      if (datePattern != null) {
         this.ignoreDatePatternChange = true;
      }

      this.placeholder = placeholder;
      if (maskPattern == null) {
         this.maskPattern = this.createMaskFromDatePattern(this.datePattern);
      } else {
         this.maskPattern = maskPattern;
      }

      this.setToolTipText(this.datePattern);
      this.setMaskVisible(showMask);
      this.addCaretListener(this);
      this.addFocusListener(this);
      this.addActionListener(this);
      this.darkGreen = new Color(0, 150, 0);
      this.setDateFormatCalendar(Calendar.getInstance());
      this.dateUtil = new DateUtil();
   }

   public Date getDate() {
      try {
         this.date = this.dateFormatter.parse(this.getText());
      } catch (ParseException var2) {
         this.date = null;
      }

      return this.date;
   }

   public void setDate(Date date) {
      this.setDate(date, true);
   }

   protected void setDate(Date date, boolean firePropertyChange) {
      Date oldDate = this.date;
      this.date = date;
      if (date == null) {
         this.setText("");
      } else {
         String formattedDate = this.dateFormatter.format(date);

         try {
            this.setText(formattedDate);
         } catch (RuntimeException var6) {
            var6.printStackTrace();
         }
      }

      if (date != null && this.dateUtil.checkDate(date)) {
         this.setForeground(Color.BLACK);
      }

      if (firePropertyChange) {
         this.firePropertyChange("date", oldDate, date);
      }

   }

   public void setDateFormatString(String dateFormatString) {
      if (!this.ignoreDatePatternChange) {
         try {
            this.dateFormatter.applyPattern(dateFormatString);
         } catch (RuntimeException var3) {
            this.dateFormatter = (SimpleDateFormat)DateFormat.getDateInstance(2);
            this.dateFormatter.setLenient(false);
         }

         this.datePattern = this.dateFormatter.toPattern();
         this.setToolTipText(this.datePattern);
         this.setDate(this.date, false);
      }
   }

   public String getDateFormatString() {
      return this.datePattern;
   }

   public void setDateFormatCalendar(Calendar calendar) {
      this.dateFormatter.setCalendar(calendar != null ? calendar : Calendar.getInstance());
      this.setDate(this.date, false);
   }

   public Calendar getDateFormatCalendar() {
      return this.dateFormatter.getCalendar();
   }

   public JComponent getUiComponent() {
      return this;
   }

   public void caretUpdate(CaretEvent event) {
      String text = this.getText().trim();
      String emptyMask = this.maskPattern.replace('#', this.placeholder);
      if (text.length() != 0 && !text.equals(emptyMask)) {
         try {
            Date date = this.dateFormatter.parse(this.getText());
            if (this.dateUtil.checkDate(date)) {
               this.setForeground(this.darkGreen);
            } else {
               this.setForeground(Color.RED);
            }
         } catch (Exception var5) {
            this.setForeground(Color.RED);
         }

      } else {
         this.setForeground(Color.BLACK);
      }
   }

   public void focusLost(FocusEvent focusEvent) {
      this.checkText();
   }

   private void checkText() {
      try {
         Date date = this.dateFormatter.parse(this.getText());
         this.setDate(date, true);
      } catch (Exception var2) {
      }

   }

   public void focusGained(FocusEvent e) {
   }

   public void setLocale(Locale locale) {
      if (locale != this.getLocale() && !this.ignoreDatePatternChange) {
         super.setLocale(locale);
         this.dateFormatter = (SimpleDateFormat)DateFormat.getDateInstance(2, locale);
         this.setToolTipText(this.dateFormatter.toPattern());
         this.setDate(this.date, false);
         this.doLayout();
      }
   }

   public String createMaskFromDatePattern(String datePattern) {
      String symbols = "GyMdkHmsSEDFwWahKzZ";
      String mask = "";

      for(int i = 0; i < datePattern.length(); ++i) {
         char ch = datePattern.charAt(i);
         boolean symbolFound = false;

         for(int n = 0; n < symbols.length(); ++n) {
            if (symbols.charAt(n) == ch) {
               mask = mask + "#";
               symbolFound = true;
               break;
            }
         }

         if (!symbolFound) {
            mask = mask + ch;
         }
      }

      return mask;
   }

   public boolean isMaskVisible() {
      return this.isMaskVisible;
   }

   public void setMaskVisible(boolean isMaskVisible) {
      this.isMaskVisible = isMaskVisible;
      if (isMaskVisible && this.maskFormatter == null) {
         try {
            this.maskFormatter = new MaskFormatter(this.createMaskFromDatePattern(this.datePattern));
            this.maskFormatter.setPlaceholderCharacter(this.placeholder);
            this.maskFormatter.install(this);
         } catch (ParseException var3) {
            var3.printStackTrace();
         }
      }

   }

   public Dimension getPreferredSize() {
      return this.datePattern != null ? (new JTextField(this.datePattern)).getPreferredSize() : super.getPreferredSize();
   }

   public void actionPerformed(ActionEvent e) {
      this.checkText();
   }

   public void setEnabled(boolean b) {
      super.setEnabled(b);
      if (!b) {
         super.setBackground(UIManager.getColor("TextField.inactiveBackground"));
      }

   }

   public Date getMaxSelectableDate() {
      return this.dateUtil.getMaxSelectableDate();
   }

   public Date getMinSelectableDate() {
      return this.dateUtil.getMinSelectableDate();
   }

   public void setMaxSelectableDate(Date max) {
      this.dateUtil.setMaxSelectableDate(max);
      this.checkText();
   }

   public void setMinSelectableDate(Date min) {
      this.dateUtil.setMinSelectableDate(min);
      this.checkText();
   }

   public void setSelectableDateRange(Date min, Date max) {
      this.dateUtil.setSelectableDateRange(min, max);
      this.checkText();
   }

   public static void main(String[] s) {
      JFrame frame = new JFrame("JTextFieldDateEditor");
      JTextFieldDateEditor jTextFieldDateEditor = new JTextFieldDateEditor();
      jTextFieldDateEditor.setDate(new Date());
      frame.getContentPane().add(jTextFieldDateEditor);
      frame.pack();
      frame.setVisible(true);
   }
}
