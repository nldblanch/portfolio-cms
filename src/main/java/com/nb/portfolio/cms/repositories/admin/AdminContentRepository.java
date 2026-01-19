package com.nb.portfolio.cms.repositories.admin;

import com.nb.portfolio.cms.models.ContentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminContentRepository extends MongoRepository<ContentEntity, String> {
    // Write operations using admin client
    // MongoRepository provides: save, saveAll, delete, deleteById, deleteAll, etc.
}
