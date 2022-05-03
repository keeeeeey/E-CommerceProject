# MarketboroAssignment

## 테이블 설계
<img width="763" alt="스크린샷 2022-05-03 오후 4 47 50" src="https://user-images.githubusercontent.com/87018762/166419933-6327c484-9d9e-4a48-bae0-908ef57a7ba5.png">
- User, Product, Cart, Order 테이블 생성 <br>
- User - Cart 1:1 연관관계 <br>
- User - Order 1:M 연관관계 <br>
- Cart - Product M:M 연관관계 <br>
- Order - Product M:M 연관관계 <br>
다대다 연관관계 매핑의 경우 예상치 못한 쿼리가 나갈수 있어 중간 테이블을 생성하여 일대다 다대일 관계로 풀어서 설계 <br>
- Cart - CartProduct - Product, 1 : M : 1 <br>
- Order - OrderProduct - Product, 1 : M : 1 <br>
