package com.booking.repositories.postgres.bookingrepocustom;

import com.booking.models.postgres.Booking;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class BookingRepositoriesCustomImpl implements BookingRepositoriesCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Booking> getBookingSelectedUser(Long userId) {
        Query query = entityManager.createNativeQuery("SELECT * " +
                "from booking " +
                "where user_id = ?", Booking.class);
        query.setParameter(1, userId);
        return query.getResultList();
    }
}
