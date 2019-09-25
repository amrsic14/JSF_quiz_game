/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.SecurityQuestion;
import entities.RegistrationRequest;
import entities.User;
import util.ImageHelper;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;
import util.PasswordUtil;
import util.SessionUtil;
import util.Transaction;

/**
 *
 * @author acamr
 */
@ManagedBean
@SessionScoped
@Named(value = "LoginController")
public class LoginController implements Serializable {

    int pageToShow = 0; // 0 - login  1 - sing up  2 - change password 3-forget password
    private final String redirect = "?faces-redirect=true";
    private String errorMessage;
    private boolean trueAnswer;
    private List<String> questions;
    private boolean canShowHome = false;

    private String username;
    private String password, confirmPassword, oldPassword;
    private String firstName;
    private String lastName;
    private String email;
    private String profession;
    private String idNumber;
    private String securityQuestion;
    private String answer, answered;
    private User.Gender gender;
    private UploadedFile uploadedFile;

    private void initQuestions() {
        Session session = Transaction.openSession();

        List questionList = session.createQuery("FROM SecurityQuestion").list();

        Transaction.closeSession();

        questions = new LinkedList<>();
        questionList.stream().filter((question) -> (question instanceof SecurityQuestion)).forEachOrdered((question) -> {
            questions.add(((SecurityQuestion) question).getQuestion());
        });
    }

    public String getStyleClass() {
        if (pageToShow == 1) {
            return "input-form-register";
        } else {
            return "input-form-login";
        }
    }

    public String login() {
        Session session = Transaction.openSession();

        Criteria querry = session.createCriteria(User.class);
        User user = (User) querry.add(Restrictions.eq("username", username)).uniqueResult();

        Transaction.closeSession();

        if (user == null) {
            errorMessage = "User does't exist";
            return "index";
        } else if (!Arrays.equals(user.getPassword(), PasswordUtil.digest.digest(password.getBytes()))) {
            errorMessage = "Wrong password";
            return "index";
        } else {
            errorMessage = "";
            SessionUtil.setUser(user);
            switch (user.getType()) {
                case Supervisor:
                    return "supervisor" + redirect;
                case Administrator:
                    return "admin" + redirect;
                case User:
                    canShowHome = true;
                    return "menu" + redirect;
                default:
                    return "index";
            }
        }
    }

    public String logout() {
        errorMessage = "";
        SessionUtil.getSession().invalidate();
        return "index" + redirect;
    }

    private boolean isLegalDate(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        return sdf.parse(s, new ParsePosition(0)) != null;
    }

    private boolean checkJMBG(String JMBG) {
        if (JMBG.length() != 13) {
            return false;
        }
        char[] letters = JMBG.toCharArray();
        String dd = "" + letters[0] + letters[1];
        String mm = "" + letters[2] + letters[3];

        if (letters[4] != '9' && letters[4] != '0') {
            return false;
        }
        String ggg = (letters[4] == '9' ? "19" : "20") + letters[5] + letters[6];

        if (!isLegalDate(ggg + "-" + mm + "-" + dd)) {
            return false;
        }

        String s = letters[9] + letters[10] + letters[11] + "";
        int bbb = Integer.parseInt(s);

        switch (gender) {
            case Male:
                if (bbb >= 500) {
                    return false;
                }
                break;
            case Female:
                if (bbb <= 499) {
                    return false;
                }
                break;
            default:
                return false;
        }

        int S = 7 * Integer.parseInt(letters[0] + "")
                + 6 * Integer.parseInt(letters[1] + "")
                + 5 * Integer.parseInt(letters[2] + "")
                + 4 * Integer.parseInt(letters[3] + "")
                + 3 * Integer.parseInt(letters[4] + "")
                + 2 * Integer.parseInt(letters[5] + "")
                + 7 * Integer.parseInt(letters[6] + "")
                + 6 * Integer.parseInt(letters[7] + "")
                + 5 * Integer.parseInt(letters[8] + "")
                + 4 * Integer.parseInt(letters[9] + "")
                + 3 * Integer.parseInt(letters[10] + "")
                + 2 * Integer.parseInt(letters[11] + "");

        int K = S % 11;
        int m = K;

        switch (m) {
            case 0:
                if (Integer.parseInt(letters[12] + "") == 0) {
                    return true;
                }
            case 1:
                return false;
            default:
                if (Integer.parseInt(letters[12] + "") == (11 - m)) {
                    return true;
                }
        }

        return false;
    }

    public void register() {
        if (!password.equals(confirmPassword)) {
            errorMessage = "Passwords doesn't match";
            return;
        }

        Session session = Transaction.openSession();

        if ((session.get(RegistrationRequest.class, username) != null) || (session.get(User.class, username) != null)) {
            session.getTransaction().commit();
            session.close();
            errorMessage = "Username already exists";
            return;
        }

        if (!checkJMBG(idNumber)) {
            Transaction.closeSession();
            errorMessage = "Wrong JMBG format";
            return;
        }

        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
        if (!handleImage(extension)) {
            return;
        }

        RegistrationRequest request = prepareRequest(extension);
        session.save(request);

        Transaction.closeSession();

        errorMessage = "";
        pageToShow = 0;
    }

