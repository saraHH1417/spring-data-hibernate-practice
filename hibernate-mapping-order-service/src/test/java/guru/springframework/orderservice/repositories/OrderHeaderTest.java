package guru.springframework.orderservice.repositories;

import guru.springframework.orderservice.domain.OrderHeader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderHeaderTest {

    @Test
    void testEquals() {
        OrderHeader oh1 = new OrderHeader();
        oh1.setId(1L);

        OrderHeader oh2 = new OrderHeader();
        oh2.setId(1L);

        Assertions.assertEquals(oh1, oh2);
    }

    @Test
    void  testNotEqualsId() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setId(1L);

        OrderHeader orderHeader2 = new OrderHeader();
        orderHeader2.setId(3L);

        Assertions.assertNotEquals(orderHeader, orderHeader2);
    }

    @Test
    void  testNotEqualsCustomer() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer("C1");

        OrderHeader orderHeader2 = new OrderHeader();
        orderHeader2.setCustomer("C2");

        Assertions.assertNotEquals(orderHeader, orderHeader2);
    }
}
