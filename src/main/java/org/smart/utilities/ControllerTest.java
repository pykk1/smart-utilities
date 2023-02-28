package org.smart.utilities;

import org.springframework.web.bind.annotation.*;

@RestController
public class ControllerTest {

    private final RepositoryTest repositoryTest;

    public ControllerTest(RepositoryTest repositoryTest) {
        this.repositoryTest = repositoryTest;
    }

    @PostMapping("/test")
    public void test(@RequestBody TestDTO dto) {
        repositoryTest.save(dto);
    }

}
