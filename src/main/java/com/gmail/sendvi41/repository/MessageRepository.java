package com.gmail.sendvi41.repository;

import com.gmail.sendvi41.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
    @Query(value = "SELECT * from messages m "
            + "INNER JOIN users u on u.user_id=m.user_id "
            + "WHERE u.user_name=:name "
            + "ORDER BY m.creation_date DESC "
            + "LIMIT :amount", nativeQuery = true)
    List<MessageEntity> findByNameWithLimit(@Param("name")String name, @Param("amount")Integer amount);
}
