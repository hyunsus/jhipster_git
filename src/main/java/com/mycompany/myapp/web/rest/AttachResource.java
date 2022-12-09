package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.AttachRepository;
import com.mycompany.myapp.service.AttachService;
import com.mycompany.myapp.service.dto.AttachDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Attach}.
 */
@RestController
@RequestMapping("/api")
public class AttachResource {

    private final Logger log = LoggerFactory.getLogger(AttachResource.class);

    private static final String ENTITY_NAME = "attach";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachService attachService;

    private final AttachRepository attachRepository;

    public AttachResource(AttachService attachService, AttachRepository attachRepository) {
        this.attachService = attachService;
        this.attachRepository = attachRepository;
    }

    /**
     * {@code POST  /attaches} : Create a new attach.
     *
     * @param attachDTO the attachDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachDTO, or with status {@code 400 (Bad Request)} if the attach has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attaches")
    public ResponseEntity<AttachDTO> createAttach(@Valid @RequestBody AttachDTO attachDTO) throws URISyntaxException {
        log.debug("REST request to save Attach : {}", attachDTO);
        if (attachDTO.getId() != null) {
            throw new BadRequestAlertException("A new attach cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttachDTO result = attachService.save(attachDTO);
        return ResponseEntity
            .created(new URI("/api/attaches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attaches/:id} : Updates an existing attach.
     *
     * @param id the id of the attachDTO to save.
     * @param attachDTO the attachDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachDTO,
     * or with status {@code 400 (Bad Request)} if the attachDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attaches/{id}")
    public ResponseEntity<AttachDTO> updateAttach(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AttachDTO attachDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Attach : {}, {}", id, attachDTO);
        if (attachDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttachDTO result = attachService.update(attachDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attachDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attaches/:id} : Partial updates given fields of an existing attach, field will ignore if it is null
     *
     * @param id the id of the attachDTO to save.
     * @param attachDTO the attachDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachDTO,
     * or with status {@code 400 (Bad Request)} if the attachDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attachDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attachDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attaches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttachDTO> partialUpdateAttach(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AttachDTO attachDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Attach partially : {}, {}", id, attachDTO);
        if (attachDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttachDTO> result = attachService.partialUpdate(attachDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attachDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attaches} : get all the attaches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attaches in body.
     */
    @GetMapping("/attaches")
    public ResponseEntity<List<AttachDTO>> getAllAttaches(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Attaches");
        Page<AttachDTO> page = attachService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attaches/:id} : get the "id" attach.
     *
     * @param id the id of the attachDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attaches/{id}")
    public ResponseEntity<AttachDTO> getAttach(@PathVariable Long id) {
        log.debug("REST request to get Attach : {}", id);
        Optional<AttachDTO> attachDTO = attachService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attachDTO);
    }

    /**
     * {@code DELETE  /attaches/:id} : delete the "id" attach.
     *
     * @param id the id of the attachDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attaches/{id}")
    public ResponseEntity<Void> deleteAttach(@PathVariable Long id) {
        log.debug("REST request to delete Attach : {}", id);
        attachService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
