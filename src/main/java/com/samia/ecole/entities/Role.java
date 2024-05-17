package com.samia.ecole.entities;

public enum Role {
     PARENT, ADMIN, SUPER_ADMIN
//     PARENT(
//             Set.of( PARENT_CREATE_STUDENT,
//                     PARENT_CREATE,
//                     PARENT_READ_STUDENT,
//                     PARENT_UPDATE_STUDENT,
//                     PARENT_DELETE_STUDENT
//             )
//     ),
//     ADMIN(
//             Set.of(
//             ADMIN_CREATE_POST,
//             ADMIN_READ,
//             ADMIN_UPDATE_POST,
//             ADMIN_DELETE_POST
//     )
//     ),
//     SUPER_ADMIN(
//             Set.of(
//                     SUPER_ADMIN_CREATE,
//                     SUPER_ADMIN_READ,
//                     SUPER_ADMIN_UPDATE,
//                     SUPER_ADMIN_DELETE,
//                     ADMIN_CREATE_POST,
//                     ADMIN_READ,
//                     ADMIN_UPDATE_POST,
//                     ADMIN_DELETE_POST
//             )
//     );
//     Set<RolePermission> permissions;
//     Role() {
//     }
//     Role(Set<RolePermission> permissions) {
//          this.permissions = permissions;
//     }
//     public Set<RolePermission> getPermissions() {
//          return permissions;
//     }
//     public Collection<? extends GrantedAuthority> getAuthorities() {
//          final List<SimpleGrantedAuthority> grantedAuthorityList = this.getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.name())
//          ).collect(Collectors.toList());
//          grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//          return grantedAuthorityList;
//     }
}
