package uz.uzkassa.smartposrestaurant.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import uz.uzkassa.smartposrestaurant.domain.base.AbstractAuditingEntity;
import uz.uzkassa.smartposrestaurant.domain.company.Company;
import uz.uzkassa.smartposrestaurant.dto.file.FileDTO;
import uz.uzkassa.smartposrestaurant.enums.EntityTypeEnum;

/**
 * Powered by Shuxratjon Rayimjonov
 * Date: 06.10.2022 17:35
 */

@Getter
@Setter
@Entity
@Table(name = "file_upload")
@SQLDelete(sql = "UPDATE file_upload set deleted='true' WHERE id=?")
public class FileUpload extends AbstractAuditingEntity {

    static final long serialVersionUID = 12L;

    @Column(name = "file_name")
    protected String fileName;

    @Column(name = "content_type")
    protected String contentType;

    @Column(name = "file_id")
    protected String fileId;

    @Column(name = "url")
    protected String url;

    @Column(name = "path")
    protected String path;

    @Column(name = "size")
    protected Long size;

    @Column(name = "entity_id")
    protected String entityId;

    @Column(name = "entity_type")
    @Enumerated(EnumType.STRING)
    protected EntityTypeEnum entityType;

    @Override
    public String getName() {
        return this.fileName;
    }

    public FileDTO toFileDTO() {
        return new FileDTO(id, url, getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileUpload)) {
            return false;
        }
        return id != null && id.equals(((FileUpload) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public FileDTO getFileDTO() {
        return new FileDTO(getId(),getUrl());
    }
}
