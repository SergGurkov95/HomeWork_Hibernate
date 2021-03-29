package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "orders_map")
public class OrderMap implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return order.getOrderId() + "\t" + order.getDate() + "\t" + order.getUser().getFirstName() +
            "\t" + order.getUser().getLastName() + "\t" + product.getCategory().getCategoryName() +
            "\t" + product.getProductName() + "\t" + quantity +
            "\t" + product.getPrice().multiply(BigDecimal.valueOf(quantity)) +
            "\t" + order.getUser().getAddress() + "\t" + order.getOrderStatus().getStatusName();
    }

}
