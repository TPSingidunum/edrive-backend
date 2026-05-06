package rs.ac.singidunum.edrivebackend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name="envelope")
@Table( uniqueConstraints = {
        @UniqueConstraint(name = "uq_envelope_user_id_file_id", columnNames = {"user_id", "file_id"}),
})
public class Envelope extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "envelope_id")
    private Integer envelopeId;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="file_id")
    private Integer fileId;

    @Column(name="iv")
    private String iv;

    @Column(name="dek_key")
    private String dekKey;

    // Relations
    @ManyToOne()
    @JoinColumn(name="user_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_envelope_user_id") )
    @JsonBackReference
    private User user;

    @ManyToOne()
    @JoinColumn(name="file_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_envelope_file_id") )
    @JsonBackReference
    private WFile file;
}
