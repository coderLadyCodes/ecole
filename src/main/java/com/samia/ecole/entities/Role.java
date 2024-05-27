package com.samia.ecole.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.samia.ecole.entities.RolePermission.*;

public enum Role {
     //PARENT, ADMIN, SUPER_ADMIN
     PARENT(
             Set.of( PARENT_CREATE_STUDENT,
                     PARENT_UPDATE_STUDENT
             )
     ),
     ADMIN(
             Set.of(
             ADMIN_CREATE_POST,
             ADMIN_READ,
             ADMIN_UPDATE_POST,
             ADMIN_DELETE_POST,
             ADMIN_DELETE_PARENT,
             ADMIN_DELETE_STUDENT
     )
     ),
     SUPER_ADMIN(
             Set.of(
                     SUPER_ADMIN_CREATE,
                     SUPER_ADMIN_READ,
                     SUPER_ADMIN_UPDATE,
                     SUPER_ADMIN_DELETE,
                     SUPER_ADMIN_DELETE_PARENT,
                     SUPER_ADMIN_DELETE_STUDENT,
                     ADMIN_CREATE_POST,
                     ADMIN_READ,
                     ADMIN_UPDATE_POST,
                     ADMIN_DELETE_POST,
                     ADMIN_DELETE_PARENT,
                     ADMIN_DELETE_STUDENT
             )
     );
     Set<RolePermission> permissions;
     Role() {
     }
     Role(Set<RolePermission> permissions) {
          this.permissions = permissions;
     }
     public Set<RolePermission> getPermissions() {
          return permissions;
     }
     public Collection<? extends GrantedAuthority> getAuthorities() {
          final List<SimpleGrantedAuthority> grantedAuthorityList = this.getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.name())
          ).collect(Collectors.toList());
          grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
          return grantedAuthorityList;
     }
}
