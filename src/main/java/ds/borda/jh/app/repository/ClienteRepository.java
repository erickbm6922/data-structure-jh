package ds.borda.jh.app.repository;

import ds.borda.jh.app.domain.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Cliente entity.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query(value = "select distinct cliente from Cliente cliente left join fetch cliente.ventas",
        countQuery = "select count(distinct cliente) from Cliente cliente")
    Page<Cliente> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct cliente from Cliente cliente left join fetch cliente.ventas")
    List<Cliente> findAllWithEagerRelationships();

    @Query("select cliente from Cliente cliente left join fetch cliente.ventas where cliente.id =:id")
    Optional<Cliente> findOneWithEagerRelationships(@Param("id") Long id);

}
