package package1;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AplicacionCRUD extends JFrame {

    private JList<String> itemList;
    private DefaultListModel<String> listModel;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    private List<String> items;
    private File selectedFile;

    public AplicacionCRUD() {
        setTitle("Aplicación CRUD");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        listModel = new DefaultListModel<>();
        itemList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(itemList);

        addButton = new JButton("Agregar");
        updateButton = new JButton("Actualizar");
        deleteButton = new JButton("Eliminar");

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newTemperatureStr = JOptionPane.showInputDialog("Ingrese la temperatura (formato °C):");
                String newHumidityStr = JOptionPane.showInputDialog("Ingrese % de humedad: ");
                String newDayStr = JOptionPane.showInputDialog("Ingrese el día (dd): ");
                String newMonthStr = JOptionPane.showInputDialog("Ingrese el mes (mm): ");
                String newYearStr = JOptionPane.showInputDialog("Ingrese el año (yyyy): ");
                String newHourStr = JOptionPane.showInputDialog("Ingrese la hora: ");
                String newMinuteStr = JOptionPane.showInputDialog("Ingrese los minutos(mn): ");
                String newSecondStr = JOptionPane.showInputDialog("Ingrese los segundos(ss): ");

                if (newTemperatureStr != null && !newTemperatureStr.isEmpty() &&
                    newHumidityStr != null && !newHumidityStr.isEmpty() &&
                    newDayStr != null && !newDayStr.isEmpty() &&
                    newMonthStr != null && !newMonthStr.isEmpty() &&
                    newYearStr != null && !newYearStr.isEmpty() &&
                    newHourStr != null && !newHourStr.isEmpty() &&
                    newMinuteStr != null && !newMinuteStr.isEmpty() &&
                    newSecondStr != null && !newSecondStr.isEmpty()) {
                    try {
                        
                        int newTemperature = Integer.parseInt(newTemperatureStr.replaceAll("[^0-9]", ""));
                        int newHumidity = Integer.parseInt(newHumidityStr.replaceAll("[^0-9]", ""));
                        String newFormattedDate = formatDate(newDayStr, newMonthStr, newYearStr, newHourStr, newMinuteStr, newSecondStr);
                        String newItem = "Fecha y hora: " + newFormattedDate +
                                " Temperatura: " + newTemperature + "°C" +
                                " Humedad: " + newHumidity + "%";
                        listModel.addElement(newItem);
                        items.add(newItem);
                        saveItemsToFile(selectedFile.getAbsolutePath(), "UTF-8");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(AplicacionCRUD.this, "Ingrese valores enteros válidos para la temperatura y la humedad.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(AplicacionCRUD.this, "Ingrese una fecha y hora válidas.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = itemList.getSelectedIndex();
                if (selectedIndex != -1) {
                    
                    String updatedTemperatureStr = JOptionPane.showInputDialog("Ingrese la temperatura actualizada (°C):");
                    String updatedHumidityStr = JOptionPane.showInputDialog("Ingrese el % humedad actualizada:");
                    String updatedDayStr = JOptionPane.showInputDialog("Ingrese el día actualizado (dd):");
                    String updatedMonthStr = JOptionPane.showInputDialog("Ingrese el mes actualizado (mm):");
                    String updatedYearStr = JOptionPane.showInputDialog("Ingrese el año actualizado (yyyy):");
                    String updatedHourStr = JOptionPane.showInputDialog("Ingrese la hora actualizada (hh):");
                    String updatedMinuteStr = JOptionPane.showInputDialog("Ingrese los minutos actualizados (mn):");
                    String updatedSecondStr = JOptionPane.showInputDialog("Ingrese los segundos actualizados (ss):");

                    if (updatedTemperatureStr != null && !updatedTemperatureStr.isEmpty() &&
                        updatedHumidityStr != null && !updatedHumidityStr.isEmpty() &&
                        updatedDayStr != null && !updatedDayStr.isEmpty() &&
                        updatedMonthStr != null && !updatedMonthStr.isEmpty() &&
                        updatedYearStr != null && !updatedYearStr.isEmpty() &&
                        updatedHourStr != null && !updatedHourStr.isEmpty() &&
                        updatedMinuteStr != null && !updatedMinuteStr.isEmpty() &&
                        updatedSecondStr != null && !updatedSecondStr.isEmpty()) {
                        try {
                            int updatedTemperature = Integer.parseInt(updatedTemperatureStr.replaceAll("[^0-9]", ""));
                            int updatedHumidity = Integer.parseInt(updatedHumidityStr.replaceAll("[^0-9]", ""));
                            String updatedFormattedDate = formatDate(updatedDayStr, updatedMonthStr, updatedYearStr, updatedHourStr, updatedMinuteStr, updatedSecondStr);
                            String updatedItem = "Fecha y hora: " + updatedFormattedDate +
                                    " Temperatura: " + updatedTemperature + "°C" +
                                    " Humedad: " + updatedHumidity + "%";
                            listModel.set(selectedIndex, updatedItem);
                            items.set(selectedIndex, updatedItem);
                            saveItemsToFile(selectedFile.getAbsolutePath(), "UTF-8");
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(AplicacionCRUD.this, "Ingrese valores enteros válidos para la temperatura y la humedad.", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (ParseException ex) {
                            JOptionPane.showMessageDialog(AplicacionCRUD.this, "Ingrese una fecha y hora válidas.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(AplicacionCRUD.this, "esta seguro que desea eliminarlo?");
                int selectedIndex = itemList.getSelectedIndex();
                if (selectedIndex != -1) {
                    listModel.remove(selectedIndex);
                    items.remove(selectedIndex);
                    saveItemsToFile(selectedFile.getAbsolutePath(), "UTF-8");
                }
            }
        });

        items = new ArrayList<>();
        if (showOpenFileDialog()) {
            if (selectedFile.getName().endsWith(".txt")) {
                loadItemsFromFile(selectedFile.getAbsolutePath(), "UTF-8");
            } else {
                JOptionPane.showMessageDialog(this, "Formato de archivo no compatible.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        for (String item : items) {
            listModel.addElement(item);
        }
    }

    private boolean showOpenFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Abrir archivo");

        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt");

        fileChooser.addChoosableFileFilter(txtFilter);
        fileChooser.setFileFilter(txtFilter);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "No se seleccionó ningún archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void loadItemsFromFile(String filename, String charset) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), charset))) {
            String line;
            while ((line = br.readLine()) != null) {
                items.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveItemsToFile(String filename, String charset) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), charset))) {
            for (String item : items) {
                bw.write(item);
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatDate(String dayStr, String monthStr, String yearStr, String hourStr, String minuteStr, String secondStr) throws ParseException {
        String formattedDateStr = dayStr + "/" + monthStr + "/" + yearStr + " " + hourStr + ":" + minuteStr + ":" + secondStr;
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = inputFormat.parse(formattedDateStr);
        return outputFormat.format(date);
    }

}
