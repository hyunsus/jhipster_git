package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Attach.
 */
@Entity
@Table(name = "attach")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Attach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "org")
    private Integer org;

    @Column(name = "name")
    private String name;

    @Column(name = "orig_name")
    private String origName;

    @Column(name = "ext")
    private String ext;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "path")
    private String path;

    @Column(name = "file_size")
    private Long fileSize;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "modified_at")
    private ZonedDateTime modifiedAt;

    @Column(name = "modified_by")
    private String modifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "post", "attaches" }, allowSetters = true)
    private AttachGroup attachGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Attach id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrg() {
        return this.org;
    }

    public Attach org(Integer org) {
        this.setOrg(org);
        return this;
    }

    public void setOrg(Integer org) {
        this.org = org;
    }

    public String getName() {
        return this.name;
    }

    public Attach name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigName() {
        return this.origName;
    }

    public Attach origName(String origName) {
        this.setOrigName(origName);
        return this;
    }

    public void setOrigName(String origName) {
        this.origName = origName;
    }

    public String getExt() {
        return this.ext;
    }

    public Attach ext(String ext) {
        this.setExt(ext);
        return this;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getContentType() {
        return this.contentType;
    }

    public Attach contentType(String contentType) {
        this.setContentType(contentType);
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPath() {
        return this.path;
    }

    public Attach path(String path) {
        this.setPath(path);
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public Attach fileSize(Long fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Attach createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Attach createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getModifiedAt() {
        return this.modifiedAt;
    }

    public Attach modifiedAt(ZonedDateTime modifiedAt) {
        this.setModifiedAt(modifiedAt);
        return this;
    }

    public void setModifiedAt(ZonedDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Attach modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public AttachGroup getAttachGroup() {
        return this.attachGroup;
    }

    public void setAttachGroup(AttachGroup attachGroup) {
        this.attachGroup = attachGroup;
    }

    public Attach attachGroup(AttachGroup attachGroup) {
        this.setAttachGroup(attachGroup);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attach)) {
            return false;
        }
        return id != null && id.equals(((Attach) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attach{" +
            "id=" + getId() +
            ", org=" + getOrg() +
            ", name='" + getName() + "'" +
            ", origName='" + getOrigName() + "'" +
            ", ext='" + getExt() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", path='" + getPath() + "'" +
            ", fileSize=" + getFileSize() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            "}";
    }
}
