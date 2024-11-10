package store;

import static camp.nextstep.edu.missionutils.test.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import camp.nextstep.edu.missionutils.test.NsTest;

public class PromotionTest extends NsTest {
	@Test
	void 프로모션_상품을_적게_가져온_경우() {
		assertSimpleTest(() -> {
			runException("[오렌지주스-1]", "N", "N");
			assertThat(output()).contains("현재 오렌지주스은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
		});
	}

	@Test
	void 프로모션_상품의_재고가_부족한_경우() {
		assertSimpleTest(() -> {
			runException("[콜라-13]", "N", "N");
			assertThat(output()).contains("현재 콜라 4개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
		});
	}

	@Test
	void 프로모션이_자동으로_적용된_경우() {
		assertSimpleTest(() -> {
			run("[콜라-3],[에너지바-5]", "N", "N");
			assertThat(output().replaceAll("\\s", "")).contains("내실돈12,000");
		});
	}

	@Override
	public void runMain() {
		Application.main(new String[]{});
	}
}
