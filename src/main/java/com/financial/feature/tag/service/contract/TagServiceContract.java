package com.financial.feature.tag.service.contract;

import com.financial.feature.tag.dto.TagDTO;
import com.financial.feature.tag.entity.Tag;
import com.financial.feature.transaction.dto.TransactionDTO;
import com.financial.feature.transaction.entity.Transaction;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface TagServiceContract {
    List<TagDTO> list();
    TagDTO get(Long id);
    Response create(TagDTO dto);
    TagDTO update(Long id, TagDTO dto);
    void delete(Long id);
    Tag findByID(Long id);
}
