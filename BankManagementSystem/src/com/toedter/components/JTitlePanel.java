package com.toedter.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class JTitlePanel extends JPanel {
   private static final long serialVersionUID = 9104873267039717087L;
   protected JPanel northPanel;
   protected JLabel label;

   public JTitlePanel(String title, Icon icon, JComponent content, Border outerBorder) {
      this.setLayout(new BorderLayout());
      this.label = new JLabel(title, icon, 10);
      this.label.setForeground(Color.WHITE);
      JTitlePanel.GradientPanel titlePanel = new JTitlePanel.GradientPanel(Color.BLACK);
      titlePanel.setLayout(new BorderLayout());
      titlePanel.add(this.label, "West");
      int borderOffset = 2;
      if (icon == null) {
         ++borderOffset;
      }

      titlePanel.setBorder(BorderFactory.createEmptyBorder(borderOffset, 4, borderOffset, 1));
      this.add(titlePanel, "North");
      JPanel northPanel = new JPanel();
      northPanel.setLayout(new BorderLayout());
      northPanel.add(content, "North");
      northPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
      this.add(northPanel, "Center");
      if (outerBorder == null) {
         this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      } else {
         this.setBorder(BorderFactory.createCompoundBorder(outerBorder, BorderFactory.createLineBorder(Color.GRAY)));
      }

   }

   public void setTitle(String label, Icon icon) {
      this.label.setText(label);
      this.label.setIcon(icon);
   }

   private static class GradientPanel extends JPanel {
      private static final long serialVersionUID = -6385751027379193053L;

      private GradientPanel(Color background) {
         this.setBackground(background);
      }

      public void paintComponent(Graphics g) {
         super.paintComponent(g);
         if (this.isOpaque()) {
            Color controlColor = new Color(99, 153, 255);
            int width = this.getWidth();
            int height = this.getHeight();
            Graphics2D g2 = (Graphics2D)g;
            Paint oldPaint = g2.getPaint();
            g2.setPaint(new GradientPaint(0.0F, 0.0F, this.getBackground(), (float)width, 0.0F, controlColor));
            g2.fillRect(0, 0, width, height);
            g2.setPaint(oldPaint);
         }

      }

      // $FF: synthetic method
      GradientPanel(Color x0, Object x1) {
         this(x0);
      }
   }
}
