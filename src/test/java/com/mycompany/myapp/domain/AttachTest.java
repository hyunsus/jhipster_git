package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttachTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attach.class);
        Attach attach1 = new Attach();
        attach1.setId(1L);
        Attach attach2 = new Attach();
        attach2.setId(attach1.getId());
        assertThat(attach1).isEqualTo(attach2);
        attach2.setId(2L);
        assertThat(attach1).isNotEqualTo(attach2);
        attach1.setId(null);
        assertThat(attach1).isNotEqualTo(attach2);
    }
}
