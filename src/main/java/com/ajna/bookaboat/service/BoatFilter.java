package com.ajna.bookaboat.service;

import com.ajna.bookaboat.entity.Boat;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

public class BoatFilter implements Specification<Boat>{

    private String name;
    private String boatType;
    private int priceFrom;
    private int priceTo;
    private Boolean isCertificateNeed;

    public BoatFilter() {
    }

    public BoatFilter(String name, String boatType, int priceFrom, int priceTo, Boolean isCertificateNeed) {
        this.name = name;
        this.boatType = boatType;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.isCertificateNeed = isCertificateNeed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoatType() {
        return boatType;
    }

    public void setBoatType(String boatType) {
        this.boatType = boatType;
    }

    public int getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(int priceFrom) {
        this.priceFrom = priceFrom;
    }

    public int getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(int priceTo) {
        this.priceTo = priceTo;
    }

    public Boolean getCertificateNeed() {
        return isCertificateNeed;
    }

    public void setCertificateNeed(Boolean certificateNeed) {
        isCertificateNeed = certificateNeed;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<Boat> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if(StringUtils.isNotBlank(name)){
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }

        if (StringUtils.isNotBlank(boatType)){
            predicates.add(criteriaBuilder.equal(root.get("boatType").get("name"), boatType));
        }

        if(priceFrom != 0){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceFrom));
        }

        if(priceTo != 0){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceTo));
        }

        if(isCertificateNeed != null){
            predicates.add(criteriaBuilder.equal(root.get("isCertificateNeed"), isCertificateNeed));
        }

        return predicates.size() <= 0 ? null : criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

    }

}
