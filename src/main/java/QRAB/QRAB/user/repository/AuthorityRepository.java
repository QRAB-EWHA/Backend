package QRAB.QRAB.user.repository;

import QRAB.QRAB.user.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}