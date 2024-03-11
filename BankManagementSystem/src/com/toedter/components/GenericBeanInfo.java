package com.toedter.components;

import java.awt.Image;
import java.beans.PropertyEditorManager;
import java.beans.SimpleBeanInfo;
import java.util.Locale;

public class GenericBeanInfo extends SimpleBeanInfo {
   protected Image iconColor16;
   protected Image iconColor32;
   protected Image iconMono16;
   protected Image iconMono32;

   public GenericBeanInfo(String bean, boolean registerLocaleEditor) {
      try {
         this.iconColor16 = this.loadImage("images/" + bean + "Color16.gif");
         this.iconColor32 = this.loadImage("images/" + bean + "Color32.gif");
         this.iconMono16 = this.loadImage("images/" + bean + "Mono16.gif");
         this.iconMono32 = this.loadImage("images/" + bean + "Mono32.gif");
      } catch (RuntimeException var4) {
         System.out.println("GenericBeanInfo.GenericBeanInfo(): " + var4);
      }

      if (registerLocaleEditor) {
         PropertyEditorManager.registerEditor(Locale.class, LocaleEditor.class);
      }

   }

   public Image getIcon(int iconKind) {
      switch(iconKind) {
      case 1:
         return this.iconColor16;
      case 2:
         return this.iconColor32;
      case 3:
         return this.iconMono16;
      case 4:
         return this.iconMono32;
      default:
         return null;
      }
   }
}
