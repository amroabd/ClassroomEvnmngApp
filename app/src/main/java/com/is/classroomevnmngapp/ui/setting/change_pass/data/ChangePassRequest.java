package com.is.classroomevnmngapp.ui.setting.change_pass.data;

public class ChangePassRequest {
    private final int userId;
    private final String currentPassword;
    private final String newPassword;

    public ChangePassRequest(int userId, String currentPassword, String newPassword) {
        this.userId = userId;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public int getUserId() {
        return userId;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
