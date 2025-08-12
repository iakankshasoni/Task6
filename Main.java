package Task5.Task6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    private JFrame frame;
    private DefaultListModel<String> listModel;
    private JList<String> todoList;
    private JTextField inputField;
    private JButton addButton;
    private JButton deleteButton;

    public Main() {
        initUI();
    }

    private void initUI() {
        frame = new JFrame("To-Do App (Java Swing)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 480);
        frame.setLocationRelativeTo(null);

        // Top: input + add button
        JPanel topPanel = new JPanel(new BorderLayout(8, 8));
        inputField = new JTextField();
        inputField.setToolTipText("Type a task and press Enter or click Add");
        addButton = new JButton("Add");
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        topPanel.add(inputField, BorderLayout.CENTER);
        topPanel.add(addButton, BorderLayout.EAST);

        // Center: scrollable list
        listModel = new DefaultListModel<>();
        todoList = new JList<>(listModel);
        todoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        todoList.setVisibleRowCount(10);
        JScrollPane listScroll = new JScrollPane(todoList);
        listScroll.setBorder(BorderFactory.createTitledBorder("Tasks"));
        listScroll.setPreferredSize(new Dimension(380, 300));
        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        centerPanel.add(listScroll);

        // Bottom: delete button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        deleteButton = new JButton("Delete Selected");
        deleteButton.setToolTipText("Select a task in the list and click Delete");
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        bottomPanel.add(deleteButton);

        // Wire actions
        addButton.addActionListener(e -> addTask());
        deleteButton.addActionListener(e -> deleteSelectedTask());

        // Enter key in inputField adds task
        inputField.addActionListener(e -> addTask());

        // Delete key removes selected item
        todoList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    deleteSelectedTask();
                }
            }
        });

        // Assemble
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(topPanel, BorderLayout.NORTH);
        cp.add(centerPanel, BorderLayout.CENTER);
        cp.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void addTask() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) {
            // optional: show a small message
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        listModel.addElement(text);
        inputField.setText("");
        inputField.requestFocusInWindow();
    }

    private void deleteSelectedTask() {
        int idx = todoList.getSelectedIndex();
        if (idx != -1) {
            int choice = JOptionPane.showConfirmDialog(frame,
                    "Delete selected task?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                listModel.remove(idx);
            }
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(frame, "Please select a task to delete.",
                    "No selection", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Launch on Event Dispatch Thread
        SwingUtilities.invokeLater(Main::new);
    }
}
