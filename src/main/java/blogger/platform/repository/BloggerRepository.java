package blogger.platform.repository;

import blogger.platform.model.entity.Blogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloggerRepository extends JpaRepository<Blogger, Long> {
    @Query("select b from Blogger b where lower(b.title) like %:term% or lower(b.content) like %:term% or lower(b.category) like %:term%")
    List<Blogger> findAllByTerm(@Param("term") String term);
}

