package com.toedter.components;

import java.beans.PropertyEditorSupport;
import java.util.Calendar;
import java.util.Locale;

public class LocaleEditor extends PropertyEditorSupport {
   private Locale[] locales = Calendar.getAvailableLocales();
   private String[] localeStrings;
   private Locale locale = Locale.getDefault();
   private int length;

   public LocaleEditor() {
      this.length = this.locales.length;
      this.localeStrings = new String[this.length];
   }

   public String[] getTags() {
      for(int i = 0; i < this.length; ++i) {
         this.localeStrings[i] = this.locales[i].getDisplayName();
      }

      return this.localeStrings;
   }

   public void setAsText(String text) throws IllegalArgumentException {
      for(int i = 0; i < this.length; ++i) {
         if (text.equals(this.locales[i].getDisplayName())) {
            this.locale = this.locales[i];
            this.setValue(this.locale);
            break;
         }
      }

   }

   public String getAsText() {
      return this.locale.getDisplayName();
   }
}
