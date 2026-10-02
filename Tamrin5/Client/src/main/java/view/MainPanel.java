package view;

import controller.ClientManager;
import model.MyFile;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class MainPanel extends MyPanel {
    private JButton logoutButton;
    private JButton uploadButton, downloadButton;
    private JTextField downloadField;
    private JTable table;
    private JScrollPane scrollPane;

    public MainPanel(ClientManager clientManager) {
        super(clientManager);
    }

    @Override
    protected void setContent() {
        setLogoutButton();
        setUploadButton();
        setDownloadButton();
        setDownloadField();
        setTable();
        setScrollPane();
    }

    private void setLogoutButton() {
        logoutButton = new JButton();
        logoutButton.addActionListener(e -> clientManager.logout());
        logoutButton.setFont(new Font("", Font.PLAIN, 15));
        logoutButton.setText("Log Out");
        logoutButton.setLayout(null);
        logoutButton.setLocation(20, 15);
        logoutButton.setSize(70, 40);
        add(logoutButton);
    }

    private void setUploadButton() {
        uploadButton = new JButton();
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int answer = fileChooser.showOpenDialog(this);
            if (answer == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                clientManager.upload(file);
            }
        });
        uploadButton.setFont(new Font("", Font.PLAIN, 20));
        uploadButton.setText("Upload");
        uploadButton.setLayout(null);
        uploadButton.setLocation(280, 450);
        uploadButton.setSize(160, 40);
        add(uploadButton);
    }

    private void setDownloadButton() {
        downloadButton = new JButton();
        downloadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int answer = fileChooser.showSaveDialog(this);
            if (answer == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                clientManager.download(downloadField.getText(), file.getPath());
            }
        });
        downloadButton.setFont(new Font("", Font.PLAIN, 20));
        downloadButton.setText("Download");
        downloadButton.setLayout(null);
        downloadButton.setLocation(450, 450);
        downloadButton.setSize(160, 40);
        add(downloadButton);
    }

    private void setDownloadField() {
        downloadField = new JTextField();
        downloadField.setLayout(null);
        downloadField.setFont(new Font("", Font.PLAIN, 18));
        downloadField.setSelectionColor(Color.CYAN);
        downloadField.setCaretColor(Color.BLUE);
        downloadField.setLocation(620, 450);
        downloadField.setSize(200, 40);
        add(downloadField);
    }

    private void setTable() {
        ArrayList<MyFile> files = clientManager.getUser().getFiles();
        String[] tableTitles = {"Name", "Volume", "Date Modified"};
        String[][] tableData = new String[files.size()][3];
        for (int i = 0; i < files.size(); i++) {
            tableData[i][0] = files.get(i).getName();
            tableData[i][1] = files.get(i).getVolume() + " bytes";
            tableData[i][2] = files.get(i).getDateModified();
        }
        table = new JTable(tableData, tableTitles);
        table.setFont(new Font("", Font.PLAIN, 15));
    }

    private void setScrollPane() {
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 80, 800, 350);
        add(scrollPane);
    }
}