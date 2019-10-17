package edu.auburn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerUI {
    public JFrame view;
    public static JTextField txtCustomerID = new JTextField(20);
    public static JTextField txtName = new JTextField(20);
    public static JTextField txtAddress = new JTextField(20);
    public static JTextField txtPhone = new JTextField(20);

    public JButton btnAdd = new JButton("Add");
    public JButton btnCancel = new JButton("Cancel");

    public AddCustomerUI() {
        view = new JFrame();
        view.setTitle("Add Customer");
        view.setSize(600, 400);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("CustomerID:"));
        line1.add(txtCustomerID);
        pane.add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Name:"));
        line2.add(txtName);
        pane.add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Address:"));
        line3.add(txtAddress);
        pane.add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Phone:"));
        line4.add(txtPhone);
        pane.add(line4);

        JPanel buttonPane = new JPanel(new FlowLayout());
        buttonPane.add(btnAdd);
        buttonPane.add(btnCancel);
        pane.add(buttonPane);

        //this.adapter = adapter;
        btnAdd.addActionListener(new AddCustomerUI.AddButtonController());
        btnCancel.addActionListener((actionEvent) -> view.dispose());

    }

    public void run() {
        view.setVisible(true);
    }

    static class AddButtonController implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customer = new CustomerModel();

            String s = txtCustomerID.getText();

            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "CustomerID could not be EMPTY!!!");
                return;
            }
            try {
                customer.mCustomerID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "customerID is INVALID (not a number)!!!");
                return;
            }

            s = txtName.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "customer Name could not be EMPTY!!!");
                return;
            }
            customer.mName = s;

            s = txtAddress.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Address could not be EMPTY!!!");
                return;
            }
            customer.mAddress = s;

            s = txtPhone.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Phone could not be EMPTY!!!");
                return;
            }
            customer.mPhone = s;

            IDataAccess adapter = StoreManager.getInstance().getDataAccess();

            if (adapter.saveCustomer(customer))
                JOptionPane.showMessageDialog(null,
                        "Customer is saved successfully!");
            else {
                System.out.println(adapter.getErrorMessage());
            }


        }
    }

}
