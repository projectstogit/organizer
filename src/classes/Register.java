package classes;

import hibernateclasses.HibernateUtil;
import hibernateclasses.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Home on 02.03.15.
 */
public class Register extends JFrame {

    public static void getForm()
    {
        /*создаем главное окно регистрации пользователя и задаем ему необходимые настройки*/
        final JFrame registerFrame = new JFrame();
        registerFrame.setLayout(new BorderLayout());
        registerFrame.setResizable(false);
        registerFrame.setTitle("Регистрация пользователя");
        registerFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        registerFrame.setVisible(true);

        /*создаем и настраиваем основную панель*/
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(8, 1));
        registerPanel.setVisible(true);

        /*создаем основной шрифт для окна регистрации*/
        Font font = new Font("Verdana", Font.PLAIN, 11);

        /*создаем описание и поле для ввода логина*/
        JLabel login = new JLabel();
        login.setText("Логин:");
        setFontAndAlignment(font, login);
        setInsets(login);
        final JTextField addLogin = new JTextField();

        /*создаем описание и поле для ввода пароль*/
        JLabel password = new JLabel();
        password.setText("Пароль:");
        setFontAndAlignment(font, password);
        setInsets(password);
        final JPasswordField addPassword = new JPasswordField();

        /*создаем описание и поле для повторного ввода пароля*/
        JLabel repeatPassword = new JLabel();
        repeatPassword.setText("Повторите пароль:");
        setFontAndAlignment(font, repeatPassword);
        setInsets(repeatPassword);
        final JPasswordField addRepeatPassword  = new JPasswordField();

        /*создаем описание и поле для ввода имени пользователя*/
        JLabel firstName = new JLabel();
        firstName.setText("Имя:");
        setFontAndAlignment(font, firstName);
        setInsets(firstName);
        final JTextField addFirstName = new JTextField();

        /*создаем описание и поле для ввода фамилии пользователя*/
        JLabel lastName = new JLabel();
        lastName.setText("Фамилия:");
        setFontAndAlignment(font, lastName);
        setInsets(lastName);
        final JTextField addLastName = new JTextField();

