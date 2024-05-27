package com.samia.ecole.entities;

public enum RolePermission {
    // SUPER ADMIN
    SUPER_ADMIN_CREATE,
    SUPER_ADMIN_READ,
    SUPER_ADMIN_UPDATE,
    SUPER_ADMIN_DELETE_PARENT,
    SUPER_ADMIN_DELETE_STUDENT,
    SUPER_ADMIN_DELETE,

    // ADMIN
    ADMIN_CREATE_POST,
    ADMIN_READ,
    ADMIN_UPDATE_POST,
    ADMIN_DELETE_POST,
    ADMIN_DELETE_PARENT,
    ADMIN_DELETE_STUDENT,

    // PARENT
    PARENT_CREATE_STUDENT,
    PARENT_UPDATE_STUDENT;

    private String valeur;
    RolePermission() {
    }
    RolePermission(String valeur) {
        this.valeur = valeur;
    }
    public String getValeur() {
        return valeur;
    }
}
