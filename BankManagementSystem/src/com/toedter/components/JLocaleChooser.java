package com.toedter.components;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class JLocaleChooser extends JComboBox implements ItemListener {
   private static final long serialVersionUID = 8152430789764877431L;
   protected JComponent component;
   private Locale[] locales;
   private Locale locale;
   private int localeCount;

   public JLocaleChooser() {
      this((JComponent)null);
   }

   public String getName() {
      return "JLocaleChoose";
   }

   public JLocaleChooser(JComponent component) {
      this.component = component;
      this.addItemListener(this);
      this.locales = Calendar.getAvailableLocales();
      this.localeCount = this.locales.length;

      for(int i = 0; i < this.localeCount; ++i) {
         if (this.locales[i].getCountry().length() > 0) {
            this.addItem(this.locales[i].getDisplayName());
         }
      }

      this.setLocale(Locale.getDefault());
   }

   public void itemStateChanged(ItemEvent iEvt) {
      String item = (String)iEvt.getItem();

      int i;
      for(i = 0; i < this.localeCount && !this.locales[i].getDisplayName().equals(item); ++i) {
      }

      this.setLocale(this.locales[i], false);
   }

   private void setLocale(Locale l, boolean select) {
      Locale oldLocale = this.locale;
      this.locale = l;
      int n = 0;
      if (select) {
         for(int i = 0; i < this.localeCount; ++i) {
            if (this.locales[i].getCountry().length() > 0) {
               if (this.locales[i].equals(this.locale)) {
                  this.setSelectedIndex(n);
               }

               ++n;
            }
         }
      }

      this.firePropertyChange("locale", oldLocale, this.locale);
      if (this.component != null) {
         this.component.setLocale(l);
      }

   }

   public void setLocale(Locale l) {
      this.setLocale(l, true);
   }

   public Locale getLocale() {
      return this.locale;
   }

   public static void main(String[] s) {
      JFrame frame = new JFrame("LocaleChooser");
      frame.getContentPane().add(new JLocaleChooser());
      frame.pack();
      frame.setVisible(true);
   }
}
