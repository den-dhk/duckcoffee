package duckshop.duckcoffee.api;

import duckshop.duckcoffee.domain.*;
import duckshop.duckcoffee.domain.item.Item;
import duckshop.duckcoffee.repository.OrderSearch;
import duckshop.duckcoffee.service.ItemService;
import duckshop.duckcoffee.service.MemberService;
import duckshop.duckcoffee.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    // members, items 동시에 어떤 자료형으로 받을지 고민해보기
    @GetMapping("/order")
    public Result createForm() {

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        HashMap map = new HashMap<String, List>();
        map.put("members", members);
        map.put("items", items);
        return new Result(map);
    }

    @GetMapping("/orders")
    public Result orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch) {
        List<Order> orders = orderService.findOrders(orderSearch);

        return new Result(orders);
    }

    @PostMapping("/orders/{orderId}/cancel")
    public void cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
    }

    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;
        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(toList());
        }
    }
    @Data
    static class OrderItemDto {
        private String itemName;//상품 명
        private int orderPrice; //주문 가격
        private int count; //주문 수량
        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
