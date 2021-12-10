package com.alkemy.disney.repository.specification;

import com.alkemy.disney.dto.PeliculaFiltersDTO;
import com.alkemy.disney.entities.PeliculaEntity;
import com.alkemy.disney.entities.PersonajeEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class PeliculaSpecification {


    public Specification<PeliculaEntity> getByFilters(PeliculaFiltersDTO filtersDTO){
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            //Parametro String : name

            if (StringUtils.hasLength(filtersDTO.getName())){
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("denominacion")),
                                "%" + filtersDTO.getName().toLowerCase() + "%"
                        )
                );
            }

            //Parametro Set<String> : genre

            if (!CollectionUtils.isEmpty(filtersDTO.getGenre())){
                Join<PersonajeEntity, PeliculaEntity> join = root.join("personajes", JoinType.INNER);
                Expression<String> generoId = join.get("id");
                predicates.add(generoId.in(filtersDTO.getGenre()));
            }

            //REMOVE DUPLICATES
            query.distinct(true);

            String orderByField = "fechaCreacion";
            query.orderBy(
                    filtersDTO.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField)) :
                            criteriaBuilder.desc(root.get(orderByField))

            );


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));


        };

    }
}
