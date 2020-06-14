package Files;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.ArrayList;


public class SongManager extends AbstractTableModel {

    private JFrame mainFrame;

    private ArrayList<Song> songs = new ArrayList<>();

    private ArrayList<Song> filteredSongs = new ArrayList<>();
    private ArrayList<Song> temporaryList;


    public SongManager(JFrame mainFrame) {
        songs.add(new Song());
        this.mainFrame = mainFrame;
    }

    public void filterCharacter(String character, String value) {
        switch (character) {

            case "title":
                songs.stream()
                        .filter(e -> e.getTitle().contains(value))
                        .forEach(e -> filteredSongs.add(e));
                temporaryList = songs;
                songs = filteredSongs;
                fireTableDataChanged();
                break;

            case "author":
                songs.stream()
                        .filter(e -> e.getAuthor().contains(value))
                        .forEach(e -> filteredSongs.add(e));
                temporaryList = songs;
                songs = filteredSongs;
                fireTableDataChanged();
                break;

            case "songGenre":
                songs.stream()
                        .filter(e -> e.getSongGenre().name().contains(value))
                        .forEach(e -> filteredSongs.add(e));
                temporaryList = songs;
                songs = filteredSongs;
                fireTableDataChanged();
                break;
        }
    }

    public void filterCharacter(String character, int value) {
        switch (character) {

            case "duration":
                songs.stream()
                        .filter(e -> e.getDuration() == value)
                        .forEach(e -> filteredSongs.add(e));
                temporaryList = songs;
                songs = filteredSongs;
                fireTableDataChanged();
                break;

            case "releaseYear":
                songs.stream()
                        .filter(e -> e.getReleaseYear() == value)
                        .forEach(e -> filteredSongs.add(e));
                temporaryList = songs;
                songs = filteredSongs;
                fireTableDataChanged();
        }
    }

    public void filterReleaseYear(String test, int value) {
        switch (test) {
            case "higher":
                songs.stream()
                        .filter(e -> e.getReleaseYear() > value)
                        .forEach(e -> filteredSongs.add(e));
                temporaryList = songs;
                songs = filteredSongs;
                fireTableDataChanged();
                break;
            case "lower":
                songs.stream()
                        .filter(e -> e.getReleaseYear() < value)
                        .forEach(e -> filteredSongs.add(e));
                temporaryList = songs;
                songs = filteredSongs;
                fireTableDataChanged();
                break;
        }
    }

    public void unfilter() {
        songs = temporaryList;
        filteredSongs = new ArrayList<>();
        fireTableDataChanged();
    }

    public void addSong() {
        songs.add(new Song());
        fireTableDataChanged();
    }

    public void removeSong() {
        if (songs.size() > 0) {
            songs.remove(songs.size() - 1);
            fireTableDataChanged();
        }
    }

    public void readFile(String path) {

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            songs = (ArrayList) ois.readObject();
            fireTableDataChanged();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Niepoprawny format pliku", "Błąd", JOptionPane.INFORMATION_MESSAGE);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Niepoprawny format pliku", "Błąd", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void saveToFile(String path) {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(songs);
            oos.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Niepoprawny format pliku", "Błąd", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public int getRowCount() {
        return songs.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 0:
                return songs.get(rowIndex).getTitle();

            case 1:
                return songs.get(rowIndex).getAuthor();

            case 2:
                return songs.get(rowIndex).getSongGenre();

            case 3:
                return songs.get(rowIndex).getDuration();

            case 4:
                return songs.get(rowIndex).getReleaseYear();

            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return true;
            case 1:
                return true;
            case 2:
                return true;
            case 3:
                return true;
            case 4:
                return true;
            default:
                return super.isCellEditable(rowIndex, columnIndex);
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Tytuł";

            case 1:
                return "Autor";

            case 2:
                return "Gatunek muzyczny";

            case 3:
                return "Czas trwania (s)";

            case 4:
                return "Rok premiery";

            default:
                return super.getColumnName(column);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 2:
                return SongGenre.class;
            case 3:
                return Integer.class;
            case 4:
                return Integer.class;
            default:
                return super.getColumnClass(columnIndex);
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                songs.get(rowIndex).setTitle((String)aValue);
                break;
            case 1:
                songs.get(rowIndex).setAuthor((String)aValue);
                break;
            case 2:
                switch ((Integer) aValue) {
                    case 0:
                        songs.get(rowIndex).setSongGenre(SongGenre.DISCO);
                        break;
                    case 1:
                        songs.get(rowIndex).setSongGenre(SongGenre.ROCK);
                        break;
                    case 2:
                        songs.get(rowIndex).setSongGenre(SongGenre.POP);
                        break;
                }
                fireTableCellUpdated(rowIndex, columnIndex);
                break;
            case 3:
                try {
                    songs.get(rowIndex).setDuration(Integer.valueOf((String) aValue));
                } catch (NumberFormatException ex) {
                    System.out.println("Zła liczba!");
                    JOptionPane.showMessageDialog(mainFrame, "Niepoprawna wartość liczbowa", "Błąd", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            case 4:
                try {
                    songs.get(rowIndex).setReleaseYear(Integer.valueOf((String) aValue));
                } catch (NumberFormatException ex) {
                    System.out.println("Zła liczba!");
                    JOptionPane.showMessageDialog(mainFrame, "Niepoprawna wartość liczbowa", "Błąd", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            default:
                super.setValueAt(aValue, rowIndex, columnIndex);
        }
    }
}
