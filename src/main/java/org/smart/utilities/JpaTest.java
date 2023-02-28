package org.smart.utilities;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTest extends JpaRepository<TestEntity, Integer> {
}
