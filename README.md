# MarketboroAssignment

## 테이블 설계
<img width="763" alt="스크린샷 2022-05-03 오후 4 47 50" src="https://user-images.githubusercontent.com/87018762/166419933-6327c484-9d9e-4a48-bae0-908ef57a7ba5.png">
- User, Product, Cart, Order 테이블 생성 <br>
- User - Cart 1:1 연관관계 <br>
- User - Order 1:M 연관관계 <br>
- Cart - Product M:M 연관관계 <br>
- Order - Product M:M 연관관계 <br>

##### 다대다 연관관계 매핑의 경우 예상치 못한 쿼리가 나갈수 있어 중간 테이블을 생성하여 일대다 다대일 관계로 풀어서 설계 <br>
- Cart - CartProduct - Product, 1 : M : 1 <br>
- Order - OrderProduct - Product, 1 : M : 1 <br>

<br>

## 기능 구현
### 로그인, 회원가입
- Spring Security 프레임워크와 Jwt 토큰방식으로 로그인 기능 구현
- Oauth 2.0을 활용하여 카카오 소셜로그인 기능 구현
- 회원 가입시 아이디, 닉네임 중복검사, 필수값 @NotEmpty Validation 체크, 비밀번호 확인 체크
- 회원 가입 성공시 userId 리턴, 로그인 성공시 회원정보와 accessToken, refreshToken return
- 로그인 시 refreshToken을 메모리 DB인 Redis에 저장 (ttl = 2주)
- accessToken이 만료 됐을때 refreshToken으로 accessToken 재발급
- 아이디 형식을 이메일로 설정, 카카오 로그인시 DB에서 이메일로 조회하여 이미 가입한 경우 회원가입 절차 패스
- 회원가입시 계정과 일대일 매핑된 장바구니 생성

    #### accessToken 재발급 조건
    - refreshToken이 만료 되지 않았을때
    - accessToken이 만료 되었을때(만료되지 않았을때 재발급 요청이 오면 해킹으로 간주)
    - Header에 포함된 refreshToken과 Redis에 저장된 refreshToken이 일치할때

<br>

### 상품 CRUD
- SecurityConfig에서 @EnableGlobalMethodSecurity(securedEnabled = true)으로 @Secured 애노테이션을 활성화 하여 관리자만 등록, 수정, 삭제 가능하도록 구현

<br>

### 장바구니 CRUD
- 장바구니 등록, 수정 시 상품의 판매상태(SELLING, SOLDOUT)를 확인하고 재고 수량보다 많은 수량을 등록, 수정하면 예외처리
- 장바구니 상품 삭제시 리스트로 요청하여 다수의 상품 삭제 가능

<br>

### 주문 CRUD
- 상품 주문 시 상품의 판매상태(SELLING, SOLDOUT)를 확인하고 재고 수량보다 많은 수량을 주문하면 예외처리
- 상품 주문 접수, 주문 취소, 배송 완료 시 로그 출력
- 주문 접수 시 장바구니에서 주문 상품 삭제
- 주문 취소 시 이미 주문 취소 상태이거나 배송 완료 상태인 경우 예외처리
- 주문 접수, 주문 취소 완료시 상품 테이블에서 재고 수량 업데이트
- 주문 접수, 주문 취소 다중 처리 가능, 단일 주문 취소 가능

<br>

## 트러블 슈팅
### LazyInitializationException
- 영속성 컨텍스트가 닫히기 전 서비스단에서 모든 로직을 구현하고 메소드마다 @Transactional 애노테이션을 걸어 주었는데도 LazyInitializationException 에러가 발생 <br>
- 서비스단에서 컨트롤러단으로 값을 전달할때 리스트의 제네릭타입을 DTO가 아닌 CartProduct 엔티티로 전달한 것이 문제였고 CartProduct 엔티티로 조회한뒤 stream().map().collect(toList())로 타입 변경한뒤 전달하여 문제를 해결 <br>
- 서비스단의 코드를 간소화 하고자 조회할때 DTO로 조회를 하도록 구현하였고 DTO 매핑이 쉬운 Querrydsl을 적용 <br>

리스트 타입이 CartProduct
```java
@Transactional(readOnly = true)
public List<CartProduct> getAllCartProduct(Long userId) {
    Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR));
    List<CartProduct> cartProductList = cartProductRepository.findAllByCartId(cart.getId());
    return cartProductList;
}
```

<br>

리스트 타입을 CartProductResponDto로 수정
```java
@Transactional(readOnly = true)
public List<CartProductResponseDto> getAllCartProduct(Long userId) {
    Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR));
    List<CartProduct> cartProductList = cartProductRepository.findAllByCartId(cart.getId());
    List<CartProductResponseDto> responseDto = cartProductList
            .stream()
            .map(o -> new CartProductResponseDto(o))
            .collect(toList());
    return responseDto;
}
```

<br>

Querrydsl을 적용하여 CartProductResponseDto로 장바구니 조회
```java
@Transactional(readOnly = true)
public List<CartProductResponseDto> getAllCartProduct(Long userId) {
    Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR));
    List<CartProductResponseDto> responseDto = cartProductRepository.findCartProductByCartId(cart.getId());
    return responseDto;
}
```
