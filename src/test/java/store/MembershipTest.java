package store;

import static camp.nextstep.edu.missionutils.test.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import camp.nextstep.edu.missionutils.test.NsTest;

public class MembershipTest extends NsTest  {
	@Test
	void 일반상품에_멤버십_적용() {
		assertSimpleTest(() -> {
			run("[에너지바-5]", "Y", "N");
			assertThat(output().replaceAll("\\s", "")).contains("내실돈7,000");
		});
	}

	@Test
	void 프로모션_상품에는_멤버십_미적용() {
		assertSimpleTest(() -> {
			run("[사이다-6]", "Y", "N");
			assertThat(output().replaceAll("\\s", "")).contains("내실돈4,000");
		});
	}

	@Test
	void 프로모션_상품과_일반상품에_멤버십_적용() {
		assertSimpleTest(() -> {
			run("[콜라-12]", "Y", "Y", "N"); // 9개는 프로모션 상품, 3개는 일반 상품으로 멤버십 적용
			assertThat(output().replaceAll("\\s", "")).contains("내실돈8,100");
		});
	}

	@Override
	public void runMain() {
		Application.main(new String[]{});
	}
}
