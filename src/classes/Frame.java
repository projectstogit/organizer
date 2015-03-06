package classes;

import hibernateclasses.HibernateUtil;
import hibernateclasses.Event;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import javax.swing.*;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Created by Home on 28.02.15.
 */
public class Frame {


    static JFrame frame;
    public static JPanel textPane = new JPanel();
    /*панель для вывода заметок*/
    public static JPanel sob;

    public static void getFrame() {
        /*создаем и настраиваем основное окно программы*/
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setTitle("Ваш облачный органайзер");

        /*вызываем главное меню программы и соответствующего класса*/
        new Menu(frame).menu();

        /*вызываем панель для выбора периода выборки заметок*/
        TimePanel.getjPanel();
        frame.add(TimePanel.getjPanel(), BorderLayout.WEST);

        /*создаем обьект панели вывода заметок и настраиваем ее*/
        sob = new JPanel();
        sob.setLayout(new BorderLayout());
        sob.setBackground(Color.white);

        /*создаем поле-оглавление для блока вывода заметок*/
        JTextField sobTextPane = new JTextField();
        sobTextPane.setText("События за выбранный период:");
        sobTextPane.setBackground(Color.LIGHT_GRAY);
        sobTextPane.setEditable(false);
        sobTextPane.setHorizontalAlignment(SwingConstants.CENTER);

        /*добавляем поле в блок для вывода заметок*/
        sob.add(sobTextPane, BorderLayout.NORTH);
        sob.setVisible(true);

        /*создаем панель со скроллом и помещаем в нее панель с заметками, на тот случай, если все заметки
        * не влезут*/
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        sob.add(scrollPane, BorderLayout.CENTER);

        /*добавляем панель для вывода заметок на основное окно*/
        frame.add(sob, BorderLayout.CENTER);

        /*задаем настройки для основного окна*/
        frame.pack();
        frame.setVisible(true);
        frame.setSize(700, 600);
        frame.setState(java.awt.Frame.ICONIFIED);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setExtendedState(java.awt.Frame.NORMAL);
        frame.setLocationRelativeTo(null);
    }

    /*метод вывод все найденные в БД заметки за указанный период*/
    public static void getEvents (Integer days)
    {
        try {
            /*создаем сессию в Hibernate*/
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            /*задаем форматы вывода даты*/
            SimpleDateFormat eventDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat calendarFormat = new SimpleDateFormat("yyyy-MM-dd");

            /*создаем обьекты типа Calendar для определения периода за который нужно вывести заметки*/
            Calendar calendar1 = new GregorianCalendar();
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DAY_OF_MONTH, days);

            /*вытаскиваем из БД заметки за нужный период*/
            ArrayList<hibernateclasses.Event> events = (ArrayList<Event>)session.createQuery("from Event as event where login='"+Login.getLog+"'and date>='"
                    + calendarFormat.format(calendar1.getTime()) + "'and date<='"+calendarFormat.format(calendar.getTime())+"' order by date").list();

            /*очищаем панель от заметок каждый раз при выборе другого периода*/
            textPane.removeAll();

            textPane.setLayout(new BoxLayout(textPane, BoxLayout.Y_AXIS));
            /*создаем шрифт для заметок*/
            Font eventFont = new Font("Century Gothic", Font.PLAIN, 14);

            /*создаем переменную для каждой заметки*/
            JTextPane area;

            for (Event event : events)
            {
                /*создаем обьект для каждой новой заметки и присваиваем необходимые харакатеристики*/
                area = new JTextPane();

                area.setPreferredSize(new Dimension(sob.getWidth(), 35));
                area.setMinimumSize(new Dimension(sob.getWidth(), 35));
                area.setMaximumSize(new Dimension(sob.getWidth(), 35));
                area.setFont(eventFont);
                area.setHighlighter(new DefaultHighlighter());
                if (event.getImportance() == 0)
                {area.setBackground(new Color(235, 224, 195));}
                else if (event.getImportance() == 1)
                {area.setBackground(new Color(255, 247, 110));}
                else if (event.getImportance() == 2)
                {area.setBackground(new Color(240, 135, 135));}
                area.setAlignmentX(Component.LEFT_ALIGNMENT);
                area.setAlignmentY(Component.CENTER_ALIGNMENT);
                area.setBorder(BorderFactory.createLoweredSoftBevelBorder());
                area.setEditable(false);
                area.setText(eventDateFormat.format(event.getDate()) + " " + event.getTime() + " " + event.getText() + "\n");

                /*добавляем заметку на панель для вывода заметок*/
                textPane.add(area);
            }
            textPane.setFont(eventFont);

            /*когда на панель добавлены все заметки найденные в БД просим перерисовать панель*/
            textPane.repaint();

            /*закрываем сессию в Hibernate*/
            session.getTransaction().commit();
            HibernateUtil.shutdown();

    }

    /*ловим возникшие исключения при вытаскивании заметок из БД*/
    catch (HibernateException e)
    {System.out.println(e.getMessage());}
    }

}
