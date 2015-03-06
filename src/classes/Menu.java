package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Home on 28.02.15.
 */
public class Menu {

    private static JFrame frame;

    public Menu(JFrame frame) {
        this.frame = frame;
    }

    public static void menu() {
        /*создаем основной шрифт для меню*/
        Font font = new Font("Verdana", Font.PLAIN, 11);

        /*создаем главное меню*/
        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(font);

        /*создаем пункт меню "Органайзер" и его подпункты
        * задаем каждому подпункту необходимые функции*/
        JMenu menu = new JMenu("Органайзер");
        menu.setFont(font);
        JMenuItem addEvent = new JMenuItem("Добавить событие");
        addEvent.setFont(font);
        addEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEvent.getFrame();
            }
        });
        JMenuItem close = new JMenuItem("Закрыть программу");
        close.setFont(font);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classes.Frame.frame.dispose();
            }
        });

        /*добавляем созданные подпункты в пункт меню*/
        menu.add(addEvent);
        menu.addSeparator();
        menu.add(close);

        /*создаем пункт меню "Дополнитель" и его подпункты
        * задаем каждому подпункту необходимые функции*/
        JMenu additionally = new JMenu("Дополнительно");
        additionally.setFont(font);
        JMenuItem about = new JMenuItem("О программе");
        about.setFont(font);
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane aboutPane = new JOptionPane();
                aboutPane.setAlignmentX(Component.LEFT_ALIGNMENT);
                aboutPane.showMessageDialog(null, "Простой органайзер, v.0.1(gamma)\n(c) Все права защищены 2015", "О программе", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        });
        JMenuItem help = new JMenuItem("Помощь");
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane helpPane = new JOptionPane();
                helpPane.setAlignmentX(Component.LEFT_ALIGNMENT);
                helpPane.showMessageDialog(null, "Раздел в разработке.", "Помощь", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        });
        help.setFont(font);

        /*добавляем созданные подпункты в пункт меню*/
        additionally.add(about);
        additionally.addSeparator();
        additionally.add(help);

        /*добавляем созданные пункты меню в главное меню
        * задаем необходимые настройки окну меню*/
        menuBar.add(menu);
        menuBar.add(additionally);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
        menuBar.setVisible(true);

    }

}
