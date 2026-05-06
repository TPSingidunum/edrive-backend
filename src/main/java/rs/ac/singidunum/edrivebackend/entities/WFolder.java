package rs.ac.singidunum.edrivebackend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name="folder")
public class WFolder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id")
    private Integer folderId;

    @Column(nullable = false)
    private String name;

    @Column(name="workspace_id", nullable = false)
    private Integer workspaceId;

    // Relations

    @ManyToOne()
    @JoinColumn(name="workspace_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_folder_workspace_id") )
    private Workspace workspace;

    @Column(name="parent_id")
    private Integer parentId;

    @ManyToOne()
    @JoinColumn(name="parent_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_folder_parent_id") )
    private WFolder parentFolder;

    @OneToMany(mappedBy = "parentFolder")
    private List<WFolder> childrenFolders;

    @OneToMany(mappedBy = "parentFolder")
    private List<WFile> childrenFiles;
}
