package GUIclasses;

import Files.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.File;

public class GUI {

    public void createAndShowGUI() {

        final String[] currentFile = new String[1];

        JFrame mainFrame = new JFrame("Song Manager");
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setSize(screenDim.width / 2, screenDim.height / 2);
        mainFrame.setLocation(screenDim.width / 4, screenDim.height / 4);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SongManager sm = new SongManager(mainFrame);

        //Menu bar
        JMenuItem openFileItem = new JMenuItem("Open file");
        JMenuItem saveFileItem = new JMenuItem("Save file");
        JMenu fileMenu = new JMenu("Menu");
        fileMenu.add(openFileItem);
        fileMenu.addSeparator();
        fileMenu.add(saveFileItem);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);

        openFileItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int code = fileChooser.showOpenDialog(mainFrame);
            if (code == JFileChooser.APPROVE_OPTION) {
                currentFile[0] = fileChooser.getSelectedFile().getAbsolutePath();
                sm.readFile(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        saveFileItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (currentFile[0] != null) {
                fileChooser.setCurrentDirectory(new File(currentFile[0]));
            }
            int code = fileChooser.showSaveDialog(mainFrame);
            if (code == JFileChooser.APPROVE_OPTION) {
                sm.saveToFile(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        //Tabela
        JTable jt = new JTable();
        jt.setModel(sm);
        jt.setAutoCreateRowSorter(true);

        jt.setDefaultEditor(SongGenre.class, new SongGenreCellEditor());
        jt.setDefaultEditor(Integer.class, new IntegerValueCellEditor());
        jt.setTableHeader(
                new JTableHeader(jt.getColumnModel())
        );

        JPanel panel = new JPanel();
        panel.add(new JScrollPane(jt));

        //Dodaj usuń piosenkę
        JButton buttonAddSong = new JButton("Dodaj piosenkę");
        JButton buttonRemoveSong = new JButton("Usuń piosenkę");

        buttonAddSong.addActionListener(e -> sm.addSong());

        buttonRemoveSong.addActionListener(e -> sm.removeSong());

        //Filtrowanie dowolnej cechy
        JLabel labelFilterCharacter = new JLabel("Wpisz wartość do wyszukania:");
        JTextField filterChosenCharacter = new JTextField();
        JRadioButton rbTitle = new JRadioButton("Tytuł");
        JRadioButton rbAuthor = new JRadioButton("Autor");
        JRadioButton rbSongGenre = new JRadioButton("Gatunek muzyczny");
        JRadioButton rbDuration = new JRadioButton("Czas trwania");
        JRadioButton rbReleaseYear = new JRadioButton("Rok premiery");
        JButton buttonFilterCharacter = new JButton("Filtruj");
        JButton buttonRemoveFilterCharacter = new JButton("Usuń filtr");
        buttonRemoveFilterCharacter.setEnabled(false);

        rbTitle.addActionListener(e -> {
            rbAuthor.setEnabled(!rbAuthor.isEnabled());
            rbSongGenre.setEnabled(!rbSongGenre.isEnabled());
            rbDuration.setEnabled(!rbDuration.isEnabled());
            rbReleaseYear.setEnabled(!rbReleaseYear.isEnabled());
        });

        rbAuthor.addActionListener(e -> {
            rbTitle.setEnabled(!rbTitle.isEnabled());
            rbSongGenre.setEnabled(!rbSongGenre.isEnabled());
            rbDuration.setEnabled(!rbDuration.isEnabled());
            rbReleaseYear.setEnabled(!rbReleaseYear.isEnabled());
        });

        rbSongGenre.addActionListener(e -> {
            rbTitle.setEnabled(!rbTitle.isEnabled());
            rbAuthor.setEnabled(!rbAuthor.isEnabled());
            rbDuration.setEnabled(!rbDuration.isEnabled());
            rbReleaseYear.setEnabled(!rbReleaseYear.isEnabled());
        });

        rbDuration.addActionListener(e -> {
            rbTitle.setEnabled(!rbTitle.isEnabled());
            rbAuthor.setEnabled(!rbAuthor.isEnabled());
            rbSongGenre.setEnabled(!rbSongGenre.isEnabled());
            rbReleaseYear.setEnabled(!rbReleaseYear.isEnabled());
        });

        rbReleaseYear.addActionListener(e -> {
            rbTitle.setEnabled(!rbTitle.isEnabled());
            rbAuthor.setEnabled(!rbAuthor.isEnabled());
            rbSongGenre.setEnabled(!rbSongGenre.isEnabled());
            rbDuration.setEnabled(!rbDuration.isEnabled());
        });

        buttonFilterCharacter.addActionListener(e -> {
            if (filterChosenCharacter.getText().length() > 0 & (rbTitle.isSelected() | rbAuthor.isSelected() | rbSongGenre.isSelected() | rbDuration.isSelected() | rbReleaseYear.isSelected())) {
                if (rbDuration.isSelected() | rbReleaseYear.isSelected()) {
                    int number;
                    try {
                        number = Integer.parseInt(filterChosenCharacter.getText());
                        buttonRemoveFilterCharacter.setEnabled(true);
                        buttonFilterCharacter.setEnabled(false);
                        if (rbDuration.isSelected()) {
                            sm.filterCharacter("duration", number);
                        } else if (rbReleaseYear.isSelected()) {
                            sm.filterCharacter("releaseYear", number);
                        }
                    } catch (NumberFormatException es) {
                        JOptionPane.showMessageDialog(mainFrame, "Niepoprawny format liczby", "Błąd", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    buttonRemoveFilterCharacter.setEnabled(true);
                    buttonFilterCharacter.setEnabled(false);
                    if (rbTitle.isSelected()) {
                        sm.filterCharacter("title", filterChosenCharacter.getText());
                    } else if (rbAuthor.isSelected()) {
                        sm.filterCharacter("author", filterChosenCharacter.getText());
                    } else if (rbSongGenre.isSelected()) {
                        sm.filterCharacter("songGenre", filterChosenCharacter.getText());
                    }
                }
            }
        });

        buttonRemoveFilterCharacter.addActionListener(e -> {
            buttonFilterCharacter.setEnabled(true);
            buttonRemoveFilterCharacter.setEnabled(false);
            sm.unfilter();
        });

        //Filtrowanie roku premiery
        JLabel labelFilterReleaseYear = new JLabel("Filtruj rok premiery:");
        JTextField filterReleaseYear = new JTextField();
        JRadioButton higher = new JRadioButton(">");
        JRadioButton lower = new JRadioButton("<");
        JButton buttonFilterReleaseYear = new JButton("Filtruj rok premiery");
        JButton buttonRemoveFilterReleaseYear = new JButton("Usuń filtr");
        buttonRemoveFilterReleaseYear.setEnabled(false);

        higher.addActionListener(e -> lower.setEnabled(!lower.isEnabled()));

        lower.addActionListener(e -> higher.setEnabled(!higher.isEnabled()));

        buttonFilterReleaseYear.addActionListener(e -> {
            if (filterReleaseYear.getText().length() > 0 & (higher.isSelected() | lower.isSelected())) {
                int number;
                try {
                    number = Integer.parseInt(filterReleaseYear.getText());
                    if (higher.isSelected()) {
                        sm.filterReleaseYear("higher", number);
                    } else if (lower.isSelected()){
                        sm.filterReleaseYear("lower", number);
                    }
                    buttonRemoveFilterReleaseYear.setEnabled(true);
                    buttonFilterReleaseYear.setEnabled(false);
                } catch (NumberFormatException ex) {
                    System.out.println("Błoąd z liczbą");
                    JOptionPane.showMessageDialog(
                            mainFrame, "Niepoprawny format liczby", "Błąd", JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }

        });

        buttonRemoveFilterReleaseYear.addActionListener(e -> {
            sm.unfilter();
            buttonRemoveFilterReleaseYear.setEnabled(false);
            buttonFilterReleaseYear.setEnabled(true);
        });



        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS));

        //Dodaj usuń piosenkę
        buttonsPanel.add(buttonAddSong);
        buttonsPanel.add(buttonRemoveSong);

        //Filtrowanie wybranej cechy
        buttonsPanel.add(labelFilterCharacter);
        buttonsPanel.add(filterChosenCharacter);
        buttonsPanel.add(rbTitle);
        buttonsPanel.add(rbAuthor);
        buttonsPanel.add(rbSongGenre);
        buttonsPanel.add(rbDuration);
        buttonsPanel.add(rbReleaseYear);
        buttonsPanel.add(buttonFilterCharacter);
        buttonsPanel.add(buttonRemoveFilterCharacter);

        //Filtrowanie roku premiery
        buttonsPanel.add(labelFilterReleaseYear);
        buttonsPanel.add(filterReleaseYear);
        buttonsPanel.add(higher);
        buttonsPanel.add(lower);
        buttonsPanel.add(buttonFilterReleaseYear);
        buttonsPanel.add(buttonRemoveFilterReleaseYear);


        JScrollPane buttonsScrollPane = new JScrollPane(buttonsPanel);

        mainFrame.setJMenuBar(menuBar);
        mainFrame.getContentPane().add(new JScrollPane(jt));
        mainFrame.getContentPane().add(BorderLayout.EAST, buttonsScrollPane);
        mainFrame.setVisible(true);

    }
}
