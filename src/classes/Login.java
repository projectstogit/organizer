package classes;

import hibernateclasses.HibernateUtil;
import hibernateclasses.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Home on 02.03.15.
 */


public class Login {

        /*строка для доступа к логину из любомго класса*/
        public static String getLog;

        public static void main(String[] args) {

        /*создаем и настраиваем окно авторизации*/
        final JFrame loginFrame = new JFrame();
        loginFrame.setLayout(new BorderLayout());
        loginFrame.setResizable(false);
        loginFrame.setTitle("Войти");
        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);

        /*создаем основной шрифт для окна и устанавливаем его же для диалоговых окон*/
        final Font font = new Font("Verdana", Font.PLAIN, 11);
        UIManager.put("OptionPane.messageFont", new FontUIResource(font));

        /*создаем и настраиваем панель для полей ввода данных*/
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(2, 1));
        loginPanel.setVisible(true);

        /*/*Добавляем описание и поле для для ввода логина*/
        JLabel login = new JLabel();
        login.setText("Логин:");
        setFontAndAlignment(font, login);
        setInsets(login);
        final JTextField addLogin = new JTextField();

        /*Добавляем описание и поле для ввода пароля*/
        JLabel password = new JLabel();
        password.setText("Пароль:");
        setFontAndAlignment(font, password);
        setInsets(password);
        final JPasswordField addPassword = new JPasswordField();

        /*создаем кнопку для входа в органайзер и добавляем слушатель событий*/
        JButton loginButton = new JButton();
        loginButton.setText("Войти");
        loginButton.setFont(font);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String log = addLogin.getText();
                String pas = addPassword.getText();

                /*добавляем условия для успешного входа*/
                if (log.isEmpty() || log == null || pas.isEmpty() || pas == null) {
                    JOptionPane emptyFields = new JOptionPane();
                    emptyFields.showMessageDialog(loginFrame, "Не введен логин или пароль", "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
                } else {

                    /*создаем сессию в Hibernate для загрузки данных пользователя*/
                    Session session = HibernateUtil.getSessionFactory().openSession();
                    session.beginTransaction();

                    try {
                       /*Десинхронизирем обьект*/
                        User user = (User) session.load(User.class, log);

                        /*закрываем окно авторизации о открываем основное окно программы*/
                        if (user.getPassword().equals(pas)) {
                            loginFrame.dispose();
                            getLog = log;
                            classes.Frame.getFrame();
                        }
                        /*выводим сообщение, если ввелен неверный пароль*/
                        else {
                            JOptionPane wrongPass = new JOptionPane();
                            wrongPass.showMessageDialog(null, "Неверный пароль");
                            return;
                        }
                    }
                    /*ловим исключения. В данном случае оно, вероятно, только одно, выводим соответствующее сообщение*/
                    catch (HibernateException e1) {
                        JOptionPane notFound = new JOptionPane();
                        notFound.showMessageDialog(null, "Пользователь с таким именем не найден");
                        return;
                    }
                    /*закрываем сессию Hibernate*/
                    session.getTransaction().commit();
                    HibernateUtil.shutdown();
                }

            }
        });
        /*создаем и настраиваем кнопку "Регистрация" на случай, если у пользователя еще нет своего аккаунта*/
        JButton regRequest = new JButton();
        regRequest.setText("Регистрация");
        regRequest.setFont(font);
        /*создаем слушатель событий для кнопки*/
        regRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*закрываем окно входа и открываем окно регистрации*/
                loginFrame.dispose();
                Register.getForm();
            }
        });

        /*добавляем созданные элементы на основную панель*/
        loginPanel.add(login);
        loginPanel.add(addLogin);
        loginPanel.add(password);
        loginPanel.add(addPassword);

        /*создаем панель для кнопок регистрации и входа и добавляем соответствующие кнопки*/
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.add(regRequest);
        buttonPanel.add(loginButton);

        /*добавляем созданные панели на основное окно и устанвливаем окну размер и позицию*/
        loginFrame.add(loginPanel, BorderLayout.CENTER);
        loginFrame.add(buttonPanel, BorderLayout.SOUTH);
        loginFrame.pack();
        loginFrame.setSize(300, 100);
        loginFrame.setLocationRelativeTo(null);


    }
    /*метод устанавливаем шрифт и выравлнивание для Jlabel*/
    public static void setFontAndAlignment(Font font, JLabel label) {
        label.setFont(font);
        label.setHorizontalAlignment(SwingConstants.LEFT);
    }

    /*метод устанавливаем оступы для компонентов*/
    public static Insets setInsets(JComponent element) {
        Insets insets = element.getInsets();
        element.setBorder(BorderFactory.createEmptyBorder(insets.top, 5, insets.bottom, insets.right));
        return insets;
    }

}
