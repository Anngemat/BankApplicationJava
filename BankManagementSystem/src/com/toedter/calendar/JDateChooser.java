package com.toedter.calendar;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JDateChooser extends JPanel implements ActionListener, PropertyChangeListener {
   private static final long serialVersionUID = -4306412745720670722L;
   protected IDateEditor dateEditor;
   protected JButton calendarButton;
   protected JCalendar jcalendar;
   protected JPopupMenu popup;
   protected boolean isInitialized;
   protected boolean dateSelected;
   protected Date lastSelectedDate;
   private ChangeListener changeListener;

   public JDateChooser() {
      this((JCalendar)null, (Date)null, (String)null, (IDateEditor)null);
   }

   public JDateChooser(IDateEditor dateEditor) {
      this((JCalendar)null, (Date)null, (String)null, dateEditor);
   }

   public JDateChooser(Date date) {
      this(date, (String)null);
   }

   public JDateChooser(Date date, String dateFormatString) {
      this(date, dateFormatString, (IDateEditor)null);
   }

   public JDateChooser(Date date, String dateFormatString, IDateEditor dateEditor) {
      this((JCalendar)null, date, dateFormatString, dateEditor);
   }

   public JDateChooser(String datePattern, String maskPattern, char placeholder) {
      this((JCalendar)null, (Date)null, datePattern, new JTextFieldDateEditor(datePattern, maskPattern, placeholder));
   }

   public JDateChooser(JCalendar jcal, Date date, String dateFormatString, IDateEditor dateEditor) {
      this.setName("JDateChooser");
      this.dateEditor = dateEditor;
      if (this.dateEditor == null) {
         this.dateEditor = new JTextFieldDateEditor();
      }

      this.dateEditor.addPropertyChangeListener("date", this);
      if (jcal == null) {
         this.jcalendar = new JCalendar(date);
      } else {
         this.jcalendar = jcal;
         if (date != null) {
            this.jcalendar.setDate(date);
         }
      }

      this.setLayout(new BorderLayout());
      this.jcalendar.getDayChooser().addPropertyChangeListener("day", this);
      this.jcalendar.getDayChooser().setAlwaysFireDayProperty(true);
      this.setDateFormatString(dateFormatString);
      this.setDate(date);
      URL iconURL = this.getClass().getResource("/com/toedter/calendar/images/JDateChooserIcon.gif");
      ImageIcon icon = new ImageIcon(iconURL);
      this.calendarButton = new JButton(icon) {
         private static final long serialVersionUID = -1913767779079949668L;

         public boolean isFocusable() {
            return false;
         }
      };
      this.calendarButton.setMargin(new Insets(0, 0, 0, 0));
      this.calendarButton.addActionListener(this);
      this.calendarButton.setMnemonic(67);
      this.add(this.calendarButton, "East");
      this.add(this.dateEditor.getUiComponent(), "Center");
      this.calendarButton.setMargin(new Insets(0, 0, 0, 0));
      this.popup = new JPopupMenu() {
         private static final long serialVersionUID = -6078272560337577761L;

         public void setVisible(boolean b) {
            Boolean isCanceled = (Boolean)this.getClientProperty("JPopupMenu.firePopupMenuCanceled");
            if (b || !b && JDateChooser.this.dateSelected || isCanceled != null && !b && isCanceled) {
               super.setVisible(b);
            }

         }
      };
      this.popup.setLightWeightPopupEnabled(true);
      this.popup.add(this.jcalendar);
      this.lastSelectedDate = date;
      this.changeListener = new ChangeListener() {
         boolean hasListened = false;

         public void stateChanged(ChangeEvent e) {
            if (this.hasListened) {
               this.hasListened = false;
            } else {
               if (JDateChooser.this.popup.isVisible() && JDateChooser.this.jcalendar.monthChooser.getComboBox().hasFocus()) {
                  MenuElement[] me = MenuSelectionManager.defaultManager().getSelectedPath();
                  MenuElement[] newMe = new MenuElement[me.length + 1];
                  newMe[0] = JDateChooser.this.popup;

                  for(int i = 0; i < me.length; ++i) {
                     newMe[i + 1] = me[i];
                  }

                  this.hasListened = true;
                  MenuSelectionManager.defaultManager().setSelectedPath(newMe);
               }

            }
         }
      };
      MenuSelectionManager.defaultManager().addChangeListener(this.changeListener);
      this.isInitialized = true;
   }

   public void actionPerformed(ActionEvent e) {
      int x = this.calendarButton.getWidth() - (int)this.popup.getPreferredSize().getWidth();
      int y = this.calendarButton.getY() + this.calendarButton.getHeight();
      Calendar calendar = Calendar.getInstance();
      Date date = this.dateEditor.getDate();
      if (date != null) {
         calendar.setTime(date);
      }

      this.jcalendar.setCalendar(calendar);
      this.popup.show(this.calendarButton, x, y);
      this.dateSelected = false;
   }

   public void propertyChange(PropertyChangeEvent evt) {
      if (evt.getPropertyName().equals("day")) {
         if (this.popup.isVisible() && this.jcalendar.getCalendar().get(2) == this.jcalendar.monthChooser.getMonth()) {
            this.dateSelected = true;
            this.popup.setVisible(false);
            this.setDate(this.jcalendar.getCalendar().getTime());
         }
      } else if (evt.getPropertyName().equals("date")) {
         if (evt.getSource() == this.dateEditor) {
            this.firePropertyChange("date", evt.getOldValue(), evt.getNewValue());
         } else {
            this.setDate((Date)evt.getNewValue());
         }
      }

   }

   public void updateUI() {
      super.updateUI();
      this.setEnabled(this.isEnabled());
      if (this.jcalendar != null) {
         SwingUtilities.updateComponentTreeUI(this.popup);
      }

   }

   public void setLocale(Locale l) {
      super.setLocale(l);
      this.dateEditor.setLocale(l);
      this.jcalendar.setLocale(l);
   }

   public String getDateFormatString() {
      return this.dateEditor.getDateFormatString();
   }

   public void setDateFormatString(String dfString) {
      this.dateEditor.setDateFormatString(dfString);
      this.invalidate();
   }

   public Date getDate() {
      return this.dateEditor.getDate();
   }

   public void setDate(Date date) {
      this.dateEditor.setDate(date);
      if (this.getParent() != null) {
         this.getParent().invalidate();
      }

   }

   public Calendar getCalendar() {
      Date date = this.getDate();
      if (date == null) {
         return null;
      } else {
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
         return calendar;
      }
   }

   public void setCalendar(Calendar calendar) {
      if (calendar == null) {
         this.dateEditor.setDate((Date)null);
      } else {
         this.dateEditor.setDate(calendar.getTime());
      }

   }

   public void setDateFormatCalendar(Calendar calendar) {
      this.dateEditor.setDateFormatCalendar(calendar);
   }

   public Calendar getDateFormatCalendar() {
      return this.dateEditor.getDateFormatCalendar();
   }

   public void setEnabled(boolean enabled) {
      super.setEnabled(enabled);
      if (this.dateEditor != null) {
         this.dateEditor.setEnabled(enabled);
         this.calendarButton.setEnabled(enabled);
      }

   }

   public boolean isEnabled() {
      return super.isEnabled();
   }

   public void setIcon(ImageIcon icon) {
      this.calendarButton.setIcon(icon);
   }

   public void setFont(Font font) {
      if (this.isInitialized) {
         this.dateEditor.getUiComponent().setFont(font);
         this.jcalendar.setFont(font);
      }

      super.setFont(font);
   }

   public JCalendar getJCalendar() {
      return this.jcalendar;
   }

   public JButton getCalendarButton() {
      return this.calendarButton;
   }

   public IDateEditor getDateEditor() {
      return this.dateEditor;
   }

   public void setSelectableDateRange(Date min, Date max) {
      this.jcalendar.setSelectableDateRange(min, max);
      this.dateEditor.setSelectableDateRange(this.jcalendar.getMinSelectableDate(), this.jcalendar.getMaxSelectableDate());
   }

   public void setMaxSelectableDate(Date max) {
      this.jcalendar.setMaxSelectableDate(max);
      this.dateEditor.setMaxSelectableDate(max);
   }

   public void setMinSelectableDate(Date min) {
      this.jcalendar.setMinSelectableDate(min);
      this.dateEditor.setMinSelectableDate(min);
   }

   public Date getMaxSelectableDate() {
      return this.jcalendar.getMaxSelectableDate();
   }

   public Date getMinSelectableDate() {
      return this.jcalendar.getMinSelectableDate();
   }

   public void cleanup() {
      MenuSelectionManager.defaultManager().removeChangeListener(this.changeListener);
      this.changeListener = null;
   }

   public static void main(String[] s) {
      JFrame frame = new JFrame("JDateChooser");
      JDateChooser dateChooser = new JDateChooser();
      frame.getContentPane().add(dateChooser);
      frame.pack();
      frame.setVisible(true);
   }
}
