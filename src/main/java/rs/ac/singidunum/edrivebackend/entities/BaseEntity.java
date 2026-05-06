package rs.ac.singidunum.edrivebackend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@MappedSuperclass
public class BaseEntity {
    @Column(name="created_at")
    @CreationTimestamp
    private String createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private String updatedAt;
}
