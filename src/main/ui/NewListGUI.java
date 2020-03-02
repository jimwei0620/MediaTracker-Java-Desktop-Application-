package ui;

import exceptions.DataExistAlreadyException;
import exceptions.EmptyStringException;
import model.ListManager;
import model.MediaList;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// GUI to handle creating new lists
public class NewListGUI extends JFrame {

    private ListManager listColl;
    private JTextField listNameField;
    private BoxLayout formLayout;
    private JPanel panel;
    private FlowLayout flowLayout;
    private JTextPane description;
    private JButton addButton;
    private JTextPane errorText;

    // MODIFIES: this
    // EFFECTS: initializes GUI for from to create new list
    public NewListGUI(ListManager listColl) {
        super("New List");
        this.listColl = listColl;
        flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 25);
        setLayout(flowLayout);
        setVisible(true);
        setPreferredSize(new Dimension(300,200));

        description = new JTextPane();
        description.setText("Type the name of the list:");
        description.setEditable(false);
        description.setBackground(null);

        initializeAddButton();
        initializeTextField();
       // initializeErrorText();

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 100));
        panel.add(description);
        panel.add(listNameField);
        panel.add(addButton);
        panel.add(errorText);
        formLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        add(panel);



        pack();
    }

    // MODIFIES: this
    // EFFECT: create text input field
    private void initializeTextField() {
        listNameField = new JTextField();
        listNameField.setPreferredSize(new Dimension(200, 25));
        listNameField.setBackground(Color.white);
    }

    // MODIFIES: this
    // EFFECT: initializes GUI for add button
    private void initializeAddButton() {
        addButton = new JButton();
        addButton.setText("Create");
        addButton.setPreferredSize(new Dimension(100, 25));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    listColl.addNewList(new MediaList(listNameField.getText()));
                } catch (EmptyStringException error) {
                    //setErrorText("Cannot Be Empty!");
                } catch (KeyAlreadyExistsException error) {
                   // setErrorText("List with that name already Exists!");
                }
            }
        });
    }

}
