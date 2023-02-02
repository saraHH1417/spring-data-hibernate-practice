package guru.springframework.orderservice.domain;

import jakarta.persistence.Entity;


public enum OrderStatus {
    NEW, IN_PROCESS, COMPLETE
}
