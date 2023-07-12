package package1;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AplicacionCRUD extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> tableSorter;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton saveButton;
    private List<String> items;
    private File selectedFile;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;

    public AplicacionCRUD() {
        this.items = new ArrayList<>();

        setTitle("Aplicación CRUD");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem openMenuItem = new JMenuItem("Abrir");
        this.tableModel = new DefaultTableModel(new Object[]{"Fecha", "Hora", "Temperatura", "Humedad"}, 0);

        this.table = new JTable(tableModel);
        this.scrollPane = new JScrollPane(table);
        this.buttonPanel = new JPanel();

        this.addButton = new JButton("Agregar");
        this.updateButton = new JButton("Actualizar");
        this.deleteButton = new JButton("Eliminar");
        this.saveButton = new JButton("Guardar");

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        this.addButton.addActionListener(new ActionListener() {
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
                        String newDate = formatDate(newDayStr, newMonthStr, newYearStr);
                        String newHour = formatHour(newHourStr, newMinuteStr, newSecondStr);
                        String[] newItem = {newDate, newHour, newTemperature + "°C", newHumidity + "%"};
                        tableModel.addRow(newItem);
                        items.add(String.join(" ", newItem));
                        saveItemsToFile(selectedFile.getAbsolutePath(), "UTF-8");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(AplicacionCRUD.this, "Ingrese valores enteros válidos para la temperatura y la humedad.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(AplicacionCRUD.this, "Ingrese una fecha y hora válidas.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        this.updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = table.getSelectedRow();
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
                            String updatedDate = formatDate(updatedDayStr, updatedMonthStr, updatedYearStr);
                            String updatedHour = formatHour(updatedHourStr, updatedMinuteStr, updatedSecondStr);
                            String[] updatedItem = {updatedDate, updatedHour, updatedTemperature + "°C", updatedHumidity + "%"};
                            tableModel.setValueAt(updatedDate, selectedIndex, 0);
                            tableModel.setValueAt(updatedHour, selectedIndex, 1);
                            tableModel.setValueAt(updatedTemperature + "°C", selectedIndex, 2);
                            tableModel.setValueAt(updatedHumidity + "%", selectedIndex, 3);
                            items.set(selectedIndex, String.join(" ", updatedItem));
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

        this.deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = table.getSelectedRow();
                if (selectedIndex != -1) {
                    // Mostrar un cuadro de diálogo de confirmación antes de eliminar la fila
                    int result = JOptionPane.showConfirmDialog(AplicacionCRUD.this, "¿Está seguro de que desea eliminar esta fila?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        tableModel.removeRow(selectedIndex);
                        items.remove(selectedIndex);
                        saveItemsToFile(selectedFile.getAbsolutePath(), "UTF-8");
                    }
                }
            }
        });

        this.saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Guardar los datos en el archivo original
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(selectedFile), StandardCharsets.UTF_8))) {
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        String date = (String) tableModel.getValueAt(i, 0);
                        String hour = (String) tableModel.getValueAt(i, 1);
                        String temperature = (String) tableModel.getValueAt(i, 2);
                        String humidity = (String) tableModel.getValueAt(i, 3);
                        writer.write(date + " " + hour + " " + temperature + " " + humidity);
                        writer.newLine();
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(AplicacionCRUD.this, "Error al guardar el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        fileMenu.add(openMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);


        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showOpenFileDialog()) {
                    items.clear();
                    while (tableModel.getRowCount() > 0) {
                        tableModel.removeRow(0);
                    }

                    if (selectedFile.getName().endsWith(".txt")) {
                        loadItemsFromFile(selectedFile.getAbsolutePath(), "UTF-8");
                    } else {
                        JOptionPane.showMessageDialog(AplicacionCRUD.this, "Formato de archivo no compatible.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                for (String item : items) {
                    String[] data = item.split(" ");
                    // Reemplazar "???" por "°"
                    for (int i = 0; i < data.length; i++) {
                        if (data[i].equals("??")) {
                            data[i] = "°";
                        }
                    }
                    tableModel.addRow(data);
                }

                // Configuración del TableRowSorter
                tableSorter = new TableRowSorter<>(tableModel);
                table.setRowSorter(tableSorter);

                // Agregar campos de texto para cada columna en la parte superior de la tabla
                JPanel filterPanel = new JPanel(new GridLayout(1, 4));
                JTextField dateFilterField = new JTextField();
                JTextField hourFilterField = new JTextField();
                JTextField temperatureFilterField = new JTextField();
                JTextField humidityFilterField = new JTextField();
                filterPanel.add(dateFilterField);
                filterPanel.add(hourFilterField);
                filterPanel.add(temperatureFilterField);
                filterPanel.add(humidityFilterField);
                add(filterPanel, BorderLayout.NORTH);

                // Agregar un DocumentListener a cada campo de texto para actualizar el filtro cuando el usuario ingresa valores
                DocumentListener filterListener = new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        updateFilters();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        updateFilters();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        updateFilters();
                    }

                    private void updateFilters() {
                        // Crear un RowFilter para cada columna según el valor ingresado por el usuario
                        RowFilter<Object, Object> dateFilter = null;
                        String dateText = dateFilterField.getText();
                        if (!dateText.isEmpty()) {
                            dateFilter = RowFilter.regexFilter(dateText, 0);
                        }

                        RowFilter<Object, Object> hourFilter = null;
                        String hourText = hourFilterField.getText();
                        if (!hourText.isEmpty()) {
                            hourFilter = RowFilter.regexFilter(hourText, 1);
                        }

                        RowFilter<Object, Object> temperatureFilter = null;
                        String temperatureText = temperatureFilterField.getText();
                        if (!temperatureText.isEmpty()) {
                            temperatureFilter = RowFilter.regexFilter(temperatureText, 2);
                        }

                        RowFilter<Object, Object> humidityFilter = null;
                        String humidityText = humidityFilterField.getText();
                        if (!humidityText.isEmpty()) {
                            humidityFilter = RowFilter.regexFilter(humidityText, 3);
                        }

                        // Combinar los filtros en un solo RowFilter y aplicarlo al TableRowSorter
                        List<RowFilter<Object, Object>> filters = new ArrayList<>();
                        if (dateFilter != null) filters.add(dateFilter);
                        if (hourFilter != null) filters.add(hourFilter);
                        if (temperatureFilter != null) filters.add(temperatureFilter);
                        if (humidityFilter != null) filters.add(humidityFilter);
                        tableSorter.setRowFilter(RowFilter.andFilter(filters));
                    }
                };
                dateFilterField.getDocument().addDocumentListener(filterListener);
                hourFilterField.getDocument().addDocumentListener(filterListener);
                temperatureFilterField.getDocument().addDocumentListener(filterListener);
                humidityFilterField.getDocument().addDocumentListener(filterListener);
            }
        });
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
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace(";", " ");                
                line = line.replace("??", "°");
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

    private String formatDate(String dayStr, String monthStr, String yearStr) throws ParseException {
        String date = dayStr + "-" + monthStr + "-" + yearStr;
        return date;
    }

    private String formatHour(String hourStr, String minuteStr, String secondStr) throws ParseException {
        String hour = hourStr + ":" + minuteStr + ":" + secondStr;
        return hour;
    }

}


