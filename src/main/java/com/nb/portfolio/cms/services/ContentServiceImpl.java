package com.nb.portfolio.cms.services;

import com.nb.portfolio.cms.dtos.ContentDTO;
import com.nb.portfolio.cms.exceptions.NotFoundException;
import com.nb.portfolio.cms.models.ContentEntity;
import com.nb.portfolio.cms.repositories.admin.AdminContentRepository;
import com.nb.portfolio.cms.repositories.pub.PublicContentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    private final PublicContentRepository publicRepository;
    private final AdminContentRepository adminRepository;

    public ContentServiceImpl(PublicContentRepository publicRepository,
            AdminContentRepository adminRepository) {
        this.publicRepository = publicRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public ContentDTO save(ContentDTO ContentDTO) {
        var entity = ContentDTO.toEntity();
        var savedEntity = adminRepository.save(entity); // Write → admin client
        return new ContentDTO(savedEntity);
    }

    @Override
    public List<ContentDTO> saveAll(List<ContentDTO> contentEntities) {
        return contentEntities.stream()
                .map(ContentDTO::toEntity)
                .peek(adminRepository::save) // Write → admin client
                .map(ContentDTO::new)
                .toList();
    }

    @Override
    public List<ContentDTO> findAll() {
        return publicRepository.findAll().stream().map(ContentDTO::new).toList(); // Read → public client
    }

    @Override
    public List<ContentDTO> findAll(List<String> ids) {
        return publicRepository.findAllById(ids).stream().map(ContentDTO::new).toList(); // Read → public client
    }

    @Override
    public ContentDTO findOne(String id) {
        ContentEntity entity = publicRepository.findById(id).orElseThrow(() -> new NotFoundException(id)); // Read →
                                                                                                           // public
                                                                                                           // client
        return new ContentDTO(entity);
    }

    @Override
    public long count() {
        return publicRepository.count(); // Read → public client
    }

    @Override
    public long delete(String id) {
        adminRepository.deleteById(id); // Write → admin client
        return 1;
    }

    @Override
    public long delete(List<String> ids) {
        adminRepository.deleteAllById(ids); // Write → admin client
        return ids.size();
    }

    @Override
    public void deleteAll() {
        adminRepository.deleteAll(); // Write → admin client
    }

    @Override
    public ContentDTO update(String id, ContentDTO dto) {
        ContentEntity entity = publicRepository.findById(id) // Read → public client
                .orElseThrow(() -> new NotFoundException("Content not found: " + id));

        entity.setTitle(dto.title());
        entity.setSlug(dto.slug());
        entity.setRawMarkdown(dto.rawMarkdown());
        entity.setSummary(dto.summary());
        entity.setTags(dto.tags());
        entity.setUpdatedAt(LocalDate.now());

        ContentEntity updated = adminRepository.save(entity); // Write → admin client
        return new ContentDTO(updated);
    }

    @Override
    public long update(List<ContentDTO> contentEntities) {
        List<ContentEntity> entities = contentEntities.stream().map(ContentDTO::toEntity).toList();
        adminRepository.saveAll(entities); // Write → admin client
        return entities.size();
    }

    @Override
    public ContentDTO convertMarkdown(ContentDTO content) {
        return null;
    }

    @Override
    public ContentDTO publish(String id) {
        return null;
    }

    @Override
    public ContentDTO generateSlug(ContentDTO content) {
        return null;
    }
}
