import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class KeyShortcuts {
    Notepad source;
    Action bold = new BoldAction();
    Action italic = new ItaliAction();
    Action increase = new IncreaseAction();
    Action decrease = new DecreaseAction();

    KeyShortcuts(Notepad source){
        this.source = source;
        source.getTextArea().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK),"bold");
        source.getTextArea().getActionMap().put("bold",bold);

        source.getTextArea().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_I,InputEvent.CTRL_DOWN_MASK),"italic");
        source.getTextArea().getActionMap().put("italic",italic);

        source.getTextArea().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD,InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK),"increase");
        source.getTextArea().getActionMap().put("increase",increase);

        source.getTextArea().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA,InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK),"decrease");
        source.getTextArea().getActionMap().put("decrease",decrease);
    }
    class BoldAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e) {
            Font font = source.getTextArea().getFont();
            int bold = font.getStyle();
            if(bold == Font.PLAIN )
            source.getTextArea().setFont(new Font(font.getName(),Font.BOLD,font.getSize()));

            else if (bold==Font.ITALIC)
                source.getTextArea().setFont(new Font(font.getName(),Font.ITALIC | Font.BOLD,font.getSize()));
            else if(bold==(Font.ITALIC | Font.BOLD))
                source.getTextArea().setFont(new Font(font.getName(),Font.ITALIC,font.getSize()));
            else
                source.getTextArea().setFont(new Font(font.getName(),Font.PLAIN,font.getSize()));
        }
    }
    class ItaliAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e) {
            Font font = source.getTextArea().getFont();
            int itailic = font.getStyle();
            if(itailic == Font.PLAIN )
                source.getTextArea().setFont(new Font(font.getName(),Font.ITALIC,font.getSize()));
            else if (itailic==(Font.BOLD))
                source.getTextArea().setFont(new Font(font.getName(),Font.BOLD | Font.ITALIC,font.getSize()));
            else if(itailic==(Font.BOLD | Font.ITALIC))
                source.getTextArea().setFont(new Font(font.getName(),Font.BOLD,font.getSize()));
            else
                source.getTextArea().setFont(new Font(font.getName(),Font.PLAIN,font.getSize()));
        }
    }
    class IncreaseAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e) {
            Font font = source.getTextArea().getFont();

            source.getTextArea().setFont(new Font(font.getName(),font.getStyle(),font.getSize()+3));
        }
    }
    class DecreaseAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e) {

            Font font = source.getTextArea().getFont();
            if(font.getSize()>3)
                   source.getTextArea().setFont(new Font(font.getName(), font.getStyle(), font.getSize()-3));
            else
                source.getTextArea().setFont(new Font(font.getName(), font.getStyle(), font.getSize()));
        }
    }


}
