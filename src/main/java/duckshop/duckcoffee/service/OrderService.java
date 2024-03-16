package duckshop.duckcoffee.service;

import duckshop.duckcoffee.domain.Delivery;
import duckshop.duckcoffee.domain.Member;
import duckshop.duckcoffee.domain.Order;
import duckshop.duckcoffee.domain.OrderItem;
import duckshop.duckcoffee.domain.item.Item;
import duckshop.duckcoffee.repository.ItemRepository;
import duckshop.duckcoffee.repository.MemberRepository;
import duckshop.duckcoffee.repository.OrderRepository;
import duckshop.duckcoffee.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());   // 실제로는 배송지 정보를 따로 받아야함
        
        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);    //cascade 옵션 때문에 다른 곳에도 자동으로 persist 날려줌
        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAllByString(orderSearch);
        return orderRepository.findAll(orderSearch);
    }
}
