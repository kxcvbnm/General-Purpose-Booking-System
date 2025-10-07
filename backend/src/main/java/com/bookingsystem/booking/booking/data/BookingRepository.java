package com.bookingsystem.booking.booking.data;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
    boolean existsOverlapForRoom(Long roomId, OffsetDateTime start, OffsetDateTime end, BookingStatus status);

    @Query("""
            select count(b) > 0
            from Booking b
            where b.user.id = :userId
                and b.status = :status
                and b.startTime < :end
                and b.endTime > :start
            """)
    boolean existsOverlapForUser(Long userId, OffsetDateTime start, OffsetDateTime end, BookingStatus status);
}
