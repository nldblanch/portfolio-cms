package com.nb.portfolio.cms.repositories.pub;

import com.nb.portfolio.cms.models.ContentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicContentRepository extends MongoRepository<ContentEntity, String> {
    // Read-only operations using public client
    // MongoRepository provides: findAll, findById, count, exists, etc.
}
