# [WEEK4] 편의점

> 😊3주차 피드백에 대한 회고와 보완해야 할 점
> * **한 메서드를 10라인이 넘지 않도록 구현**하면서 의식적으로 메서드를 분리하자
> * 예외 상황을 고려하여 정리하자
> * 비즈니스 로직과 UI 로직 분리에서 `toString()` 메서드는 로그 성격이 강할 때만 쓰자
> * `final` 키워드를 사용하여 값의 변경을 막자
> * 객체 상태는 생성자나 메서드를 통해서만 변경하도록 `private`로 제한하자
> * **필드의 수를 줄이기** 위해 노력하자
> * **객체 생성은 생성자 주입을 통해 구현해보자**

> 😄4주차 기능 구현을 통한 학습 목표
>  * 관련 함수를 묶어 클래스를 만들고, 객체들이 협력하여 하나의 큰 기능 수행
>  * 클래스와 함수에 대한 단위 테스트하여 올바른 작동 영역 확보
>  * **들여쓰기는 2까지만** 허용
>  * **함수 1개는 한 가지 일만 수행**하도록 작게 쪼개기
>  * `else` 예약어를 사용하지 않기
>  * **Java Enum을 적용**하여 프로그램 구현
>  * 단위 **테스트는 UI 로직을 제외**하고 진행하기 위해 MVC 구조 사용

### 목표: 할인, 재고를 고려하여 최종 결제  금액을 계산하고 안내 

---
사용자가 입력한 상품의 `가격 x 수량`을 계산하여 총 구매액을 구하고,
**프로모션 및 멤버십 할인 정책을 반영**하여 최종 결제 금액을 산출하고 내역을 영수증으로 출력한다.
영수증을 출력한 이후에는 **추가 구매 진행 or 종료 선택**할 수 있다.

* _**재고 관리**_
  * 각 상품의 재고 수량을 고려하여 결제 가능 여부 확인
  * 고객이 상품을 구매할 때마다, 결제된 수량만큼 해당 상품의 재고에서 차감하여 수량을 관리
  * 재고 시스템은 항상 최신 재고 상태를 유지하며, 다음 고객이 구매할 때 이 정보를 제공
* _**프로모션 할인**_
  * 프로모션 마다 기간이 정해져 있고, 오늘 날짜를 기준으로 기간에 포함된 경우에만 할인을 적용
  * Buy N Get 1 Free : N개 구매 시 1개 무료 증정 형태로 진행
  * 각각 지정된 상품에 적용되며, 동일 상품에 대해서는 하나의 프로모션만 적용
  * 프로모션 재고는 별도 관리하여, 이에 대해서만 적용 가능
  * 프로모션 기간 중이라면, 프로모션 재고를 우선적으로 차감하며 부족할 경우에 일반 재고를 사용
  * 고객이 해당 프로모션 개수모다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내
  * 프로모션 재고가 부족하여 일부 수량을 혜택 없이 결제해야 하는 경우, 이를 정가 결제에 대한 안내 수행
* **_멤버십 할인_**
  * 멤버십 회원은 프로모션 미적용 금액의 30%를 할인
  * 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용
  * 멤버십 할인의 최대 한도 = 8000원
* **_영수증 출력_**
  * 영수증은 고객의 구매 내역과 할인을 요약하여 출력
  * 영수증 항목
    * 구매 상품 내역: 구매한 상품명, 수량, 가격
    * 증정 상품 내역: 프로모션에 따라 무료로 제공된 증정 상품의 목록
    * 금액 정보
      * 총 구매액: 구매한 상품의 총 수량과 총 금액
      * 행사할인: 프로모션에 의해 할인된 금액
      * 멤버십할인: 멤버십에 의해 추가로 할인된 금액
      * 내실돈: 최종 결제 금액
  * 영수증의 구성 요소를 보기 좋게 정렬하여 고객이 쉽게 금액과 수량을 확인할 수 있게 작성

### 📖구현 기능 목록

---

1. 상품 및 프로모션 초기화
   * `promotions.md`의 항목들에 대해 Promotion 객체 생성(name,buy,get,start_date,end_date)
   * `products.md`의 항목들에 대해 Product 객체 생성(name,price,quantity,promotion)
   * 만약 프로모션 상품만 있다면, 재고 없음을 나타내는 일반 상품도 객체 생성
   * 현재 보유하고 있는 상품 출력
2. 상품 구매
   * 사용자에게 구매할 상품과 수량을 입력 (`[상품명-수량]`)
   * 입력에 대한 예외 처리
     * 형식이 올바르지 않는 경우
     * 존재하지 않는 상품을 입력한 경우
     * 구매 수량이 재고 수량을 초과한 경우
     * 구분자(,)의 위치가 올바르지 않은 경우
   * 구입한 내역을 BuyingProduct 객체에 저장
     * 상품명과 필요한 수량을 필드로 저장
     * 추가적으로 PromotionStatus 필드를 추가해 상태에 따른 안내 문구 출력
     * PromotionStatus -> quantity를 프로모션, 일반 상품에서 필요한 수량으로 구분
   * PromotionStatus에 따른 안내 문구 출력
   * 일반 재고 및 프로모션 재고 목록 업데이트
   * 위의 결과들을 반영하여 영수증에 정보 저장
3. 행사 및 멤버십 할인 적용
    * 프로모션이 적용되어 총 할인된 가격 계산 (**프로모션 미적용 금액의 30%**)
    * 멤버십 할인 여부 입력
      * `Y/N` 중 한 개의 문자가 아닌 경우 예외 발생
    * 총 결제 금액에 멤버십 할인 적용 (**최대 한도 8000원**)
    * 영수증의 최종 금액에 반영
4. 영수증 출력 및 재구매 의사 확인
    * 구매 상품, 증정 상품, 금액 정보를 포함한 영수증 출력
    * 재구매 의사 입력
      * `Y/N` 중 한 개의 문자가 아닌 경우 예외 발생
    * 최종적으로 반영된 재고를 출력

### ✏️기능 설명

---

> **다음 내용을 고민하고 적용했습니다.**
> *

1. **Model**
    * 
2. **View**
    * 
3. **Controller**
    * 
