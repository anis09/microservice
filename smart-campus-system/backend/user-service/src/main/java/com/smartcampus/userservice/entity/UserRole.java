package com.smartcampus.userservice.entity;

public enum UserRole {
    STUDENT("Student"),
    TEACHER("Teacher"),
    ADMIN("Administrator");
    
    private final String displayName;
    
    UserRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}