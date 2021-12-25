package rs.raf.agasic218rn.domaci3beagasic218rn.model;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String surname;

    @Embedded
    private PermissionList permissionList;

    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public PermissionList getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(PermissionList permissionList) {
        this.permissionList = permissionList;
    }
}
