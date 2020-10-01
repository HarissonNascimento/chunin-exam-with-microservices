package br.com.harisson.core.repository;

import br.com.harisson.core.model.ApplicationUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
}
