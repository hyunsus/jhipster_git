package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AttachGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AttachGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachGroupRepository extends JpaRepository<AttachGroup, Long> {}
