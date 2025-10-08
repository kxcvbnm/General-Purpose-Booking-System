package com.bookingsystem.booking.booking.data;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookingsystem.booking.booking.domain.entities.Booking;
import com.bookingsystem.booking.booking.domain.enums.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    @Query("""
            select count(b) > 0
            from Booking b
            where b.room.id = :roomId
                and b.status = :status
                and b.startTime < :end
                and b.endTime > :start
            """)
    boolean existsOverlapForRoom(
        @Param("roomId") Long roomId, 
        @Param("start") OffsetDateTime start, 
        @Param("end") OffsetDateTime end, 
        @Param("status") BookingStatus status);

    @Query("""
            select count(b) > 0
            from Booking b
            where b.user.id = :userId
                and b.status = :status
                and b.startTime < :end
                and b.endTime > :start
            """)
    boolean existsOverlapForUser(
        @Param("userId") Long userId, 
        @Param("start") OffsetDateTime start, 
        @Param("end") OffsetDateTime end, 
        @Param("status") BookingStatus status);
}
