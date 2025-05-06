package com.financial.feature.tag.service;

import com.financial.feature.account.service.contract.AccountServiceContract;
import com.financial.feature.tag.dto.TagDTO;
import com.financial.feature.tag.entity.Tag;
import com.financial.feature.tag.repository.TagRepository;
import com.financial.feature.tag.service.contract.TagServiceContract;
import com.financial.feature.transaction.repository.TransactionTagRepository;
import com.financial.feature.user.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;


@RequestScoped
@RequiredArgsConstructor
public class TagService implements TagServiceContract {
    private final TagRepository tagRepository;
    private final TransactionTagRepository transactionTagRepository;
    private final AccountServiceContract accountService;

    @Override
    public List<TagDTO> list() {
        return tagRepository.listAll().stream()
                .map(peb -> new TagDTO(peb.id, peb.user.id, peb.name))
                .toList();
    }

    @Override
    public TagDTO get(Long id) {
        Tag instance = findByID(id);
        return new TagDTO(instance.id, instance.user.id, instance.name);
    }

    @Override
    @Transactional
    public Response create(TagDTO dto) {
        Tag instance = new Tag();
        instance.user = (User) User.findById(dto.userId());
        instance.name = dto.name();
        tagRepository.persist(instance);
        return Response.created(URI.create("/tags/" + instance.id)).build();
    }

    @Override
    @Transactional
    public TagDTO update(Long id, TagDTO dto) {
        Tag t = findByID(id);
        t.user = (User) User.findById(dto.userId());
        t.name = dto.name();
        return new TagDTO(t.id, t.user.id, t.name);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Tag instance = findByID(id);
        tagRepository.delete(instance);
    }

    @Override
    public Tag findByID(Long id) {
        return tagRepository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);
    }
}
