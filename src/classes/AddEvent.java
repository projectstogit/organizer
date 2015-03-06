package classes;

import hibernateclasses.HibernateUtil;
import hibernateclasses.Event;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Home on 04.03.15.
 */
public class AddEvent {

    public static void getFrame(){
        /*Создаем и настраиваем окно для добавления заметки*/
        final JFrame addEventFrame = new JFrame();
        addEventFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addEventFrame.setLayout(new BorderLayout());
        addEventFrame.setResizable(false);
        addEventFrame.setTitle("Добавить заметку");
        addEventFrame.setVisible(true);

        /*Создаем и настраиваем поле, где будут выведены события из БД*/
        JPanel addEventPanel = new JPanel();
        addEventPanel.setLayout(new GridLayout(5, 1));
        addEventPanel.setVisible(true);

        /*Создаем основной шрифт для всех элементов окна*/
        Font font = new Font("Verdana", Font.PLAIN, 11);

        /*Добавляем описание и поле для выбора даты*/
        JLabel eventDate = new JLabel();
        eventDate.setText("Дата:");
        setFontAndAlignment(font, eventDate);
        setInsets(eventDate);
        Date date = new Date();
        SpinnerDateModel jSpinnerModel = new SpinnerDateModel(date, null, null, Calendar.DATE);
        final JSpinner addEventDate = new JSpinner(jSpinnerModel);
        final JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(addEventDate, "dd/MM/yyyy");
        addEventDate.setEditor(dateEditor);
        dateEditor.getTextField().setEditable(false);

        /*Добавляем описание и поле для установки времени*/
        JLabel eventTime = new JLabel();
        eventTime.setText("Время:");
        setFontAndAlignment(font, eventTime);
        setInsets(eventTime);
        DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        final JFormattedTextField addEventTime = new JFormattedTextField(dateFormat);
        addEventTime.setCaretPosition(0);
        /* Устанавливаем маску для ввода и ловим исключения*/
        try {
            MaskFormatter maskFormatter = new MaskFormatter("##:##");
            maskFormatter.setPlaceholderCharacter('_');
            if (!maskFormatter.getValueContainsLiteralCharacters())
            {maskFormatter.setMask(maskFormatter.getMask());}
            maskFormatter.install(addEventTime);
        }
        catch (ParseException e)
        {
            JOptionPane wrongInputFormat = new JOptionPane();
            wrongInputFormat.setAlignmentX(Component.LEFT_ALIGNMENT);
            wrongInputFormat.showMessageDialog(null, "Неверный формат времени", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        /*Добавляем описание и дропдаунлист (напоминание о событии)*/
        JLabel remember = new JLabel();
        remember.setText("Напомнить:");
        setFontAndAlignment(font, remember);
        setInsets(remember);
        String[] items = {
                "да",
                "нет"
        };
        final JComboBox addRemember = new JComboBox(items);
        addRemember.setEditable(false);

        /*Добавляем описание и дропдаунлист для установки важности события*/
        JLabel impotance = new JLabel();
        impotance.setText("Важно:");
        setFontAndAlignment(font, impotance);
        setInsets(impotance);
        String[] itemsImp = {
                "обычная",
                "повышенная",
                "высокая"
        };
        final JComboBox addImportance = new JComboBox(itemsImp);
        addImportance.setEditable(false);

        /*Добавляем описание и поле для добавления текста заметки*/
        JLabel describe = new JLabel();
        describe.setText("Текст заметки:");
        setFontAndAlignment(font, describe);
        setInsets(describe);
        final JTextField addDescribe = new JTextField();

        /*Добавляем кнопку с слушателем событий для сохранения данных полей в БД*/
        JButton addEventButton = new JButton();
        addEventButton.setText("Добавить");
        addEventButton.setFont(font);
        addEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /*описываем условия при которых заметка будет добавлена в БД*/
                if (!addDescribe.getText().isEmpty() && !addEventTime.getText().isEmpty() && addEventTime != null) {
                    try {
                        /*Создаем сессию в Hiberanate*/
                        Session session = HibernateUtil.getSessionFactory().openSession();
                        session.beginTransaction();

                        /*устанавливаем значение для поля (активировано ли напомининание о событии),
                        * поскольку это поле принимает только логическое значение*/
                        Boolean isAddRememerActiveted;
                        if (addRemember.getSelectedIndex() == 0) {
                            isAddRememerActiveted = true;
                        } else {
                            isAddRememerActiveted = false;
                        }

                        /*создаем новый обьект и заполняем его введенными данными*/
                        Event event = new Event(Login.getLog, (Date) addEventDate.getValue(), addEventTime.getText(), addDescribe.getText(), isAddRememerActiveted,
                                addImportance.getSelectedIndex());
                        /*сохраняем новый обьект в БД*/
                        session.save(event);
                        /*закрываем окно*/
                        addEventFrame.dispose();
                        /*закрываем сессию в Hibernate*/
                        session.getTransaction().commit();
                        HibernateUtil.shutdown();
                        /*ловим исключения, которые возникли в процессе добавления обьекта в БД*/
                    } catch (HibernateException e1) {
                        JOptionPane wrongInputFormat = new JOptionPane();
                        wrongInputFormat.setAlignmentX(Component.LEFT_ALIGNMENT);
                        wrongInputFormat.showMessageDialog(null, "Неверный формат даты", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                /*если не заполнены какие-то поля выводим соответствующее сообщение*/
                } else {
                    JOptionPane empty = new JOptionPane();
                    empty.setAlignmentX(Component.LEFT_ALIGNMENT);
                    empty.showMessageDialog(null, "Не указано время или описание события", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        });


        /*создаем отдельную панель для вывода кнопки "Добавить" и добавляем кнопку на панель
        * Это нужно для правильной расстановки элементов в окне*/
        JPanel addEventButtonPanel = new JPanel();
        addEventButtonPanel.setLayout(new GridBagLayout());
        addEventButtonPanel.add(addEventButton);

        /*добавляем все созданные элементы в основную панель*/
        addEventPanel.add(eventDate);
        addEventPanel.add(addEventDate);
        addEventPanel.add(eventTime);
        addEventPanel.add(addEventTime);
        addEventPanel.add(remember);
        addEventPanel.add(addRemember);
        addEventPanel.add(impotance);
        addEventPanel.add(addImportance);
        addEventPanel.add(describe);
        addEventPanel.add(addDescribe);


        /*добавляем в главное окно основную панель
        * Настраиваем размер и позицию главного окна*/
        addEventFrame.add(addEventPanel, BorderLayout.CENTER);
        addEventFrame.add(addEventButtonPanel, BorderLayout.SOUTH);
        addEventFrame.pack();
        addEventFrame.setSize(350, 210);
        addEventFrame.setLocationRelativeTo(null);
    }

    /*метод устанавливаем шрифт и выравнивание для всех JLabel*/
    private static void setFontAndAlignment(Font font, JLabel login) {
        login.setFont(font);
        login.setHorizontalAlignment(SwingConstants.LEFT);
    }

    /*метод устанавливем отступы для элементов окна*/
    private static Insets setInsets(JComponent element) {
        Insets insets = element.getInsets();
        element.setBorder(BorderFactory.createEmptyBorder(insets.top, 5, insets.bottom, insets.right));
        return insets;
    }

}
