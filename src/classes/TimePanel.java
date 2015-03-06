package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Home on 02.03.15.
 */
public class TimePanel {

    public static JPanel getjPanel() {
        /*создаем панель для выбора периода времени за который хотим загрузить заметки
        * настраиваем панель*/
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.setPreferredSize(new Dimension(130, 200));

        /*создаем 4 кнопки со слушателями событий
        * выставляем необходимые настройки
        * добавляем созданные кнопки на панель*/
        createRigidArea(jPanel);
        JButton today = new JButton("Сегодня");
        today.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classes.Frame.getEvents(0);
            }
        });
        setSameSett(today);
        jPanel.add(today);
        createRigidArea(jPanel);
        JButton week = new JButton("7 дней");
        week.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classes.Frame.getEvents(7);
            }
        });
        setSameSett(week);
        jPanel.add(week);
        createRigidArea(jPanel);
        JButton month = new JButton("30 дней");
        month.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classes.Frame.getEvents(30);
            }
        });
        setSameSett(month);
        jPanel.add(month);
        createRigidArea(jPanel);
        JButton year = new JButton("365 дней");
        year.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classes.Frame.getEvents(365);
            }
        });
        setSameSett(year);
        jPanel.add(year);
        return jPanel;
    }

    /*метод создает отступы для указанной панели*/
    private static void createRigidArea(JPanel jPanel) {
        jPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    }

    /*метод для задания необходимых настроек для кнопок*/
    private static void setSameSett(JButton button) {

        ImageIcon icon = new ImageIcon("src/main/resources/calender-drawing-icon.png");
        Dimension d = new Dimension (120, 30);
        button.setPreferredSize(d);
        button.setMinimumSize(d);
        button.setMaximumSize(d);
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.setBackground(Color.WHITE);
        button.setIcon(icon);
    }

}
