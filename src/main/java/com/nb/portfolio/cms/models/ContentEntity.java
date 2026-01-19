package com.nb.portfolio.cms.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Document(collection = "public_pages")
public class ContentEntity {

    @Id
    private ObjectId id;

    // Core content fields
    private String title;
    private String slug;
    private String rawMarkdown;   // original README or markdown
    private String summary;

    // Metadata
    private List<String> tags;

    // Timestamps
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public ContentEntity() {}

    public ContentEntity(ObjectId id,
                         String title,
                         String slug,
                         String rawMarkdown,
                         String summary,
                         List<String> tags,
                         LocalDate createdAt,
                         LocalDate updatedAt) {

        this.id = id;
        this.title = title;
        this.slug = slug;
        this.rawMarkdown = rawMarkdown;
        this.summary = summary;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ObjectId getId() {
        return id;
    }

    public ContentEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ContentEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSlug() {
        return slug;
    }

    public ContentEntity setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public String getRawMarkdown() {
        return rawMarkdown;
    }

    public ContentEntity setRawMarkdown(String rawMarkdown) {
        this.rawMarkdown = rawMarkdown;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public ContentEntity setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public ContentEntity setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public ContentEntity setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public ContentEntity setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Override
    public String toString() {
        return "ContentEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                ", tags=" + tags +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentEntity that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(slug, that.slug) &&
                Objects.equals(rawMarkdown, that.rawMarkdown) &&
                Objects.equals(summary, that.summary) &&
                Objects.equals(tags, that.tags) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, slug, rawMarkdown, summary, tags, createdAt, updatedAt);
    }
}
