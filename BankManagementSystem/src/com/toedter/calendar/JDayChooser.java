package com.toedter.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class JDayChooser extends JPanel implements ActionListener, KeyListener, FocusListener {
   private static final long serialVersionUID = 5876398337018781820L;
   protected JButton[] days;
   protected JButton[] weeks;
   protected JButton selectedDay;
   protected JPanel weekPanel;
   protected JPanel dayPanel;
   protected int day;
   protected Color oldDayBackgroundColor;
   protected Color selectedColor;
   protected Color sundayForeground;
   protected Color weekdayForeground;
   protected Color decorationBackgroundColor;
   protected String[] dayNames;
   protected Calendar calendar;
   protected Calendar today;
   protected Locale locale;
   protected boolean initialized;
   protected boolean weekOfYearVisible;
   protected boolean decorationBackgroundVisible;
   protected boolean decorationBordersVisible;
   protected boolean dayBordersVisible;
   private boolean alwaysFireDayProperty;
   protected Date minSelectableDate;
   protected Date maxSelectableDate;
   protected Date defaultMinSelectableDate;
   protected Date defaultMaxSelectableDate;
   protected int maxDayCharacters;

   public JDayChooser() {
      this(false);
   }

   public JDayChooser(boolean weekOfYearVisible) {
      this.decorationBackgroundVisible = true;
      this.setName("JDayChooser");
      this.setBackground(Color.blue);
      this.weekOfYearVisible = weekOfYearVisible;
      this.locale = Locale.getDefault();
      this.days = new JButton[49];
      this.selectedDay = null;
      this.calendar = Calendar.getInstance(this.locale);
      this.today = (Calendar)this.calendar.clone();
      this.setLayout(new BorderLayout());
      this.dayPanel = new JPanel();
      this.dayPanel.setLayout(new GridLayout(7, 7));
      this.sundayForeground = new Color(164, 0, 0);
      this.weekdayForeground = new Color(0, 90, 164);
      this.decorationBackgroundColor = new Color(210, 228, 238);

      int i;
      for(i = 0; i < 7; ++i) {
         for(int x = 0; x < 7; ++x) {
            int index = x + 7 * i;
            if (i == 0) {
               this.days[index] = new JDayChooser.DecoratorButton();
            } else {
               this.days[index] = new JButton("x") {
                  private static final long serialVersionUID = -7433645992591669725L;

                  public void paint(Graphics g) {
                     if ("Windows".equals(UIManager.getLookAndFeel().getID()) && JDayChooser.this.selectedDay == this) {
                        g.setColor(JDayChooser.this.selectedColor);
                        g.fillRect(0, 0, this.getWidth(), this.getHeight());
                     }

                     super.paint(g);
                  }
               };
               this.days[index].addActionListener(this);
               this.days[index].addKeyListener(this);
               this.days[index].addFocusListener(this);
            }

            this.days[index].setMargin(new Insets(0, 0, 0, 0));
            this.days[index].setFocusPainted(false);
            this.dayPanel.add(this.days[index]);
         }
      }

      this.weekPanel = new JPanel();
      this.weekPanel.setLayout(new GridLayout(7, 1));
      this.weeks = new JButton[7];

      for(i = 0; i < 7; ++i) {
         this.weeks[i] = new JDayChooser.DecoratorButton();
         this.weeks[i].setMargin(new Insets(0, 0, 0, 0));
         this.weeks[i].setFocusPainted(false);
         this.weeks[i].setForeground(new Color(100, 100, 100));
         if (i != 0) {
            this.weeks[i].setText("0" + (i + 1));
         }

         this.weekPanel.add(this.weeks[i]);
      }

      Calendar tmpCalendar = Calendar.getInstance();
      tmpCalendar.set(1, 0, 1, 1, 1);
      this.defaultMinSelectableDate = tmpCalendar.getTime();
      this.minSelectableDate = this.defaultMinSelectableDate;
      tmpCalendar.set(9999, 0, 1, 1, 1);
      this.defaultMaxSelectableDate = tmpCalendar.getTime();
      this.maxSelectableDate = this.defaultMaxSelectableDate;
      this.init();
      this.setDay(Calendar.getInstance().get(5));
      this.add(this.dayPanel, "Center");
      if (weekOfYearVisible) {
         this.add(this.weekPanel, "West");
      }

      this.initialized = true;
      this.updateUI();
   }

   protected void init() {
      JButton testButton = new JButton();
      this.oldDayBackgroundColor = testButton.getBackground();
      this.selectedColor = new Color(160, 160, 160);
      Date date = this.calendar.getTime();
      this.calendar = Calendar.getInstance(this.locale);
      this.calendar.setTime(date);
      this.drawDayNames();
      this.drawDays();
   }

   private void drawDayNames() {
      int firstDayOfWeek = this.calendar.getFirstDayOfWeek();
      DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(this.locale);
      this.dayNames = dateFormatSymbols.getShortWeekdays();
      int day = firstDayOfWeek;

      for(int i = 0; i < 7; ++i) {
         if (this.maxDayCharacters > 0 && this.maxDayCharacters < 5 && this.dayNames[day].length() >= this.maxDayCharacters) {
            this.dayNames[day] = this.dayNames[day].substring(0, this.maxDayCharacters);
         }

         this.days[i].setText(this.dayNames[day]);
         if (day == 1) {
            this.days[i].setForeground(this.sundayForeground);
         } else {
            this.days[i].setForeground(this.weekdayForeground);
         }

         if (day < 7) {
            ++day;
         } else {
            day -= 6;
         }
      }

   }

   protected void initDecorations() {
      for(int x = 0; x < 7; ++x) {
         this.days[x].setContentAreaFilled(this.decorationBackgroundVisible);
         this.days[x].setBorderPainted(this.decorationBordersVisible);
         this.days[x].invalidate();
         this.days[x].repaint();
         this.weeks[x].setContentAreaFilled(this.decorationBackgroundVisible);
         this.weeks[x].setBorderPainted(this.decorationBordersVisible);
         this.weeks[x].invalidate();
         this.weeks[x].repaint();
      }

   }

   protected void drawWeeks() {
      Calendar tmpCalendar = (Calendar)this.calendar.clone();

      for(int i = 1; i < 7; ++i) {
         tmpCalendar.set(5, i * 7 - 6);
         int week = tmpCalendar.get(3);
         String buttonText = Integer.toString(week);
         if (week < 10) {
            buttonText = "0" + buttonText;
         }

         this.weeks[i].setText(buttonText);
         if (i == 5 || i == 6) {
            this.weeks[i].setVisible(this.days[i * 7].isVisible());
         }
      }

   }

   protected void drawDays() {
      Calendar tmpCalendar = (Calendar)this.calendar.clone();
      tmpCalendar.set(11, 0);
      tmpCalendar.set(12, 0);
      tmpCalendar.set(13, 0);
      tmpCalendar.set(14, 0);
      Calendar minCal = Calendar.getInstance();
      minCal.setTime(this.minSelectableDate);
      minCal.set(11, 0);
      minCal.set(12, 0);
      minCal.set(13, 0);
      minCal.set(14, 0);
      Calendar maxCal = Calendar.getInstance();
      maxCal.setTime(this.maxSelectableDate);
      maxCal.set(11, 0);
      maxCal.set(12, 0);
      maxCal.set(13, 0);
      maxCal.set(14, 0);
      int firstDayOfWeek = tmpCalendar.getFirstDayOfWeek();
      tmpCalendar.set(5, 1);
      int firstDay = tmpCalendar.get(7) - firstDayOfWeek;
      if (firstDay < 0) {
         firstDay += 7;
      }

      int i;
      for(i = 0; i < firstDay; ++i) {
         this.days[i + 7].setVisible(false);
         this.days[i + 7].setText("");
      }

      tmpCalendar.add(2, 1);
      Date firstDayInNextMonth = tmpCalendar.getTime();
      tmpCalendar.add(2, -1);
      Date day = tmpCalendar.getTime();
      int n = 0;

      for(Color foregroundColor = this.getForeground(); day.before(firstDayInNextMonth); day = tmpCalendar.getTime()) {
         this.days[i + n + 7].setText(Integer.toString(n + 1));
         this.days[i + n + 7].setVisible(true);
         if (tmpCalendar.get(6) == this.today.get(6) && tmpCalendar.get(1) == this.today.get(1)) {
            this.days[i + n + 7].setForeground(this.sundayForeground);
         } else {
            this.days[i + n + 7].setForeground(foregroundColor);
         }

         if (n + 1 == this.day) {
            this.days[i + n + 7].setBackground(this.selectedColor);
            this.selectedDay = this.days[i + n + 7];
         } else {
            this.days[i + n + 7].setBackground(this.oldDayBackgroundColor);
         }

         if (!tmpCalendar.before(minCal) && !tmpCalendar.after(maxCal)) {
            this.days[i + n + 7].setEnabled(true);
         } else {
            this.days[i + n + 7].setEnabled(false);
         }

         ++n;
         tmpCalendar.add(5, 1);
      }

      for(int k = n + i + 7; k < 49; ++k) {
         this.days[k].setVisible(false);
         this.days[k].setText("");
      }

      this.drawWeeks();
   }

   public Locale getLocale() {
      return this.locale;
   }

   public void setLocale(Locale locale) {
      if (!this.initialized) {
         super.setLocale(locale);
      } else {
         this.locale = locale;
         super.setLocale(locale);
         this.init();
      }

   }

   public void setDay(int d) {
      if (d < 1) {
         d = 1;
      }

      Calendar tmpCalendar = (Calendar)this.calendar.clone();
      tmpCalendar.set(5, 1);
      tmpCalendar.add(2, 1);
      tmpCalendar.add(5, -1);
      int maxDaysInMonth = tmpCalendar.get(5);
      if (d > maxDaysInMonth) {
         d = maxDaysInMonth;
      }

      int oldDay = this.day;
      this.day = d;
      if (this.selectedDay != null) {
         this.selectedDay.setBackground(this.oldDayBackgroundColor);
         this.selectedDay.repaint();
      }

      for(int i = 7; i < 49; ++i) {
         if (this.days[i].getText().equals(Integer.toString(this.day))) {
            this.selectedDay = this.days[i];
            this.selectedDay.setBackground(this.selectedColor);
            break;
         }
      }

      if (this.alwaysFireDayProperty) {
         this.firePropertyChange("day", 0, this.day);
      } else {
         this.firePropertyChange("day", oldDay, this.day);
      }

   }

   public void setAlwaysFireDayProperty(boolean alwaysFire) {
      this.alwaysFireDayProperty = alwaysFire;
   }

   public int getDay() {
      return this.day;
   }

   public void setMonth(int month) {
      this.calendar.set(2, month);
      int maxDays = this.calendar.getActualMaximum(5);
      int adjustedDay = this.day;
      if (this.day > maxDays) {
         this.setDay(maxDays);
      }

      this.drawDays();
   }

   public void setYear(int year) {
      this.calendar.set(1, year);
      this.drawDays();
   }

   public void setCalendar(Calendar calendar) {
      this.calendar = calendar;
      this.drawDays();
   }

   public void setFont(Font font) {
      int i;
      if (this.days != null) {
         for(i = 0; i < 49; ++i) {
            this.days[i].setFont(font);
         }
      }

      if (this.weeks != null) {
         for(i = 0; i < 7; ++i) {
            this.weeks[i].setFont(font);
         }
      }

   }

   public void setForeground(Color foreground) {
      super.setForeground(foreground);
      if (this.days != null) {
         for(int i = 7; i < 49; ++i) {
            this.days[i].setForeground(foreground);
         }

         this.drawDays();
      }

   }

   public void actionPerformed(ActionEvent e) {
      JButton button = (JButton)e.getSource();
      String buttonText = button.getText();
      int day = new Integer(buttonText);
      this.setDay(day);
   }

   public void focusGained(FocusEvent e) {
   }

   public void focusLost(FocusEvent e) {
   }

   public void keyPressed(KeyEvent e) {
      int offset = e.getKeyCode() == 38 ? -7 : (e.getKeyCode() == 40 ? 7 : (e.getKeyCode() == 37 ? -1 : (e.getKeyCode() == 39 ? 1 : 0)));
      int newDay = this.getDay() + offset;
      if (newDay >= 1 && newDay <= this.calendar.getMaximum(5)) {
         this.setDay(newDay);
      }

   }

   public void keyTyped(KeyEvent e) {
   }

   public void keyReleased(KeyEvent e) {
   }

   public void setEnabled(boolean enabled) {
      super.setEnabled(enabled);

      short i;
      for(i = 0; i < this.days.length; ++i) {
         if (this.days[i] != null) {
            this.days[i].setEnabled(enabled);
         }
      }

      for(i = 0; i < this.weeks.length; ++i) {
         if (this.weeks[i] != null) {
            this.weeks[i].setEnabled(enabled);
         }
      }

   }

   public boolean isWeekOfYearVisible() {
      return this.weekOfYearVisible;
   }

   public void setWeekOfYearVisible(boolean weekOfYearVisible) {
      if (weekOfYearVisible != this.weekOfYearVisible) {
         if (weekOfYearVisible) {
            this.add(this.weekPanel, "West");
         } else {
            this.remove(this.weekPanel);
         }

         this.weekOfYearVisible = weekOfYearVisible;
         this.validate();
         this.dayPanel.validate();
      }
   }

   public JPanel getDayPanel() {
      return this.dayPanel;
   }

   public Color getDecorationBackgroundColor() {
      return this.decorationBackgroundColor;
   }

   public void setDecorationBackgroundColor(Color decorationBackgroundColor) {
      this.decorationBackgroundColor = decorationBackgroundColor;
      int i;
      if (this.days != null) {
         for(i = 0; i < 7; ++i) {
            this.days[i].setBackground(decorationBackgroundColor);
         }
      }

      if (this.weeks != null) {
         for(i = 0; i < 7; ++i) {
            this.weeks[i].setBackground(decorationBackgroundColor);
         }
      }

   }

   public Color getSundayForeground() {
      return this.sundayForeground;
   }

   public Color getWeekdayForeground() {
      return this.weekdayForeground;
   }

   public void setSundayForeground(Color sundayForeground) {
      this.sundayForeground = sundayForeground;
      this.drawDayNames();
      this.drawDays();
   }

   public void setWeekdayForeground(Color weekdayForeground) {
      this.weekdayForeground = weekdayForeground;
      this.drawDayNames();
      this.drawDays();
   }

   public void setFocus() {
      if (this.selectedDay != null) {
         this.selectedDay.requestFocus();
      }

   }

   public boolean isDecorationBackgroundVisible() {
      return this.decorationBackgroundVisible;
   }

   public void setDecorationBackgroundVisible(boolean decorationBackgroundVisible) {
      this.decorationBackgroundVisible = decorationBackgroundVisible;
      this.initDecorations();
   }

   public boolean isDecorationBordersVisible() {
      return this.decorationBordersVisible;
   }

   public boolean isDayBordersVisible() {
      return this.dayBordersVisible;
   }

   public void setDecorationBordersVisible(boolean decorationBordersVisible) {
      this.decorationBordersVisible = decorationBordersVisible;
      this.initDecorations();
   }

   public void setDayBordersVisible(boolean dayBordersVisible) {
      this.dayBordersVisible = dayBordersVisible;
      if (this.initialized) {
         for(int x = 7; x < 49; ++x) {
            if ("Windows".equals(UIManager.getLookAndFeel().getID())) {
               this.days[x].setContentAreaFilled(dayBordersVisible);
            } else {
               this.days[x].setContentAreaFilled(true);
            }

            this.days[x].setBorderPainted(dayBordersVisible);
         }
      }

   }

   public void updateUI() {
      super.updateUI();
      this.setFont(Font.decode("Dialog Plain 11"));
      if (this.weekPanel != null) {
         this.weekPanel.updateUI();
      }

      if (this.initialized) {
         if ("Windows".equals(UIManager.getLookAndFeel().getID())) {
            this.setDayBordersVisible(false);
            this.setDecorationBackgroundVisible(true);
            this.setDecorationBordersVisible(false);
         } else {
            this.setDayBordersVisible(true);
            this.setDecorationBackgroundVisible(this.decorationBackgroundVisible);
            this.setDecorationBordersVisible(this.decorationBordersVisible);
         }
      }

   }

   public void setSelectableDateRange(Date min, Date max) {
      if (min == null) {
         this.minSelectableDate = this.defaultMinSelectableDate;
      } else {
         this.minSelectableDate = min;
      }

      if (max == null) {
         this.maxSelectableDate = this.defaultMaxSelectableDate;
      } else {
         this.maxSelectableDate = max;
      }

      if (this.maxSelectableDate.before(this.minSelectableDate)) {
         this.minSelectableDate = this.defaultMinSelectableDate;
         this.maxSelectableDate = this.defaultMaxSelectableDate;
      }

      this.drawDays();
   }

   public Date setMaxSelectableDate(Date max) {
      if (max == null) {
         this.maxSelectableDate = this.defaultMaxSelectableDate;
      } else {
         this.maxSelectableDate = max;
      }

      this.drawDays();
      return this.maxSelectableDate;
   }

   public Date setMinSelectableDate(Date min) {
      if (min == null) {
         this.minSelectableDate = this.defaultMinSelectableDate;
      } else {
         this.minSelectableDate = min;
      }

      this.drawDays();
      return this.minSelectableDate;
   }

   public Date getMaxSelectableDate() {
      return this.maxSelectableDate;
   }

   public Date getMinSelectableDate() {
      return this.minSelectableDate;
   }

   public int getMaxDayCharacters() {
      return this.maxDayCharacters;
   }

   public void setMaxDayCharacters(int maxDayCharacters) {
      if (maxDayCharacters != this.maxDayCharacters) {
         if (maxDayCharacters >= 0 && maxDayCharacters <= 4) {
            this.maxDayCharacters = maxDayCharacters;
         } else {
            this.maxDayCharacters = 0;
         }

         this.drawDayNames();
         this.drawDays();
         this.invalidate();
      }
   }

   public static void main(String[] s) {
      JFrame frame = new JFrame("JDayChooser");
      frame.getContentPane().add(new JDayChooser());
      frame.pack();
      frame.setVisible(true);
   }

   class DecoratorButton extends JButton {
      private static final long serialVersionUID = -5306477668406547496L;

      public DecoratorButton() {
         this.setBackground(JDayChooser.this.decorationBackgroundColor);
         this.setContentAreaFilled(JDayChooser.this.decorationBackgroundVisible);
         this.setBorderPainted(JDayChooser.this.decorationBordersVisible);
      }

      public void addMouseListener(MouseListener l) {
      }

      public boolean isFocusable() {
         return false;
      }

      public void paint(Graphics g) {
         if ("Windows".equals(UIManager.getLookAndFeel().getID())) {
            if (JDayChooser.this.decorationBackgroundVisible) {
               g.setColor(JDayChooser.this.decorationBackgroundColor);
            } else {
               g.setColor(JDayChooser.this.days[7].getBackground());
            }

            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            if (this.isBorderPainted()) {
               this.setContentAreaFilled(true);
            } else {
               this.setContentAreaFilled(false);
            }
         }

         super.paint(g);
      }
   }
}
