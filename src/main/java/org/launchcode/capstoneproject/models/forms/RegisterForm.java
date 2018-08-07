package org.launchcode.capstoneproject.models.forms;

import javax.validation.constraints.NotNull;


public class RegisterForm extends LoginForm {

    @NotNull(message = "Passwords does not match")
    private String verifyPassword;

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
        verifyRegistrationPassword();
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
        verifyRegistrationPassword();

    }

    private void verifyRegistrationPassword() {
        if (!getPassword().equals(verifyPassword)) {
            verifyPassword = null;
        }
    }
}
