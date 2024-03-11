package com.toedter.calendar.demo;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import com.toedter.components.JLocaleChooser;
import com.toedter.components.JSpinField;
import com.toedter.components.JTitlePanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class JCalendarDemo extends JApplet implements PropertyChangeListener {
   private static final long serialVersionUID = 6739986412544494316L;
   private JSplitPane splitPane;
   private JPanel calendarPanel;
   private JComponent[] beans;
   private JPanel propertyPanel;
   private JTitlePanel propertyTitlePanel;
   private JTitlePanel componentTitlePanel;
   private JPanel componentPanel;
   private JToolBar toolBar;

   public void init() {
      this.initializeLookAndFeels();
      this.beans = new JComponent[6];
      this.beans[0] = new DateChooserPanel();
      this.beans[1] = new JCalendar();
      this.beans[2] = new JDayChooser();
      this.beans[3] = new JMonthChooser();
      this.beans[4] = new JYearChooser();
      this.beans[5] = new JSpinField();
      ((JSpinField)this.beans[5]).adjustWidthToMaximumValue();
      ((JYearChooser)this.beans[4]).setMaximum(((JSpinField)this.beans[5]).getMaximum());
      ((JYearChooser)this.beans[4]).adjustWidthToMaximumValue();
      this.getContentPane().setLayout(new BorderLayout());
      this.setJMenuBar(this.createMenuBar());
      this.toolBar = this.createToolBar();
      this.getContentPane().add(this.toolBar, "North");
      this.splitPane = new JSplitPane(0);
      this.splitPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      this.splitPane.setDividerSize(4);
      this.splitPane.setDividerLocation(190);
      BasicSplitPaneDivider divider = ((BasicSplitPaneUI)this.splitPane.getUI()).getDivider();
      if (divider != null) {
         divider.setBorder((Border)null);
      }

      this.propertyPanel = new JPanel();
      this.componentPanel = new JPanel();
      URL iconURL = this.beans[0].getClass().getResource("images/" + this.beans[0].getName() + "Color16.gif");
      ImageIcon icon = new ImageIcon(iconURL);
      this.propertyTitlePanel = new JTitlePanel("Properties", (Icon)null, this.propertyPanel, BorderFactory.createEmptyBorder(4, 4, 4, 4));
      this.componentTitlePanel = new JTitlePanel("Component", icon, this.componentPanel, BorderFactory.createEmptyBorder(4, 4, 0, 4));
      this.splitPane.setBottomComponent(this.propertyTitlePanel);
      this.splitPane.setTopComponent(this.componentTitlePanel);
      this.installBean(this.beans[0]);
      this.getContentPane().add(this.splitPane, "Center");
   }

   public final void initializeLookAndFeels() {
      try {
         LookAndFeelInfo[] lnfs = UIManager.getInstalledLookAndFeels();
         boolean found = false;

         for(int i = 0; i < lnfs.length; ++i) {
            if (lnfs[i].getName().equals("JGoodies Plastic 3D")) {
               found = true;
            }
         }

         if (!found) {
            UIManager.installLookAndFeel("JGoodies Plastic 3D", "com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
         }

         UIManager.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
      } catch (Throwable var5) {
         try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

   }

   public JToolBar createToolBar() {
      this.toolBar = new JToolBar();
      this.toolBar.putClientProperty("jgoodies.headerStyle", "Both");
      this.toolBar.setRollover(true);
      this.toolBar.setFloatable(false);

      for(int i = 0; i < this.beans.length; ++i) {
         JButton button;
         try {
            final JComponent bean = this.beans[i];
            URL iconURL = bean.getClass().getResource("images/" + bean.getName() + "Color16.gif");
            Icon icon = new ImageIcon(iconURL);
            button = new JButton(icon);
            ActionListener actionListener = new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  JCalendarDemo.this.installBean(bean);
               }
            };
            button.addActionListener(actionListener);
         } catch (Exception var7) {
            System.out.println("JCalendarDemo.createToolBar(): " + var7);
            button = new JButton(this.beans[i].getName());
         }

         button.setFocusPainted(false);
         this.toolBar.add(button);
      }

      return this.toolBar;
   }

   public JMenuBar createMenuBar() {
      final JMenuBar menuBar = new JMenuBar();
      JMenu componentsMenu = new JMenu("Components");
      componentsMenu.setMnemonic('C');
      menuBar.add(componentsMenu);

      for(int i = 0; i < this.beans.length; ++i) {
         JMenuItem menuItem;
         try {
            URL iconURL = this.beans[i].getClass().getResource("images/" + this.beans[i].getName() + "Color16.gif");
            Icon icon = new ImageIcon(iconURL);
            menuItem = new JMenuItem(this.beans[i].getName(), icon);
         } catch (Exception var8) {
            System.out.println("JCalendarDemo.createMenuBar(): " + var8 + " for URL: " + "images/" + this.beans[i].getName() + "Color16.gif");
            menuItem = new JMenuItem(this.beans[i].getName());
         }

         componentsMenu.add(menuItem);
         final JComponent bean = this.beans[i];
         ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               JCalendarDemo.this.installBean(bean);
            }
         };
         menuItem.addActionListener(actionListener);
      }

      LookAndFeelInfo[] lnfs = UIManager.getInstalledLookAndFeels();
      ButtonGroup lnfGroup = new ButtonGroup();
      JMenu lnfMenu = new JMenu("Look&Feel");
      lnfMenu.setMnemonic('L');
      menuBar.add(lnfMenu);

      for(int i = 0; i < lnfs.length; ++i) {
         if (!lnfs[i].getName().equals("CDE/Motif")) {
            JRadioButtonMenuItem rbmi = new JRadioButtonMenuItem(lnfs[i].getName());
            lnfMenu.add(rbmi);
            rbmi.setSelected(UIManager.getLookAndFeel().getName().equals(lnfs[i].getName()));
            rbmi.putClientProperty("lnf name", lnfs[i]);
            rbmi.addItemListener(new ItemListener() {
               public void itemStateChanged(ItemEvent ie) {
                  JRadioButtonMenuItem rbmi2 = (JRadioButtonMenuItem)ie.getSource();
                  if (rbmi2.isSelected()) {
                     LookAndFeelInfo info = (LookAndFeelInfo)rbmi2.getClientProperty("lnf name");

                     try {
                        menuBar.putClientProperty("jgoodies.headerStyle", "Both");
                        UIManager.setLookAndFeel(info.getClassName());
                        SwingUtilities.updateComponentTreeUI(JCalendarDemo.this);

                        for(int i = 0; i < JCalendarDemo.this.beans.length; ++i) {
                           SwingUtilities.updateComponentTreeUI(JCalendarDemo.this.beans[i]);
                        }

                        BasicSplitPaneDivider divider = ((BasicSplitPaneUI)JCalendarDemo.this.splitPane.getUI()).getDivider();
                        if (divider != null) {
                           divider.setBorder((Border)null);
                        }
                     } catch (Exception var5) {
                        var5.printStackTrace();
                        System.err.println("Unable to set UI " + var5.getMessage());
                     }
                  }

               }
            });
            lnfGroup.add(rbmi);
         }
      }

      JMenu helpMenu = new JMenu("Help");
      helpMenu.setMnemonic('H');
      JMenuItem aboutItem = helpMenu.add(new JCalendarDemo.AboutAction(this));
      aboutItem.setMnemonic('A');
      aboutItem.setAccelerator(KeyStroke.getKeyStroke(65, 2));
      menuBar.add(helpMenu);
      return menuBar;
   }

   public void propertyChange(PropertyChangeEvent evt) {
      if (this.calendarPanel != null && evt.getPropertyName().equals("calendar")) {
      }

   }

   public static void main(String[] s) {
      WindowListener l = new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            System.exit(0);
         }
      };
      JFrame frame = new JFrame("JCalendar Demo");
      frame.addWindowListener(l);
      JCalendarDemo demo = new JCalendarDemo();
      demo.init();
      frame.getContentPane().add(demo);
      frame.pack();
      frame.setBounds(200, 200, (int)frame.getPreferredSize().getWidth() + 20, (int)frame.getPreferredSize().getHeight() + 180);
      frame.setVisible(true);
   }

   private void installBean(final JComponent bean) {
      try {
         this.componentPanel.removeAll();
         this.componentPanel.add(bean);
         BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass(), bean.getClass().getSuperclass());
         PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
         this.propertyPanel.removeAll();
         GridBagLayout gridbag = new GridBagLayout();
         GridBagConstraints c = new GridBagConstraints();
         c.fill = 1;
         this.propertyPanel.setLayout(gridbag);
         int count = 0;
         String[] types = new String[]{"class java.util.Locale", "boolean", "int", "class java.awt.Color", "class java.util.Date", "class java.lang.String"};

         for(int t = 0; t < types.length; ++t) {
            for(int i = 0; i < propertyDescriptors.length; ++i) {
               if (propertyDescriptors[i].getWriteMethod() != null) {
                  String type = propertyDescriptors[i].getPropertyType().toString();
                  PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
                  final JComponent currentBean = bean;
                  Method readMethod = propertyDescriptor.getReadMethod();
                  final Method writeMethod = propertyDescriptor.getWriteMethod();
                  if (type.equals(types[t]) && (readMethod != null && writeMethod != null || "class java.util.Locale".equals(type))) {
                     if ("boolean".equals(type)) {
                        boolean isSelected = false;

                        try {
                           Boolean booleanObj = (Boolean)readMethod.invoke(bean, (Object[])null);
                           isSelected = booleanObj;
                        } catch (Exception var22) {
                           var22.printStackTrace();
                        }

                        final JCheckBox checkBox = new JCheckBox("", isSelected);
                        checkBox.addActionListener(new ActionListener() {
                           public void actionPerformed(ActionEvent event) {
                              try {
                                 if (checkBox.isSelected()) {
                                    writeMethod.invoke(bean, new Boolean(true));
                                 } else {
                                    writeMethod.invoke(bean, new Boolean(false));
                                 }
                              } catch (Exception var3) {
                                 var3.printStackTrace();
                              }

                           }
                        });
                        this.addProperty(propertyDescriptors[i], checkBox, gridbag);
                        ++count;
                     } else if ("int".equals(type)) {
                        JSpinField spinField = new JSpinField();
                        spinField.addPropertyChangeListener(new PropertyChangeListener() {
                           public void propertyChange(PropertyChangeEvent evt) {
                              try {
                                 if (evt.getPropertyName().equals("value")) {
                                    writeMethod.invoke(bean, evt.getNewValue());
                                 }
                              } catch (Exception var3) {
                              }

                           }
                        });

                        try {
                           Integer integerObj = (Integer)readMethod.invoke(bean, (Object[])null);
                           spinField.setValue(integerObj);
                        } catch (Exception var21) {
                           var21.printStackTrace();
                        }

                        this.addProperty(propertyDescriptors[i], spinField, gridbag);
                        ++count;
                     } else {
                        ActionListener actionListener;
                        if ("class java.lang.String".equals(type)) {
                           String string = "";

                           try {
                              string = (String)readMethod.invoke(bean, (Object[])null);
                           } catch (Exception var20) {
                              var20.printStackTrace();
                           }

                           JTextField textField = new JTextField(string);
                           actionListener = new ActionListener() {
                              public void actionPerformed(ActionEvent e) {
                                 try {
                                    writeMethod.invoke(bean, e.getActionCommand());
                                 } catch (Exception var3) {
                                 }

                              }
                           };
                           textField.addActionListener(actionListener);
                           this.addProperty(propertyDescriptors[i], textField, gridbag);
                           ++count;
                        } else if ("class java.util.Locale".equals(type)) {
                           JLocaleChooser localeChooser = new JLocaleChooser(bean);
                           localeChooser.setPreferredSize(new Dimension(200, localeChooser.getPreferredSize().height));
                           this.addProperty(propertyDescriptors[i], localeChooser, gridbag);
                           ++count;
                        } else if ("class java.util.Date".equals(type)) {
                           Date date = null;

                           try {
                              date = (Date)readMethod.invoke(bean, (Object[])null);
                           } catch (Exception var19) {
                              var19.printStackTrace();
                           }

                           JDateChooser dateChooser = new JDateChooser(date);
                           dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
                              public void propertyChange(PropertyChangeEvent evt) {
                                 try {
                                    if (evt.getPropertyName().equals("date")) {
                                       writeMethod.invoke(bean, evt.getNewValue());
                                    }
                                 } catch (Exception var3) {
                                 }

                              }
                           });
                           this.addProperty(propertyDescriptors[i], dateChooser, gridbag);
                           ++count;
                        } else if ("class java.awt.Color".equals(type)) {
                           final JButton button = new JButton();

                           try {
                              final Color colorObj = (Color)readMethod.invoke(bean, (Object[])null);
                              button.setText("...");
                              button.setBackground(colorObj);
                              actionListener = new ActionListener() {
                                 public void actionPerformed(ActionEvent e) {
                                    Color newColor = JColorChooser.showDialog(JCalendarDemo.this, "Choose Color", colorObj);
                                    button.setBackground(newColor);

                                    try {
                                       writeMethod.invoke(currentBean, newColor);
                                    } catch (Exception var4) {
                                       var4.printStackTrace();
                                    }

                                 }
                              };
                              button.addActionListener(actionListener);
                           } catch (Exception var18) {
                              var18.printStackTrace();
                           }

                           this.addProperty(propertyDescriptors[i], button, gridbag);
                           ++count;
                        }
                     }
                  }
               }
            }
         }

         URL iconURL = bean.getClass().getResource("images/" + bean.getName() + "Color16.gif");
         ImageIcon icon = new ImageIcon(iconURL);
         this.componentTitlePanel.setTitle(bean.getName(), icon);
         bean.invalidate();
         this.propertyPanel.invalidate();
         this.componentPanel.invalidate();
         this.componentPanel.repaint();
      } catch (IntrospectionException var23) {
         var23.printStackTrace();
      }

   }

   private void addProperty(PropertyDescriptor propertyDescriptor, JComponent editor, GridBagLayout grid) {
      String text = propertyDescriptor.getDisplayName();
      String newText = "";

      for(int i = 0; i < text.length(); ++i) {
         char c = text.charAt(i);
         if ((c < 'A' || c > 'Z') && i != 0) {
            newText = newText + c;
         } else {
            if (i == 0) {
               c = (char)(c - 32);
            }

            newText = newText + " " + c;
         }
      }

      JLabel label = new JLabel(newText + ": ", (Icon)null, 4);
      GridBagConstraints c = new GridBagConstraints();
      c.weightx = 1.0D;
      c.fill = 1;
      grid.setConstraints(label, c);
      this.propertyPanel.add(label);
      c.gridwidth = 0;
      grid.setConstraints(editor, c);
      this.propertyPanel.add(editor);
      JPanel blankLine = new JPanel() {
         private static final long serialVersionUID = 4514530330521503732L;

         public Dimension getPreferredSize() {
            return new Dimension(10, 2);
         }
      };
      grid.setConstraints(blankLine, c);
      this.propertyPanel.add(blankLine);
   }

   class AboutAction extends AbstractAction {
      private static final long serialVersionUID = -5204865941545323214L;
      private JCalendarDemo demo;

      AboutAction(JCalendarDemo demo) {
         super("About...");
         this.demo = demo;
      }

      public void actionPerformed(ActionEvent event) {
         JOptionPane.showMessageDialog(this.demo, "JCalendar Demo\nVersion 1.3.2\n\nKai Toedter\nkai@toedter.com\nwww.toedter.com", "About...", 1);
      }
   }
}
