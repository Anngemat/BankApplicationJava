package com.toedter.calendar.demo;

import com.toedter.calendar.JDateChooserCellEditor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class DemoTable extends JPanel {
   private static final long serialVersionUID = -2823838920746867592L;

   public DemoTable() {
      super(new GridLayout(1, 0));
      this.setName("DemoTable");
      JTable table = new JTable(new DemoTable.DemoTableModel());
      table.setPreferredScrollableViewportSize(new Dimension(180, 32));
      table.setDefaultEditor(Date.class, new JDateChooserCellEditor());
      JScrollPane scrollPane = new JScrollPane(table);
      this.add(scrollPane);
   }

   class DemoTableModel extends AbstractTableModel {
      private static final long serialVersionUID = 3283465559187131559L;
      private String[] columnNames = new String[]{"Empty Date", "Date set"};
      private Object[][] data = new Object[][]{{null, new Date()}, {null, new Date()}};

      public int getColumnCount() {
         return this.columnNames.length;
      }

      public int getRowCount() {
         return this.data.length;
      }

      public String getColumnName(int col) {
         return this.columnNames[col];
      }

      public Object getValueAt(int row, int col) {
         return this.data[row][col];
      }

      public Class getColumnClass(int c) {
         return this.getValueAt(0, 1).getClass();
      }

      public boolean isCellEditable(int row, int col) {
         return true;
      }

      public void setValueAt(Object value, int row, int col) {
         this.data[row][col] = value;
         this.fireTableCellUpdated(row, col);
      }
   }
}
