package com.nb.portfolio.cms.dtos;

import com.nb.portfolio.cms.exceptions.BadRequestException;
import com.nb.portfolio.cms.models.ContentEntity;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

public record ContentDTO(
        String id,
        String title,
        String slug,
        String rawMarkdown, // the README content
        String summary, // optional auto-generated summary
        List<String> tags,
        LocalDate createdAt,
        LocalDate updatedAt) {

    public ContentDTO(ContentEntity entity) {
        this(
                entity.getId() == null ? null : entity.getId().toHexString(),
                entity.getTitle(),
                entity.getSlug(),
                entity.getRawMarkdown(),
                entity.getSummary(),
                entity.getTags(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public ContentEntity toEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new ContentEntity(
                _id,
                title,
                slug,
                rawMarkdown,
                summary,
                tags,
                createdAt,
                updatedAt);
    }

    public void validate() {
        if (title == null || title.isBlank()) {
            throw new BadRequestException("title is required");
        }
        if (rawMarkdown == null) {
            throw new BadRequestException("rawMarkdown is required");
        }
        if (tags == null) {
            throw new BadRequestException("tags must not be null");
        }
    }

}

