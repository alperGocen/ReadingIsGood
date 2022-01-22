package com.rig.repository;

import com.rig.model.dto.StatisticsDto;
import com.rig.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Customer findByRowId(UUID customerId);

    @Query(value = "SELECT NEW com.rig.model.dto.StatisticsDto(month(ord.createDate) as month, " +
            "year(ord.createDate) as year, COUNT(ord.rowId), SUM(ordItem.numberInBasket), SUM(ord.price)) " +
            "FROM Order ord " +
            "JOIN ord.customer cst " +
            "JOIN ord.orderItems as ordItem " +
            "WHERE cst = :customer GROUP BY month, year")
    List<StatisticsDto> getCustomerStatistics(@Param("customer") Customer customer);
}
