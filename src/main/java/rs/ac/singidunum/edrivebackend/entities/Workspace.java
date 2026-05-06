package rs.ac.singidunum.edrivebackend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name="workspace")
@Table( uniqueConstraints = {
        @UniqueConstraint(name = "uq_workspace_user_id_name", columnNames = {"name", "user_id"}),
})
public class Workspace extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workspace_id")
    private Integer workspaceId;

    @Column(nullable = false)
    private String name;

    @Column(name="user_id", nullable = false)
    private Integer user_id;

    // Relations

    @ManyToOne()
    @JoinColumn(name="user_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_workspace_user_id") )
    @JsonBackReference
    private User user;
}
