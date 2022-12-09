package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttachGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachGroup.class);
        AttachGroup attachGroup1 = new AttachGroup();
        attachGroup1.setId(1L);
        AttachGroup attachGroup2 = new AttachGroup();
        attachGroup2.setId(attachGroup1.getId());
        assertThat(attachGroup1).isEqualTo(attachGroup2);
        attachGroup2.setId(2L);
        assertThat(attachGroup1).isNotEqualTo(attachGroup2);
        attachGroup1.setId(null);
        assertThat(attachGroup1).isNotEqualTo(attachGroup2);
    }
}