        /*создаем описание и поле для выбора даты рождения*/
        JLabel age = new JLabel();
        age.setText("Дата рождения:");
        setFontAndAlignment(font, age);
        setInsets(age);
        Date date = new Date();
        date.setYear(date.getYear()-30);
        SpinnerDateModel jSpinnerModel = new SpinnerDateModel(date, null, null, Calendar.DATE);
        final JSpinner addAge = new JSpinner(jSpinnerModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(addAge ,"dd/MM/yyyy");
        addAge.setEditor(dateEditor);

        /*создаем описание и дропдаунлист для выбора пола*/
        JLabel sex = new JLabel();
        sex.setText("Пол:");
        setFontAndAlignment(font, sex);
        setInsets(sex);
        String[] items = {
                "мужской",
                "женский"
        };
        final JComboBox addSex = new JComboBox(items);
        addSex.setEditable(false);

        /*создаем описание и поле для ввода email*/
        JLabel email = new JLabel();
        email.setText("Email:");
        setFontAndAlignment(font, email);
        setInsets(email);
        final JTextField addEmail = new JTextField();

        /*создаем кнопку "Регистрация" и добавляем ей слушатель событий*/
        JButton registerButton = new JButton();
        registerButton.setText("Зарегистрироваться");
        registerButton.setFont(font);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /*задаем условия для регистрации нового пользователя*/
                if (!addLogin.getText().isEmpty() && !addPassword.getText().isEmpty() && !addRepeatPassword.getText().isEmpty() && !addFirstName.getText().isEmpty()
                        && !addLastName.getText().isEmpty() && !addEmail.getText().isEmpty() && addPassword.getText().equals(addRepeatPassword.getText())
                        && addEmail.getText().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
                {
                    try {
                        /*Создаем сессию в Hibernate*/
                        Session session = HibernateUtil.getSessionFactory().openSession();
                        session.beginTransaction();

                        /*проверяем какой пол был выбран и устанавливаем соответствующее значение*/
                        Boolean isSexAdded;
                        if (addSex.getSelectedIndex()==0)
                        {
                            isSexAdded = true;
                        }
                        else
                        {
                            isSexAdded = false;
                        }
                        /*создаем две переменных типа Date и присваиваем им необходимые значения. Это необходимо для вычисления полных лет регстрируемого пользователя*/
                        Date currentDate = new Date();
                        Date age = (Date)addAge.getValue();
                        /*создаем пользователя с указанными параметрами и сохраняем в БД*/
                        User user = new User(addLogin.getText(), addPassword.getText(), addFirstName.getText(), addLastName.getText(), currentDate.getYear()-age.getYear(), isSexAdded, addEmail.getText() );
                        session.save(user);

                        /*закрываем сессию в Hibernate*/
                        session.getTransaction().commit();
                        HibernateUtil.shutdown();

                        /*закрываем окно регистрации и открываем окно входа пользователя*/
                        registerFrame.dispose();
                        Login.main(null);

                    }
                    /*перехватываем возникшие исключения при создании нового пользователя. Скорее всего, оно может быть только по одной причине,
                     по этому выводим соответствующее сообщение*/
                    catch (HibernateException e1)
                    {
                        JOptionPane empty = new JOptionPane();
                        empty.setAlignmentX(Component.LEFT_ALIGNMENT);
                        empty.showMessageDialog(null, "Пользователь с таким логином уже существует", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                /*если введенные пароли не совпадают выводим соответствующее сообщение*/
                else if (!addPassword.getText().equals(addRepeatPassword.getText()))
                {
                    JOptionPane empty = new JOptionPane();
                    empty.setAlignmentX(Component.LEFT_ALIGNMENT);
                    empty.showMessageDialog(null, "Введенные пароли не совпадают", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                /*если введенный email не соответствует указанному шаблону выводим соответствующее сообщение*/
                else if (!addEmail.getText().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$") && addEmail.getText()!=null)
                {
                    JOptionPane empty = new JOptionPane();
                    empty.setAlignmentX(Component.LEFT_ALIGNMENT);
                    empty.showMessageDialog(null, "Неверно введен e-mail", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                /**если указаны не все данные выводим соответствующее сообщение*/
                else {
                    JOptionPane empty = new JOptionPane();
                    empty.setAlignmentX(Component.LEFT_ALIGNMENT);
                    empty.showMessageDialog(null, "Указаны не все данные", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        });


        /*создаем панель для кнопки "Регистрации". Это необходимо для правильного размещения кнопки в окне
         * добавляем кнопку на панель */
        JPanel registerButtonPanel = new JPanel();
        registerButtonPanel.setLayout(new GridBagLayout());
        registerButtonPanel.add(registerButton);

        /*добавляем на основную панель все созданные элементы*/
        registerPanel.add(login);
        registerPanel.add(addLogin);
        registerPanel.add(password);
        registerPanel.add(addPassword);
        registerPanel.add(repeatPassword);
        registerPanel.add(addRepeatPassword);
        registerPanel.add(firstName);
        registerPanel.add(addFirstName);
        registerPanel.add(lastName);
        registerPanel.add(addLastName);
        registerPanel.add(age);
        registerPanel.add(addAge);
        registerPanel.add(sex);
        registerPanel.add(addSex);
        registerPanel.add(email);
        registerPanel.add(addEmail);


        /*добавляем на окно регистрации созданные панели
        * задаем окну размер и позицию*/
        registerFrame.add(registerPanel, BorderLayout.CENTER);
        registerFrame.add(registerButtonPanel, BorderLayout.SOUTH);
        registerFrame.pack();
        registerFrame.setSize(350, 260);
        registerFrame.setLocationRelativeTo(null);

    }

    /*метод устанавливаем необходимые шрифт и отступы для JLabel*/
    private static void setFontAndAlignment(Font font, JLabel login) {
        login.setFont(font);
        login.setHorizontalAlignment(SwingConstants.LEFT);
    }

    /*метод задаем оступы указанному компненту*/
    private static Insets setInsets(JComponent element) {
        Insets insets = element.getInsets();
        element.setBorder(BorderFactory.createEmptyBorder(insets.top, 5, insets.bottom, insets.right));
        return insets;
    }

}
