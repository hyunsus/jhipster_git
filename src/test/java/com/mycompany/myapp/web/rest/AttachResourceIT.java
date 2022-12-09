package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Attach;
import com.mycompany.myapp.repository.AttachRepository;
import com.mycompany.myapp.service.dto.AttachDTO;
import com.mycompany.myapp.service.mapper.AttachMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AttachResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttachResourceIT {

    private static final Integer DEFAULT_ORG = 1;
    private static final Integer UPDATED_ORG = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORIG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORIG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXT = "AAAAAAAAAA";
    private static final String UPDATED_EXT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final Long DEFAULT_FILE_SIZE = 1L;
    private static final Long UPDATED_FILE_SIZE = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MODIFIED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/attaches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttachRepository attachRepository;

    @Autowired
    private AttachMapper attachMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttachMockMvc;

    private Attach attach;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attach createEntity(EntityManager em) {
        Attach attach = new Attach()
            .org(DEFAULT_ORG)
            .name(DEFAULT_NAME)
            .origName(DEFAULT_ORIG_NAME)
            .ext(DEFAULT_EXT)
            .contentType(DEFAULT_CONTENT_TYPE)
            .path(DEFAULT_PATH)
            .fileSize(DEFAULT_FILE_SIZE)
            .createdAt(DEFAULT_CREATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .modifiedAt(DEFAULT_MODIFIED_AT)
            .modifiedBy(DEFAULT_MODIFIED_BY);
        return attach;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attach createUpdatedEntity(EntityManager em) {
        Attach attach = new Attach()
            .org(UPDATED_ORG)
            .name(UPDATED_NAME)
            .origName(UPDATED_ORIG_NAME)
            .ext(UPDATED_EXT)
            .contentType(UPDATED_CONTENT_TYPE)
            .path(UPDATED_PATH)
            .fileSize(UPDATED_FILE_SIZE)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY);
        return attach;
    }

    @BeforeEach
    public void initTest() {
        attach = createEntity(em);
    }

    @Test
    @Transactional
    void createAttach() throws Exception {
        int databaseSizeBeforeCreate = attachRepository.findAll().size();
        // Create the Attach
        AttachDTO attachDTO = attachMapper.toDto(attach);
        restAttachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attachDTO)))
            .andExpect(status().isCreated());

        // Validate the Attach in the database
        List<Attach> attachList = attachRepository.findAll();
        assertThat(attachList).hasSize(databaseSizeBeforeCreate + 1);
        Attach testAttach = attachList.get(attachList.size() - 1);
        assertThat(testAttach.getOrg()).isEqualTo(DEFAULT_ORG);
        assertThat(testAttach.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAttach.getOrigName()).isEqualTo(DEFAULT_ORIG_NAME);
        assertThat(testAttach.getExt()).isEqualTo(DEFAULT_EXT);
        assertThat(testAttach.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testAttach.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testAttach.getFileSize()).isEqualTo(DEFAULT_FILE_SIZE);
        assertThat(testAttach.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAttach.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAttach.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
        assertThat(testAttach.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createAttachWithExistingId() throws Exception {
        // Create the Attach with an existing ID
        attach.setId(1L);
        AttachDTO attachDTO = attachMapper.toDto(attach);

        int databaseSizeBeforeCreate = attachRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attachDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attach in the database
        List<Attach> attachList = attachRepository.findAll();
        assertThat(attachList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachRepository.findAll().size();
        // set the field null
        attach.setCreatedAt(null);

        // Create the Attach, which fails.
        AttachDTO attachDTO = attachMapper.toDto(attach);

        restAttachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attachDTO)))
            .andExpect(status().isBadRequest());

        List<Attach> attachList = attachRepository.findAll();
        assertThat(attachList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachRepository.findAll().size();
        // set the field null
        attach.setCreatedBy(null);

        // Create the Attach, which fails.
        AttachDTO attachDTO = attachMapper.toDto(attach);

        restAttachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attachDTO)))
            .andExpect(status().isBadRequest());

        List<Attach> attachList = attachRepository.findAll();
        assertThat(attachList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAttaches() throws Exception {
        // Initialize the database
        attachRepository.saveAndFlush(attach);

        // Get all the attachList
        restAttachMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attach.getId().intValue())))
            .andExpect(jsonPath("$.[*].org").value(hasItem(DEFAULT_ORG)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].origName").value(hasItem(DEFAULT_ORIG_NAME)))
            .andExpect(jsonPath("$.[*].ext").value(hasItem(DEFAULT_EXT)))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].fileSize").value(hasItem(DEFAULT_FILE_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(sameInstant(DEFAULT_MODIFIED_AT))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getAttach() throws Exception {
        // Initialize the database
        attachRepository.saveAndFlush(attach);

        // Get the attach
        restAttachMockMvc
            .perform(get(ENTITY_API_URL_ID, attach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attach.getId().intValue()))
            .andExpect(jsonPath("$.org").value(DEFAULT_ORG))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.origName").value(DEFAULT_ORIG_NAME))
            .andExpect(jsonPath("$.ext").value(DEFAULT_EXT))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH))
            .andExpect(jsonPath("$.fileSize").value(DEFAULT_FILE_SIZE.intValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.modifiedAt").value(sameInstant(DEFAULT_MODIFIED_AT)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getNonExistingAttach() throws Exception {
        // Get the attach
        restAttachMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAttach() throws Exception {
        // Initialize the database
        attachRepository.saveAndFlush(attach);

        int databaseSizeBeforeUpdate = attachRepository.findAll().size();

        // Update the attach
        Attach updatedAttach = attachRepository.findById(attach.getId()).get();
        // Disconnect from session so that the updates on updatedAttach are not directly saved in db
        em.detach(updatedAttach);
        updatedAttach
            .org(UPDATED_ORG)
            .name(UPDATED_NAME)
            .origName(UPDATED_ORIG_NAME)
            .ext(UPDATED_EXT)
            .contentType(UPDATED_CONTENT_TYPE)
            .path(UPDATED_PATH)
            .fileSize(UPDATED_FILE_SIZE)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY);
        AttachDTO attachDTO = attachMapper.toDto(updatedAttach);

        restAttachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attachDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attachDTO))
            )
            .andExpect(status().isOk());

        // Validate the Attach in the database
        List<Attach> attachList = attachRepository.findAll();
        assertThat(attachList).hasSize(databaseSizeBeforeUpdate);
        Attach testAttach = attachList.get(attachList.size() - 1);
        assertThat(testAttach.getOrg()).isEqualTo(UPDATED_ORG);
        assertThat(testAttach.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAttach.getOrigName()).isEqualTo(UPDATED_ORIG_NAME);
        assertThat(testAttach.getExt()).isEqualTo(UPDATED_EXT);
        assertThat(testAttach.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testAttach.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testAttach.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testAttach.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAttach.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttach.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
        assertThat(testAttach.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingAttach() throws Exception {
        int databaseSizeBeforeUpdate = attachRepository.findAll().size();
        attach.setId(count.incrementAndGet());

        // Create the Attach
        AttachDTO attachDTO = attachMapper.toDto(attach);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attachDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attachDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attach in the database
        List<Attach> attachList = attachRepository.findAll();
        assertThat(attachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttach() throws Exception {
        int databaseSizeBeforeUpdate = attachRepository.findAll().size();
        attach.setId(count.incrementAndGet());

        // Create the Attach
        AttachDTO attachDTO = attachMapper.toDto(attach);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attachDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attach in the database
        List<Attach> attachList = attachRepository.findAll();
        assertThat(attachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttach() throws Exception {
        int databaseSizeBeforeUpdate = attachRepository.findAll().size();
        attach.setId(count.incrementAndGet());

        // Create the Attach
        AttachDTO attachDTO = attachMapper.toDto(attach);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attachDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attach in the database
        List<Attach> attachList = attachRepository.findAll();
        assertThat(attachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttachWithPatch() throws Exception {
        // Initialize the database
        attachRepository.saveAndFlush(attach);

        int databaseSizeBeforeUpdate = attachRepository.findAll().size();

        // Update the attach using partial update
        Attach partialUpdatedAttach = new Attach();
        partialUpdatedAttach.setId(attach.getId());

        partialUpdatedAttach
            .org(UPDATED_ORG)
            .ext(UPDATED_EXT)
            .contentType(UPDATED_CONTENT_TYPE)
            .path(UPDATED_PATH)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restAttachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttach))
            )
            .andExpect(status().isOk());

        // Validate the Attach in the database
        List<Attach> attachList = attachRepository.findAll();
        assertThat(attachList).hasSize(databaseSizeBeforeUpdate);
        Attach testAttach = attachList.get(attachList.size() - 1);
        assertThat(testAttach.getOrg()).isEqualTo(UPDATED_ORG);
        assertThat(testAttach.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAttach.getOrigName()).isEqualTo(DEFAULT_ORIG_NAME);
        assertThat(testAttach.getExt()).isEqualTo(UPDATED_EXT);
        assertThat(testAttach.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testAttach.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testAttach.getFileSize()).isEqualTo(DEFAULT_FILE_SIZE);
        assertThat(testAttach.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAttach.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttach.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
        assertThat(testAttach.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateAttachWithPatch() throws Exception {
        // Initialize the database
        attachRepository.saveAndFlush(attach);

        int databaseSizeBeforeUpdate = attachRepository.findAll().size();

        // Update the attach using partial update
        Attach partialUpdatedAttach = new Attach();
        partialUpdatedAttach.setId(attach.getId());

        partialUpdatedAttach
            .org(UPDATED_ORG)
            .name(UPDATED_NAME)
            .origName(UPDATED_ORIG_NAME)
            .ext(UPDATED_EXT)
            .contentType(UPDATED_CONTENT_TYPE)
            .path(UPDATED_PATH)
            .fileSize(UPDATED_FILE_SIZE)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restAttachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttach))
            )
            .andExpect(status().isOk());

        // Validate the Attach in the database
        List<Attach> attachList = attachRepository.findAll();
        assertThat(attachList).hasSize(databaseSizeBeforeUpdate);
        Attach testAttach = attachList.get(attachList.size() - 1);
        assertThat(testAttach.getOrg()).isEqualTo(UPDATED_ORG);
        assertThat(testAttach.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAttach.getOrigName()).isEqualTo(UPDATED_ORIG_NAME);
        assertThat(testAttach.getExt()).isEqualTo(UPDATED_EXT);
        assertThat(testAttach.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testAttach.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testAttach.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testAttach.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAttach.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttach.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
        assertThat(testAttach.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingAttach() throws Exception {
        int databaseSizeBeforeUpdate = attachRepository.findAll().size();
        attach.setId(count.incrementAndGet());

        // Create the Attach
        AttachDTO attachDTO = attachMapper.toDto(attach);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attachDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attachDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attach in the database
        List<Attach> attachList = attachRepository.findAll();
        assertThat(attachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttach() throws Exception {
        int databaseSizeBeforeUpdate = attachRepository.findAll().size();
        attach.setId(count.incrementAndGet());

        // Create the Attach
        AttachDTO attachDTO = attachMapper.toDto(attach);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attachDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attach in the database
        List<Attach> attachList = attachRepository.findAll();
        assertThat(attachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttach() throws Exception {
        int databaseSizeBeforeUpdate = attachRepository.findAll().size();
        attach.setId(count.incrementAndGet());

        // Create the Attach
        AttachDTO attachDTO = attachMapper.toDto(attach);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(attachDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attach in the database
        List<Attach> attachList = attachRepository.findAll();
        assertThat(attachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttach() throws Exception {
        // Initialize the database
        attachRepository.saveAndFlush(attach);

        int databaseSizeBeforeDelete = attachRepository.findAll().size();

        // Delete the attach
        restAttachMockMvc
            .perform(delete(ENTITY_API_URL_ID, attach.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attach> attachList = attachRepository.findAll();
        assertThat(attachList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
