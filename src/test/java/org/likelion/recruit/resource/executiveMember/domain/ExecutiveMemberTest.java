package org.likelion.recruit.resource.executiveMember.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.likelion.recruit.resource.common.domain.Part;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ExecutiveMemberTest {

    @Test
    public void createExecutiveMember() {
        String name = "김지오";
        String imageUrl = "https://image_url";
        Part part = Part.BACKEND;
        Position position = Position.PRESIDENT;

        ExecutiveMember executiveMember = ExecutiveMember.create(name, imageUrl, part, position);

        assertThat(executiveMember.getName()).isEqualTo(name);
        assertThat(executiveMember.getImageUrl()).isEqualTo(imageUrl);
        assertThat(executiveMember.getPart()).isEqualTo(part);
        assertThat(executiveMember.getPosition()).isEqualTo(position);
    }

}