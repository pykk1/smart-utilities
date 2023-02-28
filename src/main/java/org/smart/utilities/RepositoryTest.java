package org.smart.utilities;

import org.springframework.stereotype.Repository;

@Repository
public class RepositoryTest {

    private final JpaTest jpaTest;

    public RepositoryTest(JpaTest jpaTest) {
        this.jpaTest = jpaTest;
    }

    public void save(TestDTO dto) {
        var entity = new TestEntity();
        entity.setStringuMeu(dto.getStringuMeu());
        entity.setId(dto.getId());

        jpaTest.save(entity);
    }
}
