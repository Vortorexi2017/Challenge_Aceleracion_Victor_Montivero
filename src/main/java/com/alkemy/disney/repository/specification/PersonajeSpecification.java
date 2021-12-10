package com.alkemy.disney.repository.specification;

import com.alkemy.disney.dto.PersonajeFiltersDTO;
import com.alkemy.disney.entities.PeliculaEntity;
import com.alkemy.disney.entities.PersonajeEntity;
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
public class PersonajeSpecification {


    public Specification<PersonajeEntity> getByFilters(PersonajeFiltersDTO filtersDTO){
        return (root, query, criteriaBuilder) ->{

            List<Predicate> predicates = new ArrayList<>();

            //Parametro: name

            if (StringUtils.hasLength(filtersDTO.getName())){
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("denominacion")),
                                "%" + filtersDTO.getName().toLowerCase() + "%"
                        )
                );
            }

            //Parametro: age

            if (!(filtersDTO.getAge() == null)){
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("denominacion")),
                                "%" + filtersDTO.getAge() + "%"
                        )
                );
            }



            if(!CollectionUtils.isEmpty(filtersDTO.getMovies())){
                Join<PeliculaEntity, PersonajeEntity> join = root.join("peliculas", JoinType.INNER);
                Expression<String> moviesId = join.get("id");
                predicates.add(moviesId.in(filtersDTO.getMovies()));
            }

            //REMOVE DUPLICATES
            query.distinct(true);

            //ORDENAR

            String orderByField = "denominacion";
            query.orderBy(
                    filtersDTO.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField)) :
                            criteriaBuilder.desc(root.get(orderByField))
            );



            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };

    }
}
