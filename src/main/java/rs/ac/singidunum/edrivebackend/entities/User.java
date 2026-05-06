package rs.ac.singidunum.edrivebackend.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name="user")
@Table( uniqueConstraints = {
        @UniqueConstraint(name = "uq_user_username", columnNames = "username"),
        @UniqueConstraint(name = "uq_email_username", columnNames = "email")
})
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    // Token Manager Data
    @Column(name = "public_key", columnDefinition = "TEXT")
    private String publicKey; // PEM

    @Column(name = "key_id")
    private String keyId; // UUID

    // Relations

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Workspace> workspaces;
}
