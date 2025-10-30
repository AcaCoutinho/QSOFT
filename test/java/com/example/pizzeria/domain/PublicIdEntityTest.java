package com.example.pizzeria.domain;

import com.example.pizzeria.domain.entity.PublicIdEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PublicIdEntityTest {

    @Test
    void shouldStorePublicIdentifier() {
        UUID id = UUID.randomUUID();
        SampleEntity entity = new SampleEntity();
        entity.setPublicId(id);

        assertThat(entity.getPublicId()).isEqualTo(id);
    }

    private static class SampleEntity extends PublicIdEntity {
    }
}
