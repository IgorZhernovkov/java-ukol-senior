package com.etnetera.hr.repository;

import com.etnetera.hr.data.dto.WithId;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/** Generic interface for basic CRUD and advanced search operations on Entity */
@NoRepositoryBean
public interface CrudAndSearchRepository<TEntity extends WithId<TId>, TId> extends CrudRepository<TEntity, TId>,
        JpaSpecificationExecutor<TEntity> {
}
