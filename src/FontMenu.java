import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.management.PlatformLoggingMXBean;

public class FontMenu extends JDialog {
    private Notepad source;
    JTextField currentFontStyleField;
    JTextField currentFontSizeField;
    JTextField currentfontInfo;
    JPanel currentColorBox;

    public FontMenu(Notepad source){
        this.source = source;
        this.setTitle("Font Settings");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(425,350);
        this.setLocationRelativeTo(source);
        this.setModal(true);

        addMenuComponents();
        this.setLayout(null);
    }
    private void addMenuComponents(){
        addFontChooser();
        addFontStyleChooser();
        addFontSizeChooser();
        addFontColorChooser();

        JButton applyButton = new JButton("Apply");
        applyButton.setBounds(230,255,75,25);
        applyButton.setFocusable(false);
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fontType = currentfontInfo.getText();
                int fontStyle;
                switch (currentFontStyleField.getText()){
                    case "Plain":
                        fontStyle = Font.PLAIN;
                        break;
                    case "Bold":
                        fontStyle =Font.BOLD;
                        break;
                    case "Italic":
                        fontStyle = Font.ITALIC;
                        break;
                    default:
                        fontStyle = Font.BOLD | Font.ITALIC;
                        break;

                }
                int fontSize = Integer.parseInt(currentFontSizeField.getText());
                Color fontColor = currentColorBox.getBackground();
                Font font = new Font(fontType,fontStyle,fontSize);

                source.getTextArea().setFont(font);

                source.getTextArea().setForeground(fontColor);


            }
        });
        this.add(applyButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(315,255,75,25);
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FontMenu.this.dispose();
            }
        });
        this.add(cancelButton);
    }
    private void addFontChooser(){
        JLabel fontLabel = new JLabel("Font:");
        fontLabel.setBounds(10,5,125,10);
        this.add(fontLabel);

        JPanel panel = new JPanel();
        panel.setBounds(10,15,125,170);
        panel.setBackground(Color.WHITE);


        currentfontInfo = new JTextField(source.getTextArea().getFont().getFontName());
        currentfontInfo.setPreferredSize(new Dimension(125,25));
        currentfontInfo.setEditable(false);
        currentfontInfo.setBackground(Color.white);
        panel.add(currentfontInfo);

        JPanel listofFontPanel = new JPanel();
        listofFontPanel.setBackground(Color.WHITE);

        //change our layout only have one column to display each font properly
        listofFontPanel.setLayout(new BoxLayout(listofFontPanel,BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(listofFontPanel);
        scrollPane.setPreferredSize(new Dimension(125,125));

        //retrieve all of the possible font
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();


        //for each fontNames we are going to display them to our listofFontPanel as a jLabel
        for(String fontName : fontNames){
            JLabel fontNameLabel = new JLabel(fontName);
            fontNameLabel.setFont(new Font("",Font.PLAIN,12));
            int labelsize = fontNameLabel.getFont().getSize();

            int font = fontNameLabel.getFont().getStyle();

            fontNameLabel.setFont(new Font(fontName,font,labelsize));
            fontNameLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    currentfontInfo.setText(fontName);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    fontNameLabel.setOpaque(true);
                    fontNameLabel.setBackground(Color.BLUE);
                    fontNameLabel.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    fontNameLabel.setBackground(null);
                    fontNameLabel.setForeground(null);
                }
            });

            listofFontPanel.add(fontNameLabel);

        }
        panel.add(scrollPane);




        this.add(panel);

    }

    private void addFontStyleChooser() {
        JLabel fontStyleLabel = new JLabel("Font Style:");
        fontStyleLabel.setBounds(145,5,125,10);
        this.add(fontStyleLabel);

        JPanel fontStylePanel = new JPanel();
        fontStylePanel.setBounds(145,15,125,170);
        fontStylePanel.setBackground(Color.white);

        int currentFontstyle = source.getTextArea().getFont().getStyle();
        String currentFontStyleText;
        switch (currentFontstyle){
            case Font.PLAIN:
                currentFontStyleText = "Plain";
                break;
            case Font.BOLD:
                currentFontStyleText = "Bold";
                break;
            case Font.ITALIC:
                currentFontStyleText = "Italic";
                break;
            default:
                currentFontStyleText = "Bold Italic";
        }
        currentFontStyleField = new JTextField(currentFontStyleText);
        currentFontStyleField.setPreferredSize(new Dimension(125,25));
        currentFontStyleField.setEditable(false);
        currentFontStyleField.setBackground(Color.WHITE);
        fontStylePanel.add(currentFontStyleField);

        JPanel listofFontStylepanel = new JPanel();
        listofFontStylepanel.setLayout(new BoxLayout(listofFontStylepanel,BoxLayout.Y_AXIS));
        listofFontStylepanel.setBackground(Color.WHITE);


        JLabel plainStyle = new JLabel("Plain");
        plainStyle.setFont(new Font("Dialog",Font.PLAIN,12));

        plainStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentFontStyleField.setText(plainStyle.getText());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                plainStyle.setOpaque(true);
                plainStyle.setBackground(Color.BLUE);
                plainStyle.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                plainStyle.setBackground(null);
                plainStyle.setForeground(null);
            }
        });

        listofFontStylepanel.add(plainStyle);

        JLabel boldStyle = new JLabel("Bold");
        boldStyle.setFont(new Font("Dialog",Font.BOLD,12));
        boldStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentFontStyleField.setText(boldStyle.getText());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
               boldStyle.setOpaque(true);
               boldStyle.setBackground(Color.BLUE);
               boldStyle.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {

             boldStyle.setBackground(null);
             boldStyle.setForeground(null);
            }
        });


        listofFontStylepanel.add(boldStyle);

        JLabel italicStyle = new JLabel("Italic");
        italicStyle.setFont(new Font("Dialog",Font.ITALIC,12));
        italicStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentFontStyleField.setText(plainStyle.getText());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
               italicStyle.setOpaque(true);
               italicStyle.setBackground(Color.BLUE);
               italicStyle.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {

               italicStyle.setBackground(null);
               italicStyle.setForeground(null);
            }
        });

        listofFontStylepanel.add(italicStyle);

        JLabel bolditalic = new JLabel("Bold Italic");
        bolditalic.setFont(new Font("Dialog",Font.BOLD|Font.ITALIC,12));
        bolditalic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentFontStyleField.setText(plainStyle.getText());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                bolditalic.setOpaque(true);
                bolditalic.setBackground(Color.BLUE);
                bolditalic.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                bolditalic.setBackground(null);
                bolditalic.setForeground(null);
            }
        });

        listofFontStylepanel.add(bolditalic);

        JScrollPane scrollPane = new JScrollPane(listofFontStylepanel);
        scrollPane.setPreferredSize(new Dimension(125,125));
        ;

        fontStylePanel.add(scrollPane);
        this.add(fontStylePanel);


    }

    private void addFontSizeChooser(){
        JLabel fontsizeLabel = new JLabel("Font Size:");
        fontsizeLabel.setBounds(275,5,125,10);
        this.add(fontsizeLabel);

        JPanel fontSizePanel = new JPanel();
        fontSizePanel.setBounds(275,15,125,170);
        fontSizePanel.setBackground(Color.white);


        currentFontSizeField = new JTextField(String.valueOf(source.getTextArea().getFont().getSize()));
        currentFontSizeField.setPreferredSize(new Dimension(125,25));
        currentFontSizeField.setBackground(Color.white);
        fontSizePanel.add(currentFontSizeField);

        JPanel listOfFontSizePanel = new JPanel();
        listOfFontSizePanel.setLayout(new BoxLayout(listOfFontSizePanel,BoxLayout.Y_AXIS));
        listOfFontSizePanel.setBackground(Color.white);

        for(int i=8;i<=72;i+=2){
            JLabel listOffontSizeLable = new JLabel(String.valueOf(i));
            listOfFontSizePanel.add(listOffontSizeLable);
            listOffontSizeLable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    currentFontSizeField.setText(listOffontSizeLable.getText());
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    listOffontSizeLable.setOpaque(true);
                    listOffontSizeLable.setBackground(Color.BLUE);
                    listOffontSizeLable.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    listOffontSizeLable.setBackground(null);
                    listOffontSizeLable.setForeground(null);
                }
            });
        }
        JScrollPane scrollPane =  new JScrollPane(listOfFontSizePanel);
        scrollPane.setPreferredSize(new Dimension(125,125));


        fontSizePanel.add(scrollPane);
        this.add(fontSizePanel);


    }
    private void addFontColorChooser(){
        currentColorBox = new JPanel();
        currentColorBox.setBounds(175,200,23,23);

        currentColorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        currentColorBox.setBackground(source.getTextArea().getForeground());

        this.add(currentColorBox);

        JButton chooseColorButton = new JButton("Choose Color");
        chooseColorButton.setBounds(10,200 ,150 ,25 );
        chooseColorButton.setFocusable(false);
        chooseColorButton.setBackground(Color.RED);
        chooseColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null,"Select a color",Color.black);

                currentColorBox.setBackground(c);
            }
        });
        this.add(chooseColorButton);
    }
}
