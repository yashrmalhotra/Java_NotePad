import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PopMenu {
    Notepad source;
    JTextArea textArea;
    JPopupMenu popupMenu;
    JMenuItem copy, cut, paste, selectAll;
    PopMenu(Notepad source){
        this.source = source;
        textArea = source.getTextArea();
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3){
                    popupMenu.show(textArea,e.getX(),e.getY());
                }
            }
        });
        popupMenu = new JPopupMenu();
        copy = new JMenuItem("Copy");
        cut = new JMenuItem("Cut");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("Select All");
        addFunctionality();

    }
    private void addFunctionality(){
        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.copy();
            }

        });
        popupMenu.add(copy);
        cut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.cut();
            }

        });
        popupMenu.add(cut);

        paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.paste();
            }
        });
        popupMenu.add(paste);

        selectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.selectAll();
            }
        });
        popupMenu.addSeparator();
        popupMenu.add(selectAll);
    }
}
