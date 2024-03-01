import javax.print.attribute.standard.JobImpressions;
import javax.print.attribute.standard.JobMediaSheets;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Notepad extends JFrame {
    private JFileChooser fileChooser;
    private JTextArea textArea = new JTextArea();
    public JTextArea getTextArea(){return textArea;}
    private File currentFile;
    JMenuItem newMenuItem;
    private UndoManager undoManager;
    JMenuItem saveMenuItem;
    JMenuItem saveasMenuItem;
    JMenuItem exitMenuItem;
    boolean isTextModified = false;
    JScrollPane scrollPane = new JScrollPane();
    public Notepad() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Notepad");
        this.setSize(500, 500);
        undoManager = new UndoManager();
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("src/files"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", ".txt"));
        undoManager = new UndoManager();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitMenuItem.doClick();
            }
        });

        addGuiComponnent();
        addKeyShorcut();

        addPopup();
        this.setVisible(true);

    }


    public void addGuiComponnent() {

        addToolBar();
        scrollPane = new JScrollPane(textArea);
        this.add(scrollPane,BorderLayout.CENTER);


    }

    public void addToolBar() {
        JToolBar toolBar = new JToolBar();
        JMenuBar menuBar = new JMenuBar();

        toolBar.setFloatable(false);
        toolBar.add(menuBar);
        menuBar.add(addFileMenu());
        menuBar.add(addEditMenu());
        menuBar.add(addFormatMenu());
        menuBar.add(addViewMenu());
        toolBar.setBackground(Color.BLUE);
        this.add(toolBar, BorderLayout.NORTH);
    }

    public JMenu addFileMenu() {

        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                //add each edit that we do in the text area(either adding or removing text.
                undoManager.addEdit(e.getEdit());
                isTextModified = true;
            }

        });
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isTextModified = true;

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isTextModified = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });


        JMenu file = new JMenu("File");
        newMenuItem = new JMenuItem("New");

        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Notepad newNotepad  = new Notepad();
                newNotepad.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        JMenuItem openMenuItem = new JMenuItem("Open");

        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isTextModified) {
                    int option = JOptionPane.showConfirmDialog(Notepad.this,
                            "Do you want to save", "Save", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        saveMenuItem.doClick();
                    } else if (option == JOptionPane.NO_OPTION) {
                        openHelper();
                    }
                } else openHelper();

            }

        });

        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));


        saveasMenuItem = new JMenuItem("Save As");

        saveasMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Save AS");
                int resut = fileChooser.showSaveDialog(null);

                if (resut == JFileChooser.CANCEL_OPTION) {
                    return;
                }

                    try {
                        File selectedfile = fileChooser.getSelectedFile();
                        String filename = selectedfile.getName();

                        if (selectedfile.exists()) {
                            int response = JOptionPane.showConfirmDialog(
                                    null,
                                    "File already exists. Do you want to overwrite it?",
                                    "Warning",
                                    JOptionPane.YES_NO_OPTION
                            );
                            if (response == JOptionPane.NO_OPTION || response == JOptionPane.CLOSED_OPTION) {
                                return; // User doesn't want to overwrite, return without saving
                            }
                        }

                        if (!filename.substring(filename.length() - 4).equalsIgnoreCase(".txt")) {
                            selectedfile = new File(selectedfile.getAbsolutePath() + ".txt");
                            selectedfile.createNewFile();
                            FileWriter writer = new FileWriter(selectedfile);
                            BufferedWriter bufferedWriter = new BufferedWriter(writer);
                            bufferedWriter.write(textArea.getText());
                            bufferedWriter.close();
                            writer.close();


                            setTitle(filename);
                            currentFile = selectedfile;
                            JOptionPane.showMessageDialog(Notepad.this, "File Sved");
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "File already exist");
                    }

            }
        });
        saveasMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));



        saveMenuItem = new JMenuItem("Save");

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if the currentfile is null then we perform  save
                if (currentFile == null)
                    saveasMenuItem.doClick();
                //if user chooses cancel
                if (currentFile == null) return;
                else {
                    try {
                        FileWriter write = new FileWriter(currentFile);
                        BufferedWriter writer = new BufferedWriter(write);
                        writer.write(textArea.getText());
                        writer.close();
                        write.close();
                        isTextModified = false;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK));

        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isTextModified){
                    Notepad.this.dispose();
                }else{
                    int result = JOptionPane.showConfirmDialog(Notepad.this,"Do you want to save",
                            "Save", JOptionPane.YES_NO_CANCEL_OPTION);
                    if(result==JOptionPane.YES_OPTION){
                        saveMenuItem.doClick();

                    } else if (result == JOptionPane.NO_OPTION){
                        Notepad.this.dispose();
                    }else Notepad.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }

        });

        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,InputEvent.ALT_DOWN_MASK));

        file.add(newMenuItem);
        file.add(openMenuItem);
        file.add(saveasMenuItem);
        file.add(saveMenuItem);
        file.addSeparator();
        file.add(exitMenuItem);

        return file;

    }

    public JMenu addEditMenu() {


        JMenu editmenu = new JMenu("Edit");
        JMenuItem undoMenuItem = new JMenuItem("Undo");

        undoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if there are any edits that we can undo,then we undo them

                if (undoManager.canUndo()) {
                    undoManager.undo();
                }


            }
        });
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK));

        editmenu.add(undoMenuItem);


        JMenuItem redoMenuItem = new JMenuItem("Redo");

        redoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (undoManager.canRedo()) {
                    undoManager.redo();

                }
            }
        });
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,KeyEvent.CTRL_DOWN_MASK));
        editmenu.add(redoMenuItem);


        return editmenu;

    }

    public JMenu addFormatMenu(){
        JMenu formatMenu = new JMenu("Format");
        JCheckBoxMenuItem wordWrap = new JCheckBoxMenuItem("Word Wrap");
        wordWrap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isChecked = wordWrap.getState();
                if(isChecked){
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                }else{
                    textArea.setLineWrap(false);
                    textArea.setWrapStyleWord(false);
                }
            }
        });

        formatMenu.add(wordWrap);

        JMenu aligntext = new JMenu("AlignText");
        formatMenu.add(aligntext);

        JMenuItem alignTextLeft = new JMenuItem("Left");
        aligntext.add(alignTextLeft);

        alignTextLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            }
        });

        alignTextLeft.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,InputEvent.CTRL_DOWN_MASK));

        JMenuItem alignTextRight = new JMenuItem("Right");
        aligntext.add(alignTextRight);

        alignTextRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            }
        });

        alignTextRight.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.CTRL_DOWN_MASK));


        JMenuItem fontMenuItem = new JMenuItem("Font");
        formatMenu.add(fontMenuItem);

        fontMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FontMenu(Notepad.this).setVisible(true);
            }
        });

        return formatMenu;
    }

    public JMenu addViewMenu(){
        JMenu viewMenu = new JMenu("View");
        JMenu zoomMenu = new JMenu("Zoom");
        JMenuItem zoomInItem = new JMenuItem("Zoom In");
        zoomInItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              Font currentFont = textArea.getFont();
              textArea.setFont(new Font(currentFont.getName(),currentFont.getStyle(),currentFont.getSize()+2));
            }
        });
        zoomMenu.add(zoomInItem);

        JMenuItem zoomOutItem = new JMenuItem("Zoom Out");
        zoomOutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Font currentFont = textArea.getFont();
                textArea.setFont(new Font(currentFont.getName(),currentFont.getStyle(),currentFont.getSize()-2));
            }
        });
        zoomMenu.add(zoomOutItem);

        JMenuItem zoomRestoreItem = new JMenuItem("Restore Default menu");
        zoomRestoreItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Font currentFont = textArea.getFont();
                textArea.setFont(new Font(currentFont.getName(),
                        currentFont.getStyle(),12));

            }
        });
        zoomMenu.add(zoomRestoreItem);

        viewMenu.add(zoomMenu);

        return viewMenu;
    }

    void addKeyShorcut(){
        KeyShortcuts ks = new KeyShortcuts(Notepad.this);
    }

    void openHelper() {
        int result = fileChooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) return;

        try {
            //reset notepad

            File selectedFile = fileChooser.getSelectedFile();
            currentFile = selectedFile;
            setTitle(selectedFile.getName());

            FileReader reader = new FileReader(selectedFile);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder filetext = new StringBuilder();
            String readText;

            while ((readText = bufferedReader.readLine()) != null) {
                filetext.append(readText + "\n");
            }
            textArea.setText(filetext.toString());

            if (undoManager.canUndo()) {
                undoManager.discardAllEdits();
            }
            isTextModified = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    void addPopup(){
        PopMenu pm = new PopMenu(this);
    }
}

