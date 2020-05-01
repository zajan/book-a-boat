package com.ajna.bookaboat.service;

import com.ajna.bookaboat.entity.Booking;
import com.ajna.bookaboat.exception.BookingException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;

public class BookingValidator {

    public static boolean isCorrect(Booking booking){
        return !booking.getDateEnd().isBefore(booking.getDateStart());
    }

    public static boolean isInFuture(Booking booking){
        LocalDate now = LocalDate.now();
        return booking.getDateStart().isAfter(now);
    }


    public static Specification<Booking> bookingsAtThisTime(Booking booking){
        return new Specification<Booking>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> predicates = new ArrayList<>();

                predicates.add(criteriaBuilder.equal(root.get("boat").get("id"), booking.getBoat().getId()));
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.<LocalDate>get("dateEnd"), criteriaBuilder.literal(booking.getDateStart())));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.<LocalDate>get("dateStart"), criteriaBuilder.literal(booking.getDateEnd())));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
