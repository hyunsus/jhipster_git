package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardMapperTest {

    private BoardMapper boardMapper;

    @BeforeEach
    public void setUp() {
        boardMapper = new BoardMapperImpl();
    }
}
