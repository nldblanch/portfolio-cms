package com.nb.portfolio.cms.services;


import com.nb.portfolio.cms.dtos.ContentDTO;

import java.util.List;

public interface ContentService {

    ContentDTO save(ContentDTO content);

    List<ContentDTO> saveAll(List<ContentDTO> contents);

    List<ContentDTO> findAll();

    List<ContentDTO> findAll(List<String> ids);

    ContentDTO findOne(String id);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    void deleteAll();

    ContentDTO update(String id, ContentDTO content);

    long update(List<ContentDTO> contents);

    // Optional CMS-specific helpers
    ContentDTO convertMarkdown(ContentDTO content);

    ContentDTO publish(String id);

    ContentDTO generateSlug(ContentDTO content);
}

