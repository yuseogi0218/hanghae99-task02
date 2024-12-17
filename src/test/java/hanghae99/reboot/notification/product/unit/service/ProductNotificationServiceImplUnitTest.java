package hanghae99.reboot.notification.product.unit.service;

import hanghae99.reboot.notification.common.ServiceUnitTest;
import hanghae99.reboot.notification.common.eventQueue.EventProducer;
import hanghae99.reboot.notification.common.exception.CustomException;
import hanghae99.reboot.notification.product.domain.*;
import hanghae99.reboot.notification.product.domain.status.ReStockNotificationStatus;
import hanghae99.reboot.notification.product.dto.SendReStockNotificationDTO;
import hanghae99.reboot.notification.product.dto.SendReStockNotificationDTOBuilder;
import hanghae99.reboot.notification.product.exception.ProductErrorCode;
import hanghae99.reboot.notification.product.repository.ProductNotificationHistoryRepository;
import hanghae99.reboot.notification.product.repository.ProductUserNotificationHistoryRepository;
import hanghae99.reboot.notification.product.repository.ProductUserNotificationRepository;
import hanghae99.reboot.notification.product.service.ProductNotificationServiceImpl;
import hanghae99.reboot.notification.product.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ProductNotificationServiceImplUnitTest extends ServiceUnitTest {

    @InjectMocks
    private ProductNotificationServiceImpl productNotificationService;

    @Mock
    private EventProducer eventProducer;

    @Mock
    private ProductNotificationHistoryRepository productNotificationHistoryRepository;
    @Mock
    private ProductUserNotificationRepository productUserNotificationRepository;
    @Mock
    private ProductUserNotificationHistoryRepository productUserNotificationHistoryRepository;

    @Mock
    private ProductService productService;

    /**
     * 상품 재입고 알림 기록 저장 성공
     */
    @Test
    public void saveProductNotificationHistory_성공() {
        // given
        ProductNotificationHistory productNotificationHistory = ProductNotificationHistoryBuilder.build2();

        // when
        productNotificationService.saveProductNotificationHistory(productNotificationHistory);

        // then
        verify(productNotificationHistoryRepository, times(1)).save(productNotificationHistory);
    }

    /**
     * 상품별 재입고 알림 전송 기록 조회 성공
     */
    @Test
    public void getProductNotificationHistoryByIdAndReStockNotificationStatusIsNotCompleted_성공_존재_O() {
        // given
        Long productNotificationHistoryId = 4L;
        ProductNotificationHistory expecteProductNotificationHistory = ProductNotificationHistoryBuilder.build_CANCELED_BY_ERROR();

        // stub
        when(productNotificationHistoryRepository.findTopByIdAndReStockNotificationStatusIsInProgressOrCanceledByError(productNotificationHistoryId))
                .thenReturn(Optional.of(expecteProductNotificationHistory));

        // when
        Optional<ProductNotificationHistory> productNotificationHistoryOptional
                = productNotificationService.getProductNotificationHistoryByIdAndReStockNotificationStatusIsInProgressOrCanceledByError(productNotificationHistoryId);

        // then
        Assertions.assertThat(productNotificationHistoryOptional.isPresent()).isTrue();
        productNotificationHistoryOptional.ifPresent(
                actualProductNotificationHistory -> {
                    Assertions.assertThat(actualProductNotificationHistory).isEqualTo(expecteProductNotificationHistory);
                }
        );
    }

    /**
     * 상품별 재입고 알림 전송 기록 조회 성공 - 존재 X
     */
    @Test
    public void getProductNotificationHistoryByIdAndReStockNotificationStatusIsNotCompleted_성공_존재_X() {
        // given
        Long completedProductNotificationHistoryId = 1L;

        // stub
        when(productNotificationHistoryRepository.findTopByIdAndReStockNotificationStatusIsInProgressOrCanceledByError(completedProductNotificationHistoryId))
                .thenReturn(Optional.empty());

        // when
        Optional<ProductNotificationHistory> productNotificationHistoryOptional
                = productNotificationService.getProductNotificationHistoryByIdAndReStockNotificationStatusIsInProgressOrCanceledByError(completedProductNotificationHistoryId);

        // then
        Assertions.assertThat(productNotificationHistoryOptional.isEmpty()).isTrue();
    }

    /**
     * 재입고 알림 전송 기록 생성 성공
     */
    @Test
    public void createProductNotificationHistory_성공() {
        // given
        Long productId = 1L;
        Product product = ProductBuilder.build1();

        // stub
        when(productService.getProductById(productId)).thenReturn(product);

        // when
        productNotificationService.createProductNotificationHistory(productId);

        // then
        verify(productNotificationHistoryRepository, times(1)).save(any());
    }

    /**
     * 상품 DB Id로 상품 재입고 알림 전송 이벤트 발행 성공 - 전송해야 할 상품 재입고 알림 기록 존재 O
     */
    @Test
    public void publishReStockNotificationEvent_성공_존재_O() {
        // given
        Long productId = 3L;
        ProductNotificationHistory productNotificationHistory = ProductNotificationHistoryBuilder.build_CANCELED_BY_ERROR();
        Assertions.assertThat(productNotificationHistory.getReStockNotificationStatus()).isEqualTo(ReStockNotificationStatus.CANCELED_BY_ERROR);

        // stub
        when(productNotificationHistoryRepository.findTopByProductIdAndReStockNotificationStatusIsInProgressOrCanceledByError(productId))
                .thenReturn(Optional.of(productNotificationHistory));

        // when
        productNotificationService.publishReStockNotificationEvent(productId);

        // then
        Assertions.assertThat(productNotificationHistory.getReStockNotificationStatus()).isEqualTo(ReStockNotificationStatus.IN_PROGRESS);
        verify(eventProducer, times(1)).publishEvent(any());
    }

    /**
     * 상품 DB Id로 상품 재입고 알림 전송 이벤트 발행 성공 - 전송해야 할 상품 재입고 알림 기록 존재 X
     */
    @Test
    public void publishReStockNotificationEvent_성공_존재_X() {
        // given
        Long completedProductId = 1L;

        // stub
        when(productNotificationHistoryRepository.findTopByProductIdAndReStockNotificationStatusIsInProgressOrCanceledByError(completedProductId))
                .thenReturn(Optional.empty());

        // when
        productNotificationService.publishReStockNotificationEvent(completedProductId);

        // then
        verify(eventProducer, never()).publishEvent(any());
    }

    /**
     * 상품별 재입고 알림 대상 사용자 조회 성공
     */
    @Test
    public void getUserIdOfProductUserNotificationByProductId_성공() {
        // given
        Long productId = 1L;
        Long lastSentUserId = 10L;
        Integer size = 5;

        List<Long> expectedIds = List.of(15L, 14L, 13L, 13L, 11L);
        Page<Long> expectedPage = new PageImpl<>(expectedIds, PageRequest.of(0, size), 20);

        // stub
        when(productUserNotificationRepository.findUserIdsByProductIdAfterLastSentUserId(productId, lastSentUserId, PageRequest.of(0, size)))
                .thenReturn(expectedPage);

        // when
        Page<Long> actualPage = productNotificationService.getUserIdOfProductUserNotificationByProductId(productId, lastSentUserId, size);

        // then
        Assertions.assertThat(actualPage.getContent()).isEqualTo(expectedIds);
        Assertions.assertThat(actualPage.getSize()).isEqualTo(size);
        Assertions.assertThat(actualPage.isLast()).isFalse();
    }


    /**
     * 재입고 알림 전송 기능 성공
     */
    @Test
    public void sendReStockNotification_성공() {
        // given
        SendReStockNotificationDTO dto = SendReStockNotificationDTOBuilder.build();
        Product product = ProductBuilder.build1();

        // stub
        when(productService.getProductById(dto.productId())).thenReturn(product);

        // when
        ProductUserNotificationHistory productUserNotificationHistory = productNotificationService.sendReStockNotification(dto);

        // then
        Assertions.assertThat(productUserNotificationHistory.getProduct()).isEqualTo(product);
        Assertions.assertThat(productUserNotificationHistory.getUserId()).isEqualTo(dto.userId());
        Assertions.assertThat(productUserNotificationHistory.getReStockRound()).isEqualTo(dto.reStockRound());
    }

    /**
     * 재입고 알림 전송 기능 실패
     * - 실패 사유 : 재고 소진으로 인한 중단
     */
    @Test
    public void sendReStockNotification_실패_재고_소진으로_인한() {
        // given
        SendReStockNotificationDTO dto = SendReStockNotificationDTOBuilder.build();
        Product product = ProductBuilder.build_OUT_OF_STOCK();

        // stub
        when(productService.getProductById(dto.productId())).thenReturn(product);

        // when & then
        Assertions.assertThatThrownBy(() -> productNotificationService.sendReStockNotification(dto))
                .isInstanceOf(CustomException.class)
                .hasMessage(ProductErrorCode.OUT_OF_STOCK.getMessage());
    }

    /**
     * 사용자별 재입고 알림 전송 기록 저장 성공
     */
    @Test
    public void saveAllProductUserNotificationHistories_성공() {
        // given
        List<ProductUserNotificationHistory> productUserNotificationHistories = new ArrayList<>();

        // when
        productNotificationService.saveAllProductUserNotificationHistories(productUserNotificationHistories);

        // then
        verify(productUserNotificationHistoryRepository, times(1)).saveAll(productUserNotificationHistories);
    }
}
