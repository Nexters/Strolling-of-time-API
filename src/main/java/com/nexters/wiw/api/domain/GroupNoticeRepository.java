package com.nexters.wiw.api.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupNoticeRepository extends JpaRepository<GroupNotice, Long> {
    Optional<List<GroupNotice>> findByGroup(Group group);

    Optional<List<GroupNotice>> findByUser(User user);

    Optional<List<GroupNotice>> findByTitleContaining(String keyword);
}
