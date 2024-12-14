package hanghae99.reboot.notification.product.unit.api;

import hanghae99.reboot.notification.common.APIUnitTest;
import hanghae99.reboot.notification.common.exception.CommonErrorCode;
import hanghae99.reboot.notification.product.api.NotificationAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationAPI.class)
public class NotificationAPIUnitTest extends APIUnitTest {

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mvc = buildMockMvc(context);
    }

    /**
     * 재입고 알림 전송 기능 성공
     */
    @Test
    public void 재입고_알림_전송_기능_성공() throws Exception {
        // given
        String productId = "1";

        // when
        ResultActions resultActions = requestSendReStockNotification(productId);

        // then
        resultActions.andExpect(status().isOk());
    }

    /**
     * 재입고 알림 전송 기능 실패
     * - 실패 사유 : 지원하지 않는 HTTP Method
     */
    @Test
    public void 재입고_알림_전송_기능_실패_지원하지않는_HTTP_Method() throws Exception {
        // given
        String productId = "1";

        // when
        ResultActions resultActions = requestSendReStockNotificationWithGetMethod(productId);

        // then
        assertErrorWithMessage(CommonErrorCode.METHOD_NOT_ALLOWED, resultActions, "productId");
    }

    /**
     * 재입고 알림 전송 기능 실패
     * - 실패 사유 : productId 파라미터 타입
     */
    @Test
    public void 재입고_알림_전송_기능_실패_productId_파라미터_타입() throws Exception {
        // given
        String invalidProductId = "invalid-product-id";

        // when
        ResultActions resultActions = requestSendReStockNotification(invalidProductId);

        // then
        assertErrorWithMessage(CommonErrorCode.MISMATCH_PARAMETER_TYPE, resultActions, "productId");
    }

    private ResultActions requestSendReStockNotification(String productId) throws Exception {
        return mvc.perform(post("/products/{productId}/notifications/re-stock", productId))
                .andDo(print());
    }

    private ResultActions requestSendReStockNotificationWithGetMethod(String productId) throws Exception {
        return mvc.perform(get("/products/{productId}/notifications/re-stock", productId))
                .andDo(print());
    }
}
