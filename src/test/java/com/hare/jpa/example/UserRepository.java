package com.hare.jpa.example;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ClassName: UserRepository
 * Package: com.hare.jpa.example
 * Description:
 *
 * @Author: wang cheng
 * @Create: 2023/3/24
 * @Version: v1.0
 **/
public interface UserRepository extends JpaRepository<UserDO, Long> {
}
