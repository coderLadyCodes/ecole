package com.samia.ecole.entities;

import java.util.Set;

import static com.samia.ecole.entities.RolePermission.*;

public enum Role {
     PARENT(
             Set.of( PARENT_CREATE_STUDENT,
                     PARENT_READ_STUDENT,
                     PARENT_UPDATE_STUDENT,
                     PARENT_DELETE_STUDENT
             )
     ),
     ADMIN(
             Set.of(
             ADMIN_CREATE_POST,
             ADMIN_READ,
             ADMIN_UPDATE_POST,
             ADMIN_DELETE_POST
     )
     ),
     SUPER_ADMIN(
             Set.of(
                     SUPER_ADMIN_CREATE,
                     SUPER_ADMIN_READ,
                     SUPER_ADMIN_UPDATE,
                     SUPER_ADMIN_DELETE,
                     ADMIN_CREATE_POST,
                     ADMIN_READ,
                     ADMIN_UPDATE_POST,
                     ADMIN_DELETE_POST
             )
     );
     Role() {
     }
     Role(Set<RolePermission> permissions) {
          this.permissions = permissions;
     }
     public Set<RolePermission> getPermissions() {
          return permissions;
     }
     Set<RolePermission> permissions;
}
