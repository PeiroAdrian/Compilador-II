import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Interfaz extends JFrame {
    private JTextArea txtAreaEntrada;
    private JTextArea txtAreaSalida;
    private JButton btnAccion;
    private JButton btnChooseFile;
    private File selectedFile;

    public Interfaz() {
        setTitle("Compilador");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        txtAreaEntrada = new JTextArea(10, 40);
        txtAreaSalida = new JTextArea(20, 40);
        btnAccion = new JButton("Ejecutar");
        btnChooseFile = new JButton("Seleccionar Archivo");

        txtAreaSalida.setEditable(false);

        // Agrega un espacio alrededor de los JTextAreas
        txtAreaEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        txtAreaSalida.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel textAreasPanel = new JPanel(new GridLayout(2, 1));
        textAreasPanel.add(new JScrollPane(txtAreaEntrada));
        textAreasPanel.add(new JScrollPane(txtAreaSalida));

        panel.add(textAreasPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnAccion);
        buttonPanel.add(btnChooseFile);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        btnAccion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos);
                System.setOut(ps);

                lexico b = new lexico();
                // b.run();

                System.out.flush();
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

                String consoleOutput = baos.toString();
                txtAreaSalida.setText(consoleOutput);
            }
        });

        btnChooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    loadFileContent(selectedFile);
                }
            }
        });

        // Agrega un DocumentListener para rastrear cambios en el txtAreaEntrada
        txtAreaEntrada.getDocument().addDocumentListener(new DocumentListener() {
            
            public void insertUpdate(DocumentEvent e) {
                saveFileContent(selectedFile, txtAreaEntrada.getText());
            }

          
            public void removeUpdate(DocumentEvent e) {
                saveFileContent(selectedFile, txtAreaEntrada.getText());
            }

       
            public void changedUpdate(DocumentEvent e) {
                saveFileContent(selectedFile, txtAreaEntrada.getText());
            }
        });
    }

    private void loadFileContent(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            txtAreaEntrada.setText(content.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveFileContent(File file, String content) {
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Interfaz frame = new Interfaz();
                frame.setVisible(true);
            }
        });
    }
}