    private boolean checkImgFormat(String extension) {
        if (!("png".equals(extension.toLowerCase()) || "jpg".equals(extension.toLowerCase()))) {
            errorMessage = "Only 'jpg' and 'png' formats are supported";
            return false;
        }
        return true;
    }

    private boolean handleImage(String extension) {
        if (extension != null) {
            if (!checkImgFormat(extension)) {
                return false;
            }
            try {
                if (!saveImage(extension)) {
                    return false;

                }
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class
                        .getName()).log(Level.SEVERE, null, ex);
                errorMessage = "Error when loading image";
                return false;
            }
        }
        return true;
    }

    private boolean saveImage(String extension) throws IOException {
        BufferedImage img = ImageIO.read(uploadedFile.getInputstream());
        if (img.getHeight() > 300 || img.getWidth() > 300) {
            errorMessage = "Wrong image size (300x300px allowed)";
            return false;
        }
        Path filePath = Paths.get(ImageHelper.imageFolderPath + username + "." + extension);
        Files.createFile(filePath);
        ImageIO.write(img, extension, new File(filePath.toString()));
        return true;
    }

    private RegistrationRequest prepareRequest(String extension) {
        RegistrationRequest request = new RegistrationRequest();
        if (extension != null) {
            request.setHasImage(true);
        }
        request.setUsername(username);
        request.setPassword(PasswordUtil.digest.digest(password.getBytes()));
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmail(email);
        request.setProfession(profession);
        request.setGender(gender);
        request.setIdNumber(idNumber);
        request.setSecurityQuestion(securityQuestion);
        request.setAnswer(answer);
        return request;
    }

    public void changePassword() {
        if (!password.equals(confirmPassword)) {
            errorMessage = "Passwords doesn't match";
            return;
        }
        
        if(!Pattern.matches("^(?=[a-zA-Z])((?!.*(\\S)\\1{2})(?=(.*[a-z]){3,})(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&amp;])[A-Za-z\\d@$!%*?&amp;]{8,12})$", password)){
            errorMessage = "Wrong password format";
            return;
        }

        Session session = Transaction.openSession();

        Criteria findUserQuerry = session.createCriteria(User.class);
        User user = (User) findUserQuerry.add(Restrictions.eq("username", username)).uniqueResult();

        if (user == null) {
            errorMessage = "User doesn't exist";
        } else if (!Arrays.equals(user.getPassword(), PasswordUtil.digest.digest(oldPassword.getBytes()))) {
            errorMessage = "Wrong old password";
        } else {
            user.setPassword(PasswordUtil.digest.digest(password.getBytes()));
            pageToShow = 0;
            errorMessage = "";
        }

        Transaction.closeSession();
    }

    public String enterAsGuest() {
        errorMessage = "";
        canShowHome = true;
        return "menu" + redirect;
    }

    public boolean isCanShowHome() {
        return canShowHome;
    }

    public String securityCheckForPassword() {
        Session session = Transaction.openSession();

        Criteria querry = session.createCriteria(User.class);
        User user = (User) querry.add(Restrictions.eq("username", username)).uniqueResult();

        Transaction.closeSession();

        if (user == null) {
            errorMessage = "User doesn't exist";
        } else if (!idNumber.equals(user.getIdNumber())) {
            errorMessage = "Wrong JMBG";
        } else {
            SessionUtil.setUser(user);
            errorMessage = "";
            return "showPassword";
        }

        return "index";
    }

    public User.Gender[] genderValues() {
        return User.Gender.values();
    }

    public boolean canShowPage(int pageId) {
        return pageId == pageToShow;
    }

    public void answerQuestion() {
        Session session = Transaction.openSession();

        Criteria querry = session.createCriteria(User.class);
        User user = (User) querry.add(Restrictions.eq("username", username)).uniqueResult();

        if (user.getAnswer().equals(answered)) {
            trueAnswer = true;
            errorMessage = "";
        } else {
            errorMessage = "Wrong answer";
        }

        Transaction.closeSession();
    }

    public String updatePassword() {
        Session session = Transaction.openSession();

        Criteria querry = session.createCriteria(User.class);
        User user = (User) querry.add(Restrictions.eq("username", username)).uniqueResult();

        user.setPassword(PasswordUtil.digest.digest(password.getBytes()));
        session.update(user);

        Transaction.closeSession();

        trueAnswer = false;
        errorMessage = "";
        username = "";
        idNumber = "";
        password = "";
        answered = "";
        pageToShow = 0;
        return "index";
    }

    public void forgotPassword() {
        pageToShow = 3;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(boolean trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getPageToShow() {
        return pageToShow;
    }

    public void setPageToShow(int pageToShow) {
        errorMessage = "";
        this.pageToShow = pageToShow;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public User.Gender getGender() {
        return gender;
    }

    public void setGender(User.Gender gender) {
        this.gender = gender;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getQuestions() {
        initQuestions();
        return questions;
    }

    public String getAnswered() {
        return answered;
    }

    public void setAnswered(String answered) {
        this.answered = answered;
    }

//    public static void main(String[] args) {
//        LoginController lc = new LoginController();
//        
//        if(lc.checkJMBG("0806997740046")) System.out.println("OK"); else System.out.println("NOT_OK"); 
//    }
}
