package com.BookMyShow.Notification_Service.repository;



import com.BookMyShow.Notification_Service.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface NotificationRepository
        extends JpaRepository<NotificationLog, UUID> {
}
