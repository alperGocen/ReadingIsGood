package com.rig.repository;

import com.rig.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Order findByRowId(UUID orderId);

    List<Order> findByCustomerRowId(UUID customerId);

    @Query(value = "FROM Order o where o.createDate BETWEEN :startDate AND :endDate")
    List<Order> findByStartAndEndDate(@Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate);
}
