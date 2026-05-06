package rs.ac.singidunum.edrivebackend.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name="file")
public class WFile extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file")
    private Integer fileId;

    @Column(nullable = false)
    private String name;

    @Column(name="workspace_id", nullable = false)
    private Integer workspaceId;

    // Relations

    @ManyToOne()
    @JoinColumn(name="workspace_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_file_workspace_id") )
    private Workspace workspace;

    @Column(name="parent_id")
    private Integer parentId;

    @ManyToOne()
    @JoinColumn(name="parent_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_file_parent_id") )
    private WFolder parentFolder;

    @OneToMany(mappedBy = "file")
    @JsonManagedReference
    private List<Envelope> envelopes;
}
